
package com.example.dadn.ui.statistic;



import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.library.baseAdapters.BR;

import com.example.dadn.R;
import com.example.dadn.databinding.FragmentStatisticBinding;
import com.example.dadn.di.component.FragmentComponent;
import com.example.dadn.ui.base.BaseFragment;
import com.example.dadn.utils.Constants;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Float.parseFloat;
import static java.lang.Float.sum;
import static java.lang.Integer.parseInt;


public class StatisticFragment extends BaseFragment<FragmentStatisticBinding, StatisticViewModel> implements StatisticNavigator {

    FragmentStatisticBinding mFragmentStatisticBinding;
    String USERNAME = Constants.USERNAME;
    Boolean ISHOUR = false;
    int DURATION = 1;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_statistic;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentStatisticBinding = getViewDataBinding();

        AppCompatActivity parent = (AppCompatActivity)getActivity();
        Toolbar toolbar = mFragmentStatisticBinding.statisticToolbar;
        parent.setSupportActionBar(toolbar);
        parent.getSupportActionBar().setTitle("");
        mFragmentStatisticBinding.toolbarTitle.setText("THỐNG KÊ DỮ LIỆU");

        String[] specs = new String[] {"Ánh sáng", "Nhiệt độ và " + "Độ ẩm không khí", "Độ ẩm đất"};
        Spinner spinner = mFragmentStatisticBinding.specificationSpinner;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_selected, specs);
        adapter.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spinner.setAdapter(adapter);

        String[] time_durations = new String[] {"Giờ", "Ngày", "Tuần", "Tháng"};
        Spinner spinner_time_duration = mFragmentStatisticBinding.timeDurationSpinner;
        ArrayAdapter<String> adapter_time_duration = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_selected, time_durations);
        adapter_time_duration.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spinner_time_duration.setAdapter(adapter_time_duration);

        LineChart mLineChart = view.findViewById(R.id.lineChart);
        mLineChart.setNoDataText("");
        mLineChart.clear();

    }

    @Override
    public void get_data(){
        LineChart mLineChart = getView().findViewById(R.id.lineChart);
        mLineChart.setNoDataText("");
        mLineChart.clear();

        SimpleDateFormat curr_time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        String end_time = curr_time.format(new Timestamp(System.currentTimeMillis()));

        Calendar c = Calendar.getInstance();
        Date date= new Date();
        c.setTime(date);
        c.add(Calendar.HOUR, -7);

        Spinner getUnit = getView().findViewById(R.id.time_duration_spinner);
        String unit = getUnit.getSelectedItem().toString();

        TextInputEditText getDuration = getView().findViewById(R.id.et_time_duration);
        String duration = getDuration.getText().toString();

        Spinner getatt = getView().findViewById(R.id.specification_spinner);
        String att = getatt.getSelectedItem().toString();
        String feed_key;
        if (att.equals("Độ ẩm đất")) feed_key = "bk-iot-soil";
        else if (att.equals("Ánh sáng")) feed_key = "bk-iot-light";
        else feed_key = "bk-iot-temp-humid";

        if (unit.equals("Giờ")) c.add(Calendar.HOUR, -parseInt(duration));
        else if (unit.equals("Tuần")) c.add(Calendar.WEEK_OF_YEAR, -parseInt(duration));
        else if (unit.equals("Ngày")) c.add(Calendar.DATE, -parseInt(duration));
        else if (unit.equals("Tháng")) c.add(Calendar.MONTH, -parseInt(duration));

        if (unit.equals("Giờ")) ISHOUR = true;
        else ISHOUR = false;

        if (unit.equals("Giờ")) DURATION = 1;
        else DURATION = 1;

        String start_time = curr_time.format(c.getTime());

        Call<List<ResultFeedChart>> call = RetrofitClientChart.getInstance()
                .getApi().getChartData(USERNAME, feed_key, start_time, end_time);

        call.enqueue(new Callback<List<ResultFeedChart>>() {
            @Override
            public void onResponse(Call<List<ResultFeedChart>> call, Response<List<ResultFeedChart>> response) {
                Log.w("response/", " " + response.toString());
                if (response.body().isEmpty()) waitFor();
                else {
                    List<ResultFeedChart> datas = response.body();
                    try {
                        analysisData(datas);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResultFeedChart>> call, Throwable t) {
                Log.w("Debug chart/", "failure " + t.toString());

            }
        });

    }

    public void analysisData(List<ResultFeedChart> datas) throws ParseException {
        String CHARTNAME = "";

        ArrayList<String> CHARTDATA = new ArrayList<>();
        ArrayList<String> CHARTTIME = new ArrayList<>();
        try {
            CHARTNAME = new JSONObject(datas.get(0).getValue()).getString("name");

            for (ResultFeedChart data: datas) {
                JSONObject jsonObject = new JSONObject(data.getValue());
                CHARTDATA.add(jsonObject.getString("data"));
                CHARTTIME.add(data.getTime());
            }
        } catch (Exception e){
            Log.w("analysis data", "error" + e.toString());
        }
        if (CHARTNAME.equals("TEMP-HUMID")) createChartTempHumid2(CHARTDATA, CHARTTIME);
        else if (CHARTNAME.equals("LIGHT")) createChart2(CHARTDATA, CHARTTIME, "Ánh sáng");
        else createChart2(CHARTDATA, CHARTTIME, "Độ ẩm đất");
    }

    public void createChartTempHumid(ArrayList<String> CHARTDATA, ArrayList<String> CHARTTIME)
            throws ParseException {
        LineChart mLineChart = getView().findViewById(R.id.lineChart);
        mLineChart.setNoDataText("");
        ArrayList tempEntries = new ArrayList<>();
        ArrayList humidEntries = new ArrayList<>();
        Hashtable<Long, String> dateX = new Hashtable<Long, String>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start_time = sdf.parse(CHARTTIME.get(CHARTDATA.size() - 1).replace("T", " ")
                .replace("Z",""));

        long count_date = 0;
        for (int i = CHARTDATA.size() - 1; i >= 0; i--){
            String[] value = CHARTDATA.get(i).split("-");

            Date next_time = sdf.parse(CHARTTIME.get(i).replace("T", " ")
                    .replace("Z",""));
            long diff = (next_time.getTime() - start_time.getTime()) / 1000;
            long diff_date = TimeUnit.DAYS.convert(diff, TimeUnit.SECONDS);

            if (diff_date == count_date){
                count_date++;
                dateX.put(diff, CHARTTIME.get(i).split("T")[0]);
            }
            else dateX.put(diff, "");

            tempEntries.add(new Entry(diff, parseFloat(value[0])));
            humidEntries.add(new Entry(diff, parseFloat(value[1])));
        }

        LineData lineData = new LineData();
        LineDataSet tempLineDataSet = new LineDataSet(tempEntries, "Nhiệt độ");
        tempLineDataSet.setColor(Color.parseColor("#fff9dcd4"));
        lineData.addDataSet(tempLineDataSet);

        LineDataSet humidLineDataSet = new LineDataSet(humidEntries, "Độ ẩm không khí");
        humidLineDataSet.setColor(Color.parseColor("#fffa5452"));
        lineData.addDataSet(humidLineDataSet);

        XAxis xAxis = mLineChart.getXAxis();
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (String.valueOf(dateX.get((long)value)).length() == 10) return String.valueOf(dateX.get((long)value));
                else return "";
            }
        };
        xAxis.setValueFormatter(formatter);

        mLineChart.setData(lineData);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.getXAxis().setDrawLabels(true);

        mLineChart.invalidate();
    }

    public void createChart(ArrayList<String> CHARTDATA, ArrayList<String> CHARTTIME, String name)
            throws ParseException {
        LineChart mLineChart = getView().findViewById(R.id.lineChart);
        mLineChart.setNoDataText("");
        ArrayList lineEntries = new ArrayList<>();
        Hashtable<Long, String> dateX = new Hashtable<Long, String>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start_time = sdf.parse(CHARTTIME.get(CHARTDATA.size() - 1).replace("T", " ")
                .replace("Z",""));

        long count_date = 0;
        for (int i = CHARTDATA.size() - 1; i >= 0; i--){
            Date next_time = sdf.parse(CHARTTIME.get(i).replace("T", " ")
                    .replace("Z",""));

            long diff = (next_time.getTime() - start_time.getTime()) / 1000;
            long diff_date = TimeUnit.DAYS.convert(diff, TimeUnit.SECONDS);

            if (diff_date == count_date){
                count_date++;
                dateX.put(diff, CHARTTIME.get(i).split("T")[0]);
            }
            else dateX.put(diff, "");

            lineEntries.add(new Entry(diff, parseFloat(CHARTDATA.get(i))));
        }
        LineDataSet dataSet = new LineDataSet(lineEntries, name);
        LineData lineData = new LineData(dataSet);

        XAxis xAxis = mLineChart.getXAxis();
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (String.valueOf(dateX.get((long)value)).length() == 10) return String.valueOf(dateX.get((long)value));
                else return "";
            }
        };
        xAxis.setValueFormatter(formatter);

        mLineChart.setData(lineData);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.getXAxis().setDrawLabels(true);
        //mLineChart.getLineData().setDrawValues(false);

        mLineChart.invalidate();
    }

    public void waitFor(){
        LineChart mLineChart = getView().findViewById(R.id.lineChart);
        mLineChart.clear();
        mLineChart.setNoDataText("Không có dữ liệu trong khoảng thời gian này!");
    }

    public void createChart2(ArrayList<String> CHARTDATA, ArrayList<String> CHARTTIME, String name)
            throws ParseException {
        LineChart mLineChart = getView().findViewById(R.id.lineChart);
        mLineChart.setNoDataText("");
        ArrayList lineEntries = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfX = new SimpleDateFormat("HH:mm");
        if (!ISHOUR) sdfX = new SimpleDateFormat("dd-MM");

        Date start_time = sdf.parse(CHARTTIME.get(CHARTDATA.size() - 1).replace("T", " ")
                .replace("Z",""));

        long count_data = 0;
        long diff = 0;
        float count_entry = 0;
        float sum_data = 0;
        ArrayList<String> timeX = new ArrayList<>();
        Date x = new Date();
        Calendar c = Calendar.getInstance();

        for (int i = CHARTDATA.size() - 1; i >= 0; i--){
            Date next_time = sdf.parse(CHARTTIME.get(i).replace("T", " ")
                    .replace("Z",""));
            diff = (next_time.getTime() - start_time.getTime()) / 1000;
            diff = TimeUnit.HOURS.convert(diff, TimeUnit.SECONDS);
            if (!ISHOUR) diff = TimeUnit.DAYS.convert(diff, TimeUnit.SECONDS);

            sum_data = sum_data + parseFloat(CHARTDATA.get(i));
//            Log.w("createChart2", " " + diff + " " + sum_data);

            count_data++;
            x = sdf.parse(CHARTTIME.get(i).replace("T", " ")
                    .replace("Z", ""));
            c.setTime(x);
            c.add(Calendar.HOUR, 7);
            x = c.getTime();

            if (diff == DURATION && i != 0){
                start_time = next_time;
                lineEntries.add(new Entry(count_entry++, sum_data / count_data));
                count_data = 0;
                sum_data = 0;
                timeX.add(sdfX.format(x));
            }
            else if (diff > DURATION){
                sum_data = sum_data - parseFloat(CHARTDATA.get(i));
                start_time = next_time;
                count_data--;
                lineEntries.add(new Entry(count_entry++, sum_data / count_data));
                count_data = 1;
                sum_data = parseFloat(CHARTDATA.get(i));

                c.setTime(sdf.parse(CHARTTIME.get(i+1).replace("T", " ")
                        .replace("Z", "")));
                c.add(Calendar.HOUR, 7);

                timeX.add(sdfX.format(c.getTime()));
            }
            if (i == 0){
                lineEntries.add(new Entry(count_entry++, sum_data / count_data));
                timeX.add(sdfX.format(x));
            }
        }
        Log.w("create chart 2", lineEntries.toString());
        Log.w("create chart 2", "timeX" + timeX.toString());

        LineDataSet dataSet = new LineDataSet(lineEntries, name);
        LineData lineData = new LineData(dataSet);
        XAxis xAxis = mLineChart.getXAxis();
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                try {
                    if (value - (int) value == 0) return timeX.get((int) value);
                    else return "";
                } catch (Exception e){
                    Log.w("createChart2:", "exception xaxis "+e.toString());
                    return "";
                }
            }
        };
        xAxis.setValueFormatter(formatter);
        mLineChart.setData(lineData);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.getXAxis().setDrawLabels(true);
        mLineChart.invalidate();
    }

    public void createChartTempHumid2(ArrayList<String> CHARTDATA, ArrayList<String> CHARTTIME)
            throws ParseException {
        LineChart mLineChart = getView().findViewById(R.id.lineChart);
        mLineChart.setNoDataText("");
        ArrayList tempEntries = new ArrayList<>();
        ArrayList humidEntries = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfX = new SimpleDateFormat("HH:mm");
        if (!ISHOUR) sdfX = new SimpleDateFormat("dd-MM");
        Date start_time = sdf.parse(CHARTTIME.get(CHARTDATA.size() - 1).replace("T", " ")
                .replace("Z",""));

        long count_data = 0;
        long diff = 0;
        float count_entry = 0;
        float sum_temp = 0;
        float sum_hump = 0;
        ArrayList<String> timeX = new ArrayList<>();
        Date x = new Date();
        Calendar c = Calendar.getInstance();

        for (int i = CHARTDATA.size() - 1; i >= 0; i--){
            String[] value = CHARTDATA.get(i).split("-");
            Date next_time = sdf.parse(CHARTTIME.get(i).replace("T", " ")
                    .replace("Z",""));
            diff = (next_time.getTime() - start_time.getTime()) / 1000;
            diff = TimeUnit.HOURS.convert(diff, TimeUnit.SECONDS);
            if (!ISHOUR) diff = TimeUnit.DAYS.convert(diff, TimeUnit.SECONDS);

            sum_temp = sum_temp + parseFloat(value[0]);
            sum_hump = sum_hump + parseFloat(value[1]);

            count_data++;
            x = sdf.parse(CHARTTIME.get(i).replace("T", " ")
                    .replace("Z", ""));
            c.setTime(x);
            c.add(Calendar.HOUR, 7);
            x = c.getTime();

            if (diff == DURATION && i != 0){
                start_time = next_time;
                tempEntries.add(new Entry(count_entry, sum_temp / count_data));
                humidEntries.add(new Entry(count_entry++, sum_hump / count_data));
                count_data = 0;
                sum_hump = 0;
                sum_temp = 0;
                timeX.add(sdfX.format(x));
            }
            else if (diff > DURATION){
                sum_temp = sum_temp - parseFloat(value[0]);
                sum_hump = sum_hump - parseFloat(value[1]);
                start_time = next_time;
                count_data--;
                tempEntries.add(new Entry(count_entry, sum_temp / count_data));
                humidEntries.add(new Entry(count_entry++, sum_hump / count_data));
                count_data = 1;
                sum_temp = parseFloat(value[0]);
                sum_hump = parseFloat(value[1]);

                c.setTime(sdf.parse(CHARTTIME.get(i+1).replace("T", " ")
                        .replace("Z", "")));
                c.add(Calendar.HOUR, 7);

                timeX.add(sdfX.format(c.getTime()));
            }
            if (i == 0){
                tempEntries.add(new Entry(count_entry, sum_temp / count_data));
                humidEntries.add(new Entry(count_entry++, sum_hump / count_data));
                timeX.add(sdfX.format(x));
            }
        }
        Log.w("create chart 2", "timeX" + timeX.toString());

        LineData lineData = new LineData();
        LineDataSet tempLineDataSet = new LineDataSet(tempEntries, "Nhiệt độ");
        tempLineDataSet.setColor(Color.parseColor("#fff9dcd4"));
        lineData.addDataSet(tempLineDataSet);

        LineDataSet humidLineDataSet = new LineDataSet(humidEntries, "Độ ẩm không khí");
        humidLineDataSet.setColor(Color.parseColor("#fffa5452"));
        lineData.addDataSet(humidLineDataSet);

        XAxis xAxis = mLineChart.getXAxis();
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                try {
                    if (value - (int) value == 0) return timeX.get((int) value);
                    else return "";
                } catch (Exception e){
                    Log.w("createChart2:", "exception xaxis "+e.toString());
                    return "";
                }
            }
        };
        xAxis.setValueFormatter(formatter);
        mLineChart.setData(lineData);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.getXAxis().setDrawLabels(true);
        mLineChart.invalidate();
    }
}
