package com.example.williamsumitro.dress.view.view.purchase.history.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.model.Purchase_TransactionHistoryResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.checkout.adapter.CheckoutProductRVAdapter;
import com.example.williamsumitro.dress.view.view.main.MainActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class PurchaseHistoryDetail extends AppCompatActivity {
    @BindView(R.id.purchasehistorydetail_appbar) AppBarLayout appBarLayout;
    @BindView(R.id.purchasehistorydetail_tv_total) TextView total;
    @BindView(R.id.purchasehistorydetail_tv_subtotal) TextView subtotal;
    @BindView(R.id.purchasehistorydetail_tv_shippingfee) TextView shippingfee;
    @BindView(R.id.purchasehistorydetail_tv_ordernumber) TextView ordernumber;
    @BindView(R.id.purchasehistorydetail_toolbar) Toolbar toolbar;
    @BindView(R.id.purchasehistorydetail_rv_rejectedproduct) RecyclerView rv_rejected;
    @BindView(R.id.purchasehistorydetail_rv_acceptedproduct) RecyclerView rv_accepted;
    @BindView(R.id.purchasehistorydetail_ln_rejectedproduct) LinearLayout container_rejected;
    @BindView(R.id.purchasehistorydetail_ln_rejectedproductdetail) LinearLayout container_rejecteddetail;
    @BindView(R.id.purchasehistorydetail_ln_acceptedproduct) LinearLayout container_accepted;
    @BindView(R.id.purchasehistorydetail_ln_acceptedproductdetail) LinearLayout container_accepteddetail;
    @BindView(R.id.purchasehistorydetail_img_rejectedcaret) ImageView caret_rejected;
    @BindView(R.id.purchasehistorydetail_img_acceptedcaret) ImageView caret_accepted;
    @BindView(R.id.purchasehistorydetail_btn_vieworderdetails) Button viewdetails;
    @BindView(R.id.purchasehistorydetail_tv_acceptedproduct) TextView acceptedproduct;
    @BindView(R.id.purchasehistorydetail_tv_rejectedproduct) TextView rejectedproduct;

    private Context context;
    private ProgressDialog progressDialog;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;
    private Purchase_TransactionHistoryResult result;
    private SnapHelper snapHelper;
    private Boolean click_accepted = false, click_rejected = false;
    private CheckoutProductRVAdapter rvadapter;
    private ArrayList<ProductInfo> productArrayList;
    private Dialog dialog;

    private final static String RESULT = "RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history_detail);
        initview();
        setupToolbar();
        initGetIntent();
        initObject();
        initOnClick();
    }

    private void initOnClick() {
        viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(result.getInvoiceDate(), result.getReceiverName(), result.getAddress(), result.getProvinceName(), result.getCityName(), result.getPhoneNumber(), result.getPostalCode(), result.getCourierName() + " " + result.getCourierService(), result.getNote());
            }
        });
    }

    private void initview() {
        ButterKnife.bind(this);
        supportPostponeEnterTransition();
        context = this;
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        formatter = new DecimalFormat("#,###,###");
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        progressDialog = new ProgressDialog(context);
        productArrayList = new ArrayList<>();
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Transaction Detail");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void initGetIntent() {
        Intent getintent = getIntent();
        if (getintent.hasExtra(RESULT)){
            result = (Purchase_TransactionHistoryResult) getintent.getSerializableExtra(RESULT);
        }
        else{
            Toasty.error(context, "SOMETHING WRONG", Toast.LENGTH_SHORT, true).show();
        }
    }

    private void initObject() {
        total.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(result.getTotalPrice()))));
        subtotal.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(result.getSubtotalPrice()))));
        shippingfee.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(result.getShippingPrice()))));
        ordernumber.setText("Order number : " + result.getOrderNumber());
        snapHelper = new LinearSnapHelper();
        if (result.getProductStatus().size()>0){
            rv_accepted.setVisibility(View.VISIBLE);
            productArrayList = new ArrayList<>();
            for (int i = 0; i<result.getProductStatus().size(); i++){
                ProductInfo product = new ProductInfo(result.getProductStatus().get(i).getProductId(),
                        result.getProductStatus().get(i).getProductName(),
                        result.getProductStatus().get(i).getProductPhoto(),
                        result.getProductStatus().get(i).getPriceUnit(),
                        result.getProductStatus().get(i).getTotalQty(),
                        result.getProductStatus().get(i).getPriceTotal(),
                        result.getProductStatus().get(i).getSizeInfo());
                productArrayList.add(product);
            }
            rvadapter = new CheckoutProductRVAdapter(productArrayList, context);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            rv_accepted.setLayoutManager(layoutManager);
            rv_accepted.setItemAnimator(new DefaultItemAnimator());
            rv_accepted.setAdapter(rvadapter);
            snapHelper.attachToRecyclerView(rv_accepted);
        }
        else {
            acceptedproduct.setVisibility(View.VISIBLE);
        }
        if (result.getProductRejected().size()>0){
            rv_rejected.setVisibility(View.VISIBLE);
            productArrayList = new ArrayList<>();
            for (int i = 0; i<result.getProductRejected().size();i++){
                ProductInfo product = new ProductInfo(result.getProductRejected().get(i).getProductId(),
                        result.getProductRejected().get(i).getProductName(),
                        result.getProductRejected().get(i).getProductPhoto(),
                        result.getProductRejected().get(i).getPriceUnit(),
                        result.getProductRejected().get(i).getTotalQty(),
                        result.getProductRejected().get(i).getPriceTotal(),
                        result.getProductRejected().get(i).getSizeInfo());
                productArrayList.add(product);
            }
            rvadapter = new CheckoutProductRVAdapter(productArrayList, context);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            rv_rejected.setLayoutManager(layoutManager);
            rv_rejected.setItemAnimator(new DefaultItemAnimator());
            rv_rejected.setAdapter(rvadapter);
            snapHelper.attachToRecyclerView(rv_rejected);
        }
        else {
            rejectedproduct.setVisibility(View.VISIBLE);
        }
        container_accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!click_accepted){
                    container_accepteddetail.setVisibility(View.VISIBLE);
                    click_accepted = true;
                    caret_accepted.setImageResource(R.drawable.caret1);
                }
                else {
                    container_accepteddetail.setVisibility(View.GONE);
                    click_accepted = false;
                    caret_accepted.setImageResource(R.drawable.caret);
                }
            }
        });
        container_rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!click_rejected){
                    container_rejecteddetail.setVisibility(View.VISIBLE);
                    click_rejected = true;
                    caret_rejected.setImageResource(R.drawable.caret1);
                }
                else {
                    container_rejecteddetail.setVisibility(View.GONE);
                    click_rejected = false;
                    caret_rejected.setImageResource(R.drawable.caret);
                }
            }
        });
    }
    private void initDialog(final String invoicedate, final String receivername, final String shippingaddress,
                            final String province, final String city, final String phonenumber,
                            final String postalcode, final String courier, final String note){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_order);

        final TextView tv_invoicedate = (TextView) dialog.findViewById(R.id.orderdialog_tv_invoicedate);
        final TextView tv_receivername = (TextView) dialog.findViewById(R.id.orderdialog_tv_receivername);
        final TextView tv_shippingaddress = (TextView) dialog.findViewById(R.id.orderdialog_tv_shippingaddress);
        final TextView tv_province = (TextView) dialog.findViewById(R.id.orderdialog_tv_province);
        final TextView tv_city = (TextView) dialog.findViewById(R.id.orderdialog_tv_city);
        final TextView tv_phonenumber = (TextView) dialog.findViewById(R.id.orderdialog_tv_phonenumber);
        final TextView tv_postalcode = (TextView) dialog.findViewById(R.id.orderdialog_tv_postalcode);
        final TextView tv_courier = (TextView) dialog.findViewById(R.id.orderdialog_tv_courier);
        final TextView tv_note = (TextView) dialog.findViewById(R.id.orderdialog_tv_note);
        final Button buttonok = (Button) dialog.findViewById(R.id.orderdialog_btnok);

        tv_invoicedate.setText(invoicedate);
        tv_receivername.setText(receivername);
        tv_shippingaddress.setText(shippingaddress);
        tv_province.setText(province);
        tv_city.setText(city);
        tv_phonenumber.setText(phonenumber);
        tv_postalcode.setText(postalcode);
        tv_courier.setText(courier);
        tv_note.setText(note);

        buttonok.setText("Ok");
        buttonok.setBackgroundResource(R.drawable.button1_green);
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.toolbarhome) {
            Intent intent = new Intent(context, MainActivity.class);
            initanim(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }
}
