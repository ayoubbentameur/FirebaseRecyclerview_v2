package com.example.firebaserecyclerview;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    FloatingActionButton addpost;
    private RecyclerView recyclerView;

    private UserAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this); // Initialize Firebase
        setContentView(R.layout.activity_main);
        addpost = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        addpost.setOnClickListener(v -> {
            Intent addAct = new Intent(MainActivity.this, addinfo.class);
            startActivity(addAct);
        });

        setUpRecyclerView();


    }


    private void setUpRecyclerView(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
       // Query query = databaseReference.orderByChild("timestamp"); // Assuming "timestamp" is the field name
        Query query = databaseReference.child("users").orderByChild("timestamp").limitToLast(100); // Limiting to the last 100 records

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // ... (your existing data retrieval code)

                    for (DataSnapshot keySnapshot : userSnapshot.getChildren()) {
                        DataSnapshot firstNameSnapshot = keySnapshot.child("firstname");
                        DataSnapshot LastNameSnapshot = keySnapshot.child("lastname");
                        DataSnapshot agetSnapshot = keySnapshot.child("age");
                        DataSnapshot timetampSnapshot=keySnapshot.child("timestamp");
                        String firstname = firstNameSnapshot.getValue(String.class);
                        String lastname = LastNameSnapshot.getValue(String.class);
                        String age = agetSnapshot.getValue(String.class);
                        Long timestampLong = timetampSnapshot.getValue(Long.class);
                        long currentTimeMillis=System.currentTimeMillis();
                        long timeElapsedMillis = currentTimeMillis - timestampLong;
                        String dateTime=TimeElapsedCalculator.formatTimeElapsed(timeElapsedMillis,timestampLong);
                        User user = new User(firstname, lastname, age,timestampLong,dateTime);
                        users.add(user);
                    }


                }

                Collections.reverse(users); // Reverse the list to display the newest first
                if (adapter == null){
                    adapter=new UserAdapter(users);
                    recyclerView.setAdapter(adapter);
                }else {
                    adapter.updateData(users);
                    adapter.notifyDataSetChanged();
                }
                Log.d("firebase", "Data : " + users);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new UserAdapter(users);
                recyclerView.setAdapter(adapter);
                // ... (your existing adapter and RecyclerView setup code)
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase", "Error getting data", error.toException());

            }

            // ... (your existing onCancelled method)
        });

    /*    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot keySnapshot : userSnapshot.getChildren()) {
                        DataSnapshot firstNameSnapshot = keySnapshot.child("firstname");
                        DataSnapshot LastNameSnapshot = keySnapshot.child("lastname");
                        DataSnapshot agetSnapshot = keySnapshot.child("age");
                        DataSnapshot timetampSnapshot=keySnapshot.child("timestamp");
                        String firstname = firstNameSnapshot.getValue(String.class);
                        String lastname = LastNameSnapshot.getValue(String.class);
                        String age = agetSnapshot.getValue(String.class);
                        Long timestampLong = timetampSnapshot.getValue(Long.class);
                        long currentTimeMillis=System.currentTimeMillis();
                        long timeElapsedMillis = currentTimeMillis - timestampLong;
                        String dateTime=TimeElapsedCalculator.formatTimeElapsed(timeElapsedMillis,timestampLong);
                        User user = new User(firstname, lastname, age,timestampLong,dateTime);
                        users.add(user);
                    }
                }
                if (adapter == null){
                    adapter=new UserAdapter(users);
                    recyclerView.setAdapter(adapter);
                }else {
                    adapter.updateData(users);
                    adapter.notifyDataSetChanged();
                }
                Log.d("firebase", "Data : " + users);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new UserAdapter(users);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("firebase", "Error getting data", databaseError.toException());
            }
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpRecyclerView();
    }
}

