package com.example.panicring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.panicring.modelo.RegistroUsuarioNuevo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText lastname;
    private EditText email;
    private EditText phone;
    private EditText pass;
    private EditText passConfirm;
    //private Button btn_register;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        name = findViewById(R.id.text_name);
        lastname = findViewById(R.id.text_lastname);
        email = findViewById(R.id.text_email);
        phone = findViewById(R.id.text_phone);
        pass = findViewById(R.id.register_pass);
        passConfirm = findViewById(R.id.register_pass_confirm);
        //btn_register = findViewById(R.id.btn_register);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Registro_Usuarios");

        mAuth = FirebaseAuth.getInstance();



    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void registrarUsuario(View view){

        if (pass.getText().toString().equals(passConfirm.getText().toString())){
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getApplicationContext(), "Bienvenido " + name.getText().toString(), Toast.LENGTH_SHORT).show();
                                guardarUsuarioNuevo(name.getText().toString().trim(), lastname.getText().toString().trim(),email.getText().toString().trim(),Double.parseDouble(phone.getText().toString().trim()));
                                //updateUI(user);
                                Intent i = new Intent(getApplicationContext(), LogInActivity.class);
                                startActivity(i);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        }else {
            Toast.makeText(this,"Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
        }
    }

    public void goMain(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void guardarUsuarioNuevo(String nombre, String apellido, String correo, Double numero){
        RegistroUsuarioNuevo registroUsuarioNuevo = new RegistroUsuarioNuevo(nombre,apellido, correo, numero);
        if (mAuth.getCurrentUser()!= null){
            myRef.child(mAuth.getCurrentUser().getUid()).setValue(registroUsuarioNuevo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getApplicationContext(), "Se guardo correctamente.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "No se guardo correctamente.", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

}