package com.farport.android.eiskominfo;

public class AppConfig {

    public static final String BASE_URL = "secret";

    public static final String API_USER = BASE_URL + "api/User/";
    public static final String API_PAJAK = BASE_URL + "api/Pajak/";
    public static final String API_BKD = BASE_URL + "api/BKD/";
    public static final String API_BPKAD = BASE_URL + "api/BPKAD/";
    public static final String API_RDS = BASE_URL + "api/RDS/";
    public static final String API_KEPENDUDUKAN = BASE_URL + "api/Kependudukan/";
    public static final String API_KATEGORI = BASE_URL + "api/Kategori/";

    public static final String URL_LOGIN = API_USER + "login";
    public static final String URL_GET_ALL_USER = API_USER + "alluser?";
    public static final String URL_TARGET_PAJAK = API_PAJAK + "target?tahun=";
    public static final String URL_PENDAPATAN_ALL = API_PAJAK + "pendapatanall?tahun=";
    public static final String URL_ALL_SKPD = API_BKD + "allskpd";
    public static final String URL_PEGAWAI_BY_JENIS_KELAMIN = API_BKD + "countbyjeniskelamin";
    public static final String URL_PEGAWAI_BY_USIA = API_BKD + "countbyusia";
    public static final String URL_PEGAWAI_BY_GOL = API_BKD + "countbygolkel?gol=";
    public static final String URL_SEARCH_PEGAWAI = API_BKD + "searchpegawai?";
    public static final String URL_SEARCH_PEGAWAI_POST = API_BKD  + "searchpegawai";
    public static final String URL_TOTAL_APBD = API_BPKAD + "apbd?tahun=";
    public static final String URL_ANGGARAN_REALISASI_1 = API_BPKAD + "anggaranrealisasi1?";
    public static final String URL_ANGGARAN_REALISASI_2 = API_BPKAD + "anggaranrealisasi2?";
    public static final String URL_APBD_POST = API_RDS + "apbd";
    public static final String URL_LIST_SKPD = API_RDS + "listskpd?";
    public static final String URL_PROGRAM_SKPD = API_RDS + "programskpd?";
    public static final String URL_USULAN_TOTAL = API_RDS + "totalusulan?";
    public static final String URL_DANA_SKPD = API_RDS + "topdanakegiatan?";
    public static final String URL_DANA_URUSAN = API_RDS + "topdanaurusan?";
    public static final String URL_PENDUDUK_ALL = API_KEPENDUDUKAN + "totalpenduduk";
    public static final String URL_PENDUDUK_BY_GENDER = API_KEPENDUDUKAN + "bygender";
    public static final String URL_PENDUDUK_BY_GENDER_DETAIL = API_KEPENDUDUKAN + "bygenderdetail";
    public static final String URL_PENDUDUK_BY_PENDIDIKAN = API_KEPENDUDUKAN + "bypendidikan";
    public static final String URL_PENDUDUK_BY_PENDIDIKAN_DETAIL = API_KEPENDUDUKAN + "bypendidikandetail";
    public static final String URL_PENDUDUK_BY_PEKERJAAN = API_KEPENDUDUKAN + "bypekerjaan";
    public static final String URL_PENDUDUK_KECAMATAN = API_KEPENDUDUKAN + "allkecamatan";
    public static final String URL_PENDUDUK_BY_AGAMA = API_KEPENDUDUKAN + "byagama";
    public static final String URL_PENDUDUK_BY_AGAMA_DETAIL = API_KEPENDUDUKAN + "byagamadetail?";
    public static final String URL_PENDUDUK_BY_USIA = API_KEPENDUDUKAN + "byusia";
    public static final String URL_ALL_KATEGORI = API_KATEGORI + "allcategory";
}
