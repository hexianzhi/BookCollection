package com.example.gedune.bookcollection.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.gedune.bookcollection.Bean.BookBean;
import com.example.gedune.bookcollection.Bean.BookDetail;
import com.example.gedune.bookcollection.R;
import com.example.gedune.bookcollection.module.BookDetailTask;
import com.example.gedune.bookcollection.net.MyHttpCallback;
import com.example.gedune.bookcollection.orm.OrmHelper;
import com.example.gedune.bookcollection.utils.BookDetailGenarator;
import com.example.gedune.bookcollection.widget.ScanDialog;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class ScanActivtity extends AppCompatActivity implements View.OnClickListener {
    private CaptureFragment captureFragment;
    private OrmHelper helper;
    private final static int sDIALOG_NO_NETWORK = 1;
    private final static int sDIALOG_ANALYSE_FAILDED= 2;


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
            task.enqueue(new MyHttpCallback() {
                @Override
                public void onResponse(Call call, Object data) {
                    if (data != null) {
                        BookDetail detail = BookDetailGenarator.BookBeanToDetail((BookBean) data);
                        Intent i = new Intent(ScanActivtity.this, BookDetailActivity.class);
                        i.putExtra(BookDetailActivity.sBOOK, detail);
                        startActivity(i);
                    }else {
                        showMyDialog(sDIALOG_NO_NETWORK);
                    }
                }

                @Override
                public void onFailure(Call call, Exception e) {
                    showMyDialog(sDIALOG_NO_NETWORK);
                }
            },true);

        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            showMyDialog(sDIALOG_ANALYSE_FAILDED);
        }
    };

    private void showMyDialog(int id){
        switch (id){
            case sDIALOG_ANALYSE_FAILDED:
                ScanActivtity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ScanDialog.Builder builder = new ScanDialog.Builder(ScanActivtity.this);
                        builder.setconfirmButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which != 0){
                                    dialog.dismiss();
                                    finish();
                                }
                            }
                        });
                        builder.create().show();

                    }
                });
                break;

            case sDIALOG_NO_NETWORK:
                ScanActivtity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ScanDialog.Builder builder = new ScanDialog.Builder(ScanActivtity.this);
                        builder.setMessage("没有网络连接~请确定您是否连接上了网络~");
                        builder.setconfirmButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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
