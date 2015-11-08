package co.rahala.traktino.api;


import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by aselims on 07/11/15.
 */
public class TraktClient implements TraktContract {
    private static final String BASE_URL = "https://api-v2launch.trakt.tv";
    private static TraktService traktService;

    //for MVP
    private TraktService service;


    public static TraktService getTracktService() {
        if (traktService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            traktService = retrofit.create(TraktService.class);
        }

        return traktService;

    }


    //for MVP
    @Override
    public void fetchTop10() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(TraktService.class);

    }

    @Override
    public void fetchSearch(String query) {

    }
}
