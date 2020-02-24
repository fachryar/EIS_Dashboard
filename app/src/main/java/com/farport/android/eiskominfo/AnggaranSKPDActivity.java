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
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnggaranSKPDActivity extends AppCompatActivity {
    ImageButton btnLeftS, btnRightS, btnSearchSKPD, shareAnggaran1, shareAnggaran2;
    ImageView ssAnggaran;
    TextView textTahunS, text21, textTotalUsulan, text22, text23;
    AutoCompleteTextView textSearchSKPD;
    PieChart pieChartSKPD;
    CardView cardViewA1, cardViewA2, cardTitleAnggaran;
    ProgressBar progressBar1, progressBar2;

    private String sharePath="no";

    private RecyclerView recyclerView1;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<AnggaranSKPD> listAnggaranSKPD;
    private List<AnggaranSKPD> listAnggaranSKPD2;

    String skpda = "";
    String skpdb = "";
    int tahun = Calendar.getInstance().get(Calendar.YEAR);
    int tahunini = Calendar.getInstance().get(Calendar.YEAR);
    int[] blueYellowRed = new int[]{
            Color.parseColor("#3b8ead"),
            Color.parseColor("#ffc20e"),
            Color.parseColor("#d22940")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anggaran_skpd);

        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        listAnggaranSKPD = new ArrayList<>();
        listAnggaranSKPD2 = new ArrayList<>();

        pieChartSKPD = (PieChart) findViewById(R.id.pieChartSKPD);
        btnLeftS = (ImageButton) findViewById(R.id.btnLeftS);
        btnRightS = (ImageButton) findViewById(R.id.btnRightS);
        btnSearchSKPD = (ImageButton) findViewById(R.id.btnSearchSKPD);
        textSearchSKPD = (AutoCompleteTextView) findViewById(R.id.textSearchSKPD);
        textTahunS = (TextView) findViewById(R.id.textTahunS);
        textTotalUsulan = (TextView) findViewById(R.id.textTotalUsulan);
        text21 = (TextView) findViewById(R.id.text21);
        text22 = (TextView) findViewById(R.id.text22);
        text23 = (TextView) findViewById(R.id.text23);
        ssAnggaran = (ImageView) findViewById(R.id.ssAnggaran);
        shareAnggaran1 = (ImageButton) findViewById(R.id.shareAnggaran1);
        shareAnggaran2 = (ImageButton) findViewById(R.id.shareAnggaran2);
        cardViewA1 = (CardView) findViewById(R.id.cardViewA1);
        cardViewA2 = (CardView) findViewById(R.id.cardViewA2);
        cardTitleAnggaran = (CardView) findViewById(R.id.cardTitleAnggaran);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);

        textTotalUsulan.setVisibility(View.GONE);
        shareAnggaran1.setVisibility(View.GONE);
        shareAnggaran2.setVisibility(View.GONE);
        text21.setText("Anggaran Usulan Semua SKPD Tahun "+tahunini+"\n(Triliun Rp.)");
        text22.setText("Rincian anggaran usulan SKPD tahun "+tahun);
        textSearchSKPD.setFocusableInTouchMode(false);
        textSearchSKPD.setFocusable(false);
        textSearchSKPD.setFocusableInTouchMode(true);
        textSearchSKPD.setFocusable(true);

        getListSkpd();

        {
            pieChartSKPD.setTouchEnabled(true);
            pieChartSKPD.setUsePercentValues(true);
            pieChartSKPD.getDescription().setEnabled(false);
            pieChartSKPD.setHoleRadius(20f);
            pieChartSKPD.setTransparentCircleRadius(25f);
            pieChartSKPD.getLegend().setTextSize(10f);
            pieChartSKPD.setHighlightPerTapEnabled(true);
            pieChartSKPD.setExtraOffsets(0, 15, 0, 30);
            pieChartSKPD.setEntryLabelColor(Color.BLACK);
            pieChartSKPD.setEntryLabelTextSize(10f);
            pieChartSKPD.setVisibility(View.INVISIBLE);
            pieChartSKPD.animateY(1000, Easing.EasingOption.EaseInOutCirc);

            Legend l = pieChartSKPD.getLegend();
            l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
            l.setWordWrapEnabled(true);
        }

        loadDataPieChart();
        loadRecyclerView1();

        cardTitleAnggaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnggaranSKPDActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnLeftS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tahun == 2018){
                    Toast.makeText(AnggaranSKPDActivity.this, "Tahun tidak ditemukan", Toast.LENGTH_SHORT).show();
                }else {
                    pieChartSKPD.setVisibility(View.INVISIBLE);
                    recyclerView1.setAdapter(null);
                    textTotalUsulan.setVisibility(View.GONE);
                    shareAnggaran1.setVisibility(View.GONE);
                    shareAnggaran2.setVisibility(View.GONE);

                    tahun = tahun - 1;
                    textTahunS.setText(String.valueOf(tahun));
                    text21.setText("Anggaran Usulan Semua SKPD Tahun " + tahun + "\n(Triliun Rp.)");
                    text22.setText("Rincian anggaran usulan SKPD tahun " + tahun);
//                text23.setText("Rincian anggaran urusan wajib tahun "+tahun);

                    loadDataPieChart();
                    loadRecyclerView1();
                }
            }
        });

        btnRightS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tahun >= tahunini){
                    Toast.makeText(AnggaranSKPDActivity.this, "Tahun tidak ditemukan", Toast.LENGTH_SHORT).show();
                }else{
                    pieChartSKPD.setVisibility(View.INVISIBLE);
                    recyclerView1.setAdapter(null);
                    textTotalUsulan.setVisibility(View.GONE);
                    shareAnggaran1.setVisibility(View.GONE);
                    shareAnggaran2.setVisibility(View.GONE);

                    tahun = tahun + 1;
                    textTahunS.setText(String.valueOf(tahun));
                    text21.setText("Anggaran Usulan Semua SKPD Tahun "+tahun+"\n(Triliun Rp.)");
                    text22.setText("Rincian anggaran usulan SKPD tahun "+tahun);
//                    text23.setText("Rincian anggaran urusan wajib tahun "+tahun);

                    loadDataPieChart();
                    loadRecyclerView1();
                }
            }
        });

        btnSearchSKPD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView1.setAdapter(null);
                shareAnggaran2.setVisibility(View.GONE);

                skpdb = textSearchSKPD.getText().toString().trim();

                if (TextUtils.isEmpty(skpdb)){
                    skpdb = " ";
                }

                text21.setText("Anggaran Usulan Semua SKPD Tahun "+tahun+"\n(Triliun Rp.)");
                text22.setText("Rincian anggaran usulan SKPD tahun "+tahun);
                loadRecyclerView1();
            }
        });

        shareAnggaran1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AnggaranSKPDActivity.this)
                        .setTitle("Share Diagram Anggaran Usulan SKPD?")
                        .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                takeSS(cardViewA1);

                                if (!sharePath.equals("no")) {
                                    share(sharePath);
                                } else {
                                    Toast.makeText(AnggaranSKPDActivity.this, "Bitmap Error", Toast.LENGTH_SHORT).show();
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

        shareAnggaran2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AnggaranSKPDActivity.this)
                        .setTitle("Share Rincian Anggaran Usulan SKPD?")
                        .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                takeSS(cardViewA2);

                                if (!sharePath.equals("no")) {
                                    share(sharePath);
                                } else {
                                    Toast.makeText(AnggaranSKPDActivity.this, "Bitmap Error", Toast.LENGTH_SHORT).show();
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

    public void loadDataPieChart() {
        progressBar1.setVisibility(View.VISIBLE);
        String url = AppConfig.URL_APBD_POST;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
            public void onResponse(String response) {
                try{
                    pieChartSKPD.invalidate();
                    pieChartSKPD.clear();
                    pieChartSKPD.notifyDataSetChanged();

                    JSONObject object = new JSONObject(response);
                    JSONArray arr_rds = object.getJSONArray("anggaranskpd");
                    JSONObject obj = arr_rds.getJSONObject(0);

                    Double usulan_pd = obj.getDouble("ANGGARAN_PD")/1E12;
                    Double usulan_kec = obj.getDouble("ANGGARAN_KECAMATAN")/1E12;
                    Double usulan_dprd = obj.getDouble("ANGGARAN_DPRD")/1E12;
                    Double usulan_total = obj.getDouble("TOTAL_ANGGARAN_APBD")/1E12;

                    ArrayList<PieEntry> yValues = new ArrayList<>();

                    yValues.add(new PieEntry(usulan_pd.floatValue(), "PEMERINTAH DAERAH"));
                    yValues.add(new PieEntry(usulan_kec.floatValue(), "KECAMATAN"));
                    yValues.add(new PieEntry(usulan_dprd.floatValue(), "DPRD"));

                    PieDataSet dataSet = new PieDataSet(yValues, "");
                    dataSet.setSelectionShift(2f);
                    dataSet.setSliceSpace(3f);
                    dataSet.setValueTextColor(Color.BLACK);
                    dataSet.setColors(blueYellowRed);
                    dataSet.setValueLineColor(Color.BLUE);
                    dataSet.setValueLinePart1Length(0.8f);
                    dataSet.setValueLinePart2Length(0.5f);
                    dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    dataSet.setValueFormatter(new PercentFormatter());

                    PieData data = new PieData(dataSet);
                    data.setValueTextSize(10f);
                    data.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                    DecimalFormat df = new DecimalFormat("##.00");
                    textTotalUsulan.setText("Total APBD = "+df.format(usulan_total)+"T");
                    textTotalUsulan.setVisibility(View.VISIBLE);
                    shareAnggaran1.setVisibility(View.VISIBLE);
                    shareAnggaran2.setVisibility(View.VISIBLE);

                    AnggaranSKPDActivity.CustomMarkerView mv = new AnggaranSKPDActivity.CustomMarkerView(AnggaranSKPDActivity.this, R.layout.content_value);
                    pieChartSKPD.setMarkerView(mv);
                    pieChartSKPD.setData(data);
                    pieChartSKPD.setVisibility(View.VISIBLE);
                    pieChartSKPD.animateY(1000, Easing.EasingOption.EaseInOutCirc);
                    progressBar1.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tahun", String.valueOf(tahun));
                params.put("skpd", skpda);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void loadRecyclerView1() {
        progressBar2.setVisibility(View.VISIBLE);
        String url = AppConfig.URL_APBD_POST;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            listAnggaranSKPD.clear();
                            shareAnggaran1.setVisibility(View.VISIBLE);
                            shareAnggaran2.setVisibility(View.VISIBLE);

                            JSONObject obj = new JSONObject(response);
                            JSONArray ar = obj.getJSONArray("anggaranskpd");

                            //traversing through all the object
                            for (int i = 0; i < ar.length(); i++) {

                                //getting product object from json array
                                JSONObject object = ar.getJSONObject(i);

                                //adding the product to product list
                                listAnggaranSKPD.add(new AnggaranSKPD(
                                        object.getInt("ID_SKPD"),
                                        object.getInt("TAHUN"),
                                        object.getString("NAMA_SKPD"),
                                        object.getDouble("TOTAL_ANGGARAN_APBD")
                                ));
                            }
                            //creating adapter object and setting it to recyclerview
                            AnggaranSKPDAdapter adapter = new AnggaranSKPDAdapter(AnggaranSKPDActivity.this, listAnggaranSKPD);
                            recyclerView1.setAdapter(adapter);
                            progressBar2.setVisibility(View.GONE);
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
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tahun", String.valueOf(tahun));
                params.put("skpd", skpdb);
                return params;
            }
        };
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void getListSkpd() {
        String url = AppConfig.URL_LIST_SKPD + "tahun="+tahun;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            listAnggaranSKPD.clear();

                            JSONObject obj = new JSONObject(response);
                            JSONArray ar = obj.getJSONArray("anggaranskpd");

                            ArrayList<String> responseList = new ArrayList<>();

                            for (int i = 0; i < ar.length(); i++) {

                                final JSONObject object = ar.getJSONObject(i);
                                responseList.add(object.getString("NAMA_SKPD"));
                            }
                            ArrayAdapter<String> skpdAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.layout_search, R.id.textView13SP, responseList);
                            textSearchSKPD.setAdapter(skpdAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
            axis.setLabelCount(5);
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
            DecimalFormat df = new DecimalFormat("#0.00");
            tvContent.setText("Anggaran: " + df.format(e.getY()) + "T"); // set the entry-value as the display text

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

    public void takeSS(View v){
        Date now  = new Date();

        try {

            File folder = new File(Environment.getExternalStorageDirectory().toString()+"/ExecutiveDashboard/");
            folder.mkdirs();
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/ExecutiveDashboard/Usulan " + now + ".jpeg";

            // create bitmap screen capture
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
            ssAnggaran.setImageBitmap(ssbitmap);
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

        Log.d("wat",sharePath);
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

        Intent i = new Intent(AnggaranSKPDActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
