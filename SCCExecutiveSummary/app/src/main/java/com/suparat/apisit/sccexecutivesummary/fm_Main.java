package com.suparat.apisit.sccexecutivesummary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;



/**
 * A simple {@link Fragment} subclass.
 */
public class fm_Main extends Fragment {
    private View v;

    public fm_Main() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_fm_main, container, false);


        final ImageView img_Report1 = (ImageView)v.findViewById(R.id.img_Report1);
        img_Report1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                fm_rpt_profit_from_sales fm_view = new fm_rpt_profit_from_sales();
                ((MainActivity)getActivity()).setnewFragment(fm_view,true,"รายงานผู้บริหาร");
            }
        });




        ImageView img_Report3 = (ImageView)v.findViewById(R.id.img_Report3);
        img_Report3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm_rpt_mcs_top_sale fm_view = new fm_rpt_mcs_top_sale();
                ((MainActivity)getActivity()).setnewFragment(fm_view,true,"รายงานยอดขายสูงสุด");
            }
        });









        return v;

    }

}
