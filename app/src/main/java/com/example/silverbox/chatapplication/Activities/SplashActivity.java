package com.example.silverbox.chatapplication.Activities;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.silverbox.chatapplication.R;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    TextView textTV;
    TextToSpeech ttx;
    ImageView iconIV;
    Animation iconanim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        textTV= findViewById(R.id.TextTV);
        iconIV=findViewById(R.id.iconIV);
        iconanim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.splashanim);
        iconIV.startAnimation(iconanim);


        ttx = new TextToSpeech(getBaseContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                ttx.setLanguage(Locale.ENGLISH);
                ttx.setSpeechRate(-1);
                ttx.speak("ChatApp   Chat Like Never Before",TextToSpeech.QUEUE_FLUSH,null);

            }
        });





        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it = new Intent(getBaseContext(),SignUpActivity.class);
                startActivity(it);
                finish();

            }
        }, 5000);


    }


}
