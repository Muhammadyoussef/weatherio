package me.muhammadyoussef.weatherio.utils;

import java.io.File;

import androidx.annotation.NonNull;

public final class FileUtils {

    private static final int NOT_FOUND = -1;
    private static final char EXTENSION_SEPARATOR = '.';
    private static final char UNIX_SEPARATOR = '/';

    private FileUtils() {
    }

    public static String getFileName(@NonNull File file) {
        String fileName = file.getName();
        int indexOfExtension = getIndexOfExtension(fileName);
        return fileName.substring(0, indexOfExtension);
    }

    private static int getIndexOfExtension(String filename) {
        int extensionPos = filename.lastIndexOf(EXTENSION_SEPARATOR);
        int lastSeparator = filename.lastIndexOf(UNIX_SEPARATOR);
        return lastSeparator > extensionPos ? NOT_FOUND : extensionPos;
    }
}