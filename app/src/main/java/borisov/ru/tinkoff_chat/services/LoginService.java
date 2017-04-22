package borisov.ru.tinkoff_chat.services;

import android.app.Activity;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import borisov.ru.tinkoff_chat.activities.LoginActivity;
import borisov.ru.tinkoff_chat.ui.widgets.ProgressButton;

import static android.app.Activity.RESULT_OK;
import static borisov.ru.tinkoff_chat.activities.LoginActivity.isLoginServiceWorking;

/**
 * Created by michaelborisov on 22.04.17.
 */

public class LoginService extends IntentService {

    public LoginService() {
        super("LoginService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            isLoginServiceWorking = true;
            Thread.sleep(10000);
            Intent result = new Intent().putExtra(LoginActivity.EXTRA_SUCCESS, false);
            PendingIntent pendingIntent = intent.getParcelableExtra(LoginActivity.PENDING_INTENT);
            try {
                pendingIntent.send(this, RESULT_OK, result);
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopSelf();
        isLoginServiceWorking =  false;
    }
}
