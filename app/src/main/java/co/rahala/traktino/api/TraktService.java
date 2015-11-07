package co.rahala.traktino.api;

import java.util.List;

import co.rahala.traktino.model.Movie;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by aselims on 07/11/15.
 */
public interface TraktService {

    //https://api-v2launch.trakt.tv/movies/popular?page=2&extended=full,images

    @Headers({
            "Content-Type: application/json",
            "trakt-api-key: ad005b8c117cdeee58a1bdb7089ea31386cd489b21e14b19818c91511f12a086",
            "trakt-api-version: 2"
    })
    @GET("/movies/popular?extended=full,images")
    Call<List<Movie>> getMovies(@Query("page") String page);

}

