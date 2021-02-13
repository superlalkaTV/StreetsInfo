package com.quarnuts;

public class Main {

    public static void main(String args[]) {

        FileManipulation.createFolder();
        FileManipulation.downloadZipFile();
        FileManipulation.unZipFile();
        FileManipulation.deleteZipFile();

        XMLParser.parse(Const.FOLDER_NAME +"\\28-ex_xml_atu.xml");

    }
}