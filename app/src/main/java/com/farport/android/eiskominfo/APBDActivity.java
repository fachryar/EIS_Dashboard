package com.farport.android.eiskominfo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class APBDActivity extends AppCompatActivity {
    // variable untuk screenshot dan share diagram
    private String sharePath="no";

    BarChart barChartAPBD1, barChartAPBD2;
    TextView textPersen, textValuePersenPendapatan, textValuePersenBelanja, textValuePersenPembiayaan,
            textPendapatan, textBelanja, textPembiayaan,textTahunApbd, text11, text12;
    ImageButton btnLeftApbd, btnRightApbd, btnPendapatan1, btnBelanja1, btnPembiayaan1, shareRealisasi1, shareRealisasi2;
    ImageView ssRealisasi;
    LinearLayout mainLinear;
    CardView cardView1, cardView2, cardTitleRealisasi;
    ProgressBar pbRealisasi1, pbRealisasi2, pbRealisasi3;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<APBD> listAnggaranRealisasi;

    // variable yang digunakan dalam link request GET pada API
    String kat1 = "PEND";
    String kat2 = "PEMB";
    String kat3 = "BEL";
    String kategori = kat1;

    int tahun = Calendar.getInstance().get(Calendar.YEAR);
    int tahunini = Calendar.getInstance().get(Calendar.YEAR);
    int[] blueYellow = new int[]{
            Color.parseColor("#3b8ead"),
            Color.parseColor("#ffc20e")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apbd);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewAnggaranRealisasi);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listAnggaranRealisasi = new ArrayList<>();
        
        barChartAPBD1 = (BarChart) findViewById(R.id.barChartAPBD1);
        barChartAPBD2 = (BarChart) findViewById(R.id.barChartAPBD2);
        textPersen = (TextView) findViewById(R.id.textPersen);
        textValuePersenPendapatan = (TextView) findViewById(R.id.textValuePersenPendapatan);
        textValuePersenBelanja = (TextView) findViewById(R.id.textValuePersenBelanja);
        textValuePersenPembiayaan = (TextView) findViewById(R.id.textValuePersenPembiayaan);
        textPendapatan = (TextView) findViewById(R.id.textPendapatan);
        textBelanja = (TextView) findViewById(R.id.textBelanja);
        textPembiayaan = (TextView) findViewById(R.id.textPembiayaan);
        textTahunApbd = (TextView) findViewById(R.id.textTahunApbd);
        text11 = (TextView) findViewById(R.id.text11);
        text12 = (TextView) findViewById(R.id.text12);
        btnLeftApbd = (ImageButton) findViewById(R.id.btnLeftApbd);
        btnRightApbd = (ImageButton) findViewById(R.id.btnRightApbd);
        btnPendapatan1 = (ImageButton) findViewById(R.id.btnPendapatan1);
        btnBelanja1 = (ImageButton) findViewById(R.id.btnBelanja1);
        btnPembiayaan1 = (ImageButton) findViewById(R.id.btnPembiayaan1);
        shareRealisasi1 = (ImageButton) findViewById(R.id.shareRealisasi1);
        ssRealisasi = (ImageView) findViewById(R.id.ssRealisasi);
        shareRealisasi2 = (ImageButton) findViewById(R.id.shareRealisasi2);
        mainLinear = (LinearLayout) findViewById(R.id.mainLinear);
        cardView1 = (CardView) findViewById(R.id.cardView1);
        cardView2 = (CardView) findViewById(R.id.cardView2);
        cardTitleRealisasi = (CardView) findViewById(R.id.cardTitleRealisasi);
        pbRealisasi1 = (ProgressBar) findViewById(R.id.pbRealisasi1);
        pbRealisasi2 = (ProgressBar) findViewById(R.id.pbRealisasi2);
        pbRealisasi3 = (ProgressBar) findViewById(R.id.pbRealisasi3);

        text11.setText("APBD Tahun "+tahunini+" (Triliun Rp.)");
        text12.setText("Anggaran & Realisasi Pendapatan Daerah\nTahun "+tahunini+" (Triliun Rp.)");
        textTahunApbd.setText(String.valueOf(tahun));
        textPersen.setVisibility(View.GONE);
        textBelanja.setVisibility(View.GONE);
        textPembiayaan.setVisibility(View.GONE);
        textPendapatan.setVisibility(View.GONE);
        textValuePersenBelanja.setVisibility(View.GONE);
        textValuePersenPembiayaan.setVisibility(View.GONE);
        textValuePersenPendapatan.setVisibility(View.GONE);
        shareRealisasi1.setVisibility(View.GONE);
        shareRealisasi2.setVisibility(View.GONE);

        // Diagram 1
        barChartAPBD1.setVisibility(View.INVISIBLE);
        barChartAPBD1.setTouchEnabled(true);
        barChartAPBD1.setDrawBorders(false);
        barChartAPBD1.getDescription().setEnabled(false);
        barChartAPBD1.getAxisRight().setEnabled(false);
        barChartAPBD1.animateY(1000, Easing.EasingOption.Linear);
        Legend l = barChartAPBD1.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
        l.setWordWrapEnabled(true);

        YAxis leftAxis = barChartAPBD1.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setSpaceTop(100);
        leftAxis.setDrawLimitLinesBehindData(false);

        // Diagram 2
        barChartAPBD2.setVisibility(View.INVISIBLE);
        barChartAPBD2.setTouchEnabled(true);
        barChartAPBD2.setDrawBorders(false);
        barChartAPBD2.getDescription().setEnabled(false);
        barChartAPBD2.getAxisRight().setEnabled(false);
        barChartAPBD2.animateY(1000, Easing.EasingOption.Linear);
        Legend l2 = barChartAPBD2.getLegend();
        l2.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
        l2.setWordWrapEnabled(true);

        YAxis leftAxis2 = barChartAPBD2.getAxisLeft();
        leftAxis2.removeAllLimitLines();
        leftAxis2.setAxisMinimum(0f);
        leftAxis2.setSpaceTop(20);
        leftAxis2.setDrawLimitLinesBehindData(false);

        loadDataBarChart2();
        loadDataBarChart1();
        loadRecyclerViewAnggaranRealisasi();

        cardTitleRealisasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(APBDActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnLeftApbd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tahun == 2018) {
                    Toast.makeText(APBDActivity.this, "Tahun tidak ditemukan", Toast.LENGTH_SHORT).show();
                } else {
                    barChartAPBD1.setVisibility(View.INVISIBLE);
                    barChartAPBD2.setVisibility(View.INVISIBLE);
                    recyclerView.setAdapter(null);

                    textPersen.setVisibility(View.GONE);
                    textBelanja.setVisibility(View.GONE);
                    textPendapatan.setVisibility(View.GONE);
                    textPembiayaan.setVisibility(View.GONE);
                    textValuePersenPembiayaan.setVisibility(View.GONE);
                    textValuePersenBelanja.setVisibility(View.GONE);
                    textValuePersenPendapatan.setVisibility(View.GONE);
                    shareRealisasi1.setVisibility(View.GONE);
                    shareRealisasi2.setVisibility(View.GONE);

                    tahun = tahun - 1;
                    textTahunApbd.setText(String.valueOf(tahun));
                    textPersen.setText("Persentase Realisasi Tahun " + tahun);
                    text11.setText("APBD Tahun " + tahun + " (Triliun Rp.)");

                    if (kategori.equals(kat1)) {
                        text12.setText("Anggaran & Realisasi Pendapatan Daerah\nTahun " + tahun + " (Triliun Rp.)");
                    } else {
                        text12.setText("Anggaran & Realisasi Belanja\nTahun " + tahun + " (Triliun Rp.)");
                    }


                    loadDataBarChart1();
                    loadDataBarChart2();
                    loadRecyclerViewAnggaranRealisasi();
                }
            }
        });

        btnRightApbd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tahun >= tahunini){
                    Toast.makeText(APBDActivity.this, "Tahun tidak ditemukan", Toast.LENGTH_SHORT).show();
                }else{
                    barChartAPBD1.setVisibility(View.INVISIBLE);
                    barChartAPBD2.setVisibility(View.INVISIBLE);
                    recyclerView.setAdapter(null);

                    textPersen.setVisibility(View.GONE);
                    textBelanja.setVisibility(View.GONE);
                    textPendapatan.setVisibility(View.GONE);
                    textPembiayaan.setVisibility(View.GONE);
                    textValuePersenPembiayaan.setVisibility(View.GONE);
                    textValuePersenBelanja.setVisibility(View.GONE);
                    textValuePersenPendapatan.setVisibility(View.GONE);
                    shareRealisasi1.setVisibility(View.GONE);
                    shareRealisasi2.setVisibility(View.GONE);

                    tahun = tahun + 1;
                    textTahunApbd.setText(String.valueOf(tahun));
                    textPersen.setText("Persentase Realisasi Tahun "+tahun);
                    text11.setText("APBD Tahun "+tahun+" (Triliun Rp.)");
                    if (kategori.equals(kat1)){
                        text12.setText("Anggaran & Realisasi Pendapatan Daerah\nTahun "+tahun+" (Triliun Rp.)");
                    }else{
                        text12.setText("Anggaran & Realisasi Belanja\nTahun "+tahun+" (Triliun Rp.)");
                    }

                    loadDataBarChart1();
                    loadDataBarChart2();
                    loadRecyclerViewAnggaranRealisasi();
                }
            }
        });

        btnPendapatan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBelanja1.setImageResource(R.drawable.selbelanja1);
                btnPendapatan1.setImageResource(R.drawable.selpendapatan2);
                btnPembiayaan1.setImageResource(R.drawable.selpembiayaan1);
                shareRealisasi2.setVisibility(View.GONE);
                barChartAPBD2.setVisibility(View.INVISIBLE);
                recyclerView.setAdapter(null);
                kategori = kat1;
                text12.setText("Anggaran & Realisasi Pendapatan Daerah\nTahun "+tahun+" (Triliun Rp.)");
                loadDataBarChart2();
                loadRecyclerViewAnggaranRealisasi();
            }
        });

        btnBelanja1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBelanja1.setImageResource(R.drawable.selbelanja2);
                btnPendapatan1.setImageResource(R.drawable.selpendapatan1);
                btnPembiayaan1.setImageResource(R.drawable.selpembiayaan1);
                shareRealisasi2.setVisibility(View.GONE);
                barChartAPBD2.setVisibility(View.INVISIBLE);
                recyclerView.setAdapter(null);
                kategori = kat3;
                text12.setText("Anggaran & Realisasi Belanja Daerah\nTahun "+tahun+" (Triliun Rp.)");
                loadDataBarChart2();
                loadRecyclerViewAnggaranRealisasi();
            }
        });

        btnPembiayaan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBelanja1.setImageResource(R.drawable.selbelanja1);
                btnPendapatan1.setImageResource(R.drawable.selpendapatan1);
                btnPembiayaan1.setImageResource(R.drawable.selpembiayaan2);
                shareRealisasi2.setVisibility(View.GONE);
                barChartAPBD2.setVisibility(View.INVISIBLE);
                recyclerView.setAdapter(null);
                kategori = kat2;
                text12.setText("Anggaran & Realisasi Pembiayaan Daerah\nTahun "+tahun+" (Miliar Rp.)");
                loadDataBarChart2();
                loadRecyclerViewAnggaranRealisasi();
            }
        });

        shareRealisasi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(APBDActivity.this)
                        .setTitle("Share Diagram Realisasi APBD 1?")
                        .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                takeSS(cardView1);

                                if (!sharePath.equals("no")) {
                                    share(sharePath);
                                } else {
                                    Toast.makeText(APBDActivity.this, "Bitmap Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

        shareRealisasi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(APBDActivity.this)
                        .setTitle("Share Diagram Realisasi APBD 2?")
                        .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                takeSS(cardView2);

                                if (!sharePath.equals("no")) {
                                    share(sharePath);
                                } else {
                                    Toast.makeText(APBDActivity.this, "Bitmap Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });
    }

    //Membuat Bar Chart 1 Menggunakan Volley
    public void loadDataBarChart1() {
        pbRealisasi1.setVisibility(View.VISIBLE);
        String url = AppConfig.URL_TOTAL_APBD + tahun;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                try{
                    barChartAPBD1.invalidate();
                    barChartAPBD1.clear();
                    barChartAPBD1.notifyDataSetChanged();

                    JSONObject obj = new JSONObject(response);
                    JSONArray arr_apbd = obj.getJSONArray("apbd");
                    JSONObject obj_pendapatan_daerah = arr_apbd.getJSONObject(0);
                    JSONObject obj_pembiayaan_daerah = arr_apbd.getJSONObject(1);
                    JSONObject obj_belanja = arr_apbd.getJSONObject(2);
                    ArrayList<BarEntry> yValues1 = new ArrayList<>();
                    ArrayList<BarEntry> yValues2 = new ArrayList<>();

                    Double anggaranPd = obj_pendapatan_daerah.getDouble("ANGGARAN")/1E12;
                    Double realisasiPd = obj_pendapatan_daerah.getDouble("REALISASI")/1E12;
                    String persentasePd = obj_pendapatan_daerah.getString("PERCENTAGE");
                    Double anggaranPemp = obj_pembiayaan_daerah.getDouble("ANGGARAN")/1E12;
                    Double realisasiPemp = obj_pembiayaan_daerah.getDouble("REALISASI")/1E12;
                    String persentasePemp = obj_pembiayaan_daerah.getString("PERCENTAGE");
                    Double anggaranB = obj_belanja.getDouble("ANGGARAN")/1E12;
                    Double realisasiB = obj_belanja.getDouble("REALISASI")/1E12;
                    String persentaseB = obj_belanja.getString("PERCENTAGE");

                    yValues1.add(new BarEntry(1,anggaranPd.floatValue()));
                    yValues1.add(new BarEntry(2,anggaranPemp.floatValue()));
                    yValues1.add(new BarEntry(3,anggaranB.floatValue()));
                    yValues2.add(new BarEntry(1,realisasiPd.floatValue()));
                    yValues2.add(new BarEntry(2,realisasiPemp.floatValue()));
                    yValues2.add(new BarEntry(3,realisasiB.floatValue()));

                    BarDataSet dataSet1 = new BarDataSet(yValues1,"Anggaran");
                    dataSet1.setColor(Color.parseColor("#3b8ead"));
                    dataSet1.setValueFormatter(new MyValueFormatter());
                    dataSet1.setValueTextSize(10f);

                    BarDataSet dataSet2 = new BarDataSet(yValues2,"Realisasi");
                    dataSet2.setColor(Color.parseColor("#ffc20e"));
                    dataSet2.setValueFormatter(new MyValueFormatter());
                    dataSet2.setValueTextSize(10f);
                    dataSet2.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                    BarData data = new BarData(dataSet1, dataSet2);

                    CustomMarkerView mv = new CustomMarkerView(APBDActivity.this, R.layout.content_value);
                    barChartAPBD1.setMarkerView(mv);
                    barChartAPBD1.animateY(1000, Easing.EasingOption.Linear);
                    barChartAPBD1.setData(data);
                    barChartAPBD1.setVisibleYRangeMaximum(6.5f, YAxis.AxisDependency.LEFT);
                    barChartAPBD1.setVisibility(View.VISIBLE);


                    String[] xValues = new String[]{"Pendapatan","Pembiayaan","Belanja"};
                    XAxis xAxis = barChartAPBD1.getXAxis();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
                    xAxis.removeAllLimitLines();
                    xAxis.setTextSize(10);
                    xAxis.setCenterAxisLabels(true);
                    xAxis.setGranularity(1);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                    float barSpace = 0f;
                    float groupSpace = 0.2f;
                    data.setBarWidth(0.4f);

                    barChartAPBD1.getXAxis().setAxisMinimum(0);
                    barChartAPBD1.getXAxis().setAxisMaximum(0+barChartAPBD1.getBarData().getGroupWidth(groupSpace, barSpace)*3);
                    barChartAPBD1.getAxisLeft().setAxisMinimum(0);
                    barChartAPBD1.groupBars(0, groupSpace, barSpace);

                    textPersen.setText("Persentase Realisasi Tahun "+tahun);
                    textValuePersenPembiayaan.setText(persentasePemp);
                    textValuePersenBelanja.setText(persentaseB);
                    textValuePersenPendapatan.setText(persentasePd);

                    textPersen.setVisibility(View.VISIBLE);
                    textBelanja.setVisibility(View.VISIBLE);
                    textPendapatan.setVisibility(View.VISIBLE);
                    textPembiayaan.setVisibility(View.VISIBLE);
                    textValuePersenPembiayaan.setVisibility(View.VISIBLE);
                    textValuePersenBelanja.setVisibility(View.VISIBLE);
                    textValuePersenPendapatan.setVisibility(View.VISIBLE);
                    pbRealisasi1.setVisibility(View.GONE);
                    shareRealisasi1.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    // Membuat Bar Chart 2 Menggunakan Volley
    public void loadDataBarChart2() {
        pbRealisasi2.setVisibility(View.VISIBLE);
        String param = "kategori="+kategori+"&tahun="+tahun;
        String url = AppConfig.URL_ANGGARAN_REALISASI_1 + param;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                try{
                    barChartAPBD2.invalidate();
                    barChartAPBD2.clear();
                    barChartAPBD2.notifyDataSetChanged();

                    JSONObject o = new JSONObject(response);
                    JSONArray arr_apbd = o.getJSONArray("apbd");
                    ArrayList<BarEntry> yValues1 = new ArrayList<>();
                    ArrayList<BarEntry> yValues2 = new ArrayList<>();

                    if (kategori.equals(kat2)){
                        for (int i = 0; i < arr_apbd.length(); i++){
                            JSONObject obj = arr_apbd.getJSONObject(i);
                            Double anggaran = obj.getDouble("TOTAL_TARGET")/1E9;
                            Double realisasi = obj.getDouble("TOTAL_REALISASI")/1E9;
                            String persentase = obj.getString("PERCENTAGE");
                            yValues1.add(new BarEntry(i+1,anggaran.floatValue()));
                            yValues2.add(new BarEntry(i+1, realisasi.floatValue()));
                        }

                        BarDataSet dataSet1 = new BarDataSet(yValues1,"Anggaran");
                        dataSet1.setColor(Color.parseColor("#80CBC4"));
                        dataSet1.setValueFormatter(new MyValueFormatter2());
                        dataSet1.setValueTextSize(10f);

                        BarDataSet dataSet2 = new BarDataSet(yValues2,"Realisasi");
                        dataSet2.setColor(Color.parseColor("#EF6C00"));
                        dataSet2.setValueFormatter(new MyValueFormatter2());
                        dataSet2.setValueTextSize(10f);
                        dataSet2.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                        BarData data = new BarData(dataSet1, dataSet2);

                        CustomMarkerView2 mv = new CustomMarkerView2(APBDActivity.this, R.layout.content_value);
                        barChartAPBD2.setMarkerView(mv);
                        barChartAPBD2.animateY(1000, Easing.EasingOption.Linear);
                        barChartAPBD2.setData(data);
                        barChartAPBD2.setVisibility(View.VISIBLE);

                        String[] xValues = new String[]{"Penerimaan Pembiayaan","Pengeluaran Pembiayaan"};
                        XAxis xAxis = barChartAPBD2.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
                        xAxis.removeAllLimitLines();
                        xAxis.setLabelRotationAngle(0);
                        xAxis.setTextSize(10);
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setGranularity(1);
                        xAxis.setGranularityEnabled(true);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                        float barSpace = 0f;
                        float groupSpace = 0.2f;
                        data.setBarWidth(0.4f);

                        barChartAPBD2.getXAxis().setAxisMinimum(0);
                        barChartAPBD2.getXAxis().setAxisMaximum(0+barChartAPBD2.getBarData().getGroupWidth(groupSpace, barSpace)*2);
                        barChartAPBD2.getAxisLeft().setAxisMinimum(0);
                        barChartAPBD2.groupBars(0, groupSpace, barSpace);
                        pbRealisasi2.setVisibility(View.GONE);
                        shareRealisasi2.setVisibility(View.VISIBLE);
                    } else {
                        for (int i = 0; i < arr_apbd.length(); i++){
                            JSONObject obj = arr_apbd.getJSONObject(i);
                            Double anggaran = obj.getDouble("TOTAL_TARGET")/1E12;
                            Double realisasi = obj.getDouble("TOTAL_REALISASI")/1E12;
                            String persentase = obj.getString("PERCENTAGE");
                            yValues1.add(new BarEntry(i+1,anggaran.floatValue()));
                            yValues2.add(new BarEntry(i+1, realisasi.floatValue()));
                        }

                        BarDataSet dataSet1 = new BarDataSet(yValues1,"Anggaran");
                        dataSet1.setColor(Color.parseColor("#80CBC4"));
                        dataSet1.setValueFormatter(new MyValueFormatter());
                        dataSet1.setValueTextSize(10f);

                        BarDataSet dataSet2 = new BarDataSet(yValues2,"Realisasi");
                        dataSet2.setColor(Color.parseColor("#EF6C00"));
                        dataSet2.setValueFormatter(new MyValueFormatter());
                        dataSet2.setValueTextSize(10f);
                        dataSet2.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                        BarData data = new BarData(dataSet1, dataSet2);

                        CustomMarkerView mv = new CustomMarkerView(APBDActivity.this, R.layout.content_value);
                        barChartAPBD2.setMarkerView(mv);
                        barChartAPBD2.animateY(1000, Easing.EasingOption.Linear);
                        barChartAPBD2.setData(data);
                        barChartAPBD2.setVisibility(View.VISIBLE);

                        if (kategori.equals(kat1)){
                            String[] xValues = new String[]{"Dana Perimbangan","Pendapatan Sah Lain","Pendapatan Asli Daerah"};
                            XAxis xAxis = barChartAPBD2.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
                            xAxis.removeAllLimitLines();
                            xAxis.setTextSize(10);
                            xAxis.setLabelRotationAngle(7);
                            xAxis.setCenterAxisLabels(true);
                            xAxis.setGranularity(1);
                            xAxis.setGranularityEnabled(true);
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                            float barSpace = 0f;
                            float groupSpace = 0.2f;
                            data.setBarWidth(0.4f);

                            barChartAPBD2.getXAxis().setAxisMinimum(0);
                            barChartAPBD2.getXAxis().setAxisMaximum(0+barChartAPBD2.getBarData().getGroupWidth(groupSpace, barSpace)*3);
                            barChartAPBD2.getAxisLeft().setAxisMinimum(0);
                            barChartAPBD2.groupBars(0, groupSpace, barSpace);
                            pbRealisasi2.setVisibility(View.GONE);
                            shareRealisasi2.setVisibility(View.VISIBLE);

                        } else {
                            String[] xValues = new String[]{"Belanja Langsung","Belanja Tidak Langsung"};
                            XAxis xAxis = barChartAPBD2.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
                            xAxis.removeAllLimitLines();
                            xAxis.setLabelRotationAngle(0);
                            xAxis.setTextSize(10);
                            xAxis.setCenterAxisLabels(true);
                            xAxis.setGranularity(1);
                            xAxis.setGranularityEnabled(true);
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                            float barSpace = 0f;
                            float groupSpace = 0.4f;
                            data.setBarWidth(0.3f);

                            barChartAPBD2.getXAxis().setAxisMinimum(0);
                            barChartAPBD2.getXAxis().setAxisMaximum(0+barChartAPBD2.getBarData().getGroupWidth(groupSpace, barSpace)*2);
                            barChartAPBD2.getAxisLeft().setAxisMinimum(0);
                            barChartAPBD2.groupBars(0, groupSpace, barSpace);
                            pbRealisasi2.setVisibility(View.GONE);
                            shareRealisasi2.setVisibility(View.VISIBLE);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    // Membuat tabel drill-down diagram 2 berdasarkan kategori
    private void loadRecyclerViewAnggaranRealisasi() {
        pbRealisasi3.setVisibility(View.VISIBLE);
        String param = "kategori="+kategori+"&tahun="+tahun;
        String url = AppConfig.URL_ANGGARAN_REALISASI_2+param;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            listAnggaranRealisasi.clear();

                            JSONObject obj = new JSONObject(response);
                            JSONArray ar = obj.getJSONArray("apbd");

                            //traversing through all the object
                            for (int i = 0; i < ar.length(); i++) {

                                //getting product object from json array
                                JSONObject object = ar.getJSONObject(i);

                                //adding the product to product list
                                listAnggaranRealisasi.add(new APBD(
                                        object.getString("U2"),
                                        object.getString("U3"),
                                        object.getString("PERCENTAGE"),
                                        object.getDouble("TOTAL_ANGGARAN"),
                                        object.getDouble("TOTAL_REALISASI")
                                ));
                            }
                            //creating adapter object and setting it to recyclerview
                            AnggaranRealisasiAdapter adapter = new AnggaranRealisasiAdapter(APBDActivity.this, listAnggaranRealisasi);
                            recyclerView.setAdapter(adapter);
                            pbRealisasi3.setVisibility(View.GONE);
                            shareRealisasi2.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
        };
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    // method format XAxis pada diagram
    public class XAxisValueFormatter implements IAxisValueFormatter {
        private String[] xValues;
        public XAxisValueFormatter(String[] values){
            this.xValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
//            axis.setLabelCount(2);
            return xValues[(int)value];
        }
    }

    // method format XAxis pada diagram
    public class MyValueFormatter implements IValueFormatter {
        private DecimalFormat mFormat;
        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value);
        }
    }

    // method format XAxis pada diagram
    public class MyValueFormatter2 implements IValueFormatter {
        private DecimalFormat mFormat;
        public MyValueFormatter2() {
            mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value);
        }
    }

    // tampilan markerview apabila diagram di click
    public class CustomMarkerView extends MarkerView {

        private TextView tvContent;
        public CustomMarkerView (Context context, int layoutResource) {
            super(context, layoutResource);
            tvContent = (TextView) findViewById(R.id.valueContent);
        }

        // callbacks everytime the MarkerView is redrawn, can be used to update the
        // content (user-interface)
        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            DecimalFormat df = new DecimalFormat("##0.000");
            tvContent.setText("Total: " + df.format(e.getY())+" Triliun"); // set the entry-value as the display text

            // this will perform necessary layouting
            super.refreshContent(e, highlight);
        }

        public int getXOffset() {
            // this will center the marker-view horizontally
            return -(getWidth() / 2);
        }

        public int getYOffset() {
            // this will cause the marker-view to be above the selected value
            return -getHeight();
        }

        @Override
        public void draw(Canvas canvas, float posx, float posy)
        {
            // take offsets into consideration
            posx += getXOffset();
            posy += getYOffset();

            // AVOID OFFSCREEN
            if(posx<45)
                posx=45;
            if(posx>650)
                posx=650;

            // translate to the correct position and draw
            canvas.translate(posx, posy);
            draw(canvas);
            canvas.translate(-posx, -posy);
        }
    }

    // tampilan markerview apabila diagram di click
    public class CustomMarkerView2 extends MarkerView {

        private TextView tvContent;
        public CustomMarkerView2 (Context context, int layoutResource) {
            super(context, layoutResource);
            tvContent = (TextView) findViewById(R.id.valueContent);
        }

        // callbacks everytime the MarkerView is redrawn, can be used to update the
        // content (user-interface)
        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            DecimalFormat df = new DecimalFormat("#,##0.000");
            tvContent.setText("Total: " + df.format(e.getY())+" Miliar"); // set the entry-value as the display text

            // this will perform necessary layouting
            super.refreshContent(e, highlight);
        }

        public int getXOffset() {
            // this will center the marker-view horizontally
            return -(getWidth() / 2);
        }

        public int getYOffset() {
            // this will cause the marker-view to be above the selected value
            return -getHeight();
        }

        @Override
        public void draw(Canvas canvas, float posx, float posy)
        {
            // take offsets into consideration
            posx += getXOffset();
            posy += getYOffset();

            // AVOID OFFSCREEN
            if(posx<45)
                posx=45;
            if(posx>650)
                posx=650;

            // translate to the correct position and draw
            canvas.translate(posx, posy);
            draw(canvas);
            canvas.translate(-posx, -posy);
        }
    }

    // Screenshot view berdasarkan bitmap
    public void takeSS(View v){
        Date now  = new Date();

        try {
            File folder = new File(Environment.getExternalStorageDirectory().toString()+"/ExecutiveDashboard/");
            folder.mkdirs();

            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/ExecutiveDashboard/Realisasi " + now + ".jpeg";

            // create bitmap screen capture
            v.setDrawingCacheEnabled(true);

            Bitmap bitmap = loadBitmapFromView(v);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 50;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            //setting screenshot in imageview
            String filePath = imageFile.getPath();

            Bitmap ssbitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            ssRealisasi.setImageBitmap(ssbitmap);
            sharePath = filePath;

        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    // method untuk share diagram
    private void share(String sharePath){
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        Log.d("test",sharePath);
        File file = new File(sharePath);
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(intent);
        System.out.println("Done Test");
    }

    // method mengubah view menjadi bitmap
    public static Bitmap loadBitmapFromView(View v){
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(APBDActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
