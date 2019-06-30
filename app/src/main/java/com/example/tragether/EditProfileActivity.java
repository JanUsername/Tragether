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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tragether.model.FirebaseUtility;
import com.example.tragether.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class EditProfileActivity extends AppCompatActivity {

    TextView username;
    Spinner countries;
    ArrayList<String> countriesArray = new ArrayList<String>();
    EditText description;
    AlertDialog.Builder builder;
    boolean[] checked;
    User appUser = User.getUserInstance();
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
        fbu = FirebaseUtility.getInstance();



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

        intBtn = (FloatingActionButton)findViewById(R.id.btnInterests);


        interestsPos = new ArrayList<>();
        Log.d("getInterests", interests.toString() + " dc 2");
        Log.d("dio cane", String.valueOf(interests.size()));

        checked = new boolean[interests.size()];
        final CharSequence[] cs = interests.toArray(new CharSequence[interests.size()]);


        intBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  AlertDialog.Builder mBuilder = new AlertDialog.Builder(EditProfileActivity.this);
                  mBuilder.setTitle("YOUR INTERESTS");

                  mBuilder.setMultiChoiceItems(cs, checked, new DialogInterface.OnMultiChoiceClickListener() {
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


        description = (EditText)findViewById(R.id.description);
        description.setText(appUser.getDescription());



    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop(){
        super.onStop();
        //se esco e ho apportato modifiche non salvate, avviso e richiedo come procedere
    }
}
