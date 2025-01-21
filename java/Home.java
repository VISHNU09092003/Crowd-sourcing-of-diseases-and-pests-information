package com.example.croudsourcing;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SessionManager sessionManager = new SessionManager(Home.this);


        ImageButton btnReport = findViewById(R.id.btnReports);
        ImageButton btnArticle = findViewById(R.id.btnArticle);
        ImageButton btnQuery = findViewById(R.id.btnQuery);
        Intent i = getIntent();
        String email = i.getStringExtra("email");
        sessionManager.saveSession(email);

        Dialog dialog = new Dialog(Home.this);

        btnArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.articles_dialog_box);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
                ImageButton btnReadArticles = dialog.findViewById(R.id.btnReadArticle);
                ImageButton btnWriteArticles = dialog.findViewById(R.id.btnWriteArticle);

                btnReadArticles.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Home.this, ReadArticles.class);
                        i.putExtra("email", email);
                        startActivity(i);
                    }
                });

                btnWriteArticles.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Home.this, WriteArticle.class);
                        i.putExtra("email", email);
                        startActivity(i);
                    }
                });
                dialog.show();
            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.reports_dialog_box);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
                ImageButton btnNewReport = dialog.findViewById(R.id.btnNewReport);
                ImageButton btnAllReport = dialog.findViewById(R.id.btnAllReports);

                btnNewReport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Home.this, NewReport.class);
                        i.putExtra("email", email);
                        startActivity(i);
                    }
                });

                btnAllReport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Home.this, AllReports.class);
                        i.putExtra("email", email);
                        startActivity(i);
                    }
                });
                dialog.show();
            }
        });

        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, ContactExpert.class);
                i.putExtra("email", email);
                startActivity(i);
            }
        });
    }
}