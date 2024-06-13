package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NoteRepo {
    public final static String fileName = "Notes.json";

    public List<Note> notes = new ArrayList<>();

    public NoteRepo() {
        notes = readFile();
    }

    public List<Note> getNotes() {
        return notes;
    }

    public Note addNote(Note note) {
        int highestId = 0;
        for (Note a : notes) {
            if (a.id > highestId) {
                highestId = a.id;
            }
        }
        note.id = (Integer) (highestId + 1);
        notes.add(note);
        saveList(notes);
        return note;
    }

    private List<Note> readFile() {
        Type REVIEW_TYPE = new TypeToken<List<Note>>() {
        }.getType();
        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            saveList(notes);
            return notes;
        }
        return gson.fromJson(reader, REVIEW_TYPE);
    }

    private void saveList(List<Note> list) {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(list, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
