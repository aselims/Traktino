package co.rahala.traktino.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aselims on 08/11/15.
 */
public class SearchType {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("score")
    @Expose
    private Double score;
    @SerializedName("movie")
    @Expose
    private Movie movie;

    public String getType() {
        return type;
    }

    public Double getScore() {
        return score;
    }

    public Movie getMovie() {
        return movie;
    }
}
