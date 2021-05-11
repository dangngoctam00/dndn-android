package com.example.dadn.di.module;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dadn.ViewModelProviderFactory;
import com.example.dadn.ui.base.BaseFragment;
import com.example.dadn.ui.device.DeviceViewModel;
import com.example.dadn.ui.home.HomeViewModel;
import com.example.dadn.ui.instruction.InstructionViewModel;
import com.example.dadn.ui.setting.SettingViewModel;
import com.example.dadn.ui.statistic.StatisticViewModel;
import com.example.dadn.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;


@Module
public class FragmentModule {

    private BaseFragment<?,?> fragment;

    public FragmentModule(BaseFragment<?,?> fragment) {
        this.fragment = fragment;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
    HomeViewModel provideHomeViewModel(SchedulerProvider schedulerProvider) {
        Supplier<HomeViewModel> supplier = () -> new HomeViewModel(schedulerProvider);
        ViewModelProviderFactory<HomeViewModel> factory = new ViewModelProviderFactory<>(HomeViewModel.class, supplier);
        return new ViewModelProvider((ViewModelStoreOwner) fragment, factory).get(HomeViewModel.class);
    }

    @Provides
    DeviceViewModel provideDeviceViewModel(SchedulerProvider schedulerProvider) {
        Supplier<DeviceViewModel> supplier = () -> new DeviceViewModel(schedulerProvider);
        ViewModelProviderFactory<DeviceViewModel> factory = new ViewModelProviderFactory<>(DeviceViewModel.class, supplier);
        return new ViewModelProvider((ViewModelStoreOwner) fragment, factory).get(DeviceViewModel.class);
    }

    @Provides
    SettingViewModel provideSettingViewModel(SchedulerProvider schedulerProvider) {
        Supplier<SettingViewModel> supplier = () -> new SettingViewModel(schedulerProvider);
        ViewModelProviderFactory<SettingViewModel> factory = new ViewModelProviderFactory<>(SettingViewModel.class, supplier);
        return new ViewModelProvider((ViewModelStoreOwner) fragment, factory).get(SettingViewModel.class);
    }

    @Provides
    InstructionViewModel provideInstructionViewModel(SchedulerProvider schedulerProvider) {
        Supplier<InstructionViewModel> supplier = () -> new InstructionViewModel(schedulerProvider);
        ViewModelProviderFactory<InstructionViewModel> factory = new ViewModelProviderFactory<>(InstructionViewModel.class, supplier);
        return new ViewModelProvider((ViewModelStoreOwner) fragment, factory).get(InstructionViewModel.class);
    }

    @Provides
    StatisticViewModel provideStatisticViewModel(SchedulerProvider schedulerProvider) {
        Supplier<StatisticViewModel> supplier = () -> new StatisticViewModel(schedulerProvider);
        ViewModelProviderFactory<StatisticViewModel> factory = new ViewModelProviderFactory<>(StatisticViewModel.class, supplier);
        return new ViewModelProvider((ViewModelStoreOwner) fragment, factory).get(StatisticViewModel.class);
    }
}
