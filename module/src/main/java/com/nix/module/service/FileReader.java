package com.nix.module.service;

import com.nix.module.exceptions.IncorrectStringException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public interface FileReader {
    List<String[]> readFile(BufferedReader reader) throws IOException, IncorrectStringException;
}
