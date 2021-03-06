package com.example.williamsumitro.dress.view.view.wishlist.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.WishlistResponse;
import com.example.williamsumitro.dress.view.model.WishlistResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.wishlist.adapter.WishlistRVAdapter;
import com.example.williamsumitro.dress.view.view.wishlist.adapter.WishlistRVTouch;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment implements WishlistRVTouch.WishListTouchListener {
    @BindView(R.id.wishlist_rv) RecyclerView recyclerView;
    @BindView(R.id.wishlist_ln_top) LinearLayout top;
    @BindView(R.id.wishlist_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.wishlist_container) LinearLayout container;

    private Context context;
    private WishlistRVAdapter adapter;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private ArrayList<WishlistResult> wishlistResultArrayList;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private SweetAlertDialog sweetAlertDialog;

    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        initView(view);
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
            }
        });
        return view;
    }
    private void initRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        api_getwishlist();
    }
    private void initView(View view) {
        ButterKnife.bind(this,view);
        context = getActivity();
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        wishlistResultArrayList = new ArrayList<>();
        progressDialog = new ProgressDialog(context);
    }
    private void api_getwishlist(){
        service = apiUtils.getAPIService();
        service.req_get_my_wishlist(token).enqueue(new Callback<WishlistResponse>() {
            @Override
            public void onResponse(Call<WishlistResponse> call, Response<WishlistResponse> response) {
                if (response.code() == 200){
                    if (response.body().getWishlistResult().size() >0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        wishlistResultArrayList = response.body().getWishlistResult();
                        setuprv();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    else {
                        top.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<WishlistResponse> call, Throwable t) {
                initDialog("",3);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setuprv() {
        adapter = new WishlistRVAdapter(wishlistResultArrayList, context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(alphaAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new WishlistRVTouch(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof WishlistRVAdapter.ViewHolder) {
            final int index = viewHolder.getAdapterPosition();
            final String name = wishlistResultArrayList.get(index).getProductName();
            final WishlistResult wishlistresult = wishlistResultArrayList.get(index);

            adapter.removeItem(index);
            Snackbar snackbar = Snackbar
                    .make(container, name + " removed from wishlist!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void initDialog(final String message, int stats){
        if(stats == 0){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Invalid")
                    .setContentText(message)
                    .setConfirmText("Try Again")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
        if (stats == 3){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Sorry")
                    .setContentText("There is a problem with internet connection or the server")
                    .setConfirmText("Try Again")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
    }
}
