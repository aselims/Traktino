package co.rahala.traktino.top10;


import android.util.Log;

import java.util.ArrayList;
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

    List<Movie> movieList;
    private static int currentPage = 1;
    private static int pages;


    public TopTenPresenter(TopTenContract.View toptenView) {
        this.toptenView = toptenView;
        movieList = new ArrayList<>();
        // this.traktClient = traktClient;
    }

    /**
     * could not make complete separation of concerns
     *
     * @param refresh
     */
    @Override
    public void loadShows(final boolean refresh) {
        final Call<List<Movie>> moviesCall;

        if (refresh) {
            currentPage = 1;
        } else {
            if (currentPage < pages){
                currentPage++;
            }
        }
        moviesCall = TraktClient.getTracktService().getMovies(String.valueOf(currentPage));

        moviesCall.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Response<List<Movie>> response, Retrofit retrofit) {

                Log.d("retrofit_responseCode", String.valueOf(response.code()));
                pages = Integer.parseInt(response.headers().values("x-pagination-page-count").get(0));
                currentPage = Integer.parseInt(response.headers().values("x-pagination-page").get(0));

                if(refresh){
                    movieList.clear();
                    movieList = response.body();
                }else {
                    movieList.addAll(response.body());
                }
                toptenView.showTopTen(movieList);
                toptenView.setProgressIndicator(false);
            }

            @Override
            public void onFailure(Throwable t) {
                toptenView.showError(t.getMessage());

            }
        });
    }



}
