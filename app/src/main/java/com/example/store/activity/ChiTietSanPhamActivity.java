package com.example.store.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.store.R;
import com.example.store.model.GioHang;
import com.example.store.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    Toolbar toolbarChiTiet;
    ImageView imgChiTiet;
    TextView txtTen,txtGia,txtMoTa;
    Spinner spinner;
    Button btnThemGioHang;
    SanPham sanPham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        AnhXa();
        getInfo();
        catchEventSpinner();
        eventButton();
    }

    private void eventButton() {
        btnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mangGioHang.size() > 0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exist = false;
                    for (int i = 0; i < MainActivity.mangGioHang.size(); i++) {
                        if (MainActivity.mangGioHang.get(i).getIdsp() == sanPham.getId()){
                            MainActivity.mangGioHang.get(i).setSoluongsp(MainActivity.mangGioHang.get(i).getSoluongsp() + sl);
                            if (MainActivity.mangGioHang.get(i).getSoluongsp() > 10){
                                MainActivity.mangGioHang.get(i).setSoluongsp(10);
                            }
                            MainActivity.mangGioHang.get(i).setGiasp(sanPham.getGiaSanPham() * MainActivity.mangGioHang.get(i).getSoluongsp());
                            exist = true;
                        }
                    }
                    if (exist == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long gia = soluong * sanPham.getGiaSanPham();
                        MainActivity.mangGioHang.add(new GioHang(sanPham.getId(),sanPham.getTenSanPham(),gia,sanPham.getHinhSanPham(),soluong));
                    }
                }else {
                     int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                     long gia = soluong * sanPham.getGiaSanPham();
                     MainActivity.mangGioHang.add(new GioHang(sanPham.getId(),sanPham.getTenSanPham(),gia,sanPham.getHinhSanPham(),soluong));
                }
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
            }
        });
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

    private void catchEventSpinner() {
        Integer[] soLuong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,soLuong);
        spinner.setAdapter(arrayAdapter);
    }

    private void getInfo() {
        sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        txtTen.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGia.setText("Giá: " + decimalFormat.format(sanPham.getGiaSanPham()) + " đ");
        txtMoTa.setText(sanPham.getMoTaSanPham());
        Picasso.with(getApplicationContext()).load(sanPham.getHinhSanPham())
                .placeholder(R.drawable.wait_load)
                .error(R.drawable.error_img)
                .into(imgChiTiet);
    }

    private void AnhXa() {
        toolbarChiTiet = findViewById(R.id.toolBarChiTietSanPham);
        imgChiTiet = findViewById(R.id.imageViewChiTietSanPham);
        txtTen = findViewById(R.id.textViewTenChiTetSanPham);
        txtGia = findViewById(R.id.textViewGiaChiTetSanPham);
        txtMoTa = findViewById(R.id.textViewMoTaChiTietSanPham);
        spinner = findViewById(R.id.spinner);
        btnThemGioHang = findViewById(R.id.buttonThemGioHang);

        setSupportActionBar(toolbarChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}