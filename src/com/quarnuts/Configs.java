package com.quarnuts;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Scanner;

public class Configs {

    protected static String dbHost;
    protected static String dbPort;
    protected static String dbUser;
    protected static String dbPass;
    protected static String dbName;

    public static void createInitTXT() {
        try {
            Path file = Paths.get(Const.FOLDER_NAME + "\\" + Const.CONFIG_NAME);
            Files.createFile(file);

            //Files.write(file, Collections.singleton(""), StandardCharsets.UTF_8);
        }
        catch (FileAlreadyExistsException e){

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void initConfig() {
        createInitTXT();

        System.out.println("Enter the database configuration at path C:\\Streets Info\\config.txt");
        System.out.print("Press enter to continue...");
        Scanner in = new Scanner(System.in);
        in.nextLine();

        try (Scanner s = new Scanner(new File(Const.FOLDER_NAME + "\\" + Const.CONFIG_NAME))){
            dbHost = s.next();
            dbPort = s.next();
            dbUser = s.next();
            dbPass = s.next();
            dbName = s.next();
        }
        catch (Exception e){
            System.out.println("Error! \nCheck your database configuration");
            initConfig();
        }

        /*System.out.println(dbHost);
        System.out.println(dbPort);
        System.out.println(dbUser);
        System.out.println(dbPass);
        System.out.println(dbName);*/
    }

}

