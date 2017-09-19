package edu.gatech.pavyl.pavyl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onRegisterPressed(View view) {
        //TODO
    }

    public void onCancelPressed(View view) {
        finish();
    }
}
