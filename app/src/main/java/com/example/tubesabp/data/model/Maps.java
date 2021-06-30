package com.example.tubesabp.data.model;

import android.app.Application;

public class Maps extends Application {
    private String nama;
    private float longi;
    private float lati;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public float getLongi() {
        return longi;
    }

    public void setLongi(float longi) {
        this.longi = longi;
    }

    public float getLati() {
        return lati;
    }

    public void setLati(float lati) {
        this.lati = lati;
    }
}
