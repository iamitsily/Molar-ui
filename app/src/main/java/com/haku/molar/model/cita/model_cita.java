package com.haku.molar.model.cita;

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
import com.haku.molar.controller.patient.controller_patient_HistorialCitasLayout;
import com.haku.molar.model.patient.Callback_patient;
import com.haku.molar.utils.MolarMail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class model_cita {
    String id, dia, hora, motivo, estado, status, idUusario, idMedico, descripcion,idPos,idDoctor;
    Context context;
    Callback_cita callback_cita;
    String[] res = new String[6];
    public model_cita() {
    }
    public model_cita(Context context, Callback_cita callback_cita){
        this.context = context;
        this.callback_cita = callback_cita;
    }
    public model_cita(String id, String dia, String hora, String motivo, String estado, String status, String idUusario, String idMedico) {
        this.id = id;
        this.dia = dia;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = estado;
        this.status = status;
        this.idUusario = idUusario;
        this.idMedico = idMedico;
    }

    public model_cita(String dia, Context context, Callback_cita callback_cita, String idPos) {
        this.dia = dia;
        this.context = context;
        this.callback_cita = callback_cita;
        this.idPos = idPos;
    }

    public model_cita(String id, String dia, String hora, String motivo, String estado, String status, String idUusario,String idMedico, Context context, Callback_cita callback_cita, String descripcion) {
        this.id = id;
        this.dia = dia;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = estado;
        this.status = status;
        this.idUusario = idUusario;
        this.context = context;
        this.idMedico = idMedico;
        this.callback_cita = callback_cita;
        this.descripcion = descripcion;
    }
    public model_cita(String id, String dia, String hora, String motivo, String estado, String descripcion) {
        this.id = id;
        this.dia = dia;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = estado;
        this.descripcion = descripcion;
    }

    public model_cita(String idUusario, controller_patient_HistorialCitasLayout controller_patient_historialCitasLayout, controller_patient_HistorialCitasLayout controller_patient_historialCitasLayout1) {
        this.idUusario = idUusario;
        this.context = controller_patient_historialCitasLayout;
        this.callback_cita = (Callback_cita) controller_patient_historialCitasLayout1;
    }

    public void horasCita(){

        String url = "https://molarservices.azurewebsites.net/citas/service_citasDia.php";
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Revisando disponibilidad");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (exito.equals("1")){
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String hora = object.getString("hora");
                            res[i] = hora;
                        }
                        callback_cita.onSuccesshoraCitas(res);
                        progressDialog.dismiss();
                    }
                }catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("model_cita -> horasCita -> onErrorResponse: "+error.getMessage());
                progressDialog.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("dia",getDia());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void listarCitas(){
        System.out.println("modelCita: "+idUusario);
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando historial");
        progressDialog.show();
        String url = "https://molarservices.azurewebsites.net/citas/service_listarCitasPaciente.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String exito =jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");

                    if(jsonArray.length() == 0){
                        System.out.println("model_cita -> historial -> datos vacío");
                        Toast.makeText(context, "Historial vacio, no se han encontrado registros", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else if(exito.equals("1")){
                        ArrayList<model_cita> cita = new ArrayList<>();
                        System.out.println(jsonArray.length());
                        for (int i = 0;i < jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            cita.add(new model_cita(object.getString("id"),object.getString("dia"),object.getString("hora"),object.getString("motivo"),object.getString("estado"),object.getString("descripcion")));
                        }

                        callback_cita.onSuccessHistorial(cita);
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    System.out.println("model_general_usuario -> login -> JSONException: "+e);
                    callback_cita.onErrorhoraHistorial(e.getMessage());
                    System.out.println("Error");
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Error: "+error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("matricula",idUusario);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void agendarCita(){
        System.out.println("modelCita: "+id + " "+dia+ " "+hora+ " "+motivo+ " "+estado+ " "+status+ " "+idUusario+" "+idMedico+" "+descripcion);
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Agendando cita");
        progressDialog.show();
        String url = "https://molarservices.azurewebsites.net/citas/service_crearcitasPaciente.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Registro exitoso")) {
                    Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    callback_cita.onSuccessAgendarCita();
                } else {
                    Toast.makeText(context, "No se puede registrar", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    callback_cita.onErrorAgendarCita("No se puede registrar");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Error: "+error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",id);
                params.put("dia",dia);
                params.put("hora",hora);
                params.put("motivo",motivo);
                params.put("estado",estado);
                params.put("status",status);
                params.put("idUsuario",idUusario);
                params.put("idMedico",idMedico);
                params.put("descripcion",descripcion);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void obtenerNumDoctor(){
        String url = "https://molarservices.azurewebsites.net/doctor/service_contarDoctor.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (jsonArray.length() == 0){
                        Toast.makeText(context, "No hay medicos", Toast.LENGTH_SHORT).show();
                    }else{
                        if (exito.equals("1")){
                            JSONObject object = jsonArray.getJSONObject(0);
                            String num = object.getString("Num");
                            System.out.println("CountDoctor: "+num);
                            callback_cita.onSuccessNumDoctor(num);
                        }
                    }
                } catch (JSONException e) {
                    System.out.println("model_Patient -> obtenerMedico -> JSONException: "+e);
                    e.printStackTrace();
                    callback_cita.onErrorNumDoctor(e.getMessage());
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
    public void disponibilidadDoctor(){
        String url = "https://molarservices.azurewebsites.net/doctor/service_disponibilidad.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    if (exito.equals("1")){
                        JSONArray jsonArray = jsonObject.getJSONArray("datos");
                        JSONObject object = jsonArray.getJSONObject(0);
                        String matricula = object.getString("matricula");
                        callback_cita.onSuccessdisponibilidadDoctor(matricula);
                    }else{
                        System.out.println("No hay exito en disponibilidadDoctor");
                        callback_cita.onErrordisponibilidadDoctor("");
                    }
                }catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("model_cita -> horasCita -> onErrorResponse: "+error.getMessage());
                callback_cita.onErrordisponibilidadDoctor(error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                System.out.println("idPos enviado: "+idPos);
                params.put("idPos",idPos);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdUusario() {
        return idUusario;
    }

    public void setIdUusario(String idUusario) {
        this.idUusario = idUusario;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Callback_cita getCallback_cita() {
        return callback_cita;
    }

    public void setCallback_cita(Callback_cita callback_cita) {
        this.callback_cita = callback_cita;
    }
}
