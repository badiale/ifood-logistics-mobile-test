package br.com.badiale.tweetmood.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.badiale.tweetmood.eventbus.EventBusUtils;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    private final int layoutId;

    protected BaseActivity(final int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        ButterKnife.bind(this);
        EventBusUtils.register(this);
    }
}
