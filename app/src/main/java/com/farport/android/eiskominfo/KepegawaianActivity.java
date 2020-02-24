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
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KepegawaianActivity extends AppCompatActivity {
    private String sharePath="no";
    PieChart pieChartKepegawaian;
    BarChart barChartKepegawaian;
    ImageButton btnUsia, btnKelamin, leftGol, rightGol, imageSearch, shareKepegawaian1, shareKepegawaian2;
    ImageView ssKepegawaian;
    TextView textTotalPegawai, textJumlahPegawai, textGol;
    ProgressBar pbKepegawaian1, pbKepegawaian2;
    CardView cardView1, cardView2, cardTitleKepegawaian;

    String kategori="Jenis Kelamin";
    int golint=1;
    String gol="i";
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
            Color.parseColor("#EF6C00")
    };
    String[] jenisKelamin = new String[]{"Pria","Wanita"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kepegawaian);

        pieChartKepegawaian = (PieChart) findViewById(R.id.pieChartKepegawaian);
        barChartKepegawaian = (BarChart) findViewById(R.id.barChartKepegawaian);
        btnKelamin = (ImageButton) findViewById(R.id.btnKelamin);
        btnUsia = (ImageButton) findViewById(R.id.btnUsia);
        textTotalPegawai = (TextView) findViewById(R.id.textTotalPegawai);
        textJumlahPegawai = (TextView) findViewById(R.id.textJumlahPegawai);
        textGol = (TextView) findViewById(R.id.textGol);
        leftGol = (ImageButton) findViewById(R.id.leftGol);
        rightGol = (ImageButton) findViewById(R.id.rightGol);
        imageSearch = (ImageButton) findViewById(R.id.imageSearch);
        shareKepegawaian1 = (ImageButton) findViewById(R.id.shareKepegawaian1);
        shareKepegawaian2 = (ImageButton) findViewById(R.id.shareKepegawaian2);
        pbKepegawaian1 = (ProgressBar) findViewById(R.id.pbKepegawaian1);
        pbKepegawaian2 = (ProgressBar) findViewById(R.id.pbKepegawaian2);
        cardView1 = (CardView) findViewById(R.id.cardView1);
        cardView2 = (CardView) findViewById(R.id.cardView2);
        cardTitleKepegawaian = (CardView) findViewById(R.id.cardTitleKepegawaian);
        ssKepegawaian = (ImageView) findViewById(R.id.ssKepegawaian);

        shareKepegawaian1.setVisibility(View.GONE);
        shareKepegawaian2.setVisibility(View.GONE);

        {
            //PIE CHART
            {
                pieChartKepegawaian.setTouchEnabled(true);
                pieChartKepegawaian.setUsePercentValues(true);
                pieChartKepegawaian.getDescription().setEnabled(false);
                pieChartKepegawaian.setHoleRadius(20f);
                pieChartKepegawaian.setTransparentCircleRadius(25f);
                pieChartKepegawaian.getLegend().setTextSize(10f);
                pieChartKepegawaian.setHighlightPerTapEnabled(true);
                pieChartKepegawaian.setExtraOffsets(0, 15, 0, 30);
                pieChartKepegawaian.setEntryLabelColor(Color.BLACK);
                pieChartKepegawaian.setEntryLabelTextSize(10f);
                pieChartKepegawaian.setVisibility(View.INVISIBLE);
                pieChartKepegawaian.animateY(1000, Easing.EasingOption.EaseInOutCirc);

                Legend l = pieChartKepegawaian.getLegend();
                l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
                l.setWordWrapEnabled(true);
            }

            loadDataPieChartByKel();

            imageSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(KepegawaianActivity.this, SearchPegawai.class);
                    startActivity(i);
                }
            });

            btnKelamin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnKelamin.setImageResource(R.drawable.selgender2);
                    btnUsia.setImageResource(R.drawable.selusia1);
                    pieChartKepegawaian.setVisibility(View.INVISIBLE);
                    shareKepegawaian1.setVisibility(View.GONE);
                    kategori = "Jenis Kelamin";
                    textTotalPegawai.setText("Persentase Jumlah Pegawai Pemkab Sidoarjo Berdasarkan " + kategori);
                    loadDataPieChartByKel();
                }
            });

            btnUsia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnKelamin.setImageResource(R.drawable.selgender1);
                    btnUsia.setImageResource(R.drawable.selusia2);
                    pieChartKepegawaian.setVisibility(View.INVISIBLE);
                    shareKepegawaian1.setVisibility(View.GONE);
                    kategori = "Rentang Umur";
                    textTotalPegawai.setText("Persentase Jumlah Pegawai Pemkab Sidoarjo Berdasarkan " + kategori);
                    loadDataPieChartByUsia();
                }
            });
        }

        {
            //BAR CHART
            {
                barChartKepegawaian.setVisibility(View.INVISIBLE);
                barChartKepegawaian.setTouchEnabled(true);
                barChartKepegawaian.setDrawBorders(false);
                barChartKepegawaian.getDescription().setEnabled(false);
                barChartKepegawaian.getAxisRight().setEnabled(false);
                barChartKepegawaian.setExtraBottomOffset(30);
                barChartKepegawaian.animateY(1000, Easing.EasingOption.Linear);

                Legend l = barChartKepegawaian.getLegend();
                l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
                l.setWordWrapEnabled(true);

                loadDataBarChart();

                YAxis leftAxis = barChartKepegawaian.getAxisLeft();
                leftAxis.removeAllLimitLines();
                leftAxis.setAxisMinimum(0f);
                leftAxis.setSpaceTop(25);
                leftAxis.setDrawLimitLinesBehindData(false);

                leftGol.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (golint == 1){
                            Toast.makeText(KepegawaianActivity.this, "Golongan tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }else{
                            barChartKepegawaian.setVisibility(View.INVISIBLE);
                            shareKepegawaian2.setVisibility(View.GONE);
                            golint = golint - 1;
                            if (golint == 3){
                                gol = "iii";
                                textGol.setText("Golongan III");
                                loadDataBarChart();
                            }else if (golint == 2){
                                gol = "ii";
                                textGol.setText("Golongan II");
                                loadDataBarChart();
                            }else if (golint == 1){
                                gol = "i";
                                textGol.setText("Golongan I");
                                loadDataBarChart();
                            }
                        }
                    }
                });

                rightGol.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (golint == 4) {
                            Toast.makeText(KepegawaianActivity.this, "Golongan tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }else{
                            barChartKepegawaian.setVisibility(View.INVISIBLE);
                            shareKepegawaian2.setVisibility(View.GONE);
                            golint = golint + 1;
                            if (golint == 2){
                                gol = "ii";
                                textGol.setText("Golongan II");
                                loadDataBarChart();
                            }else if (golint == 3) {
                                gol = "iii";
                                textGol.setText("Golongan III");
                                loadDataBarChart();
                            }else if (golint == 4){
                                gol = "iv";
                                textGol.setText("Golongan IV");
                                loadDataBarChart();
                            }
                        }
                    }
                });

            }
        }

        shareKepegawaian1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(KepegawaianActivity.this)
                        .setTitle("Share Pie Chart Kepegawaian?")
                        .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                takeSS(cardView1);

                                if (!sharePath.equals("no")) {
                                    share(sharePath);
                                } else {
                                    Toast.makeText(KepegawaianActivity.this, "Bitmap Error", Toast.LENGTH_SHORT).show();
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

        shareKepegawaian2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(KepegawaianActivity.this)
                        .setTitle("Share Barchart Kepegawaian?")
                        .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                takeSS(cardView2);

                                if (!sharePath.equals("no")) {
                                    share(sharePath);
                                } else {
                                    Toast.makeText(KepegawaianActivity.this, "Bitmap Error", Toast.LENGTH_SHORT).show();
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

        cardTitleKepegawaian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KepegawaianActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    public void loadDataPieChartByKel() {
        pbKepegawaian1.setVisibility(View.VISIBLE);
        String url = AppConfig.URL_PEGAWAI_BY_JENIS_KELAMIN;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                try{
                    pieChartKepegawaian.invalidate();
                    pieChartKepegawaian.clear();
                    pieChartKepegawaian.notifyDataSetChanged();

                    JSONObject o = new JSONObject(response);
                    JSONArray arr_bkd = o.getJSONArray("pegawai");

                    ArrayList<PieEntry> yValues = new ArrayList<>();

                    for (int i = 0; i < arr_bkd.length(); i++){
                        JSONObject pegawai = arr_bkd.getJSONObject(i);
                        String kelamin = pegawai.getString("JENIS_KELAMIN");
                        Double jumlah = pegawai.getDouble("JUMLAH");
                        yValues.add(new PieEntry(jumlah.floatValue(), kelamin));
                    }

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

                    CustomMarkerView mv = new CustomMarkerView (KepegawaianActivity.this, R.layout.content_value);
                    pieChartKepegawaian.setMarkerView(mv);
                    pieChartKepegawaian.setData(data);
                    pieChartKepegawaian.setVisibility(View.VISIBLE);
                    pieChartKepegawaian.animateY(1000, Easing.EasingOption.EaseInOutCirc);
                    pbKepegawaian1.setVisibility(View.GONE);
                    shareKepegawaian1.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void loadDataPieChartByUsia() {
        pbKepegawaian1.setVisibility(View.VISIBLE);
        String url = AppConfig.URL_PEGAWAI_BY_USIA;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                try{
                    pieChartKepegawaian.invalidate();
                    pieChartKepegawaian.clear();
                    pieChartKepegawaian.notifyDataSetChanged();

                    JSONObject o = new JSONObject(response);
                    JSONArray arr_bkd = o.getJSONArray("pegawai");

                    ArrayList<PieEntry> yValues = new ArrayList<>();

                    for (int i = 0; i < 6; i++){
                        JSONObject pegawai = arr_bkd.getJSONObject(i);
                        String usia = pegawai.getString("RENTANG_UMUR");
                        Double jumlah = pegawai.getDouble("JUMLAH");
                        yValues.add(new PieEntry(jumlah.floatValue(), usia));
                    }

                    Double dll=0.0;
                    for (int i = 6; i < arr_bkd.length(); i++){
                        JSONObject pegawai = arr_bkd.getJSONObject(i);
                        String usia = pegawai.getString("RENTANG_UMUR");
                        Double jumlah = pegawai.getDouble("JUMLAH");
                        dll = dll+jumlah;
                    }
                    yValues.add(new PieEntry(dll.floatValue(), "DLL"));

                    PieDataSet dataSet = new PieDataSet(yValues, "");
                    //        dataSet.setSelectionShift(2f);
                    dataSet.setSliceSpace(1f);
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

                    CustomMarkerView mv = new CustomMarkerView (KepegawaianActivity.this, R.layout.content_value);
                    pieChartKepegawaian.setMarkerView(mv);
                    pieChartKepegawaian.setData(data);
                    pieChartKepegawaian.setVisibility(View.VISIBLE);
                    pieChartKepegawaian.animateY(1000, Easing.EasingOption.EaseInOutCirc);
                    pbKepegawaian1.setVisibility(View.GONE);
                    shareKepegawaian1.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void loadDataBarChart() {
        pbKepegawaian2.setVisibility(View.VISIBLE);
        String url = AppConfig.URL_PEGAWAI_BY_GOL + gol;
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                try{
                    barChartKepegawaian.invalidate();
                    barChartKepegawaian.clear();
                    barChartKepegawaian.notifyDataSetChanged();

                    JSONObject o = new JSONObject(response);
                    JSONArray ar = o.getJSONArray("pegawai");
                    ArrayList<BarEntry> yValues1 = new ArrayList<>();
                    ArrayList<BarEntry> yValues2 = new ArrayList<>();

                    for (int i = 0; i < ar.length(); i++){
                        JSONObject obj = ar.getJSONObject(i);
                        Double pria = obj.getDouble("JUMLAH_PRIA");
                        Double wanita = obj.getDouble("JUMLAH_WANITA");
                        yValues1.add(new BarEntry(i+1,pria.floatValue()));
                        yValues2.add(new BarEntry(i+1, wanita.floatValue()));
                    }

                    BarDataSet dataSet1 = new BarDataSet(yValues1,"Pria");
                    dataSet1.setColor(Color.parseColor("#3b8ead"));
                    dataSet1.setValueTextSize(10f);

                    BarDataSet dataSet2 = new BarDataSet(yValues2,"Wanita");
                    dataSet2.setColor(Color.parseColor("#ffc20e"));
                    dataSet2.setValueTextSize(10f);

                    BarData data = new BarData(dataSet1, dataSet2);

                    barChartKepegawaian.animateY(1000, Easing.EasingOption.Linear);
                    barChartKepegawaian.setData(data);
                    barChartKepegawaian.setVisibility(View.VISIBLE);
                    pbKepegawaian2.setVisibility(View.GONE);
                    shareKepegawaian2.setVisibility(View.VISIBLE);

                    String[] golpang = new String[ar.length()];
                    for (int i=0; i < ar.length(); i++){
                        JSONObject obj = ar.getJSONObject(i);
                        String golongan = obj.getString("GOLONGAN");
                        golpang[i] = golongan;
                    }

                    XAxis xAxis = barChartKepegawaian.getXAxis();
                    xAxis.removeAllLimitLines();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(golpang));
                    xAxis.removeAllLimitLines();
                    xAxis.setLabelRotationAngle(0);
                    xAxis.setTextSize(10);
                    xAxis.setGranularity(1);
                    xAxis.setCenterAxisLabels(true);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                    if (gol.equals("i")){
                        float barSpace = 0f;
                        float groupSpace = 0.2f;
                        data.setBarWidth(0.4f);

                        barChartKepegawaian.getXAxis().setAxisMinimum(0);
                        barChartKepegawaian.getXAxis().setAxisMaximum(0+barChartKepegawaian.getBarData().getGroupWidth(groupSpace, barSpace)*3);
                        barChartKepegawaian.getAxisLeft().setAxisMinimum(0);
                        barChartKepegawaian.groupBars(0, groupSpace, barSpace);

                    } else if (gol.equals("ii") || gol.equals("iii")){
                        float barSpace = 0f;
                        float groupSpace = 0.2f;
                        data.setBarWidth(0.4f);

                        barChartKepegawaian.getXAxis().setAxisMinimum(0);
                        barChartKepegawaian.getXAxis().setAxisMaximum(0+barChartKepegawaian.getBarData().getGroupWidth(groupSpace, barSpace)*4);
                        barChartKepegawaian.getAxisLeft().setAxisMinimum(0);
                        barChartKepegawaian.groupBars(0, groupSpace, barSpace);

                    } else {
                        float barSpace = 0f;
                        float groupSpace = 0.2f;
                        data.setBarWidth(0.4f);

                        barChartKepegawaian.getXAxis().setAxisMinimum(0);
                        barChartKepegawaian.getXAxis().setAxisMaximum(0+barChartKepegawaian.getBarData().getGroupWidth(groupSpace, barSpace)*5);
                        barChartKepegawaian.getAxisLeft().setAxisMinimum(0);
                        barChartKepegawaian.groupBars(0, groupSpace, barSpace);

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
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public class XAxisValueFormatter implements IAxisValueFormatter {
        private String[] xValues;
        public XAxisValueFormatter(String[] values){
            this.xValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
//            axis.setLabelCount(5);
            return xValues[(int)value];
        }
    }

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
            tvContent.setText("Jumlah: " + (int) e.getY()); // set the entry-value as the display text

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

    public void takeSS(View v){
        Date now  = new Date();

        try {

            File folder = new File(Environment.getExternalStorageDirectory().toString()+"/ExecutiveDashboard/");
            folder.mkdirs();
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/ExecutiveDashboard/Pegawai" + now + ".jpeg";

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
            ssKepegawaian.setImageBitmap(ssbitmap);
            sharePath = filePath;

        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

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

        Intent i = new Intent(KepegawaianActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    //    public void loadDataBarChart() {
//        String url = AppConfig.URL_PEGAWAI_BY_GOL + gol;
//        JsonObjectRequest far = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try{
//                    barChartKepegawaian.invalidate();
//                    barChartKepegawaian.clear();
//                    barChartKepegawaian.notifyDataSetChanged();
//
//                    JSONArray arr_pegawai = response.getJSONArray("pegawai");
//
//                    ArrayList<BarEntry> yValues = new ArrayList<>();
//                    for (int i = 0; i < arr_pegawai.length(); i++){
//                        JSONObject obj = arr_pegawai.getJSONObject(i);
//                        Double jumlahPria = obj.getDouble("JUMLAH_PRIA");
//                        Double jumlahWanita = obj.getDouble("JUMLAH_WANITA");
//                        yValues.add(new BarEntry(i, new float[]{jumlahPria.floatValue(), jumlahWanita.floatValue()}));
//                    }
//
//                    BarDataSet dataSet = new BarDataSet(yValues,"");
//                    dataSet.setColors(blueYellow);
//                    dataSet.setDrawValues(false);
//                    dataSet.setValueTextSize(8f);
//
//                    BarData data = new BarData(dataSet);
//                    data.setBarWidth(0.75f);
//
//                    CustomMarkerView mv = new CustomMarkerView (KepegawaianActivity.this, R.layout.content_value);
//                    barChartKepegawaian.setMarkerView(mv);
//                    barChartKepegawaian.animateY(1000, Easing.EasingOption.Linear);
//                    barChartKepegawaian.setData(data);
//                    barChartKepegawaian.setVisibility(View.VISIBLE);
//
//
//                    Legend l = barChartKepegawaian.getLegend();
//                    List<LegendEntry> entries = new ArrayList<>();
//                    for (int i = 0; i < 2; i++) {
//                        LegendEntry entry = new LegendEntry();
//                        entry.formColor = blueYellow[i];
//                        entry.label = jenisKelamin[i];
//                        entries.add(entry);
//                    }
//                    l.setCustom(entries);
//                    int size = 5;
//                    String[] golpang = new String[size];
//                    for (int i=0; i < arr_pegawai.length(); i++){
//                        JSONObject obj = arr_pegawai.getJSONObject(i);
//                        String golongan = obj.getString("GOLONGAN");
//                        String pangkat = obj.getString("PANGKAT");
//                        golpang[i] = golongan;
//                    }
//
//                    XAxis xAxis = barChartKepegawaian.getXAxis();
//                    xAxis.removeAllLimitLines();
//                    xAxis.setValueFormatter(new IndexAxisValueFormatter(golpang));
//                    xAxis.setTextSize(10);
//                    xAxis.setGranularity(1);
//                    xAxis.setGranularityEnabled(true);
//                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(far);
//    }
}
