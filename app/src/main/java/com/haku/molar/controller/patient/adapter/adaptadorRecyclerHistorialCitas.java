package com.haku.molar.controller.patient.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haku.molar.R;
import com.haku.molar.controller.patient.*;
import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;
import java.util.Calendar;

public class adaptadorRecyclerHistorialCitas extends RecyclerView.Adapter<adaptadorRecyclerHistorialCitas.ViewHolder> {
    ArrayList<model_cita> model_citas = new ArrayList<>();
    String matricula, nombre, rol;
    private ItemClickListenerHistorialCitas itemClickListenerHistorialCitas;
    Context context;

    public adaptadorRecyclerHistorialCitas(ArrayList<model_cita> model_citas,String matricula,String nombre,String rol,Context context, ItemClickListenerHistorialCitas itemClickListenerHistorialCitas) {
        this.model_citas = model_citas;
        this.matricula = matricula;
        this.nombre = nombre;
        this.rol = rol;
        this.context = context;
        this.itemClickListenerHistorialCitas = itemClickListenerHistorialCitas;
    }


    @NonNull
    @Override
    public adaptadorRecyclerHistorialCitas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_historial_citas,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadorRecyclerHistorialCitas.ViewHolder holder, int position) {
        holder.motivo.setText(model_citas.get(position).getMotivo());
        holder.fecha.setText("Fecha: "+model_citas.get(position).getDia());
        holder.folio.setText("Folio: "+model_citas.get(position).getId());
        holder.status.setOnClickListener(view -> {
            itemClickListenerHistorialCitas.OnItemClick(model_citas.get(position));
        });
        switch(model_citas.get(position).getEstado()){
            case "0": //Terminada
                holder.estado.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#379416")));
                holder.estado.setTextColor(0xFFFFFFFF);
                holder.estado.setText("Terminada");
                break;
            case "1": //Pendiente
                holder.estado.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9DC03")));
                holder.estado.setTextColor(Color.parseColor("#FFFFFF"));
                holder.estado.setText("Pendiente");
                break;
            case "2": //Cancelada
                holder.estado.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D40055")));
                holder.estado.setTextColor(0xFFFFFFFF);
                holder.estado.setText("Cancelada");
                break;
            case "3": //Reagendada
                //holder.estado.setBackgroundColor(Color.GRAY);
                holder.estado.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C0C0C0")));
                holder.estado.setTextColor(0xFFFFFFFF);
                holder.estado.setText("Reagendada");
                break;
            case "4": //Pendiente a cancelar
                holder.estado.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1C6BA4")));
                holder.estado.setTextColor(0xFFFFFFFF);
                holder.estado.setTextSize(18);
                holder.estado.setText("Pendiente de cancelación");
                break;
            default:
                //holder.estado.setBackgroundColor(Color.GRAY);
                holder.estado.setTextColor(0xFFFFFFFF);
                holder.estado.setText("Desconocido");
                break;
        }
    }
    public interface ItemClickListenerHistorialCitas{
        void OnItemClick(model_cita details);
    }
    @Override
    public int getItemCount() { return model_citas.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fecha,folio,estado, motivo;
        Button status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            motivo = (TextView) itemView.findViewById(R.id.itemGHC_tv1);
            folio = (TextView) itemView.findViewById(R.id.itemGHC_tv2);
            fecha = (TextView) itemView.findViewById(R.id.itemGHC_tv3);
            estado = (TextView) itemView.findViewById(R.id.itemGHC_tv5);
            status = (Button) itemView.findViewById(R.id.itemGHC_btn1);
        }
    }
}
