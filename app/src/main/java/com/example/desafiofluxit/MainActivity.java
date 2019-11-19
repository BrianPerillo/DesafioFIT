package com.example.desafiofluxit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.PeriodicSync;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
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
import com.example.desafiofluxit.View.MapsFragment;
import com.example.desafiofluxit.View.ResultListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PerfilAdapter.PerfilAdapterListener, SearchView.OnQueryTextListener {

    private RecyclerView recyclerViewPerfiles;
    private PerfilAdapter perfilAdapter;
    //private TextView datoPrueba;
    private LinearLayoutManager linearLayoutManagerPerfiles;
    private RandomUserController randomUserController;
    private List<Perfil> perfilList;
    private SwipeRefreshLayout swipeRecyclerPerfiles;
    private Boolean booleano = false;
    private Integer page = 1;
    private ProgressBar progressBar;
    //private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //datoPrueba = findViewById(R.id.datoPrueba);

        recyclerViewPerfiles = findViewById(R.id.recyclerViewPerfiles);

        swipeRecyclerPerfiles = findViewById(R.id.swipeRecyclerPerfiles);

        progressBar = findViewById(R.id.progressBar);


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

                    page++;

                    getPerfiles();

                }

            }
        });

        swipeRecyclerPerfiles.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                perfilList.clear();
                booleano = true;
                page = 1;

                recyclerViewPerfiles.setVisibility(View.INVISIBLE);

                getPerfiles();

                recyclerViewPerfiles.setVisibility(View.VISIBLE);

            }
        });

    }

    //--------------Fin onCreate--------------------



    public void getPerfiles(){

        if(booleano==true){
            progressBar.setVisibility(View.GONE);
        }

        else{
            progressBar.setVisibility(View.VISIBLE);
        }

        randomUserController.getPerfiles(page, new ResultListener<Post, String>(){
            @Override
            public void onFinish(Post result, String error) {

                if (result!=null){

                List<Result> resultList = result.getResults();

                for (Result data : resultList) {

                    perfilList.add(new Perfil(data.getLogin().getUsername(), data.getPicture().getThumbnail(), data.getPicture().getLarge(),
                            data.getName().getFirst(), data.getName().getLast(), data.getEmail(), data.getRegistered().getAge()));

                }


                if (result!=null && booleano==false){
                    Toast.makeText(MainActivity.this, "Carga de Perfiles Exitosa", Toast.LENGTH_SHORT).show();
                }

                else if (result!=null && booleano==true){
                    Toast.makeText(MainActivity.this, "Actualización Exitosa", Toast.LENGTH_SHORT).show();
                }

                perfilAdapter.actualizarLista(perfilList);


                swipeRecyclerPerfiles.setRefreshing(false);
                }

                else if (error.length()!=0){
                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.GONE);

            }

        });

    }



  /*  @Override
    public void perfilAdapterListener(Perfil unperfil) {

        Toast.makeText(this, "Perfil seleccionado: " + unperfil.getName(), Toast.LENGTH_SHORT).show();

        Intent intentDetallePerfil = new Intent(this, DetalleDePerfilActivity.class);

        Bundle bundleDatosPerfil = new Bundle();

        bundleDatosPerfil.putSerializable(DetalleDePerfilActivity.PERFIL, unperfil);

        intentDetallePerfil.putExtras(bundleDatosPerfil);

        startActivity(intentDetallePerfil);

    }*/

    @Override
    public void perfilAdapterListener(Perfil unperfil) {

        Toast.makeText(this, "Perfil seleccionado: " + unperfil.getName(), Toast.LENGTH_SHORT).show();

        Intent intentDetallePerfil = new Intent(this, DetalleDePerfilActivity.class);

        Bundle bundleDatosPerfil = new Bundle();

        bundleDatosPerfil.putSerializable(DetalleDePerfilActivity.PERFIL, unperfil);

        intentDetallePerfil.putExtras(bundleDatosPerfil);

        startActivity(intentDetallePerfil);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_buscador, menu);
        MenuItem item = menu.findItem(R.id.buscador);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                perfilAdapter.setFilter(perfilList);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        try{

            List<Perfil> listaFiltrada = filter(perfilList, newText);
            perfilAdapter.setFilter(listaFiltrada);

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    private List<Perfil> filter(List<Perfil> perfiles, String texto){

        List<Perfil>listaFiltrada=new ArrayList<>();

        try{

            texto = texto.toLowerCase();

            for (Perfil perfil : perfiles){
                String nota2 = perfil.getUsername().toLowerCase();

                if(nota2.contains(texto)){
                    listaFiltrada.add(perfil);
                }

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return listaFiltrada;

    }
}
