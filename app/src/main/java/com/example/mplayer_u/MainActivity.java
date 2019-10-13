package com.example.mplayer_u;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mplayer_u.Favourites.Favourites;
import com.example.mplayer_u.Model.Gson_Note_Data;
import com.example.mplayer_u.Model.Table_Note;
import com.example.mplayer_u.Sqlite.NoteAdapter;
import com.example.mplayer_u.remote.NoteDao;
import com.example.mplayer_u.VIewModel.NoteViewModel;
import com.example.mplayer_u.Sqlite.Note_Database;
import com.example.mplayer_u.VIewModel.Note_Data_VIewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private NoteViewModel noteViewModel;
    NoteDao noteDao;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    AutoCompleteTextView text;
    ImageView favorite;
    Note_Database database;
    Stream stream;
    String array[];
    @Override
    protected void onResume() {
        super.onResume();

        //Data Fetch from Retrofit to Sqlite
        Datafetch();

        stream = new Stream();

        if(stream.mediaPlayer != null){
            stream.mediaPlayer.release();
            stream.mediaPlayer.reset();
            stream.mediaPlayer = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        favorite = findViewById(R.id.favorite);
        text=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);

        database = Note_Database.getInstance(getApplicationContext());
        noteDao = database.noteDao();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Set Data from sqlite to Recyclerview
        setData();

        //Serach Click listener & Click Favourite icon
        clicklistener();

    }

    private void clicklistener(){
        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if (text.getText().toString().equals(""))
                    {
                     InputMethodManager imm = (InputMethodManager)
                                getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                        Datafetch();

                    }
                    else
                    {
                        InputMethodManager imm = (InputMethodManager)
                                getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                        performSearch();
                    }
                    performSearch();
                    return true;
                }
                return false;
            }
        });
        favorite.setOnClickListener(this);
    }

    private void setData(){
        noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllData().observe(this, new Observer<List<Table_Note>>() {
            @Override
            public void onChanged(List<Table_Note> notes) {

                noteAdapter.setNotes(notes , MainActivity.this);
                array = new String[notes.size()];
                for (int i =0 ; i < notes.size() ; i++){
                    Table_Note currentNote = notes.get(i);
                    array[i] = currentNote.getTrackName();
                    Log.e("array" , array[i]);

                }
                text.setAdapter(new ArrayAdapter<String>( getApplicationContext(),android.R.layout.simple_dropdown_item_1line, array));

            }
        });

    }

    private void Datafetch(){
        Note_Data_VIewModel model = ViewModelProviders.of(this).get(Note_Data_VIewModel.class);
        model.getHeroes().observe(this, new Observer<List<Gson_Note_Data>>() {
            @Override
            public void onChanged(@Nullable List<Gson_Note_Data> heroList) {
                List<Gson_Note_Data> note_data = heroList;

                List<Gson_Note_Data.result> resultList = note_data.get(0).results;

                for (int i=0 ; i<resultList.size() ; i++){


                    noteViewModel.insert(new Table_Note(resultList.get(i).getTrackName() ,
                            resultList.get(i).getPreviewUrl(),
                            resultList.get(i).getArtistName(),
                            resultList.get(i).getArtworkUrl100(),
                            resultList.get(i).getTrackId(),
                            0) );
                }

            }

        });
    }

    private void  performSearch(){
        noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getSearchData(text.getText().toString()).observe(this, new Observer<List<Table_Note>>() {
            @Override
            public void onChanged(List<Table_Note> notes) {

                noteAdapter.setNotes(notes , MainActivity.this);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext() , Favourites.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        finish();
    }
}
