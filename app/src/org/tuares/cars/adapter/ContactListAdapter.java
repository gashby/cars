package org.tuares.cars.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import org.tuares.cars.R;
import org.tuares.cars.objects.ContactObject;
import org.tuares.cars.utils.LocalConstants;

import java.util.ArrayList;
import java.util.List;

import static org.tuares.cars.utils.LocalConstants.CONTACT_COLORS;

/**
 * Created by gashby on 24.02.2018.
 */


public class ContactListAdapter extends ArrayAdapter<ContactObject> {

    private int listItemLayoutId;
    private List<ContactObject> sObjects;

    /**
     * Parameterized constructor.
     *
     * @param context Context.
     * @param listItemLayoutId List item view resource ID.
     */

    public ContactListAdapter(Context context, int listItemLayoutId) {
        super(context, listItemLayoutId);
        this.listItemLayoutId = listItemLayoutId;
    }

    /**
     * Sets data to this adapter.
     *
     * @param data Data.
     */
    public void setData(List<ContactObject> data) {
        clear();
        sObjects = data;
        if (data != null) {
            addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(listItemLayoutId, null);
        }
        if (sObjects != null) {
            final ContactObject sObject = sObjects.get(position);
            if (sObject != null) {
                final TextView objName = (TextView) convertView.findViewById(R.id.obj_name);
                final TextView objType = (TextView) convertView.findViewById(R.id.obj_type);
                final TextView objImage = (TextView) convertView.findViewById(R.id.obj_image);
                if (objName != null) {
                    objName.setText(sObject.getName());
                }
                if (objType != null) {
                    objType.setText(sObject.getTitle());
                }
                if (objImage != null) {
                    final String firstName = sObject.getFirstName();
                    String initials = LocalConstants.EMPTY_STRING;
                    if (firstName.length() > 0) {
                        initials = firstName.substring(0, 1);
                    }
                    objImage.setText(initials);
                    setBubbleColor(getContext(),objImage, firstName);
                }
            }
        }
        return convertView;
    }

    private void setBubbleColor(Context context, TextView tv, String firstName) {
        firstName = firstName.trim();
        int code = 0;
        if (!TextUtils.isEmpty(firstName)) {
            for (int i = 0; i < firstName.length(); i++) {
                code += firstName.charAt(i);
            }
        }
        int colorIndex = code % CONTACT_COLORS.length;
        int color = CONTACT_COLORS[colorIndex];
        final GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setShape(GradientDrawable.OVAL);
        tv.setBackground(drawable);
    }




}
