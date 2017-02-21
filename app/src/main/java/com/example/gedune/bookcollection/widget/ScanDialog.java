package com.example.gedune.bookcollection.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gedune.bookcollection.R;

/**
 * Created by hg1666 on 2017/1/15.
 */
public class ScanDialog extends AlertDialog {
    public ScanDialog(Context context) {
        super(context);
    }

    public ScanDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ScanDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public static class Builder{
        private Context context;
        private String message;
        private String confimText;
        private String cancelText;
        private OnClickListener confirmListener;
        private OnClickListener cancelListener;
        public Builder(Context context){
            this.context = context;
        }

        public Builder setMessage(String message){
            this.message = message;
            return this;
        }
        public Builder setconfirmButton(String confimText,OnClickListener listener){
            this.confimText = confimText;
            this.confirmListener = listener;
            return this;
        }

        public ScanDialog create(){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ScanDialog scanDialog = new ScanDialog(context);
            View layout = inflater.inflate(R.layout.layout_scan_dialog,null);
            scanDialog.setView(layout);

            if (message !=null){
                ((TextView) layout.findViewById(R.id.dialog_tip)).setText(message);
            }

            if(confimText!=null){
                ((Button) layout.findViewById(R.id.scan_dialog_comfirm)).setText(confimText);
                if(confirmListener!=null){
                    ((Button) layout.findViewById(R.id.scan_dialog_comfirm)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confirmListener.onClick(scanDialog,DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            }else{
                layout.findViewById(R.id.scan_dialog_comfirm).setVisibility(View.GONE);
            }

            scanDialog.setContentView(layout);
            return scanDialog;
        }
    }
}
