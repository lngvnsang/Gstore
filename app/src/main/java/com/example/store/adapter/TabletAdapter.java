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

public class TabletAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> mangSanPham;

    public TabletAdapter(Context context, ArrayList<SanPham> mangSanPham) {
        this.context = context;
        this.mangSanPham = mangSanPham;
    }

    @Override
    public int getCount() {
        return mangSanPham.size();
    }

    @Override
    public Object getItem(int position) {
        return mangSanPham.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class  ViewHolder {
        TextView txtTablet,txtGiaTablet,txtMoTaTablet;
        ImageView imgTablet;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_tablet,null);

            holder.imgTablet = convertView.findViewById(R.id.imageViewTablet);
            holder.txtTablet = convertView.findViewById(R.id.textViewTablet);
            holder.txtGiaTablet = convertView.findViewById(R.id.textViewGiaTablet);
            holder.txtMoTaTablet = convertView.findViewById(R.id.textViewMoTatablet);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        SanPham sanPham = (SanPham) getItem(position);
        holder.txtMoTaTablet.setText(sanPham.getMoTaSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaTablet.setText("Giá: " + decimalFormat.format(sanPham.getGiaSanPham()) + " đ");
        holder.txtTablet.setText(sanPham.getTenSanPham());
        Picasso.with(context).load(sanPham.getHinhSanPham())
                .placeholder(R.drawable.wait_load)
                .error(R.drawable.error_img)
                .into(holder.imgTablet);
        return convertView;
    }
}
