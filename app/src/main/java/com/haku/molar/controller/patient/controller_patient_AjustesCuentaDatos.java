package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.haku.molar.R;
import com.haku.molar.model.cita.model_cita;
import com.haku.molar.model.model_General_Usuario;
import com.haku.molar.model.patient.Callback_patient;
import com.haku.molar.model.patient.model_Patient;
import com.haku.molar.utils.MolarCrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.util.ArrayList;

public class controller_patient_AjustesCuentaDatos extends AppCompatActivity implements Callback_patient {
    EditText edt_telefono, edt_email, edt_passActual, edt_passNueva, edt_passconfirmed;
    String matricula, passwordActual,passNueva,passwordConfirmar, email, telefonno;
    Button btnGuardar;
    int pos = 0;
    ProgressDialog progressDialog;
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
        edt_passNueva = findViewById(R.id.view_patient_ajustesCuentaPassword);
        edt_passconfirmed = findViewById(R.id.view_patient_ajustesCuentaConfirmedPassword);
        edt_passActual = findViewById(R.id.view_patient_ajustesCuentaPasswordActual);
        btnGuardar = findViewById(R.id.view_patient_ajustesCuentaGuardarBtn);
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
        edt_passNueva.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edt_passNueva.getRight() - edt_passNueva.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        edt_passNueva.setInputType(InputType.TYPE_CLASS_TEXT);
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
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarDatos();
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
    public void actualizarDatos(){

        telefonno = edt_telefono.getText().toString().trim();
        email = edt_email.getText().toString().trim();
        passwordActual = edt_passActual.getText().toString().trim();
        passNueva = edt_passNueva.getText().toString().trim();
        passwordConfirmar = edt_passconfirmed.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Actualizando datos");
        progressDialog.show();
        model_Patient model_patient = new model_Patient(Integer.parseInt(matricula),this,this);
        model_patient.obtenerPass();
    }
    public void actualizarUsuario(){
        //model_Patient model_patient = new model_Patient();
        //model_patient.udpatebyUser();
        progressDialog.dismiss();
        Toast.makeText(this, "TODO BIEN:D", Toast.LENGTH_SHORT).show();
    }
    public boolean validarDatos(String pass) {
        boolean error = false;

        if (passwordActual.equals("") || passNueva.equals("") || passwordConfirmar.equals("") || telefonno.equals("") || email.equals("")) {
            Toast.makeText(this, "Rellena todos los campos ", Toast.LENGTH_SHORT).show();
            error = true;
        }
        if (!pass.equals(passwordActual)) {
            Toast.makeText(this, "Contraseña actual no coincide", Toast.LENGTH_SHORT).show();
            error = true;
        }
        if (!passNueva.equals(passwordConfirmar)) {
            Toast.makeText(this, "Coontraseñas no coindiden", Toast.LENGTH_SHORT).show();
            error = true;
        }
        if (!validarEmail(email)) {
            error = true;
            edt_email.setText("");
            edt_email.setHint("Verifique email");
            edt_email.setHintTextColor(Color.RED);
        }
        if(telefonno.length()<10){
            error = true;
            edt_telefono.setText("");
            edt_telefono.setHint("Numero a 10 digitos");
            edt_telefono.setHintTextColor(Color.RED);
        }
        return error;
    }
    public static boolean validarEmail(String email) {
        String regex = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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
    @Override
    public void onSuccessObternerPass(String[] datos) {
        try {
            if(!validarDatos(MolarCrypt.decrypt(datos[2]))){
                actualizarUsuario();
            }else{
                progressDialog.dismiss();
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
        }
    }
    @Override
    public void onErrorObternerPass(String datos) {

    }
}
