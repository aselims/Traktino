package co.rahala.traktino.search;

import java.util.List;

import co.rahala.traktino.api.TraktClient;
import co.rahala.traktino.model.SearchType;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by aselims on 08/11/15.
 */
public class SearchPresenter implements SearchContract.UserActionsListener {

    private static int currentPage = 1;
    private static int pages;

    private SearchContract.View view;

    public SearchPresenter(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void loadShows(boolean refresh) {
        if (refresh) {
            currentPage = 1;
        } else {
            if (currentPage < pages){
                currentPage++;
            }
        }



    }

    @Override
    public void loadSearch(String query) {

        Call<List<SearchType>> searchCall = TraktClient.getTracktService().getSearchResults(query);

        searchCall.enqueue(new Callback<List<SearchType>>() {
            @Override
            public void onResponse(Response<List<SearchType>> response, Retrofit retrofit) {
                view.showTen(response.body());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }
}
