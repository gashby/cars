package org.tuares.cars.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.salesforce.androidsdk.accounts.UserAccount;
import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.smartstore.store.IndexSpec;
import com.salesforce.androidsdk.smartstore.store.QuerySpec;
import com.salesforce.androidsdk.smartstore.store.SmartSqlHelper;
import com.salesforce.androidsdk.smartstore.store.SmartStore;
import com.salesforce.androidsdk.smartsync.app.SmartSyncSDKManager;
import com.salesforce.androidsdk.smartsync.manager.SyncManager;
import com.salesforce.androidsdk.smartsync.target.SoqlSyncDownTarget;
import com.salesforce.androidsdk.smartsync.target.SyncDownTarget;
import com.salesforce.androidsdk.smartsync.target.SyncTarget;
import com.salesforce.androidsdk.smartsync.target.SyncUpTarget;
import com.salesforce.androidsdk.smartsync.util.SOQLBuilder;
import com.salesforce.androidsdk.smartsync.util.SyncOptions;
import com.salesforce.androidsdk.smartsync.util.SyncState;

import org.json.JSONArray;
import org.json.JSONException;
import org.tuares.cars.objects.AttendanceObject;
import org.tuares.cars.utils.LocalConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gashby on 11.08.2017.
 */

public class AttendanceListLoader  extends AsyncTaskLoader<List<AttendanceObject>> {


        public static final String ATTENDANCE_SOUP = "attendance__c";
        public static final Integer LIMIT = 10000;
        // TO-DO Need to modify LOAD_COMPLETE_INTENT_ACTION String
        public static final String LOAD_COMPLETE_INTENT_ACTION = "org.tuares.cars.loaders.LIST_LOAD_COMPLETE";
        private static final String TAG = "AttendanceListLoader";

        // TO DO

        private static IndexSpec[] ATTENDANCE_INDEX_SPEC = {
                new IndexSpec("Id", SmartStore.Type.string),
                new IndexSpec("Name", SmartStore.Type.string),
                new IndexSpec("Class_Id__c", SmartStore.Type.string),
                new IndexSpec("Student_Id__c", SmartStore.Type.string),
                new IndexSpec("Attended_On__c", SmartStore.Type.string),
                new IndexSpec(SyncTarget.LOCALLY_CREATED, SmartStore.Type.string),
                new IndexSpec(SyncTarget.LOCALLY_UPDATED, SmartStore.Type.string),
                new IndexSpec(SyncTarget.LOCALLY_DELETED, SmartStore.Type.string),
                new IndexSpec(SyncTarget.LOCAL, SmartStore.Type.string)
        };

        private SmartStore smartStore;
        private SyncManager syncMgr;
        private long syncId = -1;

        /**
         * Parameterized constructor.
         *
         * @param context Context.
         * @param account User account.
         */
        public AttendanceListLoader(Context context, UserAccount account) {
            super(context);
            smartStore = SmartSyncSDKManager.getInstance().getSmartStore(account);
            syncMgr = SyncManager.getInstance(account);
        }

        // TO-DO Add parameter for like query passing in student id

        @Override
        public List<AttendanceObject> loadInBackground() {
            if (!smartStore.hasSoup(ATTENDANCE_SOUP)) {
                return null;
            }
            final QuerySpec querySpec = QuerySpec.buildAllQuerySpec(ATTENDANCE_SOUP,
                    AttendanceObject.NAME, QuerySpec.Order.ascending, LIMIT);
            JSONArray results = null;
            List<AttendanceObject> attendance = new ArrayList<AttendanceObject>();
            try {
                results = smartStore.query(querySpec, 0);
                for (int i = 0; i < results.length(); i++) {
                    attendance.add(new AttendanceObject(results.getJSONObject(i)));
                }
            } catch (JSONException e) {
                Log.e(TAG, "JSONException occurred while parsing", e);
            } catch (SmartSqlHelper.SmartSqlException e) {
                Log.e(TAG, "SmartSqlException occurred while fetching data", e);
            }
            return attendance;
        }

        /**
         * Pushes local changes up to the server.
         */
        public synchronized void syncUp() {
            final SyncUpTarget target = new SyncUpTarget();
            final SyncOptions options = SyncOptions.optionsForSyncUp(Arrays.asList(AttendanceObject.ATTENDANCE_FIELDS_SYNC_UP),
                    SyncState.MergeMode.LEAVE_IF_CHANGED);

            try {
                syncMgr.syncUp(target, options, org.tuares.cars.loaders.AttendanceListLoader.ATTENDANCE_SOUP, new SyncManager.SyncUpdateCallback() {

                    @Override
                    public void onUpdate(SyncState sync) {
                        if (SyncState.Status.DONE.equals(sync.getStatus())) {
                            syncDown();
                        }
                    }
                });
            } catch (JSONException e) {
                Log.e(TAG, "JSONException occurred while parsing", e);
            } catch (SyncManager.SmartSyncException e) {
                Log.e(TAG, "SmartSyncException occurred while attempting to sync up", e);
            }
        }

        /**
         * Pulls the latest records from the server.
         */
        public synchronized void syncDown() {
            smartStore.registerSoup(org.tuares.cars.loaders.AttendanceListLoader.ATTENDANCE_SOUP, ATTENDANCE_INDEX_SPEC);
            final SyncManager.SyncUpdateCallback callback = new SyncManager.SyncUpdateCallback() {

                @Override
                public void onUpdate(SyncState sync) {
                    if (SyncState.Status.DONE.equals(sync.getStatus())) {
                        fireLoadCompleteIntent();
                    }
                }
            };
            try {
                if (syncId == -1) {
                    final SyncOptions options = SyncOptions.optionsForSyncDown(SyncState.MergeMode.LEAVE_IF_CHANGED);
                    final String soqlQuery = SOQLBuilder.getInstanceWithFields(AttendanceObject.ATTENDANCE_FIELDS_SYNC_DOWN)
                            .from(LocalConstants.ATTENDANCE).limit(org.tuares.cars.loaders.AttendanceListLoader.LIMIT).build();
                    final SyncDownTarget target = new SoqlSyncDownTarget(soqlQuery);
                    final SyncState sync = syncMgr.syncDown(target, options,
                            org.tuares.cars.loaders.AttendanceListLoader.ATTENDANCE_SOUP, callback);
                    syncId = sync.getId();
                } else {
                    syncMgr.reSync(syncId, callback);
                }
            } catch (JSONException e) {
                Log.e(TAG, "JSONException occurred while parsing", e);
            } catch (SyncManager.SmartSyncException e) {
                Log.e(TAG, "SmartSyncException occurred while attempting to sync down", e);
            }
        }

        /**
         * Fires an intent notifying a registered receiver that fresh data is
         * available. This is for the special case where the data change has
         * been triggered by a background sync, even though the consuming
         * activity is in the foreground. Loaders don't trigger callbacks in
         * the activity unless the load has been triggered using a LoaderManager.
         */
        private void fireLoadCompleteIntent() {
            final Intent intent = new Intent(LOAD_COMPLETE_INTENT_ACTION);
            SalesforceSDKManager.getInstance().getAppContext().sendBroadcast(intent);
        }
    }


