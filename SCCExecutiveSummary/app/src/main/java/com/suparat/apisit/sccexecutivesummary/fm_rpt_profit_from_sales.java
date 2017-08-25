package com.suparat.apisit.sccexecutivesummary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.suparat.apisit.sccexecutivesummary.model.TestData;


import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class fm_rpt_profit_from_sales extends Fragment {


    public fm_rpt_profit_from_sales() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_fm_rpt_profit_from_sales, container, false);
        BarChart barChart = (BarChart) v.findViewById(R.id.chart_rpt_profit_from_sales);



        //BarChart barChart = (BarChart)container.findViewById(R.id.chart_rpt_profit_from_sales);
        final ArrayList<TestData> testDatas = TestData.getDataTest(10);

        final ArrayList<BarEntry> barEntry = new ArrayList<>();
        int index = 0;
        for (TestData testData : testDatas) {
            float fScore = testData.getScore();
            String fDesc = testData.getName();

            barEntry.add(new BarEntry(index,fScore));

            index++;
        }


        BarDataSet dataset = new BarDataSet(barEntry, "#");
        dataset.setValueTextSize(8);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); // set the color



        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(dataset);
        BarData data = new BarData(dataSets);

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
                                        return testDatas.get((int) v).getName();
                                    }
                                    public int getDecimalDigits() { return 1; }
                                });



        YAxis RightAxis = barChart.getAxisRight();
        RightAxis.setEnabled(false);


        barChart.animateY(3000);





        //return inflater.inflate(R.layout.fragment_fm_rpt_profit_from_sales, container, false);
        return v;
    }


}
