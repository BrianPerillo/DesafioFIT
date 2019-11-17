package com.example.desafiofluxit.Service;

import com.example.desafiofluxit.Model.Perfil;
import com.example.desafiofluxit.Model.Post;
import com.example.desafiofluxit.Model.Result;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RandomUserService {

    @GET("?results=100")
    Call<Post> getPerfiles();

}
