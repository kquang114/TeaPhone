package com.example.tea.teaphone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tea.teaphone.ChiTietSanPhamActivity;
import com.example.tea.teaphone.Model.SanPham;
import com.example.tea.teaphone.Model.Server;
import com.example.tea.teaphone.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {

    ArrayList<SanPham> sanPhamArrayList;
    Context context;

    public SanPhamAdapter(ArrayList<SanPham> sanPhamArrayList, Context context) {
        this.sanPhamArrayList = sanPhamArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sanphammoinhat_dong, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        SanPham sanPham = sanPhamArrayList.get(i);
        Picasso.with(context).load(Server.localhostimage + sanPham.getHinhanhSP()).into(itemHolder.imgSPmoinhat);
        itemHolder.txtTenSP.setText(sanPham.getTenSP());
        DecimalFormat df = new DecimalFormat("###,###,###");
        itemHolder.txtTenSP.setMaxLines(1);
        itemHolder.txtTenSP.setEllipsize(TextUtils.TruncateAt.END);
        itemHolder.txtGiaSP.setText(df.format(sanPham.getGiaSP()) + " VNĐ");
    }

    @Override
    public int getItemCount() {
        return sanPhamArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        ImageView imgSPmoinhat;
        TextView txtTenSP, txtGiaSP;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgSPmoinhat = itemView.findViewById(R.id.imgSPmoinhat);
            txtTenSP = itemView.findViewById(R.id.tvtenspmoinhat);
            txtGiaSP = itemView.findViewById(R.id.tvgiaspmoinhat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.putExtra("ChiTietSanPham", sanPhamArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
