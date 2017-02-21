package com.example.gedune.bookcollection.activity;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.gedune.bookcollection.AppProfile;
import com.example.gedune.bookcollection.R;
import com.example.gedune.bookcollection.fragment.CollectionList;
import com.example.gedune.bookcollection.fragment.CollectionStatistics;
import com.example.gedune.bookcollection.orm.OrmHelper;
import com.example.gedune.bookcollection.utils.image.NetWorkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity   {

    public static final int REQUEST_CODE = 1;
    private OrmHelper helper;
    /**
     * 请求CAMERA权限码
     */
    public static final int REQUEST_CAMERA_PERM = 101;

    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;


    @BindView(R.id.rb_collecion)
    RadioButton mRadioCollection;

    @BindView(R.id.rb_statistics)
    RadioButton mRadioStatistics;

    private Fragment mCollectionListFg;
    private Fragment mCollectionStaticFg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initData();


    }

    private void initData() {
        helper = OrmHelper.getInstance(this);

        if (!NetWorkUtils.isNetworkConnected())
        {
            Toast.makeText(AppProfile.getContext(),"无网络连接，请检查您是否连接上了网络!",Toast.LENGTH_LONG).show();
        }

    }


    private void initView() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                switch (i) {
                    case R.id.rb_collecion:
                        if (mCollectionListFg == null) {
                            mCollectionListFg = new CollectionList();
                        }
                        transaction.replace(R.id.main_fl, mCollectionListFg);
                        break;
                    case R.id.rb_scan:

                        if (ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                                    Manifest.permission.CAMERA)) {
                                // 说明需要申请该权限的理由
                                Toast.makeText(MainActivity.this, "我真的需要这个权限",
                                        Toast.LENGTH_SHORT).show();
                                // 再次申请需要的权限
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.CAMERA},
                                        REQUEST_CAMERA_PERM);
                            } else {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.CAMERA},
                                        REQUEST_CAMERA_PERM);
                            }
                        } else {
                            Intent intent = new Intent(MainActivity.this, ScanActivtity.class);
                            startActivityForResult(intent, REQUEST_CODE);
                        }

                        break;
                    case R.id.rb_statistics:
                        if (mCollectionStaticFg == null) {
                            mCollectionStaticFg = new CollectionStatistics();
                        }
                        transaction.replace(R.id.main_fl, mCollectionStaticFg);

                }

                setTabState();//设置状态
                transaction.commit();
            }
        });

        mCollectionListFg = new CollectionList();
        mCollectionStaticFg = new CollectionStatistics();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.main_fl,mCollectionListFg).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRadioCollection.setChecked(true);
    }


    private void setTabState() {
        setHomeState();
        setLocationState();
    }

    /**
     * set tab home state
     */
    private void setHomeState() {
        if (mRadioCollection.isChecked()) {

            mRadioCollection.setTextColor(ContextCompat.getColor(this, R.color.basic_green));
        } else {
            mRadioCollection.setTextColor(ContextCompat.getColor(this, R.color.basec_black));
        }
    }

    private void setLocationState() {
        if (mRadioStatistics.isChecked()) {
            mRadioStatistics.setTextColor(ContextCompat.getColor(this, R.color.basic_green));
        } else {
            mRadioStatistics.setTextColor(ContextCompat.getColor(this, R.color.basec_black));
        }
    }



}
