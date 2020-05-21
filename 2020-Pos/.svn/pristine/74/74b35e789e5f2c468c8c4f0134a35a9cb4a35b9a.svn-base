package com.epro.pos.ui.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.epro.pos.R;
import com.epro.pos.mvp.model.bean.PrinterBean;
import com.epro.pos.ui.adapter.PrinterListAdapter;
import com.mike.baselib.activity.BaseSimpleActivity;

import java.util.ArrayList;

import static com.epro.pos.mvp.model.bean.PrinterBean.PRINT_TYPE;


/**
 * 类说明:打印的页面
 * 阳（360621904@qq.com）  2017/4/27  19:56
 */

public class PosPrinterListActivity extends BaseSimpleActivity {

    private static final int MY_PERMISSION_REQUEST_CONSTANT = 100;
    //设备列表
    private ListView listView;
    private ArrayList<PrinterBean> mBluetoothDevicesDatas;
    private PrinterListAdapter adapter;
    //蓝牙适配器
    private BluetoothAdapter mBluetoothAdapter;
    //请求的code
    public static final int REQUEST_ENABLE_BT = 1;

    private Switch mSwitch;
    private FloatingActionButton mFloatingActionButton;
    private ProgressBar mProgressBar;
    private Toolbar toolbar;
    private TextView searchHint;

    public static void launch(Context context) {
        launchWithPrintcontent(context, "");
    }
    /**
     * 启动打印页面
     *
     * @param printContent 要打印的内容
     */
    public static void launchWithPrintcontent(Context context, String printContent) {
        context.startActivity(new Intent(context, PosPrinterListActivity.class).putExtra("printContent", printContent));
    }


    /**
     * 判断有没有开启蓝牙
     */
    private void checkBluetooth() {
        //没有开启蓝牙
        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); // 设置蓝牙可见性，最多300秒
                intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 20);
                startActivityForResult(intent, REQUEST_ENABLE_BT);
                setViewStatus(true);
                //开启蓝牙
            } else {
                searchDevices();
                setViewStatus(true);
                mSwitch.setChecked(true);
            }
        }
    }

    /**
     * 搜索状态调整
     *
     * @param isSearch 是否开始搜索
     */
    private void setViewStatus(boolean isSearch) {

        if (isSearch) {
            mFloatingActionButton.setVisibility(View.GONE);
            searchHint.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mFloatingActionButton.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
            searchHint.setVisibility(View.GONE);
        }
    }


    /**
     * 添加View的监听
     */
    private void addViewListener() {
        //蓝牙的状态
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    openBluetooth();
                    setViewStatus(true);
                } else {
                    closeBluetooth();
                }
            }
        });
        //重新搜索
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSwitch.isChecked()) {
                    searchDevices();
                    setViewStatus(true);
                } else {
                    openBluetooth();
                    setViewStatus(true);
                }
            }
        });

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PosPrinterListActivity.this, "88", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_ENABLE_BT) {
            Log.e("YBB", "开启蓝牙");
            searchDevices();
            mSwitch.setChecked(true);
            mBluetoothDevicesDatas.clear();
            adapter.notifyDataSetChanged();
        } else if (resultCode == RESULT_CANCELED && requestCode == REQUEST_ENABLE_BT) {
            Log.e("YBB", "没有开启蓝牙");
            mSwitch.setChecked(false);
            setViewStatus(false);
        }

    }

    /**
     * 打开蓝牙
     */
    public void openBluetooth() {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); // 设置蓝牙可见性，最多300秒
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 20);
        startActivityForResult(intent, REQUEST_ENABLE_BT);

    }

    /**
     * 关闭蓝牙
     */
    public void closeBluetooth() {
        mBluetoothAdapter.disable();
    }


    /**
     * 搜索蓝牙设备
     */
    public void searchDevices() {
        if (Build.VERSION.SDK_INT >= 17) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new
                        String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_CONSTANT);
            } else {
                searchDeviceList();
            }

        } else {
            searchDeviceList();
        }
    }

    private void searchDeviceList() {
        mBluetoothDevicesDatas.clear();
        adapter.notifyDataSetChanged();
        //开始搜索蓝牙设备
        mBluetoothAdapter.startDiscovery();
    }


    /**
     * 通过广播搜索蓝牙设备
     */
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

            Log.d("BroadcastReceiver", "do here");
            String action = intent.getAction();
            Log.d("BroadcastReceiver", action);
            // 把搜索的设置添加到集合中
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                Log.d("BroadcastReceiver", "do here1");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //已经匹配的设备
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    addBluetoothDevice(device);

                    //没有匹配的设备
                } else {
                    addBluetoothDevice(device);
                }
                adapter.notifyDataSetChanged();
                //搜索完成
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setViewStatus(false);
            }


            Log.d("BroadcastReceiver", mBluetoothDevicesDatas.get(0).name);


        }

        /**
         * 添加数据
         * @param device 蓝牙设置对象
         */
        private void addBluetoothDevice(BluetoothDevice device) {
            for (int i = 0; i < mBluetoothDevicesDatas.size(); i++) {
                if (device.getAddress().equals(mBluetoothDevicesDatas.get(i).getAddress())) {
                    mBluetoothDevicesDatas.remove(i);
                }
            }
            if (device.getBondState() == BluetoothDevice.BOND_BONDED && device.getBluetoothClass().getDeviceClass() == PRINT_TYPE) {
                mBluetoothDevicesDatas.add(0, new PrinterBean(device));
            } else {
                mBluetoothDevicesDatas.add(new PrinterBean(device));
            }
        }
    };

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CONSTANT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //permission granted!
                    searchDeviceList();
                }
                return;
            }
        }

    }

    @Override
    public void initView() {
        super.initView();
        //广播注册
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
        //初始化
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mSwitch = (Switch) findViewById(R.id.switch1);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar3);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchHint = (TextView) findViewById(R.id.searchHint);
        toolbar.setTitle(getString(R.string.choose_print_device));

        listView = (ListView) findViewById(R.id.listView);
        mBluetoothDevicesDatas = new ArrayList<>();

        String printContent = getIntent().getStringExtra("printContent");
        adapter = new PrinterListAdapter(this, mBluetoothDevicesDatas, TextUtils.isEmpty(printContent) ? "\n123456789℃＄¤￠‰§№☆★完\n\n" : printContent);
        listView.setAdapter(adapter);

        checkBluetooth();
        addViewListener();

    }

    @Override
    public int layoutContentId() {
        return R.layout.activity_printer_list;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
