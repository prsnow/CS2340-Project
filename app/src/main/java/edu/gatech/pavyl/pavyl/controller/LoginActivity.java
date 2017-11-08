package edu.gatech.pavyl.pavyl.controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import edu.gatech.pavyl.pavyl.R;
import edu.gatech.pavyl.pavyl.model.AuthHandler;
import edu.gatech.pavyl.pavyl.model.NetworkUtils;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * Called when the "Login" button is pressed.
     * @param view - superview
     */
    public void onLoginPressed(View view) {
        //Get username and password text fields from view
        EditText usernameEdit = findViewById(R.id.l_edit_username);
        EditText passwordEdit = findViewById(R.id.l_edit_password);

        //Make sure they aren't empty
        if ((usernameEdit.getText().length() > 0) && (passwordEdit.getText().length() > 0)) {
            //Make that progress spinner visible
            final ProgressBar spinner = findViewById(R.id.loginSpinner);
            spinner.setVisibility(View.VISIBLE);

            //Start the login protocol
            AuthHandler.login(usernameEdit.getText().toString(), passwordEdit.getText().toString(),
                    new NetworkUtils.ResponseHandler() {
                        @Override
                        public void handle(NetworkUtils.Response response) {
                            //Note that everything here is handled in the UI thread
                            if (response.accept) {
                                //If the server approved authentication, go to main view
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                //If the server declined authentication, warn the user
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(response.message);
                                builder.setTitle("Warning");
                                builder.create().show();
                            }

                            //Hide progress spinner
                            spinner.setVisibility(View.INVISIBLE);
                        }
                    });
        }
    }

    /**
     * Called when the "Cancel" button is pressed.
     * @param view - superview
     */
    public void onCancelPressed(View view) {
        finish();
    }
}
