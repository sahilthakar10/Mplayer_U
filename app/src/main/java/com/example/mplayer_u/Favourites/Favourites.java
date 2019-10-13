package com.example.mplayer_u.Favourites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mplayer_u.MainActivity;
import com.example.mplayer_u.Model.Table_Note;
import com.example.mplayer_u.R;
import com.example.mplayer_u.remote.NoteDao;
import com.example.mplayer_u.VIewModel.NoteViewModel;
import com.example.mplayer_u.Sqlite.Note_Database;

import java.util.List;

public class Favourites extends AppCompatActivity {
    private NoteViewModel noteViewModel;
    NoteDao noteDao;
    RecyclerView recyclerView;
    Favourites_adpater favourites_adpater;
    ImageView arrow_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        recyclerView = findViewById(R.id.recycler_view);
        arrow_back = findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Note_Database database = Note_Database.getInstance(getApplicationContext());
        noteDao = database.noteDao();

        favourites_adpater = new Favourites_adpater();
        recyclerView.setAdapter(favourites_adpater);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getFavourites().observe(this, new Observer<List<Table_Note>>() {
            @Override
            public void onChanged(List<Table_Note> notes) {
                favourites_adpater.setNotes(notes , Favourites.this);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0 , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                TextView track_id = viewHolder.itemView.findViewById(R.id.track_id);

                noteViewModel.updateflag0(track_id.getText().toString());

            }
        }).attachToRecyclerView(recyclerView);

    }


}
