package com.farport.android.eiskominfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class KependudukanPendidikanAdapter extends RecyclerView.Adapter<KependudukanPendidikanAdapter.KependudukanPendidikanViewHolder> {
    private List<Kependudukan> listKependudukan;
    private Context mCtx;

    public KependudukanPendidikanAdapter(Context mCtx, List<Kependudukan> listKependudukan) {
        this.mCtx = mCtx;
        this.listKependudukan = listKependudukan;
    }

    @Override
    public KependudukanPendidikanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_penduduk_pendidikan, null);
        return new KependudukanPendidikanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final KependudukanPendidikanAdapter.KependudukanPendidikanViewHolder holder, int position) {
        final Kependudukan myList = listKependudukan.get(position);

        DecimalFormat df = new DecimalFormat("###,###,###");

        holder.textKecamatan.setText(myList.getKecamatan());
        holder.tTBS.setText(String.valueOf(df.format(myList.getTbs())));
        holder.tBTS.setText(String.valueOf(df.format(myList.getBts())));
        holder.tTTS.setText(String.valueOf(df.format(myList.getTts())));
        holder.tSLTA.setText(String.valueOf(df.format(myList.getSlta())));
        holder.tSLTP.setText(String.valueOf(df.format(myList.getSltp())));
        holder.tD1.setText(String.valueOf(df.format(myList.getD1_d2())));
        holder.tD3.setText(String.valueOf(df.format(myList.getD3())));
        holder.tS1.setText(String.valueOf(df.format(myList.getS1())));
        holder.tS2.setText(String.valueOf(df.format(myList.getS2())));
        holder.tS3.setText(String.valueOf(df.format(myList.getS3())));
    }

    @Override
    public int getItemCount() {
        return listKependudukan.size();
    }

    public class KependudukanPendidikanViewHolder extends RecyclerView.ViewHolder {
        TextView textKecamatan, tTBS, tBTS, tTTS, tSLTP, tSLTA, tD1, tD3, tS1, tS2, tS3, middleP;

        public KependudukanPendidikanViewHolder(View itemView) {
            super(itemView);

            textKecamatan = (TextView) itemView.findViewById(R.id.textKecamatan);
            tTBS = (TextView) itemView.findViewById(R.id.tTBS);
            tBTS = (TextView) itemView.findViewById(R.id.tBTS);
            tTTS = (TextView) itemView.findViewById(R.id.tTTS);
            tSLTP = (TextView) itemView.findViewById(R.id.tSLTP);
            tSLTA = (TextView) itemView.findViewById(R.id.tSLTA);
            tD1 = (TextView) itemView.findViewById(R.id.tD1);
            tD3 = (TextView) itemView.findViewById(R.id.tD3);
            tS1 = (TextView) itemView.findViewById(R.id.tS1);
            tS2 = (TextView) itemView.findViewById(R.id.tS2);
            tS3 = (TextView) itemView.findViewById(R.id.tS3);
            middleP = (TextView) itemView.findViewById(R.id.middleP);
        }
    }
}