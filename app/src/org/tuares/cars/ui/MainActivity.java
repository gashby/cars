package org.tuares.cars.ui;

import android.accounts.Account;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.salesforce.androidsdk.accounts.UserAccount;
import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.rest.RestClient;
import com.salesforce.androidsdk.smartstore.store.SmartStore;
import com.salesforce.androidsdk.smartsync.app.SmartSyncSDKManager;
import com.salesforce.androidsdk.ui.SalesforceFragmentActivity;

import org.tuares.cars.R;
import org.tuares.cars.loaders.ContactListLoader;
import org.tuares.cars.navigation.navigationDrawer;

import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main activity.
 *
 * @author bhariharan
 */

public class MainActivity extends SalesforceFragmentActivity implements StudentListFragment.OnStudentListFragementListener

{

    @BindView(R.id.toolbar)
    public Toolbar toolBar;


    public static final String OBJECT_ID_KEY = "object_id";
    public static final String OBJECT_TITLE_KEY = "object_title";
    public static final String OBJECT_NAME_KEY = "object_name";
    private static final String SYNC_CONTENT_AUTHORITY = "org.tuares.cars.sync.datasyncadapter";

    private static final long SYNC_FREQUENCY_ONE_HOUR = 1 * 60 * 60;


    private UserAccount curAccount;
    private SmartStore smartStore;
    private ContactListLoader contactLoader;
    private LogoutDialogFragment logoutConfirmationDialog;
    private LoadCompleteReceiver loadCompleteReceiver;
    private AtomicBoolean isRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        logoutConfirmationDialog = new LogoutDialogFragment();
        loadCompleteReceiver = new LoadCompleteReceiver();
        isRegistered = new AtomicBoolean(false);


        ButterKnife.bind(this);
        toolBar.setTitle(getResources().getString(R.string.drawer_item_student));
        setSupportActionBar(toolBar);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        navigationDrawer.getDrawer(this, toolBar);

    }


    @Override
    protected void logoutCompleteActions() {
        super.logoutCompleteActions();
        // If refresh token is revoked - ClientManager does a logout that doesn't finish top activity activity or show login
        if (!isChild()) {
            recreate();
        }
    }

    private void launchAccountSwitcherActivity() {
        final Intent i = new Intent(this, SalesforceSDKManager.getInstance().getAccountSwitcherActivityClass());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(i);
    }

    @Override
    public void onResume(RestClient client) {
        curAccount = SmartSyncSDKManager.getInstance().getUserAccountManager().getCurrentUser();
        Account account = null;
        if (curAccount != null) {
            account = SmartSyncSDKManager.getInstance().getUserAccountManager().buildAccount(curAccount);
        }
        smartStore = SmartSyncSDKManager.getInstance().getSmartStore(curAccount);
        // getLoaderManager().initLoader(CONTACT_LOADER_ID, null, this);
        if (!smartStore.hasSoup(ContactListLoader.CONTACT_SOUP)) {
            syncDownContacts();
        }

		/*
		 * Enables sync automatically for this provider. To enable almost
		 * instantaneous sync when records are modified locally, a call needs
		 * to be made by the content provider to notify the sync provider
		 * that the underlying data set has changed. Since we don't use cursors
		 * in this sample application, we simply enable periodic sync every hour.
		 */
        ContentResolver.setSyncAutomatically(account, SYNC_CONTENT_AUTHORITY, true);
        ContentResolver.addPeriodicSync(account, SYNC_CONTENT_AUTHORITY,
                Bundle.EMPTY, SYNC_FREQUENCY_ONE_HOUR);
        if (!isRegistered.get()) {
            registerReceiver(loadCompleteReceiver,
                    new IntentFilter(ContactListLoader.LOAD_COMPLETE_INTENT_ACTION));
        }
        isRegistered.set(true);
    }


    @Override
    public void onPause() {
        if (isRegistered.get()) {
            unregisterReceiver(loadCompleteReceiver);
        }
        isRegistered.set(false);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        loadCompleteReceiver = null;
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }


    public void onStudentSelected(Object sObject) {
    }

    ;

    /*
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article

        StudentFragment studentFrag = (StudentFragment)
                getSupportFragmentManager().findFragmentById(R.id.student_fragment);

        if (studentFrag != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the StudentDetailFragment to update its content
            studentFrag.updateDetails(sObject);
        } else {
            // Otherwise, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            ArticleFragment newFragment = new ArticleFragment();
            Bundle args = new Bundle();
            args.putInt(ArticleFragment.ARG_POSITION, position);
            newFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }



     */



   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = new SearchView(this);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchItem.setActionView(searchView);
        return super.onCreateOptionsMenu(menu);
    } */

  /*   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                Toast.makeText(this, "Synchronizing...", Toast.LENGTH_SHORT).show();
               // syncUpContacts();
                return true;
            case R.id.action_logout:
                logoutConfirmationDialog.show(getFragmentManager(), "LogoutDialog");
                return true;
            case R.id.action_switch_user:
                launchAccountSwitcherActivity();
                return true;
            case R.id.action_add:
                //launchDetailActivity(Constants.EMPTY_STRING, "New Contact",Constants.EMPTY_STRING);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    } */

   /* private void launchSmartStoreInspectorActivity() {
        this.startActivity(SmartStoreInspectorActivity.getIntent(this, false, null));
    } */


    private void syncDownContacts() {
        contactLoader.syncDown();
        Toast.makeText(MainActivity.this, "Sync down complete!",
                Toast.LENGTH_LONG).show();
    }

    private void syncUpContacts() {
        contactLoader.syncUp();
        Toast.makeText(this, "Sync up complete!", Toast.LENGTH_LONG).show();
    }




 /** A simple receiver for load complete events.
 *
 * @author bhariharan */

private class LoadCompleteReceiver extends BroadcastReceiver {

     @Override
     public void onReceive(Context context, Intent intent) {
         if (intent != null) {
             final String action = intent.getAction();
             if (ContactListLoader.LOAD_COMPLETE_INTENT_ACTION.equals(action)) {
                // refreshList();
             }
         }
     }
 }
}





