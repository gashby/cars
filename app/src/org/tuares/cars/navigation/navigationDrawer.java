package org.tuares.cars.navigation;

/**
 * Created by gashby on 23.01.2018.
 */

import android.app.Activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;


import org.tuares.cars.R;
import org.tuares.cars.ui.MainActivity;


public class navigationDrawer {

    private static final int PROFILE_SETTING = 100000;

    //save our header or result
    private AccountHeader headerResult = null;

    public static void getDrawer(final Activity activity, Toolbar toolbar) {


        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withTranslucentStatusBar(true)
                .withSelectionListEnabledForSingleProfile(false)
                .withHeaderBackground(R.drawable.tuares_header)
                .addProfiles(
                        new ProfileDrawerItem().withName("Guy Ashby").withEmail("gashby@salesforce.com").withIcon(R.drawable.logo)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();


        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem drawerItemAttendance= new PrimaryDrawerItem().withIdentifier(0).withName("Attendance").withIcon(R.drawable.ic_attendance);

        PrimaryDrawerItem drawerItemManageStudent = new PrimaryDrawerItem().withIdentifier(1)
                .withName(R.string.drawer_item_student).withIcon(R.drawable.ic_person_white_18dp);

        PrimaryDrawerItem drawerItemManageClass = new PrimaryDrawerItem()
                .withIdentifier(2).withName(R.string.drawer_item_class).withIcon(R.drawable.ic_group_white_18dp);

        SecondaryDrawerItem drawerItemSwitchAccount = new SecondaryDrawerItem().withIdentifier(3)
                .withName(R.string.drawer_item_switch_account).withIcon(R.drawable.ic_switched_account_white_18dp);

        SecondaryDrawerItem drawerItemLogout = new SecondaryDrawerItem().withIdentifier(4)
                .withName(R.string.logout_button).withIcon(R.drawable.ic_power_settings_new_white_18dp);
        /*
        SecondaryDrawerItem drawerItemHelp = new SecondaryDrawerItem().withIdentifier(5)
                .withName(R.string.help).withIcon(R.drawable.ic_help_black_24px);
        SecondaryDrawerItem drawerItemDonate = new SecondaryDrawerItem().withIdentifier(6)
                .withName(R.string.donate).withIcon(R.drawable.ic_payment_black_24px); */

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(-1)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        drawerItemAttendance,
                        drawerItemManageStudent,
                        drawerItemManageClass,
                        new DividerDrawerItem(),
                        drawerItemSwitchAccount,
                        drawerItemLogout
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 2 && !(activity instanceof MainActivity)) {
                            // load tournament screen
                            // Intent intent = new Intent(activity, MainActivity.class);
                            // view.getContext().startActivity(intent);
                        }
                        return true;
                    }
                })
                .build();
    }
}


