package com.urise;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class MainFile {
    public static void main(String[] args) throws IOException {
        // Вывод каталогов с отступом
        Path root = Paths.get("C:\\Users\\alamehova\\basejava");
        Files.walkFileTree(root, new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                int count = dir.getNameCount() - root.getNameCount() + 1;
                count += dir.getFileName().toString().length();

                String text = String.format("%" + count + "s", dir.getFileName());
                text = text.replaceAll("[\\s]", " ");
                System.out.println(text);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
