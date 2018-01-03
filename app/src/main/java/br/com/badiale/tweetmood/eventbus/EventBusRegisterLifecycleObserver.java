package br.com.badiale.tweetmood.eventbus;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import org.greenrobot.eventbus.EventBus;

class EventBusRegisterLifecycleObserver implements LifecycleObserver {
    private final EventBus eventBus;
    private final LifecycleOwner owner;

    EventBusRegisterLifecycleObserver(final EventBus eventBus, final LifecycleOwner owner) {
        this.eventBus = eventBus;
        this.owner = owner;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void connectListener() {
        eventBus.register(owner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void disconnectListener() {
        eventBus.unregister(owner);
    }

}
