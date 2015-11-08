package co.rahala.traktino.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aselims on 08/11/15.
 */
public class Poster {
     /* @SerializedName("full")
         @Expose
         private String full;
         @SerializedName("medium")
         @Expose
         private String medium;
        */

    @SerializedName("thumb")
    @Expose
    private String thumb;

    public String getThumb() {
        return thumb;
    }
}

