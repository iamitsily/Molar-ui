package com.haku.molar.model.notificaciones;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haku.molar.utils.MolarConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class model_General_Notificaciones {
    String idNotificacion, mensaje, leida, fecha, matricula;
    Context context;
    Callback_notificaciones callback_notificaciones;
    MolarConfig molarConfig = new MolarConfig();

    public model_General_Notificaciones() {
    }

    public model_General_Notificaciones(String matricula, Context context, Callback_notificaciones callback_notificaciones) {
        this.context = context;
        this.callback_notificaciones = callback_notificaciones;
        this.matricula= matricula;
    }

    public model_General_Notificaciones(String idNotificacion, String mensaje, String leida, String fecha, String matricula) {
        this.idNotificacion = idNotificacion;
        this.mensaje = mensaje;
        this.leida = leida;
        this.fecha = fecha;
        this.matricula = matricula;
    }

    public void listarNotificaciones() {
        System.out.println("modelNotificacion: " + matricula);
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando notificaciones");
        progressDialog.show();
//        String url = molarConfig.getDomainAzure() + "/patient/service_listarNotificaciones.php";
        String url = "http://192.168.0.107/Molar-Backend/patient/service_listarNotificaciones.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (!(jsonArray.length()==0)){
                        ArrayList<model_General_Notificaciones> notificaciones = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String idNotificacion = object.getString("IdNotificacion");
                            String mensaje = object.getString("Mensaje");
                            String leida = object.getString("Leida");
                            String fecha = object.getString("Fecha");
                            String matricula = object.getString("matricula");
                            notificaciones.add(new model_General_Notificaciones(idNotificacion, mensaje, leida, fecha, matricula));
                            System.out.println(idNotificacion + " "+ mensaje + " "+ leida + " "+ fecha + " "+ matricula);
                            progressDialog.dismiss();
                        }
                        callback_notificaciones.onSuccessNotificaciones(notificaciones);
                    }else{
                        progressDialog.dismiss();
                        callback_notificaciones.onErrorNotificaciones("No hay notis");
                    }
                } catch (JSONException e) {
                    System.out.println("model_notificaciones -> listarNotificaciones -> JSONException: " + e);
                    callback_notificaciones.onErrorNotificaciones(e.getMessage());
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                callback_notificaciones.onErrorNotificaciones(error.getMessage());
                Log.e("ModelNotificaciones", "listarNotificaciones -> VolleyError: " + error.getMessage());
            }
        }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("matricula", matricula);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }


    // Getters and Setters

    public String getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(String idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getLeida() {
        return leida;
    }

    public void setLeida(String leida) {
        this.leida = leida;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Callback_notificaciones getCallback_notificaciones() {
        return callback_notificaciones;
    }

    public void setCallback_notificaciones(Callback_notificaciones callback_notificaciones) {
        this.callback_notificaciones = callback_notificaciones;
    }
}
