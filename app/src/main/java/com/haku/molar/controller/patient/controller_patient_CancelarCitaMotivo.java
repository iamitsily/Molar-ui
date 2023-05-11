package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.haku.molar.R;

public class controller_patient_CancelarCitaMotivo extends AppCompatActivity {

    String matricula,nombre,rol;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_cancelarcitamotivo);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        nombre = intent.getStringExtra("rol");

        imageView = findViewById(R.id.imageButton8);
    }

    public void regresar(View view){
        Intent i = new Intent(this,controller_patient_HistorialCitasLayout.class);
        i.putExtra("matricula",matricula);
        i.putExtra("nombre",nombre);
        i.putExtra("rol",rol);
        startActivity(i);
        finish();
    }

}