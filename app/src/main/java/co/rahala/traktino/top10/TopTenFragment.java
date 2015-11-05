package co.rahala.traktino.top10;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.rahala.traktino.R;
import co.rahala.traktino.data.Movie;


public class TopTenFragment extends Fragment implements TopTenContract.View {

    TopTenContract.UserActionsListener userActionsListener;

    public TopTenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_top_ten, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.movies_list);
        return root;
    }

    @Override
    public void setProgressIndicator(boolean active) {

    }

    @Override
    public void showTopTen(List<Movie> movies) {

    }
}
