package com.farport.android.eiskominfo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.Image;
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
import android.widget.Button;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

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
import java.util.List;

public class PajakActivity extends AppCompatActivity {
    ImageButton btnLeft, btnRight, sharePajak1, sharePajak2;
    TextView textTahun, textSumber, textTotal;
    CardView cardViewP1, cardViewP2, cardTitlePajak;
    ImageView ssPajak;
    ProgressBar pbPajak1, pbPajak2;
    private String sharePath="no";

    private LineChart lineChartTotalPajak;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<Pajak> listTargetPajak;
    int tahun = Calendar.getInstance().get(Calendar.YEAR);;
    int tahunini = Calendar.getInstance().get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pajak);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewTargetPajak);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listTargetPajak = new ArrayList<>();

        btnLeft = (ImageButton) findViewById(R.id.btnLeft);
        btnRight = (ImageButton) findViewById(R.id.btnRight);
        textTahun = (TextView) findViewById(R.id.textTahun);
        textSumber = (TextView) findViewById(R.id.textSumber);
        textTotal = (TextView) findViewById(R.id.textTotal);
        sharePajak1 = (ImageButton) findViewById(R.id.sharePajak1);
        sharePajak2 = (ImageButton) findViewById(R.id.sharePajak2);
        ssPajak = (ImageView) findViewById(R.id.ssPajak);
        cardViewP1 = (CardView) findViewById(R.id.cardViewP1);
        cardViewP2 = (CardView) findViewById(R.id.cardViewP2);
        cardTitlePajak = (CardView) findViewById(R.id.cardTitlePajak);
        pbPajak1 = (ProgressBar) findViewById(R.id.pbPajak1);
        pbPajak2 = (ProgressBar) findViewById(R.id.pbPajak2);

        textTotal.setText("Penghasilan Pajak Perbulan Kabupaten Sidoarjo \n(Miliar Rp.)\nTahun "+tahun);
        textSumber.setText("Sumber Pendapatan Pajak Kabupaten Sidoarjo \n(Miliar Rp.)\nTahun "+tahun);
        sharePajak1.setVisibility(View.GONE);
        sharePajak2.setVisibility(View.GONE);

        {
            lineChartTotalPajak = (LineChart) findViewById(R.id.lineChartTotalPajak);
            lineChartTotalPajak.setDrawGridBackground(false);
            lineChartTotalPajak.setDrawBorders(false);
            lineChartTotalPajak.getDescription().setEnabled(false);

            lineChartTotalPajak.setDragEnabled(true);
            lineChartTotalPajak.setTouchEnabled(true);
            lineChartTotalPajak.setScaleEnabled(true);
            lineChartTotalPajak.setPinchZoom(true);
            lineChartTotalPajak.setDoubleTapToZoomEnabled(false);
            lineChartTotalPajak.getAxisRight().setEnabled(false);

            lineChartTotalPajak.animateY(1000, Easing.EasingOption.Linear);
            lineChartTotalPajak.setVisibility(View.INVISIBLE);

            Legend l = lineChartTotalPajak.getLegend();
            l.setEnabled(false);

//            LimitLine upper_limit = new LimitLine(60f, "Target");
//                        upper_limit.setLineWidth(2f);
//                        upper_limit.enableDashedLine(10f, 10f, 0f);
//                        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//                        upper_limit.setTextSize(10f);

            YAxis leftAxis = lineChartTotalPajak.getAxisLeft();
            leftAxis.removeAllLimitLines();
            leftAxis.setAxisMinimum(0f);
            leftAxis.setSpaceTop(75f);
            //leftAxis.addLimitLine(upper_limit);
            //leftAxis.enableGridDashedLine(10f,  10f, 0);
            leftAxis.setDrawLimitLinesBehindData(false);
        }

        loadDataLineChart();
        loadRecyclerViewTargetPajak();

        cardTitlePajak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PajakActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tahun == 2007){
                    Toast.makeText(PajakActivity.this, "Tahun tidak ditemukan", Toast.LENGTH_SHORT).show();
                }else {
                    lineChartTotalPajak.setVisibility(View.INVISIBLE);
                    recyclerView.setAdapter(null);

                    tahun = tahun - 1;
                    textTahun.setText(String.valueOf(tahun));
                    textTotal.setText("Penghasilan Pajak Perbulan Kabupaten Sidoarjo \n(Miliar Rp.)\nTahun " + tahun);
                    textSumber.setText("Sumber Pendapatan Pajak Kabupaten Sidoarjo \n(Miliar Rp.)\nTahun " + tahun);
                    sharePajak1.setVisibility(View.GONE);
                    sharePajak2.setVisibility(View.GONE);

                    loadDataLineChart();
                    loadRecyclerViewTargetPajak();
                }
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tahun >= tahunini){
                    Toast.makeText(PajakActivity.this, "Tahun tidak ditemukan", Toast.LENGTH_SHORT).show();
                }else{
                    lineChartTotalPajak.setVisibility(View.INVISIBLE);
                    recyclerView.setAdapter(null);

                    tahun = tahun + 1;
                    textTahun.setText(String.valueOf(tahun));
                    textTotal.setText("Penghasilan Pajak Perbulan Kabupaten Sidoarjo \n(Miliar Rp.)\nTahun "+tahun);
                    textSumber.setText("Sumber Pendapatan Pajak Kabupaten Sidoarjo \n(Miliar Rp.)\nTahun "+tahun);
                    sharePajak1.setVisibility(View.GONE);
                    sharePajak2.setVisibility(View.GONE);

                    loadDataLineChart();
                    loadRecyclerViewTargetPajak();   
                }
            }
        });

        sharePajak1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(PajakActivity.this)
                        .setTitle("Share Penghasilan Pajak?")
                        .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                takeSS(cardViewP1);

                                if (!sharePath.equals("no")) {
                                    share(sharePath);
                                } else {
                                    Toast.makeText(PajakActivity.this, "Bitmap Error", Toast.LENGTH_SHORT).show();
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

        sharePajak2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(PajakActivity.this)
                        .setTitle("Share Pencapaian Target Pajak?")
                        .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                takeSS(cardViewP2);

                                if (!sharePath.equals("no")) {
                                    share(sharePath);
                                } else {
                                    Toast.makeText(PajakActivity.this, "Bitmap Error", Toast.LENGTH_SHORT).show();
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

    public void loadDataLineChart() {
        pbPajak1.setVisibility(View.VISIBLE);
        String url = AppConfig.URL_PENDAPATAN_ALL + tahun;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                try{
                        lineChartTotalPajak.invalidate();
                        lineChartTotalPajak.clear();
                        lineChartTotalPajak.notifyDataSetChanged();

                        JSONObject o = new JSONObject(response);
                        JSONArray arr_pajak = o.getJSONArray("pajak");

                        ArrayList<Entry> yValues = new ArrayList<>();
                        for (int i = 0; i < arr_pajak.length(); i++){
                            JSONObject bulan = arr_pajak.getJSONObject(i);
                            Double totalPajak = bulan.getDouble("TOTAL_PENDAPATAN_PAJAK")/1E9;
                            yValues.add(new Entry(i, totalPajak.floatValue()));
                        }

                        LineDataSet set1 = new LineDataSet(yValues, "Penghasilan Pajak Perbulan");
                        set1.setValueTextSize(10f);
                        set1.setColor((Color.parseColor("#3b8ead")));
                        set1.setLineWidth(2f);
                        set1.setCircleRadius(3f);
                        set1.setCircleHoleColor((Color.parseColor("#ffc20e")));
                        set1.setCircleColor((Color.parseColor("#ffc20e")));
                        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                        set1.setDrawValues(false);

                        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                        dataSets.add(set1);
                        LineData data = new LineData(dataSets);

                        PajakActivity.CustomMarkerView mv = new PajakActivity.CustomMarkerView(PajakActivity.this, R.layout.content_value);
                        lineChartTotalPajak.setMarkerView(mv);
                        lineChartTotalPajak.setData(data);
                        lineChartTotalPajak.setVisibility(View.VISIBLE);
                        lineChartTotalPajak.animateY(1000, Easing.EasingOption.Linear);

                        String[] values = new String[]{"Jan","Feb","Mar","Apr","Mei","Jun","Jul","Agu","Sep","Okt","Nov","Des"};
                        XAxis xAxis = lineChartTotalPajak.getXAxis();
                        xAxis.setValueFormatter(new XAxisValueFormatter(values));
                        xAxis.setTextSize(10);
                        xAxis.setGranularity(1f);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        pbPajak1.setVisibility(View.GONE);
                        sharePajak1.setVisibility(View.VISIBLE);

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

    private void loadRecyclerViewTargetPajak() {
        pbPajak2.setVisibility(View.VISIBLE);
        String url = AppConfig.URL_TARGET_PAJAK + tahun;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            listTargetPajak.clear();

                            JSONObject obj = new JSONObject(response);
                            JSONArray ar = obj.getJSONArray("pajak");

                            //traversing through all the object
                            for (int i = 0; i < ar.length(); i++) {

                                //getting product object from json array
                                JSONObject object = ar.getJSONObject(i);

                                //adding the product to product list
                                listTargetPajak.add(new Pajak(
                                        object.getString("ID_JENIS_PAJAK"),
                                        object.getString("JENIS_PAJAK"),
                                        object.getInt("TAHUN"),
                                        object.getDouble("TOTAL_PAJAK"),
                                        object.getDouble("TARGET_TAHUN_INI")
                                ));
                            }
                            //creating adapter object and setting it to recyclerview
                            TargetPajakAdapter adapter = new TargetPajakAdapter(PajakActivity.this, listTargetPajak);
                            recyclerView.setAdapter(adapter);
                            pbPajak2.setVisibility(View.GONE);
                            sharePajak2.setVisibility(View.VISIBLE);
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

    public class XAxisValueFormatter implements IAxisValueFormatter{
        private String[] xValues;
        public XAxisValueFormatter(String[] values){
            this.xValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            axis.setLabelCount(12);
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
            tvContent.setText("Jumlah: " + df.format(e.getY()) + "M"); // set the entry-value as the display text

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
//        DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {

            File folder = new File(Environment.getExternalStorageDirectory().toString()+"/ExecutiveDashboard/");
            folder.mkdirs();
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/ExecutiveDashboard/Pajak " + now + ".jpeg";

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
            ssPajak.setImageBitmap(ssbitmap);
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

        Intent i = new Intent(PajakActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

}


