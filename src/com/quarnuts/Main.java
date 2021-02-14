package com.quarnuts;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) {

        FileManipulation.createFolder();
        FileManipulation.downloadZipFile();
        FileManipulation.unZipFile();
        FileManipulation.deleteZipFile();

        Configs.initConfig();



        XMLParser.parse(Const.FOLDER_NAME +"\\28-ex_xml_atu.xml");





    }
}