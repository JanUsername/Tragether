package com.example.tragether;

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
import android.widget.Toast;

import com.example.tragether.database.*;
import com.example.tragether.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TravelActivity extends AppCompatActivity {

    EditText start;
    EditText end;
    Spinner countries;
    EditText city;
    ArrayList<String> countriesArray = new ArrayList<String>();
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

        countries = findViewById(R.id.countriesTSpinner);
        Locale[] locale = Locale.getAvailableLocales();
        String country;
        for( Locale loc : locale ){
            country = loc.getDisplayCountry();
            if( country.length() > 0 && !countriesArray.contains(country) ){
                countriesArray.add( country );
            }
        }

        Collections.sort(countriesArray, String.CASE_INSENSITIVE_ORDER);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, countriesArray);
        countries.setAdapter(adapter);

        /*
        for the edit part!
        if(appUser.getCountry() != null){

            int index = adapter.getPosition(countryName);
            countries.setSelection(index);
        }
        */
        city = findViewById(R.id.city);

        start = findViewById(R.id.sTDay);

        /*
        for the edit part
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if(travel.getStart() == null){
            start.setText("");
        }else{
            Date date = appUser.getBirthday();
            start.setText(dateFormat.format(date));
        }

        */

        end = findViewById(R.id.eTDay);

        /*
        for the edit part
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if(travel.getStart() == null){
            start.setText("");
        }else{
            Date date = appUser.getBirthday();
            start.setText(dateFormat.format(date));
        }

        */

        save = findViewById(R.id.btnTSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Travel toSave = createTravel();
                if(toSave != null) {
                    //I save the data ONLY if there is internet connection, otherwise I could have
                    //inconsistency problems
                    if (utility.isNetworkAvailable(getApplicationContext())) {
                        fbu.saveTravel(toSave);

                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            travelDao.insert(toSave);
                        }
                    }).start();
                    utility.userTravels.add(toSave);
                    Toast.makeText(getApplicationContext(), "Operation successful", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Saving operation failed, every field should be filled!", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private Travel createTravel(){

        Travel temp = new Travel();

        if(countries.getSelectedItem() != null){
            temp.setCountry(countries.getSelectedItem().toString());
        }else{
            return null;
        }
        if(city.getText().toString().trim().length() > 0){
            temp.setTown(city.getText().toString());
        }else{
            return null;
        }
        if(start.getText().toString().trim().length() > 0){
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date t = dateFormat.parse(start.getText().toString());
                Log.d("createTravel", "date value: "+ t.toString());
                if(t != null){
                    temp.setStart(t);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else{
            return null;

        }if(end.getText().toString().trim().length() > 0){
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date t = dateFormat.parse(end.getText().toString());
                if(t != null){
                    temp.setEnd(t);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else{
            return null;
        }


        return temp;
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
