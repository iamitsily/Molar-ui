package com.haku.molar.controller.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.haku.molar.R;
import com.haku.molar.controller.assistant.controller_assistant_ajustesMenu;
import com.haku.molar.controller.doctor.interfaces.Callback_doctor_ajustesDoctor;
import com.haku.molar.model.assistant.model_Assistant;
import com.haku.molar.model.doctor.model_Doctor;
import com.haku.molar.utils.MolarCrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class controller_doctor_ajustesDatos extends AppCompatActivity implements Callback_doctor_ajustesDoctor {
    EditText edt_telefono, edt_email, edt_passActual, edt_passNueva, edt_passconfirmed;
    String matricula, nombre, rol, passwordActual,passNueva,passwordConfirmar, email, telefonno,sexo;
    Button btnGuardar;
    ProgressDialog progressDialog;
    ImageView imageViewPerfil, ivCerrarCV, hombre2,hombre3,hombre4,hombre5,mujer2,mujer3,mujer4,mujer5;
    ImageButton ibCambiarPerfil;
    CardView cdIconos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_doctor_ajustes_datos);

        //Intent
        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");

        //Cast
        edt_telefono = findViewById(R.id.doctor_ajusteDatos_numeroTelefono);
        edt_email = findViewById(R.id.doctor_ajusteDatos_email);
        edt_passNueva = findViewById(R.id.doctor_ajusteDatos_passwordNueva);
        edt_passconfirmed = findViewById(R.id.doctor_ajusteDatos_passwordConfirmar);
        edt_passActual = findViewById(R.id.doctor_ajusteDatos_passwordActual);
        btnGuardar = findViewById(R.id.doctor_ajusteDatos_guardarbtn);
        imageViewPerfil = findViewById(R.id.doctor_ajusteDatos_fotoperfil);
        ibCambiarPerfil = findViewById(R.id.doctor_ajusteDatos_cambiarFotoPerfil);
        cdIconos = findViewById(R.id.doctor_ajusteDatos_cv);
        ivCerrarCV = findViewById(R.id.doctor_ajusteDatos_cerrarcv);
        hombre2 = findViewById(R.id.doctor_ajusteDatos_ivHombreDos);
        hombre3 = findViewById(R.id.doctor_ajusteDatos_ivHombreTres);
        hombre4 = findViewById(R.id.doctor_ajusteDatos_ivHombreCuatro);
        hombre5 = findViewById(R.id.doctor_ajusteDatos_ivHombreCinco);
        mujer2 = findViewById(R.id.doctor_ajusteDatos_ivMujerDos);
        mujer3 = findViewById(R.id.doctor_ajusteDatos_ivMujerTres);
        mujer4 = findViewById(R.id.doctor_ajusteDatos_ivMujerCuatro);
        mujer5 = findViewById(R.id.doctor_ajusteDatos_ivMujerCinco);

        inicioUI();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarDatos();
            }
        });
        ibCambiarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdIconos.setVisibility(View.VISIBLE);
                ibCambiarPerfil.setEnabled(false);
            }
        });
        ivCerrarCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdIconos.setVisibility(View.INVISIBLE);
                ibCambiarPerfil.setEnabled(true);
            }
        });
        hombre2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIcon("12");
            }
        });
        hombre3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIcon("13");
            }
        });
        hombre4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIcon("14");
            }
        });
        hombre5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIcon("15");
            }
        });
        mujer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIcon("22");
            }
        });
        mujer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIcon("23");
            }
        });
        mujer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIcon("24");
            }
        });
        mujer5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIcon("25");
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso");
        builder.setMessage("Por favor antes de actualizar sus datos, asegurese " +
                "de que el email y número de telefono sean correctos.").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setCancelable(true).show();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, controller_doctor_ajustesMenu.class);
        intent.putExtra("matricula",matricula);
        intent.putExtra("nombre",nombre);
        intent.putExtra("rol",rol);
        intent.putExtra("sexo",sexo);
        startActivity(intent);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
    public void updateIcon(String opc){
        System.out.println(matricula);
        model_Doctor model_doctor = new model_Doctor(matricula,this,this);
        HashMap<String, Pair<Integer, ImageView.ScaleType>> mapaOpciones = new HashMap<>();
        mapaOpciones.put("12", new Pair<>(R.mipmap.hombredos, ImageView.ScaleType.CENTER_CROP));
        mapaOpciones.put("13", new Pair<>(R.mipmap.hombretres, ImageView.ScaleType.CENTER_CROP));
        mapaOpciones.put("14", new Pair<>(R.mipmap.hombrecuatro, ImageView.ScaleType.CENTER_CROP));
        mapaOpciones.put("15", new Pair<>(R.mipmap.hombrecinco, ImageView.ScaleType.CENTER_CROP));
        mapaOpciones.put("22", new Pair<>(R.mipmap.mujerdos, ImageView.ScaleType.FIT_CENTER));
        mapaOpciones.put("23", new Pair<>(R.mipmap.mujertres, ImageView.ScaleType.FIT_CENTER));
        mapaOpciones.put("24", new Pair<>(R.mipmap.mujercuatro, ImageView.ScaleType.FIT_CENTER));
        mapaOpciones.put("25", new Pair<>(R.mipmap.mujercinco, ImageView.ScaleType.FIT_CENTER));

        Pair<Integer, ImageView.ScaleType> opcion = mapaOpciones.get(opc);
        if (opcion != null) {
            model_doctor.updateIconDoctor(opc);
            imageViewPerfil.setScaleType(opcion.second);
            imageViewPerfil.setImageResource(opcion.first);
            sexo = opc;
        } else {
            Toast.makeText(this, "Elija una opción válida", Toast.LENGTH_SHORT).show();
        }
    }
    public void backCuentaMenuBtn(View view){
        Intent intent = new Intent(this, controller_doctor_ajustesMenu.class);
        intent.putExtra("matricula",matricula);
        intent.putExtra("nombre",nombre);
        intent.putExtra("rol",rol);
        intent.putExtra("sexo",sexo);
        startActivity(intent);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
    public void inicioUI(){
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
            imageViewPerfil.setScaleType(opcionSexo.second);
            imageViewPerfil.setImageResource(opcionSexo.first);
        }
        model_Doctor model_doctor= new model_Doctor(matricula,this,this);
        model_doctor.buscarDatosDoctor();
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
        model_Doctor model_doctor = new model_Doctor(matricula,this,this);
        model_doctor.obtenerPassDoctor();
    }
    public void actualizarUsuario(){
        String passCrypt = "";
        try {
            passCrypt = MolarCrypt.encrypt(passNueva);
            model_Doctor model_doctor = new model_Doctor(matricula,email,telefonno,passCrypt,this,this);
            model_doctor.udpatebyUserDoctor();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException |
                 InvalidAlgorithmParameterException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e) {
            System.out.println("Error al encriptar la contraseña");
        }
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
    public void onSuccessUpdatebyUser() {
        progressDialog.dismiss();
        Toast.makeText(this, "Datos actualizados", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorudpatebyUser(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, datos, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessUpdateIcon() {
        cdIconos.setVisibility(View.INVISIBLE);
        ibCambiarPerfil.setEnabled(true);
    }

    @Override
    public void onErrorUpdateIcon(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}