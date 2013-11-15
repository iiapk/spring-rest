package com.iiapk.rest.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class EmbeddedNeo4j {
	
	public final static String DB_PATH = "target/database/location";
	GraphDatabaseService graphDb;
	Node firstNode;
	Node secondNode;
	Relationship relationship;
	
	public EmbeddedNeo4j() {
		super();
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		registerShutdownHook( graphDb );
	}
	
	public void doSomething(){
		firstNode = graphDb.createNode();
		firstNode.setProperty( "message", "Hello, " );
		secondNode = graphDb.createNode();
		secondNode.setProperty( "message", "World!" );
		relationship = firstNode.createRelationshipTo( secondNode, RelTypes.KNOWS );
		relationship.setProperty( "message", "brave Neo4j " );
	}
	
	public void doTransaction(){
		Transaction tx = graphDb.beginTx();
		try
		{
			doSomething();
		    tx.success();
		}
		finally
		{
		    System.out.print( firstNode.getProperty( "message" ) );
			System.out.print( relationship.getProperty( "message" ) );
			System.out.print( secondNode.getProperty( "message" ) );
			tx.finish();
		}
	}
	
	private static enum RelTypes implements RelationshipType
	{
	    KNOWS
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
		new EmbeddedNeo4j().doTransaction();
	}

}
