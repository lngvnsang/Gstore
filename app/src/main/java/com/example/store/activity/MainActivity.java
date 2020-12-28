package com.example.store.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.store.R;
import com.example.store.adapter.LoaiSpAdapter;
import com.example.store.adapter.SanPhamAdapter;
import com.example.store.model.GioHang;
import com.example.store.model.Loaisp;
import com.example.store.model.SanPham;
import com.example.store.ultil.CheckConnection;
import com.example.store.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewTrangChu;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangLoaiSp;
    LoaiSpAdapter adapter;

    SanPhamAdapter adapterSP;
    ArrayList<SanPham> mangSanPham;
    int id = 0;
    String tenloaisp = "";
    String hinhanhloaisp = "";

    public static ArrayList<GioHang> mangGioHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFliper();
            getDataLoaiSp();
            getDataSanPhamMoi();
            catchOnItemListView();
        }else {
            CheckConnection.ShowToast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối internet!");
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gio_hang,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menugiohang){
            Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void catchOnItemListView() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else
                            CheckConnection.ShowToast(getApplicationContext(),"Kiểm tra lại kết nối!");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham",mangLoaiSp.get(position).getId());
                            startActivity(intent);
                        }else
                            CheckConnection.ShowToast(getApplicationContext(),"Kiểm tra lại kết nối!");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LaptopActivity.class);
                            intent.putExtra("idloaisanpham",mangLoaiSp.get(position).getId());
                            startActivity(intent);
                        }else
                            CheckConnection.ShowToast(getApplicationContext(),"Kiểm tra lại kết nối!");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, TabletActivity.class);
                            intent.putExtra("idloaisanpham",mangLoaiSp.get(position).getId());
                            startActivity(intent);
                        }else
                            CheckConnection.ShowToast(getApplicationContext(),"Kiểm tra lại kết nối!");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LienHeActivity.class);
                            startActivity(intent);
                        }else
                            CheckConnection.ShowToast(getApplicationContext(),"Kiểm tra lại kết nối!");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ThongTinActivity.class);
                            startActivity(intent);
                        }else
                            CheckConnection.ShowToast(getApplicationContext(),"Kiểm tra lại kết nối!");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
            }
        });
    }

    private void getDataSanPhamMoi() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.pathSanPham,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null){
                            int ID = 0;
                            String tenSanPham = "";
                            Integer giaSanPham = 0;
                            String hinhAnhSanPham = "";
                            String moTaSanPham = "";
                            int idSanPham = 0;
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    ID = jsonObject.getInt("id");
                                    tenSanPham = jsonObject.getString("tensp");
                                    giaSanPham = jsonObject.getInt("giasp");
                                    hinhAnhSanPham = jsonObject.getString("hinhanhsp");
                                    moTaSanPham = jsonObject.getString("motasp");
                                    idSanPham = jsonObject.getInt("idsanpham");
                                    mangSanPham.add(new SanPham(ID,tenSanPham,giaSanPham,hinhAnhSanPham,moTaSanPham,idSanPham));
                                    adapterSP.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void getDataLoaiSp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.pathLoaiSp,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null){
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    id = jsonObject.getInt("id");
                                    tenloaisp = jsonObject.getString("tenloaisp");
                                    hinhanhloaisp = jsonObject.getString("hinhloaisp");
                                    mangLoaiSp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            mangLoaiSp.add(new Loaisp(4,"Liên hệ","https://www.freeiconspng.com/uploads/contact-icons-png-15.png"));
                            mangLoaiSp.add(new Loaisp(5,"Thông tin","https://upload.wikimedia.org/wikipedia/commons/thumb/f/f6/Lol_question_mark.png/242px-Lol_question_mark.png"));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CheckConnection.ShowToast(getApplicationContext(),error.toString());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFliper() {
        ArrayList<String> mangQuangCao = new ArrayList<>();
        mangQuangCao.add("https://cdn.tgdd.vn/2020/10/banner/800-170-800x170-56.png");
        mangQuangCao.add("https://cdn.tgdd.vn/2020/10/banner/A93-800-170-800x170.png");
        mangQuangCao.add("https://cdn.tgdd.vn/2020/11/banner/800-170-800x170-25.png");
        mangQuangCao.add("https://cdn.tgdd.vn/2020/11/banner/800-170-800x170-31.png");
        mangQuangCao.add("https://cdn.tgdd.vn/2020/11/banner/800-170-800x170-2.png");
        for (int i = 0; i < mangQuangCao.size() ; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarTrangChu);
        viewFlipper = findViewById(R.id.viewFliper);
        recyclerViewTrangChu = findViewById(R.id.recyclerView);
        navigationView = findViewById(R.id.navigationView);
        listViewManHinhChinh = findViewById(R.id.listViewTrangChu);
        drawerLayout = findViewById(R.id.drawerLayout);
        mangLoaiSp = new ArrayList<>();
        mangLoaiSp.add(new Loaisp(0,"Trang chủ", "https://cdn.pixabay.com/photo/2015/12/28/02/58/home-1110868_960_720.png"));
        adapter= new LoaiSpAdapter(MainActivity.this,mangLoaiSp);
        listViewManHinhChinh.setAdapter(adapter);

        mangSanPham = new ArrayList<>();
        adapterSP = new SanPhamAdapter(MainActivity.this,mangSanPham);
        recyclerViewTrangChu.setHasFixedSize(true);
        recyclerViewTrangChu.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewTrangChu.setAdapter(adapterSP);
        if (mangGioHang == null){
            mangGioHang = new ArrayList<>();
        }
    }
}