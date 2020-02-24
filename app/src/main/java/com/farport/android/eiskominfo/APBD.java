package com.farport.android.eiskominfo;

import java.io.Serializable;

public class APBD implements Serializable {
    private String u2,u3, persentase;
    private Double anggaran, realisasi;

    public APBD(){}

    public APBD(String u2, String u3, String persentase, Double anggaran, Double realisasi) {
        this.u2 = u2;
        this.u3 = u3;
        this.persentase = persentase;
        this.anggaran = anggaran;
        this.realisasi = realisasi;
    }

    public String getU2() {
        return u2;
    }

    public String getU3() {
        return u3;
    }

    public String getPersentase() {
        return persentase;
    }

    public Double getAnggaran() {
        return anggaran;
    }

    public Double getRealisasi() {
        return realisasi;
    }
}
