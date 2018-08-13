package com.example.silverbox.chatapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.silverbox.chatapplication.Beans.PersonBean;
import com.example.silverbox.chatapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener,
View.OnTouchListener{
    private EditText fnameET, lnameET, contactET,emailET, passwordET, cpassET;
    private Button resetBT, loginBT, signupBT;
    private TextView loginTV;
    private String fname, lname, email, password, cpass, contact;
    private LinearLayout signupLinear;
    private PersonBean bean;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        preferences = getSharedPreferences("MyDatabase",MODE_PRIVATE);
        if(preferences.getBoolean("TabbedHost",false)!=true)
        {
            Intent it = new Intent(getBaseContext(),TabbedHostIntial.class);
            startActivity(it);
            finish();
        }

        signupLinear = findViewById(R.id.signupLinear);
        fnameET= findViewById(R.id.fnameET);
        lnameET=findViewById(R.id.lnameET);
        emailET=findViewById(R.id.emailET);
        contactET=findViewById(R.id.numberET);
        passwordET=findViewById(R.id.passET);
        cpassET=findViewById(R.id.cpassET);
        resetBT= findViewById(R.id.ResetBT);
        loginBT=findViewById(R.id.loginBT);
        signupBT=findViewById(R.id.SignUpBT);
        loginTV=findViewById(R.id.loginTV);
        loginBT.setOnClickListener(this);
        signupBT.setOnClickListener(this);
        loginTV.setOnTouchListener(this);
        resetBT.setOnClickListener(this);

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
            case R.id.loginBT :
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent it = new Intent(getBaseContext(),LoginActivity.class);
                        startActivity(it);
                        finish();
                    }
                }, 1000);
                break;

            case R.id.SignUpBT:
                fname =fnameET.getText().toString();
                lname= lnameET.getText().toString();
                contact=contactET.getText().toString();
                email= emailET.getText().toString();
                password=passwordET.getText().toString();
                cpass=cpassET.getText().toString();
                if(fnameET.getText().toString().isEmpty() || contactET.getText().toString().isEmpty() || emailET.getText().toString().isEmpty()|| passwordET.getText().toString().isEmpty()||cpassET.getText().toString().isEmpty()) {
                    if (fnameET.getText().toString().isEmpty()) {
                        fnameET.setError("First Name Required");
                        lnameET.setError("Optional");
                    }
                    if (emailET.getText().toString().isEmpty()) {
                        emailET.setError("Email Required");
                    }
                    if (contactET.getText().toString().isEmpty()) {
                        contactET.setError("Contact Number Required");
                    }
                    if (passwordET.getText().toString().isEmpty()) {
                        passwordET.setError("Password Required");
                    }
                    if (cpassET.getText().toString().isEmpty()) {
                        cpassET.setError("Confirmation Password Required");
                    }
                }
                else {
                    if (!(password.equals(cpass)) || !(isValidPassword(password)) || password.length() < 8 || contactET.length() != 10 || !(isValidEmaillId(email))) {
                        if (!(password.equals(cpass))) {
                            passwordET.setText("");
                            cpassET.setText("");
                            Toast.makeText(SignUpActivity.this, "Passwords do not Match !!!\nTry Again.", Toast.LENGTH_SHORT).show();
                        }
                        if (!isValidPassword(password) || password.length() < 8) {
                            passwordET.setText("");
                            cpassET.setText("");
                            Toast.makeText(SignUpActivity.this, "Password is weak... \nPassword must contain minimum 8 characters.\nPassword must have atleast one Uppercase, Lowercase, Special-Character and Digit. \nTry Again !!! ", Toast.LENGTH_SHORT).show();
                        }
                        if (!isValidEmaillId(email)) {
                            emailET.setText("");
                            emailET.setError("Invalid Email Id...\nEnter a valid Email");
                        }
                        if (contactET.length() != 10) {
                            contactET.setError("Invalid Contact Length...\nContact must have minimum 10 characters.");
                        }
                    } else {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(getBaseContext(), "Authntication Successful.", Toast.LENGTH_SHORT).show();
                                bean = new PersonBean();
                                bean.setName((fname +" "+ lname));
                                bean.setPassword(password);
                                bean.setEmail(email);
                                bean.setContact(contact);

                                FirebaseDatabase d = FirebaseDatabase.getInstance();
                                DatabaseReference ref = d.getReference("user");
                                bean.setId(ref.push().getKey());
                                ref.child(bean.getId()).setValue(bean);

                                editor = getSharedPreferences("MyDatabase",MODE_PRIVATE).edit();
                                editor.putString("keyfirst",email);
                                editor.putString("keysecond",password);
                                editor.commit();
                                fnameET.setText("");
                                lnameET.setText("");
                                contactET.setText("");
                                passwordET.setText("");
                                emailET.setText("");
                                cpassET.setText("");
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
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getBaseContext(), "Authentication Failed ...\nEmail Already registered with another User ID ", Toast.LENGTH_SHORT).show();
                                passwordET.setText("");
                                cpassET.setText("");
                                emailET.setText("");
                            }
                        });
                    }
                }

                break;

            case R.id.ResetBT:
                fname =fnameET.getText().toString();
                lname= lnameET.getText().toString();
                contact= contactET.getText().toString();
                email= emailET.getText().toString();
                password=passwordET.getText().toString();
                cpass=cpassET.getText().toString();
                fnameET.setText("");
                lnameET.setText("");
                contactET.setText("");
                emailET.setText("");
                passwordET.setText("");
                cpassET.setText("");
                Snackbar snackbar= Snackbar.make(signupLinear,"Data Cleared... \nClick To Restore Data",Snackbar.LENGTH_SHORT).setAction("Restore", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar snackbar1 = Snackbar.make(signupLinear, "Data Restored...", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                        fnameET.setText(fname);
                        lnameET.setText(lname);
                        contactET.setText(contact);
                        passwordET.setText(password);
                        emailET.setText(email);
                        cpassET.setText(cpass);
                    }
                });
                snackbar.show();
                break;
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(v.getId())
        {
            case R.id.loginTV :
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent it = new Intent(getBaseContext(),LoginActivity.class);
                        startActivity(it);
                        finish();
                    }
                }, 1000);
                break;
        }
        return false;
    }
}

