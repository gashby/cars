package org.tuares.cars.objects;

import com.salesforce.androidsdk.smartsync.model.SalesforceObject;
import com.salesforce.androidsdk.smartsync.target.SyncTarget;
import com.salesforce.androidsdk.smartsync.util.Constants;

import org.json.JSONObject;
import org.tuares.cars.utils.LocalConstants;

import static org.tuares.cars.utils.TextUtil.sanitizeText;

/**
 * Created by gashby on 08.08.2017.
 */

public class StudentInterviewObject extends SalesforceObject {


        public static final String NAME = "Name";                                            // Read Only
        public static final String STUDENT_INTERVIEW_STUDENT_ID = "StudentID__c";
        public static final String STUDENT_INTERVIEW_HOME_VISIT_DATE = "HomeVisitDate__c";
        public static final String STUDENT_INTERVIEW_INTERVIEW_DATE  = "InterviewDate__c";
        public static final String STUDENT_INTERVIEW_INTERVIEW_NOTE  = "InterviewNote__c";
        public static final String STUDENT_INTERVIEW_INTERVIEW_IS_HOME_VISIT  = "IsHomeVisit__c";


        public static final String[] INTERVIEW_FIELDS_SYNC_DOWN = {
                NAME,
                STUDENT_INTERVIEW_STUDENT_ID,
                STUDENT_INTERVIEW_HOME_VISIT_DATE,
                STUDENT_INTERVIEW_INTERVIEW_DATE,
                STUDENT_INTERVIEW_INTERVIEW_NOTE,
                STUDENT_INTERVIEW_INTERVIEW_IS_HOME_VISIT
        };

        public static final String[] INTERVIEW_FIELDS_SYNC_UP = {
                Constants.ID,
                NAME,
                STUDENT_INTERVIEW_STUDENT_ID,
                STUDENT_INTERVIEW_HOME_VISIT_DATE,
                STUDENT_INTERVIEW_INTERVIEW_DATE,
                STUDENT_INTERVIEW_INTERVIEW_NOTE,
                STUDENT_INTERVIEW_INTERVIEW_IS_HOME_VISIT
        };

        private boolean isLocallyModified;

        public StudentInterviewObject(JSONObject data) {
            super(data);
            objectType = LocalConstants.STUDENT_INTERVIEW;
            objectId = data.optString(Constants.ID);
            name = data.optString(NAME);
            isLocallyModified = data.optBoolean(SyncTarget.LOCALLY_UPDATED) ||
                    data.optBoolean(SyncTarget.LOCALLY_CREATED) ||
                    data.optBoolean(SyncTarget.LOCALLY_DELETED);
        }

        public String getStudentInterviewRefName() {
            return sanitizeText(rawData.optString(NAME));
        }

        public String getStudentInterviewStudentId() {
            return sanitizeText(rawData.optString(STUDENT_INTERVIEW_STUDENT_ID));
        }

        public String getStudentInterviewHomeVisitDate() {
            return sanitizeText(rawData.optString(STUDENT_INTERVIEW_HOME_VISIT_DATE));
        }

        public String getStudentInterviewInterviewDate() {
            return sanitizeText(rawData.optString(STUDENT_INTERVIEW_INTERVIEW_DATE));
        }

        public String getStudentInterviewInterviewIsHomeVisit() {
            return sanitizeText(rawData.optString(STUDENT_INTERVIEW_INTERVIEW_IS_HOME_VISIT));
        }

        public String getStudentInterviewInterviewNote() {
            return sanitizeText(rawData.optString(STUDENT_INTERVIEW_INTERVIEW_NOTE));
        }

        public String getObjectId() { return objectId; }

        public boolean isLocallyModified() {
            return isLocallyModified;
        }

    }


