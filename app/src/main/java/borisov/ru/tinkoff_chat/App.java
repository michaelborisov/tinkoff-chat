package borisov.ru.tinkoff_chat;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by michaelborisov on 29.04.17.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
