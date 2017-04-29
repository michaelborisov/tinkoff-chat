package borisov.ru.tinkoff_chat.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import borisov.ru.tinkoff_chat.R;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        colorStatusBar();
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        final Drawable drawable = imageView.getDrawable();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(drawable instanceof AnimatedVectorDrawable) {
                ((AnimatedVectorDrawable) drawable).start();
            }else {
                Toast.makeText(this, "NO ANIMATION :(", Toast.LENGTH_SHORT).show();
            }
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                checkCredentials();
            }
        }, 3000);
    }

    private void checkCredentials (){
        String[] credentials = readCredentials();
        if (!credentials[0].equals("") && !credentials[1].equals("")){
            Intent goToNav = new Intent(getApplicationContext(), NavigationActivity.class);
            goToNav.putExtra("userName", credentials[0]);
            startActivity(goToNav);
        }else{
            Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(goToLogin);
        }
    }
    private String[] readCredentials(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String userName = sharedPref.getString(getString(R.string.user_name_key), "");
        String userPassword = sharedPref.getString(getString(R.string.user_password_key), "");
        return new String[]{userName, userPassword};
    }

    private void colorStatusBar(){
        if  (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }
}
