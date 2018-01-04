package br.com.badiale.tweetmood.sync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Objects;

public class RescheduleJobOnBoot extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (Objects.equals(intent.getAction(), Intent.ACTION_BOOT_COMPLETED)) {
            TweetUpdaterJobService.schedule(context);
        }
    }
}
