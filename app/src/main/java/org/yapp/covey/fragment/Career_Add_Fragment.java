package org.yapp.covey.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import org.yapp.covey.R;
import org.yapp.covey.activity.CareerActivity;
import org.yapp.covey.activity.SettingActivity;
import org.yapp.covey.etc.careerClass;
import org.yapp.covey.util.Singleton;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Career_Add_Fragment extends Fragment implements View.OnClickListener{
    private Spinner careerSpinner;
    private RelativeLayout careerAddButton;
    private EditText careerCompany, careerTitle, careerTime;
    private final String TAG = "careerAdd";
    ArrayList<String> spinnerItems = new ArrayList<String>();

    public static Career_Add_Fragment newInstance() {
        return new Career_Add_Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_career_add, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ((CareerActivity)getActivity()).setCustomAppBar("경력사항 추가");

        spinnerItems.add("주");
        spinnerItems.add("개월");
        spinnerItems.add("년");
        careerSpinner = view.findViewById(R.id.career_spinner);
        careerAddButton = view.findViewById((R.id.career_edit_button));
        careerCompany = view.findViewById((R.id.career_edit_company));
        careerTitle = view.findViewById((R.id.career_edit_title));
        careerTime = view.findViewById((R.id.career_edit_time));


        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        careerSpinner.setAdapter(spinnerAdapter);

        careerCompany.addTextChangedListener(careerTextWatcher());
        careerTitle.addTextChangedListener(careerTextWatcher());
        careerTime.addTextChangedListener(careerTextWatcher());

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /*case R.id.setting_app_info:{
                ((SettingActivity)getActivity()).replaceFragment(Setting_AppInfo_Fragment.newInstance());
                break;
            }*/
        }
    }

    private TextWatcher careerTextWatcher(){
        return new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(careerCompany.getText().length() >= 1 && careerTitle.getText().length() >= 1 && careerTime.getText().length() >= 1) {
                    careerAddButton.setBackground(getResources().getDrawable(R.drawable.red_square));
                    careerAddButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            careerClass newCareer = new careerClass();
                            newCareer.setJob(careerTitle.getText().toString());
                            newCareer.setName(careerCompany.getText().toString());
                            newCareer.setPeriodNum(careerTime.getText().toString());
                            newCareer.setPeriodUnit(careerSpinner.getSelectedItem().toString());
                            addCareer(newCareer);
                        }
                    });
                }
                else {
                    careerAddButton.setBackground(getResources().getDrawable(R.drawable.gray_square));
                    careerAddButton.setOnClickListener(null);
                }
            }
            @Override
            public void afterTextChanged(Editable arg0) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        };
    }

    private void addCareer(careerClass body){
        Singleton.retrofit.addCareer(body).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Log.w(TAG, String.valueOf(response.code()));
                    if (response.code()==201){
                        Fragment next = Career_Main_Fragment.newInstance();
                        ((CareerActivity)getActivity()).replaceFragment(next);
                    }
                    else
                        Log.w(TAG, String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.w(TAG,"OnFailure phoneVerifyCheck");
            }
        });
    }
}
