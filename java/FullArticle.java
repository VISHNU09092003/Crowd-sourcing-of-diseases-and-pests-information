package com.example.croudsourcing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FullArticle extends AppCompatActivity {

    TextView tvTitle, tvIntro, tvArticle;
    ImageView imageView, approve;
    Button submit;
    int approved, temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_full_article);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SessionManager sessionManager = new SessionManager(FullArticle.this);
        String session = sessionManager.getSession();

        Intent i = getIntent();
        int sno = i.getIntExtra("sno", 1);
        String name = i.getStringExtra("name");
        String email = i.getStringExtra("email");
        String title = i.getStringExtra("title");
        String intro = i.getStringExtra("intro");
        String body = i.getStringExtra("body");
        byte[] byteArray = i.getByteArrayExtra("byteArray");
        boolean isApproved = i.getBooleanExtra("isApproved", false);
        if(isApproved) approved = 1;
        else approved = 0;

        tvTitle = findViewById(R.id.tvTitle);
        tvIntro = findViewById(R.id.tvIntro);
        tvArticle = findViewById(R.id.tvArticle);
        imageView = findViewById(R.id.imageView);
        submit = findViewById(R.id.submit);
        approve = findViewById(R.id.approve);

        tvTitle.setText(title);
        tvIntro.setText(intro);
        tvArticle.setText(body);
        if(byteArray!=null) {
            Bitmap img = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageView.setImageBitmap(img);
        }



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FullArticle.this, fullImage.class);
                i.putExtra("bite", byteArray);
                startActivity(i);
            }
        });
        if(session.equals("expert")){
            approve.setVisibility(View.VISIBLE);
            submit.setVisibility(View.VISIBLE);
            temp = approved;
            if(temp == 1){
                approve.setImageResource(R.drawable.tick_green);
            }
            approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(temp == 1){
                        approve.setImageResource(R.drawable.tick_white);
                        temp = 0;
                    }
                    else{
                        approve.setImageResource(R.drawable.tick_green);
                        temp = 1;
                    }
                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(approved != temp){
                        MyDatabaseHelper db = new MyDatabaseHelper(FullArticle.this);
                        db.setApprove(sno, temp);
                    }
                    approved = temp;
                    if(approved==1){
                        Toast.makeText(FullArticle.this, "Article Approved", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(FullArticle.this, "Article not Approved", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}