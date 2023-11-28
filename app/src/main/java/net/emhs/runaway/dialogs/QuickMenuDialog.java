package net.emhs.runaway.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import net.emhs.runaway.R;

public class QuickMenuDialog extends Dialog implements View.OnClickListener {
    public QuickMenuDialog(Activity activity) {
        super(activity, R.style.Theme_Runaway_Popup);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_quick_menu);

        findViewById(R.id.quick_menu_close).setOnClickListener(this);
    }

    @Override
    public void show() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.verticalMargin = -.3245F;
        params.horizontalMargin = -.1111F;
        getWindow().setAttributes(params);
        super.show();
    }


    @Override
    public void onClick(View view) {
        System.out.println(view.getId());
        switch (view.getId()) {
            case R.id.quick_menu_close:
                this.dismiss();
                break;
        }

    }
}
