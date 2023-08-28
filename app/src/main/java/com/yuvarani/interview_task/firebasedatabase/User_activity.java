package com.yuvarani.interview_task.firebasedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yuvarani.interview_task.R;

import java.util.HashMap;
import java.util.Map;

public class User_activity extends AppCompatActivity {
    EditText usernameET, descriptionET;
    Button submitBTN;
    FirebaseDatabase database;
    String userId;
    DatabaseReference notesRef;
    String TAG = "User_activity";
String buttoname="",name="",description="",key="";

TextView tiltle_toolbar;
LinearLayout ll_bck;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist_main);
        tiltle_toolbar=findViewById(R.id.tv_title);
        tiltle_toolbar.setText("Firebase Realtime Database");
        ll_bck=findViewById(R.id.ll_bck);
        ll_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Initialize FirebaseApp
        FirebaseApp.initializeApp(User_activity.this);
        usernameET = (EditText) findViewById(R.id.usernameET);
        descriptionET = (EditText) findViewById(R.id.descriptionET);
        submitBTN = (Button) findViewById(R.id.loginbtn);
        // database = FirebaseDatabase.getInstance().getReference().getDatabase();
        Intent intent=getIntent();
        if(intent.getExtras()!=null) {
             buttoname = intent.getStringExtra("Button");
             name = intent.getStringExtra("name");
             description = intent.getStringExtra("description");
             key = intent.getStringExtra("key");
            Log.d("buttoname",buttoname);
            Log.d("name",name);
            Log.d("description",description);
            usernameET.setText(name);
            descriptionET.setText(description);
            submitBTN.setText("Update");
        }


        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userval = usernameET.getText().toString().trim().toUpperCase();
                String descriptionval = descriptionET.getText().toString().trim();
                Log.d(TAG, "userval+++" + userval.toString());
                Log.d(TAG, "descriptionval+++" + descriptionval.toString());

                notesRef = FirebaseDatabase.getInstance().getReference("notes");

                if(!userval.equals("")){
                if(!descriptionval.equals("")){
                    if(buttoname.equals("Edit")){

//                        //update firebase realtime database
//                        Map<String, Object> updateData = new HashMap<>();
//                        updateData.put("name", userval);
//                        updateData.put("description", descriptionval);
//                        updateData.put("key", key);
//                        notesRef.updateChildren(updateData);
                        // Create a new note.
                        UserNote note = new UserNote(userval, descriptionval,key);
                        note.setName(userval);
                        note.setDescription(descriptionval);
                        // Add the note to the database.
                        notesRef.push().setValue(note);

                        Toast.makeText(User_activity.this,"Data updated successfully",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),Userlist_CRUD_Activity.class);
                        startActivity(i);
                    }else{

                        // Create a new note.
                        UserNote note = new UserNote(userval, descriptionval,"");
                        note.setName(userval);
                        note.setDescription(descriptionval);
                        // Add the note to the database.
                        notesRef.push().setValue(note);
                        Toast.makeText(User_activity.this,"Data saved successfully",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),Userlist_CRUD_Activity.class);
                        startActivity(i);
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Enter your Description",Toast.LENGTH_SHORT).show();
                }  }else{
                    Toast.makeText(getApplicationContext(),"Enter your UserName",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
