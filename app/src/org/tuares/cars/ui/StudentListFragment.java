package org.tuares.cars.ui;

import android.accounts.Account;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.TextView;
import android.widget.Toast;

import com.salesforce.androidsdk.accounts.UserAccount;
import com.salesforce.androidsdk.rest.RestClient;
import com.salesforce.androidsdk.smartstore.store.SmartStore;
import com.salesforce.androidsdk.smartsync.app.SmartSyncSDKManager;

import org.tuares.cars.R;
import org.tuares.cars.adapter.ContactListAdapter;
import org.tuares.cars.loaders.ContactListLoader;
import org.tuares.cars.objects.ContactObject;
import org.tuares.cars.utils.LocalConstants;

import java.util.ArrayList;
import java.util.List;

import static org.tuares.cars.utils.LocalConstants.CONTACT_COLORS;

public class StudentListFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<List<ContactObject>>,
                   SearchView.OnQueryTextListener, OnCloseListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int CONTACT_LOADER_ID = 1;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SearchView searchView;
    private ContactListAdapter listAdapter;
    private View contactListView;
    private NameFieldFilter nameFilter;
    private List<ContactObject> originalData;

    private UserAccount curAccount;
    private SmartStore smartStore;
    private ContactListLoader contactLoader;

    private OnStudentListFragementListener mListener;

    public StudentListFragment()  {
        // Required empty public constructor
    }

    public interface OnStudentListFragementListener {
        public void onStudentSelected(Object sObject);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentListFragment.
     */

    public static StudentListFragment newInstance(String param1, String param2) {
        StudentListFragment fragment = new StudentListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStudentListFragementListener ) {
            mListener = (OnStudentListFragementListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStudentListFragementListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<List<ContactObject>> onCreateLoader(int id, Bundle args) {
        contactLoader = new ContactListLoader(getActivity(), this.curAccount );
        return contactLoader;
    }

    @Override
    public void onLoaderReset(Loader<List<ContactObject>> loader) {
        originalData = null;
        refreshList(null);
    }

    @Override
    public void onLoadFinished(Loader<List<ContactObject>> loader,
                               List<ContactObject> data) {
        originalData = data;
        nameFilter.setOrigData(originalData);
        refreshList(data);
    }


    public boolean onClose() {
        refreshList(originalData);
        return true;
    }


    public boolean onQueryTextSubmit(String query) {
        filterList(query);
        return true;
    }


    public boolean onQueryTextChange(String newText) {
        filterList(newText);
        return true;
    }


    public void onListItemClick(ListView l, View v, int position, long id) {
        final ContactObject sObject = listAdapter.getItem(position);
         // pass back object to activity and refresh the detail fragment
        if (mListener != null) {
            mListener.onStudentSelected(sObject);
        }
    }

    private void refreshList() {
        getLoaderManager().getLoader(CONTACT_LOADER_ID).forceLoad();
    }

    private void refreshList(List<ContactObject> data) {
        listAdapter.setData(data);
    }


    private void filterList(String filterTerm) {
        nameFilter.filter(filterTerm);
    }


    /**
     * A simple utility class to implement filtering.
     *
     * @author bhariharan
     */
    private static class NameFieldFilter extends Filter {

        private ContactListAdapter adapter;
        private List<ContactObject> origList;

        /**
         * Parameterized constructor.
         *
         * @param adapter List adapter.
         * @param origList List to perform filtering against.
         */
        public NameFieldFilter(ContactListAdapter adapter, List<ContactObject> origList) {
            this.adapter = adapter;
            this.origList = origList;
        }

        /**
         * Sets the original data set.
         *
         * @param origData Original data set.
         */
        public void setOrigData(List<ContactObject> origData) {
            origList = origData;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (origList == null) {
                return null;
            }
            final FilterResults results = new FilterResults();
            if (TextUtils.isEmpty(constraint)) {
                results.values = origList;
                results.count = origList.size();
                return results;
            }
            final String filterString = constraint.toString().toLowerCase();
            int count = origList.size();
            String filterableString;
            final List<ContactObject> resultSet = new ArrayList<ContactObject>();
            for (int i = 0; i < count; i++) {
                filterableString = origList.get(i).getName();
                if (filterableString.toLowerCase().contains(filterString)) {
                    resultSet.add(origList.get(i));
                }
            }
            results.values = resultSet;
            results.count = resultSet.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results != null && results.values != null) {
                adapter.setData((List<ContactObject>
                        ) results.values);
            }
        }
    }



    /** Sync Contacts **/

    private void syncDownContacts() {
        contactLoader.syncDown();
        Toast.makeText(getActivity(), "Sync down complete!",
                Toast.LENGTH_LONG).show();
    }

    private void syncUpContacts() {
        contactLoader.syncUp();
        Toast.makeText(getActivity(), "Sync up complete!", Toast.LENGTH_LONG).show();
    }

    public void onResume(RestClient client) {
        curAccount = SmartSyncSDKManager.getInstance().getUserAccountManager().getCurrentUser();
        Account account = null;
        if (curAccount != null) {
            account = SmartSyncSDKManager.getInstance().getUserAccountManager().buildAccount(curAccount);
        }
        smartStore = SmartSyncSDKManager.getInstance().getSmartStore(curAccount);
        getLoaderManager().initLoader(CONTACT_LOADER_ID, null, this);
        if (!smartStore.hasSoup(ContactListLoader.CONTACT_SOUP)) {
            syncDownContacts();
        }
    }
}
