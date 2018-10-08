package com.example.shikh.bloodcell2.Views.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shikh.bloodcell2.R;
import com.example.shikh.bloodcell2.Utils.Constants;
import com.example.shikh.bloodcell2.Utils.RequestHandler;
import com.example.shikh.bloodcell2.Utils.SharedPrefManager;
import com.example.shikh.bloodcell2.Views.Main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.editText_mobile)
    EditText editText_mobile;
    @BindView(R.id.editText4)
    EditText editText_password;
    public static String result="";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        ButterKnife.bind(this);
    }
    @OnClick(R.id.login_button)
    public void onUserLogin(View v) {
        final String mobile=editText_mobile.getText().toString().trim();
        final String password=editText_password.getText().toString();
        if (mobile.matches(""))
            Toast.makeText(this, "Enter mobile number.", Toast.LENGTH_SHORT).show();
        else {
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    Constants.URL_LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                if(!obj.getBoolean("error")){
                                    SharedPrefManager.getInstance(getApplicationContext())
                                            .userLogin(
                                                    obj.getString("id"),
                                                    obj.getString("username"),
                                                    obj.getString("email"),
                                                    obj.getString("mobile")
                                            );
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(
                                            getApplicationContext(),
                                            obj.getString("message"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();

                            Toast.makeText(
                                    getApplicationContext(),
                                    error.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("mobile", mobile);
                    params.put("password", password);
                    return params;
                }

            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        }
    }
    @OnClick(R.id.register_button)
    public void onUserRegister(View v) {
        Intent i = new Intent(this, RegisterActivity.class);

        this.startActivity(i);

    }


}
