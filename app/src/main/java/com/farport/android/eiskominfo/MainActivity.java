package com.farport.android.eiskominfo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;

public class MainActivity extends AppCompatActivity {
    PieChart pieChart;
    ImageButton btnRealisasi, btnPajak, btnKepegawaian, btnAnggaran, btnLogout, btnUser, btnKependudukan, btnAddKategori;
    TextView text2;
    public String username;
    public int STORAGE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnKepegawaian = (ImageButton) findViewById(R.id.btnKepegawaian);
        btnPajak = (ImageButton) findViewById(R.id.btnPajak);
        btnRealisasi = (ImageButton) findViewById(R.id.btnRealisasi);
        btnAnggaran = (ImageButton) findViewById(R.id.btnAnggaran);
        btnKependudukan = (ImageButton) findViewById(R.id.btnKependudukan);
        btnAddKategori = (ImageButton) findViewById(R.id.btnAddKategori);
        btnUser = (ImageButton) findViewById(R.id.btnUser);
        btnLogout = (ImageButton) findViewById(R.id.btnLogout);
        text2 = (TextView) findViewById(R.id.text2);

        User user = SharedPrefManager.getInstance(this).getUser();
        text2.setText(user.getNama_user()+"\n"+user.getJabatan()+" - "+user.getSkpd());
        username = user.getUsername();

        btnAddKategori.setVisibility(View.INVISIBLE);

        btnRealisasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, APBDActivity.class);
                startActivity(i);
            }
        });

        btnPajak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PajakActivity.class);
                startActivity(i);
            }
        });

        btnKepegawaian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, KepegawaianActivity.class);
                startActivity(i);
            }
        });

        btnAnggaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AnggaranSKPDActivity.class);
                startActivity(i);
            }
        });

        btnKependudukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, KependudukanActivity.class);
                startActivity(i);
            }
        });

        btnAddKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (MainActivity.this, AddCategoryActivity.class);
                startActivity(i);
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, UserActivity.class);
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(MainActivity.this).logout();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });

        requestStoragePermission();

    }

    private void requestStoragePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to share information")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                                    , Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
