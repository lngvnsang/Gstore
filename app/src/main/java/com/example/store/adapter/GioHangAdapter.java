package com.example.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.store.R;
import com.example.store.activity.GioHangActivity;
import com.example.store.activity.MainActivity;
import com.example.store.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> mangGioHang;

    public GioHangAdapter(Context context, ArrayList<GioHang> mangGioHang) {
        this.context = context;
        this.mangGioHang = mangGioHang;
    }

    @Override
    public int getCount() {
        return mangGioHang.size();
    }

    @Override
    public Object getItem(int position) {
        return mangGioHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        TextView txtTenGioHang,txtGiaGioHang;
        ImageView imgHinhGioHang;
        Button btnMinus,btnValues,btnPlus;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_gio_hang,null);
            holder.txtTenGioHang = convertView.findViewById(R.id.textViewTenGioHang);
            holder.txtGiaGioHang = convertView.findViewById(R.id.textViewGiaGioHang);
            holder.btnMinus = convertView.findViewById(R.id.buttonMinus);
            holder.btnValues = convertView.findViewById(R.id.buttonValue);
            holder.btnPlus = convertView.findViewById(R.id.buttonPlus);
            holder.imgHinhGioHang = convertView.findViewById(R.id.imageViewGioHang);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        GioHang gioHang = mangGioHang.get(position);
        holder.txtTenGioHang.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaGioHang.setText("Giá: " + decimalFormat.format(gioHang.getGiasp())+" đ");
        Picasso.with(context).load(gioHang.getHinhsp())
                .placeholder(R.drawable.wait_load)
                .error(R.drawable.error_img)
                .into(holder.imgHinhGioHang);
        holder.btnValues.setText(gioHang.getSoluongsp()+"");

        int sl = Integer.parseInt(holder.btnValues.getText().toString());
        if (sl >= 10){
            holder.btnPlus.setVisibility(View.INVISIBLE);
            holder.btnMinus.setVisibility(View.VISIBLE);
        }else if (sl <=1){
            holder.btnPlus.setVisibility(View.VISIBLE);
            holder.btnMinus.setVisibility(View.INVISIBLE);
        }else if (sl > 1 && sl <10){
            holder.btnPlus.setVisibility(View.VISIBLE);
            holder.btnMinus.setVisibility(View.VISIBLE);
        }
        ViewHolder finalHolder = holder;
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  slupdate = Integer.parseInt(finalHolder.btnValues.getText().toString()) + 1;
                int slht = MainActivity.mangGioHang.get(position).getSoluongsp();
                long gia = MainActivity.mangGioHang.get(position).getGiasp();
                MainActivity.mangGioHang.get(position).setSoluongsp(slupdate);
                long giamoi = (gia * slupdate)/slht;
                MainActivity.mangGioHang.get(position).setGiasp(giamoi);
                finalHolder.btnValues.setText(String.valueOf(slupdate));
                finalHolder.txtGiaGioHang.setText("Giá: " + decimalFormat.format(giamoi) + " đ");
                GioHangActivity.eventUltil();
                if (slupdate > 9){
                    finalHolder.btnPlus.setVisibility(View.INVISIBLE);
                    finalHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalHolder.btnValues.setText(String.valueOf(slupdate));

                }else {
                    finalHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalHolder.btnValues.setText(String.valueOf(slupdate));

                }
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  slupdate = Integer.parseInt(finalHolder.btnValues.getText().toString()) - 1;
                int slht = MainActivity.mangGioHang.get(position).getSoluongsp();
                long gia = MainActivity.mangGioHang.get(position).getGiasp();
                MainActivity.mangGioHang.get(position).setSoluongsp(slupdate);
                long giamoi = (gia * slupdate)/slht;
                MainActivity.mangGioHang.get(position).setGiasp(giamoi);
                finalHolder.btnValues.setText(String.valueOf(slupdate));
                finalHolder.txtGiaGioHang.setText("Giá: " + decimalFormat.format(giamoi) + " đ");
                GioHangActivity.eventUltil();
                if (slupdate < 2){
                    finalHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalHolder.btnMinus.setVisibility(View.INVISIBLE);
                    finalHolder.btnValues.setText(String.valueOf(slupdate));

                }else {
                    finalHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalHolder.btnValues.setText(String.valueOf(slupdate));

                }
            }
        });
        return convertView;
    }
}
