package com.retailvoice.sellerapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.retailvoice.sellerapp.ApiClient;
import com.retailvoice.sellerapp.ApiInterface;
import com.retailvoice.sellerapp.R;
import com.retailvoice.sellerapp.SessionManager;
import com.retailvoice.sellerapp.models.AuthResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity{
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister, btnLinkToLogin;
    private EditText etEmail, etPassword;
    private ProgressDialog pDialog;
    private SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(SignupActivity.this,
                    RegisterActivity.class);
            startActivity(intent);
            finish();
        }

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    register(email, password);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void register(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Registering ...");
        showDialog();

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<AuthResult> call = apiService.register(email, password);
        call.enqueue(new Callback<AuthResult>() {
            @Override
            public void onResponse(Call<AuthResult> call, Response<AuthResult> response) {
                int statusCode = response.code();
                AuthResult result = response.body();
                Boolean success = result.getSuccess();
                if (success) {
                    // user successfully logged in
                    // Create login session
                    String id = result.getId().toString();
                    String email = result.getEmail().toString();
                    session.setLogin(true, id, email);

                    // Now store the user in SQLite
//                    String token = result.getToken();

                    // Inserting row in users table
//                    db.addUser(name, email, uid, created_at);

                    // Launch feed activity
                    Intent intent = new Intent(SignupActivity.this,
                            RegisterActivity.class);
//                    intent.putExtra("userid", id);
                    startActivity(intent);
                    finish();
                } else {
                    //Error in login. Get the error message
                    String errorMsg = result.getMessage().toString();
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AuthResult> call, Throwable t) {

            }
        });
    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String name, final String email,
                              final String password) {
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
