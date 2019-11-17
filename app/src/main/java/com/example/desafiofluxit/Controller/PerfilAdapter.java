package com.example.desafiofluxit.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.desafiofluxit.MainActivity;
import com.example.desafiofluxit.Model.Perfil;
import com.example.desafiofluxit.R;

import java.util.List;

public class PerfilAdapter extends RecyclerView.Adapter{

    private List<Perfil> perfilList;
    private PerfilAdapterListener listener;

    public PerfilAdapter(List<Perfil> perfilList, PerfilAdapterListener listener) {
        this.perfilList = perfilList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Traigo inflador
        LayoutInflater inflador = LayoutInflater.from(parent.getContext());

        // Inflo vista
        View view = inflador.inflate(R.layout.celdas_perfil, parent, false);

        // Instancio View Holder
        PerfilViewHolder perfilViewHolder = new PerfilViewHolder(view);

        // Retorno Vista
        return perfilViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //Guardo el holder que recibe como parámetro, y lo casteo
        PerfilViewHolder perfilViewHolder = (PerfilViewHolder) holder;
        //Guardo el chat/instanacia de Chat, que se encuentra en la Lista en la posición recibida (position).
        Perfil perfil = perfilList.get(position);
        //Se hace un bind al chatViewHolder y se le pasa el chat
        perfilViewHolder.bind(perfil);
    }

    @Override
    public int getItemCount() {
        return perfilList.size();
    }

    //Método que actualiza la lista recibida.
    public void actualizarLista(List<Perfil> perfilList){
        this.perfilList = perfilList;
        notifyDataSetChanged();
    }

    private class PerfilViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageViewCeldasPerfilThumbnail;
        private TextView textViewCeldasPerfilNombre;
        View myView;

        public PerfilViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewCeldasPerfilThumbnail = itemView.findViewById(R.id.imageViewCeldasPerfilThumbnail);
            textViewCeldasPerfilNombre = itemView.findViewById(R.id.textViewCeldasPerfilNombre);
            this.myView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer adapterPosition = getAdapterPosition();
                    Perfil perfil = perfilList.get(adapterPosition);
                    listener.perfilAdapterListener(perfil);

                }
            });

        }

        public void bind (Perfil unperfil){
            //PASAR IMAGEN CON GLIDE => imageViewCeldasPerfilThumbnail.setDra(unchat.getNombre());

            textViewCeldasPerfilNombre.setText(unperfil.getUsername());

            Glide.with(itemView)
                    .load(unperfil.getThumbnail())
                    .centerCrop()
                    .into(imageViewCeldasPerfilThumbnail);

        }


    }

    public interface PerfilAdapterListener {

        public void perfilAdapterListener(Perfil unperfil);

    }

}
