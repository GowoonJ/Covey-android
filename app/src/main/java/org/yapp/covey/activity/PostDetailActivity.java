package org.yapp.covey.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import org.yapp.covey.R;
import org.yapp.covey.databinding.LayoutPostDetailBinding;
import org.yapp.covey.etc.CustomAppBar;
import org.yapp.covey.etc.ItemPostVO;
import org.yapp.covey.util.Singleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailActivity extends AppCompatActivity {
    int postId;
    LayoutPostDetailBinding binding;
    private ItemPostVO itemPostData;
    private static String TAG = "POST DETAIL ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout_post_detail);
        binding = DataBindingUtil.setContentView(this, R.layout.layout_post_detail);

        postId = getIntent().getIntExtra("postId",0);
        Log.d(TAG, String.valueOf(postId));
        setCustomAppBar();
        getPostData(postId);
    }
    private void getPostData(int postId){
        Singleton.retrofit.postDetail(postId).enqueue(new Callback<ItemPostVO>() {
            @Override
            public void onResponse(Call<ItemPostVO> call, Response<ItemPostVO> response) {
                if (response.code()==200){
                    itemPostData = response.body();
                    Log.w(TAG, itemPostData.getTitle());
                    setPostData(itemPostData);
                }
                else if (response.code() ==404){
                    Log.d(TAG, "code 404");
                    Toast.makeText(getApplicationContext(),"해당 게시글을 찾을 수 없습니다",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ItemPostVO> call, Throwable t) {
                Log.d(TAG, "onFailure");
                Toast.makeText(getApplicationContext(),"서버 연결을 확인해주세요",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setPostData(ItemPostVO itemPostData){
        String location = itemPostData.getAddress1()+" "+itemPostData.getAddress2()+" "+itemPostData.getAddress3();
        binding.tvLocation.setText("위치\t\t"+location);
        binding.tvPay.setText("시급\t\t"+itemPostData.getPay()+"원");
        binding.tvStartToEndDate.setText("일시\t\t"+itemPostData.getStartDate()+"~"+itemPostData.getEndDate());
        binding.tvTime.setText("시간\t\t"+itemPostData.getWorkingTime());
        binding.tvContent.setText(itemPostData.getDescription());
        binding.tvTitle.setText(itemPostData.getTitle());
    }
    private void setCustomAppBar(){
        CustomAppBar customAppBar = new CustomAppBar(this, getSupportActionBar());
        customAppBar.setCustomAppBar("상세 정보");
        customAppBar.setBackClickListener(new CustomAppBar.backClickListener() {
            @Override
            public void onBackClick(View v) {
                finish();
            }
        });
    }
}