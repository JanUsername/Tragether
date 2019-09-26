package com.example.tragether.model;

import androidx.annotation.NonNull;

import android.util.Log;

import com.example.tragether.EditProfileActivity;
import com.google.android.gms.tasks.*;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.*;
import java.util.*;


public class FirebaseUtility {

    private static FirebaseFirestore db = null;
    private ArrayList<String> interestsList = new ArrayList<>();

    private static FirebaseUtility fireBaseUtilityInstance = null;

    private final String USERNAME = "username";
    private final String DESCRIPTION = "description";
    private final String BDAY = "birthday";
    private final String COUNTRY = "country";
    private final String INTERESTS = "interests";
    private final String TIMESTAMP = "timestamp";
    private final String TAG = "FirebaseUtility";
    private final String TOWN = "town";
    private final String START = "start";
    private final String END = "end";

    private FirebaseUtility(){
       db =  FirebaseFirestore.getInstance();
    }

    public static FirebaseUtility getInstance(){
        if(fireBaseUtilityInstance == null){
            fireBaseUtilityInstance = new FirebaseUtility();
        }
        return fireBaseUtilityInstance;
    }


    public void getInterests(){

    DocumentReference interest = db.collection("utility").document("interests");

    interest.get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    ArrayList<String> result = new ArrayList<>();
                    Map<String, Object> dbRes;
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("getInterests", "DocumentSnapshot data: " + document.getData());
                            dbRes = document.getData();
                            Iterator it = dbRes.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry pair = (Map.Entry)it.next();
                                Log.d("getInterests", "" + pair.getKey() + " = " + pair.getValue());
                                ArrayList<String> stuff = (ArrayList<String>) pair.getValue();
                                while(!stuff.isEmpty()){
                                    result.add(stuff.get(0));
                                    stuff.remove(0);
                                }
                                Log.d("getInterests", result.toString());
                                it.remove(); // avoids a ConcurrentModificationException
                                EditProfileActivity.interests = result;
                                Log.d("getInterests", EditProfileActivity.interests.toString() + " dc");

                            }

                        } else {
                            Log.d("getInterests", "No such document");
                        }
                    } else {
                        Log.d("getInterests", "get failed with ", task.getException());
                    }
                }
            });

}

    public void getUser(){

    DocumentReference user = db.collection("users").document(User.getInstance().getEmail());

    user.get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    ArrayList<String> result = new ArrayList<>();
                    Map<String, Object> dbRes;
                    if (task.isSuccessful()) {
                        Log.d("getUser", "onComplete: DONE");
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("getUser", "DocumentSnapshot data: " + document.getData());
                            dbRes = document.getData();
                            Iterator it = dbRes.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry pair = (Map.Entry)it.next();
                                Log.d("getUser", "onComplete: "+ pair.getKey() + " = " + pair.getValue());
                                if(pair.getKey().toString().equals(USERNAME)){
                                    Log.d("getUser", "onComplete: in username");
                                    User.getInstance().setUsername(pair.getValue().toString());
                                }else if(pair.getKey().toString().equals(BDAY)){
                                    Timestamp ts = new Timestamp(((Timestamp)pair.getValue()).getSeconds(), ((Timestamp)pair.getValue()).getNanoseconds());
                                    Log.d("getUser", "onComplete: in birthday " + ts.toDate().toString());
                                    User.getInstance().setBirthday(ts.toDate());
                                }else if(pair.getKey().toString().equals(TIMESTAMP)){
                                    Timestamp ts = new Timestamp(((Timestamp)pair.getValue()).getSeconds(), ((Timestamp)pair.getValue()).getNanoseconds());
                                    Log.d("getUser", "onComplete: in timestamp " + ts.toDate().toString());
                                    User.getInstance().setTimestamp(ts.toDate());
                                }else if(pair.getKey().toString().equals(DESCRIPTION)){
                                    Log.d("getUser", "onComplete: in description");
                                    User.getInstance().setDescription(pair.getValue().toString());
                                }else if(pair.getKey().toString().equals(COUNTRY)){
                                    Log.d("getUser", "onComplete: in country");
                                    User.getInstance().setCountry(pair.getValue().toString());
                                }else if(pair.getKey().toString().equals(INTERESTS)){
                                    Log.d("getUser", "onComplete: in interests");
                                    ArrayList<String> stuff = (ArrayList<String>) pair.getValue();
                                    while(!stuff.isEmpty()){
                                        result.add(stuff.get(0));
                                        stuff.remove(0);
                                    }
                                    User.getInstance().setInterests(result);
                                }
                            }
                        } else {
                            Log.d("getUser", "No such document");
                            User.getInstance().resetUser();
                        }
                    } else {
                        Log.d("getUser", "get failed with ", task.getException());
                    }
                }
            });
    }

    public void getTimestamp(){

        DocumentReference ts = db.collection("users").document(User.getInstance().getEmail());
        ts.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> dbRes;
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        dbRes = document.getData();
                        Iterator it = dbRes.entrySet().iterator();
                        while(it.hasNext()) {
                            Map.Entry pair = (Map.Entry) it.next();
                            if (pair.getKey().toString().equals(TIMESTAMP)) {
                                Timestamp ts = new Timestamp(((Timestamp) pair.getValue()).getSeconds(), ((Timestamp) pair.getValue()).getNanoseconds());
                                Log.d("getUser", "onComplete: in timestamp " + ts.toDate().toString());
                                Date tempTs = ts.toDate();

                                Utility.tempCloud = tempTs;
                                Log.d("timestampBuildUser", "onComplete: " + tempTs);
                            }
                        }
                    } else {
                        Log.d("getInterests", "No such document");
                    }
                } else {
                    Log.d("getInterests", "get failed with ", task.getException());
                }
            }
        });

    }

    public void saveUser(User user){

        Map<String, Object> docData = new HashMap<>();

        if(user.getUsername() != null){
            docData.put(USERNAME, user.getUsername());
        }

        if(user.getBirthday() != null){
            docData.put(BDAY, new Timestamp(user.getBirthday()));
        }

        if(user.getCountry() != null) {
            docData.put(COUNTRY, user.getCountry());
        }

        if(user.getDescription() != null) {
            docData.put(DESCRIPTION, user.getDescription());
        }

        if(!user.getInterests().isEmpty() || user.getInterests() != null) {
            docData.put(INTERESTS, user.getInterests());
        }

        if(user.getTimestamp() != null) {
            docData.put(TIMESTAMP, new Timestamp(user.getTimestamp()));
        }


        db.collection("users").document(user.getEmail()).set(docData, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }

    public void saveTravel(Travel travel){

        Map<String, Object> docData = new HashMap<>();

        if(travel.getCountry() != null){
            docData.put(COUNTRY, travel.getCountry());
        }

        if(travel.getTown() != null) {
            docData.put(TOWN, travel.getTown());
        }

        if(travel.getStart() != null){
            docData.put(START, new Timestamp(travel.getStart()));
        }
        if(travel.getEnd() != null){
            docData.put(END, new Timestamp(travel.getEnd()));
        }


        db.collection("users").document(User.getInstance().getEmail())
                .collection("travels").document(travel.getStart().toString()).
                set(docData, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }

    //I can save the events in 2 different documents in the cloud!
    //In the event suggestion we should avoid the user's events
}