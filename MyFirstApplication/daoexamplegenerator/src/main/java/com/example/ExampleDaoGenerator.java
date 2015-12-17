package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {
    public static void main(String[] args) {
        Schema schema = new Schema(1, "me.test.greendao");

        addNote(schema);
        try {
            String path = System.getProperty("user.dir") + "/greendaotest/src/main/java-gen";
            new DaoGenerator().generateAll(schema, path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("note");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");
    }
}
