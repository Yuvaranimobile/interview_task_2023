package com.yuvarani.interview_task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yuvarani.interview_task.assessmenttask.DistancecalculationActivity;
import com.yuvarani.interview_task.firebasedatabase.User_activity;

public class TaskActivitity extends AppCompatActivity {
Button task1btn,task2bbtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_mainlayout);
        task1btn=findViewById(R.id.task1btn);
        task2bbtn=findViewById(R.id.task2btn);

        task1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), DistancecalculationActivity.class);
                startActivity(i);
            }
        });task2bbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), User_activity.class);
                startActivity(i);
            }
        });


    }
}
