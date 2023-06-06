package com.haku.molar.model.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haku.molar.controller.admin.interfaces.Callback_admin_menuAdmin;
import com.haku.molar.utils.MolarConfig;
import com.haku.molar.utils.MolarMail;
import com.haku.molar.utils.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class model_Admin {
    String matricula, nombre, apellidoPaterno, apellidoMaterno, email, telefono, password, passwordNoCrypt, status, rol, sexo;
    Context context;
    private Callback_admin_menuAdmin callback_admin_menuAdmin;
    MolarConfig molarConfig = new MolarConfig();

    //controller_admin_menuAdmin
    public model_Admin(Context context, Callback_admin_menuAdmin callback_admin_menuAdmin) {
        this.context = context;
        this.callback_admin_menuAdmin = callback_admin_menuAdmin;
    }
    //controller_admin_RegistrarEmpleado

    public model_Admin(String matricula, String nombre, String apellidoPaterno, String apellidoMaterno, String email, String telefono, String password, String passwordNoCrypt, String rol, String sexo, Context context) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.passwordNoCrypt = passwordNoCrypt;
        this.rol = rol;
        this.sexo = sexo;
        this.context = context;
    }

    //Funciones
    //controller_admin_menuAdmin
    public void contarUserMed(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando Menu");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = molarConfig.getDomainAzure()+"/patient/service_contarPacienteDoctor.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (jsonArray.length() == 0){
                        callback_admin_menuAdmin.onErrorContrarMenu("");
                        progressDialog.dismiss();
                    }else{
                        if (exito.equals("1")){
                            JSONObject object = jsonArray.getJSONObject(0);
                            String numUsuario = object.getString("NumUsuario");
                            object = jsonArray.getJSONObject(1);
                            String numMedico = object.getString("NumMedico");
                            callback_admin_menuAdmin.onSuccessContarMenu(numUsuario,numMedico);
                            progressDialog.dismiss();
                        }
                    }
                } catch (JSONException e) {
                    System.out.println("model_Patient -> obtenerMedico -> JSONException: "+e);
                    e.printStackTrace();
                    callback_admin_menuAdmin.onErrorContrarMenu(e.getMessage());
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                {
                    System.out.println("model_general_usuario -> login -> onErrorResponse: "+error.getMessage());
                    if (error==null){
                        Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void registrarAsistente(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Registrando");
        progressDialog.show();
        String url = molarConfig.getDomainAzure()+"/patient/service_registrarPaciente.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Registro exitoso")) {
                    Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    boolean isConnectedToWifi = Network.isConnectedToWifi(context);
                    boolean isConnectedToMobileData = Network.isConnectedToMobileData(context);
                    if (isConnectedToWifi) {
                        MolarMail molarMail = new MolarMail(email,passwordNoCrypt,String.valueOf(matricula),nombre,context);
                        molarMail.sendMailRegistro();
                        progressDialog.dismiss();
                    } else if (isConnectedToMobileData) {
                        MolarMail molarMail = new MolarMail(email,passwordNoCrypt,String.valueOf(matricula),nombre,context);
                        molarMail.sendMailRegistroRedMovil();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(context, "No es posible enviar email, no hay conexión", Toast.LENGTH_SHORT).show();
                    }
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
                params.put("matricula",matricula);
                params.put("nombre",nombre);
                params.put("apellido_paterno",apellidoPaterno);
                params.put("apellido_materno",apellidoMaterno);
                params.put("email",email);
                params.put("telefono",telefono);
                params.put("rol",rol);
                params.put("sexo",sexo);
                params.put("password",password);
                params.put("status","1");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void registrarMedico(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Registrando");
        progressDialog.show();
        String url = molarConfig.getDomainAzure()+"/doctor/service_registrarDoctor.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Registro exitoso")) {
                    Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    boolean isConnectedToWifi = Network.isConnectedToWifi(context);
                    boolean isConnectedToMobileData = Network.isConnectedToMobileData(context);
                    if (isConnectedToWifi) {
                        MolarMail molarMail = new MolarMail(email,passwordNoCrypt,String.valueOf(matricula),nombre,context);
                        molarMail.sendMailRegistro();
                        progressDialog.dismiss();
                    } else if (isConnectedToMobileData) {
                        MolarMail molarMail = new MolarMail(email,passwordNoCrypt,String.valueOf(matricula),nombre,context);
                        molarMail.sendMailRegistroRedMovil();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(context, "No es posible enviar email, no hay conexión", Toast.LENGTH_SHORT).show();
                    }
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
                params.put("matricula",matricula);
                params.put("nombre",nombre);
                params.put("apellido_paterno",apellidoPaterno);
                params.put("apellido_materno",apellidoMaterno);
                params.put("email",email);
                params.put("telefono",telefono);
                params.put("rol",rol);
                params.put("sexo",sexo);
                params.put("password",password);
                params.put("status","1");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void registrarPaciente(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Registrando");
        progressDialog.show();
        String url = molarConfig.getDomainAzure()+"/patient/service_registrarPaciente.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Registro exitoso")) {
                    Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    boolean isConnectedToWifi = Network.isConnectedToWifi(context);
                    boolean isConnectedToMobileData = Network.isConnectedToMobileData(context);
                    if (isConnectedToWifi) {
                        MolarMail molarMail = new MolarMail(email,passwordNoCrypt,String.valueOf(matricula),nombre,context);
                        molarMail.sendMailRegistro();
                        progressDialog.dismiss();
                    } else if (isConnectedToMobileData) {
                        MolarMail molarMail = new MolarMail(email,passwordNoCrypt,String.valueOf(matricula),nombre,context);
                        molarMail.sendMailRegistroRedMovil();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(context, "No es posible enviar email, no hay conexión", Toast.LENGTH_SHORT).show();
                    }
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
                params.put("matricula",matricula);
                params.put("nombre",nombre);
                params.put("apellido_paterno",apellidoPaterno);
                params.put("apellido_materno",apellidoMaterno);
                params.put("email",email);
                params.put("telefono",telefono);
                params.put("rol",rol);
                params.put("sexo",sexo);
                params.put("password",password);
                params.put("status","1");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }


}
