package com.suparat.apisit.sccexecutivesummary;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.suparat.apisit.sccexecutivesummary.adapter.adapter_rpt_profit_from_sale;
import com.suparat.apisit.sccexecutivesummary.model.SP_WEB_RP_SUMPROFIT_ALL;



import java.io.BufferedInputStream;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class fm_rpt_profit_from_sales extends Fragment  {

    private static EditText etxt_Month;
    private EditText etxt_Year;
    private RadioGroup rg_type_where_select;
    private TextView txtAmountSum;


    private View v;


    private ArrayList<SP_WEB_RP_SUMPROFIT_ALL> testDatas;
    private BarChart barChart;
    private Date mStartDate;
    private Date mEndDate;
    private int mYear;
    private int mMonth;

    private ListView mListView;
    private adapter_rpt_profit_from_sale mAdapter;



    public fm_rpt_profit_from_sales() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_fm_rpt_profit_from_sales, container, false);
        Calendar cal = Calendar.getInstance();

        etxt_Month = (EditText)v.findViewById(R.id.etxt_Month);
        etxt_Year = (EditText)v.findViewById(R.id.etxt_Year);
        rg_type_where_select = (RadioGroup)v.findViewById(R.id.rg_type_where_select);
        txtAmountSum = (TextView)v.findViewById(R.id.txtAmountSum);


        mStartDate = new Date();
        mEndDate = new Date();

        Date tmpStartDate = Calendar.getInstance().getTime();


        cal.setTime(tmpStartDate);
        cal.get(Calendar.DAY_OF_MONTH);
        cal.get(Calendar.DAY_OF_YEAR);

        mStartDate.setDate(1);
        mStartDate.setMonth(cal.get(Calendar.MONTH) );
        mStartDate.setYear(cal.get(Calendar.YEAR) - 1900);


        mEndDate.setDate(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        mEndDate.setMonth(cal.get(Calendar.MONTH) );
        mEndDate.setYear(cal.get(Calendar.YEAR) - 1900);


        SetDataSelected(rg_type_where_select.getCheckedRadioButtonId() );

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

                                  mStartDate = fStartDate;
                                  mEndDate = fEndDate;


                                  SetDataSelected(rg_type_where_select.getCheckedRadioButtonId() );

                                  //SetChart(mStartDate,mEndDate);





                              }
                          },null);


                myp.show();
            }


        });

        rg_type_where_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                SetDataSelected(i);
            }
        });



        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);

        return v;
    }
    private void SetDataSelected(int chkRadioSelected){
        if (chkRadioSelected == R.id.rp_profit_from_sale_Check_as_Week){

            Date fTempDate = new Date();
            Date fStartDate = new Date();
            Date fEndDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(fTempDate);

            int iDay = cal.get(Calendar.DAY_OF_WEEK) - 1;



            switch (iDay){
                case 1 : fStartDate = fTempDate;
                    cal.add( Calendar.DATE, 6 );
                    fEndDate = cal.getTime();
                    break;
                case 2 : cal.add(Calendar.DATE, -1);
                    fStartDate = cal.getTime();
                    cal.add( Calendar.DATE, 6 );
                    fEndDate = cal.getTime();
                    break;
                case 3 : cal.add(Calendar.DATE, -2);
                    fStartDate = cal.getTime();
                    cal.add( Calendar.DATE, 6 );
                    fEndDate = cal.getTime();
                    break;
                case 4 : cal.add(Calendar.DATE, -3);
                    fStartDate = cal.getTime();
                    cal.add( Calendar.DATE, 6 );
                    fEndDate = cal.getTime();
                    break;
                case 5 : cal.add(Calendar.DATE, -4);
                    fStartDate = cal.getTime();
                    cal.add( Calendar.DATE, 6 );
                    fEndDate = cal.getTime();
                    break;
                case 6 : cal.add(Calendar.DATE, -5);
                    fStartDate = cal.getTime();
                    cal.add( Calendar.DATE, 6 );
                    fEndDate = cal.getTime();
                    break;
                case 7 : cal.add(Calendar.DATE, -6);
                    fStartDate = cal.getTime();
                    cal.add( Calendar.DATE, 6 );
                    fEndDate = cal.getTime();
                    break;
            }

            SetChart(fStartDate,fEndDate);

        }else if (chkRadioSelected == R.id.rp_profit_from_sale_Check_as_Month){

            SetChart(mStartDate,mEndDate);

        }else if (chkRadioSelected == R.id.rp_profit_from_sale_Check_as_Year){

            Date fTempDate = new Date();
            fTempDate = mStartDate;
            Date fStartDate = new Date();
            Date fEndDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(fTempDate);

            fStartDate.setDate(1);
            fStartDate.setMonth(0);
            fStartDate.setYear(cal.get(Calendar.YEAR) - 1900);

            fEndDate.setDate(31);
            fEndDate.setMonth(11);
            fEndDate.setYear(cal.get(Calendar.YEAR) - 1900);

            SetChart(fStartDate,fEndDate);
        }
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
        float sumAmountProfit = 0;

        for (SP_WEB_RP_SUMPROFIT_ALL testData : testDatas) {
            String fXTYPE = testData.getXTYPE();
            float fSUM_COST_ALL = testData.getSUM_COST_ALL();
            float fSUM_PROFIT_AMOUNT = testData.getSUM_PROFIT_AMOUNT();
            float fSUM_SALE_BEFORE_VAT = testData.getSUM_SALE_BEFORE_VAT();

            barEntryCost.add(new BarEntry(index,fSUM_COST_ALL));
            barEntrySale.add(new BarEntry(index,fSUM_SALE_BEFORE_VAT));
            barEntryProfit.add(new BarEntry(index,fSUM_PROFIT_AMOUNT));

            sumAmountProfit += testData.getSUM_PROFIT_AMOUNT();
            index++;
        }

        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        DecimalFormat formatter = new DecimalFormat("#,##0.00", decimalFormatSymbols);


        txtAmountSum.setText(formatter.format(sumAmountProfit));

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



        mListView = (ListView) v.findViewById(R.id.list_rpt_profit_from_sales);
        mAdapter = new adapter_rpt_profit_from_sale(getActivity(), testDatas);
        mListView.setAdapter(mAdapter);

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
