package com.suparat.apisit.sccexecutivesummary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;


import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.suparat.apisit.sccexecutivesummary.model.TestData;


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

        BarChart barChart = (BarChart)container.findViewById(R.id.chart_rpt_profit_from_sales);
        final ArrayList<TestData> testDatas = TestData.getDataTest(30);

        final ArrayList<BarEntry> barEntry = new ArrayList<>();
        int index = 0;
        for (TestData testData : testDatas) {
            float fScore = testData.getScore();
            barEntry.add(new BarEntry(fScore,index ));
            index++;
        }


        BarDataSet dataset = new BarDataSet(barEntry, "#");
        //dataset.setValueTextSize(8);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); // set the color



        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(dataset);
        BarData data = new BarData(dataSets);

        barChart.setData(data);








        return inflater.inflate(R.layout.fragment_fm_rpt_profit_from_sales, container, false);
    }

}
