package com.example.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.store.R;
import com.example.store.model.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSpAdapter extends BaseAdapter {
    Context context;
    ArrayList<Loaisp> arrayList;

    public LoaiSpAdapter(Context context, ArrayList<Loaisp> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {

        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        TextView txtTenLoaiSp;
        ImageView imgLoaiSp;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_loai_sp,null);
            holder.txtTenLoaiSp = convertView.findViewById(R.id.textViewTenLoaiSp);
            holder.imgLoaiSp = convertView.findViewById(R.id.imageViewLoaiSp);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Loaisp loaisp = (Loaisp) getItem(position);
        holder.txtTenLoaiSp.setText(loaisp.getTenloaisanpham());
        Picasso.with(context).load(loaisp.getHinhanhloaisanpham())
                .placeholder(R.drawable.wait_load)
                .error(R.drawable.error_img)
                .into(holder.imgLoaiSp);
        return convertView;
    }
}
