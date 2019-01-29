package com.yuknis.jwebtrap.providers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;

public class RequestLoggerServiceProvider {

    protected static RequestLoggerServiceProvider _instance;

    @Value("${requests.out.path}")
    public String path;

    protected RequestLoggerServiceProvider() {
        //
    }

    public static RequestLoggerServiceProvider getInstance() {
        if (_instance == null) {
            _instance = new RequestLoggerServiceProvider();
        }

        return _instance;
    }

    public void saveRecord(String contents) {
        String fileName = this.generateFileName();
        this.writeToDisk(fileName, contents);
    }

    protected String generateFileName() {

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String fileName = String.format("%s.txt", currentDateTime.format(formatter));

        return fileName;
    }

    protected void writeToDisk(String fileName, String contents) {

        File file = new File(String.format("%s", fileName));
        
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(file)); 
            file.createNewFile();
            out.write(contents);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}