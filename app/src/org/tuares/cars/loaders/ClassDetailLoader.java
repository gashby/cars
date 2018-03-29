package org.tuares.cars.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.salesforce.androidsdk.accounts.UserAccount;
import com.salesforce.androidsdk.smartstore.store.QuerySpec;
import com.salesforce.androidsdk.smartstore.store.SmartSqlHelper;
import com.salesforce.androidsdk.smartstore.store.SmartStore;
import com.salesforce.androidsdk.smartsync.app.SmartSyncSDKManager;

import com.salesforce.androidsdk.smartsync.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.tuares.cars.objects.ClassObject;

/**
 * Created by gashby on 11.08.2017.
 */

public class ClassDetailLoader extends AsyncTaskLoader<ClassObject> {

        private static final String TAG = "ContactDetailLoader";

        private String objectId;
        private SmartStore smartStore;

        /**
         * Parameterized constructor.
         *
         * @param context Context.
         * @param account User account.
         * @param objId Object ID.
         */
        public ClassDetailLoader(Context context, UserAccount account,
                                   String objId) {
            super(context);
            objectId = objId;
            smartStore = SmartSyncSDKManager.getInstance().getSmartStore(account);
        }

        @Override
        public ClassObject loadInBackground() {
            if (TextUtils.isEmpty(objectId)) {
                return null;
            }
            ClassObject sObject = null;

            if (!smartStore.hasSoup(ClassListLoader.CLASS_SOUP)) {
                return null;
            }
            final QuerySpec querySpec = QuerySpec.buildExactQuerySpec(
                    ClassListLoader.CLASS_SOUP, Constants.ID, objectId, null, null, 1);
            JSONArray results = null;
            try {
                results = smartStore.query(querySpec, 0);
                if (results != null) {
                    sObject = new ClassObject(results.getJSONObject(0));
                }
            } catch (JSONException e) {
                Log.e(TAG, "JSONException occurred while parsing", e);
            } catch (SmartSqlHelper.SmartSqlException e) {
                Log.e(TAG, "SmartSqlException occurred while fetching data", e);
            }
            return sObject;
        }
    }
