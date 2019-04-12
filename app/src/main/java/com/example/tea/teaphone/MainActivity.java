package com.example.tea.teaphone;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tea.teaphone.Adapter.LoaiSPAdapter;
import com.example.tea.teaphone.Adapter.SanPhamAdapter;
import com.example.tea.teaphone.Adapter.SanPhamPhoBienAdapter;
import com.example.tea.teaphone.Adapter.SlideAdapter;
import com.example.tea.teaphone.Model.CheckConnection;
import com.example.tea.teaphone.Model.GioHang;
import com.example.tea.teaphone.Model.LoaiSP;
import com.example.tea.teaphone.Model.SanPham;
import com.example.tea.teaphone.Model.Server;
import com.example.tea.teaphone.Model.SessionManager;
import com.example.tea.teaphone.Model.Slide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView txtAllIphone, txtAllOppo, txtAllSamSung, txtAllXiaomi;
    Toolbar toolbar;
    ViewPager viewPager;
    NavigationView navigationView;
    ListView listView;
    RecyclerView rvspmoinhat, rviphone, rvoppo, rvsamsung, rvxiaomi;
    DrawerLayout drawerLayout;
    Timer timer;
    int current_position = 0;
    LinearLayout dotsLayout;
    ArrayList<Slide> slideArrayList;
    SlideAdapter slideAdapter;
    ArrayList<LoaiSP> loaiSPArrayList;
    LoaiSPAdapter loaiSPAdapter;
    ArrayList<SanPham> sanPhamArrayList, iphoneArrayList, oppoArrayList, samsungArrayList, xiaomiArrayList;
    SanPhamAdapter sanPhamAdapter;
    SanPhamPhoBienAdapter iphoneAdapter, oppoAdapter, samsungAdapter, xiaomiAdapter;
    public static ArrayList<GioHang> gioHangArrayList;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dotsLayout = findViewById(R.id.dotscontainer);
        toolbar = findViewById(R.id.toolbarHome);
        navigationView = findViewById(R.id.navigationMenu);
        listView = findViewById(R.id.listviewMenu);
        drawerLayout = findViewById(R.id.drawerLayout);
        rvspmoinhat = findViewById(R.id.rvSPmoinhat);
        rviphone = findViewById(R.id.rviphone);
        rvoppo = findViewById(R.id.rvoppo);
        rvsamsung = findViewById(R.id.rvsamsung);
        rvxiaomi = findViewById(R.id.rvxiaomi);
        txtAllIphone = findViewById(R.id.txtAllIphone);
        txtAllOppo = findViewById(R.id.txtAllOppo);
        txtAllSamSung = findViewById(R.id.txtAllSamSung);
        txtAllXiaomi = findViewById(R.id.txtAllXiaomi);

        sessionManager = new SessionManager(MainActivity.this);

        if(gioHangArrayList == null) {
            gioHangArrayList = new ArrayList<>();
        }

        txtAllIphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DanhSachSanPhamActivity.class);
                intent.putExtra("idloaisp", 1);
                intent.putExtra("title_activity", getString(R.string.title_recyclerview_iphone));
                startActivity(intent);
            }
        });
        txtAllOppo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DanhSachSanPhamActivity.class);
                intent.putExtra("idloaisp", 2);
                intent.putExtra("title_activity", getString(R.string.title_recyclerview_oppo));
                startActivity(intent);
            }
        });
        txtAllSamSung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DanhSachSanPhamActivity.class);
                intent.putExtra("idloaisp", 3);
                intent.putExtra("title_activity", getString(R.string.title_recyclerview_samsung));
                startActivity(intent);
            }
        });
        txtAllXiaomi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DanhSachSanPhamActivity.class);
                intent.putExtra("idloaisp", 4);
                intent.putExtra("title_activity", getString(R.string.title_recyclerview_xiaomi));
                startActivity(intent);
            }
        });

        loaiSPArrayList = new ArrayList<>();
        loaiSPAdapter = new LoaiSPAdapter(loaiSPArrayList, MainActivity.this);
        listView.setAdapter(loaiSPAdapter);

        sanPhamArrayList = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(sanPhamArrayList, MainActivity.this);
//        rvspmoinhat.setHasFixedSize(true);

//        rvspmoinhat.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        rvspmoinhat.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));

        rvspmoinhat.setAdapter(sanPhamAdapter);

        iphoneArrayList = new ArrayList<>();
        iphoneAdapter = new SanPhamPhoBienAdapter(iphoneArrayList, MainActivity.this);
