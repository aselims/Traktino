package co.rahala.traktino.api;

/**
 * Created by aselims on 08/11/15.
 */
public interface TraktContract {

        void fetchTop10();
        void fetchSearch(String query);

}
