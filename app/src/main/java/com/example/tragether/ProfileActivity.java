package com.example.tragether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class ProfileActivity  extends  MenuHandler {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_profile);

        imageView = findViewById(R.id.imageView1);
        int imageRes = getResources().getIdentifier("@drawable/cat", null, this.getPackageName());
        imageView.setImageResource(imageRes);
       // getSupportActionBar().hide();
       // Toolbar toolbar =  findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(ProfileActivity.this, logged_activity.class));
    }



    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(this, logged_activity.class));
                return true;
            case R.id.user_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            case R.id.create_event:
                // do what you want here
                return true;
            case R.id.chat:
                // do what you want here
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */

}
