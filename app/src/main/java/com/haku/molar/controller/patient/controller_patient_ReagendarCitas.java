package com.haku.molar.controller.patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.haku.molar.R;

public class controller_patient_ReagendarCitas extends AppCompatActivity {
    String matricula,nombre,rol,idCita,motivo,sexo;
    ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_reagendar_citas);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        idCita = intent.getStringExtra("idCita");
        sexo = intent.getStringExtra("sexo");

        ivBack = findViewById(R.id.AC1_iv);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backBtn();
            }
        });
    }
    @Override
    public void onBackPressed() {
        backBtn();
    }
    public void reagendarHoraDia(View view){
        EditText editText = findViewById(R.id.view_patient_agendarcitas_descripcion);
        motivo = editText.getText().toString().trim();
        if (motivo.equals("") || motivo.length()<20 || !motivo.matches("^[a-zA-ZáÁéÉíÍóÓúÚñÑüÜ\\s.,]*$")){
            Toast.makeText(this, "Datos invalidos, ingresa tu motivo, por favor revisar los datos ingresados, Mayor a 20 caracteres", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(this, controller_patient_reagendar_citas_horaDia.class);
            intent.putExtra("matricula",matricula);
            intent.putExtra("nombre",nombre);
            intent.putExtra("rol",rol);
            intent.putExtra("sexo",sexo);
            intent.putExtra("idCita",idCita);
            intent.putExtra("motivo",motivo);
            startActivity(intent);
            finish();
        }
    }
    public void backBtn(){
        Intent i = new Intent(this,controller_patient_listarCitasActivas.class);
        i.putExtra("matricula",matricula);
        i.putExtra("nombre",nombre);
        i.putExtra("rol",rol);
        i.putExtra("sexo",sexo);
        i.putExtra("opcion","0");
        startActivity(i);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
}


