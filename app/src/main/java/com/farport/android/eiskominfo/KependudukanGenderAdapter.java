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

public class KependudukanGenderAdapter extends RecyclerView.Adapter<KependudukanGenderAdapter.KependudukanGenderViewHolder> {
    private List<Kependudukan> listKependudukan;
    private Context mCtx;

    public KependudukanGenderAdapter(Context mCtx, List<Kependudukan> listKependudukan) {
        this.mCtx = mCtx;
        this.listKependudukan = listKependudukan;
    }

    @Override
    public KependudukanGenderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_penduduk_gender, null);
        return new KependudukanGenderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final KependudukanGenderAdapter.KependudukanGenderViewHolder holder, int position) {
        final Kependudukan myList = listKependudukan.get(position);

        DecimalFormat df = new DecimalFormat("###,###,###");

        holder.genderKecamatan.setText(myList.getKecamatan());
        holder.genderPria.setText(String.valueOf(df.format(myList.getJumlah_pria())));
        holder.genderWanita.setText(String.valueOf(df.format(myList.getJumlah_wanita())));
        holder.genderTotal.setText(String.valueOf(df.format(myList.getTotal_jumlah())));
    }

    @Override
    public int getItemCount() {
        return listKependudukan.size();
    }

    public class KependudukanGenderViewHolder extends RecyclerView.ViewHolder {
        TextView genderKecamatan, genderPria, genderWanita, genderTotal;

        public KependudukanGenderViewHolder(View itemView) {
            super(itemView);

            genderKecamatan = (TextView) itemView.findViewById(R.id.genderKecamatan);
            genderPria = (TextView) itemView.findViewById(R.id.genderPria);
            genderWanita = (TextView) itemView.findViewById(R.id.genderWanita);
            genderTotal = (TextView) itemView.findViewById(R.id.genderTotal);
        }
    }
}