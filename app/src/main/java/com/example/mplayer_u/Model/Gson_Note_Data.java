package com.example.mplayer_u.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Gson_Note_Data {

    @SerializedName("results")
    public List<result> results = new ArrayList<>();

    public class result
    {

        @SerializedName("trackName")
        private String trackName;
        @SerializedName("previewUrl")
        private String previewUrl;
        @SerializedName("artistName")
        private String artistName;
        @SerializedName("artworkUrl100")
        private String artworkUrl100;
        @SerializedName("trackId")
        private String trackId;

        public String getTrackName() {
            return trackName;
        }

        public String getPreviewUrl() {
            return previewUrl;
        }

        public String getArtistName() {
            return artistName;
        }

        public String getArtworkUrl100() {
            return artworkUrl100;
        }

        public String getTrackId() {
            return trackId;
        }
    }
}
