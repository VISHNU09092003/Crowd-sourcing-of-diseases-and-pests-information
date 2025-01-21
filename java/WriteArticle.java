package com.example.croudsourcing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;

public class WriteArticle extends AppCompatActivity {
    ImageView imageView;

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null){
            return;
        }
        Bitmap img = (Bitmap)data.getExtras().get("data");
        imageView.setImageBitmap(img);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WriteArticle.this, fullImage.class);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] arr = stream.toByteArray();
                i.putExtra("bite", arr);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_write_article);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent i = getIntent();
        String email = i.getStringExtra("email");

        EditText etTitle = findViewById(R.id.etTitle);
        EditText etIntro = findViewById(R.id.etIntro);
        EditText etBody = findViewById(R.id.etBody);
        Button submit = findViewById(R.id.btnReportSubmit);
        TextView tvAddImage = findViewById(R.id.tvAddImage);
        imageView = findViewById(R.id.imageView);

        tvAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 100);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String intro = etIntro.getText().toString();
                String body = etBody.getText().toString();

                byte[] img;
                if(imageView.getDrawable()!=null){
                    Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    img = stream.toByteArray();
                }
                else {
                    img = null;
                }

                MyDatabaseHelper db = new MyDatabaseHelper(WriteArticle.this);
                boolean isApproved = false;
                if(email.equals("expert")){
                    isApproved = true;
                }
                if(db.writeArticle(email, title, intro, body, img, isApproved )!=-1){
                    Toast.makeText(WriteArticle.this, "Successfully submitted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(WriteArticle.this, "Submittion failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}