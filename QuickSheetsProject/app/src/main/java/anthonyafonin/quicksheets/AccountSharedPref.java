package anthonyafonin.quicksheets;

import android.content.Context;
import android.content.SharedPreferences;

public class AccountSharedPref {

    private SharedPreferences sharedPreferences;
    private static String PREF_NAME = "prefs";

    // Constructor
    public AccountSharedPref(){
    }

    // Accesses SharedPreference
    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Saves the ID of the current Logged in Account.
    public static void saveId(Context context, int id) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt("AccountId", id);
        editor.apply();
    }

    // Returns the Account Id of the current user logged in
    public static int loadAccountId(Context context){
        return getPrefs(context).getInt("AccountId", 0);
    }

    // Deletes Shared Preference
    public static void logoutUser(Context context){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.remove("AccountId");
        editor.apply();
    }
}
