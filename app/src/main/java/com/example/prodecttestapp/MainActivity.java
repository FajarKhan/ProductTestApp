package com.example.prodecttestapp;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.prodecttestapp.Adapter.ProductListAdapter;
import com.example.prodecttestapp.Fragments.ProductDetailsFragment;
import com.example.prodecttestapp.Utils.AppUtils;
import com.example.prodecttestapp.Utils.Constants;
import com.example.prodecttestapp.ViewModel.ProductViewModel;
import com.example.prodecttestapp.model.ProductModel;
import com.example.prodecttestapp.model.data;
import com.google.android.material.navigation.NavigationView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener, ProductListAdapter.ProductItemClickListener, View.OnClickListener {

    private RecyclerView rvProduct;
    private ProductListAdapter adapter;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefresh;
    private SearchView searchView;
    private AppCompatButton btnA, btnB, btnC;
    private List<data> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initlizeResorces();
        setupDrawer();
    }

    private void setupRecycler() {
        ProductViewModel model = ViewModelProviders.of(this).get(ProductViewModel.class);

        model.getProducts().observe(this, new Observer<ProductModel>() {
            @Override
            public void onChanged(@Nullable ProductModel ProductList) {
                productList = ProductList.getData();
                adapter = new ProductListAdapter(MainActivity.this, productList, MainActivity.this);
                rvProduct.setAdapter(adapter);
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void initlizeResorces() {
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        rvProduct = findViewById(R.id.rv_product);
        toolbar = findViewById(R.id.toolbar);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        btnA = findViewById(R.id.btn_a);
        btnB = findViewById(R.id.btn_b);
        btnC = findViewById(R.id.btn_c);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);

        setSupportActionBar(toolbar);

        rvProduct.setHasFixedSize(true);
        rvProduct.setLayoutManager(new LinearLayoutManager(this));

        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh();
            }
        });
    }

    private void swipeRefresh() {
        swipeRefresh.setRefreshing(true);
        if (!AppUtils.checkInternetConnection(this)) {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            swipeRefresh.setRefreshing(false);
        } else {
            // Fetching data from server
            if (swipeRefresh.isRefreshing()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setupRecycler();
                    }
                }, 1000);
            }
        }
    }

    private void setupDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
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
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            Toast.makeText(this, "Work in Progress!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_offers) {
            Toast.makeText(this, "Work in Progress!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_orders) {
            Toast.makeText(this, "Work in Progress!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_logout) {
            Toast.makeText(this, "Work in Progress!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "Work in Progress!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "Work in Progress!", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRefresh() {
        swipeRefresh();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle("Product List");
    }

    @Override
    public void onitemClick(data product) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PRODUCT_ID, product.getId());
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frame, fragment).addToBackStack(fragment.getTag());
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_a:
                sortList("a");
                break;
            case R.id.btn_b:
                sortList("b");
                break;
            case R.id.btn_c:
                sortList("c");
                break;
        }
    }

    private void sortList(final String type) {
        if(productList != null) {
            swipeRefresh.setRefreshing(true);
            Collections.sort(productList, new Comparator<data>() {
                public int compare(data obj1, data obj2) {
                    // ## Ascending order
                    if (type.equals("a")) {
                        return Integer.valueOf(obj1.getSort_props().getA()).compareTo(Integer.valueOf(obj2.getSort_props().getA()));
                    } else if (type.equals("b")) {
                        return Integer.valueOf(obj1.getSort_props().getB()).compareTo(Integer.valueOf(obj2.getSort_props().getB()));
                    } else {
                        return Integer.valueOf(obj1.getSort_props().getC()).compareTo(Integer.valueOf(obj2.getSort_props().getC()));
                    }
                }
            });
            adapter = new ProductListAdapter(MainActivity.this, productList, MainActivity.this);
            rvProduct.setAdapter(adapter);
            swipeRefresh.setRefreshing(false);
        }
    }
}
