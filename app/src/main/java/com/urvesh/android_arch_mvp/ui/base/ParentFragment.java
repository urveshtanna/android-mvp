package com.urvesh.android_arch_mvp.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urvesh.android_arch_mvp.R;

import java.lang.reflect.Field;

/**
 * Created by urveshtanna on 21/01/17.
 */

public class ParentFragment extends Fragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ParentFragment.
     */
    public static ParentFragment newInstance() {
        return new ParentFragment();
    }

    public ParentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        try {
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
                fragmentManager.executePendingTransactions();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Write all Fragment Transaction methord here
     */
    public void doFragmentTransaction(ParentActivity activity, int containerViewId, ParentFragment destinationFragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        fragmentTransaction.replace(containerViewId, destinationFragment);
        //add the fragment to backstak
        fragmentTransaction.addToBackStack(fragmentManager.getFragments().getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    public void doFragmentTransaction(int containerViewId, ParentFragment destinationFragment) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(containerViewId, destinationFragment);
        //add the fragment to backstack
        fragmentTransaction.addToBackStack(fragmentManager.getFragments().getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    public void removeFragment(ParentFragment fragment) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();

    }

}
