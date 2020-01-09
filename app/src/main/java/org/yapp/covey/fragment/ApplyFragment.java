package org.yapp.covey.fragment;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.yapp.covey.R;
import org.yapp.covey.activity.MainActivity;
import org.yapp.covey.etc.CustomAppBar;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApplyFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_apply, container, false);

        TextView titleText = rootView.findViewById(R.id.tv_title);
        titleText.setText("지원 내역");


        return rootView;
    }
}
