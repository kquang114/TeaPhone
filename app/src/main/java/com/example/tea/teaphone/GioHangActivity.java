package com.example.tea.teaphone;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tea.teaphone.Adapter.GioHangAdapter;
import com.example.tea.teaphone.Model.CheckConnection;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    ListView listViewGioHang;
    TextView txtThongBao;
    static TextView txtTongTien;
    Button btnThanhToan, btnTiepTucMua;
    Toolbar toolbar;
    GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        toolbar = findViewById(R.id.toolbarGioHang);
        listViewGioHang = findViewById(R.id.listviewGioHang);
        txtThongBao = findViewById(R.id.tvGioHangTrong);
        txtTongTien = findViewById(R.id.tvTongTienGioHang);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnTiepTucMua = findViewById(R.id.btnTiepTucMuaHang);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gioHangAdapter = new GioHangAdapter(GioHangActivity.this, MainActivity.gioHangArrayList);
        listViewGioHang.setAdapter(gioHangAdapter);

        if(MainActivity.gioHangArrayList.size() > 0) {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.INVISIBLE);
            listViewGioHang.setVisibility(View.VISIBLE);
        }
        else {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.VISIBLE);
            listViewGioHang.setVisibility(View.INVISIBLE);
        }
        GetTongTien();
        XoaSanPhamKhoiGioHang();

        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GioHangActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.gioHangArrayList.size() > 0) {
                    Intent intent = new Intent(GioHangActivity.this, ThongTinKhachHangActivity.class);
                    startActivity(intent);
                }
                else {
                    CheckConnection.ShowToast(GioHangActivity.this, "Giỏ hàng chưa có sản phẩm để thanh toán !!!");
                }
            }
        });
    }

    private void XoaSanPhamKhoiGioHang() {
        listViewGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Thông Báo");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này ra khỏi giỏ hàng ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.gioHangArrayList.remove(position);
                        gioHangAdapter.notifyDataSetChanged();
                        GetTongTien();
                        if(MainActivity.gioHangArrayList.size() <= 0) {
                            txtThongBao.setVisibility(View.VISIBLE);
                            GetTongTien();
                        }
                        else {
                            txtThongBao.setVisibility(View.INVISIBLE);
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void GetTongTien() {
        long tongtien = 0;
        for(int i = 0; i < MainActivity.gioHangArrayList.size(); i++) {
            tongtien += MainActivity.gioHangArrayList.get(i).getTongGiaSP();
        }
        DecimalFormat df = new DecimalFormat("###,###,###");
        txtTongTien.setText(df.format(tongtien) + "VNĐ");
    }
}
