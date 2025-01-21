package com.example.croudsourcing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class NewReport extends AppCompatActivity {

    Button btnSubmit;
    EditText etTitle, etReport;
    TextView tvImage;
    ImageView imageView;

    @Override
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
                Intent i = new Intent(NewReport.this, fullImage.class);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] arr = stream.toByteArray();
                i.putExtra("bite", arr);
                startActivity(i);
            }
        });
    }

    //    DatabaseReference dbReference;
    public String email, name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etTitle = findViewById(R.id.etTitle);
        etReport = findViewById(R.id.etReport);
        btnSubmit = findViewById(R.id.btnReportSubmit);
        tvImage = findViewById(R.id.tvUploadImage);
        imageView = findViewById(R.id.imageView);
        Intent i = getIntent();
        email = i.getStringExtra("email");

//        dbReference = FirebaseDatabase.getInstance().getReference("Reports");
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String title = etTitle.getText().toString();
//                String report = etReport.getText().toString();
//                dbReference.child(email+"/"+title).setValue(report);
//                Toast.makeText(NewReport.this, "Successfully submitted report", Toast.LENGTH_SHORT).show();
//            }
//        });

        tvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 100);
            }
        });



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String report = etReport.getText().toString();
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
                MyDatabaseHelper db = new MyDatabaseHelper(NewReport.this);
                if(db.newReport(email, title, report, img)!=-1){
                    Toast.makeText(NewReport.this, "Successfully submitted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(NewReport.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
