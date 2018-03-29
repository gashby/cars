package org.tuares.cars.sync;
/*
 * Copyright (c) 2015-present, salesforce.com, inc.
 * All rights reserved.
 * Redistribution and use of this software in source and binary forms, with or
 * without modification, are permitted provided that the following conditions
 * are met:
 * - Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * - Neither the name of salesforce.com, inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission of salesforce.com, inc.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */


import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.salesforce.androidsdk.accounts.UserAccount;
import com.salesforce.androidsdk.accounts.UserAccountManager;
import com.salesforce.androidsdk.app.SalesforceSDKManager;

import org.tuares.cars.loaders.AttendanceListLoader;
import org.tuares.cars.loaders.ClassListLoader;
import org.tuares.cars.loaders.ContactListLoader;
import org.tuares.cars.loaders.EnrollmentListLoader;
import org.tuares.cars.loaders.StudentInterviewListLoader;

/**
     * A simple sync adapter to perform background sync of data.
     *
     * @author bhariharan
     */

    public class DataSyncAdapter  extends AbstractThreadedSyncAdapter {

        /**
         * Parameterized constructor.
         *
         * @param context Context.
         * @param autoInitialize True - if it should be initialized automatically, False - otherwise.
         */
        public DataSyncAdapter(Context context, boolean autoInitialize,
                                  boolean allowParallelSyncs) {
            super(context, autoInitialize, allowParallelSyncs);
        }

        @Override
        public void onPerformSync(Account account, Bundle extras, String authority,
                                  ContentProviderClient provider, SyncResult syncResult) {
            final SalesforceSDKManager sdkManager = SalesforceSDKManager.getInstance();
            final UserAccountManager accManager = sdkManager.getUserAccountManager();
            if (sdkManager.isLoggingOut() || accManager.getAuthenticatedUsers() == null) {
                return;
            }
            if (account != null) {
                final UserAccount user = sdkManager.getUserAccountManager().buildUserAccount(account);

                // for each sObject to be synchronized add both ListLoader and syncUp() calls

                final ContactListLoader contactLoader = new ContactListLoader(getContext(), user);
                contactLoader.syncUp();


                final ClassListLoader classLoader = new ClassListLoader(getContext(), user);
                classLoader.syncUp();

                final EnrollmentListLoader enrollmentLoader = new EnrollmentListLoader(getContext(), user);
                enrollmentLoader.syncUp();

                final StudentInterviewListLoader interviewLoader = new StudentInterviewListLoader(getContext(), user);
                interviewLoader.syncUp();

                final AttendanceListLoader attendanceLoader = new AttendanceListLoader(getContext(), user);
                attendanceLoader.syncUp();


            }
        }
    }
