package com.example.tragether;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.tragether.chat.ChatActivity;
import com.example.tragether.model.User;
import com.google.firebase.auth.FirebaseAuth;

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
                startActivity(new Intent(getApplicationContext(), logged_activity.class));
                return true;
            case R.id.user_profile:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                return true;
            case R.id.chat:
                startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                return true;
            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                User.getInstance().resetUser();
                startActivity(new Intent(this, MainActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
