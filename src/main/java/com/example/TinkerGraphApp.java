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
        // Create the extractor with the driver and class root directory
        Extractor extractor = new Extractor(driver, new File("./src/main/resources/examples"));
        File f = new File("./src/main/resources/examples/intraprocedural/basic/Basic1.java");
        // Load the extractor with the directory of all the tests
        extractor.load(f);
        // Project the loaded classes to the graph database
        System.out.println("Projecting graph");
        extractor.project();
        // For the TinkerGraph hook, we can export this graph using the format and
        // directory specified by the extension
        driver.exportGraph("./graph.xml");
        System.out.println("Done!");
    }

}
