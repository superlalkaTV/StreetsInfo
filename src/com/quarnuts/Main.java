package com.quarnuts;

import java.io.File;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String args[]) {

        FileManipulation.createFolder();
        FileManipulation.downloadZipFile();
        FileManipulation.unZipFile();
        FileManipulation.deleteZipFile();

        XMLParser.countTags(Const.FOLDER_NAME +"\\28-ex_xml_atu.xml");

        Configs.initConfig();

        DatabaseHandler databaseHandler = new DatabaseHandler();

        XMLParser.parse(Const.FOLDER_NAME +"\\28-ex_xml_atu.xml", databaseHandler);



//        JSONParser.addressJsonParse("https://service.ombk.odessa.ua/arcgis/rest/services/Odessa_MSK51/Reestr_Adress_MSK51/MapServer/4844/query?f=json&where=1%3D1&returnGeometry=true&spatialRel=esriSpatialRelIntersects&outFields=*&orderByFields=objectid%20ASC&resultOffset=0&resultRecordCount=1000");
//        JSONParser.streetsJsonParse(databaseHandler); asd

    }
}