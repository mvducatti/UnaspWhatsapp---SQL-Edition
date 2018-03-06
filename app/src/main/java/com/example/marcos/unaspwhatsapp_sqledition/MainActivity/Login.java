package com.example.marcos.unaspwhatsapp_sqledition.MainActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marcos.unaspwhatsapp_sqledition.Database.DB;
import com.example.marcos.unaspwhatsapp_sqledition.Model.User;
import com.example.marcos.unaspwhatsapp_sqledition.R;
import com.example.marcos.unaspwhatsapp_sqledition.UserSession;
import com.example.marcos.unaspwhatsapp_sqledition.UsuarioLogado;

import java.sql.ResultSet;
import java.util.HashMap;

import javax.security.auth.login.LoginException;

public class Login extends AppCompatActivity {

    private EditText editEmail, editSenha;

    // User Session Manager Class
    UserSession session;
    private SharedPreferences sharedPreferences;
    private static final String PREFER_NAME = "Reg";

//    // Session Manager Class
//    UsuarioLogado session;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivity);
        editEmail = findViewById(R.id.loginText);
        editSenha = findViewById(R.id.SenhaText);

                    editEmail.setText("mvducatti@gmail.com");
                    editSenha.setText("roketpower");

        // User Session Manager
        session = new UserSession(getApplicationContext());

        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();

        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);


    }

    public void alert(String titulo, String txt){
        AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(txt);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void login(View view){
        ResultSet rs;

        try {
            String testeemail = editEmail.getText().toString();
            String testesenha = editSenha.getText().toString();

            String uName,uEmail = null;
            int uIdUser;

            rs = DB.execute("SELECT * FROM newuser WHERE user_email = '" + testeemail + "' AND user_password = '" + testesenha + "'");
            while (rs.next()){

                String email = rs.getString("user_email");
                String senha = rs.getString("user_password");
                if (email.equals(testeemail)){
                    if (senha.equals(testesenha)){



                        String nome = rs.getString("user_name");
                        int idUser = Integer.parseInt(rs.getString("user_id"));

                        session.createUserLoginSession(nome, email, idUser);

                        HashMap<String, String> alciomar = session.getUserDetails();

                        uName = sharedPreferences.getString("Name", "");
                        uEmail = sharedPreferences.getString("Email", "");
                        uIdUser = sharedPreferences.getInt("IdUser", 0);

                        Log.i("alciomar","ads");

                        Intent intent = new Intent(Login.this, MainActivity.class);

                        User user = new User();
                        user.setId(Integer.parseInt(rs.getString("user_id")));
                        user.setName(rs.getString("user_name"));
                        user.setEmail(email);
                        user.setPassword(senha);
                        UsuarioLogado.verifyUser(user);

                        startActivity(intent);
                        finish();
                        return;
                    }
                }
            }
            throw new LoginException("Usuário ou senha incorretos");
        }catch (LoginException e){
            alert("Erro de Login", e.getMessage());
        }
        catch (Exception e) {
            alert("Erro", e.getMessage());
        }
    }


    public void criarUsuario(View view){
        try {
            Intent intent = new Intent(this, CadastrarUsuario.class);
            startActivity(intent);
        }
        catch (Exception e){
            alert("Erro", e.getMessage());
        }
    }
}

