package com.acv.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.acv.finalproject.MainViewActivity;

public class AboutActivity extends AppCompatActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        img =(ImageView) findViewById(R.id.ivWaltones);
        img.setBackgroundResource(R.drawable.waltones);
        AnimationDrawable frameAnimation = (AnimationDrawable)
                img.getBackground();
        frameAnimation.start();
    }

    public void returnMainMenu(View view) {
        this.finish();
        startActivity(new Intent(this, MainViewActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_logout) {
            logout();
        } else if (id == R.id.action_return) {
            this.finish();
            startActivity(new Intent(this, MainViewActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        SharedPreferences preferences = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);
        SharedPreferences.Editor e = preferences.edit();
        e.remove("userID");
        e.remove("continueConnected");
        e.commit();
        startActivity(new Intent(AboutActivity.this, LoginActivity.class));
        AboutActivity.this.finish();
    }
}
