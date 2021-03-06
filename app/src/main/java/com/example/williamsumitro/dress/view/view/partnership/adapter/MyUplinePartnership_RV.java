package com.example.williamsumitro.dress.view.view.partnership.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.PartnershipResult;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

/**
 * Created by William Sumitro on 7/9/2018.
 */

public class MyUplinePartnership_RV extends RecyclerView.Adapter<MyUplinePartnership_RV.ViewHolder> {
    private Context context;
    private ArrayList<PartnershipResult> resultArrayList;
    private Boolean click = false;
    private MyPartnershipDetail_RV rvadapter;
    private SnapHelper snapHelper = new LinearSnapHelper();

    public MyUplinePartnership_RV(Context context, ArrayList<PartnershipResult> resultArrayList){
        this.context = context;
        this.resultArrayList = resultArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mypartnership, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        PartnershipResult result = resultArrayList.get(position);
        holder.storename.setText(result.getStoreName());
        holder.rvproduct.setVisibility(View.VISIBLE);
        rvadapter = new MyPartnershipDetail_RV(context, result.getProduct());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.rvproduct.setLayoutManager(layoutManager);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(rvadapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        holder.rvproduct.setAdapter(alphaAdapter);
        snapHelper.attachToRecyclerView(holder.rvproduct);
        holder.container_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!click){
                    holder.container_productdetail.setVisibility(View.VISIBLE);
                    click = true;
                    holder.caret.setImageResource(R.drawable.caret1);
                }
                else {
                    holder.container_productdetail.setVisibility(View.GONE);
                    click = false;
                    holder.caret.setImageResource(R.drawable.caret);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.item_mypartnership_circleimageview) CircleImageView circleImageView;
        @BindView(R.id.item_mypartnership_tv_storename) TextView storename;
        @BindView(R.id.item_mypartnership_rv_product) RecyclerView rvproduct;
        @BindView(R.id.item_mypartnership_ln_productdetail) LinearLayout container_productdetail;
        @BindView(R.id.item_mypartnership_img_caret) ImageView caret;
        @BindView(R.id.item_mypartnership_ln_product) LinearLayout container_product;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
