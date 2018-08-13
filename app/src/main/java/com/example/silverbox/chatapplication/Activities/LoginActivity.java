package com.example.silverbox.chatapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.silverbox.chatapplication.Beans.PersonBean;
import com.example.silverbox.chatapplication.ListClass.PersonList;
import com.example.silverbox.chatapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.silverbox.chatapplication.ListClass.PersonList.list;

public class LoginActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {
private EditText emailET, passwordET;
private Button loginBT;
private TextView forgotTV;
private PersonBean bean;
private String email, password;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailET= findViewById(R.id.emailET);
        passwordET= findViewById(R.id.passwordET);
        loginBT= findViewById(R.id.loginBT);
        forgotTV= findViewById(R.id.forgotTV);
        loginBT.setOnClickListener(this);
        forgotTV.setOnTouchListener(this);



    }
    //Password Validation Length
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
    private boolean isValidEmaillId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.loginBT:
                email = emailET.getText().toString();
                password = passwordET.getText().toString();
                if (email.isEmpty() || password.isEmpty() || !(isValidEmaillId(email))) {
                    if (email.isEmpty()) {
                        emailET.setError("Email Required");
                    }
                    if (password.isEmpty()) {
                        passwordET.setError("Password Required");
                    }
                    if(!(isValidEmaillId(email))) {
                        passwordET.setText("");
                        emailET.setError("Not Valid Email");
                    }
                 }
                else {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                editor = getSharedPreferences("MyDatabase",MODE_PRIVATE).edit();
                                editor.putString("keyfirst",email);
                                editor.putString("keysecond",password);
                                editor.commit();

                                Handler h = new Handler();
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent it = new Intent(getBaseContext(),HomeActivity.class);
                                        startActivity(it);
                                        finish();
                                    }
                                },1000);


                            }
                            else
                            {
                                Toast.makeText(getBaseContext(), "Invalid Email Id or Password....\nTry Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;

        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(v.getId())
        {
            case R.id.forgotTV:
                break;

        }
        return false;
    }
}
