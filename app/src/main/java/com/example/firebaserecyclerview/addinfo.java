package com.example.firebaserecyclerview;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addinfo extends AppCompatActivity {
    Button post;
    private DatabaseReference mDatabase;
    EditText first_name, last_name, age;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
String uid = currentFirebaseUser.getUid();
// ...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addinfo);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        post = findViewById(R.id.button_post);
        first_name = findViewById(R.id.firstname_id);
        last_name = findViewById(R.id.lastname_id);
        age = findViewById(R.id.age_id);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("users");
                long currentTimeMillis = System.currentTimeMillis();
                //long milliseconds = System.currentTimeMillis();

// Convert the milliseconds to date and time
                String dateTime = DateTimeUtil.convertMillisecondsToDateTime(currentTimeMillis);

                // User user = new User(name, email);
                User user = new User(first_name.getText().toString(), last_name.getText().toString(), age.getText().toString(),currentTimeMillis,dateTime);

                databaseReference.child(uid).push().setValue(user).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "Failed to add info", e);
                        Toast.makeText(addinfo.this, "Failed to add info", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }

    public void writeNewUser(String userId, String name, String email) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("users");
       // User user = new User(name, email);
      //  User user = new User("Johdsfsdn", "Dodfdsfe");

        //databaseReference.push().setValue(user);

    }

}
