package com.example.tea.teaphone.Model;

public class GioHang {
    public int idSP;
    public String tenSP;
    public String hinhAnhSP;
    public int giaSP;
    public int soLuong;
    public long tongGiaSP;

    public GioHang(int idSP, String tenSP, String hinhAnhSP, int giaSP, int soLuong, long tongGiaSP) {
        this.idSP = idSP;
        this.tenSP = tenSP;
        this.hinhAnhSP = hinhAnhSP;
        this.giaSP = giaSP;
        this.soLuong = soLuong;
        this.tongGiaSP = tongGiaSP;
    }

    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getHinhAnhSP() {
        return hinhAnhSP;
    }

    public void setHinhAnhSP(String hinhAnhSP) {
        this.hinhAnhSP = hinhAnhSP;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public long getTongGiaSP() {
        return tongGiaSP;
    }

    public void setTongGiaSP(long tongGiaSP) {
        this.tongGiaSP = tongGiaSP;
    }
}
