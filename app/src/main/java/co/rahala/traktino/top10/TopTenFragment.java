package co.rahala.traktino.top10;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    private static class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

        private List<Movie> mMovies;
        private MovieItemListener mItemListener;

        public MoviesAdapter(List<Movie> Movies, MovieItemListener itemListener) {
            mMovies = Movies;
            mItemListener = itemListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            View MovieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);

            return new ViewHolder(MovieView, mItemListener);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            Movie movie = mMovies.get(position);

            viewHolder.titleTextView.setText(movie.getTitle());
            viewHolder.overviewTextView.setText(movie.getOverview());
            viewHolder.yearTextView.setText(movie.getYear());
            //ToDo iv
        }

        public void replaceData(List<Movie> Movies) {
            setList(Movies);
            notifyDataSetChanged();
        }

        private void setList(List<Movie> Movies) {
            mMovies = Movies;
        }

        @Override
        public int getItemCount() {
            return mMovies.size();
        }

        public Movie getItem(int position) {
            return mMovies.get(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView titleTextView;
            public TextView overviewTextView;
            TextView yearTextView;
            ImageView imageView;
            private MovieItemListener mItemListener;

            public ViewHolder(View itemView, MovieItemListener listener) {
                super(itemView);
                mItemListener = listener;
                titleTextView = (TextView) itemView.findViewById(R.id.tv_title);
                overviewTextView = (TextView) itemView.findViewById(R.id.tv_overview);
                yearTextView = (TextView) itemView.findViewById(R.id.tv_year);
                imageView = (ImageView) itemView.findViewById(R.id.iv_movie);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Movie Movie = getItem(position);
                mItemListener.onMovieClick(Movie);

            }
        }
    }

    public interface MovieItemListener {

        void onMovieClick(Movie clickedMovie);
    }
}
