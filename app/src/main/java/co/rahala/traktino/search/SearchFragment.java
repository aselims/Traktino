package co.rahala.traktino.search;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import co.rahala.traktino.R;
import co.rahala.traktino.model.Movie;
import co.rahala.traktino.model.SearchType;


public class SearchFragment extends Fragment implements SearchContract.View {

    private static final String TAG = SearchFragment.class.getSimpleName();
    SearchContract.UserActionsListener userActionsListener;
    SearchAdapter searchAdapter;
    private String query;
    private RecyclerView recyclerView;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchAdapter = new SearchAdapter(new ArrayList<SearchType>());

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        userActionsListener = new SearchPresenter(this);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_top_ten, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.movies_list);
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    @Override
    public void setProgressIndicator(final boolean active) {

        if (getView() == null) {
            return;
        }


    }

    @Override
    public void showSearchItems(List<SearchType> searchTypes) {
        searchAdapter.replaceData(searchTypes);
    }

    @Override
    public void showError(String msg) {
        Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.menu_main, menu);

    }


    @Override
    public void setKeyword(String s) {
        this.query = s;
        userActionsListener.loadSearch(s, false);
        recyclerView.scrollToPosition(0);

    }


    private class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

        private List<SearchType> searchTypes;
        boolean loading = false;


        public SearchAdapter(ArrayList<SearchType> searchTypes) {
            this.searchTypes = searchTypes;
        }

        public List<SearchType> getSearchTypes() {
            return searchTypes;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            View MovieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);

            return new ViewHolder(MovieView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            Movie movie = searchTypes.get(position).getMovie();

            viewHolder.titleTextView.setText(movie.getTitle());
            viewHolder.overviewTextView.setText(movie.getOverview());
            viewHolder.yearTextView.setText(String.valueOf(movie.getYear()));
            //ToDo iv
            Glide.with(SearchFragment.this)
                    .load(movie.getImages().getPoster().getThumb())
                    .fitCenter()
                    .override(400, 500)
                    .crossFade()
                    .into(viewHolder.imageView);

            if (position == searchTypes.size() - 2) {
                userActionsListener.loadSearch(query, true);
                Snackbar.make(getView(), "Loading more...", Snackbar.LENGTH_SHORT).show();
                Log.d(TAG, "load 10 more");
            }
        }

        public void replaceData(List<SearchType> searchTypes) {
            this.searchTypes = searchTypes;
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            return searchTypes.size();
        }

        public SearchType getItem(int position) {
            return searchTypes.get(position);
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



