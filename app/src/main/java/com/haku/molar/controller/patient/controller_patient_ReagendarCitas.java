package com.haku.molar.controller.patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.haku.molar.R;

public class controller_patient_ReagendarCitas extends AppCompatActivity {
    String matricula,nombre,rol;
    ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_reagendar_citas);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");

        ivBack = findViewById(R.id.AC1_iv);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backBtn();
            }
        });
    }
    public void backBtn(){
        Intent i = new Intent(this,controller_patient_listarCitasActivas.class);
        i.putExtra("matricula",matricula);
        i.putExtra("nombre",nombre);
        i.putExtra("rol",rol);
        i.putExtra("opcion","0");
        startActivity(i);
        finish();
    }
}


