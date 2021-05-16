package com.example.dadn.ui.instruction;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dadn.BR;
import com.example.dadn.R;
import com.example.dadn.databinding.FragmentDeviceBinding;
import com.example.dadn.databinding.FragmentInstructionBinding;
import com.example.dadn.di.component.FragmentComponent;
import com.example.dadn.ui.base.BaseFragment;

import java.util.ArrayList;


public class InstructionFragment extends BaseFragment<FragmentInstructionBinding, InstructionViewModel> implements InstructionNavigator {

    private RecyclerView mRecyclerView;
    private InstructionAdapter mAdapter;
    FragmentInstructionBinding mFragmentInstructionBinding;
    private ArrayList<InstructionItem> instructionList;


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_instruction;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_instruction, container, false);
        mRecyclerView = rootView.findViewById(R.id.recyclerView_InstructionsFrag);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new InstructionAdapter(getContext());

        instructionList.add(new InstructionItem(R.drawable.user, "Title", "Details"));
        instructionList.add(new InstructionItem(R.drawable.user, "Title", "Details"));

        mAdapter.setData(instructionList);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentInstructionBinding = getViewDataBinding();
    }

}
