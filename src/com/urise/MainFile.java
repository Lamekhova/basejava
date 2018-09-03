package com.urise;

import java.io.File;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
        File directory = new File("E:\\basejava");
        walkTree(directory, "");
    }

    public static void walkTree(File directory, String indent) {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println(indent + file.getName());
                walkTree(file, indent + " ");
            } else {
                System.out.println(indent + file.getName());
            }
        }
    }
}
