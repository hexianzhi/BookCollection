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
public class SimpleDialog extends AlertDialog {
    public SimpleDialog(Context context) {
        super(context);
    }

    public SimpleDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SimpleDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public static class Builder{
        private Context context;
        private String message;
        private String confimText;
        private String cancelText;
        private DialogInterface.OnClickListener confirmListener;
        private DialogInterface.OnClickListener cancelListener;
        public Builder(Context context){
            this.context = context;
        }
        public Builder setMessage(String message){
            this.message = message;
            return this;
        }
        public Builder setconfirmButton(String confimText,DialogInterface.OnClickListener listener){
            this.confimText = confimText;
            this.confirmListener = listener;
            return this;
        }
        public Builder setCancelButton(String cancelText,DialogInterface.OnClickListener listener){
            this.cancelListener = listener;
            this.cancelText = cancelText;
            return this;
        }
        public SimpleDialog create(){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final SimpleDialog simpleDialog = new SimpleDialog(context);
            View layout = inflater.inflate(R.layout.layout_simple_dialog,null);
            simpleDialog.setView(layout);

            if (message !=null){
                ((TextView) layout.findViewById(R.id.dialog_tip)).setText(message);
            }

            if(confimText!=null){
                ((Button) layout.findViewById(R.id.dialog_confirm)).setText(confimText);
                if(confirmListener!=null){
                    ((Button) layout.findViewById(R.id.dialog_confirm)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confirmListener.onClick(simpleDialog,DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            }else{
                layout.findViewById(R.id.dialog_confirm).setVisibility(View.GONE);
            }

            if(cancelText!=null){
                ((Button) layout.findViewById(R.id.dialog_cancle)).setText(cancelText);
                if(cancelListener!=null){
                    ((Button) layout.findViewById(R.id.dialog_cancle)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cancelListener.onClick(simpleDialog,DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
            }else{
                layout.findViewById(R.id.dialog_cancle).setVisibility(View.GONE);
            }
            simpleDialog.setContentView(layout);
            return simpleDialog;
        }
    }
}
