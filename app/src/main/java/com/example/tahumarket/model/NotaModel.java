package com.example.tahumarket.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class NotaModel extends RealmObject {
    private Integer iddata;
    @PrimaryKey
    private String kodeBarang;
    private String namaBarang;
    private int hargaBarang;
    private String kodeWarna;
    private String kodePackaging;
    private String kodeNota;
    private int jumlahbarang;
    private int subtotal;

    public Integer getIddata() {
        return iddata;
    }

    public void setIddata(Integer iddata) {
        this.iddata = iddata;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public int getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(int hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public String getKodeWarna() {
        return kodeWarna;
    }

    public void setKodeWarna(String kodeWarna) {
        this.kodeWarna = kodeWarna;
    }

    public String getKodePackaging() {
        return kodePackaging;
    }

    public void setKodePackaging(String kodePackaging) {
        this.kodePackaging = kodePackaging;
    }

    public String getKodeNota() {
        return kodeNota;
    }

    public void setKodeNota(String kodeNota) {
        this.kodeNota = kodeNota;
    }

    public int getJumlahbarang() {
        return jumlahbarang;
    }

    public void setJumlahbarang(int jumlahbarang) {
        this.jumlahbarang = jumlahbarang;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }
}
