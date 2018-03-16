package com.example.marcos.unaspwhatsapp_sqledition;

import com.example.marcos.unaspwhatsapp_sqledition.Model.User;

/**
 * Created by Italo on 05/03/2018.
 */

public class UsuarioLogado {

    static private User user = new User();

    public static void verifyUser(User usuario){

       user = usuario;

    }

    public static User getUser() {
        return user;
    }
}

