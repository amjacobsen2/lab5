package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DisplayNote extends AppCompatActivity {

    int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);

        EditText editText = findViewById(R.id.newNote);
        noteid = getIntent().getIntExtra("noteid", -1);

        if (noteid != -1) {
            Note note = LoggedIn.notes.get(noteid);
            String noteContent = note.getContent();
            editText.setText(noteContent);
        }
    }

    public void saveNote(View view) {
        EditText editText = (EditText) findViewById(R.id.newNote);
        String newNoteContent = editText.getText().toString();

        // Create sqlite db instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",
                Context.MODE_PRIVATE,null);

        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        SharedPreferences sharedPreferences = getSharedPreferences("lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) { // Add note
            title = "NOTE_" + (LoggedIn.notes.size() + 1);
            dbHelper.saveNotes(username, title, newNoteContent, date);
        } else { // Update note
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNotes(title, date, newNoteContent, username);
        }
        back();
    }

    public void cancelClick(View view) {
        back();
    }

    public void back() {
        Intent intent = new Intent(this, LoggedIn.class);
        startActivity(intent);
    }
}