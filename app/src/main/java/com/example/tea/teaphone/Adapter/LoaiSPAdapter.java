package com.example.tea.teaphone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tea.teaphone.Model.LoaiSP;
import com.example.tea.teaphone.Model.Server;
import com.example.tea.teaphone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSPAdapter extends BaseAdapter {
    ArrayList<LoaiSP> arrayLoaiSP;
    Context context;

    public LoaiSPAdapter(ArrayList<LoaiSP> arrayLoaiSP, Context context) {
        this.arrayLoaiSP = arrayLoaiSP;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayLoaiSP.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayLoaiSP.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        ImageView menuImage;
        TextView menuTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu_listview_row, null);
            viewHolder.menuImage = convertView.findViewById(R.id.menuimages);
            viewHolder.menuTitle = convertView.findViewById(R.id.menutitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LoaiSP loaiSP = (LoaiSP) getItem(position);
        Picasso.with(context).load(Server.localhostimage + loaiSP.getHinhanhLoaiSP()).into(viewHolder.menuImage);
        viewHolder.menuTitle.setText(loaiSP.getTenLoaiSP());
        return convertView;
    }
}