package com.farport.android.eiskominfo;

import java.io.Serializable;

public class Kategori implements Serializable{
    int id_kategori, status;
    String nama_kategori;

    public Kategori(int id_kategori, int status, String nama_kategori) {
        this.id_kategori = id_kategori;
        this.status = status;
        this.nama_kategori = nama_kategori;
    }

    public int getId_kategori() {
        return id_kategori;
    }

    public int getStatus() {
        return status;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }
}
