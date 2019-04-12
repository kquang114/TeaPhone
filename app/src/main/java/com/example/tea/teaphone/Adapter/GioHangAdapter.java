package com.example.tea.teaphone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tea.teaphone.GioHangActivity;
import com.example.tea.teaphone.MainActivity;
import com.example.tea.teaphone.Model.GioHang;
import com.example.tea.teaphone.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> arrayList;

    public GioHangAdapter(Context context, ArrayList<GioHang> arrayList) {
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

    public class ViewHolder {
        ImageView imgSPGioHang;
        TextView txtTenGH, txtGiaSP, txtTongGiaSP, txtSoLuongSP;
        Button btnMinus, btnPlus;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.giohang_dong, null);
            viewHolder.imgSPGioHang = convertView.findViewById(R.id.imgSPGioHang);
            viewHolder.txtTenGH = convertView.findViewById(R.id.tvTenGioHang);
            viewHolder.txtGiaSP = convertView.findViewById(R.id.tvGiaGioHang);
            viewHolder.txtTongGiaSP = convertView.findViewById(R.id.tvTongGiaSP);
            viewHolder.txtSoLuongSP = convertView.findViewById(R.id.soluongSP);
            viewHolder.btnMinus = convertView.findViewById(R.id.btnminus);
            viewHolder.btnPlus = convertView.findViewById(R.id.btnplus);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GioHang gioHang = arrayList.get(position);
        Picasso.with(context).load(gioHang.getHinhAnhSP()).into(viewHolder.imgSPGioHang);
        viewHolder.txtTenGH.setText(gioHang.getTenSP());
        DecimalFormat df = new DecimalFormat("###,###,###");
        viewHolder.txtGiaSP.setText("Giá: " + df.format(gioHang.getGiaSP()) + " VNĐ/1");
        viewHolder.txtTongGiaSP.setText("Tổng: " + df.format(gioHang.getTongGiaSP()) + "VNĐ");
        viewHolder.txtSoLuongSP.setText(gioHang.getSoLuong() + "");

        int sl = Integer.parseInt(viewHolder.txtSoLuongSP.getText().toString());
        if(sl == 1) {
            viewHolder.btnMinus.setVisibility(View.INVISIBLE);
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
        }
        else if(sl < 10) {
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.btnPlus.setVisibility(View.INVISIBLE);
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoi = arrayList.get(position).getSoLuong() + 1;
                MainActivity.gioHangArrayList.get(position).setSoLuong(slmoi);
                finalViewHolder.txtSoLuongSP.setText(String.valueOf(slmoi));
                MainActivity.gioHangArrayList.get(position).setTongGiaSP(arrayList.get(position).getTongGiaSP() + arrayList.get(position).getGiaSP());
                DecimalFormat df = new DecimalFormat("###,###,###");
                finalViewHolder.txtTongGiaSP.setText("Tổng: " + df.format(MainActivity.gioHangArrayList.get(position).getTongGiaSP()) + "VNĐ");
                GioHangActivity.GetTongTien();
                if(slmoi > 9) {
                    finalViewHolder.btnPlus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                }
                else {
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                }
            }
        });

        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoi = arrayList.get(position).getSoLuong() - 1;
                MainActivity.gioHangArrayList.get(position).setSoLuong(slmoi);
                finalViewHolder.txtSoLuongSP.setText(String.valueOf(slmoi));
                MainActivity.gioHangArrayList.get(position).setTongGiaSP(arrayList.get(position).getTongGiaSP() - arrayList.get(position).getGiaSP());
                DecimalFormat df = new DecimalFormat("###,###,###");
                finalViewHolder.txtTongGiaSP.setText("Tổng: " + df.format(MainActivity.gioHangArrayList.get(position).getTongGiaSP()) + "VNĐ");
                GioHangActivity.GetTongTien();
                if(slmoi < 2) {
                    finalViewHolder.btnMinus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                }
                else {
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                }
            }
        });
        return convertView;
    }
}
