package com.example;

import com.google.gson.Gson;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import rx.Observable;

public class FileConvert {
    public static void main(String[] args) {

        final String masterText = readAll("app/settings/settings.json");
        final String templateText = readAll("settings-template/strings-gen.xml.template");

        final Settings masterSettings = new Gson().fromJson(masterText, Settings.class);

        final Map<String, String> masterSettingsMap = Observable.from(masterSettings.getSettings())
                .toMap(Setting::getKey, Setting::getValue)
                .toBlocking()
                .single();

        final Template template = Mustache.compiler().compile(templateText);

        writeAll(new File("app/src/main/res/values/strings-gen.xml"), template.execute(masterSettingsMap));

        System.out.println("FileConvert Finish");
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

    public static void writeAll(final File file, final String text) {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(text);
            pw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
