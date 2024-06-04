package org.muratShaikhutdinov;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Generates a specified number of Location objects and saves them to a JSON file.
 */
public class LocationGenerator {

    // Interval for logging progress
    private static final int LOG_INTERVAL = 100;

    // Faker instance to generate realistic data
    private static final Faker faker = new Faker();

    /**
     * Generates a list of Location objects and saves them to a file.
     *
     * @param n              the number of Location objects to generate
     * @param outputFileName the name of the file to save the generated data
     */
    static void generateLocations(int n, String outputFileName) {
        long startTime = System.currentTimeMillis();
        System.out.println("Generation started at: " + startTime);

        // Generate a list of unique owner IDs
        List<String> ownerIds = generateOwnerIds(Math.max(n / 2, 10));
        List<Location> locations = new ArrayList<>(n);
        Random random = new Random();

        // Generate n Location objects
        for (int i = 0; i < n; i++) {
            String ownerId = ownerIds.get(random.nextInt(ownerIds.size()));
            String city = faker.address().city();
            String street = faker.address().streetName();
            String houseNumber = faker.address().buildingNumber();
            String roomNumber = String.valueOf(random.nextInt(100) + 1);
            double longitude = Double.parseDouble(faker.address().longitude());
            double latitude = Double.parseDouble(faker.address().latitude());

            Location location = new Location(ownerId, city, street, houseNumber, roomNumber, longitude, latitude);
            locations.add(location);

            // Log progress at intervals of LOG_INTERVAL
            if ((i + 1) % LOG_INTERVAL == 0) {
                System.out.println("Generated " + (i + 1) + " records");
            }
        }

        // Save generated locations to a file
        saveLocationsToFile(locations, outputFileName);

        long endTime = System.currentTimeMillis();
        System.out.println("Generation finished at: " + endTime);
        System.out.println("Total time taken: " + (endTime - startTime) + " ms");
    }

    /**
     * Generates a list of unique owner IDs.
     *
     * @param count the number of owner IDs to generate
     * @return a list of unique owner IDs
     */
    private static List<String> generateOwnerIds(int count) {
        List<String> ownerIds = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            ownerIds.add(UUID.randomUUID().toString());
        }
        return ownerIds;
    }

    /**
     * Saves a list of Location objects to a file in JSON format.
     *
     * @param locations the list of Location objects to save
     * @param fileName  the name of the file to save the data
     */
    private static void saveLocationsToFile(List<Location> locations, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(locations, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

