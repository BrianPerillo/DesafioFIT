package com.example.desafiofluxit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desafiofluxit.Controller.PerfilAdapter;
import com.example.desafiofluxit.Controller.RandomUserController;
import com.example.desafiofluxit.Dao.RandomUserDao;
import com.example.desafiofluxit.Model.Login;
import com.example.desafiofluxit.Model.Name;
import com.example.desafiofluxit.Model.Perfil;
import com.example.desafiofluxit.Model.Post;
import com.example.desafiofluxit.Model.Result;
import com.example.desafiofluxit.R;
import com.example.desafiofluxit.View.DetalleDePerfilActivity;
import com.example.desafiofluxit.View.ResultListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PerfilAdapter.PerfilAdapterListener {

    private RecyclerView recyclerViewPerfiles;
    private PerfilAdapter perfilAdapter;
    private TextView datoPrueba;
    private LinearLayoutManager linearLayoutManagerPerfiles;
    private RandomUserController randomUserController;
    private List<Perfil> perfilList;
    private SwipeRefreshLayout swipeRecyclerPerfiles;
    private Boolean booleano = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datoPrueba = findViewById(R.id.datoPrueba);

        recyclerViewPerfiles = findViewById(R.id.recyclerViewPerfiles);

        swipeRecyclerPerfiles = findViewById(R.id.swipeRecyclerPerfiles);

    //------------------------------

        linearLayoutManagerPerfiles = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewPerfiles.setLayoutManager(linearLayoutManagerPerfiles);

        randomUserController = new RandomUserController();

        perfilList = new ArrayList<>();


        getPerfiles();


        perfilAdapter = new PerfilAdapter(perfilList, this);

        recyclerViewPerfiles.setAdapter(perfilAdapter);

        recyclerViewPerfiles.setHasFixedSize(true);

        recyclerViewPerfiles.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                booleano = false;

                Integer items = linearLayoutManagerPerfiles.getItemCount();
                Integer posicionActual = linearLayoutManagerPerfiles.findLastVisibleItemPosition();

                if (posicionActual.equals(items - 4)){

                    getPerfiles();

                }

            }
        });

        swipeRecyclerPerfiles.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                perfilList.clear();
                booleano = true;
                getPerfiles();
                
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRecyclerPerfiles.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    //--------------Fin onCreate--------------------

    public void getPerfiles(){

        randomUserController.getPerfiles(new ResultListener<Post>(){
            @Override
            public void onFinish(Post result) {

                List<Result> resultList = result.getResults();

                for (Result data : resultList) {

                    perfilList.add(new Perfil(data.getLogin().getUsername(), data.getPicture().getThumbnail(), data.getPicture().getLarge(),
                            data.getName().getFirst(), data.getName().getLast(), data.getEmail(), data.getRegistered().getAge()));

                }

                if (result!=null && booleano==false){
                    Toast.makeText(MainActivity.this, "Carga de Perfiles Exitosa", Toast.LENGTH_LONG).show();
                }

                else if (result!=null && booleano==true){
                    Toast.makeText(MainActivity.this, "Actualización Exitosa", Toast.LENGTH_LONG).show();
                }

                perfilAdapter.actualizarLista(perfilList);

            }

        });

    }



    @Override
    public void perfilAdapterListener(Perfil unperfil) {

        Toast.makeText(this, "Perfil seleccionado: " + unperfil.getName(), Toast.LENGTH_SHORT).show();

        Intent intentDetallePerfil = new Intent(this, DetalleDePerfilActivity.class);

        Bundle bundleDatosPerfil = new Bundle();

        bundleDatosPerfil.putSerializable(DetalleDePerfilActivity.PERFIL, unperfil);

        intentDetallePerfil.putExtras(bundleDatosPerfil);

        startActivity(intentDetallePerfil);

    }

}
