package com.farport.android.eiskominfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddCategoryActivity extends AppCompatActivity {
    String a = "ID_KATEGORI";
    String b = "NAMA_KATEGORI";
    String c = "STATUS";

    private RecyclerView recyclerViewMenu;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<Kategori> listKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        recyclerViewMenu = (RecyclerView) findViewById(R.id.recyclerViewMenu);
        recyclerViewMenu.setHasFixedSize(true);
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));
        listKategori = new ArrayList<>();

        loadRecyclerView();
    }

    private void loadRecyclerView() {
        String url = AppConfig.URL_ALL_KATEGORI;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            listKategori.clear();

                            JSONObject obj = new JSONObject(response);
                            JSONArray ar = obj.getJSONArray("kategori");

                            //traversing through all the object
                            for (int i = 0; i < ar.length(); i++) {

                                //getting product object from json array
                                JSONObject object = ar.getJSONObject(i);

                                //adding the product to product list
                                listKategori.add(new Kategori(
                                        object.getInt(a),
                                        object.getInt(c),
                                        object.getString(b)
                                ));
                            }
                            //creating adapter object and setting it to recyclerview
                            KategoriAdapter adapter = new KategoriAdapter(AddCategoryActivity.this, listKategori);
                            recyclerViewMenu.setAdapter(adapter);
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
}
