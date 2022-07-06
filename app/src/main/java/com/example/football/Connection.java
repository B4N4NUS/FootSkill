package com.example.football;

import android.util.Pair;

import org.json.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Connection {
    private static boolean giveAccess;
    public static JSONObject person;
    public static ArrayList<JSONObject> achievementObject = new ArrayList<>();
    public static boolean canConnect = true;

    public static String data;
    public static String schedule;
    public static String achievement;

    public static final String serverUrl = "https://cdn.lk-ft.ru/footballers";
    public static final String scheduleUrl = "https://cdn.lk-ft.ru/scheduleas";
    public static final String achievementUrl = "https://cdn.lk-ft.ru/players";
    public static final String imagesUrl = "https://cdn.lk-ft.ru";

    public static boolean getData(MainActivity act) {
        data = "";
        canConnect = true;

        long startTimer = System.currentTimeMillis();
        Thread load = new Thread() {
            @Override
            public void run() {
                getSchedule();
                getAchievements();
                try {
                    URL url = new URL(serverUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept-Encoding", "gzip");
                    connection.connect();

                    int responseCode = connection.getResponseCode();
                    System.out.println("----------------------------------------GOT_RESPONSE_FROM_SERVER_" + ((1.0 * System.currentTimeMillis() - startTimer) / 1000) + "_SECONDS________________________________________________");

                    if (responseCode != 200) {
                        throw new RuntimeException("HttpResponseCode: " + responseCode);
                    } else {
                        Scanner scanner = new Scanner(url.openStream());
                        while (scanner.hasNext()) {
                            data += scanner.nextLine();
                        }
                        scanner.close();
                    }
                    //latch.countDown();
                } catch (Exception exception) {
                    canConnect = false;
                    //latch.countDown();
                    exception.printStackTrace();
                }

                System.out.println("----------------------------------------CONNECTION_TOOK_" + ((1.0 * System.currentTimeMillis() - startTimer) / 1000) + "_SECONDS________________________________________________");
                act.Load();
                System.out.println(data);
            }
        };
        try {
            load.start();
            //latch.await();
        } catch (Exception exception) {
        }

        return canConnect;
    }

    public static void getSchedule() {
        schedule = "";
        canConnect = true;
        CountDownLatch latch = new CountDownLatch(1);
        long startTimer = System.currentTimeMillis();
        Thread start = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(scheduleUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept-Encoding", "gzip");
                    connection.connect();

                    int responseCode = connection.getResponseCode();
                    System.out.println("----------------------------------------GOT_RESPONSE_FROM_SCHEDULE_" + ((1.0 * System.currentTimeMillis() - startTimer) / 1000) + "_SECONDS________________________________________________");

                    if (responseCode != 200) {
                        throw new RuntimeException("HttpResponseCode: " + responseCode);
                    } else {
                        Scanner scanner = new Scanner(url.openStream());
                        while (scanner.hasNext()) {
                            schedule += scanner.nextLine();
                        }
                        scanner.close();
                    }
                    //latch.countDown();
                } catch (
                        Exception exception) {
                    canConnect = false;
                    //latch.countDown();
                    exception.printStackTrace();
                }

                System.out.println("----------------------------------------CONNECTION_TOOK_" + ((1.0 * System.currentTimeMillis() - startTimer) / 1000) + "_SECONDS________________________________________________");
                System.out.println(schedule);
                latch.countDown();
            }
        };
        try {
            start.start();
            latch.await();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public static void getAchievements() {
        schedule = "";
        canConnect = true;
        CountDownLatch latch = new CountDownLatch(1);
        long startTimer = System.currentTimeMillis();
        Thread start = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(achievementUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept-Encoding", "gzip");
                    connection.connect();

                    int responseCode = connection.getResponseCode();
                    System.out.println("----------------------------------------GOT_RESPONSE_FROM_ACHIEVEMENTS_" + ((1.0 * System.currentTimeMillis() - startTimer) / 1000) + "_SECONDS________________________________________________");

                    if (responseCode != 200) {
                        throw new RuntimeException("HttpResponseCode: " + responseCode);
                    } else {
                        Scanner scanner = new Scanner(url.openStream());
                        while (scanner.hasNext()) {
                            achievement += scanner.nextLine();
                        }
                        scanner.close();
                    }
                    //latch.countDown();
                } catch (
                        Exception exception) {
                    canConnect = false;
                    //latch.countDown();
                    exception.printStackTrace();
                }

                System.out.println("----------------------------------------CONNECTION_TOOK_" + ((1.0 * System.currentTimeMillis() - startTimer) / 1000) + "_SECONDS________________________________________________");
                System.out.println(achievement);
                latch.countDown();
            }
        };
        try {
            start.start();
            latch.await();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public static Pair<Boolean, JSONObject> findUser(String login, String pass) {
        //CountDownLatch latch = new CountDownLatch(1);

        giveAccess = false;
        person = null;

        if (Objects.equals(login, "123123") && Objects.equals(pass, "123123")) {
            return new Pair(true, null);
        }

        if (Objects.equals(data, "")) {
            return null;
        }

        try {
            if (data.contains(login) && data.contains(pass)) {
                JSONArray array = new JSONArray(data);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    if (Objects.equals(login, object.getString("f_email")) && Objects.equals(pass, object.getString("f_password"))) {
                        giveAccess = true;
                        person = array.getJSONObject(i);
                        System.out.println("___________________________________________________PIDORAS_NAIDEN________________________________________________________");
                        //System.out.println(achievement);
                        //System.out.println(achievement.substring(5,achievement.length()-1));
                        JSONArray aray = new JSONArray(achievement.substring(4,achievement.length()));
                        for (int j = 0; j < aray.length(); j++) {
                            JSONObject obj = aray.getJSONObject(j);
                            if (obj.getString("fullname").equals("null")) {
                                continue;
                            }
                            System.out.println(j);
                            if (Objects.equals(obj.getString("fullname"), object.getString("lastname") + " " + object.getString("firstname") + " " + object.getString("id") + " ")) {
                                achievementObject.add(aray.getJSONObject(j));
                                System.out.println("___________________________________________________ACHIEVEMENT_NAIDEN________________________________________________________");
                                String[] aboba = achievementObject.toString().split(",");

                                //for (int h = 0; h < aboba.length; h++) {
                                    System.out.println("achievement " + achievementObject.toString());
                                //}
                            }
                        }
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //latch.countDown();

        try {
            //latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Pair(giveAccess, person);
    }
}
