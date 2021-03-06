package com.example;

import io.github.plume.oss.Extractor;
import io.github.plume.oss.drivers.DriverFactory;
import io.github.plume.oss.drivers.GraphDatabase;
import io.github.plume.oss.drivers.OverflowDbDriver;

import java.io.File;
import java.io.IOException;

public class OverflowDbApp {

    public static void main(String[] args) {
        System.out.println("Creating driver");
        try (OverflowDbDriver driver = (OverflowDbDriver) DriverFactory.invoke(GraphDatabase.OVERFLOWDB)) {
            // Specify file to store database
            driver.storageLocation("cpg.bin");
            // Create the extractor with the driver and class root directory
            Extractor extractor = new Extractor(driver);
            File f = new File("./src/main/resources/examples/intraprocedural/basic/Basic1.java");
            // Load the extractor with the directory of all the tests
            extractor.load(f);
            // Project the loaded classes to the graph database
            System.out.println("Projecting graph");
            extractor.project();
            System.out.println("Done!");
        } catch (IOException e) {
            System.out.println("Something went wrong! Details " + e.getMessage());
        }
    }

}
