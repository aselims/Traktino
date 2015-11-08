package co.rahala.traktino.top10;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.rahala.traktino.R;
import co.rahala.traktino.model.Movie;


public class TopTenFragment extends Fragment implements TopTenContract.View {

    private static final String TAG = TopTenFragment.class.getSimpleName();
    TopTenContract.UserActionsListener userActionsListener;
    MoviesAdapter moviesAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    public TopTenFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moviesAdapter = new MoviesAdapter(new ArrayList<Movie>());

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        userActionsListener = new TopTenPresenter(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        userActionsListener.loadShows(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_top_ten, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.movies_list);
        recyclerView.setAdapter(moviesAdapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout =
                (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userActionsListener.loadShows(true);
            }
        });
        return root;
    }

    @Override
    public void setProgressIndicator(final boolean active) {

        if (getView() == null) {
            return;
        }

        // Make sure setRefreshing() is called after the layout is done with everything else.
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void showTopTen(List<Movie> movies) {
        moviesAdapter.replaceData(movies);
    }

    @Override
    public void showError(String msg) {
        Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG).show();
    }

    private class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

        private List<Movie> mMovies;
        boolean loading = false;


        public MoviesAdapter(List<Movie> Movies) {
            mMovies = Movies;
        }

        public List<Movie> getmMovies() {
            return mMovies;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            View MovieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);

            return new ViewHolder(MovieView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            Movie movie = mMovies.get(position);

            viewHolder.titleTextView.setText(movie.getTitle());
            viewHolder.overviewTextView.setText(movie.getOverview());
            viewHolder.yearTextView.setText(String.valueOf(movie.getYear()));
            //ToDo iv

            if(position == mMovies.size() - 2) {
                userActionsListener.loadShows(false);
                Snackbar.make(getView(), "Loading more...", Snackbar.LENGTH_SHORT).show();
                Log.d(TAG, "load 10 more");
            }
        }

        public void replaceData(List<Movie> movies) {
            mMovies = movies;
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            return mMovies.size();
        }

        public Movie getItem(int position) {
            return mMovies.get(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView titleTextView;
            public TextView overviewTextView;
            public TextView yearTextView;
            public ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                titleTextView = (TextView) itemView.findViewById(R.id.tv_title);
                overviewTextView = (TextView) itemView.findViewById(R.id.tv_overview);
                yearTextView = (TextView) itemView.findViewById(R.id.tv_year);
                imageView = (ImageView) itemView.findViewById(R.id.iv_movie);
            }

        }
    }
}



