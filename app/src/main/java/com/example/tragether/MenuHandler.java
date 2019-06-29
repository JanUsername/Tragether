package com.example.tragether;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MenuHandler extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                startActivity(new Intent(this, Overview.class));
                return true;
            case R.id.chat:
                // do what you want here
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
