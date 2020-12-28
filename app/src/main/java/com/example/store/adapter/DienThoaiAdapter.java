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

public class DienThoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> mangSanPham;

    public DienThoaiAdapter(Context context, ArrayList<SanPham> mangSanPham) {
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
    class ViewHolder{
        ImageView imgDT;
        TextView txtTenDT,txtGiaDT,txtMoTaDt;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_dien_thoai,null);
            holder.imgDT = convertView.findViewById(R.id.imageViewDienThoai);
            holder.txtGiaDT = convertView.findViewById(R.id.textViewGiaDienThoai);
            holder.txtMoTaDt = convertView.findViewById(R.id.textViewMoTaDienThoai);
            holder.txtTenDT = convertView.findViewById(R.id.textViewDienThoai);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        SanPham sanPham = mangSanPham.get(position);
        holder.txtTenDT.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaDT.setText("Giá: " + decimalFormat.format(sanPham.getGiaSanPham()) + " đ");
        holder.txtMoTaDt.setText(sanPham.getMoTaSanPham());
        Picasso.with(context).load(sanPham.getHinhSanPham())
                .placeholder(R.drawable.wait_load)
                .error(R.drawable.error_img)
                .into(holder.imgDT);
        return convertView;
    }
}
