package com.example;

import com.example.data.ConfigData;
import com.example.api.APIQueryHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * lorem ipsum sit dor tass
 */
public class Main {

    /**
     * Documented function
     *
     * @param args adb, ddhdhfwdfe
     */
    public static void main(String[] args) throws IOException {
        System.out.println("test");

        File configLocation = new File(args[1]); // JAVA-S0406
        BufferedReader configReader = null;
        CharBuffer configBuf = CharBuffer.wrap(new String());

        try {
            configReader = java.nio.file.Files.newBufferedReader(configLocation.toPath()); // JAVA-S0268
            configReader.read(configBuf);
        } catch (Throwable ignored) {
            ignored.printStackTrace();
        }

        configReader.close();
        String config = configBuf.toString();
        ArrayList<ConfigData> configs = new ArrayList<>();

        for (String line : config.lines().collect(Collectors.toList())) {
            String[] data = line.split(" ");
            URL url = null;
            try {
                url = new URL(data[0]);
            } catch (Throwable t) {
                t.printStackTrace();
            }

            List<String> paramStrings = Arrays.asList(data).subList(1, data.length);
            HashMap<String, String> params = new HashMap<>();

            for (String i : paramStrings) {
                String[] vals = i.split(":");
                params.put(vals[0], vals[1]);
            }

            var configElem = new ConfigData();
            configElem.setUrl(url);
            configElem.setParams(params);
            configs.add(configElem);
        }

        APIQueryHandler queryHandler = new APIQueryHandler(configs);

        try {
            queryHandler.getDataInParallel();
        } catch (InterruptedException e) { }

        List<String> results = queryHandler.getOutputs();
        for (String i : results) {
            System.out.println(i);
        }

    }
}
