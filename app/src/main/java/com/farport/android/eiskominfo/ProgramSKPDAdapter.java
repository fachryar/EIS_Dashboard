package com.farport.android.eiskominfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class ProgramSKPDAdapter extends RecyclerView.Adapter<ProgramSKPDAdapter.ProgramSKPDViewHolder> {
    private List<AnggaranSKPD> listAnggaranSKPD;
    private Context mCtx;

    public ProgramSKPDAdapter(Context mCtx, List<AnggaranSKPD> listAnggaranSKPD) {
        this.mCtx = mCtx;
        this.listAnggaranSKPD = listAnggaranSKPD;
    }

    @Override
    public ProgramSKPDViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_program_skpd, null);
        return new ProgramSKPDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProgramSKPDAdapter.ProgramSKPDViewHolder holder, int position) {
        final AnggaranSKPD myList = listAnggaranSKPD.get(position);

        DecimalFormat df = new DecimalFormat("###,###,###,###,###,###");
        holder.namaProgram.setText(myList.getProgram());
        holder.isiIndikator.setText(myList.getIndikator());
        holder.isiTarget.setText(myList.getTarget());
        holder.isiDana.setText("Rp. "+String.valueOf(df.format(myList.getDana())));
        holder.noProgram.setText((position+1)+".");
    }

    @Override
    public int getItemCount() {
        return listAnggaranSKPD.size();
    }

    public class ProgramSKPDViewHolder extends RecyclerView.ViewHolder {
        TextView isiIndikator, isiTarget, isiDana, namaProgram, noProgram;

        public ProgramSKPDViewHolder(View itemView) {
            super(itemView);

            isiTarget = (TextView) itemView.findViewById(R.id.isiTarget);
            isiDana = (TextView) itemView.findViewById(R.id.isiDana);
            isiIndikator = (TextView) itemView.findViewById(R.id.isiIndikator);
            namaProgram = (TextView) itemView.findViewById(R.id.namaProgram);
            noProgram = (TextView) itemView.findViewById(R.id.noProgram);

        }
    }
}