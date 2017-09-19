package edu.gatech.pavyl.pavyl;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLoginPressed(View view) {
        EditText usernameEdit = (EditText)findViewById(R.id.l_edit_username);
        EditText passwordEdit = (EditText)findViewById(R.id.l_edit_password);

        if(usernameEdit.getText().toString().equals(USERNAME) &&
                passwordEdit.getText().toString().equals(PASSWORD)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Invalid credentials.");
            builder.setTitle("Warning");
            builder.create().show();
        }
    }

    public void onCancelPressed(View view) {
        finish();
    }
}
