package com.ubudu.gmaps.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ubudu.gmaps.sample.fragment.BaseFragment;
import com.ubudu.gmaps.sample.fragment.MapFragment;
import com.ubudu.gmaps.sample.util.FragmentUtils;

import butterknife.ButterKnife;

/**
 * Created by mgasztold on 09/01/2017.
 */

public class MainActivity extends AppCompatActivity implements BaseFragment.ViewController {

    public static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // hide toolbar
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        onMapFragmentRequested();
    }

    @Override
    public void onMapFragmentRequested() {
        FragmentUtils.changeFragment(this, new MapFragment(), true);
    }
}
