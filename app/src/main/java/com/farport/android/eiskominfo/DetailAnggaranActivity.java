package com.farport.android.eiskominfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailAnggaranActivity extends AppCompatActivity {
    TextView textViewSKPD, textTotalAnggaran;
    ImageButton backAnggaran;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<AnggaranSKPD> listAnggaranSKPD;

    String id_skpd = "";
    String tahun = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_anggaran);

        textViewSKPD = (TextView) findViewById(R.id.textViewSKPD);
        textTotalAnggaran = (TextView) findViewById(R.id.textTotalAnggaran);
        backAnggaran = (ImageButton) findViewById(R.id.backAnggaran);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listAnggaranSKPD = new ArrayList<>();

        final AnggaranSKPD myList = (AnggaranSKPD) getIntent().getExtras().getSerializable("myList");
        DecimalFormat df = new DecimalFormat("###,###,###,###,###,###");
        id_skpd = String.valueOf(myList.getId_skpd());
        tahun = String.valueOf(myList.getTahun());

        textViewSKPD.setText(myList.getKategori());
        textTotalAnggaran.setText("Total Anggaran Tahun "+tahun+"\nRp. "+String.valueOf(df.format(myList.getTotal()))+",00");

        loadRecyclerView();

        backAnggaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailAnggaranActivity.this, AnggaranSKPDActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void loadRecyclerView() {
        String url = AppConfig.URL_PROGRAM_SKPD+"tahun="+tahun+"&id_skpd="+id_skpd;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray ar = obj.getJSONArray("anggaranskpd");

                            for (int i = 0; i < ar.length(); i++) {

                                JSONObject object = ar.getJSONObject(i);
                                listAnggaranSKPD.add(new AnggaranSKPD(
                                        object.getString("PROGRAM"),
                                        object.getString("INDIKATOR"),
                                        object.getString("TARGET"),
                                        object.getDouble("DANA")
                                ));
                            }
                            //creating adapter object and setting it to recyclerview
                            ProgramSKPDAdapter adapter = new ProgramSKPDAdapter(DetailAnggaranActivity.this, listAnggaranSKPD);
                            recyclerView.setAdapter(adapter);
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
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(DetailAnggaranActivity.this, AnggaranSKPDActivity.class);
        startActivity(i);
        finish();
    }
}
