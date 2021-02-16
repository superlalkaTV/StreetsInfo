package com.quarnuts;

import java.sql.*;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() {
        try {
            String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

            Class.forName("com.mysql.cj.jdbc.Driver");

            dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
            return dbConnection;
        } catch (Exception e) {
            System.out.println("Error with connecting to database!");
        }

        return null;

    }

    public void record(Integer oblId, Integer regionId, Integer cityId, Integer cityRegionId, Integer streetId) {
        String insert = "INSERT INTO " + Const.RECORD_TABLE + " (" + Const.RECORD_OBL_ID + "," + Const.RECORD_REGION_ID + "," + Const.RECORD_CITY_ID +
                "," + Const.RECORD_CITY_REGION_ID + "," + Const.RECORD_STREET_ID + ") VALUES(?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setObject(1, oblId);
            preparedStatement.setObject(2, regionId);
            preparedStatement.setObject(3, cityId);
            preparedStatement.setObject(4, cityRegionId);
            preparedStatement.setObject(5, streetId);

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Ошибка при создании поля record!");
        }
    }

    public boolean checkRecord(Integer oblId, Integer regionId, Integer cityId, Integer cityRegionId, Integer streetId) {

        boolean isAvailable = false;

        try {
            Statement statement = dbConnection.createStatement();

            String obl = String.valueOf(oblId);
            obl = (obl.equals("null")) ? " is null" : " =" + obl;
            String region = String.valueOf(regionId);
            region = (region.equals("null")) ? " is null" : " =" + region;
            String city = String.valueOf(cityId);
            city = (city.equals("null")) ? " is null" : " =" + city;
            String cityRegion = String.valueOf(cityRegionId);
            cityRegion = (cityRegion.equals("null")) ? " is null" : " =" + cityRegion;
            String street = String.valueOf(streetId);
            street = (street.equals("null")) ? " is null" : " =" + street;


            String insert = "SELECT " + Const.RECORD_OBL_ID + "," + Const.RECORD_REGION_ID + "," + Const.RECORD_CITY_ID + "," + Const.RECORD_CITY_REGION_ID + ","
                    + Const.RECORD_STREET_ID + " FROM " + Const.RECORD_TABLE + " WHERE " + Const.RECORD_OBL_ID + obl + " AND " + Const.RECORD_REGION_ID + region +" AND " + Const.RECORD_CITY_ID
                    + city +" AND " + Const.RECORD_CITY_REGION_ID + cityRegion +" AND " + Const.RECORD_STREET_ID + street;


            ResultSet resultSet;
            resultSet = statement.executeQuery(insert);

            if (!resultSet.next()) {
                isAvailable = true;
            }

        } catch (Exception e) {
            System.out.println("Ошибка в проверке возможности добваления поля record!");
            e.printStackTrace();
        }

        return isAvailable;
    }

    public Integer searchId(String table, String value, String id) {
        String insert = "SELECT " + id + " FROM " + table + " WHERE NAME=(?)";
        Integer result = null;

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, value);
            ResultSet resultSet;
            resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {

                result = resultSet.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Ошибка в поиске в таблице " + table);
        }

        return result;
    }

    public boolean check(String table, String value, String id) {
        String insert = "SELECT " + id + " FROM " + table + " WHERE name=(?)";
        boolean isAvailable = false;
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, value);
            ResultSet resultSet;
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                isAvailable = true;
            }
        } catch (Exception e) {
            System.out.println("Ошибка в поиске в таблице " + table);
            e.printStackTrace();
        }

        return isAvailable;
    }

    public void insert(String table, String tableValue, String value) {

        String insert = "INSERT INTO " + table + "(" + tableValue + ") VALUES(?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, value);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

