package com.farport.android.eiskominfo;

public class Kependudukan {
    String kecamatan, agama, pekerjaan;
    int jumlah_pria, jumlah_wanita, total_jumlah, tbs, bts, tts, sltp, slta, d1_d2, d3, s1, s2, s3,
        jumlah_agama, jumlah_pekerjaan;

    public Kependudukan(String kecamatan, int jumlah_pria, int jumlah_wanita, int total_jumlah) {
        this.kecamatan = kecamatan;
        this.jumlah_pria = jumlah_pria;
        this.jumlah_wanita = jumlah_wanita;
        this.total_jumlah = total_jumlah;
    }

    public Kependudukan(String kecamatan, int tbs, int bts, int tts, int sltp, int slta, int d1_d2, int d3, int s1, int s2, int s3) {
        this.kecamatan = kecamatan;
        this.tbs = tbs;
        this.bts = bts;
        this.tts = tts;
        this.sltp = sltp;
        this.slta = slta;
        this.d1_d2 = d1_d2;
        this.d3 = d3;
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
    }

    public Kependudukan(String agama, int jumlah_agama) {
        this.agama = agama;
        this.jumlah_agama = jumlah_agama;
    }

    public Kependudukan(int jumlah_pekerjaan, String pekerjaan){
        this.jumlah_pekerjaan = jumlah_pekerjaan;
        this.pekerjaan = pekerjaan;
    }

    public String getAgama() {
        return agama;
    }

    public int getJumlah_agama() {
        return jumlah_agama;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public int getJumlah_pekerjaan() {
        return jumlah_pekerjaan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public int getJumlah_pria() {
        return jumlah_pria;
    }

    public int getJumlah_wanita() {
        return jumlah_wanita;
    }

    public int getTotal_jumlah() {
        return total_jumlah;
    }

    public int getTbs() {
        return tbs;
    }

    public int getBts() {
        return bts;
    }

    public int getTts() {
        return tts;
    }

    public int getSltp() {
        return sltp;
    }

    public int getSlta() {
        return slta;
    }

    public int getD1_d2() {
        return d1_d2;
    }

    public int getD3() {
        return d3;
    }

    public int getS1() {
        return s1;
    }

    public int getS2() {
        return s2;
    }

    public int getS3() {
        return s3;
    }
}
