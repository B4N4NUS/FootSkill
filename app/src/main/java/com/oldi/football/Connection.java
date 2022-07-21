package com.oldi.football;

import static com.oldi.football.ui.stats.StatsFragment.names;

import android.util.Pair;

import com.oldi.football.ui.news.NewsInfo;
import com.oldi.football.ui.schedule.RawSchedule;

import org.json.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Connection {
    private static JSONObject person;

    public static ArrayList<JSONObject> userAchievements = new ArrayList<>();

    public static boolean canConnect = true;

    private static String data;
    private static String schedule;
    private static String achievement;
    private static String news;

    private static final String serverUrl = "https://cdn.lk-ft.ru/footballers";
    private static final String scheduleUrl = "https://cdn.lk-ft.ru/scheduleas";
    private static final String achievementUrl = "https://cdn.lk-ft.ru/players";
    private static final String newsUrl = "https://cdn.lk-ft.ru/posts";

    public static final String imagesUrl = "https://cdn.lk-ft.ru";

    private static final String adminLog = "0451";
    private static final String adminPass = "0451";

    public static boolean isPersonAlive() {
        return person != null;
    }

    public static JSONArray getStats() throws JSONException {
        return person.getJSONArray("Statistics");
    }

    public static String getProfileUrl() {
        try {
            return person.getJSONObject("avatar").getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static NewsInfo getNews() {
        NewsInfo info = new NewsInfo();
        try {
            String header = "", desc = "", url = "", vid = "", autor = "", lastname = "", date = "";
            String[] rawDate;
            JSONArray newsArr = new JSONArray(news);
            for (int i = 0; i < newsArr.length(); i++) {
                header = newsArr.getJSONObject(i).getString("Post_Title");
                desc = newsArr.getJSONObject(i).getString("Post_teaser");
                vid = newsArr.getJSONObject(i).getString("Post_video");
                autor = newsArr.getJSONObject(i).getString("Post_author");
                lastname = newsArr.getJSONObject(i).getString("Post_author_lastname");
                date = newsArr.getJSONObject(i).getString("Post_Date");
                rawDate = date.split("-");
                if (rawDate.length == 3) {
                    date = rawDate[0] + "." + rawDate[1] + "." + rawDate[2];
                }
                JSONArray arr = newsArr.getJSONObject(i).
                        getJSONArray("Post_image");
                if (arr.length() != 0) {
                    url = arr.getJSONObject(0).getJSONObject("formats").getJSONObject("large").getString("url");
                    System.out.println("OK--------------------------------------------------------------------------------");
                } else {
                    url = "";
                    System.out.println("EXCEPTION--------------------------------------------------------------------------------");
                }
                info.setInfo(desc, header, url, vid, autor, lastname, date);
            }


//            Random rnd = new Random();
//            for(int i = 0; i <20; i++) {
//                info.setInfo(header,desc,url,vid, autor,lastname, "2021.0" + rnd.nextInt(10) + ".0" + rnd.nextInt(10));
//            }


            info.sort();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return info;
    }

    public static String getName() throws JSONException {
        return person.getString("firstname") + " " + person.getString("lastname");
    }

    public static String getPosition() throws JSONException {
        return person.getString("playerPosition");
    }

    public static String getFoot() throws JSONException {
        return person.getString("lead_leg");
    }

    public static String getTeam() throws JSONException {
        return person.getString("team");
    }

    public static String getLastPay() throws JSONException {
        String[] rawDate = person.getString("date_of_last_pay").split("-");
        return rawDate.length == 3 ? rawDate[2] + "." + rawDate[1] + "." + rawDate[0] : "null";
    }

    public static String getAbonement() throws JSONException {
        return person.getString("variant_of_subscription");
    }

    public static String getAge() throws JSONException {
        String[] rawAge = person.getString("birthday").split("-");
        return rawAge.length == 3 ? rawAge[2] + "." + rawAge[1] + "." + rawAge[0] + " " : "null";
    }

    public static String[] getYears() {
        Set<String> years = new HashSet<>();
        try {
            JSONArray peoArr = new JSONArray(data);
            for (int i = 0; i < peoArr.length(); i++) {
                years.add(peoArr.getJSONObject(i).getString("birthday").split("-")[0]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return years.toArray(new String[years.size()]);
    }

    public static int[][] getAverage(String year) {
        int[][] ret = new int[3][names.length];
        try {
            JSONArray peoArr = new JSONArray(data);
            int[] counter = new int[names.length];
            float[] avers = new float[names.length];
            float[] maxes = new float[names.length];
            float[] yours = new float[names.length];
            final float eps = 0.0001f;

            maxes[3] = Float.MAX_VALUE;
            yours[3] = Float.MAX_VALUE;
            JSONArray pers = person.getJSONArray("Statistics");

            for (int i = 0; i < pers.length(); i++) {
                for (int j = 1; j < names.length; j++) {
                    if (((JSONObject) pers.get(i)).getString(names[j]).equals("null")) {
                        continue;
                    }
                    if (names[j].equals("Reaction")) {
                        if (yours[j] - Float.parseFloat(((JSONObject) pers.get(i)).getString(names[j])) > eps) {
                            yours[j] = Float.parseFloat(((JSONObject) pers.get(i)).getString(names[j]));
                        }
                    } else {
                        if (yours[j] - Float.parseFloat(((JSONObject) pers.get(i)).getString(names[j])) < eps) {
                            yours[j] = Float.parseFloat(((JSONObject) pers.get(i)).getString(names[j]));
                        }
                    }
                }
            }

            for (int k = 0; k < peoArr.length(); k++) {
                    if (peoArr.getJSONObject(k).getString("birthday").split("-")[0].equals(year)) {
                        JSONArray raw = peoArr.getJSONObject(k).getJSONArray("Statistics");

                        for (int i = 0; i < raw.length(); i++) {
                            for (int j = 1; j < names.length; j++) {
                                if (((JSONObject) raw.get(i)).getString(names[j]).equals("null") || ((JSONObject) raw.get(i)).getString(names[j]).equals("0.00")) {
                                    continue;
                                }
                                counter[j]++;
                                avers[j] += Float.parseFloat(((JSONObject) raw.get(i)).getString(names[j]));

                                if (names[j].equals("Reaction")) {
                                    if (maxes[j] - Float.parseFloat(((JSONObject) raw.get(i)).getString(names[j])) > eps) {
                                        System.out.println("old " + maxes[j]);
                                        maxes[j] = Float.parseFloat(((JSONObject) raw.get(i)).getString(names[j]));
                                        System.out.println("new " + maxes[j]);
                                    }
                                } else {
                                    if (maxes[j] - Float.parseFloat(((JSONObject) raw.get(i)).getString(names[j])) < eps) {
                                        System.out.println("old " + maxes[j]);
                                        maxes[j] = Float.parseFloat(((JSONObject) raw.get(i)).getString(names[j]));
                                        System.out.println("new " + maxes[j]);
                                    }
                                }
                            }
                    }
                }
            }
            System.out.println("Entry counter " + counter);

            for (int i = 0; i < names.length; i++) {
                if (yours[i] == Float.MAX_VALUE) {
                    yours[i] = 0;
                }
                if (maxes[i] == Float.MAX_VALUE) {
                    maxes[i] = 0;
                }
                ret[1][i] = (int) Math.round(avers[i] * 1.0 / counter[i]);
                ret[0][i] = (int) Math.round(yours[i]);
                ret[2][i] = (int) Math.round(maxes[i]);
            }

            System.out.println("\nNames:");
            for (int i = 0; i < names.length; i++) {
                System.out.print(names[i] + " ");
            }
            System.out.println("\navers:");
            for (int i = 0; i < names.length; i++) {
                System.out.print(avers[i] + " ");
            }
            System.out.println("\nyour:");
            for (int i = 0; i < names.length; i++) {
                System.out.print(yours[i] + " ");
            }
            System.out.println("\nmaxes:");
            for (int i = 0; i < names.length; i++) {
                System.out.print(maxes[i] + " ");
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return ret;
    }

    /**
     * Получение данных для расписания.
     *
     * @return - данные для построения расписания.
     */
    public static ArrayList<RawSchedule> GetSchedule() {
        ArrayList<RawSchedule> pl = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(schedule);
            String[] names = new String[array.length()];

            RawSchedule raw;

            for (int i = 0; i < array.length(); i++) {
                raw = new RawSchedule();

                JSONObject object = array.getJSONObject(i);
                names[i] = object.getString("Loation");

                if (!(object.getString("MondayStart").equals("null") || object.getString("MondayStart").equals(""))) {
                    raw.date.add("Понедельник");
                    raw.time.add(object.getString("MondayStart"));
                    raw.act.add(object.getString("MondayArtema"));
                }
                if (!(object.getString("TuesdayStart").equals("null") || object.getString("TuesdayStart").equals(""))) {
                    raw.date.add("Вторник");
                    raw.time.add(object.getString("TuesdayStart"));
                    raw.act.add(object.getString("TuesdayArtema"));
                }
                if (!(object.getString("WednesdayStart").equals("null") || object.getString("WednesdayStart").equals(""))) {
                    raw.date.add("Среда");
                    raw.time.add(object.getString("WednesdayStart"));
                    raw.act.add(object.getString("WednesdayArtema"));
                }
                if (!(object.getString("ThursdayStart").equals("null") || object.getString("ThursdayStart").equals(""))) {
                    raw.date.add("Четверг");
                    raw.time.add(object.getString("ThursdayStart"));
                    raw.act.add(object.getString("ThursdayArtema"));
                }
                if (!(object.getString("FridayStart").equals("null") || object.getString("FridayStart").equals(""))) {
                    raw.date.add("Пятница");
                    raw.time.add(object.getString("FridayStart"));
                    raw.act.add(object.getString("FridayArtema"));
                }
                if (!(object.getString("SaturdayStart").equals("null") || object.getString("SaturdayStart").equals(""))) {
                    raw.date.add("Суббота");
                    raw.time.add(object.getString("SaturdayStart"));
                    raw.act.add(object.getString("SaturdayArtema"));
                }
                if (!(object.getString("SundayStart").equals("null") || object.getString("SundayStart").equals(""))) {
                    raw.date.add("Воскресенье");
                    raw.time.add(object.getString("SundayStart"));
                    raw.act.add(object.getString("SundayArtema"));
                }
                if (raw.date.size() == 0) {
                    continue;
                }
                raw.name = names[i];

                pl.add(raw);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pl;
    }

    /**
     * Получение количества тренировок.
     *
     * @return - количество тренировок.
     */
    public static int getCountOfTraining() {
        if (person != null) {
            try {
                return Integer.parseInt(person.getString("count_of_training"));
            } catch (JSONException e) {
                return 0;
            }
        }
        return 0;
    }


    /**
     * Получение количества тренировок.
     *
     * @return - количество тренировок.
     */
    public static int getCountOfMinusPoints() {
        if (person != null) {
            try {
                return Integer.parseInt(person.getString("count_of_minus_points"));
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

    /**
     * Получение количества лагерей.
     *
     * @return - количество лагерей.
     */
    public static int getCountOfCamps() {
        if (person != null) {
            try {
                return Integer.parseInt(person.getString("count_of_camps"));
            } catch (JSONException e) {
                return 0;
            }
        }
        return 0;
    }

    /**
     * Получение данных с сервера.
     *
     * @param act - основная активность. (Для обработки данных после загрузки)
     */
    public static void getData(MainActivity act) {
        // Обнуление переменных.
        data = "";
        schedule = "";
        achievement = "";

        // Обнуление флага на успешное соединение.
        canConnect = true;

        // Таймер для дебага.
        long startTimer = System.currentTimeMillis();

        // Запуск треда с выкачкой инфы с сервера.
        Thread load = new Thread() {
            @Override
            public void run() {
                try {
                    StringBuilder string = new StringBuilder();


                    System.out.println("----------------------------------------STARTED_CONNECTION_THREAD--------------------------------------------------------------");
                    // Подключение к серверу.
                    URL url = new URL(serverUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept-Encoding", "gzip");
                    connection.connect();

                    // Ответный код сервера.
                    int responseCode = connection.getResponseCode();
                    System.out.println("----------------------------------------GOT_RESPONSE_FROM_PLAYERS_" + ((1.0 * System.currentTimeMillis() - startTimer) / 1000) + "_SECONDS________________________________________________");


                    // Если сервак не захотел отдавать данные.
                    if (responseCode != 200) {
                        throw new RuntimeException("Players: HttpResponseCode: " + responseCode);
                    } else {
                        // Перегоняем инфу с сервера в строку.
                        Scanner scanner = new Scanner(url.openStream());
                        while (scanner.hasNext()) {
                            string.append(scanner.nextLine());
                        }
                        scanner.close();
                    }
                    data = string.toString();
                    string = new StringBuilder();
                    System.out.println("----------------------------------------CONNECTION_TO_PLAYERS_TOOK_" + ((1.0 * System.currentTimeMillis() - startTimer) / 1000) + "_SECONDS________________________________________________");
                    System.out.println("Raw Players: " + data);


                    url = new URL(scheduleUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept-Encoding", "gzip");
                    connection.connect();

                    responseCode = connection.getResponseCode();
                    System.out.println("----------------------------------------GOT_RESPONSE_FROM_SCHEDULE_" + ((1.0 * System.currentTimeMillis() - startTimer) / 1000) + "_SECONDS________________________________________________");

                    if (responseCode != 200) {
                        throw new RuntimeException("Schedule: HttpResponseCode: " + responseCode);
                    } else {
                        Scanner scanner = new Scanner(url.openStream());
                        while (scanner.hasNext()) {
                            string.append(scanner.nextLine());
                        }
                        scanner.close();
                    }
                    schedule = string.toString();
                    string = new StringBuilder();
                    System.out.println("----------------------------------------CONNECTION_TO_SCHEDULE_TOOK_" + ((1.0 * System.currentTimeMillis() - startTimer) / 1000) + "_SECONDS________________________________________________");
                    System.out.println("Raw Schedule: " + schedule);


                    url = new URL(achievementUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept-Encoding", "gzip");
                    connection.connect();

                    responseCode = connection.getResponseCode();
                    System.out.println("----------------------------------------GOT_RESPONSE_FROM_ACHIEVEMENTS_" + ((1.0 * System.currentTimeMillis() - startTimer) / 1000) + "_SECONDS________________________________________________");

                    if (responseCode != 200) {
                        throw new RuntimeException("Achievements: HttpResponseCode: " + responseCode);
                    } else {
                        Scanner scanner = new Scanner(url.openStream());
                        while (scanner.hasNext()) {
                            string.append(scanner.nextLine());
                        }
                        scanner.close();
                    }
                    achievement = string.toString();
                    string = new StringBuilder();
                    System.out.println("----------------------------------------CONNECTION_TO_ACHIEVEMENTS_TOOK_" + ((1.0 * System.currentTimeMillis() - startTimer) / 1000) + "_SECONDS________________________________________________");
                    System.out.println("Raw Achievements: " + achievement);


                    url = new URL(newsUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept-Encoding", "gzip");
                    connection.connect();

                    responseCode = connection.getResponseCode();
                    System.out.println("----------------------------------------GOT_RESPONSE_FROM_NEWS" + ((1.0 * System.currentTimeMillis() - startTimer) / 1000) + "_SECONDS________________________________________________");

                    if (responseCode != 200) {
                        throw new RuntimeException("News: HttpResponseCode: " + responseCode);
                    } else {
                        Scanner scanner = new Scanner(url.openStream());
                        while (scanner.hasNext()) {
                            string.append(scanner.nextLine());
                        }
                        scanner.close();
                    }
                    news = string.toString();
                    System.out.println("----------------------------------------CONNECTION_TO_NEWS_TOOK_" + ((1.0 * System.currentTimeMillis() - startTimer) / 1000) + "_SECONDS________________________________________________");
                    System.out.println("Raw News: " + news);
                } catch (Exception exception) {
                    canConnect = false;
                    System.out.println("-----------------------------------------CONNECTION_FAILED---------------------------------------------------");
                    exception.printStackTrace();
                }


                System.out.println("------------------------------------------ENDED_CONNECTION_THREAD--------------------------------------------------------------");

                // Продолжение работы главного потока.
                act.Load();
            }
        };

        try {
            load.start();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Получение информации о пользователе.
     *
     * @param login - логин, забитый пользователем.
     * @param pass  - пароль, забитый пользователем.
     * @return - является ли пользователь валидным.
     */
    public static Pair<Boolean, JSONObject> findUser(String login, String pass, MainActivity main) {
        // Обнуление переменных.
        boolean giveAccess = false;
        person = null;
        userAchievements.clear();

        // Если пользователь использует логин разработчика.
        if (Objects.equals(login, adminLog) && Objects.equals(pass, adminPass)) {
            try {
                JSONArray peoArr = new JSONArray(data);

                // Проходимся по всем пользователям.
                JSONObject object = peoArr.getJSONObject(0);

                // Если логин и пароль подходят.
                giveAccess = true;
                person = peoArr.getJSONObject(0);
                System.out.println("___________________________________________________USER_FOUND________________________________________________________");

                // Проходимся по всем достижениям.
                JSONArray achArr = new JSONArray(achievement);
                for (int j = 0; j < achArr.length(); j++) {
                    JSONObject obj = achArr.getJSONObject(j);
                    if (obj.getString("fullname").equals("null")) {
                        continue;
                    }

                    // Проверяем ачивку на вшивость.
                    if (Objects.equals(obj.getString("fullname"), object.getString("lastname") + " " + object.getString("firstname") + " " + object.getString("id") + " ")) {
                        userAchievements.add(achArr.getJSONObject(j));
                        System.out.println("___________________________________________________ACHIEVEMENT_FOUND________________________________________________________");
                    }
                }
                main.login.setText(object.getString("f_email"));
                main.pass.setText(object.getString("f_password"));
            } catch (Exception ex) {
                return new Pair(true, null);
            }
        }

        //  Если с сервака ничего не подсосало.
        if (Objects.equals(data, "")) {
            return null;
        }

        try {
            // Если пароль и логин могут быть в системе.
            if (data.contains(login) && data.contains(pass)) {
                JSONArray peoArr = new JSONArray(data);

                // Проходимся по всем пользователям.
                for (int i = 0; i < peoArr.length(); i++) {
                    JSONObject object = peoArr.getJSONObject(i);

                    // Если логин и пароль подходят.
                    if (Objects.equals(login, object.getString("f_email")) && Objects.equals(pass, object.getString("f_password"))) {
                        giveAccess = true;
                        person = peoArr.getJSONObject(i);
                        System.out.println("___________________________________________________USER_FOUND________________________________________________________");

                        // Проходимся по всем достижениям.
                        JSONArray achArr = new JSONArray(achievement);
                        for (int j = 0; j < achArr.length(); j++) {
                            JSONObject obj = achArr.getJSONObject(j);
                            if (obj.getString("fullname").equals("null")) {
                                continue;
                            }

                            // Проверяем ачивку на вшивость.
                            if (Objects.equals(obj.getString("fullname"), object.getString("lastname") + " " + object.getString("firstname") + " " + object.getString("id") + " ")) {
                                userAchievements.add(achArr.getJSONObject(j));
                                System.out.println("___________________________________________________ACHIEVEMENT_FOUND________________________________________________________");
                            }
                        }
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            giveAccess = false;
            ex.printStackTrace();
        }

        return new Pair(giveAccess, person);
    }
}
