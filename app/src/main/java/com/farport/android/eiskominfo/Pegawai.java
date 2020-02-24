package com.farport.android.eiskominfo;

import java.io.Serializable;

public class Pegawai implements Serializable {
    String nip, nama, jenis_kelamin, kedudukan, status_pegawai, golongan, pangkat, unit_kerja;
    int usia;

    public Pegawai(String nip, String nama, String jenis_kelamin, String kedudukan, String status_pegawai, String golongan, String pangkat, String unit_kerja, int usia) {
        this.nip = nip;
        this.nama = nama;
        this.jenis_kelamin = jenis_kelamin;
        this.kedudukan = kedudukan;
        this.status_pegawai = status_pegawai;
        this.golongan = golongan;
        this.pangkat = pangkat;
        this.unit_kerja = unit_kerja;
        this.usia = usia;
    }

    public Pegawai (String unit_kerja){
        this.unit_kerja = unit_kerja;
    }

    public String getNip() {
        return nip;
    }

    public String getNama() {
        return nama;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public String getKedudukan() {
        return kedudukan;
    }

    public String getStatus_pegawai() {
        return status_pegawai;
    }

    public String getGolongan() {
        return golongan;
    }

    public String getPangkat() {
        return pangkat;
    }

    public String getUnit_kerja() {
        return unit_kerja;
    }

    public int getUsia() {
        return usia;
    }
}
