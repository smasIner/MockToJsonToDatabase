package org.muratShaikhutdinov;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: Main <number_of_records> <output_file>");
            return;
        }

        int n = Integer.parseInt(args[0]);
        String outputFileName = args[1];

        LocationGenerator.generateLocations(n, outputFileName);
    }
}
