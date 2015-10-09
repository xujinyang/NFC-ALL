package jamesxu.nfcall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import de.greenrobot.event.EventBus;

/**
 * Created by james on 29/6/15.
 */
public class BaseActivity extends Activity {
    protected Activity activity;
    protected EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        eventBus = EventBus.getDefault();
        eventBus.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    //EventBus至少有一个onEvent方法
    public void onEvent(String defaultEvent) {

    }

    protected void startActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

}
