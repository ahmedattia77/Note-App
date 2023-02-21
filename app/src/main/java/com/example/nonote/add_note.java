package com.example.nonote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;

import org.w3c.dom.Document;

public class add_note extends AppCompatActivity {

    private TextInputLayout title;
    private TextInputLayout content;
    private MaterialButton add;
    private String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
    private String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        add = findViewById(R.id.addNote_add_ib);
        title = findViewById(R.id.addNote_title);
        content = findViewById(R.id.addNote_content);

        add.setOnClickListener((v)-> addNote());



    }

    private void addNote() {
        String title_ = title.getEditText().getText().toString();
        String content_ = content.getEditText().getText().toString();

        if (title_ == null  || content_.isEmpty())
        {
            this.title.setError("PLS type something ... ");
            return;
        }
        Note note = new Note(title_,content_,currentDate,currentTime);
        saveNoteToFirebase(note);
    }

    private void saveNoteToFirebase(Note note) {
        DocumentReference documentReference;
        documentReference = collectionReference.getCollectionReference().document();
        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(add_note.this, "note has been added", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                    Toast.makeText(add_note.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}