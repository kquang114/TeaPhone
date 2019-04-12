package com.example.tea.teaphone.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tea.teaphone.Model.SanPham;
import com.example.tea.teaphone.Model.Server;
import com.example.tea.teaphone.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DanhSachSanPhamAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayList;

    public DanhSachSanPhamAdapter(Context context, ArrayList<SanPham> arrayList) {
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

    class ViewHolder {
        ImageView imgSP;
        TextView txtTenSP, txtGiaSP, txtMoTaSP;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.danhsachsanpham_dong, null);
            viewHolder.imgSP = convertView.findViewById(R.id.imgSP);
            viewHolder.txtTenSP = convertView.findViewById(R.id.tvTenSP);
            viewHolder.txtGiaSP = convertView.findViewById(R.id.tvGiaSP);
            viewHolder.txtMoTaSP = convertView.findViewById(R.id.tvMoTaSP);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SanPham sanPham = arrayList.get(position);
        Picasso.with(context).load(Server.localhostimage + sanPham.getHinhanhSP()).into(viewHolder.imgSP);
        viewHolder.txtTenSP.setText(sanPham.getTenSP());
        DecimalFormat df = new DecimalFormat("###,###,###");
        viewHolder.txtGiaSP.setText(df.format(sanPham.getGiaSP()) + " VNĐ");
        viewHolder.txtMoTaSP.setMaxLines(2);
        viewHolder.txtMoTaSP.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTaSP.setText(sanPham.getMotaSP());
        return convertView;
    }
}
