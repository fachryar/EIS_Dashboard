package com.farport.android.eiskominfo;

import java.io.Serializable;

public class User implements Serializable {
    int id_user, state;
    String nama, email, nohp;

    String username, nama_user, jabatan, skpd, no_hp, password;

    public User(String username, String nama_user, String jabatan, String skpd, String no_hp, int state) {
        this.username = username;
        this.nama_user = nama_user;
        this.jabatan = jabatan;
        this.skpd = skpd;
        this.no_hp = no_hp;
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public String getNama_user() {
        return nama_user;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public int getState() {
        return state;
    }

    public String getJabatan() {
        return jabatan;
    }

    public String getSkpd() {
        return skpd;
    }

}
