package com.nix.lesson10.repository.collection;

import com.nix.lesson10.model.functionals.FunctionImpl;
import com.nix.lesson10.model.vehicle.Auto;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VehicleFileReader {
    private final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    public Auto redaFromFile(String type) throws URISyntaxException {
        File file;
        Pattern pattern;
        if (type.equals("xml")) {
            file = Paths.get(Objects.requireNonNull(classLoader.getResource("auto.xml")).toURI()).toFile();
            pattern = Pattern.compile("(\\w+)>(.*)<");
        } else {
            file = Paths.get(Objects.requireNonNull(classLoader.getResource("auto.json")).toURI()).toFile();
            pattern = Pattern.compile("(\\w+)\":\\s\"(.*)\"");
        }
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
