package co.rahala.traktino.top10;

import java.util.List;

import co.rahala.traktino.model.Movie;

/**
 * Created by aselims on 05/11/15.
 */
public interface TopTenContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showTopTen(List<Movie> movies);

        void showError(String msg);

    }

    interface UserActionsListener {

        void loadShows(boolean refresh);


    }
}
