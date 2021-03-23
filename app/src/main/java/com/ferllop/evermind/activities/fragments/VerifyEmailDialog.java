package com.ferllop.evermind.activities.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.ferllop.evermind.R;

public class VerifyEmailDialog extends DialogFragment {
    public interface VerifyEmailDialogListener {
        public void onOk(DialogFragment dialog);
    }

    VerifyEmailDialogListener listener;

    String message;

    public VerifyEmailDialog(String message) {
        this.message = message;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (VerifyEmailDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onOk(VerifyEmailDialog.this);
            }
        });


        return builder.create();
    }


}
