package com.example.firebaserecyclerview;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addpost;
    private RecyclerView recyclerView;

    private UserAdapter adapter;
     List<User> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this); // Initialize Firebase
        setContentView(R.layout.activity_main);
        addpost = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();

      DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        // Set the adapter to the ListView
        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addAct = new Intent(MainActivity.this, addinfo.class);
                startActivity(addAct);
            }
        });


        // Set up RecyclerView
      //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
       /* Query query = databaseReference;
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(query, User.class)
                        .build();*/


        //databaseReference = FirebaseDatabase.getInstance().getReference("users/"+user.getUid());


        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot keySnapshot : userSnapshot.getChildren()) {
                        DataSnapshot firstNameSnapshot = keySnapshot.child("firstname");
                        DataSnapshot LastNameSnapshot = keySnapshot.child("lastname");
                        DataSnapshot agetSnapshot = keySnapshot.child("age");
                        DataSnapshot timeSnapshot=keySnapshot.child("time");
                        DataSnapshot timetampSnapshot=keySnapshot.child("timestamp");


                        String firstname = firstNameSnapshot.getValue(String.class);
                        String lastname = LastNameSnapshot.getValue(String.class);
                        String age = agetSnapshot.getValue(String.class);
                        Long timestampLong = timetampSnapshot.getValue(Long.class);
                        String date=timeSnapshot.getValue(String.class);

// Assuming you have the number of milliseconds stored in a variable called 'milliseconds'
                      //  long milliseconds = System.currentTimeMillis();

// Convert the milliseconds to date and time
                       // String dateTime = DateTimeUtil.convertMillisecondsToDateTime(milliseconds);

// Now 'dateTime' will hold the formatted date and time as per the specified format



                        // Create a new User object using the retrieved data
                       User user = new User(firstname, lastname, age,timestampLong,date);
                       //Log.e("time",date);
                        users.add(user);

                        //adapter = new UserAdapter(options);
                       // recyclerView.setAdapter(adapter);

                        //}



                    }


                }
                Log.d("firebase", "Data : " + users);
                // Use the 'firstNames' list with your RecyclerView adapter here

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new UserAdapter(users); // Replace 'String' with your data type
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("firebase", "Error getting data", databaseError.toException());
            }
        });


    }


}

