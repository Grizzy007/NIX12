package com.nix.lesson10.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;

public interface Command {
    void execute(BufferedReader reader) throws IOException, URISyntaxException;
}
