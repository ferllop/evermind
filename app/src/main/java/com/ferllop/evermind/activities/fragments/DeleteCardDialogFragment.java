package com.ferllop.evermind.activities.fragments;

import android.app.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.ferllop.evermind.R;

public class DeleteCardDialogFragment extends DialogFragment {

    public interface DeleteDialogListener {
        public void onDeleteDialogConfirmClick(DialogFragment dialog, String cardID);
        public void onDialogCancelClick(DialogFragment dialog);
    }

    DeleteDialogListener listener;
    String cardID;

    public DeleteCardDialogFragment(String cardID) {
        this.cardID = cardID;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (DeleteDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.delete_dialog_confirm_message)
                .setTitle(R.string.delete_dialog_confirm_title);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onDeleteDialogConfirmClick(DeleteCardDialogFragment.this, cardID);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        return builder.create();
    }
}
