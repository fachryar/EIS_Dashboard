package com.farport.android.eiskominfo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.text.DecimalFormat;
import java.util.List;

public class AnggaranRealisasiAdapter extends RecyclerView.Adapter<AnggaranRealisasiAdapter.AnggaranRealisasiViewHolder> {
    private List<APBD> listAnggaranRealisasi;
    private Context mCtx;

    public AnggaranRealisasiAdapter(Context mCtx, List<APBD> listAnggaranRealisasi) {
        this.mCtx = mCtx;
        this.listAnggaranRealisasi = listAnggaranRealisasi;
    }

    @Override
    public AnggaranRealisasiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_anggaran_realisasi, null);
        return new AnggaranRealisasiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnggaranRealisasiAdapter.AnggaranRealisasiViewHolder holder, int position) {
        final APBD myList = listAnggaranRealisasi.get(position);

        DecimalFormat df = new DecimalFormat("##0.00");
        Double anggaran, realisasi;
        String u2;

        if (myList.getU2().equals("Dana Perimbangan")){
            u2 = "DP-";
        } else if (myList.getU2().equals("Lain-lain Pendapatan Daerah yang Sah")){
            u2 = "PSL-";
        } else if (myList.getU2().equals("Pendapatan Asli Daerah")){
            u2 = "PAD-";
        } else if (myList.getU2().equals("Belanja Langsung")){
            u2 = "BL-";
        } else if (myList.getU2().equals("Belanja Tidak Langsung")){
            u2 = "BTL-";
        } else if (myList.getU2().equals("Penerimaan Pembiayaan Daerah")){
            u2 = "01-";
        } else {
            u2 = "02-";
        }

        if (myList.getAnggaran() >= 1E12){
            anggaran = myList.getAnggaran()/1E12;
            holder.textAnggaran.setText(String.valueOf(df.format(anggaran)+"T"));
        } else {
            anggaran = myList.getAnggaran()/1E9;
            holder.textAnggaran.setText(String.valueOf(df.format(anggaran)+"M"));
        }

        if (myList.getRealisasi() >= 1E12){
            realisasi = myList.getRealisasi()/1E12;
            holder.textRealisasi.setText(String.valueOf(df.format(realisasi)+"T"));
        } else {
            realisasi = myList.getRealisasi()/1E9;
            holder.textRealisasi.setText(String.valueOf(df.format(realisasi)+"M"));
        }

        holder.textU2.setText(u2);
        holder.textU3.setText(myList.getU3());
        holder.textPersen.setText(myList.getPersentase());
    }

    @Override
    public int getItemCount() {
        return listAnggaranRealisasi.size();
    }

    public class AnggaranRealisasiViewHolder extends RecyclerView.ViewHolder {
        TextView textU2, textU3, textAnggaran, textRealisasi, textPersen;

        public AnggaranRealisasiViewHolder(View itemView) {
            super(itemView);

            textU2 = (TextView) itemView.findViewById(R.id.textU2);
            textU3 = (TextView) itemView.findViewById(R.id.textU3);
            textAnggaran = (TextView) itemView.findViewById(R.id.textAnggaran);
            textRealisasi = (TextView) itemView.findViewById(R.id.textRealisasi);
            textPersen = (TextView) itemView.findViewById(R.id.textPersen);

        }
    }
}
