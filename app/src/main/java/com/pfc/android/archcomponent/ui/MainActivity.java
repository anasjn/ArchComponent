package com.pfc.android.archcomponent.ui;

import android.animation.Animator;
import android.arch.lifecycle.LifecycleActivity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.pfc.android.archcomponent.R;
import com.pfc.android.archcomponent.util.PermissionsRequester;
import com.pfc.android.archcomponent.viewmodel.AddFavouriteViewModel;
import com.pfc.android.archcomponent.viewmodel.DetailViewModel;
import com.pfc.android.archcomponent.viewmodel.ListLocationsViewModel;
import com.pfc.android.archcomponent.viewmodel.LocationViewModel;
import com.pfc.android.archcomponent.viewmodel.UnifiedModelView;

import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends LifecycleActivity {

    private final String TAG = MainActivity.class.getName();

    private PermissionsRequester permissionsRequester;
    private LocationFragment locationFragment;
    private View fragmentContainer;

    //ViewModels used by different fragments
    private ListLocationsViewModel mViewModel;
    //private AddFavouriteViewModel afViewModel;
    private LocationViewModel lViewModel;
    //private DetailViewModel dViewModel;

    //FAB
    FloatingActionButton fab, fab1, fab2;
    LinearLayout fabLayout1, fabLayout2;
    TextView textView1,textView2;
    View fabBGLayout;
    boolean isFABOpen=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListFragment nearmeFragment = new ListFragment();
        FavouritesFragment favouritesFragment = new FavouritesFragment();


        //Location permissions check and fragment to use for location
        fragmentContainer = findViewById(R.id.fragment_container);
        permissionsRequester = PermissionsRequester.newInstance(this);

        if (savedInstanceState == null) {

            Log.v(TAG,"savedInstanceState null");
            getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, nearmeFragment).commit();
        }


        fabLayout1= (LinearLayout) findViewById(R.id.fabLayout1);
        fabLayout2= (LinearLayout) findViewById(R.id.fabLayout2);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2= (FloatingActionButton) findViewById(R.id.fab2);
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

        fab1.setOnClickListener(fab1OnClick);
        fab2.setOnClickListener(fab2OnClick);

    }

    private View.OnClickListener fab1OnClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Toast.makeText(getBaseContext(),"tecleado click 1",Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener fab2OnClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Toast.makeText(getBaseContext(),"tecleado click 2",Toast.LENGTH_SHORT).show();
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
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);

        fabBGLayout.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fabBGLayout.setVisibility(View.GONE);
        fab.animate().rotationBy(-180);
        fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(!isFABOpen){
                    fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
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
