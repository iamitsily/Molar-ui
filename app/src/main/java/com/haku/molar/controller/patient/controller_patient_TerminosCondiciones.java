package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.haku.molar.R;

public class controller_patient_TerminosCondiciones extends AppCompatActivity {
    String matricula, nombre, rol,sexo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_terminos_condiciones);

        //Intent
        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");

    }
    @Override
    public void onBackPressed() {
        Intent terminosCondiciones = new Intent(this, controller_patient_AjustesDeCuentaMenu.class);
        terminosCondiciones.putExtra("matricula",matricula);
        terminosCondiciones.putExtra("nombre",nombre);
        terminosCondiciones.putExtra("rol",rol);
        terminosCondiciones.putExtra("sexo",sexo);
        startActivity(terminosCondiciones);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
    public void backBtn(View view){
        Intent terminosCondiciones = new Intent(this, controller_patient_AjustesDeCuentaMenu.class);
        terminosCondiciones.putExtra("matricula",matricula);
        terminosCondiciones.putExtra("nombre",nombre);
        terminosCondiciones.putExtra("rol",rol);
        terminosCondiciones.putExtra("sexo",sexo);
        startActivity(terminosCondiciones);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
}