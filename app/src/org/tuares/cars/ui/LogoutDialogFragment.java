package org.tuares.cars.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.salesforce.androidsdk.app.SalesforceSDKManager;

import org.tuares.cars.R;

/**
 * Created by gashby on 01.11.2017.
 */

public class LogoutDialogFragment extends DialogFragment {

    private AlertDialog logoutConfirmationDialog;

    public LogoutDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        logoutConfirmationDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.logout_title)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                SalesforceSDKManager.getInstance().logout(getActivity());
                            }
                        })
                .setNegativeButton(R.string.logout_cancel, null)
                .create();
        return logoutConfirmationDialog;
    }
}
