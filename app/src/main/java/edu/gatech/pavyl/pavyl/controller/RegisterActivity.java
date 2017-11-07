package edu.gatech.pavyl.pavyl.controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;

import edu.gatech.pavyl.pavyl.R;
import edu.gatech.pavyl.pavyl.model.AuthHandler;
import edu.gatech.pavyl.pavyl.model.NetworkUtils;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    /**
     * Called when the "Register" button is pressed.
     * @param view - superview
     */
    public void onRegisterPressed(View view) {
        //Get necessary UI elements from view
        EditText usernameEdit = findViewById(R.id.r_edit_username);
        EditText passwordEdit = findViewById(R.id.r_edit_password);
        EditText confirmEdit = findViewById(R.id.r_edit_confirm);
        Switch adminSwitch = findViewById(R.id.adminSwitch);

        //Make sure username, password and confirm fields aren't empty
        if(usernameEdit.getText().length() > 0 && passwordEdit.getText().length() > 0 &&
                confirmEdit.getText().length() > 0) {
            //Make sure password field == confirm field
            if(passwordEdit.getText().toString().equals(confirmEdit.getText().toString())) {
                //Show the progress spinner
                final ProgressBar spinner = findViewById(R.id.registerSpinner);
                spinner.setVisibility(View.VISIBLE);
                //Prepare extra data for registration protocol
                String extraText = "admin=" + adminSwitch.isChecked();

                //Start registration protocol
                AuthHandler.register(usernameEdit.getText().toString(),
                        passwordEdit.getText().toString(), extraText,
                        new NetworkUtils.ResponseHandler() {
                            @Override
                            public void handle(NetworkUtils.Response response) {
                                //All handled in UI thread
                                if (response.accept) {
                                    //If the server approved the new account, go to main view
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    //If the server declined the new account, warn the user
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage(response.message);
                                    builder.setTitle("Warning");
                                    builder.create().show();
                                }

                                //Hide the progress spinner
                                spinner.setVisibility(View.INVISIBLE);
                            }
                        });
            }
            else {
                //If passwords don't match, warn user
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Passwords don't match");
                builder.setTitle("Warning");
                builder.create().show();
            }
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
