package com.quarnuts;

import java.sql.*;

public class DatabaseHandler extends Configs {
    static Connection dbConnection;

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
                    + Const.RECORD_STREET_ID + " FROM " + Const.RECORD_TABLE + " WHERE " + Const.RECORD_OBL_ID + obl + " AND " + Const.RECORD_REGION_ID + region + " AND " + Const.RECORD_CITY_ID
                    + city + " AND " + Const.RECORD_CITY_REGION_ID + cityRegion + " AND " + Const.RECORD_STREET_ID + street;


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

    public Integer searchJsonStreets(String search) {
        String insert = "SELECT " + Const.STREET_ID + " FROM " + Const.STREET_TABLE + " WHERE " + Const.STREET_VALUE + "=(?)";
        Integer result = null;
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, search);
            ResultSet resultSet;
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Integer searchStreetId(String street) {
        String insert = "SELECT street FROM streets WHERE NAME=(?)";
        Integer result = null;

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, street);
            ResultSet resultSet;
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Ошибка в поиске в таблице значения street");
        }
        return result;
    }


    public void insertJsonAddress(String buildingnumb, String street, String litera_object, String corpus_numb_new) {

        Integer num = searchStreetId(street);
        String insert = "INSERT INTO buildings (buildingnumb,street,litera_object,corpus_numb_new) VALUES (?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, buildingnumb);
            preparedStatement.setObject(2,num);
            preparedStatement.setObject(3,litera_object);
            preparedStatement.setObject(4,corpus_numb_new);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateJsonStreets(String search, String reestrcode, String codecategory_ukr, String codecategoryRu, String codecategoryLat,
                                  String streetname_ukr, String streetnameRu, String streetnameLat, String history,
                                  String kodSts, String docNumber, String docDate) {

        String insert = "UPDATE " + Const.STREET_TABLE + " SET " + Const.STREET_REESTRCODE + "='" + reestrcode + "'," +
                Const.STREET_CODECATEGORY_UKR + "='" + codecategory_ukr + "'," + Const.STREET_CODECATEGORY_RU + "='" + codecategoryRu + "'," + Const.STREET_CODECATEGORY_LAT + "='" + codecategoryLat + "'," +
                Const.STREET_STREETNAME_UKR + "='" + streetname_ukr + "'," + Const.STREET_STREETNAME_RU + "='" + streetnameRu + "'," + Const.STREET_STREETNAME_LAT + "='" + streetnameLat + "'," + Const.STREET_HISTORY + "=" + history + "," + Const.STREET_KOD_STS + "=" + kodSts + "," +
                Const.STREET_DOC_NUMBER + "='" + docNumber + "'," + Const.STREET_DOC_DATE + "='" + docDate + "' WHERE " + Const.STREET_VALUE + "='" + search+"'";
        try {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate(insert);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

