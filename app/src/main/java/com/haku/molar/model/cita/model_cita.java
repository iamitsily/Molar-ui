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
import com.haku.molar.model.patient.Callback_patient;
import com.haku.molar.utils.MolarMail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class model_cita {
    String id, dia, hora, motivo, estado, status, idUusario, idMedico, descripcion;
    Context context;
    Callback_cita callback_cita;
    String[] res = new String[6];
    public model_cita() {
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

    public model_cita(String dia, Context context, Callback_cita callback_cita) {
        this.dia = dia;
        this.context = context;
        this.callback_cita = callback_cita;
    }

    public model_cita(String id, String dia, String hora, String motivo, String estado, String status, String idUusario, Context context, Callback_cita callback_cita, String descripcion) {
        this.id = id;
        this.dia = dia;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = estado;
        this.status = status;
        this.idUusario = idUusario;
        this.context = context;
        this.callback_cita = callback_cita;
        this.descripcion = descripcion;
    }

    public void horasCita(){

        String url = "http://192.168.1.71/Molar-Backend/citas/service_citasDia.php";
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
    public void agendarCita(){
        System.out.println("modelCita: "+id + " "+dia+ " "+hora+ " "+motivo+ " "+estado+ " "+status+ " "+idUusario+descripcion);
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Agendando cita");
        progressDialog.show();
        String url = "http://192.168.1.71/Molar-Backend/citas/service_crearcitasPaciente.php";

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
                params.put("descripcion",descripcion);
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
