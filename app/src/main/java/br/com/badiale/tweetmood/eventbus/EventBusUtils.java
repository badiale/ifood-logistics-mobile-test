package br.com.badiale.tweetmood.eventbus;

import android.arch.lifecycle.LifecycleOwner;

import org.greenrobot.eventbus.EventBus;

public class EventBusUtils {
    private EventBusUtils() {
        throw new AssertionError("invalid constructor call");
    }

    private static EventBus getInstance() {
        return EventBus.getDefault();
    }

    public static void register(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(new EventBusRegisterLifecycleObserver(getInstance(), lifecycleOwner));
    }

    public static void post(Object event) {
        getInstance().post(event);
    }
}
