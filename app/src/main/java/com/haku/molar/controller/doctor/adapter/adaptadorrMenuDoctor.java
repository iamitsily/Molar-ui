package com.haku.molar.controller.doctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.haku.molar.R;
import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;
import java.util.Calendar;

public class adaptadorrMenuDoctor extends RecyclerView.Adapter<adaptadorrMenuDoctor.ViewHolder>{
    ArrayList<model_cita> model_citas = new ArrayList<>();
    private ItemClickListener itemClickListener;
    Context context;

    public adaptadorrMenuDoctor(ArrayList<model_cita> model_citas, Context context, ItemClickListener itemClickListener) {
        this.model_citas = model_citas;
        this.itemClickListener = itemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public adaptadorrMenuDoctor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient_menuitem,parent,false);
        return new adaptadorrMenuDoctor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadorrMenuDoctor.ViewHolder holder, int position) {
        holder.dia.setText(divdirDate(model_citas.get(position).getDia()));
        holder.diaString.setText(diaSemana(model_citas.get(position).getDia()));
        holder.hora.setText(model_citas.get(position).getHora());
        holder.medico.setText(model_citas.get(position).getNombreDoctor()+" "+model_citas.get(position).getApellidoDoctor());
        holder.motivo.setText(model_citas.get(position).getMotivo());
        holder.cvItem.setOnClickListener(view -> {
            itemClickListener.OnItemClick(model_citas.get(position));
        });
    }
    public interface ItemClickListener{
        void OnItemClick(model_cita details);
    }
    @Override
    public int getItemCount() {
        return model_citas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dia, diaString, hora, medico, motivo;
        CardView cvItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dia = itemView.findViewById(R.id.recyclerViewListarCitasActivasDia);
            diaString = itemView.findViewById(R.id.recyclerViewListarCitasActivasMes);
            hora = itemView.findViewById(R.id.recyclerViewListarCitasActivasHora);
            medico = itemView.findViewById(R.id.recyclerViewListarCitasActivasAsunto);
            motivo = itemView.findViewById(R.id.recyclerViewMenuPacienteMotivo);
            cvItem = itemView.findViewById(R.id.cvMenuItem);
        }
    }
    public String diaSemana(String date){
        String diaSemn = "";
        String[] dateParts = date.split("-");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month - 1, day);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String[]dias = { "Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb" };
        return dias[dayOfWeek -1];
    }
    public String divdirDate(String date){
        String[] dateParts = date.split("-");
        return dateParts[0];
    }
}
