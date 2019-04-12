package com.example.tea.teaphone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tea.teaphone.Model.CheckConnection;
import com.example.tea.teaphone.Model.Server;
import com.example.tea.teaphone.Model.SessionManager;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DangKiActivity extends AppCompatActivity {
    EditText editTaiKhoan, editMatKhau, editXacNhanMatKhau;
    Button btnDangKi;
    Toolbar toolbarDangKi;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);

        editTaiKhoan = findViewById(R.id.editTaiKhoan);
        editMatKhau = findViewById(R.id.editMatKhau);
        editXacNhanMatKhau = findViewById(R.id.editXacNhanMatKhau);
        btnDangKi = findViewById(R.id.btnDangKi);
        toolbarDangKi = findViewById(R.id.toolbarDangKi);

        sessionManager = new SessionManager(getApplicationContext());
        setSupportActionBar(toolbarDangKi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDangKi.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangKiActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        XuLyDangKi();
    }

    private void XuLyDangKi() {
        if(CheckConnection.haveNetworkConnection(DangKiActivity.this)) {
            btnDangKi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String username = editTaiKhoan.getText().toString();
                    final String password = editMatKhau.getText().toString();
                    final String confirmPassword = editXacNhanMatKhau.getText().toString();
                    if(username.length() > 0 && password.length() > 0 && confirmPassword.length() > 0) {
                        if(password.equals(confirmPassword)) {
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URLDangKi, new Response.Listener<String>() {
                                @Override
                                public void onResponse(final String response) {
                                    if(response.equals("user da ton tai")) {
                                        CheckConnection.ShowToast(getApplicationContext(), getString(R.string.tai_khoan_da_ton_tai));
                                    }
                                    else {
                                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                        StringRequest request = new StringRequest(Request.Method.POST, Server.URLInsertTTTK, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                CheckConnection.ShowToast(getApplicationContext(), getString(R.string.kiem_tra_ket_noi));
                                            }
                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                                hashMap.put("idAccount", response);
                                                return hashMap;
                                            }
                                        };
                                        queue.add(request);
                                        Intent intent = new Intent(DangKiActivity.this, MainActivity.class);
                                        sessionManager.setLoginState(true);
                                        sessionManager.setUserName(username);
                                        sessionManager.setIDAccount(response);
                                        startActivity(intent);
                                        finish();
                                        CheckConnection.ShowToast(getApplicationContext(), getString(R.string.dang_ki_thanh_cong));
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
                            CheckConnection.ShowToast(getApplicationContext(), getString(R.string.mat_khau_khong_trung));
                        }
                    }
                    else {
                        CheckConnection.ShowToast(getApplicationContext(),getString(R.string.TK_MK_trong));
                    }
                }
            });
        }
        else {
            CheckConnection.ShowToast(getApplicationContext(), getString(R.string.kiem_tra_ket_noi));
        }
    }
}
