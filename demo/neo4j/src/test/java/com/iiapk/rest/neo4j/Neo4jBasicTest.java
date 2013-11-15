package com.iiapk.rest.neo4j;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.test.TestGraphDatabaseFactory;

public class Neo4jBasicTest {
	
	public final static String USERNAME_KEY = "user_key";
	GraphDatabaseService graphDb;
	Node firstNode;
	Node secondNode;
	Relationship relationship;
	Index<Node> nodeIndex;
	
	@Before
	public void prepareTestDatabase()
	{
	    graphDb = new TestGraphDatabaseFactory().newImpermanentDatabaseBuilder().newGraphDatabase();
	}
	
	@After
	public void destroyTestDatabase()
	{
	    graphDb.shutdown();
	}
	
	public void doSomething(){
		nodeIndex = graphDb.index().forNodes( "nodes" );
		firstNode = graphDb.createNode();
		graphDb.getReferenceNode().createRelationshipTo(firstNode, RelTypes.USERS_REFERENCE );
		for ( int id = 0; id < 100; id++ )
	    {
	        Node userNode = createAndIndexUser( idToUserName( id ) );
	        firstNode.createRelationshipTo( userNode,RelTypes.USER );
	    }
	}
	
	public void findSomething(){
		int idToFind = 45;
		Node foundUser = nodeIndex.get(USERNAME_KEY,idToUserName(idToFind)).getSingle();
		Assert.assertEquals("user45@neo4j.org", foundUser.getProperty(USERNAME_KEY));
	}
	
	@Test
	public void testdoSomething(){
		Transaction tx = graphDb.beginTx();
		try
		{
			doSomething();
		    tx.success();
		}
		catch ( Exception e )
		{
		    tx.failure();
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

}
