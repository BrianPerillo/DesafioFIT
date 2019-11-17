package com.example.desafiofluxit.Controller;

import com.example.desafiofluxit.Dao.RandomUserDao;
import com.example.desafiofluxit.Model.Post;
import com.example.desafiofluxit.Model.Result;
import com.example.desafiofluxit.View.ResultListener;

public class RandomUserController {

    RandomUserDao randomUserDao;
    Integer pageSize = 20;
    String seed = " ";

    //Instancia el RandomUserDao en el constructor, - al instanciarse este Controller
    public RandomUserController(){
        randomUserDao = new RandomUserDao();
    }

    // Recibe escuchador de la View, le pide al Dao que ejecute su método getPerfiles y crea un escuchador.
    public void getPerfiles(final ResultListener escuchadorDeLaView){
        randomUserDao.getPerfiles(pageSize, seed, new ResultListener<Post>() {
            @Override
            public void onFinish(Post result) {
                escuchadorDeLaView.onFinish(result);

                seed = result.getInfo().getSeed();


            }
        });
    }

}
