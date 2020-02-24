package com.farport.android.eiskominfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchPegawai extends AppCompatActivity {
    CheckBox check1, check2, check3, check4;
    AutoCompleteTextView editUnit;
    EditText editNama, editNIP;
    Button buttonSearch;
    ImageButton backKepegawaian;
    ProgressBar pbSearch;

    String nama = "";
    String golongan = "";
    String skpd = "";
    String nip = "";
    String[] unit;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<Pegawai> listPegawai;
    private List<Pegawai> listUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pegawai);

        editNama = (EditText) findViewById(R.id.editNama);
        editNIP = (EditText) findViewById(R.id.editNIP);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        check1 = (CheckBox) findViewById(R.id.check1);
        check2 = (CheckBox) findViewById(R.id.check2);
        check3 = (CheckBox) findViewById(R.id.check3);
        check4 = (CheckBox) findViewById(R.id.check4);
        editUnit = (AutoCompleteTextView) findViewById(R.id.editUnit);
        pbSearch = (ProgressBar) findViewById(R.id.pbSearch);
        backKepegawaian = (ImageButton) findViewById(R.id.backKepegawaian);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listPegawai = new ArrayList<>();
        listUnit = new ArrayList<>();

        getAllSkpd();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1.isChecked()){
                    check2.setChecked(false);
                    check3.setChecked(false);
                    check4.setChecked(false);
                } else if (check2.isChecked()){
                    check1.setChecked(false);
                    check3.setChecked(false);
                    check4.setChecked(false);
                } else if (check3.isChecked()){
                    check1.setChecked(false);
                    check2.setChecked(false);
                    check4.setChecked(false);
                } else if (check4.isChecked()){
                    check1.setChecked(false);
                    check2.setChecked(false);
                    check3.setChecked(false);
                }

                loadRecyclerView();
            }
        });

        backKepegawaian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchPegawai.this, KepegawaianActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void loadRecyclerView() {
        if (check1.isChecked()){
            golongan = "i/";
        } else if (check2.isChecked()){
            golongan = "ii/";
        } else if (check3.isChecked()){
            golongan = "iii/";
        } else if (check4.isChecked()){
            golongan = "iv/";
        } else {
            golongan = "";
        }

        nama = editNama.getText().toString().trim();
        skpd = editUnit.getText().toString().trim();
        nip = editNIP.getText().toString().trim();

        if (TextUtils.isEmpty(nama) && TextUtils.isEmpty(skpd) && TextUtils.isEmpty(nip)){
            Toast.makeText(this, "Isi salah satu form!", Toast.LENGTH_SHORT).show();
            editNama.requestFocus();
            return;
        }

        pbSearch.setVisibility(View.VISIBLE);
        String url = AppConfig.URL_SEARCH_PEGAWAI_POST;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            listPegawai.clear();

                            JSONObject obj = new JSONObject(response);
                            JSONArray ar = obj.getJSONArray("pegawai");

                            //traversing through all the object
                            for (int i = 0; i < ar.length(); i++) {

                                //getting product object from json array
                                JSONObject object = ar.getJSONObject(i);

                                //adding the product to product list
                                listPegawai.add(new Pegawai(
                                        object.getString("NIP"),
                                        object.getString("NAMA"),
                                        object.getString("JENIS_KELAMIN"),
                                        object.getString("KEDUDUKAN"),
                                        object.getString("STATUS_PEGAWAI"),
                                        object.getString("GOLONGAN"),
                                        object.getString("PANGKAT"),
                                        object.getString("UNIT_KERJA"),
                                        object.getInt("USIA")
                                ));
                            }
                            //creating adapter object and setting it to recyclerview
                            PegawaiAdapter adapter = new PegawaiAdapter(SearchPegawai.this, listPegawai);
                            recyclerView.setAdapter(adapter);
                            pbSearch.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchPegawai.this, "Data pegawai tidak ditemukan", Toast.LENGTH_SHORT).show();
                        pbSearch.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nama", nama);
                params.put("golongan", golongan);
                params.put("skpd", skpd);
                params.put("nip", nip);
                return params;
            }
        };
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void getAllSkpd(){
        String url = AppConfig.URL_ALL_SKPD;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("tes");
                        try {
                            System.out.println("Test");
                            JSONObject obj = new JSONObject(response);
                            JSONArray ar = obj.getJSONArray("pegawai");

//                            List<String> responseList = new ArrayList<>();
                            ArrayList<String> responseList = new ArrayList<>();

                            for (int i = 0; i < ar.length(); i++) {

                                final JSONObject object = ar.getJSONObject(i);
//                                listUnit.add(new Pegawai("UNIT_KERJA"));
//                                responseList.add(listUnit.get(i).getUnit_kerja());

                                responseList.add(object.getString("UNIT_KERJA"));
//                                System.out.println(i);
                            }
                            ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.layout_spinner, R.id.textView3, responseList);
                            editUnit.setAdapter(unitAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("Testereasd");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchPegawai.this, "Error", Toast.LENGTH_SHORT).show();
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

    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(SearchPegawai.this, KepegawaianActivity.class);
        startActivity(i);
        finish();
    }
}
