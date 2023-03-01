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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Settings extends AppCompatActivity {

    private TextView nombre;
    private TextView apellido;
    private TextView correo;
    private TextView telefono;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Registro_Usuarios");

        nombre = findViewById(R.id.text_name);
        apellido = findViewById(R.id.text_lastname);
        correo = findViewById(R.id.text_email);
        telefono = findViewById(R.id.text_phone);

        myRef.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RegistroUsuarioNuevo registroUsuarioNuevoTemporal = snapshot.getValue(RegistroUsuarioNuevo.class);
                if (registroUsuarioNuevoTemporal != null){
                    nombre.setText(registroUsuarioNuevoTemporal.getNombre());
                    apellido.setText(registroUsuarioNuevoTemporal.getApellido());
                    correo.setText(registroUsuarioNuevoTemporal.getCorreo());
                    telefono.setText(registroUsuarioNuevoTemporal.getNumero().toString());
                    //apellido = registroUsuarioNuevoTemporal.getApellido();
                }else{
                    //apellido = "";

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void goAccount(View view){
        if (mAuth.getCurrentUser()!=null){
            Intent i = new Intent(this, accountActivity.class);
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

    public void guardarCambios(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Guardar Cambios");
        builder.setMessage("¿Quieres guardar los cambios?");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cambiosAceptados();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void cambiosAceptados(){
        RegistroUsuarioNuevo registroUsuarioNuevo = new RegistroUsuarioNuevo(nombre.getText().toString().trim(),apellido.getText().toString().trim(), correo.getText().toString().trim(), Double.parseDouble(telefono.getText().toString().trim()));
        if (mAuth.getCurrentUser()!= null){
            myRef.child(mAuth.getCurrentUser().getUid()).setValue(registroUsuarioNuevo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getApplicationContext(), "Se guardaron los cambios.", Toast.LENGTH_SHORT).show();
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