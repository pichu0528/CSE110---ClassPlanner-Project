package com.example.annchih.classplanner;

import android.util.Log;

import com.parse.ParseObject;

/**
 * Created by AnnChih on 11/22/15.
 */
public class ClassData{
    private String class_id;
    private String class_name;
    private boolean taken;
    private String des;
    public String get_class_id_2() {

        return class_id;
    }

    public void set_class_id_2(String class_id) {
        this.class_id = class_id;
    }

    public String get_class_name_2() {
        return class_name;
    }

    public void set_class_name_2(String class_name) {
        this.class_name = class_name;
    }
    public boolean get_taken()
    {
        return taken;
    }
    public void set_taken(boolean taken){
        this.taken = taken;
    }
    public String get_description() {

        return des;
    }
    public void set_description(String des) {
        this.des = des;
    }
}
