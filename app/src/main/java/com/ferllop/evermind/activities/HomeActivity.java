package com.ferllop.evermind.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ferllop.evermind.AndroidApplication;
import com.ferllop.evermind.R;
import com.ferllop.evermind.models.User;

import java.security.NoSuchAlgorithmException;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button searchButton = findViewById(R.id.search_cards_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SearchCardsActivity.class));
            }
        });


        Button createButton = findViewById(R.id.create_card_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CardDataActivity.class));
            }
        });

        ((TextView) findViewById(R.id.logo_textView))
                .setText(AndroidApplication.getUser(this));

    }
}