package com.example;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileConvert {
    public static void main(String[] args) {
        System.out.println("HW");
        System.out.println("=========");
        Gson gson = new Gson();


        final String master = readAll("setting.json");
        System.out.println(master);

        System.out.println("=========");
        final Settings settings = gson.fromJson(master,Settings.class);

        System.out.println(settings);
        System.out.println("=========");

    }

    // メソッド定義
    public static String readAll(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String string = reader.readLine();
                while (string != null) {
                    builder.append(string + System.getProperty("line.separator"));
                    string = reader.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
}
