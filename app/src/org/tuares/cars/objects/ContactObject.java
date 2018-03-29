package org.tuares.cars.objects;

/**
 * Created by gashby on 07.08.2017.
 */

/*
 * Copyright (c) 2014-present, salesforce.com, inc.
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


import org.json.JSONObject;

import com.salesforce.androidsdk.smartsync.model.SalesforceObject;
import com.salesforce.androidsdk.smartsync.util.Constants;
import com.salesforce.androidsdk.smartsync.target.SyncTarget;

import static org.tuares.cars.utils.TextUtil.sanitizeText;

/**
 * A simple representation of a Contact object fo type student
 *
 * @author gashby
 *
 * 2017 08 17
 *
 */
public class ContactObject extends SalesforceObject {

    public static final String FIRST_NAME = "FirstName";
    public static final String LAST_NAME = "LastName";
    public static final String TITLE = "Title";
    public static final String PHONE = "MobilePhone";
    public static final String PROTECTED = "IsUnderProtection__c";
    public static final String LANGUAGE_SKILL = "Has_Language__c";
    public static final String IT_SKILL = "Has_IT_Skills__c";
    public static final String LIFE_SKILL = "Has_Life_Skill__c";
    public static final String JOURNEY = "JourneyToSchool__c";
    public static final String LIVESWITH = "LivesWith__c";
    public static final String HABITAT = "HabitatSituation__c";
    public static final String DOB = "DateOfBirth__c";
    public static final String POB = "PlaceOfBirth__c";
    public static final String LASTMODDATE ="LastModifiedDate";
    public static final String STUDENT = "IsStudent__c";
    public static final String GRADE ="Student_Grad_Level__c";

    public static final String[] CONTACT_FIELDS_SYNC_DOWN = {
            FIRST_NAME,
            LAST_NAME,
            TITLE,
            PHONE,
            PROTECTED,
            LANGUAGE_SKILL,
            IT_SKILL,
            LIFE_SKILL,
            JOURNEY,
            LIVESWITH,
            HABITAT,
            DOB,
            POB,
            LASTMODDATE,
            STUDENT,
            GRADE
    };
    public static final String[] CONTACT_FIELDS_SYNC_UP = {
            Constants.ID,
            FIRST_NAME,
            LAST_NAME,
            TITLE,
            PHONE,
            PROTECTED,
            LANGUAGE_SKILL,
            IT_SKILL,
            LIFE_SKILL,
            JOURNEY,
            LIVESWITH,
            HABITAT,
            DOB,
            POB
    };

    private boolean isLocallyModified;

    /**
     * Parameterized constructor.
     *
     * @param data Raw data.
     */
    public ContactObject(JSONObject data) {
        super(data);
        objectType = Constants.CONTACT;
        objectId = data.optString(Constants.ID);
        name = data.optString(FIRST_NAME) + " " + data.optString(LAST_NAME);
        isLocallyModified = data.optBoolean(SyncTarget.LOCALLY_UPDATED) ||
                data.optBoolean(SyncTarget.LOCALLY_CREATED) ||
                data.optBoolean(SyncTarget.LOCALLY_DELETED);
    }

    public String getFirstName() {
        return sanitizeText(rawData.optString(FIRST_NAME));
    }

    public String getLastName() {
        return sanitizeText(rawData.optString(LAST_NAME));
    }

    public String getTitle() {
        return sanitizeText(rawData.optString(TITLE));
    }

    public String getPhone() {
        return sanitizeText(rawData.optString(PHONE));
    }

    public String getProtected() {
        return sanitizeText(rawData.optString(PROTECTED));
    }

    public String getHasLanguageSkill() {
        return sanitizeText(rawData.optString(LANGUAGE_SKILL));
    }
    public String getHasITSkill() {
        return sanitizeText(rawData.optString(IT_SKILL));
    }
    public String getHasLifeSkill() {
        return sanitizeText(rawData.optString(LIFE_SKILL));
    }

    public String getJourney() {
        return sanitizeText(rawData.optString(JOURNEY));
    }

    public String getLivesWith() {
        return sanitizeText(rawData.optString(LIVESWITH));
    }

    public String getHabitat() {
        return sanitizeText(rawData.optString(HABITAT));
    }

    public String getDOB() {
        return sanitizeText(rawData.optString(DOB));
    }

    public String getPOB() {
        return sanitizeText(rawData.optString(POB));
    }

    public String getLastModDate() {
        return sanitizeText(rawData.optString(LASTMODDATE));
    }

    public String getStudent() {
        return sanitizeText(rawData.optString(STUDENT));
    }

    public String getGrade() {
        return sanitizeText(rawData.optString(GRADE));
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

