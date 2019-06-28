package com.example.tragether.model;

import androidx.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FirebaseUtility {

    FirebaseFirestore db;

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


    public ArrayList<String> getInterests(){

        DocumentReference interest = db.collection("utility").document("interests");
        ArrayList<String> result = new ArrayList<>();


        interest.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("getInterests", "DocumentSnapshot data: " + document.getData());

                            } else {
                                Log.d("getInterests", "No such document");
                            }
                        } else {
                            Log.d("getInterests", "get failed with ", task.getException());
                        }
                    }
                });
        return result;

    }


}
