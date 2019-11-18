package com.example.desafiofluxit.Dao;

import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.desafiofluxit.Model.Perfil;
import com.example.desafiofluxit.Model.Post;
import com.example.desafiofluxit.Model.Result;
import com.example.desafiofluxit.Service.RandomUserService;
import com.example.desafiofluxit.View.ResultListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RandomUserDao {

    private Retrofit retrofit;
    private String baseUrl;
    private RandomUserService randomUserService;

    public RandomUserDao(){
        baseUrl = "https://randomuser.me/";
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        randomUserService = retrofit.create(RandomUserService.class);
    }

    public void getPerfiles(Integer pageSize, String seed, Integer page, final ResultListener<Post> escuchadorDelControlador){

        //Al hacerle .getPerfiles le digo ejecuta el método getPerfiles del Service


        randomUserService.getPerfiles(pageSize, seed, page).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                Post perfil = response.body();
                escuchadorDelControlador.onFinish(perfil);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                t.printStackTrace();
            }
        });

    }


}
