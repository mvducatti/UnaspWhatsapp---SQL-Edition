package com.example.marcos.unaspwhatsapp_sqledition.MainActivity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marcos.unaspwhatsapp_sqledition.Database.DBNoticias;
import com.example.marcos.unaspwhatsapp_sqledition.Database.DBUsuario;
import com.example.marcos.unaspwhatsapp_sqledition.Model.Noticia;
import com.example.marcos.unaspwhatsapp_sqledition.Model.User;
import com.example.marcos.unaspwhatsapp_sqledition.R;

public class PostNews extends AppCompatActivity {

    private DBNoticias dbNoticias;
    private Button btnpostar;
    private EditText editTextCDNewsTitle;
    private EditText editTextCDNewsPost;
    private Noticia noticia;

    public void alert(String titulo, String txt){
        AlertDialog alertDialog = new AlertDialog.Builder(PostNews.this).create();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_news);

        btnpostar = findViewById(R.id.btn_postar);
        dbNoticias = new DBNoticias();

        editTextCDNewsTitle = findViewById(R.id.EditTextNewsTitle);
        editTextCDNewsPost = findViewById(R.id.EditTextNewsPost);

    }

    public void salvarNoticia(View view) {
        try {
            {
                String newsTitle = editTextCDNewsTitle.getText().toString();
                String newsPost = editTextCDNewsPost.getText().toString();

                if (!(editTextCDNewsTitle.getText().toString().equals("") || editTextCDNewsTitle.getText() == null ||
                        editTextCDNewsPost.getText().toString().equals("") || editTextCDNewsPost.getText() == null

                )) {
                    dbNoticias.setNewsTitle(newsTitle);
                    dbNoticias.setNewsPost(newsPost);

                    dbNoticias.salvar();
                    noticia = new Noticia();


                    Toast.makeText(getApplicationContext(), "Notícia postada com sucesso",
                            Toast.LENGTH_LONG).show();

                    editTextCDNewsTitle.setText("");
                    editTextCDNewsPost.setText("");
                }

            }
        }
        catch (Exception e){
            alert("Erro", e.getMessage());
        }
    }

}
