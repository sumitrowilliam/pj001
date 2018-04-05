package com.example.williamsumitro.dress.view.view.store.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.view.store.adapter.DetailOutletVPAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyStore extends AppCompatActivity {
    @BindView(R.id.mystore_appbar) AppBarLayout appBarLayout;
    @BindView(R.id.mystore_circleLogo) CircleImageView circleImageView;
    @BindView(R.id.mystore_viewpager) ViewPager viewPager;
    @BindView(R.id.mystore_tvNamaToko) TextView namatoko;
    @BindView(R.id.mystore_Toolbar) Toolbar toolbar;
    @BindView(R.id.mystore_tablayout) TabLayout tabLayout;
    @BindView(R.id.mystore_imgBanner) ImageView banner;
    @BindView(R.id.mystore_imgstar1) ImageView star1;
    @BindView(R.id.mystore_imgstar2) ImageView star2;
    @BindView(R.id.mystore_imgstar3) ImageView star3;
    @BindView(R.id.mystore_imgstar4) ImageView star4;
    @BindView(R.id.mystore_imgstar5) ImageView star5;
    @BindView(R.id.mystore_collaptoolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store);
        initObject();
        initCollapToolbar();
        setupToolbar();
        setupViewPager();
    }
    private void initObject() {
        ButterKnife.bind(this);
        supportPostponeEnterTransition();
        context = this;
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
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
                    toolbar.setTitle("Nama Toko");
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
    private void setupViewPager(){
        viewPager.setAdapter(new DetailOutletVPAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }
}
