package com.haku.molar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.haku.molar.controller.doctor.controller_doctor_menu_doctor;
import com.haku.molar.controller.patient.*;
import com.haku.molar.general.controller_General_Login;
import com.haku.molar.utils.Network;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, controller_General_Login.class);
        startActivity(intent);
        finish();
    }
}