package com.yuvarani.interview_task.firebasedatabase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yuvarani.interview_task.R;

import java.util.ArrayList;

public class Userlist_CRUD_Activity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private static ArrayList<UserNote> arrayList  = new ArrayList<>();
    private RecyclerView list;
    public static Activity Fa;
    TextView tiltle_toolbar;
    LinearLayout ll_bck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_crud_mainactivity);
        tiltle_toolbar=findViewById(R.id.tv_title);
        ll_bck=findViewById(R.id.ll_bck);
        tiltle_toolbar.setText("FRD LISTS");

        ll_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        arrayList.clear();
        Fa =this;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference("notes");
        getUsers();
        list = findViewById(R.id.list);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(),
                new LinearLayoutManager(this).getOrientation());
        list.addItemDecoration(dividerItemDecoration);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new ListAdapter(arrayList,this ));
//        btnCreate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(User_activity.this,SecondActivity.class);
//                String s;
//                s="Create";
//                i.putExtra("Button",s);
//                startActivity(i);
//                finish();
//            }
//        });
    }
    public void getUsers(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            String description,Name;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.exists()){
                    Log.d("dataSnapshot++",dataSnapshot.toString());
                 String key =(String) dataSnapshot.getKey();
                    Name =(String) dataSnapshot.child("name").getValue();
                    description =(String) dataSnapshot.child("description").getValue();
                    Log.d("key=+",key);

                    arrayList.add(new UserNote(description,Name,key));;
                }
                    list.setAdapter(new ListAdapter(arrayList,Userlist_CRUD_Activity.this));

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public static void cleanList(){
        arrayList.clear();
    }


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {
    private ArrayList<UserNote> arrayList;
    private Context context;
    private UserNote usernote;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public ListAdapter(ArrayList<UserNote> arrayList,Context  context) {
        this.arrayList = arrayList;
        this.context= context;
    }
    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_items,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        String name = arrayList.get(position).getName();
        String description = arrayList.get(position).getdescription();
        //String key = arrayList.get(position).getKey();
//        Log.d("name",name);
//        Log.d("description",description);
        holder.title_tv.setText(name);
        holder.description_tv.setText(description);
        holder.editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernote = arrayList.get(position);
                Intent i = new Intent(context, User_activity.class);
                i.putExtra("Button","Edit");
                i.putExtra("name",usernote.getName());
                i.putExtra("description",usernote.getdescription());
                i.putExtra("key",usernote.getKey());
                context.startActivity(i);
            }
        });
//        holder.readList_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                usernote = arrayList.get(position);
//                Intent i = new Intent(context, User_activity.class);
//                i.putExtra("Button","Read");
//                i.putExtra("name",usernote.getName());
//                i.putExtra("description",usernote.getdescription());
//                i.putExtra("number",usernote.getNumber());
//                context.startActivity(i);
//                MainActivity.Fa.finish();
//            }
//        });
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernote = arrayList.get(position);
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference =firebaseDatabase.getReference("notes");
                String temp =usernote.getKey();
                Log.d("temp+++",temp);

                databaseReference.child(temp).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                           // databaseReference.child(temp).removeValue();
                            Toast.makeText(context,"Data Removed",Toast.LENGTH_SHORT).show();
                            Intent intent=getIntent();
                            finish();
                            startActivity(intent);
                        }else {
                            Toast.makeText(context,"Failed to remove data",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
               // MainActivity.cleanList();
            }
        });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class ListHolder extends RecyclerView.ViewHolder{
        private TextView title_tv,description_tv;
      private   Button editbtn,deletebtn;
        public ListHolder(@NonNull View itemView ) {
            super(itemView);
            title_tv =(TextView) itemView.findViewById(R.id.title_tv);
            description_tv =(TextView) itemView.findViewById(R.id.description_tv);
            editbtn =(Button) itemView.findViewById(R.id.editbtn);
            deletebtn =(Button) itemView.findViewById(R.id.deletebtn);
           }
    }
}
}
