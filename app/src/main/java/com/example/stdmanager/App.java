package com.example.stdmanager;

import android.app.Application;

import com.example.stdmanager.models.GiaoVien;

public class App  extends Application {

    private GiaoVien gv = null;

    public GiaoVien getGiaoVien() {
        return gv;
    }

    public void setGiaoVien(GiaoVien gv) {
        this.gv = gv;
    }
}
