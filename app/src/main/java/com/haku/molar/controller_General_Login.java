package com.haku.molar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.haku.molar.controller.patient.controller_patient_MenuPaciente;
import com.haku.molar.model.model_General_Usuario;

public class controller_General_Login extends AppCompatActivity implements Callback_General_Login {
    EditText matricula, password;
    String passwordString;
    CardView emailCV, codeCV,passCV;
    ConstraintLayout clBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_general_login);

        emailCV = (CardView) findViewById(R.id.loginLayoutCardViewPassword);
        codeCV = (CardView) findViewById(R.id.loginLayoutCardViewPassword2);
        passCV = (CardView) findViewById(R.id.loginLayoutCardViewPassword3);
        clBackground = (ConstraintLayout) findViewById(R.id.loginLayoutConstrainLayoutCardView);
        matricula = (EditText) findViewById(R.id.edtMatriculaLogin);
        password = (EditText) findViewById(R.id.edtPasswordLogin);

    }
    public void fpButton(View view){
        emailCV.setVisibility(View.VISIBLE);
        clBackground.setVisibility(View.VISIBLE);
        clBackground.setBackgroundColor(Color.parseColor("#BC858585"));
    }
    public void fpEmailButton(View view){
        emailCV.setVisibility(View.INVISIBLE);
        codeCV.setVisibility(View.VISIBLE);
    }
    public void fpCodeButton(View view){
        codeCV.setVisibility(View.INVISIBLE);
        passCV.setVisibility(View.VISIBLE);
    }
    public void fpRestorePass(View view){
        passCV.setVisibility(View.INVISIBLE);
        clBackground.setVisibility(View.INVISIBLE);
    }
    public void Login(View view){
        int matriculaInt = Integer.parseInt(matricula.getText().toString().trim());
        passwordString = password.getText().toString().trim();

        model_General_Usuario model_general_usuario = new model_General_Usuario(matriculaInt, passwordString,this,this);
        model_general_usuario.login();
    }
    @Override
    public void onSuccess(String[] datos) {
        System.out.println("controller_General_Login"+datos[0] + datos[1] + datos[2]);
        
        if (passwordString.equals(datos[2])){
            Intent intent = new Intent(this, controller_patient_MenuPaciente.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onError(String mensaje) {
        Toast.makeText(this, "Verifique el usuario", Toast.LENGTH_SHORT).show();
    }
}