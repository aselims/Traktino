package co.rahala.traktino.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
    private boolean keyboardHidden;
    SearchContract.UserActionsListener userActionsListener;
    SearchAdapter searchAdapter;
    private String query;
    private RecyclerView recyclerView;
    private boolean loading;

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
        View root = inflater.inflate(R.layout.fragment_movies, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.movies_list);
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!keyboardHidden) {
                    hideKeyboard(getActivity());
                    keyboardHidden = true;
                }
            }
        });

        SwipeRefreshLayout swipeRefreshLayout =
                (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setEnabled(false);

        return root;
    }

    @Override
    public void setProgressIndicator(final boolean active) {
        loading = active;
        final View pb = getView().findViewById(R.id.progress);
        if(active) {
            pb.setVisibility(View.VISIBLE);
        }
        else {
            pb.setVisibility(View.GONE);

        }
    }

    @Override
    public void showSearchItems(List<SearchType> searchTypes) {
        searchAdapter.replaceData(searchTypes);
    }

    @Override
    public void showMsg(String msg) {
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
        if(s.equals("") && loading){
            userActionsListener.cancel();
        }else{
            userActionsListener.loadSearch(s, false);
            recyclerView.scrollToPosition(0);
            keyboardHidden = false;
        }


    }

    public static void hideKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        View focus = activity.getCurrentFocus();
        if (focus != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    focus.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN
            );
        }
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



