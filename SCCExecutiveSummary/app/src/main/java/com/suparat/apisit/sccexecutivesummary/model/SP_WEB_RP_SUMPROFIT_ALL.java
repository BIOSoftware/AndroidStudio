package com.suparat.apisit.sccexecutivesummary.model;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;




/**
 * Created by Apisit on 28/08/2560.
 */

public class SP_WEB_RP_SUMPROFIT_ALL {
    String XTYPE;
    Float SUM_COST_ALL;
    Float SUM_SALE_BEFORE_VAT;
    Float SUM_PROFIT_AMOUNT;


    public String getXTYPE() {
        return XTYPE;
    }

    public void setXTYPE(String XTYPE) {
        this.XTYPE = XTYPE;
    }

    public Float getSUM_COST_ALL() {
        return SUM_COST_ALL;
    }

    public void setSUM_COST_ALL(Float SUM_COST_ALL) {
        this.SUM_COST_ALL = SUM_COST_ALL;
    }

    public Float getSUM_SALE_BEFORE_VAT() {
        return SUM_SALE_BEFORE_VAT;
    }

    public void setSUM_SALE_BEFORE_VAT(Float SUM_SALE_BEFORE_VAT) {
        this.SUM_SALE_BEFORE_VAT = SUM_SALE_BEFORE_VAT;
    }

    public Float getSUM_PROFIT_AMOUNT() {
        return SUM_PROFIT_AMOUNT;
    }

    public void setSUM_PROFIT_AMOUNT(Float SUM_PROFIT_AMOUNT) {
        this.SUM_PROFIT_AMOUNT = SUM_PROFIT_AMOUNT;
    }


    @Nullable
    public static  ArrayList<SP_WEB_RP_SUMPROFIT_ALL> getSP_WEB_RP_SUMPROFIT_ALL() {


        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String forecastJsonStr = null;
        String js_result;
        String jsonURI = "http://www2.suparat.com/API/rp_.php?start_date=01/01/2016&end_date=12/31/2017";

        ArrayList<HashMap<String, String>> fDataString = new ArrayList<HashMap<String, String>>();
        ArrayList<SP_WEB_RP_SUMPROFIT_ALL> fData = new ArrayList<SP_WEB_RP_SUMPROFIT_ALL>();


        getJSON(jsonURI);

        return fData;
    }

    private static void getJSON(String url){
        class GetJSON extends AsyncTask<String,Void,String>{

            @Override
            protected String doInBackground(String... strings) {
                String uri = strings[0];
                StringBuilder stringBuilder = null;
                try{
                    URL url_data = new URL(uri);
                    HttpURLConnection connection = (HttpURLConnection)url_data.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null){
                        sb.append(json+"\n");

                    }
                    return sb.toString().trim();


                }catch (Exception e){
                    return null;

                }

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                
            }


        }


        GetJSON dataJSON = new GetJSON();
        dataJSON.execute(url);




    }









}
