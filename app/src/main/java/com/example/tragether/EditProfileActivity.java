package com.example.tragether;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tragether.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class EditProfileActivity extends AppCompatActivity {

    TextView username;
    Spinner countries;
    ArrayList<String> countriesArray = new ArrayList<String>();
    EditText description;
    AlertDialog.Builder builder;
    User appUser = User.getUserInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_edit_profile);

        username = (TextView)findViewById(R.id.username);
        username.setText(appUser.getUsername());

        countries = (Spinner) findViewById(R.id.countriesSpinner);
        Locale[] locale = Locale.getAvailableLocales();

        String country;
        for( Locale loc : locale ){
            country = loc.getDisplayCountry();
            if( country.length() > 0 && !countriesArray.contains(country) ){
                countriesArray.add( country );
            }
        }

        Collections.sort(countriesArray, String.CASE_INSENSITIVE_ORDER);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, countriesArray);
        countries.setAdapter(adapter);

        description = (EditText)findViewById(R.id.description);
        description.setText(appUser.getDescription());



    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //se esco e ho apportato modifiche non salvate, avviso e richiedo come procedere
    }
}
