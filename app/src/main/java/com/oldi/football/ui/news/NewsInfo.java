package com.oldi.football.ui.news;

import com.oldi.football.Connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsInfo {
    ArrayList<String> descriptions;
    ArrayList<String> headers;
    ArrayList<String> bitmaps;
    ArrayList<String> vids;
    ArrayList<String> autors;
    ArrayList<String> lastnames;
    ArrayList<String> dates;

    public NewsInfo() {
        descriptions = new ArrayList<>();
        headers = new ArrayList<>();
        bitmaps = new ArrayList<>();
        vids = new ArrayList<>();
        autors = new ArrayList<>();
        lastnames = new ArrayList<>();
        dates = new ArrayList<>();
    }

    public void sort() {
        concurrentSort(dates, headers, descriptions, bitmaps, vids, autors, lastnames, dates);
    }
    public static <T extends Comparable<T>> void concurrentSort(
            final List<String> key, List<?>... lists) {
        // Create a List of indices
        List<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < key.size(); i++)
            indices.add(i);

        // Sort the indices list based on the key
        Collections.sort(indices, new Comparator<Integer>() {
            @Override
            public int compare(Integer i, Integer j) {
                Integer a = Integer.parseInt(key.get(i).replace(".",""));
                Integer b = Integer.parseInt(key.get(j).replace(".",""));
                return a.compareTo(b);
            }
        });
        for(int i = 0; i < indices.size(); i++) {
            System.out.println(indices.get(i));
        }

        // Create a mapping that allows sorting of the List by N swaps.
        // Only swaps can be used since we do not know the type of the lists
        Map<Integer, Integer> swapMap = new HashMap<Integer, Integer>(indices.size());
        List<Integer> swapFrom = new ArrayList<Integer>(indices.size()),
                swapTo = new ArrayList<Integer>(indices.size());
        for (int i = 0; i < key.size(); i++) {
            int k = indices.get(i);
            while (i != k && swapMap.containsKey(k))
                k = swapMap.get(k);

            swapFrom.add(i);
            swapTo.add(k);
            swapMap.put(i, k);
        }
        // use the swap order to sort each list by swapping elements
        for(List<?> list : lists)
            for(int i = 0; i < list.size(); i++)
                Collections.swap(list, swapFrom.get(i), swapTo.get(i));
    }

    public void setInfo(String desc, String header, String url, String vid, String autor, String lastname, String date) {
        descriptions.add(desc);
        headers.add(header);
        vids.add(vid);
        autors.add(autor);
        lastnames.add(lastname);
        dates.add(date);
        bitmaps.add(Connection.imagesUrl + url);
    }

    public int getLength() {
        return descriptions.size();
    }
}
