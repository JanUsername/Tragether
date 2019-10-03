package com.example.tragether.model;

import androidx.annotation.NonNull;
import android.util.Log;
import com.example.tragether.EditProfileActivity;
import com.google.android.gms.tasks.*;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;
import java.util.*;

import io.grpc.okhttp.internal.Util;


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
    private final String TITLE = "title";
    private final String TAGS = "tags";
    private final String STARTTIME = "startTime";
    private final String ENDTIME = "endTime";
    private final String ORGANIZER = "organizer";


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

    public void getTravels(){
        final ArrayList<Travel> travels = new ArrayList<>();
        final Task<QuerySnapshot> trav = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .collection("travels").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<DocumentSnapshot> docs = task.getResult().getDocuments();
                            Log.d(TAG, "travels: " +docs.size());
                            Iterator it = docs.iterator();
                            while(it.hasNext()){
                                Map<String, Object> dbRes = new HashMap<String, Object>();
                                DocumentSnapshot temp = (DocumentSnapshot) it.next();
                                if(temp.exists()){
                                    dbRes = temp.getData();
                                    Iterator iterator = dbRes.entrySet().iterator();
                                    Travel t = new Travel();
                                    while(iterator.hasNext()){
                                        Map.Entry pair = (Map.Entry) iterator.next();
                                        if(pair.getKey().toString().equals(COUNTRY)){
                                            t.setCountry(pair.getValue().toString());
                                        }
                                        if(pair.getKey().toString().equals(TOWN)){
                                            t.setTown(pair.getValue().toString());
                                        }
                                        if(pair.getKey().toString().equals(START)){
                                            Timestamp ts = new Timestamp(((Timestamp) pair.getValue()).getSeconds(), ((Timestamp) pair.getValue()).getNanoseconds());
                                            t.setStart(ts.toDate());
                                        }
                                        if(pair.getKey().toString().equals(END)){
                                            Timestamp ts = new Timestamp(((Timestamp) pair.getValue()).getSeconds(), ((Timestamp) pair.getValue()).getNanoseconds());
                                            t.setEnd(ts.toDate());
                                        }

                                    }
                                    travels.add(t);
                                    Log.d(TAG, "travels Array: " +travels.size());

                                }

                            }

                        }else{
                            Log.d("getEvents", "get failed with ", task.getException());
                        }
                        Log.d(TAG, "getTravels: OUT "+travels.size());
                        Utility.userTravels = travels;
                    }


                });

    }

    public void saveTravel(Travel travel){

        Map<String, Object> docData = new HashMap<>();

        if(travel.getCountry() != ""){
            docData.put(COUNTRY, travel.getCountry());
        }
        if(travel.getTown() != "") {
            docData.put(TOWN, travel.getTown());
        }
        if(travel.getStart()!= null){
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
    public void saveEvent(Event event){

        Map<String, Object> docData = new HashMap<>();

        docData.put(TITLE, event.getTitle());
        docData.put(COUNTRY, event.getCountry());
        docData.put(TOWN, event.getTown());
        docData.put(START, new Timestamp(event.getStart()));
        docData.put(END, new Timestamp(event.getEnd()));
        docData.put(STARTTIME, new Timestamp(event.getStartTime()));
        docData.put(ENDTIME, new Timestamp(event.getEnd()));
        docData.put(TAGS, event.getTags());
        docData.put(ORGANIZER, User.getInstance().getEmail());

        //save the event in the user document
        db.collection("users").document(User.getInstance().getEmail())
                .collection("events").document(""+event.getTitle()+event.getStart()+event.getStartTime()).set(docData, SetOptions.merge())
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

        //save the event in the general events document
        db.collection("events").document(event.getCountry()).set(docData, SetOptions.merge())
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

    public void getUserEvents(){
        final ArrayList<Event> userE = new ArrayList<>();
        final Task<QuerySnapshot> events = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .collection("events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<DocumentSnapshot> docs = task.getResult().getDocuments();
                            Iterator it = docs.iterator();
                            while(it.hasNext()){
                                Map<String, Object> dbRes = new HashMap<String, Object>();
                                DocumentSnapshot temp = (DocumentSnapshot) it.next();
                                if(temp.exists()){
                                    dbRes = temp.getData();
                                    Log.d("eventsNum", ""+dbRes.size());
                                    Iterator iterator = dbRes.entrySet().iterator();
                                    Event t = new Event();
                                    while(iterator.hasNext()){
                                        Map.Entry pair = (Map.Entry) iterator.next();
                                        if(pair.getKey().toString().equals(COUNTRY)){
                                            t.setCountry(pair.getValue().toString());
                                        }
                                        if(pair.getKey().toString().equals(TOWN)){
                                            t.setTown(pair.getValue().toString());
                                        }
                                        if(pair.getKey().toString().equals(START)){
                                            Timestamp ts = new Timestamp(((Timestamp) pair.getValue()).getSeconds(), ((Timestamp) pair.getValue()).getNanoseconds());
                                            t.setStart(ts.toDate());
                                        }
                                        if(pair.getKey().toString().equals(END)){
                                            Timestamp ts = new Timestamp(((Timestamp) pair.getValue()).getSeconds(), ((Timestamp) pair.getValue()).getNanoseconds());
                                            t.setEnd(ts.toDate());
                                        }
                                        if(pair.getKey().toString().equals(STARTTIME)){
                                            Timestamp ts = new Timestamp(((Timestamp) pair.getValue()).getSeconds(), ((Timestamp) pair.getValue()).getNanoseconds());
                                            t.setStartTime(ts.toDate());

                                        }
                                        if(pair.getKey().toString().equals(ENDTIME)){
                                            Timestamp ts = new Timestamp(((Timestamp) pair.getValue()).getSeconds(), ((Timestamp) pair.getValue()).getNanoseconds());
                                            t.setEndTime(ts.toDate());

                                        }
                                        if(pair.getKey().toString().equals(ORGANIZER)){
                                            t.setOrganizer(pair.getValue().toString());
                                        }
                                        if(pair.getKey().toString().equals(TITLE)){
                                            t.setTitle(pair.getValue().toString());
                                        }
                                        if(pair.getKey().toString().equals(TAGS)){
                                            ArrayList<String> tags = new ArrayList<>();
                                            ArrayList<String> stuff = (ArrayList<String>) pair.getValue();
                                            while(!stuff.isEmpty()){
                                                tags.add(stuff.get(0));
                                                stuff.remove(0);
                                            }
                                            t.setTags(tags);
                                        }

                                    }
                                    userE.add(t);

                                }

                            }

                        }else{
                            Log.d("getEvents", "get failed with ", task.getException());
                        }

                    }

                });
        Log.d(TAG, "getUserEvents: OUT" + userE.size());
        Utility.userEvents = userE;


    }

    public void getSuggEvents() {
        final ArrayList<Event> suggested = new ArrayList<>();

        Log.d(TAG, " "+Utility.userTravels.size());
        for (Travel travel : Utility.userTravels) {

            final DocumentReference events = db.collection("events").document(travel.getCountry());
                    events.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            Map<String, Object> dbRes;
                            if (task.isSuccessful()) {
                                DocumentSnapshot doc = task.getResult();
                                    if (doc.exists()) {
                                        dbRes = doc.getData();
                                        Log.d("eventsNum", "" + dbRes.size());
                                        Iterator iterator = dbRes.entrySet().iterator();
                                        Event t = new Event();
                                        while (iterator.hasNext()) {
                                            Map.Entry pair = (Map.Entry) iterator.next();
                                            if (pair.getKey().toString().equals(COUNTRY)) {
                                                t.setCountry(pair.getValue().toString());
                                            }
                                            if (pair.getKey().toString().equals(TOWN)) {
                                                t.setTown(pair.getValue().toString());
                                            }
                                            if (pair.getKey().toString().equals(START)) {
                                                Timestamp ts = new Timestamp(((Timestamp) pair.getValue()).getSeconds(), ((Timestamp) pair.getValue()).getNanoseconds());
                                                t.setStart(ts.toDate());
                                            }
                                            if (pair.getKey().toString().equals(END)) {
                                                Timestamp ts = new Timestamp(((Timestamp) pair.getValue()).getSeconds(), ((Timestamp) pair.getValue()).getNanoseconds());
                                                t.setEnd(ts.toDate());
                                            }
                                            if (pair.getKey().toString().equals(STARTTIME)) {
                                                Timestamp ts = new Timestamp(((Timestamp) pair.getValue()).getSeconds(), ((Timestamp) pair.getValue()).getNanoseconds());
                                                t.setStartTime(ts.toDate());

                                            }
                                            if (pair.getKey().toString().equals(ENDTIME)) {
                                                Timestamp ts = new Timestamp(((Timestamp) pair.getValue()).getSeconds(), ((Timestamp) pair.getValue()).getNanoseconds());
                                                t.setEndTime(ts.toDate());

                                            }
                                            if (pair.getKey().toString().equals(ORGANIZER)) {
                                                t.setOrganizer(pair.getValue().toString());
                                            }
                                            if (pair.getKey().toString().equals(TITLE)) {
                                                t.setTitle(pair.getValue().toString());
                                            }
                                            if (pair.getKey().toString().equals(TAGS)) {
                                                ArrayList<String> tags = new ArrayList<>();
                                                ArrayList<String> stuff = (ArrayList<String>) pair.getValue();
                                                while (!stuff.isEmpty()) {
                                                    tags.add(stuff.get(0));
                                                    stuff.remove(0);
                                                }
                                                t.setTags(tags);
                                            }

                                        }

                                        if(!(t.getOrganizer().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))){
                                            suggested.add(t);
                                            Log.d(TAG, "onCheck: " + suggested.size());
                                        }

                                    }

                            } else {
                                Log.d("getEvents", "get failed with ", task.getException());
                            }
                            Utility.suggestedEv = suggested;
                            Log.d(TAG, "onComplete: "+ Utility.suggestedEv.size());

                        }

                    });
        }


    }

    public void getThread(String id){
        final ArrayList<Message> thread = new ArrayList<>();
        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .collection("travels").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<DocumentSnapshot> docs = task.getResult().getDocuments();
                            Log.d(TAG, "travels: " +docs.size());
                            Iterator it = docs.iterator();
                            while(it.hasNext()){
                                Map<String, Object> dbRes = new HashMap<String, Object>();
                                DocumentSnapshot temp = (DocumentSnapshot) it.next();
                                if(temp.exists()){
                                    dbRes = temp.getData();
                                    Iterator iterator = dbRes.entrySet().iterator();
                                    Message t = new Message();
                                    while(iterator.hasNext()){
                                        Map.Entry pair = (Map.Entry) iterator.next();
                                        if(pair.getKey().toString().equals("sender")){
                                            t.setSender(pair.getValue().toString());
                                        }
                                        if(pair.getKey().toString().equals("receiver")){
                                            t.setReceiver(pair.getValue().toString());
                                        }
                                        if(pair.getKey().toString().equals("msg")){
                                            t.setMsg(pair.getValue().toString());
                                        }


                                    }
                                    thread.add(t);
                                    Log.d(TAG, "chats Array: " +thread.size());

                                }

                            }

                        }else{
                            Log.d("getThread", "get failed with ", task.getException());
                        }
                        Log.d(TAG, "getThread: OUT "+thread.size());

                    }


                });
    }



}