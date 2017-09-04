package com.pfc.android.archcomponent.ui;

import android.animation.Animator;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.pfc.android.archcomponent.R;
import com.pfc.android.archcomponent.util.PermissionsRequester;
import com.pfc.android.archcomponent.viewmodel.UnifiedModelView;
import com.pfc.android.archcomponent.vo.ArrivalsFormatedEntity;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


/**
 * MainActivity extends LifecycleActivity
 * <p>
 * This is the fragment that is in charge of the list of stops near the location of the user.
 * <p>
 *
 * @author      Ana San Juan
 * @version     1.0
 * @since       1.0
 */
public class MainActivity extends LifecycleActivity {

    private final String TAG = MainActivity.class.getName();

    private PermissionsRequester permissionsRequester;
    private ListFragment nearmeFragment;
    private FavouritesFragment favouritesFragment;
    private LocationFragment locationFragment;
    private View fragmentContainer;

    private UnifiedModelView unifiedModelView;
    private List<ArrivalsFormatedEntity> favourites = new ArrayList<>();

    private Bundle arguments = new Bundle();

    //FAB
    FloatingActionButton fab, fabNearMe, fabFavourites;
    LinearLayout fabLayoutNearMe, fabLayoutFavourites;
    View fabBGLayout;
    boolean isFABOpen=false;


    /**
     * onCreate Method
     * <p>
     * This method handle the diversion of the traffic to
     * the favourites fragment in case that the user has some favourites.
     * And manage the menu button (floating).
     * <p>
     *
     * @param   savedInstanceState  Bundle
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nearmeFragment = new ListFragment();
        favouritesFragment = new FavouritesFragment();
        locationFragment = new LocationFragment();

        //Location permissions check and fragment to use for location
        fragmentContainer = findViewById(R.id.fragment_container);
        permissionsRequester = PermissionsRequester.newInstance(this);

        //Getting the list of favourites in order to see if there is any and show them in first place.
        unifiedModelView = ViewModelProviders.of(this).get(UnifiedModelView.class);

        unifiedModelView.getmLiveDataFavourites().observe(this, new Observer<List<ArrivalsFormatedEntity>>() {
            @Override
            public void onChanged(@Nullable List<ArrivalsFormatedEntity> favouriteEntities) {
                favourites.addAll(favouriteEntities);
                if(favourites.size()>0) {
                    arguments.putString("fav", "true");
                    locationFragment.setArguments(arguments);
                    arguments.clear();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, locationFragment).commit();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, favouritesFragment).addToBackStack("favourite").commit();
                }else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, locationFragment).commit();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, nearmeFragment).addToBackStack("nearme").commit();
                }
            }
        });


        if (savedInstanceState == null) {
            if(favourites.size()>0) {
                arguments.putString("fav", "true");
                locationFragment.setArguments(arguments);
                arguments.clear();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, locationFragment).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, favouritesFragment).addToBackStack("favourite").commit();
            }else{
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, locationFragment).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, nearmeFragment).addToBackStack("nearme").commit();
            }
        }

        //Menu Fab
        fabLayoutNearMe= (LinearLayout) findViewById(R.id.fabLayoutNearMe);
        fabLayoutFavourites= (LinearLayout) findViewById(R.id.fabLayoutFavourites);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fabNearMe = (FloatingActionButton) findViewById(R.id.fabNearMe);
        fabFavourites= (FloatingActionButton) findViewById(R.id.fabFavourites);
        fabBGLayout=findViewById(R.id.fabBGLayout);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
            }
        });

        fabNearMe.setOnClickListener(fabNearMeOnClick);
        fabFavourites.setOnClickListener(fabFavouritesOnClick);

    }


    //Handle the onclick event in the menu Favourite option
    private View.OnClickListener fabFavouritesOnClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(favourites.size()>0) {
                favouritesFragment = new FavouritesFragment();
                locationFragment = new LocationFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, favouritesFragment).addToBackStack("favouriteClick").commit();
                arguments.putString("fav", "true");
                locationFragment.setArguments(arguments);
                arguments.clear();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, locationFragment).commit();
            }else{
                Toast.makeText(getBaseContext(),"Sorry! We haven't found any favourite",Toast.LENGTH_SHORT).show();
            }
            closeFABMenu();
        }
    };

    //Handle the onclick event in the menu Near Me option
    private View.OnClickListener fabNearMeOnClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            locationFragment = new LocationFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, locationFragment).commit();
            nearmeFragment = new ListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, nearmeFragment).addToBackStack("nearmeClick").commit();
            closeFABMenu();
        }
    };

    /**
     * This method instantiate the location fragment and call the fragment.
     *
     */
    private void createLocationFragment() {
        locationFragment = (LocationFragment) Fragment.instantiate(this, LocationFragment.class.getCanonicalName());
        if(arguments.size()>0) locationFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, locationFragment).commit();

    }

    /**
     * This method request the permissions in case the user has not enough permissions or call
     * the Fragment creation method
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (!permissionsRequester.hasPermissions()) {
            permissionsRequester.requestPermissions();
        } else {
            createLocationFragment();
        }
    }

    /**
     * This method handle the permission request.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(fragmentContainer, R.string.no_permissions, Snackbar.LENGTH_LONG).show();
                finish();
                return;
            }
        }
        createLocationFragment();
    }

    /**
     * OnDestroy method
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy ");
    }

    /**
     * This method show the options of the menu
     */
    private void showFABMenu(){
        isFABOpen=true;
        fabLayoutNearMe.setVisibility(View.VISIBLE);
        fabLayoutFavourites.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);
        fab.animate().rotationBy(180);
        fabLayoutNearMe.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabLayoutFavourites.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
    }

    /**
     * This method hide the options of the menu
     */
    private void closeFABMenu(){
        isFABOpen=false;
        fabBGLayout.setVisibility(View.GONE);
        fab.animate().rotationBy(-180);
        fabLayoutNearMe.animate().translationY(0);
        fabLayoutFavourites.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }
            @Override
            public void onAnimationEnd(Animator animator) {
                if(!isFABOpen){
                    fabLayoutNearMe.setVisibility(View.GONE);
                    fabLayoutFavourites.setVisibility(View.GONE);
                }

            }
            @Override
            public void onAnimationCancel(Animator animator) {

            }
            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    /**
     * This method handle what is going on with the back button
     */
    @Override
    public void onBackPressed() {
        if(isFABOpen){
            closeFABMenu();
        }else{
            super.onBackPressed();
        }
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}
