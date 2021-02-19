package com.quarnuts;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JSONParser {

    private static StringBuffer getJson(String link) {
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(link);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (Exception e) {
            System.out.println("Ошибка в загрузке json по ссылке!");
        }
        return response;
    }

    private static final StringBuffer json1 = getJson("https://service.ombk.odessa.ua/arcgis/rest/services/Odessa_MSK51/Streets_Reestr_MSK51/MapServer/0/query?f=json&where=1%3D1&returnGeometry=false&spatialRel=esriSpatialRelIntersects&outFields=*&orderByFields=objectid%20ASC&resultOffset=0&resultRecordCount=1000");
    private static final StringBuffer json2 = getJson("https://service.ombk.odessa.ua/arcgis/rest/services/Odessa_MSK51/Streets_Reestr_MSK51/MapServer/0/query?f=json&where=1%3D1&returnGeometry=false&spatialRel=esriSpatialRelIntersects&outFields=*&orderByFields=objectid%20ASC&resultOffset=1000&resultRecordCount=1000");


    public static void addressJsonParse(String url) {
        StringBuffer response = getJson(url);
        DatabaseHandler databaseHandler = new DatabaseHandler();

        try {
            String str = null;
            JsonParser parser = new JsonParser();

            JsonObject mainObject = parser.parse(String.valueOf(response)).getAsJsonObject();
            JsonArray pItem = mainObject.getAsJsonArray("features");
            System.out.println(pItem.size());
            for (JsonElement user : pItem) {

                JsonObject userObject = user.getAsJsonObject();

                JsonObject asd = (JsonObject) userObject.get("attributes");


                str = userObject.get("attributes").toString();

                System.out.println(str);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        System.out.println(response);
    }


    private static int num2;

    public static void streetsJsonParse(DatabaseHandler databaseHandler, String streetName) {

            try {
                JsonParser parser = new JsonParser();

                JsonObject mainObject = parser.parse(String.valueOf(json1)).getAsJsonObject();
                JsonArray pItem = mainObject.getAsJsonArray("features");

                if (pItem.size() < 1)
                    return;

                for (JsonElement element : pItem) {
                    JsonObject userObject = element.getAsJsonObject();

                    JsonObject object = (JsonObject) userObject.get("attributes");

                    String searchName = streetName;
                    searchName = searchName.replace("вул.", "");
                    searchName = searchName.replace("пл.", "");
                    searchName = searchName.replace("пр", "");
                    searchName = searchName.replace("пров", "");


                    String streetnameUkr = object.get("streetname_ukr").toString().replace("\"", "");
                    if (streetnameUkr.equals(searchName)) {
                        String codecategoryUkr = object.get("codecategory_ukr").toString().replace("\"", "");
                        String reestrcode = object.get("reestrcode").toString().replace("\"", "");
                        String codecategoryRu = object.get("codecategory_ru").toString().replace("\"", "");
                        String codecategoryLat = object.get("codecategory_lat").toString().replace("\"", "");
                        String streetnameRu = object.get("streetname_ru").toString().replace("\"", "");
                        String streetnameLat = object.get("streetname_lat").toString().replace("\"", "");
                        String history = object.get("history").toString().replace("\"", "");
                        String kodSts = object.get("kod_sts").toString().replace("\"", "");
                        String docNumber = object.get("doc_number").toString().replace("\"", "");
                        String docDate;
                        if (String.valueOf(object.get("doc_date")).equals("null")) {
                            docDate = "null";
                        } else {
                            long longDate = Long.valueOf(String.valueOf(object.get("doc_date")));
                            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                            Date date = new Date(longDate);
                            docDate = format.format(date);
                        }
                        String koment = object.get("koment").toString().replace("\"", "");

                        databaseHandler.updateJsonStreets(streetName, reestrcode, codecategoryUkr, codecategoryRu, codecategoryLat, streetnameUkr, streetnameRu, streetnameLat, history, kodSts, docNumber, docDate);
                        return;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        try {
            JsonParser parser = new JsonParser();

            JsonObject mainObject = parser.parse(String.valueOf(json2)).getAsJsonObject();
            JsonArray pItem = mainObject.getAsJsonArray("features");

            if (pItem.size() < 1)
                return;

            for (JsonElement element : pItem) {
                JsonObject userObject = element.getAsJsonObject();

                JsonObject object = (JsonObject) userObject.get("attributes");

                String searchName = streetName;
                searchName = searchName.replace("вул.", "");
                searchName = searchName.replace("пл.", "");
                searchName = searchName.replace("пр", "");
                searchName = searchName.replace("пров", "");


                String streetnameUkr = object.get("streetname_ukr").toString().replace("\"", "");
                if (streetnameUkr.equals(searchName)) {
                    String codecategoryUkr = object.get("codecategory_ukr").toString().replace("\"", "");
                    String reestrcode = object.get("reestrcode").toString().replace("\"", "");
                    String codecategoryRu = object.get("codecategory_ru").toString().replace("\"", "");
                    String codecategoryLat = object.get("codecategory_lat").toString().replace("\"", "");
                    String streetnameRu = object.get("streetname_ru").toString().replace("\"", "");
                    String streetnameLat = object.get("streetname_lat").toString().replace("\"", "");
                    String history = object.get("history").toString().replace("\"", "");
                    String kodSts = object.get("kod_sts").toString().replace("\"", "");
                    String docNumber = object.get("doc_number").toString().replace("\"", "");
                    String docDate;
                    if (String.valueOf(object.get("doc_date")).equals("null")) {
                        docDate = "null";
                    } else {
                        long longDate = Long.valueOf(String.valueOf(object.get("doc_date")));
                        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                        Date date = new Date(longDate);
                        docDate = format.format(date);
                    }
                    String koment = object.get("koment").toString().replace("\"", "");

                    databaseHandler.updateJsonStreets(streetName, reestrcode, codecategoryUkr, codecategoryRu, codecategoryLat, streetnameUkr, streetnameRu, streetnameLat, history, kodSts, docNumber, docDate);
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
