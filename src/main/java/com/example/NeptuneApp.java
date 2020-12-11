package com.example;

import io.github.plume.oss.Extractor;
import io.github.plume.oss.drivers.DriverFactory;
import io.github.plume.oss.drivers.GraphDatabase;
import io.github.plume.oss.drivers.NeptuneDriver;

import java.io.File;
import java.io.IOException;

public class NeptuneApp {

    public static void main(String[] args) {
        System.out.println("Creating driver");
        try (NeptuneDriver driver = (NeptuneDriver) DriverFactory.invoke(GraphDatabase.NEPTUNE)) {
            driver.addHostnames("<neptune-cluster-address-without-https>")
                    .keyCertChainFile("<pem-certificate-path-here>")
                    .port(8182)
                    .connect();
            // Create the extractor with the driver and class root directory
            Extractor extractor = new Extractor(driver, new File("./src/main/resources/examples"));
            File f = new File("./src/main/resources/examples/intraprocedural/basic/Basic1.java");
            // Load the extractor with the directory of all the tests
            extractor.load(f);
            // Project the loaded classes to the graph database
            System.out.println("Projecting graph");
            extractor.project();
            driver.close();
            System.out.println("Done!");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Something went wrong! Details " + e.getMessage());
        }
    }

}
