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
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    FirebaseAuth firebase;
    DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SessionManager sessionManager = new SessionManager(SignUp.this);
        sessionManager.saveSession("");

        EditText etName = findViewById(R.id.etSignUpFullName);
        EditText etEmail = findViewById(R.id.etSignUpEmail);
        EditText etPassword = findViewById(R.id.etSignUpPassword);
        EditText etConfirmPassword = findViewById(R.id.etSignUpConfirmPassword);
        Button btnSubmit = findViewById(R.id.btnSignUpSubmit);
        TextView tvLogin = findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, Login.class);
                startActivity(i);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+[.][a-z]+";
                if(email.isEmpty()){
                    Toast.makeText(SignUp.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
                    etEmail.setError("Email Id is must");
                    return;
                }
                if(!email.matches(emailPattern)){
                    Toast.makeText(SignUp.this, "Enter valid Email Address", Toast.LENGTH_SHORT).show();
                    etEmail.setError("Invalid Email");
                    return;
                }
                if(password.isEmpty()){
                    Toast.makeText(SignUp.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    etPassword.setError("Enter Password");
                    return;
                }
                if(!password.equals(confirmPassword)){
                    etConfirmPassword.setError("Password didn't match");
                    Toast.makeText(SignUp.this, "Password didn't match", Toast.LENGTH_SHORT).show();
                    return;
                }
                MyDatabaseHelper db = new MyDatabaseHelper(SignUp.this);
                db.insertData(name, email, password);
//                if(db.insertData(name, email, password)==-1){
//                    Toast.makeText(SignUp.this, "signup failed", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                Toast.makeText(SignUp.this, "Successfully signup", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(SignUp.this, Login.class);
//                startActivity(i);
//                }
                firebase = FirebaseAuth.getInstance();
                dbRef = FirebaseDatabase.getInstance().getReference("Users/"+email.substring(0, email.length()-10));
                firebase.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String key = dbRef.push().getKey();
                            dbRef.child("name").setValue(name);
                            dbRef.child("email").setValue(email);
                            Toast.makeText(SignUp.this, "Successfully signup", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignUp.this, Login.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(SignUp.this, "signup failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}