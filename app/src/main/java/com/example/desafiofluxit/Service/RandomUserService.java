package com.example.desafiofluxit.Service;

import com.example.desafiofluxit.Model.Perfil;
import com.example.desafiofluxit.Model.Post;
import com.example.desafiofluxit.Model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RandomUserService {

    @GET("api/")
    Call<Post> getPerfiles(@Query("results") Integer pageSize, @Query("seed") String seed);

}
