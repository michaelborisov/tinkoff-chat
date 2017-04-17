package borisov.ru.tinkoff_chat.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import borisov.ru.tinkoff_chat.R;
import borisov.ru.tinkoff_chat.ui.widgets.ProgressButton;

public class LoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;
    private ProgressButton button;

    private LoginTask task = new LoginTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkCredentials();
        login = (EditText) findViewById(R.id.edit_text_login);
        password = (EditText) findViewById(R.id.edit_text_password);
        button = (ProgressButton) findViewById(R.id.btn_enter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.execute();
                saveCredentials(login.getText().toString(), password.getText().toString());
            }
        });
    }

    private void startNextScreen() {
        Intent goToNav = new Intent(getApplicationContext(), NavigationActivity.class);
        goToNav.putExtra("userName", login.getText().toString());
        startActivity(goToNav);
    }

    private class LoginTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            button.showProgress();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            button.hideProgress();
            startNextScreen();
        }
    }

    private void checkCredentials (){
        String[] credentials = readCredentials();
        if (!credentials[0].equals("") && !credentials[1].equals("")){
            Intent goToNav = new Intent(getApplicationContext(), NavigationActivity.class);
            goToNav.putExtra("userName", credentials[0]);
            startActivity(goToNav);
        }
    }

    private void saveCredentials(String username, String password ){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.user_name_key), username);
        editor.putString(getString(R.string.user_password_key), password);
        editor.apply();

    }

    private String[] readCredentials(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String userName = sharedPref.getString(getString(R.string.user_name_key), "");
        String userPassword = sharedPref.getString(getString(R.string.user_password_key), "");
        return new String[]{userName, userPassword};
    }
}

