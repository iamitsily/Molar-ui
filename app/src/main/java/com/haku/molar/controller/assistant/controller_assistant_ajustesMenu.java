package com.haku.molar.controller.assistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haku.molar.R;
import com.haku.molar.general.controller_General_Login;

import java.util.HashMap;
import java.util.Map;

public class controller_assistant_ajustesMenu extends AppCompatActivity {
    String matricula, nombre, rol,sexo;
    ImageView imageView, imageViewPerfil;
    TextView tvNombre, tvRol;
    Button terminosCondiciones, ajustesCuentaDatos, cerrarSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_assistant_ajustes_menu);

        //Cast
        imageView = findViewById(R.id.assistant_menu_ajustescuentamenu_imvw);
        tvNombre = findViewById(R.id.assistant_menu_ajustesCuentaMenuNombre);
        tvRol = findViewById(R.id.assistant_menu_ajustesCuentaMenuRol);
        imageViewPerfil = findViewById(R.id.assistant_menu_ADC1_iv2);
        terminosCondiciones = findViewById(R.id.assistant_menu_ADC1_btn2);
        ajustesCuentaDatos = findViewById(R.id.assistant_menu_ADC1_btn1);
        cerrarSesion = findViewById(R.id.assistant_menu_cerrarSesion);
        //Intent
        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");

        inicioUI();

        terminosCondiciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), controller_assistant_TerminosCondiciones.class);
                intent1.putExtra("matricula",matricula);
                intent1.putExtra("nombre", nombre);
                intent1.putExtra("rol", rol);
                intent1.putExtra("sexo", sexo);
                startActivity(intent1);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            }
        });
        ajustesCuentaDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),controller_assistant_ajustesMenuDatos.class);
                intent.putExtra("matricula",matricula);
                intent.putExtra("nombre", nombre);
                intent.putExtra("rol", rol);
                intent.putExtra("sexo", sexo);
                startActivity(intent);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),controller_assistant_MenuAsistente.class);
                intent.putExtra("matricula",matricula);
                intent.putExtra("nombre", nombre);
                intent.putExtra("rol", rol);
                intent.putExtra("sexo", sexo);
                startActivity(intent);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            }
        });
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), controller_General_Login.class));
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,controller_assistant_MenuAsistente.class);
        intent.putExtra("matricula",matricula);
        intent.putExtra("nombre", nombre);
        intent.putExtra("rol", rol);
        intent.putExtra("sexo", sexo);
        startActivity(intent);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
    public void inicioUI(){
        tvNombre.setText(nombre);
        Map<String, String> roles = new HashMap<>();
        roles.put("0", "Administrador");
        roles.put("1", "Paciente");
        roles.put("2", "Doctor");
        roles.put("3", "Asistente");

        String rolText = roles.getOrDefault(rol, "Consulte su rol con el Administrador");
        tvRol.setText(rolText);

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
}