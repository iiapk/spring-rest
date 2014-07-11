package com.iiapk.rest.zoo;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.server.ZooKeeperServerMain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestMainServer extends ZooKeeperServerMain {
	
    public static final int CLIENT_PORT = 2181;
    public static final String CONF_FILE = "D:\\soft\\zookeeper-3.4.5-1\\conf\\zoo.cfg";
    public static final String DIR_FILE = "D:\\soft\\zookeeper-3.4.5-1\\data";
    
    public static class MainThread extends Thread {
    	
        final File confFile;
        final TestMainServer main;
        
        public MainThread(int clientPort) throws IOException {
        	
            super("Standalone server with clientPort:" + clientPort);
            confFile = new File(CONF_FILE);
            FileWriter fwriter = new FileWriter(confFile);
            fwriter.write("tickTime=2000\n");
            fwriter.write("initLimit=10\n");
            fwriter.write("syncLimit=5\n");
            
            File dataDir = new File(DIR_FILE);
            String df = StringUtils.replace(dataDir.toString(),"\\","/");
            fwriter.write("dataDir=" + df + "\n");
            fwriter.write("clientPort=" + clientPort + "\n");
            fwriter.flush();
            fwriter.close();

            main = new TestMainServer();
        }

        public void run() {
            String args[] = new String[1];
            args[0] = confFile.toString();
            try {
                main.initializeAndRun(args);
            } catch (Exception e) {
                
            }
        }
    }
    
    public static void start(){

        MainThread main = null;
        try {
            main = new MainThread(CLIENT_PORT);
        } catch (IOException e) {
            return ;
        }
        main.start();
    }

    public static void main(String[] args) {
        TestMainServer.start();
    }
}
