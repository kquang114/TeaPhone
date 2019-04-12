package com.example.tea.teaphone.Model;

public class LoaiSP {
    private int id;
    private String tenLoaiSP;
    private String hinhanhLoaiSP;

    public LoaiSP(int id, String tenLoaiSP, String hinhanhLoaiSP) {
        this.id = id;
        this.tenLoaiSP = tenLoaiSP;
        this.hinhanhLoaiSP = hinhanhLoaiSP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoaiSP() {
        return tenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        this.tenLoaiSP = tenLoaiSP;
    }

    public String getHinhanhLoaiSP() {
        return hinhanhLoaiSP;
    }

    public void setHinhanhLoaiSP(String hinhanhLoaiSP) {
        this.hinhanhLoaiSP = hinhanhLoaiSP;
    }
}
