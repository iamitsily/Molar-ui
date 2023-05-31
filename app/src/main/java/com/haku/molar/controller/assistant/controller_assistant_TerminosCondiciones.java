package com.haku.molar.controller.assistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.haku.molar.R;

public class controller_assistant_TerminosCondiciones extends AppCompatActivity {
    String matricula, nombre, rol,sexo;
    ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_assistant_terminos_condiciones);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");

        btnBack = findViewById(R.id.assistant_menu_ibBackBtnTerminosCondiciones);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }
    @Override
    public void onBackPressed() {
        back();
    }
    public void back(){
        Intent intent = new Intent(getApplicationContext(),controller_assistant_ajustesMenu.class);
        intent.putExtra("matricula",matricula);
        intent.putExtra("nombre", nombre);
        intent.putExtra("rol", rol);
        intent.putExtra("sexo", sexo);
        startActivity(intent);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
}