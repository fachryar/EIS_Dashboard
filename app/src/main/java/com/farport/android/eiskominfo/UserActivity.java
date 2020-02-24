package com.farport.android.eiskominfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    TextView auNama, auEmail, auJabatanSKPD, auNoHP;
    String username;
    CardView cardTitleUser;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<User> listUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        User user = SharedPrefManager.getInstance(this).getUser();
        username = user.getUsername();

        auNama = (TextView) findViewById(R.id.auNama);
        auEmail = (TextView) findViewById(R.id.auEmail);
        auJabatanSKPD = (TextView) findViewById(R.id.auJabatanSKPD);
        auNoHP = (TextView) findViewById(R.id.auNoHP);
        cardTitleUser = (CardView) findViewById(R.id.cardTitleUser);

        auNama.setText(user.getNama_user());
        auEmail.setText(user.getUsername());
        auNoHP.setText(user.getNo_hp());
        auJabatanSKPD.setText(user.getJabatan()+ " " + user.getSkpd());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listUser = new ArrayList<>();

        loadRecyclerView();

        cardTitleUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    // method untuk menampilkan list pengguna dengan GET API
    private void loadRecyclerView() {
        final String url = AppConfig.URL_GET_ALL_USER + "username=" + username;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(url);
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray ar = obj.getJSONArray("user");

                            for (int i = 0; i < ar.length(); i++) {

                                JSONObject object = ar.getJSONObject(i);

                                listUser.add(new User(
                                        object.getString("USERNAME"),
                                        object.getString("NAMA_USER"),
                                        object.getString("JABATAN"),
                                        object.getString("SKPD"),
                                        object.getString("NO_HP"),
                                        object.getInt("STATE")
                                ));
                            }
                            //creating adapter object and setting it to recyclerview
                            UserAdapter adapter = new UserAdapter(UserActivity.this, listUser);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
        };
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(UserActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
