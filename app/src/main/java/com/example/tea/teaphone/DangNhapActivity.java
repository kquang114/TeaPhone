package com.example.tea.teaphone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tea.teaphone.Model.Account;
import com.example.tea.teaphone.Model.CheckConnection;
import com.example.tea.teaphone.Model.Server;
import com.example.tea.teaphone.Model.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DangNhapActivity extends AppCompatActivity {
    EditText editTaiKhoan, editMatKhau;
    Button btnDangNhap;
    TextView txtDangKi;
    Toolbar toolbarDangNhap;
    ArrayList<Account> arrayList;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        editTaiKhoan = findViewById(R.id.editTaiKhoan);
        editMatKhau = findViewById(R.id.editMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        txtDangKi = findViewById(R.id.tvDangKi);
        toolbarDangNhap = findViewById(R.id.toolbarDangNhap);
        arrayList = new ArrayList<>();
        sessionManager = new SessionManager(getApplicationContext());

        setSupportActionBar(toolbarDangNhap);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDangNhap.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        XuLyDangNhap();
        XyLyNutDangKi();
    }

    private void XyLyNutDangKi() {
        txtDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapActivity.this, DangKiActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void XuLyDangNhap() {
        if(CheckConnection.haveNetworkConnection(DangNhapActivity.this)) {
            btnDangNhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String username = editTaiKhoan.getText().toString();
                    final String password = editMatKhau.getText().toString();
                    if(username.length() > 0 && password.length() > 0) {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URLDangNhap, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("username va password khong dung")) {
                                    CheckConnection.ShowToast(getApplicationContext(), getString(R.string.sai_tai_khoan_hoac_mat_khau));
                                }
                                else {
                                    Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                                    sessionManager.setLoginState(true);
                                    sessionManager.setUserName(username);
                                    sessionManager.setIDAccount(response);
                                    startActivity(intent);
                                    finish();
                                    CheckConnection.ShowToast(getApplicationContext(), getString(R.string.dang_nhap_thanh_cong));
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                CheckConnection.ShowToast(getApplicationContext(), error.toString());
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                hashMap.put("username", username);
                                hashMap.put("password", password);
                                return hashMap;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                    else {
                        CheckConnection.ShowToast(getApplicationContext(), getString(R.string.TK_MK_trong));
                    }
                }
            });
        }
        else {
            CheckConnection.ShowToast(getApplicationContext(), getString(R.string.kiem_tra_ket_noi));
        }
    }
}
