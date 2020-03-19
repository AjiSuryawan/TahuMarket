package com.example.tahumarket.helper;

/**
 * Created by WSeven7 on 2/13/2017.
 */

public class ItemOption {
    private String optId;
    private String optLabel;

    public String getOptId() {
        return optId;
    }

    public void setOptId(String optId) {
        this.optId = optId;
    }

    public String getOptLabel() {
        return optLabel;
    }

    public void setOptLabel(String optLabel) {
        this.optLabel = optLabel;
    }

    public ItemOption(String optId, String optLabel) {
        this.optId = optId;
        this.optLabel = optLabel;
    }
}
