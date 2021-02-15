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





        XMLParser.parse(Const.FOLDER_NAME +"\\28-ex_xml_atu.xml");


    }
}