package br.com.badiale.tweetmood.timber;

import com.crashlytics.android.Crashlytics;

import timber.log.Timber;

public class TimberProductionLog extends Timber.Tree {
    @Override
    protected void log(final int priority, final String tag, final String message, final Throwable t) {
        Crashlytics.log(priority, tag, message);
        if (t != null) {
            Crashlytics.logException(t);
        }
    }
}
