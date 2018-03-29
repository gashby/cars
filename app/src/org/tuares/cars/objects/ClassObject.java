package org.tuares.cars.objects;

import android.os.Parcelable;

import com.salesforce.androidsdk.smartsync.model.SalesforceObject;
import com.salesforce.androidsdk.smartsync.target.SyncTarget;
import com.salesforce.androidsdk.smartsync.util.Constants;

import org.tuares.cars.utils.LocalConstants;
import org.json.JSONObject;

import static org.tuares.cars.utils.TextUtil.sanitizeText;

public class ClassObject extends SalesforceObject {

    public static final String NAME = "Name";
    public static final String CLASS_NAME = "Class_Name__c";
    public static final String CLASS_CATEGORY = "Class_Category__c";
    public static final String CLASS_GRADE = "Grade__c";
    public static final String CLASS_TYPE = "Type__c";
    public static final String CLASS_HELD = "Held__c";
    public static final String CLASS_ENROLLED_COUNT = "StudentsEnrolled__c";
    public static final String CLASS_TEACHER = "Teacher__c";

    public static final String[] CLASS_FIELDS_SYNC_DOWN = {
            NAME,
            CLASS_NAME,
            CLASS_TYPE,
            CLASS_ENROLLED_COUNT,
            CLASS_CATEGORY,
            CLASS_GRADE,
            CLASS_HELD,
            CLASS_TEACHER
    };

    public static final String[] CLASS_FIELDS_SYNC_UP = {
            Constants.ID,
            NAME,
            CLASS_NAME
    };

    private boolean isLocallyModified;

    public ClassObject(JSONObject data) {
        super(data);
        objectType = LocalConstants.CLASS;
        objectId = data.optString(Constants.ID);
        name = data.optString(NAME);
        isLocallyModified = data.optBoolean(SyncTarget.LOCALLY_UPDATED) ||
                data.optBoolean(SyncTarget.LOCALLY_CREATED) ||
                data.optBoolean(SyncTarget.LOCALLY_DELETED);
    }

    public String getClassRefName() {
        return sanitizeText(rawData.optString(NAME));
    }

    public String getClassName() {
        return sanitizeText(rawData.optString(CLASS_NAME));
    }

    public String getClassCategory() {
        return sanitizeText(rawData.optString(CLASS_CATEGORY));
    }

    public String getClassType() {
        return sanitizeText(rawData.optString(CLASS_TYPE));
    }

    public String getClassGrade() {
        return sanitizeText(rawData.optString(CLASS_GRADE));
    }

    public String getClassHeld() {
        return sanitizeText(rawData.optString(CLASS_HELD));
    }

    public String getEnrollmentCount() {
        return sanitizeText(rawData.optString(CLASS_ENROLLED_COUNT));
    }

    public String getClassTeacher() {
        return sanitizeText(rawData.optString(CLASS_TEACHER));
    }


    public String getObjectId() { return objectId; }
    /**
     * Returns whether the contact has been locally modified or not.
     *
     * @return True - if the contact has been locally modified, False - otherwise.
     */
    public boolean isLocallyModified() {
        return isLocallyModified;
    }


}