//        rvtivi.setHasFixedSize(true);
//        rvtivi.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        rviphone.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        rviphone.setAdapter(iphoneAdapter);

        oppoArrayList = new ArrayList<>();
        oppoAdapter = new SanPhamPhoBienAdapter(oppoArrayList, MainActivity.this);
 //       rvoppo.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        rvoppo.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        rvoppo.setAdapter(oppoAdapter);

        samsungArrayList = new ArrayList<>();
        samsungAdapter = new SanPhamPhoBienAdapter(samsungArrayList, MainActivity.this);
  //      rvsamsung.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        rvsamsung.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        rvsamsung.setAdapter(samsungAdapter);

        xiaomiArrayList = new ArrayList<>();
        xiaomiAdapter = new SanPhamPhoBienAdapter(xiaomiArrayList, MainActivity.this);
//        rvxiaomi.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        rvxiaomi.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        rvxiaomi.setAdapter(xiaomiAdapter);

        SlideQuangCao();



        if(CheckConnection.haveNetworkConnection(this)){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });

            getDuLieuLoaiSP();
            getDuLieuSPMoiNhat();
            getIphone();
            getOppo();
            getsamsung();
            getXiaomi();
        }
        else {
            CheckConnection.ShowToast(this, getString(R.string.kiem_tra_ket_noi));
            finish();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(loaiSPArrayList.get(position).getTenLoaiSP().equals("TK: " + sessionManager.getUserName())) {
                    if(CheckConnection.haveNetworkConnection(MainActivity.this)) {
                        Intent intent = new Intent(MainActivity.this, ThongTinTaiKhoanActivity.class);
                        startActivity(intent);
                    }
                    else {
                        CheckConnection.ShowToast(MainActivity.this, getString(R.string.kiem_tra_ket_noi));
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                switch (loaiSPArrayList.get(position).getTenLoaiSP()) {
                    case "Trang Chủ":
                        if(CheckConnection.haveNetworkConnection(MainActivity.this)) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            CheckConnection.ShowToast(MainActivity.this, getString(R.string.kiem_tra_ket_noi));
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case "Iphone":
                        if(CheckConnection.haveNetworkConnection(MainActivity.this)) {
                            Intent intent = new Intent(MainActivity.this, DanhSachSanPhamActivity.class);
                            intent.putExtra("idloaisp", loaiSPArrayList.get(position).getId());
                            intent.putExtra("title_activity", getString(R.string.title_recyclerview_iphone));
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.ShowToast(MainActivity.this, getString(R.string.kiem_tra_ket_noi));
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case "Oppo":
                        if(CheckConnection.haveNetworkConnection(MainActivity.this)) {
                            Intent intent = new Intent(MainActivity.this, DanhSachSanPhamActivity.class);
                            intent.putExtra("idloaisp", loaiSPArrayList.get(position).getId());
                            intent.putExtra("title_activity", getString(R.string.title_recyclerview_oppo));
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.ShowToast(MainActivity.this, getString(R.string.kiem_tra_ket_noi));
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case "SamSung":
                        if(CheckConnection.haveNetworkConnection(MainActivity.this)) {
                            Intent intent = new Intent(MainActivity.this, DanhSachSanPhamActivity.class);
                            intent.putExtra("idloaisp", loaiSPArrayList.get(position).getId());
                            intent.putExtra("title_activity", getString(R.string.title_recyclerview_samsung));
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.ShowToast(MainActivity.this, getString(R.string.kiem_tra_ket_noi));
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case "Xiaomi":
                        if(CheckConnection.haveNetworkConnection(MainActivity.this)) {
                            Intent intent = new Intent(MainActivity.this, DanhSachSanPhamActivity.class);
                            intent.putExtra("idloaisp", loaiSPArrayList.get(position).getId());
                            intent.putExtra("title_activity", getString(R.string.title_recyclerview_xiaomi));
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.ShowToast(MainActivity.this, getString(R.string.kiem_tra_ket_noi));
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case "Thông Tin":
                        if(CheckConnection.haveNetworkConnection(MainActivity.this)) {
                            Intent intent = new Intent(MainActivity.this, ThongTinActivity.class);
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.ShowToast(MainActivity.this, getString(R.string.kiem_tra_ket_noi));
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case "Đăng Nhập":
                        if(CheckConnection.haveNetworkConnection(MainActivity.this)) {
                            Intent intent = new Intent(MainActivity.this, DangNhapActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            CheckConnection.ShowToast(MainActivity.this, getString(R.string.kiem_tra_ket_noi));
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case "Lịch Sử Mua Hàng":
                        if(CheckConnection.haveNetworkConnection(MainActivity.this)) {
                            Intent intent = new Intent(MainActivity.this, LichSuMuaHangActivity.class);
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.ShowToast(MainActivity.this, getString(R.string.kiem_tra_ket_noi));
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case "Đăng Xuất":
                        sessionManager.setLoginState(false);
                        sessionManager.setIDAccount("");
                        sessionManager.setUserName("");
                        CheckConnection.ShowToast(MainActivity.this, "Đăng xuất thành công");
                        if(CheckConnection.haveNetworkConnection(MainActivity.this)) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            CheckConnection.ShowToast(MainActivity.this, getString(R.string.kiem_tra_ket_noi));
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void SlideQuangCao() {
        slideShow();
        prepareDots(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                current_position = i;
                prepareDots(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void getIphone() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.URLIphone, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null) {
                    for(int i = 0; i < response.length(); i ++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            iphoneArrayList.add(new SanPham(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("tensp"),
                                    jsonObject.getInt("giasp"),
                                    jsonObject.getString("hinhanhsp"),
                                    jsonObject.getString("motasp"),
                                    jsonObject.getInt("idloaisp")
                            ));
                            iphoneAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast(MainActivity.this, error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getOppo() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.URLOppo, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null) {
                    for(int i = 0; i < response.length(); i ++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            oppoArrayList.add(new SanPham(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("tensp"),
                                    jsonObject.getInt("giasp"),
                                    jsonObject.getString("hinhanhsp"),
                                    jsonObject.getString("motasp"),
                                    jsonObject.getInt("idloaisp")
                            ));
                            oppoAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast(MainActivity.this, error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getsamsung() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.URLSamSung, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null) {
                    for(int i = 0; i < response.length(); i ++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            samsungArrayList.add(new SanPham(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("tensp"),
                                    jsonObject.getInt("giasp"),
                                    jsonObject.getString("hinhanhsp"),
                                    jsonObject.getString("motasp"),
                                    jsonObject.getInt("idloaisp")
                            ));
                            samsungAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast(MainActivity.this, error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getXiaomi() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.URLXiaomi, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null) {
                    for(int i = 0; i < response.length(); i ++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            xiaomiArrayList.add(new SanPham(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("tensp"),
                                    jsonObject.getInt("giasp"),
                                    jsonObject.getString("hinhanhsp"),
                                    jsonObject.getString("motasp"),
                                    jsonObject.getInt("idloaisp")
                            ));
                            xiaomiAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast(MainActivity.this, error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getDuLieuSPMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.URLSPMoiNhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null) {
                    for(int i = 0; i < response.length(); i ++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            sanPhamArrayList.add(new SanPham(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("tensp"),
                                    jsonObject.getInt("giasp"),
                                    jsonObject.getString("hinhanhsp"),
                                    jsonObject.getString("motasp"),
                                    jsonObject.getInt("idloaisp")
                            ));
                            sanPhamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast(MainActivity.this, error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getDuLieuLoaiSP() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.URLLoaiSP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null) {
                    for(int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            loaiSPArrayList.add(new LoaiSP(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("tenloaisp"),
                                    jsonObject.getString("hinhanhloaisp")));
                            loaiSPAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if(sessionManager.getLoginState()) {
                        loaiSPArrayList.add(0, new LoaiSP(0, "TK: " + sessionManager.getUserName(), "boy.png"));
                        loaiSPArrayList.add(new LoaiSP(0,"Lịch Sử Mua Hàng", "history1.png"));
                        loaiSPArrayList.add(new LoaiSP(0,"Đăng Xuất", "exit.png"));
                    }
                    else {
                        loaiSPArrayList.add(0, new LoaiSP(0,"Đăng Nhập", "password.png"));
                    }
                    loaiSPArrayList.add(1, new LoaiSP(0,"Trang Chủ", "house.png"));
                    loaiSPArrayList.add(response.length() + 2, new LoaiSP(0,"Thông Tin", "information1.png"));


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast(MainActivity.this, error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);

    }
    public void slideShow() {
        slideArrayList = new ArrayList<>();
        slideArrayList = Slide.init();
        viewPager = findViewById(R.id.viewpager);
        slideAdapter = new SlideAdapter(slideArrayList, this);
        viewPager.setAdapter(slideAdapter);

        final Handler handler = new Handler();
        final Runnable run = new Runnable() {
            @Override
            public void run() {
                if (current_position == slideArrayList.size())
                    current_position = 0;
                viewPager.setCurrentItem(current_position++, false);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(run);
            }
        }, 250, 3000);
    }

    public void prepareDots (int slideCurrentPosition) {
        if(dotsLayout.getChildCount() > 0)
            dotsLayout.removeAllViews();
        ImageView dots[] = new ImageView[slideArrayList.size()];

        for(int i = 0; i < slideArrayList.size(); i++) {
            dots[i] = new ImageView(this);
            if(i == slideCurrentPosition)
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dot));
            else
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.inactive_dot));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(4, 0, 4, 0);
            dotsLayout.addView(dots[i], layoutParams);
        }
    }
}

