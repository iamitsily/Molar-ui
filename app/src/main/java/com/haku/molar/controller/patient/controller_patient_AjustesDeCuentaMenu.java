package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haku.molar.R;
import com.haku.molar.general.controller_General_Login;

public class controller_patient_AjustesDeCuentaMenu extends AppCompatActivity {
    ImageView imageView;
    TextView tvNombre, tvRol;
    String matricula, nombre, rol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_ajustes_de_cuenta_menu);

        //Intent
        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");

        //Cast
        imageView = findViewById(R.id.view_patient_ajustescuentamenu_imvw);
        tvNombre = findViewById(R.id.view_patient_ajustesCuentaMenuNombre);
        tvRol = findViewById(R.id.view_patient_ajustesCuentaMenuRol);

        //Funciones
        inicioUI();

        //Listeners
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), controller_patient_MenuPaciente.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void inicioUI(){
        tvNombre.setText(nombre);
        switch (rol){
            case "0":
                tvRol.setText("Administrador");
                break;
            case "1":
                tvRol.setText("Paciente");
                break;
            case "2":
                tvRol.setText("Doctor");
                break;
            case "3":
                tvRol.setText("Asistente");
                break;
            default:
            tvRol.setText("Consulte su rol con el Administrador");
                break;
        }
    }
    public void ajustdesCuenta(View view){
        Intent intentAjustesCuenta = new Intent(this, controller_patient_AjustesCuentaDatos.class);
        intentAjustesCuenta.putExtra("matricula",matricula);
        startActivity(intentAjustesCuenta);
        finish();
    }
    public void cerrarSesion(View view){
        startActivity(new Intent(this, controller_General_Login.class));
        finish();
    }
}