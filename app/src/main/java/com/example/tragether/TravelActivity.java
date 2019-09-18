package com.example.tragether;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tragether.database.*;
import com.example.tragether.model.*;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class TravelActivity extends AppCompatActivity {

    EditText start;
    EditText end;
    Spinner countries;
    Spinner cities;
    ArrayList<String> countriesArray = new ArrayList<String>();
    ArrayList<String> citiesArray = new ArrayList<String>();
    Button save;
    User appUser;
    FirebaseUtility fbu;
    Utility utility;
    private SupportDataBase sdb;
    private TravelDao travelDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_travel);
        appUser = User.getInstance();
        fbu = FirebaseUtility.getInstance();
        utility = new Utility(getApplicationContext());

        sdb = SupportDataBase.getInstance(getApplicationContext());
        travelDao = sdb.travelDao();

/*

        username = findViewById(R.id.username);
        bDay = findViewById(R.id.bDay);
        countries = findViewById(R.id.countriesSpinner);
        description = findViewById(R.id.description);
        save = findViewById(R.id.btnSave);

        username.setText(appUser.getUsername());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if(appUser.getBirthday() == null){
            bDay.setText("");
        }else{
            Date date = appUser.getBirthday();
            bDay.setText(dateFormat.format(date));
        }


        //Country spinner
        //add also "select a country" in order to know if something has been added??
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
        if(appUser.getCountry() != null){

            int index = adapter.getPosition(countryName);
            countries.setSelection(index);
        }



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //I save the data ONLY if there is internet connection, otherwise I could have
                //inconsistency problems
                if(utility.isNetworkAvailable(getApplicationContext())){
                    fbu.saveUser(appUser);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        travelDao.insert(appUser);
                    }
                }).start();

            }
        });*/

    }

    @Override
    protected void onStop(){
        super.onStop();
        //se esco e ho apportato modifiche non salvate, avviso e richiedo come procedere
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

}
