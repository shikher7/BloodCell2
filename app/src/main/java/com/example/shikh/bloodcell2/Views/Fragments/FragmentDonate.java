package com.example.shikh.bloodcell2.Views.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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


public class FragmentDonate extends Fragment
{
    private int mYear, mMonth, mDay;
    private int mYear2, mMonth2, mDay2;
    @BindView(R.id.spinner_blood_bank)
    Spinner bloodbank;
        @BindView(R.id.spinner_city)
        Spinner city;
        @BindView(R.id.spinner_time_slot)
        Spinner timeslot;
        @BindView(R.id.calendar_donate)
        TextView date;
        @BindView(R.id.button_donate)
        Button submit;
        private ProgressDialog progressDialog;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_donate, container, false);
            ButterKnife.bind(this, rootView);

            String Blood_Banks[]={"VIT BloodBank","Katpadi BloodBank","CMC BloodBank"};

            ArrayAdapter<String> adapter1= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,Blood_Banks);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            bloodbank.setAdapter(adapter1);
            ArrayAdapter<String> adapter2= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Cities));
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            city.setAdapter(adapter2);
            ArrayAdapter<String> adapter3= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Time_slot));
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            timeslot.setAdapter(adapter3);

            return rootView;
    }
    @OnClick(R.id.button_donate)
    public void onDonateSubmit(View v) {
        final String CITY = city.getSelectedItem().toString();
        final String BLOODBANK = bloodbank.getSelectedItem().toString();
        final String TIMESLOT = timeslot.getSelectedItem().toString();
        mYear2=mYear2-1900;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date(mYear2, mMonth2, mDay2));
        progressDialog = new ProgressDialog(getContext());
        Calendar c = Calendar.getInstance();
        final String DATE1 = sdf.format(c.getTime());

        if(date.compareTo(DATE1)>=0) {
            if (CITY.matches("") || BLOODBANK.matches("") ||TIMESLOT.matches("") || DATE1.matches(""))
                Toast.makeText(getActivity(), "All Fields are not filled.", Toast.LENGTH_LONG).show();
            else {
                progressDialog.setMessage("Sending Donation Request...");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        Constants.URL_DONATE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(!jsonObject.getBoolean("error"))
                                    {
                                        Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(getContext(), MainActivity.class);
                                        getActivity().startActivity(i);
                                        ((Activity)getContext()).finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("city", CITY);
                        params.put("bloodbank", BLOODBANK);
                        params.put("timeslot", TIMESLOT);
                        params.put("date", DATE1);
                        params.put("user_id",SharedPrefManager.getInstance(getActivity()).getUserID());
                        return params;
                    }
                };
                RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);


            }

        }
        else {
            Toast.makeText(getActivity(),"Selected Date is incorrect !!",Toast.LENGTH_LONG).show();
        }

}
@OnClick(R.id.calendar_donate)
public void calendar_dialog()
{
    final Calendar c = Calendar.getInstance();
    mYear = c.get(Calendar.YEAR);
    mMonth = c.get(Calendar.MONTH);
    mDay = c.get(Calendar.DAY_OF_MONTH);
    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
            new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {

                    date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                mYear2=year;
                    mDay2=dayOfMonth;
                    mMonth2=monthOfYear;
                }
            }, mYear, mMonth, mDay);
    datePickerDialog.show();
}


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        android.support.v7.app.ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Donate Blood");
    }

}

