package com.example.gedune.bookcollection.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.gedune.bookcollection.Bean.BookBean;
import com.example.gedune.bookcollection.Bean.BookDetail;
import com.example.gedune.bookcollection.R;
import com.example.gedune.bookcollection.module.BookDetailTask;
import com.example.gedune.bookcollection.orm.OrmHelper;
import com.example.gedune.bookcollection.utils.AsyncUtils;
import com.example.gedune.bookcollection.utils.BookDetailGenarator;
import com.example.gedune.bookcollection.widget.ScanDialog;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScanActivtity extends AppCompatActivity implements View.OnClickListener {
    private CaptureFragment captureFragment;
    private OrmHelper helper;

    @BindView(R.id.scan_cancel)
    ImageButton cancelBtn;

    @BindView(R.id.scan_flash)
    ImageButton scanFlashBtn;

    private Boolean isFlash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scan_activtity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        isFlash = false;

        helper = OrmHelper.getInstance(ScanActivtity.this);
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        captureFragment.setAnalyzeCallback(analyzeCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, final String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);

            //没有收藏该书
            final BookDetailTask task = new BookDetailTask(result);
            AsyncUtils.getSingleExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Object response = task.execute();
                        if (response != null) {
                            BookDetail detail = BookDetailGenarator.BookBeanToDetail((BookBean) response);
                            Intent i = new Intent(ScanActivtity.this, BookDetailActivity.class);
                            i.putExtra(BookDetailActivity.sBOOK, detail);
                            startActivity(i);

                        } else {
                            showDialog();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            showDialog();
        }
    };

    private void showDialog(){
        ScanActivtity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ScanDialog.Builder builder = new ScanDialog.Builder(ScanActivtity.this);

                builder.setconfirmButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("fuck","which : " + which);
                        if (which != 0){
                            dialog.dismiss();
                            finish();
                        }
                    }
                });
                builder.create().show();

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.scan_cancel:
                finish();
                break;
            case R.id.scan_flash:
                if (!isFlash){
                    CodeUtils.isLightEnable(true);
                    isFlash = true;
                }else {
                    CodeUtils.isLightEnable(false);
                    isFlash = false;
                }

                break;
        }
    }
}
