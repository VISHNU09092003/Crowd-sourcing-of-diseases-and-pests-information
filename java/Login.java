package com.example.croudsourcing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Login extends AppCompatActivity {
    FirebaseAuth firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SessionManager sessionManager = new SessionManager(Login.this);
        sessionManager.saveSession("");

        EditText etGmail = findViewById(R.id.etGmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        TextView tvSignUp = findViewById(R.id.tvSignUp);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etGmail.getText().toString();
                String password = etPassword.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+[.][a-z]+";
                if(email.isEmpty()){
                    Toast.makeText(Login.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
                    etGmail.setError("Email Id is must");
                    return;
                }
                if(!email.matches(emailPattern)){
                    Toast.makeText(Login.this, "Enter valid Email Address", Toast.LENGTH_SHORT).show();
                    etGmail.setError("Invalid Email");
                    return;
                }
//                MyDatabaseHelper db = new MyDatabaseHelper(Login.this);
//                if(db.validateUser(email, password)){
//                    Toast.makeText(Login.this, "Successful login", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(Login.this, Home.class);
//                    startActivity(i);
//                }
//                else{
//                    Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
//                }
                if(email.equals("expert@gmail.com") && password.equals("expert")){
                    Intent i = new Intent(Login.this, ExpertMain.class);
                    i.putExtra("email", "expert");
                    Toast.makeText(Login.this, "Successfully loged in", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                    return;
                }
                firebase = FirebaseAuth.getInstance();
                firebase.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Successfully loged in", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Login.this, Home.class);
                            i.putExtra("email", email);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);
            }
        });
    }
}