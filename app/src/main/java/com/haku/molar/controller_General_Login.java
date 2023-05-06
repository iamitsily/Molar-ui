package com.haku.molar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.haku.molar.controller.assistant.controller_assistant_MenuAsistente;
import com.haku.molar.controller.doctor.controller_doctor_menu_doctor;
import com.haku.molar.controller.patient.controller_patient_MenuPaciente;
import com.haku.molar.model.model_General_Usuario;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class controller_General_Login extends AppCompatActivity implements Callback_General_Login {
    EditText matricula, password, edtEmailfp, edtCodefp, edtPassfp, edtPassConfirmedfp;
    String passwordString;
    CardView emailCV, codeCV,passCV, succesCV;
    ConstraintLayout clBackground;
    Button forgetPassbtn, loginBtn;
    CheckBox checkBoxDatos;
    int Codefp = 0;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_general_login);

        emailCV = (CardView) findViewById(R.id.loginLayoutCardViewPassword);
        codeCV = (CardView) findViewById(R.id.loginLayoutCardViewPassword2);
        passCV = (CardView) findViewById(R.id.loginLayoutCardViewPassword3);
        clBackground = (ConstraintLayout) findViewById(R.id.loginLayoutConstrainLayoutCardView);
        matricula = (EditText) findViewById(R.id.edtMatriculaLogin);
        password = (EditText) findViewById(R.id.edtPasswordPro);
        forgetPassbtn = (Button) findViewById(R.id.view_general_login_btnForgetPassword);
        loginBtn = (Button) findViewById(R.id.view_general_login_loginbtn);
        checkBoxDatos = (CheckBox) findViewById(R.id.view_general_login_checkboxDatos);
        succesCV = (CardView) findViewById(R.id.loginLayoutCardViewSuccess);
        edtEmailfp = (EditText) findViewById(R.id.edtMail_login_fp);
        edtCodefp = (EditText) findViewById(R.id.edtCode_login_fp);
        edtPassfp = (EditText) findViewById(R.id.edtPass_login_fp);
        edtPassConfirmedfp = (EditText) findViewById(R.id.edtPassConfirmed_login_fp);
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        password.setInputType(InputType.TYPE_CLASS_TEXT);
                    }
                }
                return false;
            }
        });

        clBackground.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                emailCV.setVisibility(View.INVISIBLE);
                codeCV.setVisibility(View.INVISIBLE);
                passCV.setVisibility(View.INVISIBLE);
                succesCV.setVisibility(View.INVISIBLE);
                clBackground.setVisibility(View.INVISIBLE);
                forgetPassbtn.setVisibility(View.VISIBLE);
                loginBtn.setVisibility(View.VISIBLE);
                checkBoxDatos.setVisibility(View.VISIBLE);
                password.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }
    public void fpButton(View view){
        emailCV.setVisibility(View.VISIBLE);
        clBackground.setVisibility(View.VISIBLE);
        forgetPassbtn.setVisibility(View.INVISIBLE);
        loginBtn.setVisibility(View.INVISIBLE);
        checkBoxDatos.setVisibility(View.INVISIBLE);
        clBackground.setBackgroundColor(Color.parseColor("#BC858585"));
    }
    //Fase email
    public void fpEmailButton(View view){
        if(edtEmailfp.getText().toString().trim().equals("")){
            Toast.makeText(this, "Por favor ingrese su email", Toast.LENGTH_SHORT).show();
        }else{
            emailCV.setVisibility(View.INVISIBLE);
            codeCV.setVisibility(View.VISIBLE);
            Codefp = genCode();
            System.out.println(String.valueOf(Codefp));
            MolarMail molarMail = new MolarMail(edtEmailfp.getText().toString().trim(), String.valueOf(Codefp),this);
            molarMail.codeMail();
        }
    }
    //Fase codigo
    public void fpCodeButton(View view){
        if (edtCodefp.getText().toString().equals("")){
            Toast.makeText(this, "Por favor ingrese el codigo recibido", Toast.LENGTH_SHORT).show();
        }else{
            if (Codefp == Integer.parseInt(edtCodefp.getText().toString())){
                codeCV.setVisibility(View.INVISIBLE);
                passCV.setVisibility(View.VISIBLE);
                password.setVisibility(View.INVISIBLE);
            }else{
                Toast.makeText(this, "No coincide el codigo", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //Fase nueva contraseña
    public void fpRestorePass(View view){
        if (edtPassfp.getText().toString().trim().equals("") || edtPassConfirmedfp.getText().toString().trim().equals("")){
            Toast.makeText(this, "Por favor ingrese la nueva contraseña", Toast.LENGTH_SHORT).show();
        }else{
            if(edtPassfp.getText().toString().equals(edtPassConfirmedfp.getText().toString())){
                String password="";
                try {
                    password = MolarCrypt.encrypt(edtPassfp.getText().toString());
                } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
                    throw new RuntimeException(e);
                }
                model_General_Usuario model_general_usuario = new model_General_Usuario(edtEmailfp.getText().toString(),password,this,this);
                model_general_usuario.restorePass();
            }else{
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //Fase completada
    public void fpSucces(View view){
        succesCV.setVisibility(View.INVISIBLE);
        clBackground.setVisibility(View.INVISIBLE);
        forgetPassbtn.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.VISIBLE);
        checkBoxDatos.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
    }
    public void Login(View view){
        int matriculaInt = Integer.parseInt(matricula.getText().toString().trim());
        passwordString = password.getText().toString().trim();
        if ((matriculaInt==1001) && (passwordString.equals("admin"))){
            startActivity(new Intent(getApplicationContext(), controller_patient_MenuPaciente.class));
            finish();
        }else{
            model_General_Usuario model_general_usuario = new model_General_Usuario(matriculaInt, passwordString,this,this);
            model_general_usuario.login();
        }
    }
    @Override
    public void onSuccess(String[] datos) {
        System.out.println("controller_General_Login"+datos[0] + datos[1] + datos[2] + datos[3]);

        try {
            if (passwordString.equals(MolarCrypt.decrypt(datos[2]))){
                System.out.println("controller_General_Login -> loginOnSuccess -> rol: "+datos[3]);
                switch (datos[3]){
                    //0 -> menuAdmin
                    case "0":
                        Toast.makeText(this, "MenuAdmin", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(this, controller_patient_MenuAdmin));
                        finish();
                        break;
                    //1 -> menuPaciente
                    case "1":
                        Toast.makeText(this, "MenuPacienet", Toast.LENGTH_SHORT).show();
                        Intent intentPatient = new Intent(this, controller_patient_MenuPaciente.class);
                        intentPatient.putExtra("matricula",datos[0]);
                        intentPatient.putExtra("nombre", datos[1]);
                        intentPatient.putExtra("rol",datos[3]);
                        startActivity(intentPatient);
                        finish();
                        break;
                    //2 -> menuDoctor
                    case "2":
                        Toast.makeText(this, "MenuDoctor", Toast.LENGTH_SHORT).show();
                        Intent intentDoctor = new Intent(this, controller_doctor_menu_doctor.class);
                        intentDoctor.putExtra("matricula",datos[0]);
                        intentDoctor.putExtra("nombre", datos[1]);
                        intentDoctor.putExtra("rol",datos[3]);
                        startActivity(intentDoctor);
                        finish();
                        break;
                    //3 -> menuAsistente
                    case "3":
                        Toast.makeText(this, "MenuAsistente", Toast.LENGTH_SHORT).show();
                        Intent intentAssistant = new Intent(this, controller_assistant_MenuAsistente.class);
                        intentAssistant.putExtra("matricula",datos[0]);
                        intentAssistant.putExtra("nombre", datos[1]);
                        intentAssistant.putExtra("rol",datos[3]);
                        startActivity(intentAssistant);
                        finish();
                        break;
                }
            }else{
                Toast.makeText(this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessForgetPass() {
        passCV.setVisibility(View.INVISIBLE);
        succesCV.setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorForgetPass() {
        succesCV.setVisibility(View.INVISIBLE);
        clBackground.setVisibility(View.INVISIBLE);
        forgetPassbtn.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.VISIBLE);
        checkBoxDatos.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
    }

    public int genCode(){
        int code = 0;
        Random random = new Random();
        code = random.nextInt(99999 - 10000 + 1) + 10000;
        return code;
    }
}