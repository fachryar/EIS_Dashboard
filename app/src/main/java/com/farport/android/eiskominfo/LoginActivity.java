package com.farport.android.eiskominfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText valEmail, valPassword;
    ProgressBar pbLogin;

    public String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        valEmail = (EditText) findViewById(R.id.valEmail);
        valPassword = (EditText) findViewById(R.id.valPassword);
        pbLogin = (ProgressBar) findViewById(R.id.pbLogin);

        User user = SharedPrefManager.getInstance(this).getUser();
        state = String.valueOf(user.getState());

        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }

    private void userLogin() {
        //first getting the values
        final String email = valEmail.getText().toString().trim();
        final String password = valPassword.getText().toString().trim();

        //validating inputs
        if (TextUtils.isEmpty(email)){
            valEmail.setError("Email harus diisi!");
            valEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            valPassword.setError("Password harus diisi!");
            valPassword.requestFocus();
            return;
        }

        pbLogin.setVisibility(View.VISIBLE);
        String url = AppConfig.URL_LOGIN;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (obj.getBoolean("status")) {
                                //getting the user from the response
                                JSONArray ar = obj.getJSONArray("user");
                                JSONObject userJson = ar.getJSONObject(0);

                                //creating a new user object
                                User user = new User(
                                        userJson.getString("USERNAME"),
                                        userJson.getString("NAMA_USER"),
                                        userJson.getString("JABATAN"),
                                        userJson.getString("SKPD"),
                                        userJson.getString("NO_HP"),
                                        userJson.getInt("STATE")
                                );

                                if (user.getState() == 0){
                                    Toast.makeText(LoginActivity.this, "Akun ada belum diverifikasi", Toast.LENGTH_SHORT).show();
                                }else{
                                    //storing the user in shared preferences
                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                    pbLogin.setVisibility(View.GONE);

                                    //starting the profile activity
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }

                            } else {
                                Toast.makeText(LoginActivity.this, "Login gagal, silahkan cek kembali email dan password Anda", Toast.LENGTH_SHORT).show();
                                pbLogin.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Login gagal, silahkan cek kembali email dan password Anda", Toast.LENGTH_SHORT).show();
                            pbLogin.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Login gagal, silahkan cek kembali email dan password Anda", Toast.LENGTH_SHORT).show();
                        pbLogin.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", email);
                params.put("password", password);
                return params;
            }
        };
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
