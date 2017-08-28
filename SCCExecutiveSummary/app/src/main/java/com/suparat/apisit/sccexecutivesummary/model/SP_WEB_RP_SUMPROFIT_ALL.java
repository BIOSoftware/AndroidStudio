package com.suparat.apisit.sccexecutivesummary.model;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

        ArrayList<HashMap<String, String>> fDataString = new ArrayList<HashMap<String, String>>();
        ArrayList<SP_WEB_RP_SUMPROFIT_ALL> fData = new ArrayList<SP_WEB_RP_SUMPROFIT_ALL>();


        try {
            fData = new ArrayList<>();
                /* Set to Http post*/
                /* End set Value*/

            URL url = new URL("http://www2.suparat.com/API/rp_.php?start_date=01/01/2016&end_date=12/31/2017");

            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();


            InputStream inputStream = conn.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            inputStream.close();
            js_result = sb.toString();

        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
            return fData;
        }

        try {
            final JSONArray jArray = new JSONArray(js_result);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jo = jArray.getJSONObject(i);
                HashMap<String, String> rpData = new HashMap<String, String>();
                rpData.put("XTYPE", jo.get("XTYPE").toString());
                rpData.put("SUM_COST_ALL", jo.get("SUM_COST_ALL").toString());
                rpData.put("SUM_SALE_BEFORE_VAT", jo.get("SUM_SALE_BEFORE_VAT").toString());
                rpData.put("SUM_PROFIT_AMOUNT", jo.get("SUM_PROFIT_AMOUNT").toString());
                fDataString.add(rpData);



            }

            for (int iCount = 0;iCount < fDataString.size();iCount++){


                SP_WEB_RP_SUMPROFIT_ALL aData = null;
                aData.setXTYPE(fDataString.get(iCount).get("XTYPE"));
                aData.setSUM_COST_ALL(Float.parseFloat(fDataString.get(iCount).get("SUM_COST_ALL")));
                aData.setSUM_SALE_BEFORE_VAT(Float.parseFloat(fDataString.get(iCount).get("SUM_SALE_BEFORE_VAT")));
                aData.setSUM_PROFIT_AMOUNT(Float.parseFloat(fDataString.get(iCount).get("SUM_PROFIT_AMOUNT")));

                fData.add(aData);
            }


        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
            return fData;
        }


        return fData;
    }






}
