package com.example.tragether;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import com.example.tragether.model.FirebaseUtility;
import com.example.tragether.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class EditProfileActivity extends AppCompatActivity {

    TextView username;
    Spinner countries;
    ArrayList<String> countriesArray = new ArrayList<String>();
    EditText description;
    EditText bDay;
    Button save;
    boolean[] checked = new boolean[interests.size()];
    User appUser;
    public static ArrayList<String> interests ;
    ArrayList<Integer> interestsPos;
    FloatingActionButton intBtn;
    FirebaseUtility fbu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_edit_profile);
        appUser = User.getInstance();
        fbu = FirebaseUtility.getInstance();

        username = findViewById(R.id.username);
        bDay = findViewById(R.id.bDay);
        countries = findViewById(R.id.countriesSpinner);
        description = findViewById(R.id.description);
        save = findViewById(R.id.btnSave);

        username.setText(appUser.getUsername());
        if(appUser.getBirthday() == null){
            bDay.setText("");
        }else{
            bDay.setText(appUser.getBirthday().toString());
        }
        description.setText(appUser.getDescription());
        String countryName = appUser.getCountry();

        if(appUser.getInterests() != null){

            ArrayList<String> stuff = new ArrayList<>(appUser.getInterests());

            if(!stuff.isEmpty()){
                while(!stuff.isEmpty()){
                    int index = interests.indexOf(stuff.get(0));
                    stuff.remove(0);
                    checked[index] = true;
                }

            }
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

        //Interests checkbox
        intBtn = findViewById(R.id.btnInterests);
        interestsPos = new ArrayList<>();

        final boolean[] checkedCopy = checked;

        for(int i = 0; i<checked.length; i++){
            Log.d("checked", "" + checked[i]);
        }
        final CharSequence[] cs = interests.toArray(new CharSequence[interests.size()]);

        intBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  AlertDialog.Builder mBuilder = new AlertDialog.Builder(EditProfileActivity.this);
                  mBuilder.setTitle("YOUR INTERESTS");

                  mBuilder.setMultiChoiceItems(cs, checkedCopy, new DialogInterface.OnMultiChoiceClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                          //
                          if (isChecked) {
                              interestsPos.add(position);
                          } else {
                              interestsPos.remove((Integer.valueOf(position)));
                          }
                      }
                  });

                  AlertDialog mDialog = mBuilder.create();
                  mDialog.show();
              }

          });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
                fbu.saveUser(appUser);

            }
        });

    }

    private void updateUser() {
        appUser.setUsername(username.getText().toString());
        try {
            appUser.setBirthday(new SimpleDateFormat().parse(bDay.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        appUser.setDescription(description.getText().toString());
        appUser.setCountry(countries.getSelectedItem().toString());
        ArrayList<String> toUpdate = new ArrayList<>();
        for(int i = 0; i < interestsPos.size(); i ++){
            toUpdate.add(interests.get(interestsPos.get(i)));
        }

        appUser.setInterests(toUpdate);
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
