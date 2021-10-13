package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

// In this Activity (Window) a user can edit their note.
public class EditActivity extends AppCompatActivity {

    // global, so it can be accessed anywhere
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // display the text that is already in the note
        EditText editText = (EditText) findViewById(R.id.editText);

        // get the noteId from the MainActivity (of the note that was clicked)
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId",-1);          // default is -1, because listView id starts at 0


        if (noteId != -1) {
            editText.setText(MainActivity.notes.get(noteId));
        } else {

            // add a new note, with a new NoteId, and then notify
            MainActivity.notes.add("");
            noteId = MainActivity.notes.size() - 1;
            MainActivity.arrayAdapter.notifyDataSetChanged();

        }

        // Save the changed text
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            // This is the only method we will be using, it updates the note with the new (changed) note
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // set the note to a new, updated note, and notify the changes
                MainActivity.notes.set(noteId, s.toString());
                MainActivity.arrayAdapter.notifyDataSetChanged();

                // save and update the sharedPreferences when the note is modified
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.noteapp", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}