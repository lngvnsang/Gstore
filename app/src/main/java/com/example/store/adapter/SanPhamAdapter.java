package com.example.store.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store.R;
import com.example.store.activity.ChiTietSanPhamActivity;
import com.example.store.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    Context context;
    ArrayList<SanPham> mangSanPham;

    public SanPhamAdapter(Context context, ArrayList<SanPham> mangSanPham) {
        this.context = context;
        this.mangSanPham = mangSanPham;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_san_pham_moi,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = mangSanPham.get(position);
        holder.txtTenSp.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        holder.txtGiaSp.setText("Giá: "+ decimalFormat.format(sanPham.getGiaSanPham()) + " đ");
        Log.d("AAA",sanPham.getHinhSanPham());
        Picasso.with(context).load(sanPham.getHinhSanPham())
                .placeholder(R.drawable.wait_load)
                .error(R.drawable.error_img)
                .into(holder.imgHinhSP);
    }

    @Override
    public int getItemCount() {
        return mangSanPham.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinhSP;
        TextView txtTenSp,txtGiaSp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhSP = itemView.findViewById(R.id.imageViewHinhSanPham);
            txtGiaSp = itemView.findViewById(R.id.textViewGiaSanPham);
            txtTenSp = itemView.findViewById(R.id.textViewTenSanPham);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.putExtra("thongtinsanpham",mangSanPham.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
