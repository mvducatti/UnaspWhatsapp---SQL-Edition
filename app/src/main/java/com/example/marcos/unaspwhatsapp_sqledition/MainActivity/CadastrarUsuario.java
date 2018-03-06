package com.example.marcos.unaspwhatsapp_sqledition.MainActivity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.marcos.unaspwhatsapp_sqledition.Database.DB;
import com.example.marcos.unaspwhatsapp_sqledition.Database.DBUsuario;
import com.example.marcos.unaspwhatsapp_sqledition.Model.User;
import com.example.marcos.unaspwhatsapp_sqledition.R;

public class CadastrarUsuario extends AppCompatActivity {

    private DBUsuario dbUsuario;
    private EditText editTextCDEmail;
    private EditText editTextCDSenha;
    private EditText editTextCDNome;
    private EditText PIN;
    private User user;

    public void alert(String titulo, String txt){
        AlertDialog alertDialog = new AlertDialog.Builder(CadastrarUsuario.this).create();
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
        setContentView(R.layout.activity_cadastrar_usuario);

        dbUsuario = new DBUsuario();

        editTextCDNome = findViewById(R.id.editTextCDNome);
        editTextCDEmail = findViewById(R.id.editTextCDEmail);
        editTextCDSenha = findViewById(R.id.editTextCDSenha);
        PIN = findViewById(R.id.editTextPIN);
        editTextCDNome.setText("marcos");
        editTextCDEmail.setText("mvducatti");
        editTextCDSenha.setText("roketpower");
        PIN.setText("3705");

    }

    public void salvar(View view) {
        try {

            if (PIN.getText().toString().equals("3705")) {
                String nomeusuario = editTextCDNome.getText().toString();
                String loginusuario = editTextCDEmail.getText().toString();
                String senhausuario = editTextCDSenha.getText().toString();

                if (!(editTextCDNome.getText().toString().equals("") || editTextCDNome.getText() == null ||
                        editTextCDEmail.getText().toString().equals("") || editTextCDEmail.getText() == null ||
                        editTextCDSenha.getText().toString().equals("") || editTextCDSenha.getText() == null

                )) {
                    dbUsuario.setNome(nomeusuario);
                    dbUsuario.setEmail(loginusuario);
                    dbUsuario.setSenha(senhausuario);

                    dbUsuario.salvar(PIN.getText().toString());
                    user = new User();


                    alert("CRIAÇÃO DE USUÁRIO", "Usuário " + nomeusuario + " criado com sucesso!");

                    editTextCDNome.setText("");
                    editTextCDEmail.setText("");
                    editTextCDSenha.setText("");
                    PIN.setText("");
                }
            } else {
                PIN.setError("PIN Incorreto");
            }
        }
        catch (Exception e){
            alert("Erro", e.getMessage());
        }
    }

    public void cdCancelar(View view) {
        finish();
    }
}


