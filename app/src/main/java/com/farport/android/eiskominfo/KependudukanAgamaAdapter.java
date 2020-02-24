package com.farport.android.eiskominfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class KependudukanAgamaAdapter extends RecyclerView.Adapter<KependudukanAgamaAdapter.KependudukanAgamaViewHolder> {
    private List<Kependudukan> listKependudukan;
    private Context mCtx;

    public KependudukanAgamaAdapter(Context mCtx, List<Kependudukan> listKependudukan) {
        this.mCtx = mCtx;
        this.listKependudukan = listKependudukan;
    }

    @Override
    public KependudukanAgamaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_penduduk_agama, null);
        return new KependudukanAgamaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final KependudukanAgamaAdapter.KependudukanAgamaViewHolder holder, int position) {
        final Kependudukan myList = listKependudukan.get(position);

        DecimalFormat df = new DecimalFormat("###,###,###");

        holder.textViewAgama.setText(myList.getAgama()+": ");
        holder.jumlahAgama.setText(String.valueOf(df.format(myList.getJumlah_agama())));
    }

    @Override
    public int getItemCount() {
        return listKependudukan.size();
    }

    public class KependudukanAgamaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAgama, jumlahAgama;

        public KependudukanAgamaViewHolder(View itemView) {
            super(itemView);

            textViewAgama = (TextView) itemView.findViewById(R.id.textViewAgama);
            jumlahAgama = (TextView) itemView.findViewById(R.id.jumlahAgama);
        }
    }
}
