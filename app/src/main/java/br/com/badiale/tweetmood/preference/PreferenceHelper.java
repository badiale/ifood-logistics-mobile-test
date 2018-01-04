package br.com.badiale.tweetmood.preference;

import android.content.Context;
import android.content.SharedPreferences;

import static com.google.common.base.MoreObjects.firstNonNull;

public class PreferenceHelper {
    private static final String PREFERENCE_FILE = "tweetMoodPreferences";
    private static final String LAST_UPDATE_URL = "LAST_UPDATE_URL";
    private static final String LAST_USER_NAME = "LAST_USER_NAME";

    private SharedPreferences sharedPreferences;

    public static PreferenceHelper from(Context context) {
        return new PreferenceHelper(context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE));
    }

    private PreferenceHelper(final SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void setLastUpdateUrl(String url) {
        sharedPreferences.edit()
                .putString(LAST_UPDATE_URL, firstNonNull(url, ""))
                .apply();
    }

    public String getLastUpdateUrl() {
        return sharedPreferences.getString(LAST_UPDATE_URL, "");
    }

    public void setLastUser(String username) {
        sharedPreferences.edit()
                .putString(LAST_USER_NAME, firstNonNull(username, ""))
                .apply();
    }

    public String getLastUser() {
        return sharedPreferences.getString(LAST_USER_NAME, "");
    }
}
