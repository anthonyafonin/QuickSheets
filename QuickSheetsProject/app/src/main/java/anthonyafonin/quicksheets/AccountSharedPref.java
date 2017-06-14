/*
 * Programmer: Afonin, Anthony
 * Chemeketa Community College
 * Created: Tuesday, June 13
 * Assignment: CIS234J, Final Project - QuickSheets
 * File Name: AccountSharedPref.java
 */

/**
 * An application that manages timesheet using a sqlite database.
 */
package anthonyafonin.quicksheets;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Creates a Shared Preference for current User
 */
public class AccountSharedPref {

    // Declare variables and objects.
    private SharedPreferences sharedPreferences;
    private static String PREF_NAME = "prefs";

    /**
     * Empty Constructor
     */
    public AccountSharedPref(){
    }

    /**
     * Accesses Shared Preference
     * @param context Context.
     * @return Shared Preference
     */
    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Saves the ID of the current Logged in Account.
     * @param context Context.
     * @param id Account Id.
     */
    public static void saveId(Context context, int id) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt("AccountId", id);
        editor.apply();
    }

    /**
     * Returns the Account Id of the current user logged in
     * @param context Context.
     * @return Logged in user Account Id.
     */
    public static int loadAccountId(Context context){
        return getPrefs(context).getInt("AccountId", 0);
    }

    /**
     * Deletes Shared Preference
     * @param context Context.
     */
    public static void logoutUser(Context context){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.remove("AccountId");
        editor.apply();
    }
}
