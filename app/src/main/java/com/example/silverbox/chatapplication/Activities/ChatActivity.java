package com.example.silverbox.chatapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.silverbox.chatapplication.Adapters.ChatAdapter;
import com.example.silverbox.chatapplication.Adapters.PersonAdapter;
import com.example.silverbox.chatapplication.Beans.ChatBean;
import com.example.silverbox.chatapplication.Beans.PersonBean;
import com.example.silverbox.chatapplication.ListClass.PersonList;
import com.example.silverbox.chatapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
  private ChatBean bean, bean1;
  private ListView chatLV;
  private EditText msgET;
  private List<ChatBean> list;
  private ImageButton sendmsgBT;
  private String recieverID, senderID, recname, msg;
  SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent it= getIntent();
        recieverID= it.getStringExtra("KeyUserEmail");
        recname=it.getStringExtra("KeyUserName");
        setTitle(recname);
        preferences = getSharedPreferences("MyDatabase",MODE_PRIVATE);
        senderID = preferences.getString("keyfirst","NA");
        msgET= findViewById(R.id.emsg);
        chatLV=findViewById(R.id.chatLV);
        sendmsgBT=findViewById(R.id.sendmsgbtn);

        list=new ArrayList<>();
        FirebaseDatabase f= FirebaseDatabase.getInstance();
        DatabaseReference ref1 = f.getReference("Chat");
        ref1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                bean1=dataSnapshot.getValue(ChatBean.class);
                //  Toast.makeText(getBaseContext(),""+bean.getId()+"\n"+bean.getMsg()+"\n"+bean.getSenderID()+"\n"+bean.getRecieverID(),Toast.LENGTH_SHORT).show();
                if ((senderID.equals(bean1.getSenderID()) && recieverID.equals(bean1.getRecieverID()))
                        || (senderID.equals(bean1.getRecieverID()) && recieverID.equals(bean1.getSenderID())))
                {
                    if (list.add(bean1)) {
                        chatLV.setAdapter(new ChatAdapter(getBaseContext(),list,senderID));
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        sendmsgBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg= msgET.getText().toString();
                if(msg.isEmpty())
                {
                    msgET.setError("Type a message");
                }
                else {

                    bean = new ChatBean();
                    bean.setRecieverID(recieverID);
                    bean.setSenderID(senderID);
                    bean.setMsg(msg);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference("Chat");
                    bean.setId(ref.push().getKey());
                    ref.child(bean.getId()).setValue(bean);
                    msgET.setText("");
                }


            }
        });

    }
}
