package org.example;

import java.io.FileReader;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonToSQLite {

    private static final Logger LOGGER = Logger.getLogger(JsonToSQLite.class.getName());

    public static void main(String[] args) {
        String url = "jdbc:sqlite:database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);

            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(new FileReader("/path/.."), JsonArray.class);

            createTableIfNotExists(conn);

            insertData(conn, jsonArray);
            conn.commit();
            logStatistics(conn);


        } catch (Exception e) {
            handleException(conn, e);
        } finally {
            closeConnection(conn);
        }
    }

    private static void handleException(Connection conn, Exception e) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to rollback transaction", ex);
        }
        LOGGER.log(Level.SEVERE, "Failed to insert data", e);
    }

    private static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to close connection", e);
        }
    }

    private static void insertData(Connection conn, JsonArray jsonArray) throws SQLException {
        String sql = "INSERT OR REPLACE INTO locationData (UID, ownerId, city, street, houseNumber, roomNumber, longitude, latitude, creationTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int insertedRecords = 0;
        int replacedRecords = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                pstmt.setString(1, jsonObject.get("UID").getAsString());
                pstmt.setString(2, jsonObject.get("ownerId").getAsString());
                pstmt.setString(3, jsonObject.get("city").getAsString());
                pstmt.setString(4, jsonObject.get("street").getAsString());
                pstmt.setString(5, jsonObject.get("houseNumber").getAsString());
                pstmt.setString(6, jsonObject.get("roomNumber").getAsString());
                pstmt.setDouble(7, jsonObject.get("longitude").getAsDouble());
                pstmt.setDouble(8, jsonObject.get("latitude").getAsDouble());
                pstmt.setLong(9, jsonObject.get("creationTime").getAsLong());

                int rowsAffected = pstmt.executeUpdate();
            }
       
        }
    }


    private static void createTableIfNotExists(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS locationData ("
                + "UID VARCHAR(36) PRIMARY KEY,"
                + "ownerId VARCHAR(36),"
                + "city VARCHAR(255),"
                + "street VARCHAR(255),"
                + "houseNumber VARCHAR(10),"
                + "roomNumber VARCHAR(10),"
                + "longitude DOUBLE,"
                + "latitude DOUBLE,"
                + "creationTime BIGINT)";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    private static void logStatistics(Connection conn) throws SQLException {
        LOGGER.info(STR."Total records saved: \{getTotalRecords(conn)}");
        LOGGER.info(STR."Unique owners \{getTotalUniqueOwners(conn)}");
    }
    private static int getTotalRecords(Connection conn){
        String query = "SELECT COUNT(*) AS total FROM locationData";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("total");
            } else {
                System.out.println("No result returned from the query.");
                return 0;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    private static int getTotalUniqueOwners(Connection conn) {
        String query = "SELECT COUNT(DISTINCT ownerId) AS total FROM locationData";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("total");
            } else {
                System.out.println("No result returned from the query.");
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

}



