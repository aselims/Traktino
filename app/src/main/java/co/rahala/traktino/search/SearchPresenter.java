package co.rahala.traktino.search;

import android.util.Log;

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
    private String query;
    private Call<List<SearchType>> searchCall;
    List<SearchType> searchTypes;
    private SearchContract.View view;

    public SearchPresenter(SearchContract.View view) {
        this.view = view;
    }


    @Override
    public void loadSearch(String query, final boolean more) {
        view.setProgressIndicator(true);
        if(!more){
            searchCall = TraktClient.getTracktService().getSearchResults(query, "1");
        }else {
            if (currentPage < pages){
                currentPage++;
                searchCall = TraktClient.getTracktService().getSearchResults(query, String.valueOf(currentPage));
            }else {
                view.showMsg("No more results");
                view.setProgressIndicator(false);
                return;
            }
        }

        searchCall.enqueue(new Callback<List<SearchType>>() {
            @Override
            public void onResponse(Response<List<SearchType>> response, Retrofit retrofit) {
                Log.d("retrofit_responseCode", String.valueOf(response.code()));
                pages = Integer.parseInt(response.headers().values("x-pagination-page-count").get(0));
                currentPage = Integer.parseInt(response.headers().values("x-pagination-page").get(0));
                if(!more){
                    searchTypes = response.body();
                    if (searchTypes.size() == 0) view.showMsg("No results!");
                }else {
                    searchTypes.addAll(response.body());
                }
                view.showSearchItems(searchTypes);
                view.setProgressIndicator(false);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    @Override
    public void cancel() {
        searchCall.cancel();
    }

}
