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

import com.example.gedune.bookcollection.Bean.BookBean;
import com.example.gedune.bookcollection.Bean.BookDetail;
import com.example.gedune.bookcollection.R;
import com.example.gedune.bookcollection.fragment.Collection_list;
import com.example.gedune.bookcollection.fragment.Collection_statistics;
import com.example.gedune.bookcollection.module.BookDetailTask;
import com.example.gedune.bookcollection.net.BaseRequest;
import com.example.gedune.bookcollection.net.HttpCallback;
import com.example.gedune.bookcollection.orm.OrmHelper;
import com.example.gedune.bookcollection.utils.BookDetailGenarator;
import com.uuzuche.lib_zxing.activity.CodeUtils;

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
    }


    private void initView() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                switch (i) {
                    case R.id.rb_collecion:
                        if (mCollectionListFg == null) {
                            mCollectionListFg = new Collection_list();
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
                            mRadioCollection.setChecked(true);
                        } else {
                            Intent intent = new Intent(MainActivity.this, ScanActivtity.class);
                            startActivityForResult(intent, REQUEST_CODE);
                        }


                        break;
                    case R.id.rb_statistics:


                        if (mCollectionStaticFg == null) {
                            mCollectionStaticFg = new Collection_statistics();
                        }
                        transaction.replace(R.id.main_fl, mCollectionStaticFg);

                }
                setTabState();//设置状态
                transaction.commit();
            }
        });

        mCollectionListFg = new Collection_list();
        mCollectionStaticFg = new Collection_statistics();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.main_fl,mCollectionListFg).commit();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    final String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();

                    final BookDetailTask task = new BookDetailTask(result);
                    task.enqueue(new HttpCallback() {
                        @Override
                        public void onResponse(BaseRequest request, Object data) {
                            BookDetail detail = BookDetailGenarator.BookBeanToDetail((BookBean)data);
//                            Log.e("fuck",detail.toString());

                            helper.insertBook(detail);

                        }

                        @Override
                        public void onFailure(BaseRequest request, Exception e, int code, String message) {

                        }
                    },true);

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        } else if (requestCode == REQUEST_CAMERA_PERM) {
            Toast.makeText(this, "从设置页面返回...", Toast.LENGTH_SHORT)
                    .show();
        }
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
