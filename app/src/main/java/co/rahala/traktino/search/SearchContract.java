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

        void showSearchItems(List<SearchType> searchTypes);

        void showError(String msg);


    }

    interface UserActionsListener {


        void loadSearch(String query, boolean more);


    }
}
