package com.haku.molar.general;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.haku.molar.R;
import com.haku.molar.controller.admin.controller_admin_menuAdmin;
import com.haku.molar.controller.assistant.controller_assistant_MenuAsistente;
import com.haku.molar.controller.doctor.controller_doctor_menu_doctor;
import com.haku.molar.controller.patient.controller_patient_MenuPaciente;
import com.haku.molar.model.model_General_Usuario;
import com.haku.molar.utils.MolarCrypt;
import com.haku.molar.utils.MolarMail;
import com.haku.molar.utils.Network;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class controller_General_Login extends AppCompatActivity implements Callback_General_Login {
    EditText matricula, password, edtEmailfp, edtCodefp, edtPassfp, edtPassConfirmedfp;
    String passwordString, emailString;
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

        SharedPreferences loginDatos=getSharedPreferences("loginDatos", Context.MODE_PRIVATE);

        if (loginDatos.contains("matricula")&&loginDatos.contains("password")&&loginDatos.contains("checkbox")){
            matricula.setText(loginDatos.getString("matricula",""));
            password.setText(loginDatos.getString("password",""));
            if (loginDatos.getString("checkbox","").equals("1")){
                checkBoxDatos.setChecked(true);
            }else{
                checkBoxDatos.setChecked(false);
            }
        }

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
            emailString = edtEmailfp.getText().toString().trim();
            model_General_Usuario model_general_usuario = new model_General_Usuario(emailString,"",this,this);
            model_general_usuario.validarEmail();
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
                model_General_Usuario model_general_usuario = new model_General_Usuario(emailString,password,this,this);
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
            if (checkBoxDatos.isChecked()){
                SharedPreferences loginDatos=getSharedPreferences("loginDatos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = loginDatos.edit();
                editor.putString("matricula","1001");
                editor.putString("password","admin");
                editor.putString("checkbox","1");
                editor.apply();
            }else{
                SharedPreferences loginDatos=getSharedPreferences("loginDatos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = loginDatos.edit();
                editor.putString("matricula","");
                editor.putString("password","");
                editor.putString("checkbox","0");
                editor.apply();
            }
            startActivity(new Intent(getApplicationContext(), controller_patient_MenuPaciente.class));
            finish();
        }if((matriculaInt==2002) && (passwordString.equals("asistant"))) {
            if (checkBoxDatos.isChecked()){
                SharedPreferences loginDatos=getSharedPreferences("loginDatos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = loginDatos.edit();
                editor.putString("matricula","2002");
                editor.putString("password","asistant");
                editor.putString("checkbox","1");
                editor.apply();
            }else{
                SharedPreferences loginDatos=getSharedPreferences("loginDatos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = loginDatos.edit();
                editor.putString("matricula","");
                editor.putString("password","");
                editor.putString("checkbox","0");
                editor.apply();
            }
            startActivity(new Intent(getApplicationContext(), controller_assistant_MenuAsistente.class));
            finish();
        }else{
            model_General_Usuario model_general_usuario = new model_General_Usuario(matriculaInt, passwordString,this,this);
            model_general_usuario.login();
        }
    }
    @Override
    public void onSuccess(String[] datos) {
        if(datos[5].equals("3")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Aviso");
            builder.setMessage("Su cuenta puede ser suspendida, ya que alcanzo la tolerancia para reagendar o cancelar citas.\n\n" +
                    "En caso de que quiera reiniciar su tolerancia comuniquese con un asistente o a molar.haku@gmail.com").setPositiveButton("Aceptar",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (checkBoxDatos.isChecked()){
                        SharedPreferences loginDatos=getSharedPreferences("loginDatos", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = loginDatos.edit();
                        editor.putString("matricula",matricula.getText().toString().trim());
                        editor.putString("password",password.getText().toString().trim());
                        editor.putString("checkbox","1");
                        editor.apply();
                    }else{
                        SharedPreferences loginDatos=getSharedPreferences("loginDatos", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = loginDatos.edit();
                        editor.putString("matricula","");
                        editor.putString("password","");
                        editor.putString("checkbox","0");
                        editor.apply();
                    }
                    try {
                        if (passwordString.equals(MolarCrypt.decrypt(datos[2]))){
                            System.out.println("controller_General_Login -> loginOnSuccess -> rol: "+datos[3]);
                            switch (datos[3]){
                                //0 -> menuAdmin
                                case "0":
                                    Intent intentAdmin = new Intent(getApplicationContext(), controller_admin_menuAdmin.class);
                                    intentAdmin.putExtra("matricula",datos[0]);
                                    intentAdmin.putExtra("nombre", datos[1]);
                                    intentAdmin.putExtra("rol",datos[3]);
                                    intentAdmin.putExtra("sexo",datos[4]);
                                    startActivity(intentAdmin);
                                    finish();
                                    break;
                                //1 -> menuPaciente
                                case "1":
                                    Intent intentPatient = new Intent(getApplicationContext(), controller_patient_MenuPaciente.class);
                                    intentPatient.putExtra("matricula",datos[0]);
                                    intentPatient.putExtra("nombre", datos[1]);
                                    intentPatient.putExtra("rol",datos[3]);
                                    intentPatient.putExtra("sexo",datos[4]);
                                    startActivity(intentPatient);
                                    finish();
                                    break;
                                //2 -> menuDoctor
                                case "2":
                                    Toast.makeText(getApplicationContext(), "MenuDoctor", Toast.LENGTH_SHORT).show();
                                    Intent intentDoctor = new Intent(getApplicationContext(), controller_doctor_menu_doctor.class);
                                    intentDoctor.putExtra("matricula",datos[0]);
                                    intentDoctor.putExtra("nombre", datos[1]);
                                    intentDoctor.putExtra("rol",datos[3]);
                                    startActivity(intentDoctor);
                                    finish();
                                    break;
                                //3 -> menuAsistente
                                case "3":
                                    Intent intentAssistant = new Intent(getApplicationContext(), controller_assistant_MenuAsistente.class);
                                    intentAssistant.putExtra("matricula",datos[0]);
                                    intentAssistant.putExtra("nombre", datos[1]);
                                    intentAssistant.putExtra("rol",datos[3]);
                                    intentAssistant.putExtra("sexo",datos[4]);
                                    startActivity(intentAssistant);
                                    finish();
                                    break;
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).setCancelable(false).show();
        }else if (datos[5].equals("4")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Aviso");
            builder.setMessage("Su cuenta alcanzo la maxima tolerancia por cancelar o reagendar una cita de manera irresponsable y se inhabilitó su cuenta.\n\n" +
                    "Comunicate con un asistente o a molar.haku@gmail.com").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }).setCancelable(false).show();
        }else{
            if (checkBoxDatos.isChecked()){
                SharedPreferences loginDatos=getSharedPreferences("loginDatos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = loginDatos.edit();
                editor.putString("matricula",matricula.getText().toString().trim());
                editor.putString("password",password.getText().toString().trim());
                editor.putString("checkbox","1");
                editor.apply();
            }else{
                SharedPreferences loginDatos=getSharedPreferences("loginDatos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = loginDatos.edit();
                editor.putString("matricula","");
                editor.putString("password","");
                editor.putString("checkbox","0");
                editor.apply();
            }
            try {
                if (passwordString.equals(MolarCrypt.decrypt(datos[2]))){
                    System.out.println("controller_General_Login -> loginOnSuccess -> rol: "+datos[3]);
                    switch (datos[3]){
                        //0 -> menuAdmin
                        case "0":
                            Intent intentAdmin = new Intent(getApplicationContext(), controller_admin_menuAdmin.class);
                            intentAdmin.putExtra("matricula",datos[0]);
                            intentAdmin.putExtra("nombre", datos[1]);
                            intentAdmin.putExtra("rol",datos[3]);
                            intentAdmin.putExtra("sexo",datos[4]);
                            startActivity(intentAdmin);
                            finish();
                            break;
                        //1 -> menuPaciente
                        case "1":
                            Intent intentPatient = new Intent(this, controller_patient_MenuPaciente.class);
                            intentPatient.putExtra("matricula",datos[0]);
                            intentPatient.putExtra("nombre", datos[1]);
                            intentPatient.putExtra("rol",datos[3]);
                            intentPatient.putExtra("sexo",datos[4]);
                            startActivity(intentPatient);
                            finish();
                            break;
                        //2 -> menuDoctor
                        case "2":
                            Intent intentDoctor = new Intent(this, controller_doctor_menu_doctor.class);
                            intentDoctor.putExtra("matricula",datos[0]);
                            intentDoctor.putExtra("nombre", datos[1]);
                            intentDoctor.putExtra("rol",datos[3]);
                            intentDoctor.putExtra("sexo",datos[4]);
                            startActivity(intentDoctor);
                            finish();
                            break;
                        //3 -> menuAsistente
                        case "3":
                            Intent intentAssistant = new Intent(this, controller_assistant_MenuAsistente.class);
                            intentAssistant.putExtra("matricula",datos[0]);
                            intentAssistant.putExtra("nombre", datos[1]);
                            intentAssistant.putExtra("rol",datos[3]);
                            intentAssistant.putExtra("sexo",datos[4]);
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

    @Override
    public void onSuccesValidarEmail() {
        emailCV.setVisibility(View.INVISIBLE);
        codeCV.setVisibility(View.VISIBLE);
        Codefp = genCode();
        System.out.println(String.valueOf(Codefp));
        boolean isConnectedToWifi = Network.isConnectedToWifi(this);
        boolean isConnectedToMobileData = Network.isConnectedToMobileData(this);
        if (isConnectedToWifi) {
            MolarMail molarMail = new MolarMail(edtEmailfp.getText().toString().trim(), String.valueOf(Codefp),this);
            molarMail.codeMailFP();
        } else if (isConnectedToMobileData) {
            MolarMail molarMail = new MolarMail(edtEmailfp.getText().toString().trim(), String.valueOf(Codefp),this);
            molarMail.codeMailFPRedMovil();
        } else {
            Toast.makeText(this, "No es posible enviar email, no hay conexión", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onErrorValidarEmail(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}