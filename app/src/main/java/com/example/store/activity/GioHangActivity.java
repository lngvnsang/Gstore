package com.example.store.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.store.R;
import com.example.store.adapter.GioHangAdapter;
import com.example.store.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {

    Toolbar toolbarGioHang;
    ListView lvGioHang;
    TextView txtThongBao;
    static TextView txtTongTien;
    Button btnThanhToan,btnTiepTucMua;
    GioHangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        AnhXa();
        checkData();
        eventUltil();
        catchOnItemListView();
        eventButton();
    }

    private void eventButton() {
        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GioHangActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mangGioHang.size()>0){
                    Intent intent = new Intent(GioHangActivity.this,ThongTinMuaHangActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(GioHangActivity.this, "Không có mặc hàng để mua!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void catchOnItemListView() {
        lvGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chác muốn xóa sản phẩm khỏi giỏ hàng!");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.mangGioHang.size() <= 0){
                            txtThongBao.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.mangGioHang.remove(position);
                            adapter.notifyDataSetChanged();
                            eventUltil();
                            if (MainActivity.mangGioHang.size() <= 0){
                                txtThongBao.setVisibility(View.VISIBLE);
                            }else {
                                txtThongBao.setVisibility(View.INVISIBLE);
                                adapter.notifyDataSetChanged();
                                eventUltil();
                            }
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

    public static void eventUltil() {
        long tongTien = 0;
        for (int i = 0; i < MainActivity.mangGioHang.size(); i++) {
            tongTien+=MainActivity.mangGioHang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText(decimalFormat.format(tongTien)+"đ");
    }

    private void checkData() {
        if (MainActivity.mangGioHang.size() <=0){
            adapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.VISIBLE);
            lvGioHang.setVisibility(View.INVISIBLE);
        }else {
            adapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.INVISIBLE);
            lvGioHang.setVisibility(View.VISIBLE);
        }
    }

    private void AnhXa() {
        lvGioHang = findViewById(R.id.listViewGioHang);
        toolbarGioHang = findViewById(R.id.toolBarGioHang);
        txtThongBao = findViewById(R.id.textViewThongBao);
        txtTongTien =findViewById(R.id.textViewTongTien);
        btnThanhToan = findViewById(R.id.buttonThanhToanGioHang);
        btnTiepTucMua = findViewById(R.id.buttonTiepTucMuaHang);
        adapter = new GioHangAdapter(getApplicationContext(),MainActivity.mangGioHang);
        lvGioHang.setAdapter(adapter);
        setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}