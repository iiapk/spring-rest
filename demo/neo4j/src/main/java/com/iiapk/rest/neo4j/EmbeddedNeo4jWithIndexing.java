package com.iiapk.rest.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;

public class EmbeddedNeo4jWithIndexing {
	
	public final static String DB_PATH = "target/database/location";
	public final static String USERNAME_KEY = "user_key";
	GraphDatabaseService graphDb;
	Node firstNode;
	Node secondNode;
	Relationship relationship;
	Index<Node> nodeIndex;
	
	public EmbeddedNeo4jWithIndexing() {
		super();
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		nodeIndex = graphDb.index().forNodes( "nodes" );
		System.out.println(nodeIndex.getName());
		registerShutdownHook( graphDb );
	}
	
	public void doSomething(){
		firstNode = graphDb.createNode();
		//graphDb.getReferenceNode().createRelationshipTo(firstNode, RelTypes.USERS_REFERENCE );
		for ( int id = 0; id < 100; id++ )
	    {
	        Node userNode = createAndIndexUser( idToUserName( id ) );
	        firstNode.createRelationshipTo( userNode,RelTypes.USER );
	    }
	}
	
	public void findSomething(){
		int idToFind = 45;
		Node foundUser = nodeIndex.get( USERNAME_KEY,idToUserName( idToFind ) ).getSingle();
		System.out.println( "The username of user " + idToFind + " is " + foundUser.getProperty( USERNAME_KEY ) );
	}
	
	private String idToUserName( final int id )
	{
	    return "user" + id + "@neo4j.org";
	}
	 
	private Node createAndIndexUser( final String username )
	{
	    Node node = graphDb.createNode();
	    node.setProperty( USERNAME_KEY, username );
	    nodeIndex.add( node, USERNAME_KEY, username );
	    return node;
	}
	
	public void doTransaction(){
		Transaction tx = graphDb.beginTx();
		try
		{
			//doSomething();
		    tx.success();
		}
		finally
		{
			findSomething();
			tx.finish();
		}
	}
	
	private static enum RelTypes implements RelationshipType
	{
		USERS_REFERENCE,
	    USER
	}
	
	private static void registerShutdownHook( final GraphDatabaseService graphDb )
	{
	    // Registers a shutdown hook for the Neo4j instance so that it
	    // shuts down nicely when the VM exits (even if you "Ctrl-C" the
	    // running example before it's completed)
	    Runtime.getRuntime().addShutdownHook( new Thread()
	    {
	        @Override
	        public void run()
	        {
	            graphDb.shutdown();
	        }
	    } );
	}

	public static void main(String[] args) {
		new EmbeddedNeo4jWithIndexing().doTransaction();
	}

}
