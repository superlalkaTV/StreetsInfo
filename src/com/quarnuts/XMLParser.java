package com.quarnuts;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class XMLParser {
    private static int COUNTER = 0;


    /*public static void recordCounter () {
        XmlDocument readDoc = new XmlDocument();
        readDoc.Load(MapPath("Results.xml"));
        int count = readDoc.SelectNodes("root/User").Count;
        lblResults.Text = count.ToString();
        int NoCount = readDoc.SelectNodes("JSEnabled[. = \"No\"]").Count;
    }*/

    public static void parse(String file) {

        DatabaseHandler databaseHandler = new DatabaseHandler();

        final String fileName = file;

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            // Здесь мы определили анонимный класс, расширяющий класс DefaultHandler
            DefaultHandler handler = new DefaultHandler() {
                // Флаги установки полей
                boolean obl = false;
                boolean region = false;
                boolean city = false;
                boolean city_region = false;
                boolean street = false;

                // ID добавленных полей
                Integer oblId = null;
                Integer regionId = null;
                Integer cityId = null;
                Integer cityRegionId = null;
                Integer streetId = null;

                // Метод вызывается когда SAXParser "натыкается" на начало тэга
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("OBL_NAME")) {
                        obl = true;
                    }
                    if (qName.equalsIgnoreCase("REGION_NAME")) {
                        region = true;
                    }
                    if (qName.equalsIgnoreCase("CITY_NAME")) {
                        city = true;
                    }
                    if (qName.equalsIgnoreCase("CITY_REGION_NAME")) {
                        city_region = true;
                    }
                    if (qName.equalsIgnoreCase("STREET_NAME")) {
                        street = true;
                    }
                }

                // Метод вызывается когда SAXParser "натыкается" на конец тэга
                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("OBL_NAME")) {
                        obl = false;
                    }
                    if (qName.equalsIgnoreCase("REGION_NAME")) {
                        region = false;
                    }
                    if (qName.equalsIgnoreCase("CITY_NAME")) {
                        city = false;
                    }
                    if (qName.equalsIgnoreCase("CITY_REGION_NAME")) {
                        city_region = false;
                    }
                    if (qName.equalsIgnoreCase("STREET_NAME")) {
                        street = false;
                    }
                    if (qName.equalsIgnoreCase("RECORD")){
                        //запрос к record
                        if(oblId!=null||regionId!=null||cityId!=null||cityRegionId!=null||streetId!=null){
                            if(databaseHandler.checkRecord(oblId, regionId, cityId, cityRegionId, streetId)){
                                databaseHandler.record(oblId, regionId, cityId, cityRegionId, streetId);
                                System.out.println("Отправлен запрос RECORD " + ++COUNTER);
                            }
                        }

                        oblId = null;
                        regionId = null;
                        cityId = null;
                        cityRegionId = null;
                        streetId = null;
                    }
                }


                // Метод вызывается когда SAXParser считывает текст между тэгами
                @Override
                public void characters(char ch[], int start, int length) throws SAXException {
                    if (obl) {
                        String oblName = new String(ch, start, length);
                        if (oblName.trim().length()>0) {
                            if (databaseHandler.check(Const.OBL_TABLE, oblName, Const.OBL_ID)) {
                                databaseHandler.insert(Const.OBL_TABLE, Const.OBL_VALUE, oblName);
                            }
                        }

                        oblId = databaseHandler.searchId(Const.OBL_TABLE, oblName, Const.OBL_ID);
                    }
                    if (region) {
                        String regionName = new String(ch, start, length);
                        if (regionName.trim().length()>0) {
                            if (databaseHandler.check(Const.REGION_TABLE, regionName, Const.REGION_ID)) {
                                databaseHandler.insert(Const.REGION_TABLE, Const.REGION_VALUE, regionName);
                            }
                        }
                        regionId = databaseHandler.searchId(Const.REGION_TABLE, regionName, Const.REGION_ID);
                    }
                    if (city) {
                        String cityName = new String(ch, start, length);
                        if (cityName.trim().length()>0) {
                            if (databaseHandler.check(Const.CITY_TABLE, cityName, Const.CITYS_ID)) {
                                databaseHandler.insert(Const.CITY_TABLE, Const.CITYS_VALUE, cityName);
                            }
                        }
                        cityId = databaseHandler.searchId(Const.CITY_TABLE, cityName, Const.CITYS_ID);
                    }
                    if (city_region) {
                        String cityRegionName = new String(ch, start, length);
                        if (cityRegionName.trim().length()>0) {
                            if (databaseHandler.check(Const.CITY_REGION_TABLE, cityRegionName, Const.CITY_REGIONS_ID)) {
                                databaseHandler.insert(Const.CITY_REGION_TABLE, Const.CITY_REGIONS_VALUE, cityRegionName);
                            }
                        }
                        cityRegionId = databaseHandler.searchId(Const.CITY_REGION_TABLE, cityRegionName, Const.CITY_REGIONS_ID);
                    }
                    if (street) {
                        String streetName = new String(ch, start, length);
                        if (streetName.trim().length()>0) {
                            if (databaseHandler.check(Const.STREET_TABLE, streetName, Const.STREET_ID)) {
                                databaseHandler.insert(Const.STREET_TABLE, Const.STREET_VALUE, streetName);
                            }
                        }
                        streetId = databaseHandler.searchId(Const.STREET_TABLE, streetName, Const.STREET_ID);
                    }
                }
            };

            // Стартуем разбор методом parse, которому передаем наследника от DefaultHandler, который будет вызываться в нужные моменты
            saxParser.parse(fileName, handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

