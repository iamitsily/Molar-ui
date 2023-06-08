package com.haku.molar.model.admin;

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
import com.haku.molar.controller.admin.interfaces.Callback_admin_historialCitas;
import com.haku.molar.controller.admin.interfaces.Callback_admin_listaEmpleados;
import com.haku.molar.controller.admin.interfaces.Callback_admin_listaPacientes;
import com.haku.molar.controller.admin.interfaces.Callback_admin_menuAdmin;
import com.haku.molar.controller.admin.interfaces.Callback_admin_reporteGeneral;
import com.haku.molar.model.assistant.model_Assistant;
import com.haku.molar.model.cita.model_cita;
import com.haku.molar.utils.MolarConfig;
import com.haku.molar.utils.MolarMail;
import com.haku.molar.utils.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class model_Admin {
    String matricula, nombre, apellidoPaterno, apellidoMaterno, email, telefono, password, passwordNoCrypt, status, rol, sexo;
    String reporteId, reporteDia, reporteHora, reporteMotivo, reporteDescripcion, reporteEstado, reporteIdUsuario, reporteIdMedico;
    Context context;
    private Callback_admin_menuAdmin callback_admin_menuAdmin;
    private Callback_admin_historialCitas callback_admin_historialCitas;
    private Callback_admin_listaEmpleados callback_admin_listaEmpleados;
    private Callback_admin_listaPacientes callback_admin_listaPacientes;
    private Callback_admin_reporteGeneral callback_admin_reporteGeneral;
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
    //controller_admin_HistorialCitas

    public model_Admin(Context context, Callback_admin_historialCitas callback_admin_historialCitas) {
        this.context = context;
        this.callback_admin_historialCitas = callback_admin_historialCitas;
    }
    //controller_admin_listarEmpleados
    public model_Admin(Context context, Callback_admin_listaEmpleados callback_admin_listaEmpleados) {
        this.context = context;
        this.callback_admin_listaEmpleados = callback_admin_listaEmpleados;
    }
    public model_Admin(String matricula, String nombre, String apellidoPaterno, String apellidoMaterno, String email, String telefono, String sexo, String rol) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.email = email;
        this.telefono = telefono;
        this.rol = rol;
        this.sexo = sexo;
    }
    //controller_admin_listarPacientes

    public model_Admin(Context context, Callback_admin_listaPacientes callback_admin_listaPacientes) {
        this.context = context;
        this.callback_admin_listaPacientes = callback_admin_listaPacientes;
    }
    //controller_admin_menuReportes
    public model_Admin(Context context, Callback_admin_reporteGeneral callback_admin_reporteGeneral) {
        this.context = context;
        this.callback_admin_reporteGeneral = callback_admin_reporteGeneral;
    }

    public model_Admin(String reporteId, String reporteDia, String reporteHora, String reporteMotivo, String reporteDescripcion, String reporteEstado, String reporteIdUsuario, String reporteIdMedico, Context context) {
        this.reporteId = reporteId;
        this.reporteDia = reporteDia;
        this.reporteHora = reporteHora;
        this.reporteMotivo = reporteMotivo;
        this.reporteDescripcion = reporteDescripcion;
        this.reporteEstado = reporteEstado;
        this.reporteIdUsuario = reporteIdUsuario;
        this.reporteIdMedico = reporteIdMedico;
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
    public void listarCitasTodas(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Listando citas");
        progressDialog.show();

        String url = molarConfig.getDomainAzure()+"/citas/service_listarCitasAdmin.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String exito =jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");

                    if(jsonArray.length() == 0){
                        System.out.println("model_cita -> historial -> datos vacío");
                        callback_admin_historialCitas.onErrorListar("No hay citas");
                        progressDialog.dismiss();
                    } else if(exito.equals("1")){
                        ArrayList<model_cita> cita = new ArrayList<>();
                        for (int i = 0;i < jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            cita.add(new model_cita(object.getString("id"),object.getString("dia"),object.getString("hora"),object.getString("motivo"),object.getString("estado"),object.getString("descripcion"),object.getString("motivoCancelar"), object.getString("nombre"), object.getString("apellidoPaterno"), object.getString("email"), object.getString("matricula"),object.getString("sexo")));
                        }
                        callback_admin_historialCitas.onSuccessListar(cita);
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    System.out.println("model_general_usuario -> listarCitasActivas -> JSONException: "+e);
                    callback_admin_historialCitas.onErrorListar(e.getMessage());
                    System.out.println("Error");
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("modelcita -> listarCitasCancelar -> onErrorResponse: "+error.getMessage());
                if (error==null){
                    Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
                System.out.println("Error: "+error.getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void listarEmpleados(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Listando pacientes");
        progressDialog.show();

        String url = molarConfig.getDomainAzure()+"/admin/service_listarEmpleados.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String exito =jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (jsonArray.length()==0){
                        callback_admin_listaEmpleados.onErrorLista("No hay pacientes registrados");
                        progressDialog.dismiss();
                    }else if (exito.equals("1")){
                        ArrayList<model_Admin> listaPacientes = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            listaPacientes.add(new model_Admin(object.getString("matricula"),
                                    object.getString("nombre"),
                                    object.getString("apellidoPaterno"),
                                    object.getString("apellidoMaterno"),
                                    object.getString("email"),
                                    object.getString("telefono"),
                                    object.getString("sexo"),
                                    object.getString("rol")));
                        }
                        callback_admin_listaEmpleados.onSuccessLista(listaPacientes);
                        progressDialog.dismiss();
                    }
                }catch (JSONException e){
                    System.out.println("model_assistant -> listaPacientes -> JSONException: "+e.getMessage());
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("model_assistant -> listaPacientes -> onErrorResponse: "+error.getMessage());
                if (error==null){
                    Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
                System.out.println("Error: "+error.getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void listarPacientes(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Listando pacientes");
        progressDialog.show();

        String url = molarConfig.getDomainAzure()+"/admin/service_listarPacientes.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String exito =jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (jsonArray.length()==0){
                        callback_admin_listaPacientes.onErrorLista("No hay pacientes registrados");
                        progressDialog.dismiss();
                    }else if (exito.equals("1")){
                        ArrayList<model_Admin> listaPacientes = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            listaPacientes.add(new model_Admin(object.getString("matricula"),
                                    object.getString("nombre"),
                                    object.getString("apellidoPaterno"),
                                    object.getString("apellidoMaterno"),
                                    object.getString("email"),
                                    object.getString("telefono"),
                                    object.getString("sexo"),
                                    object.getString("rol")));
                        }
                        callback_admin_listaPacientes.onSuccessLista(listaPacientes);
                        progressDialog.dismiss();
                    }
                }catch (JSONException e){
                    System.out.println("model_assistant -> listaPacientes -> JSONException: "+e.getMessage());
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("model_assistant -> listaPacientes -> onErrorResponse: "+error.getMessage());
                if (error==null){
                    Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
                System.out.println("Error: "+error.getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    //controller_admin_reporteGeneral
    public void reporteGeneral(){
        String url = molarConfig.getDomainAzure()+"/admin/service_reporteGeneral.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (jsonArray.length() == 0){
                        callback_admin_menuAdmin.onErrorContrarMenu("");
                    }else{
                        if (exito.equals("1")){
                            String[] datos = new String[8];
                            JSONObject object = jsonArray.getJSONObject(0);
                            datos[0] = object.getString("NumUsuario");
                            JSONObject object1 = jsonArray.getJSONObject(1);
                            datos[1] = object1.getString("NumMedico");
                            JSONObject object2 = jsonArray.getJSONObject(2);
                            datos[2] = object2.getString("NumAsistente");
                            JSONObject object3 = jsonArray.getJSONObject(3);
                            datos[3] = object3.getString("NumCitas");
                            JSONObject object4 = jsonArray.getJSONObject(4);
                            datos[4] = object4.getString("NumCitasAgendadas");
                            JSONObject object5 = jsonArray.getJSONObject(5);
                            datos[5] = object5.getString("NumCitasReagendadas");
                            JSONObject object6 = jsonArray.getJSONObject(6);
                            datos[6] = object6.getString("NumCitasCanceladas");
                            JSONObject object7 = jsonArray.getJSONObject(7);
                            datos[7] = object7.getString("NumCitasTerminadas");
                            callback_admin_reporteGeneral.onSuccessReporte(datos);
                        }else{
                            callback_admin_reporteGeneral.onErrorReporte("No hay datos");
                        }
                    }
                } catch (JSONException e) {
                    System.out.println("model_Patient -> obtenerMedico -> JSONException: "+e);
                    e.printStackTrace();
                    callback_admin_reporteGeneral.onErrorReporte(e.getMessage());
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
                }
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void reporteMes(String mes, String year){
        String url = molarConfig.getDomainAzure()+"/admin/service_reportePorMes.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (jsonArray.length() == 0){
                        callback_admin_reporteGeneral.onErrorReporteMes("No hay datos para hacer el reporte");
                    }else{
                        if (exito.equals("1")){
                            ArrayList<model_Admin>listaCitas = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                listaCitas.add(new model_Admin(object.getString("id"),
                                        object.getString("dia"),
                                        object.getString("hora"),
                                        object.getString("motivo"),
                                        object.getString("descripcion"),
                                        object.getString("estado"),
                                        object.getString("idUsuario"),
                                        object.getString("idMedico"),context));
                            }
                            callback_admin_reporteGeneral.onSuccessReporteMes(listaCitas);
                        }else{
                            callback_admin_reporteGeneral.onErrorReporteMes("No hay datos para hacer el reporte");
                        }
                    }
                } catch (JSONException e) {
                    System.out.println("model_Patient -> obtenerMedico -> JSONException: "+e);
                    e.printStackTrace();
                    callback_admin_reporteGeneral.onErrorReporte(e.getMessage());
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
                }
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mes",mes);
                params.put("year",year);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }


    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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

    public String getPasswordNoCrypt() {
        return passwordNoCrypt;
    }

    public void setPasswordNoCrypt(String passwordNoCrypt) {
        this.passwordNoCrypt = passwordNoCrypt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getReporteId() {
        return reporteId;
    }

    public void setReporteId(String reporteId) {
        this.reporteId = reporteId;
    }

    public String getReporteDia() {
        return reporteDia;
    }

    public void setReporteDia(String reporteDia) {
        this.reporteDia = reporteDia;
    }

    public String getReporteHora() {
        return reporteHora;
    }

    public void setReporteHora(String reporteHora) {
        this.reporteHora = reporteHora;
    }

    public String getReporteMotivo() {
        return reporteMotivo;
    }

    public void setReporteMotivo(String reporteMotivo) {
        this.reporteMotivo = reporteMotivo;
    }

    public String getReporteDescripcion() {
        return reporteDescripcion;
    }

    public void setReporteDescripcion(String reporteDescripcion) {
        this.reporteDescripcion = reporteDescripcion;
    }

    public String getReporteEstado() {
        return reporteEstado;
    }

    public void setReporteEstado(String reporteEstado) {
        this.reporteEstado = reporteEstado;
    }

    public String getReporteIdUsuario() {
        return reporteIdUsuario;
    }

    public void setReporteIdUsuario(String reporteIdUsuario) {
        this.reporteIdUsuario = reporteIdUsuario;
    }

    public String getReporteIdMedico() {
        return reporteIdMedico;
    }

    public void setReporteIdMedico(String reporteIdMedico) {
        this.reporteIdMedico = reporteIdMedico;
    }
}
