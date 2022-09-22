package com.threads.factory;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Robots {
    private static final AtomicInteger GAS = new AtomicInteger();
    private static AtomicInteger producedDetails = new AtomicInteger(0);
    private static final Random random = new Random();

    public void startSession(){
        firstRobot();
        secondAndThirdrobots();
        fourthRobot();
        fifthRobot();
    }

    @SneakyThrows
    private void firstRobot() {
        final Runnable robotOne = () -> {
            System.out.println("Gas produced robot starts work");
            while (true) {
                int produced = random.nextInt(500) + 500;
                GAS.addAndGet(produced);
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Robot has produced " + produced + " gallons of gas.");
            }
        };
        final Thread firstRobot = new Thread(robotOne);
        firstRobot.setDaemon(true);
        firstRobot.start();
    }

    @SneakyThrows
    private void secondAndThirdrobots() {
        final Runnable secondAndThirdRobotsRunnable = () -> {
            System.out.println("Constructors robots start work");
            while (producedDetails.get() < 100) {
                producedDetails.set(producedDetails.get() + random.nextInt(10) + 10);
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Constructors robots finished");
        };
        final Thread secondRobot = new Thread(secondAndThirdRobotsRunnable);
        final Thread thirdRobot = new Thread(secondAndThirdRobotsRunnable);
        thirdRobot.setDaemon(true);
        secondRobot.setDaemon(true);
        thirdRobot.start();
        secondRobot.start();
    }

    @SneakyThrows
    private void fourthRobot() {
        final Runnable fourthRobotRunnable = () -> {
            System.out.println("Programmer robot starts work");
            AtomicInteger programingDetailsProgress = new AtomicInteger(0);
            while (programingDetailsProgress.get() < 100) {
                programingDetailsProgress.set(programingDetailsProgress.get() + random.nextInt(10) + 25);
                int broke = random.nextInt(100);
                if (broke <= 30) {
                    System.out.println("Programmer robot broke the chip");
                    programingDetailsProgress.set(0);
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Programmer robot finished");
        };
        final Thread fourthRobot = new Thread(fourthRobotRunnable);
        fourthRobot.setDaemon(true);
        fourthRobot.start();
    }

    @SneakyThrows
    private void fifthRobot() {
        final Runnable fifthRobotRunnable = () -> {
            System.out.println("Forming robot start work");
            AtomicInteger workProgress = new AtomicInteger(0);
            while (workProgress.get() < 100) {
                int requiredGas = random.nextInt(400) + 350;
                if (GAS.get() < requiredGas) {
                    System.out.println("Gas is not enough, waiting for gas produced robot");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    GAS.set(GAS.get() - requiredGas);
                    workProgress.addAndGet(10);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            System.out.println("Forming robot finished");
        };
        Thread fifthRobot = new Thread(fifthRobotRunnable);
        fifthRobot.start();
        fifthRobot.join();
    }

}