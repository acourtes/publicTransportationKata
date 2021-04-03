package com.transportation;

import com.transportation.calculator.UnknownCostException;

import java.io.File;

public class CustomerSummariesLauncher {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("You have to indicate the source JSON file and the target json file as arguments");
            System.exit(1);
        }

        var inputFile = new File(args[0]);

        if (!inputFile.exists()) {
            System.out.println("The source JSON file does not exist");
            System.exit(2);
        }

        var outputFile = new File(args[1]);
        try {
            CustomerSummariesGenerator.from(inputFile).generateCustomersSummariesFile(outputFile);
        } catch (UnknownCostException e) {
            System.out.println(e.getMessage());
        }
    }
}
