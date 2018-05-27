package com.example.williamsumitro.dress.view.view.authentication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity{
    @BindView(R.id.login_btnLogin) Button login;
    @BindView(R.id.login_etemail) TextInputEditText email;
    @BindView(R.id.login_lnRegister) LinearLayout register;
    @BindView(R.id.login_layoutpassword) TextInputLayout layoutpassword;
    @BindView(R.id.login_layoutemail) TextInputLayout layoutemail;
    @BindView(R.id.login_etpassword) TextInputEditText password;
    @BindView(R.id.login_container) RelativeLayout container;
    @BindView(R.id.login_lnskip) LinearLayout skip;
    private Context context;
    private apiService service;
    private String token;
    private Dialog dialog;
    private ProgressDialog progressDialog;
    private SessionManagement sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initOnClick();
    }

    private void initOnClick() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDataToAPI(view);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Register.class);
                initanim(intent);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                initanim(intent);
            }
        });
    }
    private void presentActivity(View view){
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(MainActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(this, intent, optionsCompat.toBundle());
        finish();
    }
    private void postDataToAPI(View view){
        layoutemail.setErrorEnabled(false);
        layoutpassword.setErrorEnabled(false);
        if (TextUtils.isEmpty(email.getText())) {
            layoutemail.setErrorEnabled(true);
            layoutemail.setError("Email is required");
            return;
        }else if (TextUtils.isEmpty(password.getText())) {
            layoutpassword.setErrorEnabled(true);
            layoutpassword.setError("Password is required");
            return;
        }
        progressDialog.setMessage("Wait a sec..");
        progressDialog.show();
        api_login(view);
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        progressDialog = new ProgressDialog(this);
        sessionManagement = new SessionManagement(getApplicationContext());
    }
    private void api_login(final View view){
        service = apiUtils.getAPIService();
        service.req_login(email.getText().toString(), password.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 200){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                            JSONObject jsonResults = new JSONObject(response.body().string());
                            if(jsonResults.getString("status").toLowerCase().equals("true")){
                                token = jsonResults.getString("jwt");
                                progressDialog.dismiss();
                                dialog = new Dialog(context);
                                dialog.setCancelable(false);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.setContentView(R.layout.custom_dialog);
                                LinearLayout bg = (LinearLayout) dialog.findViewById(R.id.customdialog_lnBg);
                                TextView status = (TextView) dialog.findViewById(R.id.customdialog_tvStatus);
                                TextView detail = (TextView) dialog.findViewById(R.id.customdialog_tvDetail);
                                ImageView imageView = (ImageView) dialog.findViewById(R.id.customdialog_img);
                                Button button = (Button) dialog.findViewById(R.id.customdialog_btnok);
                                bg.setBackgroundResource(R.color.green7);
                                status.setText("Welcome back!");
                                detail.setText("Hello there, check out our new dresses");
                                imageView.setImageResource(R.drawable.emoji_success1);
                                button.setBackgroundResource(R.drawable.button1_green);
                                button.setText("Ok");
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        sessionManagement.createLoginSession(token);
                                        MainActivity.mainactivity.finish();
                                        presentActivity(view);
                                    }
                                });
                                dialog.show();
                            }else if (jsonResults.getString("status").toLowerCase().equals("false")){
                                String message = jsonResults.getString("message");
                                progressDialog.dismiss();
                                initDialog(message, 0);
                            }else {
                                Snackbar.make(container, "FUCKING ERROR IS THIS ?", Snackbar.LENGTH_LONG).show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                        else{
                        Snackbar.make(container, "Invalid Email or Password", Snackbar.LENGTH_LONG).show();
                    }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        progressDialog.dismiss();
                        initDialog(t.getMessage(), 3);
                    }
                });
    }
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }
    private void initDialog(String message, int stats){
        dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.custom_dialog);
        LinearLayout bg = (LinearLayout) dialog.findViewById(R.id.customdialog_lnBg);
        TextView status = (TextView) dialog.findViewById(R.id.customdialog_tvStatus);
        TextView detail = (TextView) dialog.findViewById(R.id.customdialog_tvDetail);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.customdialog_img);
        Button button = (Button) dialog.findViewById(R.id.customdialog_btnok);
        if(stats == 1){
            status.setText("Registered Success!");
            detail.setText(message);
            bg.setBackgroundResource(R.color.green7);
            imageView.setImageResource(R.drawable.emoji_success);
            button.setBackgroundResource(R.drawable.button1_green);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    startActivity(new Intent(context, Login.class));
                    finish();
                }
            });
            dialog.show();
        }
        else if(stats == 0){
            status.setText("Oops!");
            detail.setText(message);
            bg.setBackgroundResource(R.color.red7);
            imageView.setImageResource(R.drawable.emoji_oops);
            button.setBackgroundResource(R.drawable.button1_red);
            button.setText("Try Again");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else if (stats == 3){
            status.setText("Uh Oh!");
            bg.setBackgroundResource(R.color.red7);
            detail.setText("There is a problem with internet connection or the server");
            imageView.setImageResource(R.drawable.emoji_cry);
            button.setBackgroundResource(R.drawable.button1_red);
            button.setText("Try Again");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}
