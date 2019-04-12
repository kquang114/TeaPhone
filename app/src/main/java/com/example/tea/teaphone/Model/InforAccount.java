package com.example.tea.teaphone.Model;

import java.io.Serializable;

public class InforAccount implements Serializable {
    private String hoTen;
    private String diaChi;
    private String SDT;

    public InforAccount(String hoTen, String diaChi, String SDT) {
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.SDT = SDT;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
}
