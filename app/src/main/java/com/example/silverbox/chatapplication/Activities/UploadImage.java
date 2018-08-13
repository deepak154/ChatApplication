package com.example.silverbox.chatapplication.Activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.silverbox.chatapplication.R;

import java.util.Calendar;

public class UploadImage extends AppCompatActivity {
    private DatePicker datepickDP;
    private int year, month, day;
    private final int dialogid =100;
    private static Uri selectedImage;
    final static int Result_Load_Image=1;
    private EditText dialogET;
    private CardView cardmale, cardfemale, cardothers;
    private ImageView maleIV, femaleIV, othersIV;
    private RadioGroup genderRG;
    private RadioButton maleRB, femaleRB, othersRB;
    private Button UploadImageBT, skipBT;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==Result_Load_Image && resultCode == RESULT_OK && data!=null) {
            selectedImage= data.getData();
            if(maleRB.isChecked()) {
                maleIV.setImageURI(selectedImage);
                femaleIV.setImageResource(R.drawable.female);
                othersIV.setImageResource(R.drawable.others);

            }
            else if(femaleRB.isChecked())
            {
                femaleIV.setImageURI(selectedImage);
                maleIV.setImageResource(R.drawable.male);
                othersIV.setImageResource(R.drawable.others);
            }
            else if(othersRB.isChecked())
            {
                othersIV.setImageURI(selectedImage);
                femaleIV.setImageResource(R.drawable.female);
                maleIV.setImageResource(R.drawable.male);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploaduserimage);
        dialogET=   findViewById(R.id.dialogET);
        datepickDP= findViewById(R.id.datepickDP);
        cardmale= findViewById(R.id.cardmale);
        cardfemale= findViewById(R.id.cardfemale);
        cardothers= findViewById(R.id.cardothers);
        maleIV= findViewById(R.id.maleIV);
        femaleIV=findViewById(R.id.femaleIV);
        othersIV=findViewById(R.id.otherIV);
        genderRG=findViewById(R.id.genderRG);
        maleRB=findViewById(R.id.maleRB);
        femaleRB=findViewById(R.id.femaleRB);
        othersRB=findViewById(R.id.otherRB);
        UploadImageBT= findViewById(R.id.uploadBT);
        skipBT=findViewById(R.id.skipBT);
        setCurrentDate();
        selectedImage=null;

        genderRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {                  case R.id.maleRB:
                                   cardmale.setVisibility(View.VISIBLE);
                                   cardfemale.setVisibility(View.VISIBLE);
                                   femaleIV.setImageResource(R.drawable.female);
                                   othersIV.setImageResource(R.drawable.others);
                                   cardothers.setVisibility(View.INVISIBLE);
                                   break;
                                   case R.id.femaleRB:
                                       cardmale.setVisibility(View.VISIBLE);
                                       cardfemale.setVisibility(View.VISIBLE);
                                       maleIV.setImageResource(R.drawable.male);
                                       othersIV.setImageResource(R.drawable.others);
                                       cardothers.setVisibility(View.INVISIBLE);
                                   break;
                                   case R.id.otherRB:
                                       cardmale.setVisibility(View.INVISIBLE);
                                       cardfemale.setVisibility(View.INVISIBLE);
                                       cardothers.setVisibility(View.VISIBLE);
                                       femaleIV.setImageResource(R.drawable.female);
                                       maleIV.setImageResource(R.drawable.male);
                                   break;

                }
            }
        });

        UploadImageBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If not permission present
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},10);
                    }
                    else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        //galleryIntent.setType("img/*"); //only takes images data type
                        startActivityForResult(galleryIntent,Result_Load_Image);
                    }
                }

            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id)
        {
            case dialogid: return  new DatePickerDialog(UploadImage.this,dateListener,year,month,day);

        }
        return null;
    }

    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            UploadImage.this.year=year;
            UploadImage.this.month=monthOfYear;
            UploadImage.this.day=dayOfMonth;
            datepickDP.init(year, month, day, null);
            dialogET.setText(day + "/" + (month+1)+ "/" + year);
        }
    };


    private void setCurrentDate() {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        day =cal.get(Calendar.DAY_OF_MONTH);
        month =cal.get(Calendar.MONTH);
        datepickDP.init(year, month, day, null);
        dialogET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogid);

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==10)
        {
            if(permissions[0]== Manifest.permission.READ_EXTERNAL_STORAGE  && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getBaseContext(),"Thank You !",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getBaseContext(),"Permission Not Granted !",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
