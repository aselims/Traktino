package co.rahala.traktino.model;

/**
 * Created by aselims on 05/11/15.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Images {

    @SerializedName("poster")
    @Expose
    private Poster poster;

    /*@SerializedName("fanart")
        @Expose
        private Fanart fanart;

        @SerializedName("logo")
        @Expose
        private Logo logo;
        @SerializedName("clearart")
        @Expose
        private Clearart clearart;
        @SerializedName("banner")
        @Expose
        private Banner banner;
        @SerializedName("thumb")
        @Expose
        private Thumb thumb;
*/

    public Poster getPoster() {
        return poster;
    }

    private class Poster {
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

}
