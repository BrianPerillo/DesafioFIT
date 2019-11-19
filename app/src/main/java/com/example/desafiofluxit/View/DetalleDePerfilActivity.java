package com.example.desafiofluxit.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.PeriodicSync;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.desafiofluxit.Model.Perfil;
import com.example.desafiofluxit.R;
import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;

public class DetalleDePerfilActivity extends AppCompatActivity {

    private ImageView imageViewLargeImage;
    private TextView textViewUserName;
    private TextView textViewLastName;
    private TextView textViewLastEdad;
    private TextView textViewUserEmail;
    private MapsFragment mapsFragment;
    private Double latitudeD;
    private Double longitudeD;
    LinearLayout layout;

    public static final String PERFIL = "perfil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_de_perfil);

        mapsFragment = new MapsFragment();

        //imageViewLargeImage = findViewById(R.id.imageViewLargeImage);

        textViewUserName = findViewById(R.id.textViewUserName);;

        textViewLastName = findViewById(R.id.textViewLastName);;

        textViewLastEdad = findViewById(R.id.textViewLastEdad);;

        textViewUserEmail = findViewById(R.id.textViewUserEmail);

        layout = findViewById(R.id.detalleDePerfilActivity);

        //------------------------------

        Intent intentRecibido = getIntent();

        Bundle bundleRecibido = intentRecibido.getExtras();

        Perfil perfilRecibido = (Perfil) bundleRecibido.getSerializable(PERFIL);


        /*Glide.with(this)
                .load(perfilRecibido.getLargePic())
                .centerCrop()
                .into(imageViewLargeImage);*/

        //-----Bundle coordenadas para MapsFragment

        String latitudeS = perfilRecibido.getLatitude();
        String longitudeS = perfilRecibido.getLongitude();

        latitudeD = Double.valueOf(latitudeS);
        longitudeD = Double.valueOf(longitudeS);

        Bundle bundleConCoordenadas = new Bundle();

        bundleConCoordenadas.putDouble("latitude", latitudeD);
        bundleConCoordenadas.putDouble("longitude", longitudeD);

        mapsFragment.setArguments(bundleConCoordenadas);

        //-----Bundle coordenadas para MapsFragment

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mapdetalle, mapsFragment).commit();

        textViewUserName.setText("Nombre: " + perfilRecibido.getName());
        textViewLastName.setText("Apellido: " + perfilRecibido.getLastName());
        textViewLastEdad.setText("Edad: " + perfilRecibido.getEdad());
        textViewUserEmail.setText("Email: " + perfilRecibido.getEmail());

        Snackbar.make(layout, "Perfil seleccionado: " + perfilRecibido.getUsername(), Snackbar.LENGTH_SHORT).show();

    }
}
