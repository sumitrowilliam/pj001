package com.example.williamsumitro.dress.view.view.openstore.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.openstore.adapter.OpenStore_Button;
import com.example.williamsumitro.dress.view.view.sellerpanel.OnNavigationBarListener;
import com.example.williamsumitro.dress.view.view.openstore.adapter.StepAdapter;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class OpenStore extends AppCompatActivity implements StepperLayout.StepperListener, OnNavigationBarListener{
    @BindView(R.id.openstore_stepperLayout) StepperLayout stepperLayout;
    @BindView(R.id.openstore_toolbar) Toolbar toolbar;

    private static final String CURRENT_STEP_POSITION_KEY = "position";
    private SessionManagement sessionManagement;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_store);
        initView();
        setuptoolbar();
        int startingStepPosition = savedInstanceState != null ? savedInstanceState.getInt(CURRENT_STEP_POSITION_KEY) : 0;
        stepperLayout.setAdapter(new StepAdapter(getSupportFragmentManager(), this), startingStepPosition);
        stepperLayout.setOffscreenPageLimit(2);
        stepperLayout.setListener(this);

    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        sessionManagement = new SessionManagement(context);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setTitle("Open Store");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_STEP_POSITION_KEY, stepperLayout.getCurrentStepPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        final int currentStepPosition = stepperLayout.getCurrentStepPosition();
        if (currentStepPosition > 0) {
            stepperLayout.onBackClicked();
        } else {
            finish();
        }
    }
    @Override
    public void onCompleted(View completeButton) {
        Intent intent = new Intent(this, Openstrore_Fileupload.class);
        initanim(intent);
    }

    @Override
    public void onError(VerificationError verificationError) {
        Toasty.warning(this, verificationError.getErrorMessage(), Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {

    }

    @Override
    public void onReturn() {
        finish();
    }

    @Override
    public void onChangeEndButtonsEnabled(boolean enabled) {
        stepperLayout.setNextButtonVerificationFailed(!enabled);
        stepperLayout.setCompleteButtonVerificationFailed(!enabled);
    }
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
        context.startActivity(intent);
    }
    public void ChangeColor(int stat) {
        if (stat==1){
            stepperLayout.setNextButtonColor(getResources().getColor(R.color.blue1));
        }
        if (stat==2){
            stepperLayout.setCompleteButtonColor(getResources().getColor(R.color.green1));
        }
    }
}
