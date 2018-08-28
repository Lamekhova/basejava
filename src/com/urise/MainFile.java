package com.urise;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        // Обход всех файлов проекта
        File file = new File("C:\\Users\\alamehova\\basejava");
        walkFileTree(file);
    }

    private static void walkFileTree(File file) {
        File[] folders = file.listFiles();
        for (File element : folders) {
            if (element.isDirectory()) {
                walkFileTree(element);
            } else {
                System.out.println("\t" + element.getName());
            }
        }
    }
}
