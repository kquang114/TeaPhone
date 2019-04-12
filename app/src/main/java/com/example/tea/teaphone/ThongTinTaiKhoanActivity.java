package com.example.tea.teaphone;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tea.teaphone.Model.CheckConnection;
import com.example.tea.teaphone.Model.InforAccount;
import com.example.tea.teaphone.Model.Server;
import com.example.tea.teaphone.Model.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThongTinTaiKhoanActivity extends AppCompatActivity {
    TextView txtTaiKhoan, txtThayDoiMatKhau, txtHoTen, txtDiaChi, txtSDT, txtChinhSua;
    SessionManager sessionManager;
    Toolbar toolbar;
    ArrayList<InforAccount> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_tai_khoan);

        txtTaiKhoan = findViewById(R.id.tvTaiKhoan);
        txtThayDoiMatKhau = findViewById(R.id.tvThayDoiMatKhau);
        txtHoTen = findViewById(R.id.tvHoTen);
        txtDiaChi = findViewById(R.id.tvDiaChi);
        txtSDT = findViewById(R.id.tvSDT);
        txtChinhSua = findViewById(R.id.tvChinhSua);
        sessionManager = new SessionManager(ThongTinTaiKhoanActivity.this);
        toolbar = findViewById(R.id.toolbarTTTK);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LoadData();
        ThayDoiMatKhau();
        ChinhSuaThongTin();
    }

    private void ChinhSuaThongTin() {
        txtChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongTinTaiKhoanActivity.this, ChinhSuaTTKActivity.class);
                intent.putExtra("tttk", arrayList);
                startActivity(intent);
                finish();
            }
        });
    }

    private void ThayDoiMatKhau() {
        txtThayDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void LoadData() {
        arrayList = new ArrayList<>();
        txtTaiKhoan.setText(sessionManager.getUserName());

        if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URLGetTTTK, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        arrayList.add(new InforAccount(
                                jsonObject.getString("hoten"),
                                jsonObject.getString("diachi"),
                                jsonObject.getString("sdt")
                        ));
                        txtHoTen.setText(getString(R.string.ho_ten) + "\n" + jsonObject.getString("hoten"));
                        txtDiaChi.setText(getString(R.string.dia_chi) + "\n" + jsonObject.getString("diachi"));
                        txtSDT.setText(getString(R.string.sdt) + "\n" + jsonObject.getString("sdt"));
                    } catch (JSONException e) {
                        e.printStackTrace();
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
                    hashMap.put("idAccount", sessionManager.getIDAccount());
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);

        }
        else {
            CheckConnection.ShowToast(getApplicationContext(), getString(R.string.kiem_tra_ket_noi));
        }

    }
}
