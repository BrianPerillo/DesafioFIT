package com.example.desafiofluxit.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.PeriodicSync;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.desafiofluxit.Model.Perfil;
import com.example.desafiofluxit.R;

import java.io.Serializable;

public class DetalleDePerfilActivity extends AppCompatActivity {

    private ImageView imageViewLargeImage;
    private TextView textViewUserName;
    private TextView textViewLastName;
    private TextView textViewLastEdad;
    private TextView textViewUserEmail;

    public static final String PERFIL = "perfil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_de_perfil);

        imageViewLargeImage = findViewById(R.id.imageViewLargeImage);

        textViewUserName = findViewById(R.id.textViewUserName);;

        textViewLastName = findViewById(R.id.textViewLastName);;

        textViewLastEdad = findViewById(R.id.textViewLastEdad);;

        textViewUserEmail = findViewById(R.id.textViewUserEmail);



        Intent intentRecibido = getIntent();

        Bundle bundleRecibido = intentRecibido.getExtras();

        Perfil perfilRecibido = (Perfil) bundleRecibido.getSerializable(PERFIL);


        Glide.with(this)
                .load(perfilRecibido.getLargePic())
                .centerCrop()
                .into(imageViewLargeImage);

        textViewUserName.setText("Nombre: " + perfilRecibido.getName());
        textViewLastName.setText("Apellido: " + perfilRecibido.getLastName());
        textViewLastEdad.setText("Edad: " + perfilRecibido.getEdad());
        textViewUserEmail.setText("Email: " + perfilRecibido.getEmail());

    }
}
