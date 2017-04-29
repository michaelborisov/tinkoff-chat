package borisov.ru.tinkoff_chat.activities;

import android.app.Dialog;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import borisov.ru.tinkoff_chat.R;
import borisov.ru.tinkoff_chat.services.LoginService;
import borisov.ru.tinkoff_chat.ui.widgets.ProgressButton;

public class LoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;
    private ProgressButton button;

    public static final String PENDING_INTENT = "pi";
    public static final String EXTRA_SUCCESS = "extra_success";
    public static final String CREDENTIALS = "credentials";
    public static final int LOGIN_REQUEST_CODE = 1;

    public static boolean isLoginServiceWorking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (EditText) findViewById(R.id.edit_text_login);
        password = (EditText) findViewById(R.id.edit_text_password);

        button = (ProgressButton) findViewById(R.id.btn_enter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                Intent data = new Intent().putExtra(CREDENTIALS, new String[]{});
                PendingIntent pi = createPendingResult(LOGIN_REQUEST_CODE, data, 0);
                startService(new Intent(LoginActivity.this, LoginService.class).putExtra(PENDING_INTENT, pi));
            }
        });
    }

    private void startNextScreen() {
        Intent goToNav = new Intent(getApplicationContext(), NavigationActivity.class);
        goToNav.putExtra("userName", login.getText().toString());
        startActivity(goToNav);
    }

    public void showProgress() {
        button.showProgress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isLoginServiceWorking){
            showProgress();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.i("LoginActivity", "onActivityResultC " + toString());
                saveCredentials(login.getText().toString(), password.getText().toString());
                startNextScreen();
                hideProgress();
            } else {
                new LoginActivity.MyDialogFragment().show(this.getSupportFragmentManager(), null);
            }
        }
    }

    public void hideProgress() {
        button.hideProgress();
    }



    private void saveCredentials(String username, String password ){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.user_name_key), username);
        editor.putString(getString(R.string.user_password_key), password);
        editor.apply();

    }

    public static class MyDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog dialog = super.onCreateDialog(savedInstanceState);
            dialog.setTitle("Неверный логин и/или пароль. Попробуйте ещё раз.");
            return dialog;
        }
    }
}

