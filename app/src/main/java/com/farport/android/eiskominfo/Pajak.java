package com.farport.android.eiskominfo;

import java.io.Serializable;

public class Pajak implements Serializable {
    private int tahun;
    private Double totalpajak, target;
    private String id_jenis_pajak, jenis_pajak;

    public Pajak(){}

    public Pajak(String id_jenis_pajak, String jenis_pajak, int tahun, Double totalpajak, Double target) {
        this.id_jenis_pajak = id_jenis_pajak;
        this.jenis_pajak = jenis_pajak;
        this.tahun = tahun;
        this.totalpajak = totalpajak;
        this.target = target;
    }

    public String getId_jenis_pajak() {
        return id_jenis_pajak;
    }

    public int getTahun() {
        return tahun;
    }

    public Double getTotalpajak() {
        return totalpajak;
    }

    public Double getTarget() {
        return target;
    }

    public String getJenis_pajak() {
        return jenis_pajak;
    }
}
