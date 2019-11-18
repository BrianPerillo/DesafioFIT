package com.example.desafiofluxit.Controller;

import com.example.desafiofluxit.Dao.RandomUserDao;
import com.example.desafiofluxit.Model.Post;
import com.example.desafiofluxit.Model.Result;
import com.example.desafiofluxit.View.ResultListener;

public class RandomUserController {

    private RandomUserDao randomUserDao;
    private Integer pageSize = 20;
    private String seed = "";



    public RandomUserController(){
        randomUserDao = new RandomUserDao();
    }


    public void getPerfiles(Integer page, final ResultListener escuchadorDeLaView){
        randomUserDao.getPerfiles(pageSize, seed, page, new ResultListener<Post>() {
            @Override
            public void onFinish(Post result) {
                escuchadorDeLaView.onFinish(result);

                seed = result.getInfo().getSeed();


            }
        });
    }



}
