package com.example.panicring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.panicring.modelo.RegistroUsuarioNuevo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class accountActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView nombre;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String apellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Registro_Usuarios");

        nombre = findViewById(R.id.textAcountName);

        myRef.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RegistroUsuarioNuevo registroUsuarioNuevoTemporal = snapshot.getValue(RegistroUsuarioNuevo.class);
                if (registroUsuarioNuevoTemporal != null){
                    nombre.setText("Hola, " + registroUsuarioNuevoTemporal.getNombre());
                    apellido = registroUsuarioNuevoTemporal.getApellido();
                }else{
                    apellido = "";

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void logout(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cerrar Sesión");
        builder.setMessage("¿Quieres cerrar la sesión?");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Hasta luego",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                if (mAuth.getCurrentUser() == null){
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                }
            }
        });
        builder.setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void goSettings(View view){
        if (mAuth.getCurrentUser()!=null){
            Intent i = new Intent(this, Settings.class);
            startActivity(i);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Debes iniciar sesión");
            builder.setCancelable(true);
            builder.setPositiveButton("Ok", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void goMain(View view){
        Intent i = new Intent(this, MainPage.class);
        startActivity(i);
    }
}