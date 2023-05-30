package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haku.molar.R;
import com.haku.molar.general.controller_General_Login;

import java.util.HashMap;

public class controller_patient_AjustesDeCuentaMenu extends AppCompatActivity {
    ImageView imageView, imageViewPerfil;
    TextView tvNombre, tvRol;
    String matricula, nombre, rol,sexo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_ajustes_de_cuenta_menu);

        //Intent
        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");

        //Cast
        imageView = findViewById(R.id.view_patient_ajustescuentamenu_imvw);
        tvNombre = findViewById(R.id.view_patient_ajustesCuentaMenuNombre);
        tvRol = findViewById(R.id.view_patient_ajustesCuentaMenuRol);
        imageViewPerfil = findViewById(R.id.ADC1_iv2);
        //Funciones
        inicioUI();

        //Listeners
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               regresar(v);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, controller_patient_MenuPaciente.class);
        i.putExtra("matricula",matricula);
        i.putExtra("nombre",nombre);
        i.putExtra("rol",rol);
        i.putExtra("sexo",sexo);
        startActivity(i);
        finish();
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
        HashMap<String, Pair<Integer, ImageView.ScaleType>> mapaSexo = new HashMap<>();
        mapaSexo.put("0", new Pair<>(R.mipmap.hombre, ImageView.ScaleType.CENTER_CROP));
        mapaSexo.put("1", new Pair<>(R.mipmap.mujer, ImageView.ScaleType.CENTER_CROP));
        mapaSexo.put("12", new Pair<>(R.mipmap.hombredos, ImageView.ScaleType.CENTER_CROP));
        mapaSexo.put("13", new Pair<>(R.mipmap.hombretres, ImageView.ScaleType.CENTER_CROP));
        mapaSexo.put("14", new Pair<>(R.mipmap.hombrecuatro, ImageView.ScaleType.CENTER_CROP));
        mapaSexo.put("15", new Pair<>(R.mipmap.hombrecinco, ImageView.ScaleType.CENTER_CROP));
        mapaSexo.put("22", new Pair<>(R.mipmap.mujerdos, ImageView.ScaleType.FIT_CENTER));
        mapaSexo.put("23", new Pair<>(R.mipmap.mujertres, ImageView.ScaleType.FIT_CENTER));
        mapaSexo.put("24", new Pair<>(R.mipmap.mujercuatro, ImageView.ScaleType.FIT_CENTER));
        mapaSexo.put("25", new Pair<>(R.mipmap.mujercinco, ImageView.ScaleType.FIT_CENTER));

        Pair<Integer, ImageView.ScaleType> opcionSexo = mapaSexo.get(sexo);

        if (opcionSexo == null) {
            Toast.makeText(this, "Elija una opción válida", Toast.LENGTH_SHORT).show();
        } else {
            imageViewPerfil.setScaleType(opcionSexo.second);
            imageViewPerfil.setImageResource(opcionSexo.first);
        }
    }
    public void ajustdesCuenta(View view){
        Intent intentAjustesCuenta = new Intent(this, controller_patient_AjustesCuentaDatos.class);
        intentAjustesCuenta.putExtra("matricula",matricula);
        intentAjustesCuenta.putExtra("nombre",nombre);
        intentAjustesCuenta.putExtra("rol",rol);
        intentAjustesCuenta.putExtra("sexo",sexo);
        startActivity(intentAjustesCuenta);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
    public void terminosCondiciones(View view){
        Intent terminosCondiciones = new Intent(this, controller_patient_TerminosCondiciones.class);
        terminosCondiciones.putExtra("matricula",matricula);
        terminosCondiciones.putExtra("nombre",nombre);
        terminosCondiciones.putExtra("rol",rol);
        terminosCondiciones.putExtra("sexo",sexo);
        startActivity(terminosCondiciones);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
    public void cerrarSesion(View view){
        startActivity(new Intent(this, controller_General_Login.class));
        finish();
    }
    public void regresar(View view){
        Intent i = new Intent(this, controller_patient_MenuPaciente.class);
        i.putExtra("matricula",matricula);
        i.putExtra("nombre",nombre);
        i.putExtra("rol",rol);
        i.putExtra("sexo",sexo);
        startActivity(i);
        finish();
    }
}