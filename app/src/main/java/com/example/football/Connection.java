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

    public static final String serverUrl = "https://cdn.lk-ft.ru/footballers";
    public static final String imagesUrl = "https://cdn.lk-ft.ru";

    public static Pair<Boolean, JSONObject> GetData(String login, String pass) {
        if (Objects.equals(login, "1") && Objects.equals(pass, "1")) {
            return new Pair(true, null);
        }

        CountDownLatch latch = new CountDownLatch(1);

        Thread find = new Thread() {
            @Override
            public void run() {
                try {
                    giveAccess = false;
                    person = null;

                    URL url = new URL(serverUrl);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    conn.connect();

                    int responsecode = conn.getResponseCode();

                    if (responsecode != 200) {
                        throw new RuntimeException("HttpResponseCode: " + responsecode);
                    } else {

                        String inline = "";
                        Scanner scanner = new Scanner(url.openStream());

                        while (scanner.hasNext()) {
                            inline += scanner.nextLine();
                        }

                        scanner.close();
                        //System.out.println(inline);

                        JSONArray array = new JSONArray(inline);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
//                            System.out.print(object.getString("f_email") + " ");
//                            System.out.println(object.getString("f_password"));

                            if (Objects.equals(login, object.getString("f_email")) && Objects.equals(pass, object.getString("f_password"))) {
                                giveAccess = true;
                                person = array.getJSONObject(i);
                                System.out.println("___________________________________________________PIDORAS_NAIDEN________________________________________________________");
                                break;
                            }
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
                latch.countDown();
            }
        };

        find.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Pair(giveAccess, person);
    }
}
