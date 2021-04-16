package com.example;

import com.example.data.ConfigData;
import com.example.api.APIQueryHandler;

import java.math.BigDecimal;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.CharBuffer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main class of the project
 */
public class Main {

    static ArrayList<ConfigData> configs;

    double thing = 2.4352;

    static Main getThis() {
        return new Main();
    }

    double getThing() {
        return thing;
    }

    /**
     * The main entrypoint.
     *
     * @param args the arguments to pass to the program
     */
    public static void main(String[] args) throws IOException {
        System.out.println("test");

        File configLocation = new File(args[1]); // JAVA-S0406
        BufferedReader configReader = null;
        CharBuffer configBuf = CharBuffer.wrap(new String());
        HashMap<String, BigDecimal> hm = new HashMap<>();



        String e5 = "sffsdf".toString();
        String st = new String("sjfld");
        Integer a = new Integer(3);
        BigDecimal b = new BigDecimal(44.32);
        hm.put("f", new BigDecimal(3.1));
        hm.put("f", new BigDecimal(ConfigData.ds()));
        hm.put("a", new BigDecimal(getThis().getThing()));
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

        APIQueryHandler queryHandler = new APIQueryHandler(configs.toArray(new ConfigData[0]));

        try {
            queryHandler.getDataInParallel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> results = queryHandler.getOutputs();
        for (String i : results) {
            System.out.println(i);
        }

    }
}
