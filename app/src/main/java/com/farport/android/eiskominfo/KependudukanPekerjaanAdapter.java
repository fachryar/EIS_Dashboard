package com.farport.android.eiskominfo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class KependudukanPekerjaanAdapter extends RecyclerView.Adapter<KependudukanPekerjaanAdapter.KependudukanPekerjaanViewHolder> {
    private List<Kependudukan> listKependudukan;
    private Context mCtx;

    public KependudukanPekerjaanAdapter(Context mCtx, List<Kependudukan> listKependudukan) {
        this.mCtx = mCtx;
        this.listKependudukan = listKependudukan;
    }

    @Override
    public KependudukanPekerjaanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_penduduk_pekerjaan, null);
        return new KependudukanPekerjaanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final KependudukanPekerjaanAdapter.KependudukanPekerjaanViewHolder holder, int position) {
        final Kependudukan myList = listKependudukan.get(position);

        DecimalFormat df = new DecimalFormat("###,###,###");

        holder.textPekerjaan.setText(myList.getPekerjaan());
        holder.textJumlah.setText(String.valueOf(df.format(myList.getJumlah_pekerjaan())));
    }

    @Override
    public int getItemCount() {
        return listKependudukan.size();
    }

    public class KependudukanPekerjaanViewHolder extends RecyclerView.ViewHolder {
        TextView textPekerjaan, textJumlah;

        public KependudukanPekerjaanViewHolder(View itemView) {
            super(itemView);

            textPekerjaan = (TextView) itemView.findViewById(R.id.textPekerjaan);
            textJumlah = (TextView) itemView.findViewById(R.id.textJumlah);
        }
    }


}
