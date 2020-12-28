package com.example.store.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.store.R;
import com.example.store.ultil.CheckConnection;
import com.example.store.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinMuaHangActivity extends AppCompatActivity {

    EditText edtTenKhachHang, edtSDT, edtEmail;
    Button btnXacNhan,btnReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_mua_hang);
        AnhXa();
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            eventButton();
        }else {
            CheckConnection.ShowToast(getApplicationContext(),"Kiểm tra lại kết nối!");
        }
    }

    private void eventButton() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edtTenKhachHang.getText().toString().trim();
                String sdt = edtSDT.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();

                if (ten.length()>0&&sdt.length()>0&&email.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.pathThongTinKhachHang,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (Integer.parseInt(response) > 0){
                                        RequestQueue  queue = Volley.newRequestQueue(getApplicationContext());
                                        StringRequest request = new StringRequest(Request.Method.POST, Server.pathChiTietDonHang,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        if (response.equals("1")){
                                                            MainActivity.mangGioHang.clear();
                                                            Toast.makeText(ThongTinMuaHangActivity.this, "Bạn đã thêm thành công", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(ThongTinMuaHangActivity.this,MainActivity.class);
                                                            startActivity(intent);
                                                            Toast.makeText(ThongTinMuaHangActivity.this, "Mời bạn tiếp tục mua hàng", Toast.LENGTH_SHORT).show();
                                                        }else {
                                                            Toast.makeText(ThongTinMuaHangActivity.this, "Dữ liệu đã bị lỗi!", Toast.LENGTH_SHORT).show();
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
                                                JSONArray jsonArray = new JSONArray();
                                                for (int i = 0; i <MainActivity.mangGioHang.size() ; i++) {
                                                    JSONObject jsonObject = new JSONObject();
                                                    try {
                                                        jsonObject.put("madonhang", response);
                                                        jsonObject.put("masanpham",MainActivity.mangGioHang.get(i).getIdsp());
                                                        jsonObject.put("tensanpham",MainActivity.mangGioHang.get(i).getTensp());
                                                        jsonObject.put("giasanpham",MainActivity.mangGioHang.get(i).getGiasp());
                                                        jsonObject.put("soluongsanpham",MainActivity.mangGioHang.get(i).getSoluongsp());
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    jsonArray.put(jsonObject);
                                                }
                                                HashMap<String,String> hashMap = new HashMap<>();
                                                hashMap.put("json",jsonArray.toString());

                                                return hashMap;
                                            }
                                        };
                                        queue.add(request);
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
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else Toast.makeText(ThongTinMuaHangActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXa() {
        edtTenKhachHang = findViewById(R.id.editTextTenKhachHang);
        edtSDT = findViewById(R.id.editTextSoDienThoai);
        edtEmail = findViewById(R.id.editTextEmail);
        btnXacNhan = findViewById(R.id.buttonXacNhan);
        btnReturn = findViewById(R.id.buttonTroVe);
    }
}