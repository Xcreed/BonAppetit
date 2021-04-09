package com.example.bonappetit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.module.AppGlideModule;
import com.example.bonappetit.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;

import com.example.bonappetit.model.Restaurante;

import java.util.ArrayList;

public class RestauranteAdapter extends FirebaseRecyclerAdapter<
        Restaurante, RestauranteAdapter.RestauranteViewholder> {

    public RestauranteAdapter(
            @NonNull FirebaseRecyclerOptions<Restaurante> options)
    {
        super(options);
    }

    @Override
    protected void
    onBindViewHolder(@NonNull RestauranteViewholder holder,
                     int position, @NonNull Restaurante model)
    {

        String imagenPerfil = model.getImagenPerfil();

        Glide.with(holder.itemView.getContext()).load(imagenPerfil).centerCrop().into(holder.imagen);


        //holder.imagen.setText(model.getImagen());


        holder.nombre.setText(model.getNombre());
        holder.tipoComida.setText(model.getTipoComida());
        holder.rangoPrecio.setText(model.getRangoPrecio());
        holder.ubicacion.setText(model.getUbicacion());
    }

    @NonNull
    @Override
    public RestauranteViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_restaurante, parent, false);
        return new RestauranteAdapter.RestauranteViewholder(view);
    }

    class RestauranteViewholder
            extends RecyclerView.ViewHolder {
        TextView nombre, rangoPrecio, ubicacion, tipoComida;
        ImageView imagen;
        public RestauranteViewholder(@NonNull View itemView)
        {
            super(itemView);


            tipoComida = itemView.findViewById(R.id.tvTipoComida);
            imagen = itemView.findViewById(R.id.ivImagen);
            nombre = itemView.findViewById(R.id.tvNombreRestaurante);
            rangoPrecio = itemView.findViewById(R.id.tvRango);
            ubicacion = itemView.findViewById(R.id.tvUbicacion);
        }
    }
}
