package com.example.williamsumitro.dress.view.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Help extends AppCompatActivity {
    @BindView(R.id.help_toolbar) Toolbar toolbar;
    @BindView(R.id.help_button) Button help;
    @BindView(R.id.help_linear) LinearLayout linearLayout;
    private Context context;
    private int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        initView();
        setuptoolbar();
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Help");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
}
