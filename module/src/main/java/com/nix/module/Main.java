package com.nix.module;

import com.nix.module.utils.StreamRealisation;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        StreamRealisation realisation = new StreamRealisation();
        realisation.doTask(1500);
    }
}
