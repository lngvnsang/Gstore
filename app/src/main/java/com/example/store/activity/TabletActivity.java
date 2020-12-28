package com.example.store.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.store.R;
import com.example.store.adapter.DienThoaiAdapter;
import com.example.store.adapter.LaptopAdapter;
import com.example.store.adapter.TabletAdapter;
import com.example.store.model.SanPham;
import com.example.store.ultil.CheckConnection;
import com.example.store.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TabletActivity extends AppCompatActivity {

    Toolbar toolbarTablet;
    ListView listViewTablet;
    TabletAdapter adapter;
    ArrayList<SanPham> mangSanPham;
    int idTablet = 0;
    int page = 1;

    View footerView;
    boolean isLoading = false ;
    boolean limitData = false;
    myHandler myHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        toolbarTablet = findViewById(R.id.toolBarTablet);
        setSupportActionBar(toolbarTablet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTablet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listViewTablet =findViewById(R.id.listViewTablet);
        mangSanPham = new ArrayList<>();
        adapter = new TabletAdapter(getApplicationContext(),mangSanPham);
        listViewTablet.setAdapter(adapter);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.processbar,null);

        myHandler = new myHandler();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            getIdDT();
            getDataDt(page);
            loadMoreData();
        }else {
            CheckConnection.ShowToast(getApplicationContext(),"Kiểm tra lại kết nối!");
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

    private void loadMoreData() {
        listViewTablet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TabletActivity.this,ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsanpham",mangSanPham.get(position));
                startActivity(intent);
            }
        });
        listViewTablet.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false && limitData == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void getDataDt(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.pathSanPhamDienThoai+"?page="+page,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int id = 0;
                        String tenTablet = "";
                        int giaTablet = 0;
                        String hinhAnhTablet = "";
                        String moTaTablet = "";
                        int idloaisp = 0;
                        if (response != null && response.length() != 2){
                            listViewTablet.removeFooterView(footerView);
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    id = jsonObject.getInt("id");
                                    tenTablet = jsonObject.getString("tensp");
                                    giaTablet = jsonObject.getInt("giasp");
                                    hinhAnhTablet = jsonObject.getString("hinhanhsp");
                                    moTaTablet = jsonObject.getString("motasp");
                                    idloaisp = jsonObject.getInt("idsanpham");
                                    mangSanPham.add(new SanPham(id,tenTablet,giaTablet,hinhAnhTablet,moTaTablet,idloaisp));
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            limitData = true;
                            listViewTablet.removeFooterView(footerView);
                            Toast.makeText(getApplicationContext(), "Đã hết dữ liệu!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("idsanpham",String.valueOf(idTablet));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void getIdDT() {
        idTablet = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("AAA",idTablet + "");
    }
    public class myHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listViewTablet.addFooterView(footerView);
                    break;
                case 1:
                    getDataDt(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends  Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = myHandler.obtainMessage(1);
            myHandler.sendMessage(message);
            super.run();
        }
    }
}