package com.linkhand.lajiaoshipin.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.lajiaoshipin.R;

public class CommonShareDialog extends Dialog {

    private Context mContext;
    private OnClickListener optionOneClickListener;
    private OnClickListener optionfuzhiClickListener;
    private TextView message;
    private ImageView imagetitle;
    private Button no;
    private Button yes;


    public CommonShareDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        initView();
        initListener();
    }


    private void initView() {
        setContentView(R.layout.dialog_appointment_success);
        imagetitle = (ImageView) findViewById(R.id.title);
        message = (TextView) findViewById(R.id.message);
        no = (Button) findViewById(R.id.no);
        yes = (Button) findViewById(R.id.yes);
    }

    private void initListener() {
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionOneClickListener.onClick(CommonShareDialog.this, DialogInterface.BUTTON_POSITIVE);
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionfuzhiClickListener.onClick(CommonShareDialog.this,DialogInterface.BUTTON_POSITIVE);
            }
        });
    }

    public void setMessage(String messaget) {
        message.setText(messaget);
    }

    public void setImage(@DrawableRes int image) {
        this.imagetitle.setImageResource(image);
    }

    public void setOptionOneClickListener(String optionOne, OnClickListener listener) {
        this.optionOneClickListener = listener;
    }
    public void setOptionfuzhiClickListener(String optionOne, OnClickListener listener) {
        this.optionfuzhiClickListener = listener;
    }

}
