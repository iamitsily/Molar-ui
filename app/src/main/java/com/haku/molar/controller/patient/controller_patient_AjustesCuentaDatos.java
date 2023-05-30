package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.haku.molar.R;
import com.haku.molar.controller.patient.interfaces.Callback_patient_ajustesPaciente;
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

public class controller_patient_AjustesCuentaDatos extends AppCompatActivity implements Callback_patient_ajustesPaciente {
    EditText edt_telefono, edt_email, edt_passActual, edt_passNueva, edt_passconfirmed;
    String matricula, nombre, rol, passwordActual,passNueva,passwordConfirmar, email, telefonno,sexo;
    Button btnGuardar;
    int pos = 0;
    ProgressDialog progressDialog;
    ImageView imageViewPerfil, ivCerrarCV, hombre2,hombre3,hombre4,hombre5,mujer2,mujer3,mujer4,mujer5;
    ImageButton ibCambiarPerfil;
    CardView cdIconos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_ajustesdecuenta);

        //Intent
        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");

        //Cast
        edt_telefono = findViewById(R.id.view_patient_ajustesCuentaTelefono);
        edt_email = findViewById(R.id.view_patient_ajustesCuentaEmail);
        edt_passNueva = findViewById(R.id.view_patient_ajustesCuentaPassword);
        edt_passconfirmed = findViewById(R.id.view_patient_ajustesCuentaConfirmedPassword);
        edt_passActual = findViewById(R.id.view_patient_ajustesCuentaPasswordActual);
        btnGuardar = findViewById(R.id.view_patient_ajustesCuentaGuardarBtn);
        imageViewPerfil = findViewById(R.id.patient_ajustesCuentaPerfil);
        ibCambiarPerfil = findViewById(R.id.patient_ibAjusteDatosCambiarPerfil);
        cdIconos = findViewById(R.id.patient_ajustesCuentaDatosCv);
        ivCerrarCV = findViewById(R.id.patient_ajustesCuenta_cerrarCV);
        hombre2 = findViewById(R.id.ivHombreDos);
        hombre3 = findViewById(R.id.ivHombreTres);
        hombre4 = findViewById(R.id.ivHombreCuatro);
        hombre5 = findViewById(R.id.ivHombreCinco);
        mujer2 = findViewById(R.id.ivMujerDos);
        mujer3 = findViewById(R.id.ivMujerTres);
        mujer4 = findViewById(R.id.ivMujerCuatro);
        mujer5 = findViewById(R.id.ivMujerCinco);

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
                "de que el email y numero de telefono sean correctos").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setCancelable(true).show();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, controller_patient_AjustesDeCuentaMenu.class);
        intent.putExtra("matricula",matricula);
        intent.putExtra("nombre",nombre);
        intent.putExtra("rol",rol);
        intent.putExtra("sexo",sexo);
        startActivity(intent);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
    public void updateIcon(String opc){
        model_Patient model_patient = new model_Patient(Integer.parseInt(matricula),this,this);
        switch (opc){
            case "12":
                model_patient.updateIcon(opc);
                imageViewPerfil.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageViewPerfil.setImageResource(R.mipmap.hombredos);
                sexo = "12";
                break;
            case "13":
                model_patient.updateIcon(opc);
                imageViewPerfil.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageViewPerfil.setImageResource(R.mipmap.hombretres);
                sexo = "13";
                break;
            case "14":
                model_patient.updateIcon(opc);
                imageViewPerfil.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageViewPerfil.setImageResource(R.mipmap.hombrecuatro);
                sexo = "14";
                break;
            case "15":
                model_patient.updateIcon(opc);
                imageViewPerfil.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageViewPerfil.setImageResource(R.mipmap.hombrecinco);
                sexo = "15";
                break;
            case "22":
                model_patient.updateIcon(opc);
                imageViewPerfil.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageViewPerfil.setImageResource(R.mipmap.mujerdos);
                sexo = "22";
                break;
            case "23":
                model_patient.updateIcon(opc);
                imageViewPerfil.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageViewPerfil.setImageResource(R.mipmap.mujertres);
                sexo = "23";
                break;
            case "24":
                model_patient.updateIcon(opc);
                imageViewPerfil.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageViewPerfil.setImageResource(R.mipmap.mujercuatro);
                sexo = "24";
                break;
            case "25":
                model_patient.updateIcon(opc);
                imageViewPerfil.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageViewPerfil.setImageResource(R.mipmap.mujercinco);
                sexo = "25";
                break;
            default:
                Toast.makeText(this, "Elija una opcion valida", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public void backCuentaMenuBtn(View view){
        Intent intent = new Intent(this, controller_patient_AjustesDeCuentaMenu.class);
        intent.putExtra("matricula",matricula);
        intent.putExtra("nombre",nombre);
        intent.putExtra("rol",rol);
        intent.putExtra("sexo",sexo);
        startActivity(intent);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
    public void inicioUI(){
        switch (sexo){
            case "12":
                imageViewPerfil.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageViewPerfil.setImageResource(R.mipmap.hombredos);
                break;
            case "13":
                imageViewPerfil.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageViewPerfil.setImageResource(R.mipmap.hombretres);
                break;
            case "14":
                imageViewPerfil.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageViewPerfil.setImageResource(R.mipmap.hombrecuatro);
                break;
            case "15":
                imageViewPerfil.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageViewPerfil.setImageResource(R.mipmap.hombrecinco);
                break;
            case "22":
                imageViewPerfil.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageViewPerfil.setImageResource(R.mipmap.mujerdos);
                break;
            case "23":
                imageViewPerfil.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageViewPerfil.setImageResource(R.mipmap.mujertres);
                break;
            case "24":
                imageViewPerfil.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageViewPerfil.setImageResource(R.mipmap.mujercuatro);
                break;
            case "25":
                imageViewPerfil.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageViewPerfil.setImageResource(R.mipmap.mujercinco);
                break;
            default:
                Toast.makeText(this, "Elija una opcion valida", Toast.LENGTH_SHORT).show();
                break;
        }
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
        String passCrypt = "";
        try {
            passCrypt = MolarCrypt.encrypt(passNueva);
            model_Patient model_patient = new model_Patient(Integer.parseInt(matricula),email,telefonno,passCrypt,this,this);
            model_patient.udpatebyUser();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
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
