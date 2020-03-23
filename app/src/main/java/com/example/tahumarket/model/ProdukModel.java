package com.example.tahumarket.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class ProdukModel extends RealmObject implements Parcelable {
    private String kodeBarang;
    private String namaBarang;
    private int hargaBarang;
    private String kodeWarna;
    private String kodePackaging;


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

    public ProdukModel(){}

    public ProdukModel(String kodeBarang, String namaBarang, int hargaBarang, String kodeWarna, String kodePackaging){
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
        this.kodeWarna = kodeWarna;
        this.kodePackaging = kodePackaging;
    }

    protected ProdukModel(Parcel in) {
        kodeBarang = in.readString();
        namaBarang = in.readString();
        hargaBarang = in.readInt();
        kodeWarna = in.readString();
        kodePackaging = in.readString();
    }

    public static final Creator<ProdukModel> CREATOR = new Creator<ProdukModel>() {
        @Override
        public ProdukModel createFromParcel(Parcel in) {
            return new ProdukModel(in);
        }

        @Override
        public ProdukModel[] newArray(int size) {
            return new ProdukModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kodeBarang);
        dest.writeString(namaBarang);
        dest.writeInt(hargaBarang);
        dest.writeString(kodeWarna);
        dest.writeString(kodePackaging);
    }
}
