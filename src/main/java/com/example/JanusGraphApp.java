package com.example;

import za.ac.sun.plume.Extractor;
import za.ac.sun.plume.drivers.DriverFactory;
import za.ac.sun.plume.drivers.GraphDatabase;
import za.ac.sun.plume.drivers.JanusGraphDriver;

import java.io.File;

public class JanusGraphApp {

    public static void main(String[] args) throws Exception {
        System.out.println("Creating driver");
        JanusGraphDriver driver = (JanusGraphDriver) DriverFactory.invoke(GraphDatabase.JANUS_GRAPH);
        driver.remoteConfig("./src/main/resources/conf/remote-graph.properties").connect();
        // Attach the hook to the cannon
        Extractor extractor = new Extractor(driver, new File("./src/main/resources/examples"));
        File f = new File("./src/main/resources/examples/intraprocedural/basic/Basic1.java");
        // Load the cannon with the directory of all the tests
        extractor.load(f);
        // Fire the loaded files to be projected the graph database
        System.out.println("Projecting graph");
        extractor.project();
        driver.close();
        System.out.println("Done!");
        System.exit(0);
    }

}
