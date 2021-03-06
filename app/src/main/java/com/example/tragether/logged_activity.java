package com.example.tragether;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tragether.model.*;
import com.example.tragether.database.*;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class logged_activity extends  MenuHandler {

    TextView welcome;
    TextView feedTxt;
    User appUser;
    FirebaseUtility fbu;
    SupportDataBase sdb;
    UserDao dao;
    Utility utility;
    RecyclerView recyclerView;
    EventDetailAdapter mAdapter;
    ArrayList<Event> eventDetails;
    int eventNum;

    /*
    In this activity we will show a recycle view for the suggested events
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appUser = User.getInstance();
        fbu = FirebaseUtility.getInstance();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        utility = new Utility(getApplicationContext());
        sdb = SupportDataBase.getInstance(getApplicationContext());
        dao = sdb.userDao();
        appUser.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        utility.userCreation(getApplicationContext());

        fbu.getInterests();

        setContentView(R.layout.activity_logged_activity);

        welcome = findViewById(R.id.welcome);
        feedTxt = findViewById(R.id.feedTxt);

        if(utility.isSEvent) {

            recyclerView = findViewById(R.id.recycler_view_events);
            eventDetails = new ArrayList<>();

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            mAdapter = new EventDetailAdapter(Utility.suggestedEv);

            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            recyclerView.addOnItemTouchListener(new RecyclerItemListener(getApplicationContext(),
                    recyclerView, new RecyclerItemListener.RecyclerTouchListener() {
                @Override
                public void onClickItem(View v, int position) {
                    eventNum = position;
                    registerForContextMenu(v);
                }

                @Override
                public void onLongClickItem(View v, int position) {
                    eventNum = position;
                    registerForContextMenu(v);
                }
            }));

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);

        getMenuInflater().inflate(R.menu.sugg_events_menu, contextMenu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.tags:
                ArrayList<String> temp = Utility.suggestedEv.get(eventNum).getTags();
                String res = "";
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(logged_activity.this);
                mBuilder.setTitle("EVENT TAGS");
                for (int i = 0; i < temp.size(); i++) {
                    res = res + "\n" + temp.get(i);
                }
                mBuilder.setMessage(res);
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

}
