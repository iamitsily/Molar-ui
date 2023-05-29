package com.haku.molar.controller.patient.adapter;

import android.content.Context;
import android.content.Intent;
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
    Context context;

    public adaptadorRecyclerHistorialCitas(ArrayList<model_cita> model_citas,String matricula,String nombre,String rol,Context context) {
        this.model_citas = model_citas;
        this.matricula = matricula;
        this.nombre = nombre;
        this.rol = rol;
        this.context = context;
    }


    @NonNull
    @Override
    public adaptadorRecyclerHistorialCitas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_historial_citas,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadorRecyclerHistorialCitas.ViewHolder holder, int position) {
        holder.fecha.setText(model_citas.get(position).getDia());
        holder.folio.setText(model_citas.get(position).getId());
        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, controller_patient_DetallesCitaPaciente.class);
                i.putExtra("matricula",matricula);
                i.putExtra("nombre",nombre);
                i.putExtra("rol",rol);
                context.startActivity(i);
            }
        });
        switch(model_citas.get(position).getEstado()){
            case "0": //Terminada
                holder.estado.setBackgroundColor(0xFF008000);
                holder.estado.setTextColor(0xFFFFFFFF);
                holder.estado.setText("Terminada");
                break;
            case "1": //Pendiente
                holder.estado.setBackgroundColor(0xFFFFFF00);
                holder.estado.setText("Pendiente");
                break;
            case "2": //Cancelada
                holder.estado.setBackgroundColor(0xFFDD0000);
                holder.estado.setTextColor(0xFFFFFFFF);
                holder.estado.setText("Cancelada");
                break;
            case "3": //Reagendada
                holder.estado.setBackgroundColor(Color.GRAY);
                holder.estado.setTextColor(0xFFFFFFFF);
                holder.estado.setText("Reagendada");
                break;
            case "4": //Pendiente a cancelar
                holder.estado.setBackgroundColor(Color.DKGRAY);
                holder.estado.setTextColor(0xFFFFFFFF);
                holder.estado.setTextSize(18);
                holder.estado.setText("Pendiente de cancelación");
                break;
            default:
                holder.estado.setBackgroundColor(Color.GRAY);
                holder.estado.setTextColor(0xFFFFFFFF);
                holder.estado.setText("Desconocido");
                break;
        }
    }

    @Override
    public int getItemCount() { return model_citas.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fecha,folio,estado;
        Button status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            folio = (TextView) itemView.findViewById(R.id.itemGHC_tv2);
            fecha = (TextView) itemView.findViewById(R.id.itemGHC_tv3);
            estado = (TextView) itemView.findViewById(R.id.itemGHC_tv5);
            status = (Button) itemView.findViewById(R.id.itemGHC_btn1);
        }
    }
}
