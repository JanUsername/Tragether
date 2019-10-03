package com.example.tragether;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

import com.example.tragether.database.*;
import com.example.tragether.model.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EventActivity extends AppCompatActivity {

    EditText title;
    Spinner countries;
    ArrayList<String> countriesArray = new ArrayList<String>();
    EditText city;
    EditText startD;
    EditText startT;
    TimePickerDialog pickerS;
    EditText endD;
    EditText endT;
    TimePickerDialog pickerE;
    public static ArrayList<String> interests = EditProfileActivity.interests;
    boolean[] checked = new boolean[interests.size()];
    User appUser;
    ArrayList<Integer> interestsPos;
    ArrayList<String> toUpdate = new ArrayList<>();
    FloatingActionButton intBtn;
    Button save;

    FirebaseUtility fbu;
    Utility utility;
    SupportDataBase sdb;
    EventDao eventDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_event);
        appUser = User.getInstance();
        fbu = FirebaseUtility.getInstance();
        utility = new Utility(getApplicationContext());

        sdb = SupportDataBase.getInstance(getApplicationContext());
        eventDao = sdb.eventDao();

        title = findViewById(R.id.eventTitle);

        countries = findViewById(R.id.countriesESpinner);
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

        city = findViewById(R.id.cityE);

        startD = findViewById(R.id.eventStarts);
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

        startT = findViewById(R.id.eventStartTime);
        startT.setTag(startT.getKeyListener());
        startT.setKeyListener(null);
        startT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                pickerS = new TimePickerDialog(EventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                if(sMinute >=10){
                                    startT.setText(sHour + ":" + sMinute);
                                }else{
                                    startT.setText(sHour + ":0" + sMinute);
                                }

                            }
                        }, hour, minutes, false);
                pickerS.show();

            }
        });

        endD = findViewById(R.id.eventEnds);
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

        endT = findViewById(R.id.eventEndTime);
        endT.setTag(startT.getKeyListener());
        endT.setKeyListener(null);
        endT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                pickerE = new TimePickerDialog(EventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                if(sMinute >= 10){
                                    endT.setText(sHour + ":" + sMinute);
                                }else {
                                    endT.setText(sHour + ":0" + sMinute);
                                }
                            }
                        }, hour, minutes, false);
                pickerE.show();

            }
        });

        intBtn = findViewById(R.id.btnEInterests);
        interestsPos = new ArrayList<>();

        final boolean[] checkedCopy = checked;

        final CharSequence[] cs = EditProfileActivity.interests.toArray(new CharSequence[interests.size()]);

        intBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(EventActivity.this);
                mBuilder.setTitle("EVENT TAGS");

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

        save = findViewById(R.id.btnESave);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Event toSave = createEvent();

                if(toSave != null){

                    fbu.saveEvent(toSave);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            eventDao.insert(toSave);
                        }
                    }).start();
                    utility.userEvents.add(toSave);
                    Toast.makeText(getApplicationContext(), "Operation successful", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Saving operation failed, every field should be filled!", Toast.LENGTH_LONG).show();
                }



            }
        });

    }

    private Event createEvent(){
        Event temp = new Event();

        if(title.getText().toString() != ""){
            temp.setTitle(title.getText().toString());
        }else{
            return null;
        }

        if(countries.getSelectedItem() != null){
            temp.setCountry(countries.getSelectedItem().toString());
        }else{
            return null;
        }
        if(city.getText().toString() != ""){
            temp.setTown(city.getText().toString());
        }else{
            return null;
        }

        if(startD.getText().toString() != "" && startT.getText().toString() != ""){
            try {
                SimpleDateFormat dateFormatD = new SimpleDateFormat("dd/MM/yyyy");
                temp.setStart(dateFormatD.parse(startD.getText().toString()));

                SimpleDateFormat dateFormatT = new SimpleDateFormat("HH:mm");
                temp.setStartTime(dateFormatT.parse(startT.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            return null;
        }

        if(endD.getText().toString() != "" && endT.getText().toString() != ""){
            try {
                SimpleDateFormat dateFormatD = new SimpleDateFormat("dd/MM/yyyy");
                temp.setEnd(dateFormatD.parse(endD.getText().toString()));

                SimpleDateFormat dateFormatT = new SimpleDateFormat("HH:mm");
                temp.setEndTime(dateFormatT.parse(endT.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            return null;
        }


        if(toUpdate.size() != 0){
            temp.setTags(toUpdate);
        }else {
            return null;
        }

        return temp;
    }
}
