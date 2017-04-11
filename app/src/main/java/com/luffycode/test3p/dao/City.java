package com.luffycode.test3p.dao;

/**
 * Created by Luffynas on 4/11/2017.
 */

public class City {
    String kode_kota;
    String NamaKota;
    String Jenis;
    String FK_Propinsi;

    public String getKode_kota() {
        return kode_kota;
    }

    public void setKode_kota(String kode_kota) {
        this.kode_kota = kode_kota;
    }

    public String getNamaKota() {
        return NamaKota;
    }

    public void setNamaKota(String namaKota) {
        NamaKota = namaKota;
    }

    public String getJenis() {
        return Jenis;
    }

    public void setJenis(String jenis) {
        Jenis = jenis;
    }

    public String getFK_Propinsi() {
        return FK_Propinsi;
    }

    public void setFK_Propinsi(String FK_Propinsi) {
        this.FK_Propinsi = FK_Propinsi;
    }
}
