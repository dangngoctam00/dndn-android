package com.example.dadn.ui.instruction;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
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
    private ArrayList<InstructionItem> instructionList = new ArrayList<InstructionItem>();


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

    public void setInstructionList(){
        instructionList.add(new InstructionItem(R.drawable.instruction_img, "Quản lý lượng nước",
                "Trong 7 – 10 ngày đầu tiên sau khi trồng cây cà chua cần tưới đều đặn..."));
        instructionList.add(new InstructionItem(R.drawable.instruction_img, "Điều kiện ánh sáng",
                "Ánh sáng cho cây con là một trong những điều kiện quan trọng..."));
        instructionList.add(new InstructionItem(R.drawable.instruction_img, "Điều kiện ánh sáng",
                " Cà chua là cây chịu úng kém, nhưng yêu cầu độ ẩm..."));
        instructionList.add(new InstructionItem(R.drawable.instruction_img, "Lượng phân bón",
                "Ure: 30kg, NPK 16 - 16 - 8: 25kg, sulphat kali..."));
        instructionList.add(new InstructionItem(R.drawable.instruction_img, "Gieo hạt",
                "Trung bình gieo 100 - 150g hạt cho 1 ha..."));
        instructionList.add(new InstructionItem(R.drawable.instruction_img, "Bấm ngọn và tỉa cành",
                "Tùy thuộc vào từng giống cây cà chua..."));
        instructionList.add(new InstructionItem(R.drawable.instruction_img, "Quản lý lượng nước",
                "Trong 7 – 10 ngày đầu tiên sau khi trồng cây cà chua cần tưới đều đặn..."));
        instructionList.add(new InstructionItem(R.drawable.instruction_img, "Vun xới",
                "Từ khi trồng đến khi cây được 20 ngày cần vun... "));
        instructionList.add(new InstructionItem(R.drawable.instruction_img, "Thu hoạch cà chua",
                "Thu hoạch khi cà chua chuyển sang màu vàng hoặc đỏ..."));

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentInstructionBinding = getViewDataBinding();

        mRecyclerView = view.findViewById(R.id.recyclerView_InstructionsFrag);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new InstructionAdapter(getContext());

        setInstructionList();

        mAdapter.setData(instructionList);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

}
