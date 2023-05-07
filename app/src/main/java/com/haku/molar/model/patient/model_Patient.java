package com.haku.molar.model.patient;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haku.molar.utils.MolarMail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class model_Patient {
    int matricula, rol, sexo;
    String[] res = new String[4];
    String nombre, apellidoPaterno, apellidoMaterno, email, telefono, password, passwordNoCrypt;
    Context context;
    private Callback_patient buscarDatosCallback;

    //Constructores
    public model_Patient() {
    }
    public model_Patient(int matricula, Context context, Callback_patient callback_patient) {
        this.matricula = matricula;
        this.context = context;
        this.buscarDatosCallback = callback_patient;
    }

    public model_Patient(int matricula, int rol, int sexo, String nombre, String apellidoPaterno, String apellidoMaterno, String email, String telefono, String password, Context context, String ContraseñaNoCrypt) {
        this.matricula = matricula;
        this.rol = rol;
        this.sexo = sexo;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.context = context;
        this.passwordNoCrypt = ContraseñaNoCrypt;
    }


    //Funciones
    public void buscarDatos(){
        String url = "https://molarservices.azurewebsites.net/patient/service_seleccionPaciente.php";
        //String url = "https://molarservices.azurewebsites.net/general/login.php";

        String matricula = String.valueOf(getMatricula());
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Obteniendo datos");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (jsonArray.length() == 0){
                        System.out.println("model_general_usuario -> buscarDatos -> datos vacío");
                        buscarDatosCallback.onErrorbuscarDatos("Datos no encontrados");
                        progressDialog.dismiss();
                    }else{
                        if (exito.equals("1")){
                            JSONObject object = jsonArray.getJSONObject(0);
                            String matricula = object.getString("matricula");
                            String email = object.getString("email");
                            String telefono = object.getString("telefono");
                            String password = object.getString("password");
                            String[] res = {matricula, email, telefono, password};
                            progressDialog.dismiss();
                            buscarDatosCallback.onSuccessbuscarDatos(res);
                        }
                    }
                }catch (JSONException e){
                    System.out.println("model_general_usuario -> buscarDatos -> JSONException: "+e);
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("model_general_usuario -> buscarDatos -> onErrorResponse: "+error.getMessage());
                progressDialog.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("matricula",matricula);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void udpatebyUser(){
        String url = "http://192.168.1.70/Molar-Backend/patient/service_modificacionPaciente.php";
        //String url = "https://molarservices.azurewebsites.net/general/login.php";

        String matricula = String.valueOf(getMatricula());
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Obteniendo datos");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                }catch (JSONException e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
    public void registrarPaciente(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Registrando");
        progressDialog.show();
        String url = "https://molarservices.azurewebsites.net/patient/service_registrarPaciente.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Registro exitoso")) {
                    Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    MolarMail molarMail = new MolarMail(email,passwordNoCrypt,String.valueOf(matricula),nombre,context);
                    molarMail.sendMail();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(context, "No se puede registrar", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Error: "+error.getMessage());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                System.out.println("model_Patient->registro->getparams: "+String.valueOf(matricula)+apellidoPaterno+apellidoMaterno+email+telefono+rol+sexo+password);
                params.put("matricula",String.valueOf(matricula));
                params.put("nombre",nombre);
                params.put("apellido_paterno",apellidoPaterno);
                params.put("apellido_materno",apellidoMaterno);
                params.put("email",email);
                params.put("telefono",telefono);
                params.put("rol",String.valueOf(rol));
                params.put("sexo",String.valueOf(sexo));
                params.put("password",password);
                params.put("status","1");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    //Getters y setters
    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
