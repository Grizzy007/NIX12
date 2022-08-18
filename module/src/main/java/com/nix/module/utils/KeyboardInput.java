package com.nix.module.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KeyboardInput {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private KeyboardInput() {
    }

    public static String read() throws IOException {
        return reader.readLine();
    }
}
