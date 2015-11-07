package co.rahala.traktino.top10;


import android.util.Log;

import java.util.List;

import co.rahala.traktino.api.TraktClient;
import co.rahala.traktino.model.Movie;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by aselims on 05/11/15.
 */
public class TopTenPresenter implements TopTenContract.UserActionsListener {
    //ref to view & to repo
    private TopTenContract.View toptenView;
    //private TraktClient traktClient; should be injected

    public TopTenPresenter(TopTenContract.View toptenView) {
        this.toptenView = toptenView;
        // this.traktClient = traktClient;
    }

    /**
     * could not make complete separation of concerns
     */
    @Override
    public void loadShows(boolean refresh) {
        Call<List<Movie>> moviesCall = TraktClient.getTracktService().getMovies("1");
       /* if (refresh) {
            moviesCall = TraktClient.getTracktService().getMovies("1");
        } else {
           // moviesCall = TraktClient.getTracktService().getMovies(String.valueOf(page));
        }
        // Call<List<Movie>> moviesCall = traktClient.
       */ moviesCall.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Response<List<Movie>> response, Retrofit retrofit) {

                Log.d("retrofit_responseCode", String.valueOf(response.code()));
                toptenView.showTopTen(response.body());
                toptenView.setProgressIndicator(false);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();

            }
        });
    }
}
