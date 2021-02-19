package com.quarnuts;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileManipulation {

    public static void createFolder() {
        File folder = new File(Const.FOLDER_NAME);
        folder.mkdir();
        System.out.println("Создана папка C:\\Streets Info");
    }

    public static void downloadZipFile() {
        String saveTo = Const.FOLDER_NAME + "\\";
        try {
            URL url = new URL(Const.DOWNLOAD_LINK);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            FileOutputStream out = new FileOutputStream(saveTo + Const.ZIP_NAME);
            byte[] b = new byte[1024];
            int count;
            while ((count = in.read(b)) >= 0) {
                out.write(b, 0, count);
            }
            out.flush();
            out.close();
            in.close();
            System.out.println("Скачан ZIP архив");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unZipFile() {
        final int BUFFER_SIZE = 1024;

        byte[] buffer = new byte[BUFFER_SIZE];  // Coздaem kaтaлoг, kyдa бyдyт pacпakoвaны фaйлы

        final File dstDir = new File(Const.FOLDER_NAME);

        if (!dstDir.exists()) {
            dstDir.mkdir();
        }

        try {
            final ZipInputStream zis = new ZipInputStream(
                    new FileInputStream(Const.FOLDER_NAME + "\\" + Const.ZIP_NAME));
            ZipEntry ze = zis.getNextEntry();
            String nextFileName;
            while (ze != null) {
                nextFileName = ze.getName();
                File nextFile = new File(Const.FOLDER_NAME + File.separator + nextFileName);
                System.out.println("Распакован: " + nextFile.getAbsolutePath());

                if (ze.isDirectory()) {
                    nextFile.mkdir();
                } else {
                    new File(nextFile.getParent()).mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(nextFile)) {
                        int length;
                        while ((length = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void deleteZipFile() {
        File file  = new File(Const.FOLDER_NAME + "\\" + Const.ZIP_NAME);
        if(file.delete()){
            System.out.println("Удален ZIP архив");
        }else System.out.println("ZIP Файла не обнаружено");
    }

    public String readFile(){



        return null;
    }

}
