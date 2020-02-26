package org.yapp.covey.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import org.yapp.covey.R;
import org.yapp.covey.databinding.ActivitySearchAddressBinding;
import org.yapp.covey.etc.CustomAppBar;
import org.yapp.covey.model.KaKaoMapSearchModel;
import org.yapp.covey.util.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAddressActivity extends AppCompatActivity {

    private static ActivitySearchAddressBinding binding;
    private String addressStr;
    private static final String TAG = "SEARCH_ADDRESS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search_address);

        setCustomAppBar();
        binding.editSearchAddress.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    getAddress();
                    return true;
                }
                return false;
            }
        });
        binding.recyclerAddress.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        getAddress();
    }

    private void setCustomAppBar(){
        CustomAppBar customAppBar = new CustomAppBar(this, getSupportActionBar());
        customAppBar.setCustomAppBar("주소 검색");
        customAppBar.setBackClickListener(new CustomAppBar.backClickListener() {
            @Override
            public void onBackClick(View v) {
                finish();
            }
        });
    }

    private void getAddress(){
        addressStr = binding.editSearchAddress.getText().toString();
        Singleton.KakaoMaoApi.categoryList(1,addressStr).enqueue(new Callback<KaKaoMapSearchModel>() {
            @Override
            public void onResponse(Call<KaKaoMapSearchModel> call, Response<KaKaoMapSearchModel> response) {

                Log.d(TAG, "response success"+ response.code());
            }

            @Override
            public void onFailure(Call<KaKaoMapSearchModel> call, Throwable t) {
                Log.d(TAG, "OnFailure"+ t.toString());
            }
        });
    }

}
