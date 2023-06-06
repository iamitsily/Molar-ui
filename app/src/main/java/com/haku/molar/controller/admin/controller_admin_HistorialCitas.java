package com.haku.molar.controller.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;

public class controller_admin_HistorialCitas extends AppCompatActivity {
    String matricula, nombre, rol, sexo;
    ImageView ivBackBtn, fotoPerfil;
    TextView tvNombre, tvMatricula;
    RecyclerView rvListaCitas;
    BottomNavigationView menuNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_admin_historial_citas);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");

        ivBackBtn = findViewById(R.id.assistant_listaCitas_ivBackbtn);
        tvNombre = findViewById(R.id.assistant_listaCitas_detallesCitaNombre);
        tvMatricula = findViewById(R.id.assistant_listaCitas_detallesCitaMatricula);
        fotoPerfil = findViewById(R.id.assistant_listaCitas_FotoPerfil);
        rvListaCitas = findViewById(R.id.assistant_listaCitas_istarCitasActivasRV);

        menuNav = findViewById(R.id.menu_admin_menu);
        menuNav.setSelectedItemId(R.id.menu_admin_citas);

        menuNav.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.menu_admin_empleados:
                    Intent intent1 = new Intent(this, controller_admin_menuEmpleados.class);
                    intent1.putExtra("matricula",matricula);
                    intent1.putExtra("nombre", nombre);
                    intent1.putExtra("rol", rol);
                    intent1.putExtra("sexo", sexo);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_admin_usuarios:
                    Intent intent2 = new Intent(this, controller_admin_menuUsuario.class);
                    intent2.putExtra("matricula",matricula);
                    intent2.putExtra("nombre", nombre);
                    intent2.putExtra("rol", rol);
                    intent2.putExtra("sexo", sexo);
                    startActivity(intent2);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_admin_home:

                    Intent intent3 = new Intent(this, controller_admin_menuAdmin.class);
                    intent3.putExtra("matricula",matricula);
                    intent3.putExtra("nombre", nombre);
                    intent3.putExtra("rol", rol);
                    intent3.putExtra("sexo", sexo);
                    startActivity(intent3);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_admin_reportes:
                    Intent intent4 = new Intent(this, controller_admin_menuReportes.class);
                    intent4.putExtra("matricula",matricula);
                    intent4.putExtra("nombre", nombre);
                    intent4.putExtra("rol", rol);
                    intent4.putExtra("sexo", sexo);
                    startActivity(intent4);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
            }
            return false;
        });

    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Molar");
        builder.setMessage("¿Desea salir de la aplicación?").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setCancelable(false).show();
    }
}