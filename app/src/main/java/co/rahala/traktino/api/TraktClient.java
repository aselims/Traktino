package co.rahala.traktino.api;


import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by aselims on 07/11/15.
 */
public class TraktClient {
    private static final String BASE_URL = "https://api-v2launch.trakt.tv";
    private static TraktService traktService;


    public static TraktService getTracktService(){
        if(traktService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            traktService = retrofit.create(TraktService.class);
        }

        return traktService;

    }
}
