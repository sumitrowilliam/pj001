package com.example.williamsumitro.dress.view.view.product.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.FullScreenImage;
import com.example.williamsumitro.dress.view.model.DownlinePartner;
import com.example.williamsumitro.dress.view.model.FilterProductStore;
import com.example.williamsumitro.dress.view.model.Price;
import com.example.williamsumitro.dress.view.model.ProductResponse;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.model.ReviewRating;
import com.example.williamsumitro.dress.view.model.StoreInfo;
import com.example.williamsumitro.dress.view.model.StoreResponse;
import com.example.williamsumitro.dress.view.model.UplinePartner;
import com.example.williamsumitro.dress.view.model.UserResponse;
import com.example.williamsumitro.dress.view.model.dress_attribute.Size;
import com.example.williamsumitro.dress.view.model.model_CourierService;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.authentication.Login;
import com.example.williamsumitro.dress.view.view.bag.activity.AddToBagActivity;
import com.example.williamsumitro.dress.view.view.bag.adapter.BuyRVAdapter;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.example.williamsumitro.dress.view.view.product.adapter.DetailProductCourierRVAdapter;
import com.example.williamsumitro.dress.view.view.product.adapter.DetailProductReviewsRVADapter;
import com.example.williamsumitro.dress.view.view.product.adapter.DetailProduct_DownlineRV;
import com.example.williamsumitro.dress.view.view.store.activity.DetailStore;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProduct extends AppCompatActivity {
    @BindView(R.id.detailproduct_appbar) AppBarLayout appBarLayout;
    @BindView(R.id.detailproduct_collapstoolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.detailproduct_toolbar) Toolbar toolbar;
    @BindView(R.id.detailproduct_tvMinOrder) TextView minOrder;
    @BindView(R.id.detailproduct_tvPrice) TextView price;
    @BindView(R.id.detailproduct_tv_store) TextView storename;
    @BindView(R.id.detailproduct_btnAddtobag) Button addtobag;
    @BindView(R.id.detailproduct_lncourier) LinearLayout container_courier;
    @BindView(R.id.detailproduct_lnreview) LinearLayout review;
    @BindView(R.id.detailproduct_rvpricedetails) RecyclerView pricedetails;
    @BindView(R.id.detailproduct_image) ImageView image;
    @BindView(R.id.detailproduct_tv_productname) TextView productname;
    @BindView(R.id.detailproduct_tv_decoration) TextView decoration;
    @BindView(R.id.detailproduct_tv_waiseline) TextView waiseline;
    @BindView(R.id.detailproduct_tv_style) TextView style;
    @BindView(R.id.detailproduct_tv_sleeveLength) TextView sleevelength;
    @BindView(R.id.detailproduct_tv_productdetail) TextView productdetail;
    @BindView(R.id.detailproduct_tv_patterntype) TextView patterntype;
    @BindView(R.id.detailproduct_tv_neckline) TextView neckline;
    @BindView(R.id.detailproduct_tv_material) TextView material;
    @BindView(R.id.detailproduct_tv_season) TextView season;
    @BindView(R.id.detailproduct_tv_fabrictype) TextView fabrictype;
    @BindView(R.id.detailproduct_lnproduct) LinearLayout container_product;
    @BindView(R.id.detailproduct_ln_productdetail) LinearLayout container_productdetail;
    @BindView(R.id.detailproduct_img_productcaret) ImageView productcaret;
    @BindView(R.id.detailproduct_tv_weight) TextView weight;
    @BindView(R.id.detailproduct_tv_sold) TextView sold;
    @BindView(R.id.detailproduct_tv_size) TextView size;
    @BindView(R.id.detailproduct_tv_rating) TextView rating;
    @BindView(R.id.detailproduct_tv_courier) TextView courier;
    @BindView(R.id.detailproduct_lnsize) LinearLayout container_size;
    @BindView(R.id.detailproduct_img_star5) ImageView star5;
    @BindView(R.id.detailproduct_img_star4) ImageView star4;
    @BindView(R.id.detailproduct_img_star3) ImageView star3;
    @BindView(R.id.detailproduct_img_star2) ImageView star2;
    @BindView(R.id.detailproduct_img_star1) ImageView star1;
    @BindView(R.id.detailproduct_lnstore) LinearLayout container_store;
    @BindView(R.id.detailproduct_btnAddtowishlist) Button addtowishlist;
    @BindView(R.id.detailproduct_ln_downlinepartnership) LinearLayout container_downlinepartnership;
    @BindView(R.id.detailproduct_ln_detaildownlinepartnership) LinearLayout container_detaildownlinepartnership;
    @BindView(R.id.detailproduct_rv_downlinepartnership) RecyclerView rv_downlinepartnership;
    @BindView(R.id.detailproduct_btn_uplinepartnership) Button btn_uplinepartnerhsip;
    @BindView(R.id.detailproduct_tv_uplinepartnership) TextView storename_uplinepartnership;
    @BindView(R.id.detailproduct_ln_uplinepartnership) LinearLayout container_uplinepartnership;
    @BindView(R.id.detailproduct_img_uplinepartnership) CircleImageView imagestore_uplinepartnership;
    @BindView(R.id.detailproduct_caret_downlinepartnership) ImageView caret_downline;
    @BindView(R.id.detailproduct_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.detailproduct_ln_report) LinearLayout reportproduct;

    private final static String NAMAPRODUCT = "NAMAPRODUCT";
    private final static String QTYMINORDER = "QTYMINORDER";
    private final static String QTYMAXORDER = "QTYMAXORDER";
    private final static String GAMBARPRODUCT = "GAMBARPRODUCT";
    private final static String MINORDER = "MINORDER";
    private final static String PRICELIST = "PRICELIST";
    private final static String SIZELIST = "SIZELIST";
    private final static String PRODUCT_ID = "PRODUCT_ID";
    private final static String STORE_ID = "STORE_ID";
    private final static String IMAGE = "IMAGE";
    private final static String INBAG = "INBAG";


    private static final Integer[] XMEN= {R.drawable.fake,R.drawable.fake,R.drawable.fake,R.drawable.fake,R.drawable.fake};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    private Context context;
    private static int currentPage = 0;

    private List<String> report = new ArrayList<>();
    private BuyRVAdapter pricedetailsadapter;
    private apiService service;
    private ProductInfo productInfo;
    private StoreInfo storeInfo;
    private ArrayList<Size> sizeList;
    private ArrayList<Price> priceList;
    private ArrayList<String> qtyminlist, qtymaxlist, priceminlist, availablesizelist;
    private Boolean detailclick = false, wishliststatus= false, click_downline = false;
    private ArrayList<model_CourierService> courierServiceList;
    private String extra_productid;
    private String token, total_qty="0", averagerating, choosen_report;
    private SessionManagement sessionManagement;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private SweetAlertDialog sweetAlertDialog;
    private DetailProductCourierRVAdapter adapter;
    private DetailProductReviewsRVADapter review_adapter;
    private TextView itemcount;
    private UplinePartner uplinePartner;
    private ArrayList<DownlinePartner> downlinePartnerArrayList;
    private Boolean is_partnerhsip = false;
    private DetailProduct_DownlineRV downline_adapter;
    private ArrayList<ReviewRating> reviewRatingArrayList;
    private DecimalFormat formatter, df;
    private ArrayList<Size> sizes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        initView();
        initGetIntent();
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
            }
        });
        initCollapToolbar();
        setupToolbar();
    }
    private void initGetIntent(){
        Intent getintent = getIntent();
        if (getintent.hasExtra(PRODUCT_ID)){
            extra_productid = getintent.getExtras().getString(PRODUCT_ID);
        }
        else{
            Toasty.error(context, "SOMETHING WRONG", Toast.LENGTH_SHORT, true).show();
        }
    }
    private void initRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        progressDialog.setMessage("Retrieving all the data, please wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        api_getdetailproduct();
        swipeRefreshLayout.setRefreshing(false);
    }
    private void initProductDetails() {
        if (is_partnerhsip) {
            container_uplinepartnership.setVisibility(View.VISIBLE);
            storename_uplinepartnership.setText(productInfo.getUplinePartner().getStoreNameUpline());
            Picasso.with(context)
                    .load(productInfo.getUplinePartner().getStorePhotoUpline())
                    .memoryPolicy(MemoryPolicy.NO_CACHE )
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.default_photo)
                    .into(imagestore_uplinepartnership);
            btn_uplinepartnerhsip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailProduct.class);
                    intent.putExtra(PRODUCT_ID, productInfo.getUplinePartner().getUplineProductId().toString());
                    initanim(intent);
                }
            });
        }
        else
            container_downlinepartnership.setVisibility(View.VISIBLE);
        productname.setText(productInfo.getProductName());
        if (productInfo.getMaxPrice().matches("\\d+(?:\\.\\d+)?")){
            price.setText("IDR " + formatter.format(Double.parseDouble(priceList.get(0).getPrice())));
        }
        else {
            price.setText(productInfo.getMaxPrice());
            addtobag.setBackgroundColor(getResources().getColor(R.color.grey3));
            addtobag.setEnabled(false);
        }
        minOrder.setText(String.valueOf(productInfo.getMinOrder()));
        Picasso.with(context)
                .load(productInfo.getPhoto())
                .memoryPolicy(MemoryPolicy.NO_CACHE )
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.default_product)
                .into(image);
        style.setText(productInfo.getStyleName());
        season.setText(productInfo.getSeasonName());
        neckline.setText(productInfo.getNecklineName());
        sleevelength.setText(productInfo.getSleevelengthName());
        waiseline.setText(productInfo.getWaiselineName());
        material.setText(productInfo.getMaterialName());
        fabrictype.setText(productInfo.getFabrictypeName());
        decoration.setText(productInfo.getDecorationName());
        patterntype.setText(productInfo.getPatterntypeName());
        rating.setText("("+df.format(Double.parseDouble(productInfo.getRating()))+")");
        weight.setText(String.valueOf(productInfo.getWeight()) +" gr");
        courier.setText(String.valueOf(courierServiceList.size()));
        if (Double.parseDouble(productInfo.getRating()) == 0){
            star1.setImageResource(R.drawable.star0);
            star2.setImageResource(R.drawable.star0);
            star3.setImageResource(R.drawable.star0);
            star4.setImageResource(R.drawable.star0);
            star5.setImageResource(R.drawable.star0);
        }
        else if(Double.parseDouble(productInfo.getRating())>0 && Double.parseDouble(productInfo.getRating())<1){
            star1.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(productInfo.getRating()) == 1){
            star1.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(productInfo.getRating())>1 && Double.parseDouble(productInfo.getRating())<2){
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(productInfo.getRating()) == 2){
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(productInfo.getRating())>2 && Double.parseDouble(productInfo.getRating())<3){
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(productInfo.getRating()) == 3){
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(productInfo.getRating())>3 && Double.parseDouble(productInfo.getRating())<4){
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star);
            star4.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(productInfo.getRating()) == 4){
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star);
            star4.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(productInfo.getRating())>4 && Double.parseDouble(productInfo.getRating())<5){
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star);
            star4.setImageResource(R.drawable.star);
            star5.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(productInfo.getRating()) == 5){
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star);
            star4.setImageResource(R.drawable.star);
            star5.setImageResource(R.drawable.star);
        }
        StringBuilder availablesize = new StringBuilder();
        for (int i = 0; i<sizeList.size();i++) {
            availablesizelist.add(String.valueOf(sizeList.get(i).getSizeId()));
            availablesize.append(sizeList.get(i).getSizeName());
            if (i<sizeList.size()-1)
                availablesize.append(", ");
        }
        size.setText(availablesize);
        sold.setText(String.valueOf(productInfo.getSold()));
        storename.setText(storeInfo.getName());

    }
    private void initSendData(Intent intent){
        intent.putExtra(PRODUCT_ID, extra_productid);
        intent.putExtra(NAMAPRODUCT, productInfo.getProductName());
        intent.putExtra(GAMBARPRODUCT, productInfo.getPhoto());
        intent.putExtra(MINORDER, String.valueOf(productInfo.getMinOrder()));
        intent.putStringArrayListExtra(PRICELIST, priceminlist);
        intent.putStringArrayListExtra(QTYMINORDER, qtyminlist);
        intent.putStringArrayListExtra(QTYMAXORDER, qtymaxlist);
        intent.putExtra(SIZELIST, productInfo.getSize());
        intent.putExtra(INBAG, productInfo.getInBag().toString());
    }
    private void api_wishlist(){
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        service.req_get_auth_user(token).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code()==200){
                    if (response.body().getStatus()){
                        if (wishliststatus){
                            service.req_delete_from_wishlist(token, String.valueOf(productInfo.getProductId())).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.code()==200){
                                        try{
                                            JSONObject jsonResults = new JSONObject(response.body().string());
                                            if(jsonResults.getBoolean("status")){
                                                String message = jsonResults.getString("message");
                                                Toasty.success(context, message, Toast.LENGTH_SHORT, true).show();
                                                addtowishlist.setBackgroundColor(getResources().getColor(R.color.blue1));
                                                addtowishlist.setText("Add to Wishlist");
                                                wishliststatus = false;
                                                progressDialog.dismiss();
                                            }
                                        }catch (JSONException e){
                                            e.printStackTrace();
                                        }catch (IOException e){
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    progressDialog.dismiss();
                                    initDialog(3);
                                }
                            });
                        }
                        else {
                            service.req_add_to_wishlist(token, String.valueOf(productInfo.getProductId())).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.code()==200){
                                        try{
                                            JSONObject jsonResults = new JSONObject(response.body().string());
                                            if(jsonResults.getBoolean("status")){
                                                String message = jsonResults.getString("message");
                                                Toasty.success(context, message, Toast.LENGTH_SHORT, true).show();
                                                addtowishlist.setBackgroundColor(getResources().getColor(R.color.red2));
                                                addtowishlist.setText("Delete from Wishlist");
                                                wishliststatus = true;
                                                progressDialog.dismiss();
                                            }
                                        }catch (JSONException e){
                                            e.printStackTrace();
                                        }catch (IOException e){
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    progressDialog.dismiss();
                                    initDialog(3);
                                }
                            });
                        }
                    }
                    else {
                        Toasty.error(context, response.message(), Toast.LENGTH_SHORT, true).show();
                    }
                }
                else {
                    progressDialog.dismiss();
                    initDialog(1);
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                initDialog(3);
                progressDialog.dismiss();
            }
        });
    }
    private void api_addtobag(){
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        service.req_get_auth_user(token).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code()==200){
                    for (int i = 0; i <priceList.size(); i++){
                        priceminlist.add(String.valueOf(priceList.get(i).getPrice()));
                        qtyminlist.add(String.valueOf(priceList.get(i).getQtyMin()));
                        qtymaxlist.add(String.valueOf(priceList.get(i).getQtyMax()));
                    }
                    progressDialog.dismiss();
                    Intent intent = new Intent(context, AddToBagActivity.class);
                    initSendData(intent);
                    initanim(intent);
                }
                else {
                    progressDialog.dismiss();
                    initDialog(1);
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                initDialog(3);
                progressDialog.dismiss();
            }
        });
    }
    private void initClick(){
        addtowishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api_wishlist();
            }
        });
        addtobag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                api_addtobag();
            }
        });
        container_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!detailclick){
                    container_productdetail.setVisibility(View.VISIBLE);
                    detailclick = true;
                    productcaret.setImageResource(R.drawable.caret1);
                }
                else {
                    container_productdetail.setVisibility(View.GONE);
                    detailclick = false;
                    productcaret.setImageResource(R.drawable.caret);
                }
            }
        });

        container_downlinepartnership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!click_downline){
                    container_detaildownlinepartnership.setVisibility(View.VISIBLE);
                    click_downline = true;
                    caret_downline.setImageResource(R.drawable.caret1);
                }
                else {
                    container_detaildownlinepartnership.setVisibility(View.GONE);
                    click_downline = false;
                    caret_downline.setImageResource(R.drawable.caret);
                }
            }
        });

        container_courier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDialog(courierServiceList);
            }
        });
        container_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_store(storeInfo);
            }
        });

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_reviewrating(averagerating, reviewRatingArrayList);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FullScreenImage.class);
                intent.putExtra(IMAGE, productInfo.getPhoto());
                initanim(intent);
            }
        });
        reportproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_report();
            }
        });
    }
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }
    private void initView() {
        ButterKnife.bind(this);
        supportPostponeEnterTransition();
        context = this;
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        priceList = new ArrayList<>();
        sizeList = new ArrayList<>();
        courierServiceList = new ArrayList<>();
        priceminlist = new ArrayList<>();
        qtyminlist = new ArrayList<>();
        qtymaxlist = new ArrayList<>();
        availablesizelist = new ArrayList<>();
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        progressDialog = new ProgressDialog(context);
        downlinePartnerArrayList = new ArrayList<>();
        reviewRatingArrayList = new ArrayList<>();
        formatter = new DecimalFormat("#,###,###");
        df = new DecimalFormat("###.#");
        service = apiUtils.getAPIService();
        PicassoTools.clearCache(Picasso.with(context));
        sizes = new ArrayList<>();
    }
    private void initCollapToolbar(){
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context, R.color.colorPrimary));
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollrange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollrange == -1){
                    scrollrange = appBarLayout.getTotalScrollRange();
                }
                if(scrollrange + verticalOffset == 0){
                    toolbar.setTitle(productInfo.getProductName());
                    isShow = true;
                } else if (isShow) {
                    toolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(" ");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void setupRV(){
        pricedetailsadapter = new BuyRVAdapter(priceList, context);
        RecyclerView.LayoutManager horizontallayout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        pricedetails.setLayoutManager(horizontallayout);
        pricedetails.setItemAnimator(new DefaultItemAnimator());
        pricedetails.setAdapter(pricedetailsadapter);
        if (downlinePartnerArrayList.size()>0){
            downline_adapter = new DetailProduct_DownlineRV(context, downlinePartnerArrayList);
            RecyclerView.LayoutManager horizontallayout1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            rv_downlinepartnership.setLayoutManager(horizontallayout1);
            rv_downlinepartnership.setItemAnimator(new DefaultItemAnimator());
            rv_downlinepartnership.setAdapter(downline_adapter);
        }else {
            container_downlinepartnership.setVisibility(View.GONE);
        }
    }
    private void api_getdetailproduct(){
        service.req_get_auth_user(token).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code()==200){
                    service.req_get_product_detail(token, extra_productid).enqueue(new Callback<ProductResponse>() {
                        @Override
                        public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                            if (response.code()==200){
                                if (response.body().getStatus()){
                                    productInfo = response.body().getProductInfo();
                                    storeInfo = response.body().getStoreInfo();
                                    service.req_get_user_store(token).enqueue(new Callback<StoreResponse>() {
                                        @Override
                                        public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                                            if(response.body().getHaveStore()){
                                                if (storeInfo.getStoreId().toString().equals(response.body().getStore().getStoreId().toString())){
                                                    addtobag.setBackgroundColor(getResources().getColor(R.color.grey3));
                                                    addtobag.setEnabled(false);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<StoreResponse> call, Throwable t) {

                                        }
                                    });
                                    priceList = productInfo.getPrice();
                                    sizeList = productInfo.getSize();
                                    courierServiceList = storeInfo.getCourierService();
                                    wishliststatus = response.body().getWishlistStatus();
                                    is_partnerhsip = productInfo.getIsPartnership();
                                    averagerating = productInfo.getRating();
                                    downlinePartnerArrayList = productInfo.getDownlinePartner();
                                    reviewRatingArrayList = productInfo.getReviewRating();
                                    if (wishliststatus){
                                        addtowishlist.setBackgroundColor(getResources().getColor(R.color.red2));
                                        addtowishlist.setText("Delete from Wishlist");
                                    }
                                    else {
                                        addtowishlist.setBackgroundColor(getResources().getColor(R.color.blue1));
                                        addtowishlist.setText("Add to Wishlist");
                                    }
                                    initProductDetails();
                                    setupRV();
                                    initClick();
                                    progressDialog.dismiss();
                                }
                                else {
                                    Toasty.error(context, "Product Non Active", Toast.LENGTH_SHORT, true).show();
                                    onBackPressed();
                                    overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                                    finish();
                                    progressDialog.dismiss();
                                }

                            }
                            else {
                                Toasty.error(context, response.message(), Toast.LENGTH_SHORT, true).show();
                                progressDialog.dismiss();
                            }
                        }
                        @Override
                        public void onFailure(Call<ProductResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            initDialog(3);
                        }
                    });
                }
                else {
                    service.req_get_product_detail(extra_productid).enqueue(new Callback<ProductResponse>() {
                        @Override
                        public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                            if (response.code()==200){
                                productInfo = response.body().getProductInfo();
                                storeInfo = response.body().getStoreInfo();
                                priceList = productInfo.getPrice();
                                sizeList = productInfo.getSize();
                                courierServiceList = storeInfo.getCourierService();
                                wishliststatus = response.body().getWishlistStatus();
                                is_partnerhsip = productInfo.getIsPartnership();
                                averagerating = productInfo.getRating();
                                downlinePartnerArrayList = productInfo.getDownlinePartner();
                                reviewRatingArrayList = productInfo.getReviewRating();
                                initProductDetails();
                                setupRV();
                                initClick();
                                progressDialog.dismiss();
                            }
                            else {
                                Toasty.error(context, "Some error here", Toast.LENGTH_SHORT, true).show();
                                progressDialog.dismiss();
                            }
                        }
                        @Override
                        public void onFailure(Call<ProductResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            initDialog(3);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.dismiss();
                initDialog(3);
            }
        });

    }
    private void initDialog(int stats){
        if (stats == 1){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Authorization")
                    .setContentText("You need to login first !")
                    .setConfirmText("Login")
                    .setCancelText("Cancel")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            Intent intent = new Intent(context, Login.class);
                            initanim(intent);
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();
        }
        if (stats == 2){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Success")
                    .setConfirmText("Ok")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();
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
    private void initDialog(ArrayList<model_CourierService> courierServices){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_courier);

        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.courierdialog_rv);
        final Button buttonok = (Button) dialog.findViewById(R.id.courierdialog_btn_ok);
        adapter = new DetailProductCourierRVAdapter(courierServices, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
    private void dialog_reviewrating(String average_rating, ArrayList<ReviewRating> reviewRatingArrayList){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_reviewrating);

        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.reviewratingdialog_recyclerview);
        final TextView averagerating = (TextView) dialog.findViewById(R.id.reviewratingdialog_tvscore);
        final ImageView one = (ImageView) dialog.findViewById(R.id.reviewratingdialog_imgstar1);
        final ImageView two = (ImageView) dialog.findViewById(R.id.reviewratingdialog_imgstar2);
        final ImageView three = (ImageView) dialog.findViewById(R.id.reviewratingdialog_imgstar3);
        final ImageView four = (ImageView) dialog.findViewById(R.id.reviewratingdialog_imgstar4);
        final ImageView five = (ImageView) dialog.findViewById(R.id.reviewratingdialog_imgstar5);
        final Button ok = (Button) dialog.findViewById(R.id.reviewratingdialog_btn_ok);

        averagerating.setText("Score : "+df.format(Double.parseDouble(average_rating)));
        if (average_rating == null || Double.parseDouble(average_rating) == 0){
            one.setImageResource(R.drawable.star0);
            two.setImageResource(R.drawable.star0);
            three.setImageResource(R.drawable.star0);
            four.setImageResource(R.drawable.star0);
            five.setImageResource(R.drawable.star0);
        }
        else if(Double.parseDouble(average_rating)>0 && Double.parseDouble(average_rating)<1){
            one.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(average_rating) == 1){
            one.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(average_rating)>1 && Double.parseDouble(average_rating)<2){
            one.setImageResource(R.drawable.star);
            two.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(average_rating) == 2){
            one.setImageResource(R.drawable.star);
            two.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(average_rating)>2 && Double.parseDouble(average_rating)<3){
            one.setImageResource(R.drawable.star);
            two.setImageResource(R.drawable.star);
            three.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(average_rating) == 3){
            one.setImageResource(R.drawable.star);
            two.setImageResource(R.drawable.star);
            three.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(average_rating)>3 && Double.parseDouble(average_rating)<4){
            one.setImageResource(R.drawable.star);
            two.setImageResource(R.drawable.star);
            three.setImageResource(R.drawable.star);
            four.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(average_rating) == 4){
            one.setImageResource(R.drawable.star);
            two.setImageResource(R.drawable.star);
            three.setImageResource(R.drawable.star);
            four.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(average_rating)>4 && Double.parseDouble(average_rating)<5){
            one.setImageResource(R.drawable.star);
            two.setImageResource(R.drawable.star);
            three.setImageResource(R.drawable.star);
            four.setImageResource(R.drawable.star);
            five.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(average_rating) == 5){
            one.setImageResource(R.drawable.star);
            two.setImageResource(R.drawable.star);
            three.setImageResource(R.drawable.star);
            four.setImageResource(R.drawable.star);
            five.setImageResource(R.drawable.star);
        }

        review_adapter = new DetailProductReviewsRVADapter(reviewRatingArrayList, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(review_adapter);
        review_adapter.notifyDataSetChanged();
        ok.setText("Ok");
        ok.setBackgroundResource(R.drawable.button1_green);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void dialog_store(final StoreInfo storeInfo){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_store);

        final TextView tv_storename = (TextView) dialog.findViewById(R.id.storedialog_tv_storename);
        final TextView tv_soldproduct = (TextView) dialog.findViewById(R.id.storedialog_tv_soldproduct);
        final TextView tv_totaltransaction = (TextView) dialog.findViewById(R.id.storedialog_tv_totaltransaction);
        final TextView tv_establishedyear = (TextView) dialog.findViewById(R.id.storedialog_tv_establishedyear);
        final TextView tv_province = (TextView) dialog.findViewById(R.id.storedialog_tv_province);
        final TextView tv_city = (TextView) dialog.findViewById(R.id.storedialog_tv_city);
        final TextView tv_businesstype = (TextView) dialog.findViewById(R.id.storedialog_tv_businesstype);
        final TextView tv_description = (TextView) dialog.findViewById(R.id.storedialog_tv_description);
        final TextView tv_contactpersonname = (TextView) dialog.findViewById(R.id.storedialog_tv_contactpersonname);
        final TextView tv_contactpersonjobtitle = (TextView) dialog.findViewById(R.id.storedialog_tv_contactpersonjobtitle);
        final TextView tv_contactpersonphonenumber = (TextView) dialog.findViewById(R.id.storedialog_tv_contactpersonphonenumber);
        final Button buttonok = (Button) dialog.findViewById(R.id.storedialog_btn_ok);
        final Button buttonviewstore = (Button) dialog.findViewById(R.id.storedialog_btn_viewstore);

        tv_storename.setText(storeInfo.getName());
        tv_soldproduct.setText(storeInfo.getSoldProduct());
        tv_totaltransaction.setText(storeInfo.getTransaction().toString());
        tv_establishedyear.setText(storeInfo.getEstablishedYear());
        tv_province.setText(storeInfo.getProvinceName());
        tv_city.setText(storeInfo.getCityName());
        tv_businesstype.setText(storeInfo.getBusinessType());
        tv_description.setText(storeInfo.getDescription());
        tv_contactpersonname.setText(storeInfo.getContactPersonName());
        tv_contactpersonjobtitle.setText(storeInfo.getContactPersonJobTitle());
        tv_contactpersonphonenumber.setText(storeInfo.getContactPersonPhoneNumber());

        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        buttonviewstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailStore.class);
                intent.putExtra(STORE_ID, storeInfo.getStoreId().toString());
                initanim(intent);
            }
        });
        dialog.show();
    }

    private void dialog_report(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_report);

        final Spinner spinner = (Spinner) dialog.findViewById(R.id.dialog_report_spinner);
        final EditText comment = (EditText) dialog.findViewById(R.id.dialog_report_et);
        final Button buttonok = (Button) dialog.findViewById(R.id.dialog_report_btn_submit);
        final Button close = (Button) dialog.findViewById(R.id.dialog_report_btn_close);
        report.add("Images does not match product");
        report.add("Offensive or adult content");
        report.add("Incorrect Information");
        report.add("Missing Information");
        report.add("Other");
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.item_spinner, report);
        dataAdapter.setDropDownViewResource(R.layout.item_spinner);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choosen_report = dataAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.req_reportproduct(token, productInfo.getProductId().toString(), choosen_report, comment.getText().toString()).enqueue(new Callback<FilterProductStore>() {
                    @Override
                    public void onResponse(Call<FilterProductStore> call, Response<FilterProductStore> response) {
                        if (response.code()==200){
                            initDialog(2);
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<FilterProductStore> call, Throwable t) {

                    }
                });
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
}
