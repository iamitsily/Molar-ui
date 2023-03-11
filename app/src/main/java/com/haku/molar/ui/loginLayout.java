package com.haku.molar.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haku.molar.R;

public class loginLayout extends AppCompatActivity {
    CardView emailCV, codeCV,passCV;
    ConstraintLayout clBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);

        emailCV = (CardView) findViewById(R.id.loginLayoutCardViewPassword);
        codeCV = (CardView) findViewById(R.id.loginLayoutCardViewPassword2);
        passCV = (CardView) findViewById(R.id.loginLayoutCardViewPassword3);
        clBackground = (ConstraintLayout) findViewById(R.id.loginLayoutConstrainLayoutCardView);
    }
    public void fpButton(View view){
        emailCV.setVisibility(View.VISIBLE);
        clBackground.setVisibility(View.VISIBLE);
        clBackground.setBackgroundColor(Color.parseColor("#BC858585"));
    }
    public void fpEmailButton(View view){
        emailCV.setVisibility(View.INVISIBLE);
        codeCV.setVisibility(View.VISIBLE);
    }
    public void fpCodeButton(View view){
        codeCV.setVisibility(View.INVISIBLE);
        passCV.setVisibility(View.VISIBLE);
    }
    public void fpRestorePass(View view){
        passCV.setVisibility(View.INVISIBLE);
        clBackground.setVisibility(View.INVISIBLE);
    }
}