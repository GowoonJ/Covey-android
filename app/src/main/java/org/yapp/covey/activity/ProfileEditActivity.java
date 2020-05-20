package org.yapp.covey.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.yapp.covey.R;
import org.yapp.covey.etc.CustomAppBar;
import org.yapp.covey.etc.profileEditClass;
import org.yapp.covey.etc.userClass;
import org.yapp.covey.etc.userResponseClass;
import org.yapp.covey.util.Singleton;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEditActivity extends AppCompatActivity {
    private userResponseClass userResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        setCustomAppBar();

        getUser();

        final Context mContext = getApplicationContext();
        Spinner locationSpinner = findViewById(R.id.profile_edit_spinner_location);
        final Spinner locationDetailSpinner = findViewById(R.id.profile_edit_spinner_location_detail);
        String[] locationStr = getResources().getStringArray(R.array.city);
        EditText intro = findViewById(R.id.profile_edit_introduction);
        RelativeLayout editProfile = findViewById(R.id.profile_edit_button);


        TextView name = findViewById(R.id.profile_edit_name);
        TextView gender = findViewById(R.id.profile_edit_gender);
        TextView age = findViewById(R.id.profile_edit_age);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileEditClass body = new profileEditClass();
                body.setName(name.getText().toString());
                if(gender.getText().toString().equals("남"))
                    body.setGender(true);
                else
                    body.setGender(false);
                body.setAge(age.getText().toString());
                body.setAddress1(locationSpinner.getSelectedItem().toString());
                body.setAddress2(locationDetailSpinner.getSelectedItem().toString());
                body.setIntro(intro.getText().toString());
                editUser(body);
            }
        });

        ArrayAdapter<String> locationAdapter= new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item, locationStr);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationAdapter);
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String[] locationDetailStr = null;
                switch(position) {
                    case 0:
                        locationDetailStr = getResources().getStringArray(R.array.seoul);
                        break;
                    case 1:
                        locationDetailStr = getResources().getStringArray(R.array.kyung_gi);
                        break;
                    case 2:
                        locationDetailStr = getResources().getStringArray(R.array.incheon);
                        break;
                    case 3:
                        locationDetailStr = getResources().getStringArray(R.array.busan);
                        break;
                    case 4:
                        locationDetailStr = getResources().getStringArray(R.array.daegu);
                        break;
                    case 5:
                        locationDetailStr = getResources().getStringArray(R.array.daejeon);
                        break;
                    case 6:
                        locationDetailStr = getResources().getStringArray(R.array.ulsan);
                        break;
                    case 7:
                        locationDetailStr = getResources().getStringArray(R.array.gwangju);
                        break;
                    case 8:
                        locationDetailStr = getResources().getStringArray(R.array.gangwon);
                        break;
                    case 9:
                        locationDetailStr = getResources().getStringArray(R.array.chungnam);
                        break;
                    case 10:
                        locationDetailStr = getResources().getStringArray(R.array.chungbuk);
                        break;
                    case 11:
                        locationDetailStr = getResources().getStringArray(R.array.jeonnam);
                        break;
                    case 12:
                        locationDetailStr = getResources().getStringArray(R.array.jeonbuk);
                        break;
                    case 13:
                        locationDetailStr = getResources().getStringArray(R.array.kyungnam);
                        break;
                    case 14:
                        locationDetailStr = getResources().getStringArray(R.array.kyungbuk);
                        break;
                    case 15:
                        locationDetailStr = getResources().getStringArray(R.array.jeju);
                }
                ArrayAdapter<String> locationDetailAdapter= new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, locationDetailStr);
                locationDetailAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                locationDetailSpinner.setAdapter(locationDetailAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }
    public void setCustomAppBar(){
        CustomAppBar customAppBar = new CustomAppBar(this, getSupportActionBar());
        customAppBar.setCustomAppBar("내 프로필 수정");
        customAppBar.setBackClickListener(new CustomAppBar.backClickListener() {
            @Override
            public void onBackClick(View v) {
                finish();
            }
        });
    }

    private void getUser(){
        Singleton.retrofit.getUser().enqueue(new Callback<userResponseClass>() {
            @Override
            public void onResponse(Call<userResponseClass> call, Response<userResponseClass> response) {
                if (response.isSuccessful()){
                    if (response.code()==200){
                        TextView name = findViewById(R.id.profile_edit_name);
                        TextView gender = findViewById(R.id.profile_edit_gender);
                        TextView age = findViewById(R.id.profile_edit_age);
                        TextView phone = findViewById(R.id.profile_edit_phone);
                        EditText intro = findViewById(R.id.profile_edit_introduction);

                        name.setText(response.body().getUser().getName());
                        age.setText(response.body().getUser().getAge());
                        phone.setText(response.body().getUser().getPhoneNum());
                        if(response.body().getUser().isGender())
                            gender.setText("남");
                        else
                            gender.setText("여");
                        intro.setText(response.body().getUser().getIntro());

                        userResponse = response.body();
                    }
                    else;
                    //Log.w(TAG, String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<userResponseClass> call, Throwable t) {
                //Log.w(TAG,"OnFailure phoneVerify");
            }
        });
    }

    private void editUser(profileEditClass body){
        Singleton.retrofit.editUser(body).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.w("asd", String.valueOf(response.code()));
                if (response.isSuccessful()){
                    if (response.code()==201){
                        Log.d("201", "success");
                        finish();
                    }
                    else
                        Log.w("asd", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.w("asd","OnFailure");
            }
        });
    }
}
