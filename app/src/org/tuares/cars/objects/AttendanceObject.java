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

public class AttendanceObject extends SalesforceObject {

    public static final String NAME = "Name";                                            // Read Only
    public static final String ATTENDANCE_CLASS_ID = "Class_Id__c";
    public static final String ATTENDANCE_STUDENT_ID = "Student_Id__c";
    public static final String ATTENDANCE_CLASS_NAME = "Class_Name__c";                 // Read Only
    public static final String ATTENDANCE_DATE = "Attended_On__c";
    public static final String ATTENDANCE_STUDENT_LAST_NAME = "Student_Last_Name__c";   // Read Only
    public static final String ATTENDANCE_STUDENT_FIRST_NAME = "Student_First_Name__c"; // Read Only
    public static final String ATTENDANCE_STUDENT_ATTENDED = "Attended__c";             // Y or N
    public static final String ATTENDANCE_STUDENT_ABSENT = "Absent__c";                 // Picklist Reason for Absent
    public static final String ATTENDANCE_STUDENT_COMMENTS = "Comments__c";             // Max 80 Characters

    public static final String[] ATTENDANCE_FIELDS_SYNC_DOWN = {
            NAME,
            ATTENDANCE_CLASS_NAME,
            ATTENDANCE_CLASS_ID,
            ATTENDANCE_STUDENT_ID,
            ATTENDANCE_STUDENT_LAST_NAME,
            ATTENDANCE_STUDENT_FIRST_NAME,
            ATTENDANCE_DATE,
            ATTENDANCE_STUDENT_ATTENDED,
            ATTENDANCE_STUDENT_ABSENT,
            ATTENDANCE_STUDENT_COMMENTS
    };

    public static final String[] ATTENDANCE_FIELDS_SYNC_UP = {
            Constants.ID,
            NAME,
            ATTENDANCE_CLASS_ID,
            ATTENDANCE_STUDENT_ID,
            ATTENDANCE_STUDENT_ATTENDED,
            ATTENDANCE_STUDENT_ABSENT,
            ATTENDANCE_STUDENT_COMMENTS
    };

    private boolean isLocallyModified;

    public AttendanceObject(JSONObject data) {
        super(data);
        objectType = LocalConstants.ATTENDANCE;
        objectId = data.optString(Constants.ID);
        name = data.optString(NAME);
        isLocallyModified = data.optBoolean(SyncTarget.LOCALLY_UPDATED) ||
                data.optBoolean(SyncTarget.LOCALLY_CREATED) ||
                data.optBoolean(SyncTarget.LOCALLY_DELETED);
    }

    public String getAttendanceRefName() {
        return sanitizeText(rawData.optString(NAME));
    }

    public String getAttendanceClassId() {
        return sanitizeText(rawData.optString(ATTENDANCE_CLASS_ID));
    }

    public String getAttendanceClassName() {
        return sanitizeText(rawData.optString(ATTENDANCE_CLASS_NAME));
    }

    public String getAttendanceStudentId() {
        return sanitizeText(rawData.optString(ATTENDANCE_STUDENT_ID));
    }

    public String getAttendanceStudentLastName() {
        return sanitizeText(rawData.optString(ATTENDANCE_STUDENT_LAST_NAME));
    }

    public String getAttendanceStudentFirstName() {
        return sanitizeText(rawData.optString(ATTENDANCE_STUDENT_LAST_NAME));
    }

    public String getAttendanceDate() {
        return sanitizeText(rawData.optString(ATTENDANCE_DATE));
    }

    public String getAttendanceStudentAttended() {
        return sanitizeText(rawData.optString(ATTENDANCE_STUDENT_ATTENDED));
    }

    public String getAttendanceStudentAbsent() {
        return sanitizeText(rawData.optString(ATTENDANCE_STUDENT_ABSENT));
    }

    public String getAttendanceStudentComments() {
        return sanitizeText(rawData.optString(ATTENDANCE_STUDENT_COMMENTS));
    }

    public String getObjectId() { return objectId; }

    public boolean isLocallyModified() {
        return isLocallyModified;
    }


}
