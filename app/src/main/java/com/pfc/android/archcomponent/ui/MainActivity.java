package com.pfc.android.archcomponent.ui;

import android.animation.Animator;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LiveData;
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


public class MainActivity extends LifecycleActivity {

    private final String TAG = MainActivity.class.getName();

    private PermissionsRequester permissionsRequester;
    private ListFragment nearmeFragment;
    FavouritesFragment favouritesFragment;
    private LocationFragment locationFragment;
    private View fragmentContainer;

    private UnifiedModelView unifiedModelView;
    private List<ArrivalsFormatedEntity> favourites = new ArrayList<>();

    //FAB
    FloatingActionButton fab, fabNearMe, fabFavourites;
    LinearLayout fabLayoutNearMe, fabLayoutFavourites;
    View fabBGLayout;
    boolean isFABOpen=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nearmeFragment = new ListFragment();
        favouritesFragment = new FavouritesFragment();

        //Location permissions check and fragment to use for location
        fragmentContainer = findViewById(R.id.fragment_container);
        permissionsRequester = PermissionsRequester.newInstance(this);

        //Getting the list of favourites in order to see if there is any if them and show them in first place.
        unifiedModelView = ViewModelProviders.of(this).get(UnifiedModelView.class);

        unifiedModelView.setmMutableLiveDataFavourites();

        unifiedModelView.getmMutableLiveDataFavourites().observe(this, new Observer<List<ArrivalsFormatedEntity>>() {
            @Override
            public void onChanged(@Nullable List<ArrivalsFormatedEntity> favouriteEntities) {
                favourites.addAll(favouriteEntities);
                if(favourites.size()>0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, favouritesFragment).addToBackStack("favourite").commit();
                }else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, nearmeFragment).addToBackStack("nearme").commit();
                }
            }
        });


        if (savedInstanceState == null) {
            if(favourites.size()>0) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, favouritesFragment).addToBackStack("favourite").commit();
            }else{
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

    private View.OnClickListener fabFavouritesOnClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(favourites.size()>0) {
                FavouritesFragment favouritesFragment = new FavouritesFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, favouritesFragment).addToBackStack("favouriteClick").commit();
            }else{
                Toast.makeText(getBaseContext(),"No favourites saved",Toast.LENGTH_SHORT).show();
            }

        }
    };

    private View.OnClickListener fabNearMeOnClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            nearmeFragment = new ListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, nearmeFragment).addToBackStack("nearmeClick").commit();
        }
    };

    //Location
    private void createLocationFragment() {
        Log.v(TAG,"createLocationFragment");
        locationFragment = (LocationFragment) Fragment.instantiate(this, LocationFragment.class.getCanonicalName());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, locationFragment).commit();

    }

    //Location
    @Override
    protected void onStart() {
        super.onStart();
        if (!permissionsRequester.hasPermissions()) {
            permissionsRequester.requestPermissions();
        } else {
            createLocationFragment();
        }
    }

    //Location
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy ");
    }

    private void showFABMenu(){
        isFABOpen=true;
        fabLayoutNearMe.setVisibility(View.VISIBLE);
        fabLayoutFavourites.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);
        fab.animate().rotationBy(180);
        fabLayoutNearMe.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabLayoutFavourites.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
    }

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
