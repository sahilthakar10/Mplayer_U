package com.example.mplayer_u.Favourites;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mplayer_u.Model.Table_Note;
import com.example.mplayer_u.R;
import com.example.mplayer_u.Stream;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Favourites_adpater extends RecyclerView.Adapter<Favourites_adpater.Favouriteholder> {

    private List<Table_Note> notes = new ArrayList<>();
    private Context context;
    @NonNull
    @Override
    public Favouriteholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourites_adapter , parent ,false);
        return new Favourites_adpater.Favouriteholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Favouriteholder holder, int position) {
        final Table_Note currentNote = notes.get(position);

        holder.track_url.setText(currentNote.getPreviewUrl());
        holder.track_flag.setText(String.valueOf(currentNote.getFlag()));
        holder.track_name.setText(currentNote.getTrackName());
        holder.track_artist_name.setText(currentNote.getArtistName());
        holder.track_id.setText(currentNote.getTrackId());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context , Stream.class);
                Toast.makeText(context, holder.track_name.getText().toString(), Toast.LENGTH_SHORT).show();
                intent.putExtra("track_name",holder.track_name.getText().toString());
                intent.putExtra("artist_name",holder.track_artist_name.getText().toString());
                intent.putExtra("url",holder.track_url.getText().toString());
                intent.putExtra("image_url",currentNote.getArtworkUrl30());
                intent.putExtra("Trackid",holder.track_id.getText().toString());
                intent.putExtra("flag",holder.track_flag.getText().toString());
                context.startActivity(intent);
            }
        });
        Picasso.get().load(currentNote.getArtworkUrl30()).fit().into(holder.track_image);

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Table_Note> notes , Context context) {
        this.notes = notes;
        this.context = context;
        notifyDataSetChanged();
    }

    class Favouriteholder extends  RecyclerView.ViewHolder{

        private ImageView track_image;
        private TextView track_flag;
        private TextView track_url;
        private TextView track_id;
        private TextView track_name;
        private TextView track_artist_name;
        private RelativeLayout relativeLayout;

        public Favouriteholder(View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.r1);
            track_url = itemView.findViewById(R.id.url);
            track_flag = itemView.findViewById(R.id.flag);
            track_id = itemView.findViewById(R.id.track_id);

            track_image = itemView.findViewById(R.id.track_image);
            track_name = itemView.findViewById(R.id.track_name);
            track_artist_name = itemView.findViewById(R.id.artist_name);
        }
    }

}
