package com.example.mplayer_u.Model;


import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "M_Player", indices = {@Index(value = "trackId", unique = true)})
public class Table_Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String trackName;
    private String previewUrl;
    private String artistName;
    private String artworkUrl30;
    private String trackId;
    private int flag;

    public Table_Note(String trackName, String previewUrl, String artistName, String artworkUrl30, String trackId, int flag) {
        this.trackName = trackName;
        this.previewUrl = previewUrl;
        this.artistName = artistName;
        this.artworkUrl30 = artworkUrl30;
        this.trackId = trackId;
        this.flag = flag;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtworkUrl30() {
        return artworkUrl30;
    }

    public String getTrackId() {
        return trackId;
    }

    public int getFlag() {
        return flag;
    }
}
