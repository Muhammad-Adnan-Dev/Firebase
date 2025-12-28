package com.example.firebaseandcrudoperations;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddEditBookActivity extends AppCompatActivity {

    private EditText titleEditText, authorEditText, isbnEditText, publicationYearEditText;
    private Button saveButton;
    private FirebaseFirestore db;
    private String bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_book);

        titleEditText = findViewById(R.id.edit_text_title);
        authorEditText = findViewById(R.id.edit_text_author);
        isbnEditText = findViewById(R.id.edit_text_isbn);
        publicationYearEditText = findViewById(R.id.edit_text_publication_year);
        saveButton = findViewById(R.id.button_save);

        db = FirebaseFirestore.getInstance();

        bookId = getIntent().getStringExtra("book_id");
        if (bookId != null) {
            db.collection("books").document(bookId).get().addOnSuccessListener(documentSnapshot -> {
                Book book = documentSnapshot.toObject(Book.class);
                if (book != null) {
                    titleEditText.setText(book.getTitle());
                    authorEditText.setText(book.getAuthor());
                    isbnEditText.setText(book.getIsbn());
                    publicationYearEditText.setText(book.getPublicationYear());
                }
            });
        }

        saveButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String author = authorEditText.getText().toString();
            String isbn = isbnEditText.getText().toString();
            String publicationYear = publicationYearEditText.getText().toString();

            Map<String, Object> book = new HashMap<>();
            book.put("title", title);
            book.put("author", author);
            book.put("isbn", isbn);
            book.put("publicationYear", publicationYear);

            if (bookId == null) {
                db.collection("books").add(book).addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Book added", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Error adding book", Toast.LENGTH_SHORT).show();
                });
            } else {
                db.collection("books").document(bookId).set(book).addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Book updated", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Error updating book", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}