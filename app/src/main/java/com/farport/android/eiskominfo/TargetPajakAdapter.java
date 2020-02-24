package com.farport.android.eiskominfo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class TargetPajakAdapter extends RecyclerView.Adapter<TargetPajakAdapter.TargetPajakViewHolder> {
    private List<Pajak> listTargetPajak;
    private Context mCtx;

    public TargetPajakAdapter(Context mCtx, List<Pajak> listTargetPajak) {
        this.mCtx = mCtx;
        this.listTargetPajak = listTargetPajak;
    }

    @Override
    public TargetPajakViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_target_pajak, null);
        return new TargetPajakViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TargetPajakAdapter.TargetPajakViewHolder holder, int position) {
        final Pajak myList = listTargetPajak.get(position);

        DecimalFormat df = new DecimalFormat("#0.00");
        Double pengPj = myList.getTotalpajak()/1E9;
        Double totPj = myList.getTarget()/1E9;
        String id_pajak = myList.getId_jenis_pajak();

        holder.textPj.setText(myList.getJenis_pajak());
        holder.totalPj.setText(String.valueOf(df.format(myList.getTotalpajak()/1E9))+"M");
        holder.targetPj.setText(String.valueOf(df.format(myList.getTarget()/1E9))+"M");
        holder.barPj.setProgress(pengPj.floatValue());
        holder.barPj.setSecondaryProgress(totPj.floatValue());
        holder.barPj.setMax(totPj.floatValue());

        if (id_pajak.equals("01")){
            holder.barPj.setProgressColor(Color.parseColor("#3b8ead"));
            holder.imgPj.setImageResource(R.drawable.pajakhotel);
        }else if (id_pajak.equals("02")){
            holder.barPj.setProgressColor(Color.parseColor("#d22940"));
            holder.imgPj.setImageResource(R.drawable.pajakrestoran);
        }else if (id_pajak.equals("03")){
            holder.barPj.setProgressColor(Color.parseColor("#7CB342"));
            holder.imgPj.setImageResource(R.drawable.pajakhiburan);
        }else if (id_pajak.equals("04")){
            holder.barPj.setProgressColor(Color.parseColor("#9575CD"));
            holder.imgPj.setImageResource(R.drawable.pajakreklame);
        }else if (id_pajak.equals("05")){
            holder.barPj.setProgressColor(Color.parseColor("#ffc20e"));
            holder.imgPj.setImageResource(R.drawable.pajakpeneranganjalan);

        }else if (id_pajak.equals("07")){
            holder.barPj.setProgressColor(Color.parseColor("#EF6C00"));
            holder.imgPj.setImageResource(R.drawable.pajakparkir);

        }else if (id_pajak.equals("08")){
            holder.barPj.setProgressColor(Color.parseColor("#007cbb"));
            holder.imgPj.setImageResource(R.drawable.pajakairtanah);

        }
    }

    @Override
    public int getItemCount() {
        return listTargetPajak.size();
    }

    public class TargetPajakViewHolder extends RecyclerView.ViewHolder {
        TextView textPj, targetPj, totalPj;
        RoundCornerProgressBar barPj;
        ImageView imgPj;

        public TargetPajakViewHolder(View itemView) {
            super(itemView);

            textPj = (TextView) itemView.findViewById(R.id.textPj);
            targetPj = (TextView) itemView.findViewById(R.id.targetPj);
            totalPj = (TextView) itemView.findViewById(R.id.totalPj);
            imgPj = (ImageView) itemView.findViewById(R.id.imgPj);
            barPj = (RoundCornerProgressBar) itemView.findViewById(R.id.barPj);
        }
    }
}
