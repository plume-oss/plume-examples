package com.example;

import za.ac.sun.plume.Extractor;
import za.ac.sun.plume.drivers.DriverFactory;
import za.ac.sun.plume.drivers.GraphDatabase;
import za.ac.sun.plume.drivers.TinkerGraphDriver;

import java.io.File;
import java.io.IOException;

public class TinkerGraphApp {

    public static void main(String[] args) throws IOException {
        System.out.println("Creating driver");
        TinkerGraphDriver driver = (TinkerGraphDriver) DriverFactory.invoke(GraphDatabase.TINKER_GRAPH);
        driver.connect();
        // Attach the hook to the cannon
        Extractor extractor = new Extractor(driver, new File("./src/main/resources/examples"));
        File f = new File("./src/main/resources/examples/intraprocedural/basic/Basic1.java");
        // Load the cannon with the directory of all the tests
        extractor.load(f);
        // Fire the loaded files to be projected the graph database
        System.out.println("Projecting graph");
        extractor.project();
        // For the TinkerGraph hook, we can export this graph using the format and
        // directory specified by the extension
        driver.exportGraph("./graph.xml");
        System.out.println("Done!");
    }

}
