package com.example.football;

import android.util.Pair;

import org.json.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Connection {
    private static boolean giveAccess;
    private static JSONObject person;
    public static String data;

    public static final String serverUrl = "https://cdn.lk-ft.ru/footballers";
    public static final String imagesUrl = "https://cdn.lk-ft.ru";

    public static void getData() {
        data = "";
        CountDownLatch latch = new CountDownLatch(1);
        Thread load = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(serverUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();

                    int responsecode = conn.getResponseCode();

                    if (responsecode != 200) {
                        throw new RuntimeException("HttpResponseCode: " + responsecode);
                    } else {
                        Scanner scanner = new Scanner(url.openStream());
                        while (scanner.hasNext()) {
                            data += scanner.nextLine();
                        }
                        scanner.close();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                latch.countDown();
                System.out.println("--------------------------------------------------------THREAD DIED");
            }
        };
        try{
            load.start();
            latch.await();
        } catch (Exception exception) {}

    }

    public static Pair<Boolean, JSONObject> findUser(String login, String pass) {
        //CountDownLatch latch = new CountDownLatch(1);

        giveAccess = false;
        person = null;

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
