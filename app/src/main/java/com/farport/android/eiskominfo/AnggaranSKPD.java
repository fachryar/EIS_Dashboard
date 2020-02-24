package com.farport.android.eiskominfo;

import java.io.Serializable;

public class AnggaranSKPD implements Serializable{
    String kategori, program, indikator, target;
    int tahun, id_skpd;
    Double usulan_pd, usulan_kecamatan, usulan_dprd, total, dana;

    public AnggaranSKPD(int id_skpd, int tahun, String kategori, Double total) {
        this.id_skpd = id_skpd;
        this.tahun = tahun;
        this.kategori = kategori;
        this.total = total;
    }

    public AnggaranSKPD(String kategori, Double total) {
        this.kategori = kategori;
        this.total = total;
    }

    public AnggaranSKPD(int tahun, Double usulan_pd, Double usulan_kecamatan, Double usulan_dprd, Double total) {
        this.tahun = tahun;
        this.usulan_pd = usulan_pd;
        this.usulan_kecamatan = usulan_kecamatan;
        this.usulan_dprd = usulan_dprd;
        this.total = total;
    }

    public AnggaranSKPD(String program, String indikator, String target, Double dana) {
        this.program = program;
        this.indikator = indikator;
        this.target = target;
        this.dana = dana;
    }

    public String getProgram() {
        return program;
    }

    public String getIndikator() {
        return indikator;
    }

    public String getTarget() {
        return target;
    }

    public Double getDana() {
        return dana;
    }

    public String getKategori() {
        return kategori;
    }

    public Double getTotal() {
        return total;
    }

    public int getTahun() {
        return tahun;
    }

    public Double getUsulan_pd() {
        return usulan_pd;
    }

    public Double getUsulan_kecamatan() {
        return usulan_kecamatan;
    }

    public Double getUsulan_dprd() {
        return usulan_dprd;
    }

    public int getId_skpd() {
        return id_skpd;
    }
}
