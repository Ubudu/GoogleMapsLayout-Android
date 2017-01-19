package com.ubudu.gmaps.sample.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by mgasztold on 09/01/2017.
 */

public class BaseFragment extends Fragment {

    private ViewController mViewController;
    private FragmentActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivity = (FragmentActivity) context;

        try {
            mViewController = (ViewController) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ViewController and UbuduInterface");
        }
    }

    public Activity getContextActivity() {
        return mActivity;
    }

    public ViewController getViewController() {
        return mViewController;
    }

    public interface ViewController {
        void onMapFragmentRequested();
    }
}
