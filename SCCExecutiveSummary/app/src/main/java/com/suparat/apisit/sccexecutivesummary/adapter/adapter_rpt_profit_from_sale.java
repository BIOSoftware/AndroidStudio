package com.suparat.apisit.sccexecutivesummary.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.suparat.apisit.sccexecutivesummary.R;
import com.suparat.apisit.sccexecutivesummary.model.SP_WEB_RP_SUMPROFIT_ALL;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Apisit on 30/08/2560.
 */

public class adapter_rpt_profit_from_sale extends BaseAdapter {
    private LayoutInflater mInflater;
    ArrayList<SP_WEB_RP_SUMPROFIT_ALL> mList ;
    private ViewHolder mViewHolder;

    public adapter_rpt_profit_from_sale (Activity activity,ArrayList<SP_WEB_RP_SUMPROFIT_ALL> sp_web_rp_sumprofit_alls){
        mInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mList = sp_web_rp_sumprofit_alls;
    }



    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(R.layout.listview_rpt_profit_from_sale, viewGroup, false);
            mViewHolder = new ViewHolder();
            mViewHolder._SaleType = (TextView) view.findViewById(R.id.txt_SaleType);
            mViewHolder._SaleAmount = (TextView) view.findViewById(R.id.txt_titleSaleAmount);
            mViewHolder._CostAmount = (TextView) view.findViewById(R.id.txt_titleCostAmount);
            mViewHolder._ProfitAmount = (TextView) view.findViewById(R.id.txt_titleProfitAmount);

            view.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) view.getTag();
        }

        SP_WEB_RP_SUMPROFIT_ALL aData = mList.get(i);

        switch (aData.getXTYPE()){
            case "1" : mViewHolder._SaleType.setText("ขายรถ");
                break;
            case "2" : mViewHolder._SaleType.setText("ขายอะไหล่");
                break;
            case "3" : mViewHolder._SaleType.setText("บริการ");
                break;
            case "4" : mViewHolder._SaleType.setText("เช่าซื้อ");
                break;
        }

        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        DecimalFormat formatter = new DecimalFormat("#,##0.00", decimalFormatSymbols);


        mViewHolder._SaleAmount.setText( formatter.format(aData.getSUM_SALE_BEFORE_VAT()) );
        mViewHolder._CostAmount.setText( formatter.format(aData.getSUM_COST_ALL()) );
        mViewHolder._ProfitAmount.setText( formatter.format(aData.getSUM_PROFIT_AMOUNT()) );

        return view;

            }

    private static class ViewHolder {
        TextView _SaleType;
        TextView _SaleAmount;
        TextView _CostAmount;
        TextView _ProfitAmount;

    }
}
