package com.example.dadn.ui.device.spec_limitation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dadn.R;
import com.example.dadn.databinding.FragmentSpecificationLimitationBinding;
import com.example.dadn.di.component.FragmentComponent;
import com.example.dadn.network.APIClient;
import com.example.dadn.ui.base.BaseFragment;
import com.example.dadn.ui.device.spec_limitation.spec_limitation_detail.SpecificationLimitationDetailFragment;
import com.example.dadn.utils.ItemClickSupport;

import javax.inject.Inject;


public class SpecificationLimitationFragment extends BaseFragment<FragmentSpecificationLimitationBinding, SpecificationLimitationViewModel> {

    FragmentSpecificationLimitationBinding mFragmentSpecificationLimitationBinding;

    @Inject
    SpecificationsAdapter mSpecificationsAdapter;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_specification_limitation;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentSpecificationLimitationBinding = getViewDataBinding();

        mViewModel.setIsLoading(true);

        AppCompatActivity parent = (AppCompatActivity)getActivity();
        Toolbar toolbar = mFragmentSpecificationLimitationBinding.specLimitationToolbar;
        parent.setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_back_btn);
        parent.getSupportActionBar().setTitle("");
        mFragmentSpecificationLimitationBinding.toolbarTitle.setText("THIẾT LẬP THÔNG SỐ");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        setup();

        APIClient.getRetrofit().getSpecifications()
                .subscribeOn(mViewModel.getSchedulerProvider().io())
                .observeOn(mViewModel.getSchedulerProvider().ui())
                .subscribe(response -> {
                    Log.d("SUCCESS: ", response.toString());
                    mSpecificationsAdapter.setSpecs(response);
                    mViewModel.setIsLoading(false);

                }, throwable -> {
                    Log.d("ERROR: ", throwable.getMessage());
                    mViewModel.setIsLoading(false);
                });

        ItemClickSupport.addTo(mFragmentSpecificationLimitationBinding.rvSpecs).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Fragment fragment = SpecificationLimitationDetailFragment.newInstance(getIdSpecs(position));
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment, fragment.toString());
                        fragmentTransaction.addToBackStack(fragment.toString());
                        fragmentTransaction.commit();
                    }
                });

    }

    public void setup() {
        mFragmentSpecificationLimitationBinding.rvSpecs.setLayoutManager(new LinearLayoutManager(getContext()));
        mFragmentSpecificationLimitationBinding.rvSpecs.setAdapter(mSpecificationsAdapter);

    }

    private int getIdSpecs(int pos) {
        return mSpecificationsAdapter.getSpecs().get(pos).getId();
    }
}
