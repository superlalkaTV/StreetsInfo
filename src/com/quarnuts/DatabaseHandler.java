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
        }
        catch (Exception e){
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

    public boolean checkRecord(Integer oblId, Integer regionId, Integer cityId, Integer cityRegionId, Integer streetId) {
        String insert = "SELECT " + Const.RECORD_OBL_ID +"," + Const.RECORD_REGION_ID + "," + Const.RECORD_CITY_ID + "," + Const.RECORD_CITY_REGION_ID + ","
                + Const.RECORD_STREET_ID + " FROM " + Const.RECORD_TABLE  + " WHERE " + Const.RECORD_OBL_ID + "=? AND " + Const.RECORD_REGION_ID + "=? AND " + Const.RECORD_CITY_ID
                + "=? AND " + Const.RECORD_CITY_REGION_ID + "=? AND " + Const.RECORD_STREET_ID +"=?";
        boolean isAvailable = false;

        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setObject(1, oblId);
            preparedStatement.setObject(2, regionId);
            preparedStatement.setObject(3, cityId);
            preparedStatement.setObject(4, cityRegionId);
            preparedStatement.setObject(5, streetId);

            ResultSet resultSet;
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                isAvailable = true;
            }

        }
        catch (Exception e){
            System.out.println("Ошибка в проверке поля record!");
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

