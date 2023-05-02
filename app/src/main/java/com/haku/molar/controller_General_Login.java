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

import com.haku.molar.controller.doctor.controller_doctor_menu_doctor;
import com.haku.molar.controller.patient.controller_patient_MenuPaciente;
import com.haku.molar.model.model_General_Usuario;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class controller_General_Login extends AppCompatActivity implements Callback_General_Login {
    EditText matricula, password;
    String passwordString;
    CardView emailCV, codeCV,passCV;
    ConstraintLayout clBackground;
    Button forgetPassbtn, loginBtn;
    CheckBox checkBoxDatos;
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
        password = (EditText) findViewById(R.id.edtPasswordLogin);
        forgetPassbtn = (Button) findViewById(R.id.view_general_login_btnForgetPassword);
        loginBtn = (Button) findViewById(R.id.view_general_login_loginbtn);
        checkBoxDatos = (CheckBox) findViewById(R.id.view_general_login_checkboxDatos);

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
    }
    public void fpButton(View view){
        emailCV.setVisibility(View.VISIBLE);
        clBackground.setVisibility(View.VISIBLE);
        forgetPassbtn.setVisibility(View.INVISIBLE);
        loginBtn.setVisibility(View.INVISIBLE);
        checkBoxDatos.setVisibility(View.INVISIBLE);
        clBackground.setBackgroundColor(Color.parseColor("#BC858585"));
    }
    public void fpEmailButton(View view){
        emailCV.setVisibility(View.INVISIBLE);
        codeCV.setVisibility(View.VISIBLE);
    }
    public void fpCodeButton(View view){
        codeCV.setVisibility(View.INVISIBLE);
        passCV.setVisibility(View.VISIBLE);
        password.setVisibility(View.INVISIBLE);
    }
    public void fpRestorePass(View view){
        passCV.setVisibility(View.INVISIBLE);
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
                        startActivity(intentDoctor);
                        finish();
                        break;
                    //3 -> menuAsistente
                    case "3":
                        Toast.makeText(this, "MenuAsistente", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(this, controller_assistant_MenuAdmin));
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
}