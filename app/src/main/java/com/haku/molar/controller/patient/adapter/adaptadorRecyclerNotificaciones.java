package com.haku.molar.controller.patient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haku.molar.R;
import com.haku.molar.model.notificaciones.model_General_Notificaciones;

import java.util.ArrayList;

public class adaptadorRecyclerNotificaciones extends RecyclerView.Adapter<adaptadorRecyclerNotificaciones.ViewHolder> {
    ArrayList<model_General_Notificaciones> model_General_Notificaciones = new ArrayList<>();

    public adaptadorRecyclerNotificaciones(ArrayList<model_General_Notificaciones> model_General_Notificaciones) {
        this.model_General_Notificaciones = model_General_Notificaciones;
    }

    @NonNull
    @Override
    public adaptadorRecyclerNotificaciones.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient_notificacionesitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadorRecyclerNotificaciones.ViewHolder holder, int position) {

        String FechaCompleta = model_General_Notificaciones.get(position).getFecha();
        String dia = FechaCompleta.substring(4,6);

        String mescompleto = model_General_Notificaciones.get(position).getFecha();
        String mes = mescompleto.substring(0,3);

        //holder.dia.setText(model_notificaciones.get(position).getFecha());
        holder.dia.setText(dia);
        //holder.mes.setText(model_notificaciones.get(position).getFecha());
        holder.mes.setText(mes);
        holder.mensaje.setText(model_General_Notificaciones.get(position).getMensaje());

    }

    @Override
    public int getItemCount() {
        return model_General_Notificaciones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dia, mes , mensaje;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dia = (TextView) itemView.findViewById(R.id.itemNotificacionesPatient_dia);
            mes = (TextView) itemView.findViewById(R.id.itemNotificacionesPatient_mes);
            mensaje = (TextView) itemView.findViewById(R.id.itemNotificacionesPatient_mensaje);
        }
    }



}
