package com.haku.molar.controller.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;
import com.haku.molar.controller.admin.interfaces.Callback_admin_menuAdmin;
import com.haku.molar.controller.assistant.controller_assistant_ajustesMenu;
import com.haku.molar.controller.assistant.controller_assistant_menuCitas;
import com.haku.molar.controller.assistant.controller_assistant_menuPacientes;
import com.haku.molar.model.admin.model_Admin;
import com.haku.molar.model.assistant.model_Assistant;

import java.util.HashMap;

public class controller_admin_menuAdmin extends AppCompatActivity implements Callback_admin_menuAdmin {
    TextView tvNombre,tvMatricula,totalUser,totalMedicos;
    CardView cvRegistroEmpleados, cvRegistroUsuarios;
    String matricula, nombre, rol,sexo;
    ImageButton ibPerfil;
    BottomNavigationView menuNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_admin_menu_admin);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");

        menuNav = findViewById(R.id.menu_admin_menu);
        menuNav.setSelectedItemId(R.id.menu_admin_menu);
        tvNombre = findViewById(R.id.admin_menuAdmin_nombreAsistente);
        tvMatricula = findViewById(R.id.admin_menuAdmin_matricula);
        cvRegistroEmpleados = findViewById(R.id.admin_menuAdmin_registroEmpleados);
        cvRegistroUsuarios = findViewById(R.id.admin_menuAdmin_registroUsuarios);
        ibPerfil = findViewById(R.id.admin_menuAdmin_Perfil);
        totalUser = findViewById(R.id.admin_menuAdmin_totalPacientes);
        totalMedicos = findViewById(R.id.admin_menuAdmin_totalMedicos);

        inicioUI();

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
                case R.id.menu_admin_citas:

                    Intent intent3 = new Intent(this, controller_admin_HistorialCitas.class);
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
        cvRegistroEmpleados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), controller_admin_RegistrarEmpleado.class);
                intent1.putExtra("matricula",matricula);
                intent1.putExtra("nombre", nombre);
                intent1.putExtra("rol", rol);
                intent1.putExtra("sexo", sexo);
                startActivity(intent1);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            }
        });
        cvRegistroUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), controller_admin_RegistrarPaciente.class);
                intent1.putExtra("matricula",matricula);
                intent1.putExtra("nombre", nombre);
                intent1.putExtra("rol", rol);
                intent1.putExtra("sexo", sexo);
                startActivity(intent1);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            }
        });
        ibPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), controller_assistant_ajustesMenu.class);
                intent.putExtra("matricula",matricula);
                intent.putExtra("nombre", nombre);
                intent.putExtra("rol", rol);
                intent.putExtra("sexo", sexo);
                startActivity(intent);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            }
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
    public void inicioUI(){
        tvNombre.setText(nombre);
        tvMatricula.setText(matricula+ " | Administrador");
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
            ibPerfil.setScaleType(opcionSexo.second);
            ibPerfil.setImageResource(opcionSexo.first);
        }
        model_Admin model_admin = new model_Admin(this,this);
        model_admin.contarUserMed();
    }

    @Override
    public void onSuccessContarMenu(String numPacientes, String numMedicos) {
        totalUser.setText(numPacientes);
        totalMedicos.setText(numMedicos);
    }

    @Override
    public void onErrorContrarMenu(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}