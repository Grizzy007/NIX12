package com.nix.lesson10;

import com.nix.lesson10.annotations.Autowired;
import com.nix.lesson10.annotations.AutowiredConfig;
import com.nix.lesson10.annotations.SingletonConfig;
import com.nix.lesson10.ui.UserInterface;


public class Main {
    public static void main(String[] args) {

//        UserInterface ui = new UserInterface();
//        ui.start();
        AutowiredConfig ac = new AutowiredConfig();
        SingletonConfig sc = new SingletonConfig();
        ac.configure();
        sc.configure();

    }

}

