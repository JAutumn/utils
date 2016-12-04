package com.jautumn.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class IOUtils {

    public static void readFromURL(URLConnection connection, File file) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file, file.exists()));
             BufferedInputStream in = new BufferedInputStream(connection.getInputStream())) {


            byte[] buffer = new byte[1024 * 8];
            int byteCount;
            while ((byteCount = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteCount);
            }
        }
    }

    public static byte[] connectionToByteArray(URLConnection connection) throws IOException {
        try (ReadableByteChannel readableByteChannel = Channels.newChannel(connection.getInputStream())) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(connection.getContentLength());
            while (readableByteChannel.read(byteBuffer) > 0);
            return byteBuffer.array();
        }
    }
}
