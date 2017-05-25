package com.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miyaki on 2017/05/25.
 */

public class DirectoryReader {
    /**
     * ディレクトリを再帰的に読む
     **/
    public List<File> readFolder(File dir, List<File> list) {

        System.out.println("readFolder dir:" + dir.getAbsolutePath());
        File[] files = dir.listFiles();

        if (files == null) {
            System.out.println("readFolder files: null");
            return list;
        }
        System.out.println("readFolder files: " + files.length);
        if (list == null) {
            list = new ArrayList<>();
        }

        for (File file : files) {
            System.out.println("readFolder file: " + file.getAbsolutePath());
            if (!file.exists()) {
                continue;
            } else if (file.isDirectory()) {
                readFolder(file, list);
            } else if (file.isFile()) {
                list.add(file);
            }
        }
        System.out.println("-------");
        return list;
    }

    public List<File> readFolder(File dir) {
        return readFolder(dir, null);
    }
}
