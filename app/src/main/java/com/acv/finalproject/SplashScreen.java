package com.acv.finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.acv.finalproject.api.UserAPI;
import com.acv.finalproject.dao.UserDAO;
import com.acv.finalproject.model.User;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreen extends AppCompatActivity implements Callback<User> {

    private Animation animacao;
    private ImageView ivImagem;
    private final String urlUsers = "http://www.mocky.io/v2/58b9b1740f0000b614f09d2f/";
    private UserDAO userDAO;
    private User user;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ivImagem = (ImageView) findViewById(R.id.iv_animation);
// load the animation
        animacao =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.animacao);
        animacao.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivImagem.startAnimation(animacao);
        this.loadUsers();
        img =(ImageView) findViewById(R.id.ivLucao);
        img.setBackgroundResource(R.drawable.lucao);
//        Picasso.with(this).load(R.drawable.lucao).into(img);
//        Picasso.with(this).load(R.drawable.lucao).into(new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                img.setBackgroundResource(new BitmapDrawable(SplashScreen.this.getResources(), bitmap));
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        });
        AnimationDrawable frameAnimation = (AnimationDrawable)
                img.getBackground();
        frameAnimation.start();

    }

    private void loadUsers() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(urlUsers)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<User> call = userAPI.getUser();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        this.user = response.body();
        if (this.user != null) {
            this.userDAO = new UserDAO(this);
            this.userDAO.open();
            this.userDAO.insert(this.user);
            this.userDAO.close();
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
