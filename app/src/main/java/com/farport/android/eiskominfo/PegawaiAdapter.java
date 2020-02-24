package com.farport.android.eiskominfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class PegawaiAdapter extends RecyclerView.Adapter<PegawaiAdapter.PegawaiViewHolder> {
    private List<Pegawai> listPegawai;
    private Context mCtx;

    public PegawaiAdapter(Context mCtx, List<Pegawai> listPegawai) {
        this.mCtx = mCtx;
        this.listPegawai = listPegawai;
    }

    @Override
    public PegawaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_pegawai, null);
        return new PegawaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PegawaiAdapter.PegawaiViewHolder holder, int position) {
        final Pegawai myList = listPegawai.get(position);

        holder.tpNama.setText(myList.getNama());
        holder.isiNIP.setText(myList.getNip());
        holder.isiUnit.setText(myList.getUnit_kerja());
        holder.isiGolonganPangkat.setText(myList.getGolongan()+ "\n" +myList.getPangkat());
        holder.isiStatus.setText(myList.getStatus_pegawai());
        holder.isiKelamin.setText(myList.getJenis_kelamin());
        holder.isiUsia.setText(String.valueOf(myList.getUsia()));
        holder.isiKedudukan.setText(myList.getKedudukan());
//        holder.valNo.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return listPegawai.size();
    }

    public class PegawaiViewHolder extends RecyclerView.ViewHolder {
        TextView isiNIP, isiUnit, isiKelamin, isiStatus, isiKedudukan, isiUsia, isiGolonganPangkat, tpNama;

        public PegawaiViewHolder(View itemView) {
            super(itemView);

            tpNama = (TextView) itemView.findViewById(R.id.tpNama);
            isiNIP = (TextView) itemView.findViewById(R.id.isiNIP);
            isiKelamin = (TextView) itemView.findViewById(R.id.isiKelamin);
            isiKedudukan = (TextView) itemView.findViewById(R.id.isiKedudukan);
            isiStatus = (TextView) itemView.findViewById(R.id.isiStatus);
            isiGolonganPangkat = (TextView) itemView.findViewById(R.id.isiPangkatGolongan);
            isiUnit = (TextView) itemView.findViewById(R.id.isiUnit);
            isiUsia = (TextView) itemView.findViewById(R.id.isiUsia);

        }
    }
}
