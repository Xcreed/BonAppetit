package com.example.bonappetit.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.module.AppGlideModule;
import com.example.bonappetit.R;
import com.example.bonappetit.RestauranteActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.DataSnapshot;

import com.example.bonappetit.model.Restaurante;

import java.util.ArrayList;
import java.util.List;

public class RestauranteAdapter extends FirebaseRecyclerAdapter<
        Restaurante, RestauranteAdapter.RestauranteViewholder> {

    private OnItemClickListener mItemClickListener;
    private List<Restaurante> items;
    private Context context;

    public RestauranteAdapter(
            @NonNull FirebaseRecyclerOptions<Restaurante> options)
    {

        super(options);

    }

//    public RestauranteAdapter(final Context context, final List<Restaurante> items, final FragmentClicked listener) {
//        this.items = items;
//        this.listener = listener;
//        this.context = context;
//    }

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
        BootstrapLabel nombre;
        AwesomeTextView rangoPrecio, ubicacion, tipoComida;
        BootstrapCircleThumbnail imagen;
        public RestauranteViewholder(@NonNull View itemView)
        {
            super(itemView);


            tipoComida = itemView.findViewById(R.id.tvTipoComida);
            imagen = itemView.findViewById(R.id.ivImagen);
            nombre = itemView.findViewById(R.id.tvNombreRestaurante);
            rangoPrecio = itemView.findViewById(R.id.tvRango);
            ubicacion = itemView.findViewById(R.id.tvUbicacion);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && mItemClickListener != null) {
                        mItemClickListener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(DataSnapshot dataSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}
