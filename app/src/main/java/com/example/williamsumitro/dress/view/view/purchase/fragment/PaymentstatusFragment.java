package com.example.williamsumitro.dress.view.view.purchase.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Order;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentstatusFragment extends Fragment {

    @BindView(R.id.paymentstatus_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.paymentstatus_tv_total) TextView total;

    private Context context;
    private List<Order> orderList;
    int temp = 0;
    private DecimalFormat formatter;

    public PaymentstatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paymentstatus, container, false);
        initView(view);
        initData();
        for(int i = 0; i<orderList.size();i++){
            temp += Integer.valueOf(orderList.get(i).getTotal());
        }
        total.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(temp))));
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this, view);
        context = getActivity();
        orderList = new ArrayList<>();
        formatter = new DecimalFormat("#,###,###");
    }
    private void initData(){
        Order order = new Order(R.drawable.fake_store, "ABC Store", "ODR/2018/02/001/XX", "20 Februari 2018", 2000000);
        orderList.add(order);
        order = new Order(R.drawable.fake_store1,"TEK Store", "ODR/2018/01/001/XX", "18 Januari 2018", 2000000);
        orderList.add(order);
    }

}