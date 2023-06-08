package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.haku.molar.R;
import com.haku.molar.controller.patient.interfaces.Callback_patient_cancelarCitas;
import com.haku.molar.model.cita.model_cita;

public class controller_patient_CancelarCitaMotivo extends AppCompatActivity implements Callback_patient_cancelarCitas {

    String matricula,nombre,rol,idCita,motivo,fecha,hora,sexo;
    ImageView imageView;
    EditText edtMotivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_cancelarcitamotivo);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");
        idCita = intent.getStringExtra("idCita");
        motivo = intent.getStringExtra("motivo");
        fecha = intent.getStringExtra("fecha");
        hora = intent.getStringExtra("hora");
        imageView = findViewById(R.id.imageButton8);
        edtMotivo = findViewById(R.id.edtMotivoCancelarCita);
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,controller_patient_listarCitasActivas.class);
        i.putExtra("matricula",matricula);
        i.putExtra("nombre",nombre);
        i.putExtra("rol",rol);
        i.putExtra("sexo",sexo);
        i.putExtra("opcion","1");
        startActivity(i);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
    public void cancelarCita(View view){
        if (edtMotivo.getText().toString().trim().equals("") || edtMotivo.getText().toString().trim().length()<20|| !edtMotivo.getText().toString().trim().matches("^[a-zA-ZáÁéÉíÍóÓúÚñÑüÜ\\s.,]*$")){
            Toast.makeText(this, "Datos invalidos, ingresa tu motivo, por favor revisar los datos ingresados, Mayor a 20 caracteres", Toast.LENGTH_SHORT).show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("¿Cancelar cita?");
            builder.setMessage("Revise los datos de la cita a cancelar.\nIdCita: "+idCita+"\n"+"Motivo: "+motivo+
                    "\nFecha: "+fecha+"\nHora: "+hora).setPositiveButton("Confirmar",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    model_cita model_cita = new model_cita(idCita,edtMotivo.getText().toString().trim(),controller_patient_CancelarCitaMotivo.this,controller_patient_CancelarCitaMotivo.this);
                    model_cita.pendienteCancelarCita();
                }
            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), controller_patient_OpcionesCitas.class);
                    intent.putExtra("matricula",matricula);
                    intent.putExtra("nombre",nombre);
                    intent.putExtra("rol",rol);
                    intent.putExtra("sexo",sexo);
                    startActivity(intent);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                }
            }).setCancelable(false).show();
        }
    }
    public void regresar(View view){
        Intent i = new Intent(this,controller_patient_listarCitasActivas.class);
        i.putExtra("matricula",matricula);
        i.putExtra("nombre",nombre);
        i.putExtra("rol",rol);
        i.putExtra("sexo",sexo);
        i.putExtra("opcion","1");
        startActivity(i);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }

    @Override
    public void onSuccessReagendarCita() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso");
        builder.setMessage("Su solicitud será revisada por nuestros asistentes. \n\nEn cuanto se tenga" +
                " confirmación se le avisará via Email").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intentOpciones = new Intent(getApplicationContext(),controller_patient_OpcionesCitas.class);
                intentOpciones.putExtra("matricula",matricula);
                intentOpciones.putExtra("nombre",nombre);
                intentOpciones.putExtra("rol",rol);
                intentOpciones.putExtra("opcion","1");
                intentOpciones.putExtra("sexo",sexo);
                startActivity(intentOpciones);
                finish();
            }
        }).show();
    }

    @Override
    public void onErrorReagendarCita(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}