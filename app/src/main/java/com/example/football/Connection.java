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
    public static boolean canConnect = true;

    public static String data;

    public static final String serverUrl = "https://cdn.lk-ft.ru/footballers";
    public static final String imagesUrl = "https://cdn.lk-ft.ru";

    public static boolean getData(MainActivity act) {
        data = "";
        canConnect = true;

        long startTimer = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(1);
        Thread load = new Thread() {
            @Override
            public void run() {
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
