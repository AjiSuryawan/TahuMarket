package com.example.tahumarket.helper;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class HeaderNotaModel extends RealmObject {

    private Integer iddata;
    @PrimaryKey
    private String noNota;
    private String noCustomer;
    private String transdate;
    private String totalOrigin;
    private String ppn;
    private String discount;
    private String grandTotal;
    private String payment;
    private String kembalian;

    public Integer getIddata() {
        return iddata;
    }

    public void setIddata(Integer iddata) {
        this.iddata = iddata;
    }

    public String getNoNota() {
        return noNota;
    }

    public void setNoNota(String noNota) {
        this.noNota = noNota;
    }

    public String getNoCustomer() {
        return noCustomer;
    }

    public void setNoCustomer(String noCustomer) {
        this.noCustomer = noCustomer;
    }

    public String getTransdate() {
        return transdate;
    }

    public void setTransdate(String transdate) {
        this.transdate = transdate;
    }

    public String getTotalOrigin() {
        return totalOrigin;
    }

    public void setTotalOrigin(String totalOrigin) {
        this.totalOrigin = totalOrigin;
    }

    public String getPpn() {
        return ppn;
    }

    public void setPpn(String ppn) {
        this.ppn = ppn;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getKembalian() {
        return kembalian;
    }

    public void setKembalian(String kembalian) {
        this.kembalian = kembalian;
    }
}
