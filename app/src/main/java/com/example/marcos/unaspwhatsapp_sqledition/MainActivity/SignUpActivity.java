package com.example.marcos.unaspwhatsapp_sqledition.MainActivity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.marcos.unaspwhatsapp_sqledition.PHP.Constants;
import com.example.marcos.unaspwhatsapp_sqledition.PHP.RequestHandler;
import com.example.marcos.unaspwhatsapp_sqledition.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {


    private TextInputEditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextUsername = findViewById(R.id.editTextLoginUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextLoginPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        bar = findViewById(R.id.progressBar);
        bar.setVisibility(View.GONE);
    }

    public void registerUser(View view) {

        final String username = editTextUsername.getText().toString();
        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();
        final String confirmPassword = editTextConfirmPassword.getText().toString();

        if (password.equals(confirmPassword) || confirmPassword.equals(password)) {

            bar.setVisibility(View.VISIBLE);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    bar.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"),
                                Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    bar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Erro: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };

            RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(getApplicationContext(), "Senhas não conferem, tente novamente", Toast.LENGTH_LONG).show();
        }

    }
}
