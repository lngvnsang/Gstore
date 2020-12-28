package com.example.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.store.R;
import com.example.store.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> mangLapTop;

    public LaptopAdapter(Context context, ArrayList<SanPham> mangLapTop) {
        this.context = context;
        this.mangLapTop = mangLapTop;
    }

    @Override
    public int getCount() {
        return mangLapTop.size();
    }

    @Override
    public Object getItem(int position) {
        return mangLapTop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder{
        ImageView imgLaptop;
        TextView txtTenLaptop,txtGiaLaptop,txtMoTaLaptop;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_laptop,null);
            holder.imgLaptop = convertView.findViewById(R.id.imageViewLaptop);
            holder.txtGiaLaptop = convertView.findViewById(R.id.textViewGiaLaptop);
            holder.txtMoTaLaptop = convertView.findViewById(R.id.textViewMoTaLaptop);
            holder.txtTenLaptop = convertView.findViewById(R.id.textViewLaptop);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        SanPham sanPham = mangLapTop.get(position);
        holder.txtTenLaptop.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaLaptop.setText("Giá: " + decimalFormat.format(sanPham.getGiaSanPham()) + " đ");
        holder.txtMoTaLaptop.setText(sanPham.getMoTaSanPham());
        Picasso.with(context).load(sanPham.getHinhSanPham())
                .placeholder(R.drawable.wait_load)
                .error(R.drawable.error_img)
                .into(holder.imgLaptop);
        return convertView;
    }
}
