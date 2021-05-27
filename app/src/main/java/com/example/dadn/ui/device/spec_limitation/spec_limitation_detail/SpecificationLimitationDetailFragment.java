package com.example.dadn.ui.device.spec_limitation.spec_limitation_detail;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.library.baseAdapters.BR;

import com.example.dadn.R;
import com.example.dadn.data.dto.SpecificationDetailRequest;
import com.example.dadn.data.dto.SpecificationDetailResponse;
import com.example.dadn.databinding.FragmentSpecificationLimitationDetailBinding;
import com.example.dadn.di.component.FragmentComponent;
import com.example.dadn.network.APIClient;
import com.example.dadn.ui.base.BaseFragment;

public class SpecificationLimitationDetailFragment extends
                BaseFragment<FragmentSpecificationLimitationDetailBinding, SpecificationLimitationDetailViewModel>
            implements SpecificationLimitationDetailNavigator{

    FragmentSpecificationLimitationDetailBinding mFragmentSpecificationLimitationDetailBinding;

    public static SpecificationLimitationDetailFragment newInstance(Integer id){
        SpecificationLimitationDetailFragment fragment= new SpecificationLimitationDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("spec_id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_specification_limitation_detail;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.setIsLoading(true);
        mFragmentSpecificationLimitationDetailBinding = getViewDataBinding();

        AppCompatActivity parent = (AppCompatActivity)getActivity();
        Toolbar toolbar = mFragmentSpecificationLimitationDetailBinding.specDetailToolbar;
        EditText lower_bound = mFragmentSpecificationLimitationDetailBinding.lowerBound;
        EditText upper_bound = mFragmentSpecificationLimitationDetailBinding.upperBound;
        TextView spec_detail_title = mFragmentSpecificationLimitationDetailBinding.specDetailToolbarTitle;
        parent.setSupportActionBar(toolbar);
        parent.getSupportActionBar().setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_back_btn);

        Bundle bundle = this.getArguments();

        APIClient.getRetrofit().getSpecificationDetail(bundle.getInt("spec_id"))
                .subscribeOn(mViewModel.getSchedulerProvider().io())
                .observeOn(mViewModel.getSchedulerProvider().ui())
                .subscribe(response -> {
                    Log.d("SUCCESS: ", response.toString());
                    SpecificationDetailResponse res = (SpecificationDetailResponse)response;
                    spec_detail_title.setText(res.getSpec());
                    lower_bound.setText(processValue(res.getLower_bound()));
                    upper_bound.setText(processValue(res.getUpper_bound()));
                    mViewModel.setIsLoading(false);

                }, throwable -> {
                    Log.d("ERROR: ", throwable.getMessage());
                    mViewModel.setIsLoading(false);
                });

//        mFragmentSpecificationLimitationDetailBinding.specDetailToolbarTitle.setText("AAA");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }


    private String processValue(Float v) {
        Log.d("PROCESS VALUE", v.toString());
        if (Math.floor(v) == v) {
            return v.toString().split("\\.")[0];
        }
        return v.toString();
    }

    @Override
    public void decreaseLowerBound() {
         Float value = Float.parseFloat(mFragmentSpecificationLimitationDetailBinding.lowerBound.getText().toString());
         value--;
         mFragmentSpecificationLimitationDetailBinding.lowerBound.setText(processValue(value));
    }

    @Override
    public void increaseLowerBound() {
        Float value = Float.parseFloat(mFragmentSpecificationLimitationDetailBinding.lowerBound.getText().toString());
        value++;
        mFragmentSpecificationLimitationDetailBinding.lowerBound.setText(processValue(value));
    }

    @Override
    public void decreaseUpperBound() {
        Float value = Float.parseFloat(mFragmentSpecificationLimitationDetailBinding.upperBound.getText().toString());
        value--;
        mFragmentSpecificationLimitationDetailBinding.upperBound.setText(processValue(value));
    }

    @Override
    public void increaseUpperBound() {
        Float value = Float.parseFloat(mFragmentSpecificationLimitationDetailBinding.upperBound.getText().toString());
        value++;
        mFragmentSpecificationLimitationDetailBinding.upperBound.setText(processValue(value));
    }

    @Override
    public void updateLimitation() {
        mViewModel.setIsLoading(true);
        Float lower_bound = Float.parseFloat(mFragmentSpecificationLimitationDetailBinding.lowerBound.getText().toString());
        Float upper_bound = Float.parseFloat(mFragmentSpecificationLimitationDetailBinding.upperBound.getText().toString());
        Bundle bundle = this.getArguments();
        Integer id = bundle.getInt("spec_id");
        SpecificationDetailRequest request = new SpecificationDetailRequest();

        request.setUpper_bound(upper_bound);
        request.setLower_bound(lower_bound);
        APIClient.getRetrofit().updateSpecificationDetail(id, request)
                .subscribeOn(mViewModel.getSchedulerProvider().io())
                .observeOn(mViewModel.getSchedulerProvider().ui())
                .subscribe(response -> {
                    Log.d("SUCCESS: ", response.toString());
                    mViewModel.setIsLoading(false);
                    displaySuccessfulMessage();
                }, throwable -> {
                    Log.d("ERROR: ", throwable.getMessage());
                    mViewModel.setIsLoading(false);
                });
    }

    public void displaySuccessfulMessage() {
        getActivity().runOnUiThread(() -> {
            Toast.makeText(getActivity(),
                    "Thay đổi thành công!",
                    Toast.LENGTH_SHORT).show();
        });
    }
}