package borisov.ru.tinkoff_chat.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import borisov.ru.tinkoff_chat.R;
import borisov.ru.tinkoff_chat.fragments.AboutFragment;
import borisov.ru.tinkoff_chat.fragments.DialogListFragment;
import borisov.ru.tinkoff_chat.fragments.SettingsFragment;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final static int MENU_DIALOGS = 0;
    private ActionBarDrawerToggle toggle;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_dialogs:
                DialogListFragment dialogsFragment = DialogListFragment.newInstance("Диалоги");
                replaceFragment(dialogsFragment);
                break;
            case R.id.nav_settings:
                SettingsFragment settingsFragment = SettingsFragment.newInstance("Настройки");
                replaceFragment(settingsFragment);
                break;
            case R.id.nav_about:
                AboutFragment aboutFragment = AboutFragment.newInstance("О приложении");
                replaceFragment(aboutFragment);
                break;
            case R.id.nav_exit:
                deleteCredentials();
                finish();
                Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(goToLogin);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void deleteCredentials(){
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.user_name_key),
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.user_name_key), "");
        editor.putString(getString(R.string.user_password_key), "");
        editor.apply();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Мессенджер");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        drawer.addDrawerListener(toggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initNavigationHeader(navigationView.getHeaderView(0));

        if (savedInstanceState == null) {
            navigationView.getMenu().getItem(MENU_DIALOGS).setChecked(true);
            onNavigationItemSelected(navigationView.getMenu().getItem(MENU_DIALOGS));
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction = fragmentTransaction.replace(R.id.content_navigation, fragment);
        fragmentTransaction.commit();
    }

    private void initNavigationHeader(View navView){
        String userName = getIntent().getStringExtra("userName");
        TextView login = (TextView)navView.findViewById(R.id.nav_login);
        login.setText(userName);
    }
}
