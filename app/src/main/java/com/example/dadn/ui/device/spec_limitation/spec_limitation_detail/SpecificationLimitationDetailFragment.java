package com.example.dadn.ui.device.spec_limitation.spec_limitation_detail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.library.baseAdapters.BR;

import com.example.dadn.R;
import com.example.dadn.data.dto.SpecificationDetailResponse;
import com.example.dadn.data.dto.UpdateSpecificationRequest;
import com.example.dadn.databinding.FragmentSpecificationLimitationDetailBinding;
import com.example.dadn.di.component.FragmentComponent;
import com.example.dadn.network.APIClient;
import com.example.dadn.ui.base.BaseFragment;

public class SpecificationLimitationDetailFragment extends
                BaseFragment<FragmentSpecificationLimitationDetailBinding, SpecificationLimitationDetailViewModel>
            implements SpecificationLimitationDetailNavigator{

    FragmentSpecificationLimitationDetailBinding mFragmentSpecificationLimitationDetailBinding;

    public static SpecificationLimitationDetailFragment newInstance(String type){
        SpecificationLimitationDetailFragment fragment= new SpecificationLimitationDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
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

        APIClient.getRetrofit().getSpecificationDetail(bundle.getString("type"))
                .subscribeOn(mViewModel.getSchedulerProvider().io())
                .observeOn(mViewModel.getSchedulerProvider().ui())
                .subscribe(response -> {
                    Log.d("SUCCESS: ", response.toString());
                    spec_detail_title.setText(convertTitle(response.get(0)));
                    lower_bound.setText(processValue(response.get(0).getLower_bound()));
                    upper_bound.setText(processValue(response.get(0).getUpper_bound()));
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

    public String convertTitle(SpecificationDetailResponse type) {
        if (type.getType().equals("light")) {
            return "ÁNH SÁNG";
        }
        else if (type.getType().equals("temperature")){
            return "NHIỆT ĐỘ";
        }
        else if (type.getType().equals("moisture")) {
            return "ĐỘ ẨM ĐẤT";
        }
        else if (type.getType().equals("humidity")) {
            return "ĐỘ ẨM KHÔNG KHÍ";
        }
        return "";
    }


    private String processValue(Integer v) {
        Log.d("PROCESS VALUE", v.toString());
        return v.toString();
    }

    @Override
    public void decreaseLowerBound() {
        try {
            Integer value = Integer.parseInt(mFragmentSpecificationLimitationDetailBinding.lowerBound.getText().toString());
            value--;
            mFragmentSpecificationLimitationDetailBinding.lowerBound.setText(processValue(value));
        }
        catch (NumberFormatException e) {
            displayErrorMessage();
        }
    }

    @Override
    public void increaseLowerBound() {
        try {
            Integer value = Integer.parseInt(mFragmentSpecificationLimitationDetailBinding.lowerBound.getText().toString());
            value++;
            mFragmentSpecificationLimitationDetailBinding.lowerBound.setText(processValue(value));
        }
        catch (NumberFormatException e) {
            displayErrorMessage();
        }
    }

    @Override
    public void decreaseUpperBound() {
        try {
            Integer value = Integer.parseInt(mFragmentSpecificationLimitationDetailBinding.upperBound.getText().toString());
            value--;
            mFragmentSpecificationLimitationDetailBinding.upperBound.setText(processValue(value));
        }
        catch (NumberFormatException e) {
            displayErrorMessage();
        }
    }

    @Override
    public void increaseUpperBound() {
        try {
            Integer value = Integer.parseInt(mFragmentSpecificationLimitationDetailBinding.upperBound.getText().toString());
            value++;
            mFragmentSpecificationLimitationDetailBinding.upperBound.setText(processValue(value));
        }
        catch (NumberFormatException e) {
            displayErrorMessage();
        }

    }

    @Override
    public void updateLimitation() {
        hideKeyboard();
        mViewModel.setIsLoading(true);
        try {
            Integer lower_bound = Integer.parseInt(mFragmentSpecificationLimitationDetailBinding.lowerBound.getText().toString());
            Integer upper_bound = Integer.parseInt(mFragmentSpecificationLimitationDetailBinding.upperBound.getText().toString());
            if (lower_bound >= upper_bound) {
                mViewModel.setIsLoading(false);
                displayErrorMessage();
                return;
            }
            Bundle bundle = this.getArguments();
            String type = bundle.getString("type");
            UpdateSpecificationRequest request = new UpdateSpecificationRequest();
            request.setType(type);
            request.setUpper_bound(upper_bound);
            request.setLower_bound(lower_bound);
            APIClient.getRetrofit().updateSpecificationDetail(request)
                    .subscribeOn(mViewModel.getSchedulerProvider().io())
                    .observeOn(mViewModel.getSchedulerProvider().ui())
                    .subscribe(response -> {
                        mViewModel.setIsLoading(false);
                        Log.d("SUCCESS: ", response.toString());
//                    displaySuccessfulMessage();
                        getActivity().onBackPressed();
                    }, throwable -> {
                        Log.d("ERROR: ", throwable.getMessage());
                        mViewModel.setIsLoading(false);
                    });
        }
        catch (NumberFormatException e) {
            mViewModel.setIsLoading(false);
            displayErrorMessage();
        }
    }

    public void displaySuccessfulMessage() {
        getActivity().runOnUiThread(() -> {
            Toast.makeText(getActivity(),
                    "Thay đổi thành công!",
                    Toast.LENGTH_SHORT).show();
        });
    }

    public void displayErrorMessage() {
        getActivity().runOnUiThread(() -> {
            new AlertDialog.Builder(getActivity(), R.style.WarningAlertDialogStyle)
                    .setTitle("")
                    .setMessage("Thông số không hợp lệ, vui lòng nhập lại.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        });
    }
}
