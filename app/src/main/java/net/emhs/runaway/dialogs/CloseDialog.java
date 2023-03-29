package net.emhs.runaway.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.emhs.runaway.R;

public class CloseDialog extends Dialog implements View.OnClickListener{

    // Initializes global variables
    private final String text;
    private final Dialog parentDialog;

    public CloseDialog(Activity activity, Dialog parentDialog, String text) {
        super(activity, R.style.Theme_Runaway_Popup); // Sets theme and activity

        this.text = text;
        this.parentDialog = parentDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirmation); // Sets dialog view

        // On click listeners for actions (Confirm or deny)
        findViewById(R.id.confirmation_confirm).setOnClickListener(this);
        findViewById(R.id.confirmation_deny).setOnClickListener(this);

        // Sets warning text
        TextView textView = findViewById(R.id.confirmation_text);
        textView.setText(text);
    }

    @SuppressLint("NonConstantResourceId") // For switch statement
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirmation_confirm: // Confirm
                dismiss();
                break;
            case R.id.confirmation_deny: // Deny just shows the parent dialog so editing is continued
                parentDialog.show();
                dismiss();
                break;
        }
    }
}
