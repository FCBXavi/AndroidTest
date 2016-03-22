package com.example.quxiaopeng.retrofittest;

/**
 * Created by quxiaopeng on 16/3/22.
 */
public class MovieModel {
    public int id;
    public String alt;
    public int year;
    public String subtype;
    public String original_title;
    public int collect_count;
    public String title;

    @Override
    public String toString() {
        return "title: " + title + " year: " + year + " subtype: " + subtype + " original_title: " + original_title;
    }
}
