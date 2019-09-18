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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


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
    ArrayList<String> toUpdate = new ArrayList<>();
    FloatingActionButton intBtn;
    FirebaseUtility fbu;
    Utility utility;
    private UserDao dao;
    private SupportDataBase sdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_edit_profile);
        appUser = User.getInstance();
        fbu = FirebaseUtility.getInstance();
        utility = new Utility(getApplicationContext());

        sdb = SupportDataBase.getInstance(getApplicationContext());
        dao = sdb.userDao();


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


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();

                //I save the data ONLY if there is internet connection, otherwise I could have
                //inconsistency problems
                if(utility.isNetworkAvailable(getApplicationContext())){
                    fbu.saveUser(appUser);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dao.delete(appUser);
                        dao.insert(appUser);
                    }
                }).start();

            }
        });

    }

    private void updateUser() {
        appUser.setUsername(username.getText().toString());
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            appUser.setBirthday(dateFormat.parse(bDay.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        appUser.setDescription(description.getText().toString());
        appUser.setCountry(countries.getSelectedItem().toString());
        appUser.setInterests(toUpdate);
        appUser.setTimestamp(Calendar.getInstance().getTime());

    }

    @Override
    protected void onStart(){
        super.onStart();

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


                        for(int i = 0; i<checkedCopy.length; i++){
                            Log.d("checkedCopy", "onClick: " + checkedCopy[i]);
                            if(checkedCopy[i]){
                                if(!toUpdate.contains(interests.get(i))) {
                                    toUpdate.add(interests.get(i));
                                }
                            }
                        }

                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }

        });
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
