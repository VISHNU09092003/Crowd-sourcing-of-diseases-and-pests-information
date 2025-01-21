package com.example.croudsourcing;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ExpertMain extends AppCompatActivity {
    ImageButton writeArticle, approveArticle, solution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expert_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SessionManager sessionManager = new SessionManager(ExpertMain.this);
        sessionManager.saveSession("expert");

        writeArticle = findViewById(R.id.writeArticle);
        approveArticle = findViewById(R.id.approveArticle);
        solution = findViewById(R.id.solution);

        solution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExpertMain.this, ExpertHome.class);
                i.putExtra("email", "expert");
                startActivity(i);
            }
        });

        writeArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExpertMain.this, WriteArticle.class);
                i.putExtra("email", "expert");
                startActivity(i);
            }
        });

        approveArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExpertMain.this, ReadArticles.class);
                i.putExtra("email", "expert");
                startActivity(i);
            }
        });

    }
}