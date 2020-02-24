package com.farport.android.eiskominfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.KategoriViewHolder> {
    private List<Kategori> listKategori;
    private Context mCtx;

    public KategoriAdapter(Context mCtx, List<Kategori> listKategori) {
        this.mCtx = mCtx;
        this.listKategori = listKategori;
    }

    @Override
    public KategoriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_menu, null);
        return new KategoriViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final KategoriAdapter.KategoriViewHolder holder, int position) {
        final Kategori myList = listKategori.get(position);

        holder.noId.setText(String.valueOf(myList.getId_kategori())+".");
        holder.namaMenu.setText(myList.getNama_kategori());

        if(myList.getId_kategori() > 5 && myList.getStatus() == 0){
            holder.btnAddMenu.setVisibility(View.VISIBLE);
            holder.btnAddMenu.setImageResource(R.drawable.selusia2);
            holder.namaMenu.setAlpha(0.3f);
        } else if (myList.getId_kategori() > 5 && myList.getStatus() == 1){
            holder.btnDeleteMenu.setVisibility(View.VISIBLE);
            holder.btnDeleteMenu.setImageResource(R.drawable.selusia1);
        }

        holder.btnAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCtx, FormAddCategoryActivity.class);
                mCtx.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listKategori.size();
    }

    public class KategoriViewHolder extends RecyclerView.ViewHolder {
        TextView noId, namaMenu;
        ImageButton btnAddMenu, btnEditMenu, btnDeleteMenu;

        public KategoriViewHolder(View itemView) {
            super(itemView);

            noId = (TextView) itemView.findViewById(R.id.noId);
            namaMenu = (TextView) itemView.findViewById(R.id.namaMenu);
            btnAddMenu = (ImageButton) itemView.findViewById(R.id.btnAddMenu);
            btnEditMenu = (ImageButton) itemView.findViewById(R.id.btnEditMenu);
            btnDeleteMenu = (ImageButton) itemView.findViewById(R.id.btnDeleteMenu);

        }
    }
}
