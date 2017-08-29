package com.suparat.apisit.sccexecutivesummary;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;


import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.suparat.apisit.sccexecutivesummary.model.SP_WEB_RP_SUMPROFIT_ALL;
import com.suparat.apisit.sccexecutivesummary.model.TestData;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class fm_rpt_profit_from_sales extends Fragment  {

    private static EditText etxt_Month;
    private EditText etxt_Year;

    private DatePickerDialog dialogMonth;
    private DatePickerDialog dialogYear;
    private SimpleDateFormat dateFormatter;

    private View v;


    private ArrayList<SP_WEB_RP_SUMPROFIT_ALL> testDatas;
    private BarChart barChart;
    static final int DATE_DIALOG_ID = 1;
    private int mYear;
    private int mMonth;
    private int mDay;
    private Date mStartDate;
    private Date mEndDate;



    public fm_rpt_profit_from_sales() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_fm_rpt_profit_from_sales, container, false);

        mStartDate = new Date();
        mEndDate = new Date();

        Date tmpStartDate = Calendar.getInstance().getTime();

        Calendar cal = Calendar.getInstance();
        cal.setTime(tmpStartDate);

        cal.get(Calendar.DAY_OF_MONTH);

        cal.get(Calendar.DAY_OF_YEAR);

        mStartDate.setDate(1);
        mStartDate.setMonth(cal.get(Calendar.MONTH) );
        mStartDate.setYear(cal.get(Calendar.YEAR) - 1900);


        mEndDate.setDate(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        mEndDate.setMonth(cal.get(Calendar.MONTH) );
        mEndDate.setYear(cal.get(Calendar.YEAR) - 1900);






        SetChart(mStartDate,mEndDate);




        etxt_Month = (EditText)v.findViewById(R.id.etxt_Month);
        etxt_Year = (EditText)v.findViewById(R.id.etxt_Year);

        etxt_Month.setEnabled(false);
        etxt_Year.setEnabled(false);

        etxt_Month.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
        etxt_Year.setText(Integer.toString(cal.get(Calendar.YEAR) + 543) );


        Button btnSelectMonth = (Button)v.findViewById(R.id.btnSelectMonth);

        btnSelectMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final MonthYearPicker myp = new MonthYearPicker(getActivity());;

                myp.build(new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialogInterface, int i) {

                                  int selectMonth = myp.getSelectedMonth();
                                  String selectMonthName = myp.getSelectedMonthName();
                                  int selectedYear = myp.getSelectedYear();

                                  etxt_Month.setText( selectMonthName);
                                  etxt_Year.setText( Integer.toString(selectedYear));


                                  Date fStartDate = new Date();
                                  Date fEndDate = new Date();

                                  fStartDate.setDate(1);
                                  fStartDate.setMonth(selectMonth );
                                  fStartDate.setYear(selectedYear - 543 - 1900);


                                  Calendar cal = Calendar.getInstance();
                                  cal.setTime(fStartDate);

                                  fEndDate.setDate(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                                  fEndDate.setMonth(selectMonth);
                                  fEndDate.setYear(selectedYear - 543 - 1900);



                                  SetChart(fStartDate,fEndDate);





                              }
                          },null);


                myp.show();
            }


        });

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);

        return v;
    }

    private void SetChart(Date aStartDate,Date aEndDate) {




        String fStartDate = "07/01/2017";
        String fEndDate = "07/30/2017";

        DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = df.parse(fStartDate);
            endDate = df.parse(fEndDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        testDatas = GetDataJSON(aStartDate,aEndDate);


        // Set BarChart
        barChart = (BarChart) v.findViewById(R.id.chart_rpt_profit_from_sales);
        //BarChart barChart = (BarChart)container.findViewById(R.id.chart_rpt_profit_from_sales);

        final ArrayList<BarEntry> barEntryCost = new ArrayList<>();
        final ArrayList<BarEntry> barEntrySale = new ArrayList<>();
        final ArrayList<BarEntry> barEntryProfit = new ArrayList<>();
        float index = 0.5f;
        for (SP_WEB_RP_SUMPROFIT_ALL testData : testDatas) {
            String fXTYPE = testData.getXTYPE();
            float fSUM_COST_ALL = testData.getSUM_COST_ALL();
            float fSUM_PROFIT_AMOUNT = testData.getSUM_PROFIT_AMOUNT();
            float fSUM_SALE_BEFORE_VAT = testData.getSUM_SALE_BEFORE_VAT();

            barEntryCost.add(new BarEntry(index,fSUM_COST_ALL));
            barEntrySale.add(new BarEntry(index,fSUM_SALE_BEFORE_VAT));
            barEntryProfit.add(new BarEntry(index,fSUM_PROFIT_AMOUNT));


            index++;
        }


        BarDataSet datasetSale = new BarDataSet(barEntrySale, "ยอดขาย");
        BarDataSet datasetCost = new BarDataSet(barEntryCost, "ต้นทุน");
        BarDataSet datasetProfit = new BarDataSet(barEntryProfit, "กำไร");

        datasetCost.setValueTextSize(8);
        datasetSale.setValueTextSize(8);
        datasetProfit.setValueTextSize(8);



        int fColorSet = getResources().getColor(R.color.colorChart01);
        datasetCost.setColors(fColorSet);
        fColorSet = getResources().getColor(R.color.colorChart02);
        datasetSale.setColors(fColorSet);
        fColorSet = getResources().getColor(R.color.colorChart03);
        datasetProfit.setColors(fColorSet);


        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();


        dataSets.add(datasetSale);
        dataSets.add(datasetCost);
        dataSets.add(datasetProfit);

        BarData data = new BarData(datasetSale,datasetCost,datasetProfit);



        barChart.setData(data);


        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setLabelRotationAngle(0);

        final XAxis xAxis = barChart.getXAxis();
        xAxis.setTextSize(8);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawLabels(true);



        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float vData, AxisBase axisBase) {

                if (vData < 0 || vData >= testDatas.size()) {
                    return "";
                }else {
                    String fXTYPE = testDatas.get((int) vData).getXTYPE();
                    if (vData == 0 || vData == 1 || vData == 2){
                        if (fXTYPE.equals("1")) {
                            return "ขายรถ";
                        } else if (fXTYPE.equals("2")) {
                            return "ขายอะไหล่";
                        } else if (fXTYPE.equals("3")) {
                            return "บริการ";
                        } else
                            return "";
                    } else return "";

                }
            }
            public int getDecimalDigits() { return 1; }
        });



        YAxis RightAxis = barChart.getAxisRight();
        RightAxis.setEnabled(false);


        barChart.animateY(3000);



        float groupSpace = 0.14f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.46f; // x2 dataset
        barWidth = 0.26f; // x2 dataset



        barChart.getBarData().setBarWidth(barWidth);
        barChart.groupBars(0, groupSpace, barSpace);

    }

    private ArrayList<SP_WEB_RP_SUMPROFIT_ALL> GetDataJSON(Date aStartDate,Date aEndDate) {
        ArrayList<SP_WEB_RP_SUMPROFIT_ALL> listItems = new ArrayList<SP_WEB_RP_SUMPROFIT_ALL>();

        HttpURLConnection conn;


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String fReportStartDate = df.format(aStartDate);
        String fReportEndDate = df.format(aEndDate);


        try{
            URL url = new URL("http://www2.suparat.com/API/rp_.php?start_date="+fReportStartDate+"&end_date="+fReportEndDate+"");

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            int response_code = conn.getResponseCode();
            if (response_code == HttpURLConnection.HTTP_OK) {

                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();

                String line;

                while ((line = r.readLine()) != null) {
                    JSONArray ja = new JSONArray(line);
                    //System.out.println(line);
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = (JSONObject) ja.get(i);

                        SP_WEB_RP_SUMPROFIT_ALL Items = new SP_WEB_RP_SUMPROFIT_ALL();
                        Items.setXTYPE(jo.getString("XTYPE"));
                        Items.setSUM_COST_ALL(Float.parseFloat(jo.getString("SUM_COST_ALL")));
                        Items.setSUM_PROFIT_AMOUNT(Float.parseFloat(jo.getString("SUM_PROFIT_AMOUNT")));
                        Items.setSUM_SALE_BEFORE_VAT(Float.parseFloat(jo.getString("SUM_SALE_BEFORE_VAT")));

                        listItems.add(Items);
                    }
                }


            }




        }catch (Exception e){
            Log.e("XXXxXXX",e.toString());
        }

        return listItems;

    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            return  dialog;
        }

        public  void onDateSet(DatePicker view, int year, int month, int day) {

        }




    }





}
