package com.example.firebaseandcrudoperations;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class BookActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton addBookButton;
    private FirebaseFirestore db;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        recyclerView = findViewById(R.id.recycler_view);
        addBookButton = findViewById(R.id.add_book_button);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        Query query = db.collection("books");

        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();

        adapter = new BookAdapter(options);
        recyclerView.setAdapter(adapter);

        addBookButton.setOnClickListener(v -> {
            startActivity(new Intent(this, AddEditBookActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private class BookAdapter extends FirestoreRecyclerAdapter<Book, BookAdapter.BookViewHolder> {

        public BookAdapter(@NonNull FirestoreRecyclerOptions<Book> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull BookViewHolder holder, int position, @NonNull Book model) {
            holder.title.setText(model.getTitle());
            holder.author.setText(model.getAuthor());

            holder.deleteButton.setOnClickListener(v -> {
                DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
                db.collection("books").document(snapshot.getId()).delete();
            });

            holder.itemView.setOnClickListener(v -> {
                DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
                Intent intent = new Intent(BookActivity.this, AddEditBookActivity.class);
                intent.putExtra("book_id", snapshot.getId());
                startActivity(intent);
            });
        }

        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
            return new BookViewHolder(view);
        }

        class BookViewHolder extends RecyclerView.ViewHolder {
            TextView title, author;
            ImageButton deleteButton;

            public BookViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.text_view_title);
                author = itemView.findViewById(R.id.text_view_author);
                deleteButton = itemView.findViewById(R.id.button_delete);
            }
        }
    }
}