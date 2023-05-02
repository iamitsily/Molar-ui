package com.haku.molar.controller.assistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;

public class controller_assistant_menuPacientes extends AppCompatActivity {
    BottomNavigationView menuNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_assistant_menu_pacientes);

        menuNav = findViewById(R.id.menu_assistant_menu);
        menuNav.setSelectedItemId(R.id.menu_assistant_paciente);

        //Listeners
        menuNav.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.menu_assistant_home:
                    startActivity(new Intent(this, controller_assistant_MenuAsistente.class));
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_assistant_citas:
                    startActivity(new Intent(this, controller_assistant_menuCitas.class));
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
            }
            return false;
        });
    }
}