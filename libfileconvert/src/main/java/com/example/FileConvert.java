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
import java.util.List;
import java.util.Map;

import rx.Observable;

public class FileConvert {
    public static void main(String[] args) throws IOException {
        System.out.println("FileConvert start");
        // prepare master data
        final String masterText = readAll("app/settings/settings.json");
        System.out.println("FileConvert masterText:" + masterText);
        final Settings masterSettings = new Gson().fromJson(masterText, Settings.class);
        final Map<String, String> masterSettingsMap = Observable.from(masterSettings.getSettings())
                .toMap(Setting::getKey, Setting::getValue)
                .toBlocking()
                .single();

        // read templates
        final File templatesDir = new File("settings-template");
        List<File> files = new DirectoryReader().readFolder(templatesDir);
        for (File file : files) {
            final String filepath = file.getPath();
            System.out.println("Convert:" + filepath);
            final String templateText = readAll(filepath);
            final Template template = Mustache.compiler().compile(templateText);
            File generated = new File(filepath.replace("settings-template/", "").replace(".template", ""));
            writeAll(generated, template.execute(masterSettingsMap));
            System.out.println("Converted:" + generated.getPath());
        }
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
