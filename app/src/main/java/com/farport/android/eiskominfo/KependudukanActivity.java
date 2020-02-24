package com.farport.android.eiskominfo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KependudukanActivity extends AppCompatActivity {
    // variable untuk screenshot dan share diagram
    private String sharePath="no";

    PieChart pieChartKependudukan;
    TextView textJumlahPenduduk, textKategori, pageText, genderText1, genderText2, genderText3, genderText4,
            dotRecycler, textViewPilih, pekerjaanText1, pekerjaanText2;
    ImageButton btnAge, btnPekerjaan, btnPendidikan, btnGender, btnAgama, pageLeft, pageRight, shareKependudukan;
    ImageView lineViewKependudukan, ssKependudukan;
    Button buttonSearch;
    Spinner spinnerKecamatan;
    CardView cardView1, cardTitleKependudukan;
    ProgressBar pbKependudukan1, pbKependudukan2;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<Kependudukan> listKependudukan;

    // warna pie chart
    int[] blueYellow = new int[]{
            Color.parseColor("#3b8ead"),
            Color.parseColor("#ffc20e")};
    int[] fachryPallete = new int[]{
            Color.parseColor("#3b8ead"),
            Color.parseColor("#ffc20e"),
            Color.parseColor("#d22940"),
            Color.parseColor("#7CB342"),
            Color.parseColor("#80CBC4"),
            Color.parseColor("#007cbb"),
            Color.parseColor("#81C784"),
            Color.parseColor("#EF6C00"),
            Color.parseColor("#d22941"),
            Color.parseColor("#7CB343")
    };
    int page = 1;
    String kategori = "Jenis Kelamin";
    String kecamatan = "BALONGBENDO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kependudukan);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listKependudukan = new ArrayList<>();

        pieChartKependudukan = (PieChart) findViewById(R.id.pieChartKependudukan);
        btnAgama = (ImageButton) findViewById(R.id.btnAgama);
        btnAge = (ImageButton) findViewById(R.id.btnAge);
        btnGender = (ImageButton) findViewById(R.id.btnGender);
        btnPekerjaan = (ImageButton) findViewById(R.id.btnPekerjaan);
        btnPendidikan = (ImageButton) findViewById(R.id.btnPendidikan);
        textKategori = (TextView) findViewById(R.id.textKategori);
        textJumlahPenduduk = (TextView) findViewById(R.id.textJumlahPenduduk);
        lineViewKependudukan = (ImageView) findViewById(R.id.lineViewKependudukan);
        pageLeft = (ImageButton) findViewById(R.id.pageLeft);
        pageRight = (ImageButton) findViewById(R.id.pageRight);
        pageText = (TextView) findViewById(R.id.pageText);
        genderText1 = (TextView) findViewById(R.id.genderText1);
        genderText2 = (TextView) findViewById(R.id.genderText2);
        genderText3 = (TextView) findViewById(R.id.genderText3);
        genderText4 = (TextView) findViewById(R.id.genderText4);
        dotRecycler = (TextView) findViewById(R.id.dotRecycler);
        pekerjaanText1 = (TextView) findViewById(R.id.pekerjaanText1);
        pekerjaanText2 = (TextView) findViewById(R.id.pekerjaanText2);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        spinnerKecamatan = (Spinner) findViewById(R.id.spinnerKecamatan);
        textViewPilih = (TextView) findViewById(R.id.textViewPilih);
        shareKependudukan = (ImageButton) findViewById(R.id.shareKependudukan);
        ssKependudukan = (ImageView) findViewById(R.id.ssKependudukan);
        cardView1 = (CardView) findViewById(R.id.cardViewKP1);
        cardTitleKependudukan = (CardView) findViewById(R.id.cardTitleKependudukan);
        pbKependudukan1 = (ProgressBar) findViewById(R.id.pbKependudukan1);
        pbKependudukan2 = (ProgressBar) findViewById(R.id.pbKependudukan2);

        {
            pieChartKependudukan.setTouchEnabled(true);
            pieChartKependudukan.setUsePercentValues(true);
            pieChartKependudukan.getDescription().setEnabled(false);
            pieChartKependudukan.setHoleRadius(20f);
            pieChartKependudukan.setTransparentCircleRadius(25f);
            pieChartKependudukan.getLegend().setTextSize(10f);
            pieChartKependudukan.setHighlightPerTapEnabled(true);
            pieChartKependudukan.setExtraOffsets(0, 15, 0, 30);
            pieChartKependudukan.setEntryLabelColor(Color.BLACK);
            pieChartKependudukan.setEntryLabelTextSize(10f);
            pieChartKependudukan.setVisibility(View.INVISIBLE);
            pieChartKependudukan.animateY(1000, Easing.EasingOption.EaseInOutCirc);

            Legend l = pieChartKependudukan.getLegend();
            l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
            l.setWordWrapEnabled(true);
        }

        textKategori.setText("Penduduk Pemkab Sidoarjo\nBerdasarkan "+kategori);
        recyclerView.setVisibility(View.VISIBLE);
        shareKependudukan.setVisibility(View.GONE);

        loadTotalPenduduk();
        loadDataPieChartByGender();
        loadDetailGender();
        getAllKecamatan();

        cardTitleKependudukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KependudukanActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGender.setImageResource(R.drawable.selgender2);
                btnAgama.setImageResource(R.drawable.selagama1);
                btnAge.setImageResource(R.drawable.selusia1);
                btnPekerjaan.setImageResource(R.drawable.selpekerjaan1);
                btnPendidikan.setImageResource(R.drawable.selpendidikan1);

                page = 1;
                pageText.setText("Page "+page);

                kategori = "Jenis Kelamin";
                pieChartKependudukan.setVisibility(View.INVISIBLE);
                textKategori.setText("Penduduk Pemkab Sidoarjo\nBerdasarkan "+kategori);

                lineViewKependudukan.setVisibility(View.VISIBLE);
                pageLeft.setVisibility(View.VISIBLE);
                pageRight.setVisibility(View.VISIBLE);
                pageText.setVisibility(View.VISIBLE);
                genderText1.setVisibility(View.VISIBLE);
                genderText2.setVisibility(View.VISIBLE);
                genderText3.setVisibility(View.VISIBLE);
                genderText4.setVisibility(View.VISIBLE);
                pekerjaanText1.setVisibility(View.INVISIBLE);
                pekerjaanText2.setVisibility(View.INVISIBLE);
                buttonSearch.setVisibility(View.INVISIBLE);
                spinnerKecamatan.setVisibility(View.INVISIBLE);
                textViewPilih.setVisibility(View.INVISIBLE);
                dotRecycler.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                shareKependudukan.setVisibility(View.GONE);


                loadDataPieChartByGender();
                loadDetailGender();
            }
        });

        btnPendidikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGender.setImageResource(R.drawable.selgender1);
                btnAgama.setImageResource(R.drawable.selagama1);
                btnAge.setImageResource(R.drawable.selusia1);
                btnPekerjaan.setImageResource(R.drawable.selpekerjaan1);
                btnPendidikan.setImageResource(R.drawable.selpendidikan2);

                page = 1;
                pageText.setText("Page "+page);

                kategori = "Pendidikan Terakhir";
                pieChartKependudukan.setVisibility(View.INVISIBLE);
                textKategori.setText("Penduduk Pemkab Sidoarjo\nBerdasarkan "+kategori);

                lineViewKependudukan.setVisibility(View.VISIBLE);
                pageLeft.setVisibility(View.VISIBLE);
                pageRight.setVisibility(View.VISIBLE);
                pageText.setVisibility(View.VISIBLE);
                genderText1.setVisibility(View.INVISIBLE);
                genderText2.setVisibility(View.INVISIBLE);
                genderText3.setVisibility(View.INVISIBLE);
                genderText4.setVisibility(View.INVISIBLE);
                pekerjaanText1.setVisibility(View.INVISIBLE);
                pekerjaanText2.setVisibility(View.INVISIBLE);
                buttonSearch.setVisibility(View.INVISIBLE);
                spinnerKecamatan.setVisibility(View.INVISIBLE);
                textViewPilih.setVisibility(View.INVISIBLE);
                dotRecycler.setVisibility(View.GONE);
                recyclerView.setVisibility(View.INVISIBLE);
                shareKependudukan.setVisibility(View.GONE);

                loadDataPieChartByPendidikan();
                loadDetailPendidikan();
            }
        });

        btnPekerjaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGender.setImageResource(R.drawable.selgender1);
                btnAgama.setImageResource(R.drawable.selagama1);
                btnAge.setImageResource(R.drawable.selusia1);
                btnPekerjaan.setImageResource(R.drawable.selpekerjaan2);
                btnPendidikan.setImageResource(R.drawable.selpendidikan1);

                page = 1;
                pageText.setText("Page "+page);

                kategori = "Pekerjaan";
                pieChartKependudukan.setVisibility(View.INVISIBLE);
                textKategori.setText("Penduduk Pemkab Sidoarjo\nBerdasarkan "+kategori);

                lineViewKependudukan.setVisibility(View.VISIBLE);
                pageLeft.setVisibility(View.VISIBLE);
                pageRight.setVisibility(View.VISIBLE);
                pageText.setVisibility(View.VISIBLE);
                genderText1.setVisibility(View.INVISIBLE);
                genderText2.setVisibility(View.INVISIBLE);
                genderText3.setVisibility(View.INVISIBLE);
                genderText4.setVisibility(View.INVISIBLE);
                pekerjaanText1.setVisibility(View.VISIBLE);
                pekerjaanText2.setVisibility(View.VISIBLE);
                buttonSearch.setVisibility(View.INVISIBLE);
                spinnerKecamatan.setVisibility(View.INVISIBLE);
                textViewPilih.setVisibility(View.INVISIBLE);
                dotRecycler.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                shareKependudukan.setVisibility(View.GONE);

                loadDataPieChartByPekerjaan();
                loadDetailPekerjaan();
            }
        });

        btnAgama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGender.setImageResource(R.drawable.selgender1);
                btnAgama.setImageResource(R.drawable.selagama2);
                btnAge.setImageResource(R.drawable.selusia1);
                btnPekerjaan.setImageResource(R.drawable.selpekerjaan1);
                btnPendidikan.setImageResource(R.drawable.selpendidikan1);

                kategori = "Agama";
                pieChartKependudukan.setVisibility(View.INVISIBLE);
                textKategori.setText("Penduduk Pemkab Sidoarjo\nBerdasarkan "+kategori);

                lineViewKependudukan.setVisibility(View.VISIBLE);
                pageLeft.setVisibility(View.INVISIBLE);
                pageRight.setVisibility(View.INVISIBLE);
                pageText.setVisibility(View.INVISIBLE);
                genderText1.setVisibility(View.INVISIBLE);
                genderText2.setVisibility(View.INVISIBLE);
                genderText3.setVisibility(View.INVISIBLE);
                genderText4.setVisibility(View.INVISIBLE);
                pekerjaanText1.setVisibility(View.INVISIBLE);
                pekerjaanText2.setVisibility(View.INVISIBLE);
                buttonSearch.setVisibility(View.VISIBLE);
                spinnerKecamatan.setVisibility(View.VISIBLE);
                textViewPilih.setVisibility(View.VISIBLE);
                dotRecycler.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.GONE);
                shareKependudukan.setVisibility(View.GONE);

                loadDataPieChartByAgama();
            }
        });

        btnAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGender.setImageResource(R.drawable.selgender1);
                btnAgama.setImageResource(R.drawable.selagama1);
                btnAge.setImageResource(R.drawable.selusia2);
                btnPekerjaan.setImageResource(R.drawable.selpekerjaan1);
                btnPendidikan.setImageResource(R.drawable.selpendidikan1);

                kategori = "Usia";
                pieChartKependudukan.setVisibility(View.INVISIBLE);
                textKategori.setText("Penduduk Pemkab Sidoarjo\nBerdasarkan "+kategori);

                lineViewKependudukan.setVisibility(View.GONE);
                pageLeft.setVisibility(View.GONE);
                pageRight.setVisibility(View.GONE);
                pageText.setVisibility(View.GONE);
                genderText1.setVisibility(View.GONE);
                genderText2.setVisibility(View.GONE);
                genderText3.setVisibility(View.GONE);
                genderText4.setVisibility(View.GONE);
                pekerjaanText1.setVisibility(View.GONE);
                pekerjaanText2.setVisibility(View.GONE);
                buttonSearch.setVisibility(View.GONE);
                spinnerKecamatan.setVisibility(View.GONE);
                textViewPilih.setVisibility(View.GONE);
                dotRecycler.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                shareKependudukan.setVisibility(View.GONE);

                loadDataPieChartByUsia();
            }
        });

        pageLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kategori.equals("Jenis Kelamin")){
                    if (page == 1){
                        Toast.makeText(KependudukanActivity.this, "Halaman Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    } else {
                        page = page-1;
                        pageText.setText("Page "+page);
                        recyclerView.setVisibility(View.INVISIBLE);
                        loadDetailGender();
                    }
                } else if (kategori.equals("Pendidikan Terakhir")){
                    if (page == 1){
                        Toast.makeText(KependudukanActivity.this, "Halaman Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    } else {
                        page = page-1;
                        pageText.setText("Page "+page);
                        recyclerView.setVisibility(View.INVISIBLE);
                        loadDetailPendidikan();
                    }
                } else if (kategori.equals("Pekerjaan")){
                    if (page == 1){
                        Toast.makeText(KependudukanActivity.this, "Halaman Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    } else {
                        page = page - 1;
                        pageText.setText("Page "+page);
                        recyclerView.setVisibility(View.INVISIBLE);
                        loadDetailPekerjaan();
                    }
                }
            }
        });

        pageRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kategori.equals("Jenis Kelamin")){
                    if (page == 2){
                        Toast.makeText(KependudukanActivity.this, "Halaman Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    } else {
                        page = page + 1;
                        pageText.setText("Page "+page);
                        recyclerView.setVisibility(View.INVISIBLE);
                        loadDetailGender();
                    }
                } else if (kategori.equals("Pendidikan Terakhir")){
                    if (page == 4){
                        Toast.makeText(KependudukanActivity.this, "Halaman Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    } else {
                        page = page + 1;
                        pageText.setText("Page "+page);
                        recyclerView.setVisibility(View.INVISIBLE);
                        loadDetailPendidikan();
                    }
                } else if (kategori.equals("Pekerjaan")){
                    if (page == 6){
                        Toast.makeText(KependudukanActivity.this, "Halaman Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    } else {
                        page = page + 1;
                        pageText.setText("Page "+page);
                        recyclerView.setVisibility(View.INVISIBLE);
                        loadDetailPekerjaan();
                    }
                }
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDetailAgama();
            }
        });

        shareKependudukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(KependudukanActivity.this)
                        .setTitle("Share Data Kependudukan?")
                        .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                takeSS(cardView1);

                                if (!sharePath.equals("no")) {
                                    share(sharePath);
                                } else {
                                    Toast.makeText(KependudukanActivity.this, "Bitmap Error", Toast.LENGTH_SHORT).show();
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

    // Mendapatkan total penduduk dengan merequest GET pada API dengan bantuan Volley
    public void loadTotalPenduduk() {
        String url = AppConfig.URL_PENDUDUK_ALL;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                try{
                    JSONObject o = new JSONObject(response);
                    JSONArray arr_kependudukan = o.getJSONArray("kependudukan");
                    JSONObject penduduk = arr_kependudukan.getJSONObject(0);
                    Integer jumlah = penduduk.getInt("TOTAL_PENDUDUK");

                    DecimalFormat formatter = new DecimalFormat("###,###,###");
                    textJumlahPenduduk.setText(formatter.format(jumlah));
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

    // Membuat Pie Chart dengan mendapatkan data menggunakan request GET API dengan bantuan volley
    public void loadDataPieChartByGender() {
        pbKependudukan1.setVisibility(View.VISIBLE);
        String url = AppConfig.URL_PENDUDUK_BY_GENDER;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                try{
                    pieChartKependudukan.invalidate();
                    pieChartKependudukan.clear();
                    pieChartKependudukan.notifyDataSetChanged();

                    JSONObject o = new JSONObject(response);
                    JSONArray arr_kependudukan = o.getJSONArray("kependudukan");

                    ArrayList<PieEntry> yValues = new ArrayList<>();

                    JSONObject penduduk = arr_kependudukan.getJSONObject(0);
                    Integer jumlah_pria = penduduk.getInt("JUMLAH_PRIA");
                    Integer jumlah_wanita = penduduk.getInt("JUMLAH_WANITA");
                    yValues.add(new PieEntry(jumlah_pria.floatValue(), "Pria"));
                    yValues.add(new PieEntry(jumlah_wanita.floatValue(), "Wanita"));

                    PieDataSet dataSet = new PieDataSet(yValues, "");
                    dataSet.setSelectionShift(2f);
                    dataSet.setSliceSpace(3f);
                    dataSet.setValueTextColor(Color.BLACK);
                    dataSet.setColors(blueYellow);
                    dataSet.setValueLineColor(Color.BLUE);
                    dataSet.setValueLinePart1Length(1f);
                    dataSet.setValueLinePart2Length(1.5f);
                    dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    dataSet.setValueFormatter(new PercentFormatter());

                    PieData data = new PieData(dataSet);
                    data.setValueTextSize(10f);
                    data.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                    CustomMarkerView mv = new CustomMarkerView (KependudukanActivity.this, R.layout.content_value);
                    pieChartKependudukan.setMarkerView(mv);
                    pieChartKependudukan.setData(data);
                    pieChartKependudukan.setVisibility(View.VISIBLE);
                    pieChartKependudukan.setExtraOffsets(0, 15, 0, 30);
                    pieChartKependudukan.animateY(1000, Easing.EasingOption.EaseInOutCirc);
                    pbKependudukan1.setVisibility(View.GONE);
                    shareKependudukan.setVisibility(View.VISIBLE);

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

    // Membuat Pie Chart dengan mendapatkan data menggunakan request GET API dengan bantuan volley
    public void loadDataPieChartByPendidikan() {
        pbKependudukan1.setVisibility(View.VISIBLE);
        String url = AppConfig.URL_PENDUDUK_BY_PENDIDIKAN;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                try{
                    pieChartKependudukan.invalidate();
                    pieChartKependudukan.clear();
                    pieChartKependudukan.notifyDataSetChanged();

                    JSONObject o = new JSONObject(response);
                    JSONArray arr_kependudukan = o.getJSONArray("kependudukan");

                    ArrayList<PieEntry> yValues = new ArrayList<>();
                    JSONObject penduduk = arr_kependudukan.getJSONObject(0);

                    int tbs, bts, tss, sltp, slta, d1d2, d3, d4s1, s2, s3;

                    yValues.add(new PieEntry(penduduk.getInt("TBS"), "Tidak Sekolah"));
                    yValues.add(new PieEntry(penduduk.getInt("BTS"), "Belum Tamat SD"));
                    yValues.add(new PieEntry(penduduk.getInt("TSS"), "Tamat SD"));
                    yValues.add(new PieEntry(penduduk.getInt("SLTP"), "SLTP"));
                    yValues.add(new PieEntry(penduduk.getInt("SLTA"), "SLTA"));
                    yValues.add(new PieEntry((penduduk.getInt("D1_D2")+penduduk.getInt("D3")), "D1/D2/D3"));
                    yValues.add(new PieEntry((penduduk.getInt("D4_S1")+penduduk.getInt("S2")+penduduk.getInt("S3"))
                            , "S1/S2/S3"));

                    PieDataSet dataSet = new PieDataSet(yValues, "");
                    dataSet.setValueTextColor(Color.BLACK);
                    dataSet.setSliceSpace(0.1f);
                    dataSet.setColors(fachryPallete);
                    dataSet.setValueLineColor(Color.BLUE);
                    dataSet.setValueLinePart1Length(1f);
                    dataSet.setValueLinePart2Length(1.5f);
                    dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    dataSet.setValueFormatter(new PercentFormatter());

                    PieData data = new PieData(dataSet);
                    data.setValueTextSize(10f);
                    data.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                    CustomMarkerView mv = new CustomMarkerView (KependudukanActivity.this, R.layout.content_value);
                    pieChartKependudukan.setMarkerView(mv);
                    pieChartKependudukan.setData(data);
                    pieChartKependudukan.setVisibility(View.VISIBLE);
                    pieChartKependudukan.setExtraOffsets(0, 15, 0, 20);
                    pieChartKependudukan.animateY(1000, Easing.EasingOption.EaseInOutCirc);
                    pbKependudukan1.setVisibility(View.GONE);
                    shareKependudukan.setVisibility(View.VISIBLE);

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

    // Membuat Pie Chart dengan mendapatkan data menggunakan request GET API dengan bantuan volley
    public void loadDataPieChartByPekerjaan() {
        pbKependudukan1.setVisibility(View.VISIBLE);
        String url = AppConfig.URL_PENDUDUK_BY_PEKERJAAN;
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                try{
                    pieChartKependudukan.invalidate();
                    pieChartKependudukan.clear();
                    pieChartKependudukan.notifyDataSetChanged();

                    JSONObject o = new JSONObject(response);
                    JSONArray arr_kependudukan = o.getJSONArray("kependudukan");

                    ArrayList<PieEntry> yValues = new ArrayList<>();

                    for (int i = 0; i < 5; i++){
                        JSONObject penduduk = arr_kependudukan.getJSONObject(i);
                        String pekerjaan = penduduk.getString("PEKERJAAN");
                        int jumlah = penduduk.getInt("JUMLAH");
                        yValues.add(new PieEntry(jumlah, pekerjaan));
                    }

                    int dll=0;
                    for (int i = 5; i < arr_kependudukan.length(); i++){
                        JSONObject penduduk = arr_kependudukan.getJSONObject(i);
                        String pekerjaan = penduduk.getString("PEKERJAAN");
                        int jumlah = penduduk.getInt("JUMLAH");
                        dll = dll+jumlah;
                    }
                    yValues.add(new PieEntry(dll, "PEKERJAAN LAIN"));

                    PieDataSet dataSet = new PieDataSet(yValues, "");
                    dataSet.setValueTextColor(Color.BLACK);
                    dataSet.setSliceSpace(1f);
                    dataSet.setColors(fachryPallete);
                    dataSet.setValueLineColor(Color.BLUE);
                    dataSet.setValueLinePart1Length(1f);
                    dataSet.setValueLinePart2Length(1.5f);
                    dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    dataSet.setValueFormatter(new PercentFormatter());

                    PieData data = new PieData(dataSet);
                    data.setValueTextSize(10f);
                    data.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                    CustomMarkerView mv = new CustomMarkerView (KependudukanActivity.this, R.layout.content_value);
                    pieChartKependudukan.setMarkerView(mv);
                    pieChartKependudukan.setData(data);
                    pieChartKependudukan.setVisibility(View.VISIBLE);
                    pieChartKependudukan.setExtraOffsets(0, 15, 0, 15);
                    pieChartKependudukan.animateY(1000, Easing.EasingOption.EaseInOutCirc);
                    pbKependudukan1.setVisibility(View.GONE);
                    shareKependudukan.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(stringRequest);
    }

    // Membuat Pie Chart dengan mendapatkan data menggunakan request GET API dengan bantuan volley
    public void loadDataPieChartByAgama() {
        pbKependudukan1.setVisibility(View.VISIBLE);
        String url = AppConfig.URL_PENDUDUK_BY_AGAMA;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                try{
                    pieChartKependudukan.invalidate();
                    pieChartKependudukan.clear();
                    pieChartKependudukan.notifyDataSetChanged();

                    JSONObject o = new JSONObject(response);
                    JSONArray arr_kependudukan = o.getJSONArray("kependudukan");

                    ArrayList<PieEntry> yValues = new ArrayList<>();

                    for (int i = 0; i < 2; i++){
                        JSONObject penduduk = arr_kependudukan.getJSONObject(i);
                        String agama = penduduk.getString("AGAMA");
                        int jumlah = penduduk.getInt("JUMLAH");
                        yValues.add(new PieEntry(jumlah, agama));
                    }

                    int dll=0;
                    for (int i = 2; i < arr_kependudukan.length(); i++){
                        JSONObject penduduk = arr_kependudukan.getJSONObject(i);
                        String agama = penduduk.getString("AGAMA");
                        int jumlah = penduduk.getInt("JUMLAH");
                        dll = dll+jumlah;
                    }
                    yValues.add(new PieEntry(dll, "AGAMA LAIN"));

                    PieDataSet dataSet = new PieDataSet(yValues, "");
                    dataSet.setValueTextColor(Color.BLACK);
                    dataSet.setColors(fachryPallete);
                    dataSet.setValueLineColor(Color.BLUE);
                    dataSet.setValueLinePart1Length(1f);
                    dataSet.setValueLinePart2Length(1.5f);
                    dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    dataSet.setValueFormatter(new PercentFormatter());

                    PieData data = new PieData(dataSet);
                    data.setValueTextSize(10f);
                    data.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                    CustomMarkerView mv = new CustomMarkerView (KependudukanActivity.this, R.layout.content_value);
                    pieChartKependudukan.setMarkerView(mv);
                    pieChartKependudukan.setData(data);
                    pieChartKependudukan.setVisibility(View.VISIBLE);
                    pieChartKependudukan.setExtraOffsets(0, 15, 0, 20);
                    pieChartKependudukan.animateY(1000, Easing.EasingOption.EaseInOutCirc);
                    pbKependudukan1.setVisibility(View.GONE);
                    shareKependudukan.setVisibility(View.VISIBLE);

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

    // Membuat Pie Chart dengan mendapatkan data menggunakan request GET API dengan bantuan volley
    public void loadDataPieChartByUsia() {
        pbKependudukan1.setVisibility(View.VISIBLE);
        String url = AppConfig.URL_PENDUDUK_BY_USIA;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                try{
                    pieChartKependudukan.invalidate();
                    pieChartKependudukan.clear();
                    pieChartKependudukan.notifyDataSetChanged();

                    JSONObject o = new JSONObject(response);
                    JSONArray arr_kependudukan = o.getJSONArray("kependudukan");

                    ArrayList<PieEntry> yValues = new ArrayList<>();
                    int u1 = arr_kependudukan.getJSONObject(0).getInt("JUMLAH")
                            + arr_kependudukan.getJSONObject(9).getInt("JUMLAH");
                    int u2 = arr_kependudukan.getJSONObject(1).getInt("JUMLAH")
                            + arr_kependudukan.getJSONObject(2).getInt("JUMLAH");
                    int u3 = arr_kependudukan.getJSONObject(3).getInt("JUMLAH")
                            + arr_kependudukan.getJSONObject(4).getInt("JUMLAH");
                    int u4 = arr_kependudukan.getJSONObject(5).getInt("JUMLAH")
                            + arr_kependudukan.getJSONObject(6).getInt("JUMLAH");
                    int u5 = arr_kependudukan.getJSONObject(7).getInt("JUMLAH")
                            + arr_kependudukan.getJSONObject(8).getInt("JUMLAH");
                    int u6 = arr_kependudukan.getJSONObject(10).getInt("JUMLAH")
                            + arr_kependudukan.getJSONObject(11).getInt("JUMLAH");
                    int u7 = arr_kependudukan.getJSONObject(12).getInt("JUMLAH")
                            + arr_kependudukan.getJSONObject(13).getInt("JUMLAH");
                    int u8 = arr_kependudukan.getJSONObject(14).getInt("JUMLAH")
                            + arr_kependudukan.getJSONObject(15).getInt("JUMLAH");

                    yValues.add(new PieEntry(u1, "0-9"));
                    yValues.add(new PieEntry(u2, "10-19"));
                    yValues.add(new PieEntry(u3, "20-29"));
                    yValues.add(new PieEntry(u4, "30-39"));
                    yValues.add(new PieEntry(u5, "40-49"));
                    yValues.add(new PieEntry(u6, "50-59"));
                    yValues.add(new PieEntry(u7, "60-69"));
                    yValues.add(new PieEntry(u8, ">=70"));

                    PieDataSet dataSet = new PieDataSet(yValues, "");
                    dataSet.setValueTextColor(Color.BLACK);
                    dataSet.setColors(fachryPallete);
                    dataSet.setSliceSpace(1f);
                    dataSet.setValueLineColor(Color.BLUE);
                    dataSet.setValueLinePart1Length(1f);
                    dataSet.setValueLinePart2Length(1.5f);
                    dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    dataSet.setValueFormatter(new PercentFormatter());

                    PieData data = new PieData(dataSet);
                    data.setValueTextSize(10f);
                    data.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                    CustomMarkerView mv = new CustomMarkerView (KependudukanActivity.this, R.layout.content_value);
                    pieChartKependudukan.setMarkerView(mv);
                    pieChartKependudukan.setData(data);
                    pieChartKependudukan.setVisibility(View.VISIBLE);
                    pieChartKependudukan.setExtraOffsets(0, 15, 0, 20);
                    pieChartKependudukan.animateY(1000, Easing.EasingOption.EaseInOutCirc);
                    pbKependudukan1.setVisibility(View.GONE);

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

    // Drill-down dari Pie Chart yang mendapatkan data dengan merequest GET API dengan bantuan volley
    private void loadDetailGender() {
        pbKependudukan2.setVisibility(View.VISIBLE);
        String url = AppConfig.URL_PENDUDUK_BY_GENDER_DETAIL;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            listKependudukan.clear();
                            recyclerView.setVisibility(View.VISIBLE);

                            JSONObject obj = new JSONObject(response);
                            JSONArray ar = obj.getJSONArray("kependudukan");

                            if (page == 1){
                                for (int i = 0; i < 10; i++) {

                                    JSONObject object = ar.getJSONObject(i);
                                    listKependudukan.add(new Kependudukan(
                                            object.getString("KECAMATAN"),
                                            object.getInt("JUMLAH_PRIA"),
                                            object.getInt("JUMLAH_WANITA"),
                                            object.getInt("TOTAL_PENDUDUK")
                                    ));
                                }
                                KependudukanGenderAdapter adapter = new KependudukanGenderAdapter(KependudukanActivity.this, listKependudukan);
                                recyclerView.setAdapter(adapter);
                                pbKependudukan2.setVisibility(View.GONE);

                            } else {
                                for (int i = 10; i < ar.length(); i++) {

                                    JSONObject object = ar.getJSONObject(i);
                                    listKependudukan.add(new Kependudukan(
                                            object.getString("KECAMATAN"),
                                            object.getInt("JUMLAH_PRIA"),
                                            object.getInt("JUMLAH_WANITA"),
                                            object.getInt("TOTAL_PENDUDUK")
                                    ));
                                }
                                KependudukanGenderAdapter adapter = new KependudukanGenderAdapter(KependudukanActivity.this, listKependudukan);
                                recyclerView.setAdapter(adapter);
                                pbKependudukan2.setVisibility(View.GONE);
                            }
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

    // Drill-down dari Pie Chart yang mendapatkan data dengan merequest GET API dengan bantuan volley
    private void loadDetailPendidikan() {
        pbKependudukan2.setVisibility(View.VISIBLE);
        String url = AppConfig.URL_PENDUDUK_BY_PENDIDIKAN_DETAIL;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            listKependudukan.clear();
                            recyclerView.setVisibility(View.VISIBLE);

                            JSONObject obj = new JSONObject(response);
                            JSONArray ar = obj.getJSONArray("kependudukan");

                            if (page == 1){
                                for (int i = 0; i < 5; i++) {

                                    JSONObject object = ar.getJSONObject(i);
                                    listKependudukan.add(new Kependudukan(
                                            object.getString("KECAMATAN"),
                                            object.getInt("TBS"),
                                            object.getInt("BTS"),
                                            object.getInt("TSS"),
                                            object.getInt("SLTP"),
                                            object.getInt("SLTA"),
                                            object.getInt("D1_D2"),
                                            object.getInt("D3"),
                                            object.getInt("D4_S1"),
                                            object.getInt("S2"),
                                            object.getInt("S3")

                                    ));
                                }

                            } else if (page == 2){
                                for (int i = 5; i < 10; i++) {

                                    JSONObject object = ar.getJSONObject(i);
                                    listKependudukan.add(new Kependudukan(
                                            object.getString("KECAMATAN"),
                                            object.getInt("TBS"),
                                            object.getInt("BTS"),
                                            object.getInt("TSS"),
                                            object.getInt("SLTP"),
                                            object.getInt("SLTA"),
                                            object.getInt("D1_D2"),
                                            object.getInt("D3"),
                                            object.getInt("D4_S1"),
                                            object.getInt("S2"),
                                            object.getInt("S3")
                                    ));
                                }
                            } else if (page == 3){
                                for (int i = 10; i < 15; i++) {

                                    JSONObject object = ar.getJSONObject(i);
                                    listKependudukan.add(new Kependudukan(
                                            object.getString("KECAMATAN"),
                                            object.getInt("TBS"),
                                            object.getInt("BTS"),
                                            object.getInt("TSS"),
                                            object.getInt("SLTP"),
                                            object.getInt("SLTA"),
                                            object.getInt("D1_D2"),
                                            object.getInt("D3"),
                                            object.getInt("D4_S1"),
                                            object.getInt("S2"),
                                            object.getInt("S3")
                                    ));
                                }
                            } else {
                                for (int i = 15; i < ar.length(); i++) {

                                    JSONObject object = ar.getJSONObject(i);
                                    listKependudukan.add(new Kependudukan(
                                            object.getString("KECAMATAN"),
                                            object.getInt("TBS"),
                                            object.getInt("BTS"),
                                            object.getInt("TSS"),
                                            object.getInt("SLTP"),
                                            object.getInt("SLTA"),
                                            object.getInt("D1_D2"),
                                            object.getInt("D3"),
                                            object.getInt("D4_S1"),
                                            object.getInt("S2"),
                                            object.getInt("S3")
                                    ));
                                }
                            }
                            KependudukanPendidikanAdapter adapter = new KependudukanPendidikanAdapter(KependudukanActivity.this, listKependudukan);
                            recyclerView.setAdapter(adapter);
                            pbKependudukan2.setVisibility(View.GONE);
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

    // Drill-down dari Pie Chart yang mendapatkan data dengan merequest GET API dengan bantuan volley
    private void loadDetailPekerjaan() {
        pbKependudukan2.setVisibility(View.VISIBLE);
        String url = AppConfig.URL_PENDUDUK_BY_PEKERJAAN;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            listKependudukan.clear();
                            recyclerView.setVisibility(View.VISIBLE);

                            JSONObject obj = new JSONObject(response);
                            JSONArray ar = obj.getJSONArray("kependudukan");

                            if (page == 1){
                                for (int i = 0; i < 15; i++) {
                                    JSONObject object = ar.getJSONObject(i);
                                    listKependudukan.add(new Kependudukan(
                                            object.getInt("JUMLAH"),
                                            object.getString("PEKERJAAN")
                                    ));
                                }
                            } else if (page == 2){
                                for (int i = 15; i < 30; i++) {
                                    JSONObject object = ar.getJSONObject(i);
                                    listKependudukan.add(new Kependudukan(
                                            object.getInt("JUMLAH"),
                                            object.getString("PEKERJAAN")
                                    ));
                                }
                            } else if (page == 3) {
                                for (int i = 30; i < 45; i++) {
                                    JSONObject object = ar.getJSONObject(i);
                                    listKependudukan.add(new Kependudukan(
                                            object.getInt("JUMLAH"),
                                            object.getString("PEKERJAAN")
                                    ));
                                }
                            } else if (page == 4) {
                                for (int i = 45; i < 60; i++) {
                                    JSONObject object = ar.getJSONObject(i);
                                    listKependudukan.add(new Kependudukan(
                                            object.getInt("JUMLAH"),
                                            object.getString("PEKERJAAN")
                                    ));
                                }
                            } else if (page == 5) {
                                for (int i = 60; i < 75; i++) {
                                    JSONObject object = ar.getJSONObject(i);
                                    listKependudukan.add(new Kependudukan(
                                            object.getInt("JUMLAH"),
                                            object.getString("PEKERJAAN")
                                    ));
                                }
                            } else{
                                for (int i = 75; i < ar.length(); i++) {
                                    JSONObject object = ar.getJSONObject(i);
                                    listKependudukan.add(new Kependudukan(
                                            object.getInt("JUMLAH"),
                                            object.getString("PEKERJAAN")
                                    ));
                                }
                            }

                            KependudukanPekerjaanAdapter adapter = new KependudukanPekerjaanAdapter(KependudukanActivity.this, listKependudukan);
                            recyclerView.setAdapter(adapter);
                            pbKependudukan2.setVisibility(View.GONE);

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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    // merequest GET API dengan bantuan volley
    public void getAllKecamatan(){
        String url = AppConfig.URL_PENDUDUK_KECAMATAN;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray ar = obj.getJSONArray("kependudukan");

                            ArrayList<String> responseList = new ArrayList<>();

                            for (int i = 0; i < ar.length(); i++) {
                                final JSONObject object = ar.getJSONObject(i);
                                responseList.add(object.getString("NAMA_KECAMATAN"));
                            }
                            ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.layout_spinner, R.id.textView3, responseList);
                            spinnerKecamatan.setAdapter(unitAdapter);

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
        Volley.newRequestQueue(this).add(stringRequest);
    }

    // Drill-down dari Pie Chart yang mendapatkan data dengan merequest GET API dengan bantuan volley
    private void loadDetailAgama() {
        pbKependudukan2.setVisibility(View.VISIBLE);
        kecamatan = spinnerKecamatan.getSelectedItem().toString();
        String url = AppConfig.URL_PENDUDUK_BY_AGAMA_DETAIL + "kecamatan=" + kecamatan;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            listKependudukan.clear();
                            recyclerView.setVisibility(View.VISIBLE);

                            JSONObject obj = new JSONObject(response);
                            JSONArray ar = obj.getJSONArray("kependudukan");

                                for (int i = 0; i < ar.length(); i++) {
                                    JSONObject object = ar.getJSONObject(i);
                                    listKependudukan.add(new Kependudukan(
                                            object.getString("AGAMA"),
                                            object.getInt("JUMLAH")
                                    ));
                                }
                                KependudukanAgamaAdapter adapter = new KependudukanAgamaAdapter(KependudukanActivity.this, listKependudukan);
                                recyclerView.setAdapter(adapter);
                                pbKependudukan2.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(KependudukanActivity.this, "tes", Toast.LENGTH_SHORT).show();

                    }
                }) {
        };
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    // Tampilan markerview apabila diagram di click
    public class CustomMarkerView extends MarkerView {

        private TextView tvContent;
        public CustomMarkerView (Context context, int layoutResource) {
            super(context, layoutResource);
            // this markerview only displays a textview
            tvContent = (TextView) findViewById(R.id.valueContent);
        }

        // callbacks everytime the MarkerView is redrawn, can be used to update the
        // content (user-interface)
        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            int value = (int) e.getY();

            tvContent.setText("Jumlah: " + formatter.format(value)); // set the entry-value as the display text

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
    }

    // Screenshot view berdasarkan bitmap
    public void takeSS(View v){
        Date now  = new Date();

        try {

            File folder = new File(Environment.getExternalStorageDirectory().toString()+"/ExecutiveDashboard/");
            folder.mkdirs();
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/ExecutiveDashboard/Penduduk " + now + ".jpeg";

            // create bitmap screen capture
            v.setDrawingCacheEnabled(true);
//            Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
//            v.setDrawingCacheEnabled(false);

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
            ssKependudukan.setImageBitmap(ssbitmap);
            sharePath = filePath;

        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    // method share diagram
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

    // method membuat bitmap dari view
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

        Intent i = new Intent(KependudukanActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
