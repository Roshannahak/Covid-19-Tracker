package com.news.handson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.news.handson.adepter.adepterview;
import com.news.handson.model.CovidData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String url = "https://api.rootnet.in/covid19-in/stats/latest";
    RecyclerView recyclerView;
    List<CovidData> covidDataList;
    CovidData covidData;
    TextView totalcase, totalrecover, totaldeath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        covidDataList = new ArrayList<>();

        totalcase = findViewById(R.id.textTotalConfirmedcase);
        totalrecover = findViewById(R.id.textTotalRecoveredcase);
        totaldeath = findViewById(R.id.textTotalDeathcase);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseObject = new JSONObject(response);
                    Log.d("first response: ", String.valueOf(responseObject));

                    JSONObject object = responseObject.getJSONObject("data");
                    Log.d("second response: ", String.valueOf(object));

                    JSONObject summary = object.getJSONObject("summary");
                    getTotalCaseInIndia(summary);

                    JSONArray array = object.getJSONArray("regional");
                    Log.d("array: ", String.valueOf(array));

                    for (int i = 0; i < array.length(); i++){
                        JSONObject jsonObject = array.getJSONObject(i);

                        String state = jsonObject.getString("loc");
                        String CasesIndian = jsonObject.getString("confirmedCasesIndian");
                        String CasesForeign = jsonObject.getString("confirmedCasesForeign");
                        String recover = jsonObject.getString("discharged");
                        String deaths = jsonObject.getString("deaths");
                        String Confirmed = jsonObject.getString("totalConfirmed");

                        covidData = new CovidData(CasesIndian, CasesForeign, recover, deaths, state, Confirmed);
                        covidDataList.add(covidData);
                    }
                    adepterview adepterview = new adepterview(getApplicationContext(), covidDataList);
                    recyclerView.setAdapter(adepterview);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"error: "+e, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "something error: "+error, Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

    private void getTotalCaseInIndia(JSONObject summary) throws JSONException {
        String totaldata = summary.getString("total");
        String recoverdata = summary.getString("discharged");
        String deathdata = summary.getString("deaths");

        totalcase.setText(totaldata);
        totalrecover.setText(recoverdata);
        totaldeath.setText(deathdata);
    }
}