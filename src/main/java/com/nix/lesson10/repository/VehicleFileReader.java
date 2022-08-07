package com.nix.lesson10.repository;

import com.nix.lesson10.model.functionals.FunctionImpl;
import com.nix.lesson10.model.vehicle.Auto;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VehicleFileReader {
    public Auto redaFromFile(String type) {
        String filename;
        Pattern pattern;
        if (type.equals("xml")) {
            filename = "./src/main/resources/auto.xml";
            pattern = Pattern.compile("(\\w+)>(.*)<");
        } else {
            filename = "./src/main/resources/auto.json";
            pattern = Pattern.compile("(\\w+)\":\\s\"(.*)\"");
        }
        File file = new File(filename);
        if (file.isDirectory() || !file.exists()) {
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8))) {
            String stringFromFile = reader.readLine();
            if (stringFromFile == null) {
                throw new IllegalStateException();
            } else {
                return FunctionImpl.toAuto(listToMap(readToList(reader, pattern)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, Object> listToMap(List<String> results) {
        Map<String, Object> map = new HashMap<>();
        int index = 0;
        while (index < results.size()) {
            map.put(results.get(index), results.get(index + 1));
            index += 2;
        }
        return map;
    }

    private List<String> readToList(BufferedReader reader, Pattern pattern) throws IOException {
        Matcher matcher;
        List<String> results = new ArrayList<>();
        String readedString;
        while ((readedString = reader.readLine()) != null) {
            matcher = pattern.matcher(readedString);
            while (matcher.find()) {
                results.add(matcher.group(1));
                results.add(matcher.group(2));
            }
        }
        return results;
    }

}
