package com.farport.android.eiskominfo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> listUser;
    private Context mCtx;

    public UserAdapter(Context mCtx, List<User> listUser) {
        this.mCtx = mCtx;
        this.listUser = listUser;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_user, null);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserAdapter.UserViewHolder holder, int position) {
        final User myList = listUser.get(position);

        holder.uNama.setText(myList.getNama_user());
        holder.uNoHP.setText(myList.getNo_hp());
        holder.uEmail.setText(myList.getUsername());
        holder.uJabatanSKPD.setText(myList.getJabatan()+ " " + myList.getSkpd());

        // Intent to Gmail
        holder.btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", myList.getUsername(), null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Executive Dashboard");
                    mCtx.startActivity(Intent.createChooser(emailIntent, null));
                }catch (Throwable e){

                }
            }
        });

        // Intent to WhatsApp
        holder.btnWA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager packageManager = mCtx.getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);

                try {
                    String url = "https://api.whatsapp.com/send?phone=62"+ myList.getNo_hp();
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        mCtx.startActivity(i);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView uNama, uEmail, uJabatanSKPD, uNoHP;
        ImageButton btnWA, btnEmail;

        public UserViewHolder(View itemView) {
            super(itemView);

            uNama = (TextView) itemView.findViewById(R.id.uNama);
            uEmail = (TextView) itemView.findViewById(R.id.uEmail);
            uJabatanSKPD = (TextView) itemView.findViewById(R.id.uJabatanSKPD);
            uNoHP = (TextView) itemView.findViewById(R.id.uNoHP);
            btnWA = (ImageButton) itemView.findViewById(R.id.btnWA);
            btnEmail = (ImageButton) itemView.findViewById(R.id.btnEmail);

        }
    }
}
