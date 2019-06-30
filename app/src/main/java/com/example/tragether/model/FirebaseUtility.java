package com.example.tragether.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import android.util.Log;

import com.example.tragether.EditProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FirebaseUtility {

    FirebaseFirestore db;
    private ArrayList<String> interestsList = new ArrayList<>();

    private static FirebaseUtility fireBaseUtilityInstance = null;

    private FirebaseUtility(){
        db = FirebaseFirestore.getInstance();

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
                                System.out.println(pair.getKey() + " = " + pair.getValue());
                                ArrayList<String> stuff = (ArrayList<String>) pair.getValue();
                                while(!stuff.isEmpty()){
                                    result.add(stuff.get(0));
                                    stuff.remove(0);
                                }
                                //result.add(pair.getValue().toString());
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

    public LiveData<User> getUser(){
        User user = User.getInstance();
        DocumentReference interest = db.collection("users").document(user.getEmail());

        interest.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("getUser", "DocumentSnapshot data: " + document.getData());


                            } else {
                                Log.d("getUser", "No such document");
                            }
                        } else {
                            Log.d("getUser", "get failed with ", task.getException());
                        }
                    }
                });
        return user;
    }


}
