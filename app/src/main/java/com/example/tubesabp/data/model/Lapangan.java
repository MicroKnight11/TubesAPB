package com.example.tubesabp.data.model;

public class Lapangan {

    private String nama, alamat, gambar;

    public Lapangan(){}

    public Lapangan(String nama, String alamat, String gambar){
        this.nama = nama;
        this.alamat = alamat;
        this.gambar = gambar;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
