package com.suparat.apisit.sccexecutivesummary.model;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import com.suparat.apisit.sccexecutivesummary.R;
import com.suparat.apisit.sccexecutivesummary.fm_rpt_profit_from_sales;




/**
 * Created by Apisit on 28/08/2560.
 */

public class SP_WEB_RP_SUMPROFIT_ALL {
    String XTYPE;
    Float SUM_COST_ALL;
    Float SUM_SALE_BEFORE_VAT;
    Float SUM_PROFIT_AMOUNT;

    String fDataOutput;


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













}
