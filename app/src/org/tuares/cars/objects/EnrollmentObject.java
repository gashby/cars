package org.tuares.cars.objects;

/**
 * Created by gashby on 08.08.2017.
 */

import com.salesforce.androidsdk.smartsync.model.SalesforceObject;
import com.salesforce.androidsdk.smartsync.target.SyncTarget;
import com.salesforce.androidsdk.smartsync.util.Constants;
import org.tuares.cars.utils.LocalConstants;
import org.json.JSONObject;
import static org.tuares.cars.utils.TextUtil.sanitizeText;


public class EnrollmentObject extends SalesforceObject {

    public static final String NAME = "Name";
    public static final String ENROLLMENT_CLASS = "Class__c";
    public static final String ENROLLMENT_STUDENT = "Student__c";
    public static final String ENROLLMENT_CLASS_CATEGORY = "Class_Category__c";
    public static final String ENROLLMENT_DATE = "EnrollmentDate__c";

    public static final String[] ENROLLMENT_FIELDS_SYNC_DOWN = {
            NAME,
            ENROLLMENT_CLASS,
            ENROLLMENT_STUDENT,
            ENROLLMENT_CLASS_CATEGORY,
            ENROLLMENT_DATE
    };

    public static final String[] ENROLLMENT_FIELDS_SYNC_UP = {
            Constants.ID,
            NAME,
            ENROLLMENT_CLASS,
            ENROLLMENT_STUDENT,
            ENROLLMENT_CLASS_CATEGORY,
            ENROLLMENT_DATE
    };

    private boolean isLocallyModified;

    public EnrollmentObject(JSONObject data) {
        super(data);
        objectType = LocalConstants.ENROLLMENT;
        objectId = data.optString(Constants.ID);
        name = data.optString(NAME);
        isLocallyModified = data.optBoolean(SyncTarget.LOCALLY_UPDATED) ||
                data.optBoolean(SyncTarget.LOCALLY_CREATED) ||
                data.optBoolean(SyncTarget.LOCALLY_DELETED);
    }

    public String getEnrollmentRefName() {
        return sanitizeText(rawData.optString(NAME));
    }

    public String getEnrollmentClass() {
        return sanitizeText(rawData.optString(ENROLLMENT_CLASS));
    }

    public String getEnrollmentStudent() {
        return sanitizeText(rawData.optString(ENROLLMENT_STUDENT));
    }

    public String getEnrollmentCategory() {
        return sanitizeText(rawData.optString(ENROLLMENT_CLASS_CATEGORY));
    }

    public String getEnrollmentDate() {
        return sanitizeText(rawData.optString(ENROLLMENT_DATE));
    }

    public String getObjectId() { return objectId; }

    public boolean isLocallyModified() {
        return isLocallyModified;
    }



}


