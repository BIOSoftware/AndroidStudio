package com.suparat.apisit.sccexecutivesummary;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

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


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;






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

    private int mYear = 2013;
    private int mMonth = 5;
    private int mDay = 30;


    public fm_rpt_profit_from_sales() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_fm_rpt_profit_from_sales, container, false);


        // Set BarChart

        BarChart barChart = (BarChart) v.findViewById(R.id.chart_rpt_profit_from_sales);
        //BarChart barChart = (BarChart)container.findViewById(R.id.chart_rpt_profit_from_sales);



        final ArrayList<SP_WEB_RP_SUMPROFIT_ALL> testDatas = SP_WEB_RP_SUMPROFIT_ALL.getSP_WEB_RP_SUMPROFIT_ALL();

        final ArrayList<BarEntry> barEntryCost = new ArrayList<>();
        final ArrayList<BarEntry> barEntrySale = new ArrayList<>();
        final ArrayList<BarEntry> barEntryProfit = new ArrayList<>();
        int index = 0;
        for (SP_WEB_RP_SUMPROFIT_ALL testData : testDatas) {
            String fXTYPE = testData.getXTYPE();
            float fSUM_COST_ALL = testData.getSUM_COST_ALL();
            float fSUM_PROFIT_AMOUNT = testData.getSUM_PROFIT_AMOUNT();
            float fSUM_SALE_BEFORE_VAT = testData.getSUM_SALE_BEFORE_VAT();

            barEntryCost.add(new BarEntry(index,fSUM_COST_ALL));
            barEntrySale.add(new BarEntry(index,fSUM_PROFIT_AMOUNT));
            barEntryProfit.add(new BarEntry(index,fSUM_SALE_BEFORE_VAT));


            index++;
        }


        BarDataSet datasetCost = new BarDataSet(barEntryCost, "ต้นทุน");
        BarDataSet datasetSale = new BarDataSet(barEntrySale, "ยอดขาย");
        BarDataSet datasetProfit = new BarDataSet(barEntryProfit, "กำไร");

        datasetCost.setValueTextSize(8);
        datasetSale.setValueTextSize(8);
        datasetProfit.setValueTextSize(8);

        List<Integer> fColorList = new ArrayList<Integer>();



        for (int i=0 ;i< testDatas.size();i++){
            Random rnd = new Random();

            int fColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            fColorList.add(fColor);
        }

        int fColorSet = getResources().getColor(R.color.colorChart01);
        datasetCost.setColors(fColorSet);
        fColorSet = getResources().getColor(R.color.colorChart02);
        datasetSale.setColors(fColorSet);
        fColorSet = getResources().getColor(R.color.colorChart03);
        datasetProfit.setColors(fColorSet);


        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();


        dataSets.add(datasetCost);
        dataSets.add(datasetSale);
        dataSets.add(datasetProfit);

        BarData data = new BarData(datasetCost,datasetSale,datasetProfit);



        barChart.setData(data);


        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setLabelRotationAngle(80);

        final XAxis xAxis = barChart.getXAxis();
        xAxis.setTextSize(8);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawLabels(true);



        xAxis.setValueFormatter(new IAxisValueFormatter() {
                                    @Override
                                    public String getFormattedValue(float v, AxisBase axisBase) {
                                        if (v < 0 || v >= testDatas.size()) {
                                            return "";
                                        }
                                        if (testDatas.get((int) v).getXTYPE() == "1"){
                                            return "ขายรถ";
                                        }else
                                        if (testDatas.get((int) v).getXTYPE() == "2"){
                                            return "ขายอะไหล่";
                                        }else
                                        if (testDatas.get((int) v).getXTYPE() == "3"){
                                            return "บริการ";
                                        } else
                                            return "";

                                    }
                                    public int getDecimalDigits() { return 1; }
                                });



        YAxis RightAxis = barChart.getAxisRight();
        RightAxis.setEnabled(false);


        barChart.animateY(3000);



        float groupSpace = 0.04f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.46f; // x2 dataset



        barChart.getBarData().setBarWidth(barWidth);
        barChart.groupBars(0, groupSpace, barSpace);


        etxt_Month = (EditText)v.findViewById(R.id.etxt_Month);
        etxt_Month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        return v;
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
