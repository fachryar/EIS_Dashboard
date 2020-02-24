package com.farport.android.eiskominfo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class AnggaranSKPDAdapter extends RecyclerView.Adapter<AnggaranSKPDAdapter.AnggaranSKPDViewHolder> {
    private List<AnggaranSKPD> listAnggaranSKPD;
    private Context mCtx;

    public AnggaranSKPDAdapter(Context mCtx, List<AnggaranSKPD> listAnggaranSKPD) {
        this.mCtx = mCtx;
        this.listAnggaranSKPD = listAnggaranSKPD;
    }

    @Override
    public AnggaranSKPDViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_anggaran_skpd, null);
        return new AnggaranSKPDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnggaranSKPDAdapter.AnggaranSKPDViewHolder holder, int position) {
        final AnggaranSKPD myList = listAnggaranSKPD.get(position);

        DecimalFormat df = new DecimalFormat("##0.00");
        holder.valNo.setText(String.valueOf(position+1));
        holder.valTipe.setText(myList.getKategori());

        if (myList.getTotal() >= 1E12){
            holder.valTotal.setText(String.valueOf(df.format(myList.getTotal()/1E12))+"T");
        }else{
            holder.valTotal.setText(String.valueOf(df.format(myList.getTotal()/1E9))+"M");
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCtx, DetailAnggaranActivity.class);
                i.putExtra("myList", myList);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listAnggaranSKPD.size();
    }

    public class AnggaranSKPDViewHolder extends RecyclerView.ViewHolder {
        TextView valNo, valTipe, valTotal;
        RelativeLayout relativeLayout;

        public AnggaranSKPDViewHolder(View itemView) {
            super(itemView);

            valNo = (TextView) itemView.findViewById(R.id.valNo);
            valTipe = (TextView) itemView.findViewById(R.id.valTipe);
            valTotal = (TextView) itemView.findViewById(R.id.valTotal);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);

        }
    }
}

