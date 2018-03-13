package com.example.marcos.unaspwhatsapp_sqledition.MainActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.example.marcos.unaspwhatsapp_sqledition.Database.DBUsuario;
import com.example.marcos.unaspwhatsapp_sqledition.Model.User;
import com.example.marcos.unaspwhatsapp_sqledition.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class CadastrarUsuario extends AppCompatActivity {

    private DBUsuario dbUsuario;
    private EditText editTextCDEmail;
    private EditText editTextCDSenha;
    private EditText editTextCDNome;
    private EditText PIN;
    private ImageButton saveProfilePictureButton;
    private String image_str;
    private ImageButton savePhotoFromCamera;
    private User user;

    //YOU CAN EDIT THIS TO WHATEVER YOU WANT
    private static final int ACTIVITY_SELECT_IMAGE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;

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

        saveProfilePictureButton = findViewById(R.id.imageProfileButton);
        savePhotoFromCamera = findViewById(R.id.btnTakePicture);

        editTextCDNome = findViewById(R.id.editTextCDNome);
        editTextCDEmail = findViewById(R.id.editTextCDEmail);
        editTextCDSenha = findViewById(R.id.editTextCDSenha);
        PIN = findViewById(R.id.editTextPIN);
        editTextCDNome.setText("marcos");
        editTextCDEmail.setText("mvducatti");
        editTextCDSenha.setText("roketpower");
        PIN.setText("3705");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {

//        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            savePhotoFromCamera.setImageBitmap(imageBitmap);
//        }

        switch(requestCode) {
            case ACTIVITY_SELECT_IMAGE:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        alert("VIIIXII", "Algo deu errado: " + e);
                    }

                    Bitmap bitmap = null;
                    try {
                        Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);

                        //Change image to blob and select what kind of file to compress to
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
                        byte [] byte_arr = stream.toByteArray();
                        image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);

                        bitmap = null;
                    } catch (Exception e) {
                        alert("VIIIXII V.3", "Algo deu errado na hora de postar a foto: " + e);
                    }

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        alert("VIIIXII V.2", "Algo deu errado na hora de postar a foto: " + e);
                    }
                    Drawable d = new BitmapDrawable(getResources(),bitmap);
                    saveProfilePictureButton.setBackground(d);
                }
        }
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

                    dbUsuario.setPhoto(Byte.parseByte(image_str));
                    dbUsuario.setNome(nomeusuario);
                    dbUsuario.setEmail(loginusuario);
                    dbUsuario.setSenha(senhausuario);

                    dbUsuario.salvar(PIN.getText().toString());
                    user = new User();


                    alert("CRIAÇÃO DE USUÁRIO", "Usuário " + nomeusuario + " criado com sucesso!");

                    saveProfilePictureButton.setBackgroundResource(R.drawable.ic_menu_gallery);
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
            alert("ERROUUU", e.getMessage());
        }
    }



    public void dispatchTakePictureIntent(View view) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void saveImage (View view) {

        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, ACTIVITY_SELECT_IMAGE);
    }

    public void cdCancelar(View view) {
        finish();
    }
}


