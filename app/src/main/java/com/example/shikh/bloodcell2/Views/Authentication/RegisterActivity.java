package com.example.shikh.bloodcell2.Views.Authentication;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shikher on 4/7/17.
 */

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.register_calenderView)
    TextView dob;
    @BindView(R.id.register_full_name)
    EditText full_name;
    @BindView(R.id.register_city_spinner)
    Spinner city;
    @BindView(R.id.register_spinner_bloodgroup)
    Spinner bloodgroup;
    @BindView(R.id.register_email)
    EditText email;
    @BindView(R.id.editText_mobile_register)
    EditText mobile;
    @BindView(R.id.button_register)
    Button submit;
    @BindView(R.id.register_password)
    EditText password;
    @BindView(R.id.register_radioGroup)
    RadioGroup radioGroup;
    private int mYear, mMonth, mDay;
    private int mYear2, mMonth2, mDay2;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
        progressDialog = new ProgressDialog(this);
        ButterKnife.bind(this);
        ArrayAdapter<String> adapter1= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Blood_Group));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodgroup.setAdapter(adapter1);
        ArrayAdapter<String> adapter2= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Cities));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(adapter2);
    }
    @OnClick(R.id.button_register)
    public void onRegisterSubmit(View v) {
        String g;
        int selectedRadioButtonID = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonID != -1) {

            RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
            String selectedRadioButtonText = selectedRadioButton.getText().toString();

            g=selectedRadioButtonText;
        }
        else{
            g="";
        }

        mYear2=mYear2-1900;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String GENDER=g;
        final String DOB = sdf.format(new Date(mYear2, mMonth2, mDay2));
        final String FULL_NAME=full_name.getText().toString().trim();
        final String CITY=city.getSelectedItem().toString();
        final String BLOODGROUP=bloodgroup.getSelectedItem().toString();
        final String EMAIL=email.getText().toString().trim();
        final String MOBILE=mobile.getText().toString().trim();
        final String PASSWORD=password.getText().toString();
        if(PASSWORD.matches("")||DOB.matches("")||FULL_NAME.matches("")||CITY.matches("")||BLOODGROUP.matches("")||EMAIL.matches("")||MOBILE.matches(""))
            Toast.makeText(this, "All Fields are not filled.", Toast.LENGTH_LONG).show();
        else {
            progressDialog.setMessage("Registering user...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if(!jsonObject.getBoolean("error"))
                                {
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("gender", GENDER);
                    params.put("dob", DOB);
                    params.put("name", FULL_NAME);
                    params.put("city", CITY);
                    params.put("bloodgroup", BLOODGROUP);
                    params.put("email", EMAIL);
                    params.put("mobile", MOBILE);
                    params.put("password", PASSWORD);
                    return params;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


        }

    }
    @OnClick(R.id.register_calenderView)
    public void calendar_dialog()
    {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        mYear2=year;
                        mDay2=dayOfMonth;
                        mMonth2=monthOfYear;
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}
