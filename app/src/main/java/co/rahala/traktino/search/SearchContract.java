package co.rahala.traktino.search;

import java.util.List;

import co.rahala.traktino.model.Movie;
import co.rahala.traktino.model.SearchType;

/**
 * Created by aselims on 08/11/15.
 */
public interface SearchContract {

    interface View {
        void setKeyword(String s);

        void setProgressIndicator(boolean active);

        void showTen(List<SearchType> searchTypes);

        void showError(String msg);


    }

    interface UserActionsListener {

        void loadShows(boolean refresh);

        void loadSearch(String query);


    }
}
