package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.haku.molar.R;
import com.haku.molar.model.cita.model_cita;
import com.haku.molar.model.model_General_Usuario;
import com.haku.molar.model.patient.Callback_patient;
import com.haku.molar.model.patient.model_Patient;

import java.util.ArrayList;

public class controller_patient_AjustesCuentaDatos extends AppCompatActivity implements Callback_patient {
    String matricula;
    EditText edt_telefono, edt_email, edt_passActual, edt_pass, edt_passconfirmed;
    int pos = 0;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_ajustesdecuenta);

        //Intent
        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");

        System.out.println(matricula);

        //Cast
        edt_telefono = findViewById(R.id.view_patient_ajustesCuentaTelefono);
        edt_email = findViewById(R.id.view_patient_ajustesCuentaEmail);
        edt_pass = findViewById(R.id.view_patient_ajustesCuentaPassword);
        edt_passconfirmed = findViewById(R.id.view_patient_ajustesCuentaConfirmedPassword);
        edt_passActual = findViewById(R.id.view_patient_ajustesCuentaPasswordActual);

        inicioUI();

        edt_passActual.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edt_passActual.getRight() - edt_passActual.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            edt_passActual.setInputType(InputType.TYPE_CLASS_TEXT);
                    }
                }
                return false;
            }
        });
        edt_pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edt_pass.getRight() - edt_pass.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        edt_pass.setInputType(InputType.TYPE_CLASS_TEXT);
                    }
                }
                return false;
            }
        });
        edt_passconfirmed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edt_passconfirmed.getRight() - edt_passconfirmed.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        edt_passconfirmed.setInputType(InputType.TYPE_CLASS_TEXT);
                    }
                }
                return false;
            }
        });
    }

    public void backCuentaMenuBtn(View view){
        Intent intent = new Intent(this, controller_patient_AjustesDeCuentaMenu.class);
        startActivity(intent);
        finish();
    }
    public void inicioUI(){
        model_Patient model_patient= new model_Patient(Integer.parseInt(matricula),this,this);
        model_patient.buscarDatos();
    }

    @Override
    public void onSuccessbuscarDatos(String[] datos) {
        System.out.println("controller_General_Login"+datos[0] + datos[1] + datos[2] + datos[3]);
        edt_email.setText(datos[1]);
        edt_telefono.setText(datos[2]);
    }

    @Override
    public void onErrorbuscarDatos(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessudpatebyUser(String[] datos) {

    }

    @Override
    public void onErrorudpatebyUser(String mensaje) {

    }

    @Override
    public void OnSuccesslistarCitas(ArrayList<model_cita> citas) {

    }

    @Override
    public void OneErrorlistarCitas(String mensaje) {

    }
}
