package com.ferllop.evermind.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ferllop.evermind.R;
import com.ferllop.evermind.repositories.GlobalUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReviewResultActivity extends AppCompatActivity {
    final String TAG = "MYAPP-ReviewResult";
    TextView reaction;
    TextView result;
    Button continue_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_result);

        reaction = findViewById(R.id.result_reaction_textview);
        result = findViewById(R.id.result_result_textview);
        continue_button = findViewById(R.id.result_continue_button);

        Intent intent = getIntent();
        int total = intent.getIntExtra("total", 0);
        int right = intent.getIntExtra("right", 0);

        reaction.setText(getMessage());
        result.setText(right + "/" + total);

        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });
    }

    private String getMessage(){
        String name = GlobalUser.getInstance().getUser().getName();
        List<String> messages = new ArrayList<>();
        messages.add("Keep on");
        messages.add("Don't defeat");
        messages.add("You rock");
        int random = new Random().nextInt(messages.size());
        return messages.get(random) + "\n" + name + "!";
    }
}