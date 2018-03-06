package com.example.marcos.unaspwhatsapp_sqledition;

import android.content.Intent;

import com.example.marcos.unaspwhatsapp_sqledition.Database.DB;
import com.example.marcos.unaspwhatsapp_sqledition.MainActivity.Login;
import com.example.marcos.unaspwhatsapp_sqledition.Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

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

