package co.rahala.traktino;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import co.rahala.traktino.search.SearchContract;
import co.rahala.traktino.search.SearchFragment;
import co.rahala.traktino.top10.TopTenFragment;

public class MainActivity extends AppCompatActivity {
    public static final String TOPTEN_FRAGMENT = "topten";
    SearchContract.View searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * I used the SearchView widget; Another approach would be using custom layout in the toolbar.
         * I have implemented an AutoComplete TextView based on data from API at */

        searchView = new SearchFragment();


        if (getSupportFragmentManager().findFragmentById(R.id.container) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,
                            new TopTenFragment(), TOPTEN_FRAGMENT).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView view = (SearchView) MenuItemCompat.getActionView(item);

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                getSupportFragmentManager().popBackStack();
               // onBackPressed();
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, (Fragment) searchView)
                            .addToBackStack(null)
                            .commit();
                }
                return true;
            }
        });

        view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText) && newText.length() > 3) {
                    searchView.setKeyword(newText);

                }
                return true;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_search) {
            if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, (Fragment) searchView)
                        .commit();
            }
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }
}
