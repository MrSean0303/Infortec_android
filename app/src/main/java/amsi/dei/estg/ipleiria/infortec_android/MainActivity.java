package amsi.dei.estg.ipleiria.infortec_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import amsi.dei.estg.ipleiria.infortec_android.models.SingletonGestorTabelas;
import amsi.dei.estg.ipleiria.infortec_android.models.User;
import amsi.dei.estg.ipleiria.infortec_android.utils.UserJsonParser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private FragmentManager fragmentManager;
    private String username;
    private TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();
        drawer.addDrawerListener(toggle);
        fragmentManager = getSupportFragmentManager();

        navigationView.setNavigationItemSelectedListener(this);
        carregarFragmentoInicial();

        SingletonGestorTabelas.getInstance(getApplicationContext()).initialize(this);

        //Vai buscar o tvUsername ao header da navbar e muda o nome
        View headerLayout = navigationView.getHeaderView(0);
        tvUsername = headerLayout.findViewById(R.id.tvUsername);
        changeLogin();
    }

    private void carregarFragmentoInicial(){
        navigationView.setCheckedItem(R.id.nav_home);
        Fragment fragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

    }

    public void changeLogin(){
        //Verificar se a algo nas SharedPreferences
        SharedPreferences pref = SingletonGestorTabelas.getInstance(getApplicationContext()).readPreferences(getApplicationContext());
        username = pref.getString("username", null);
        System.out.println("--> User: " + username);

        //Se existir SharedPreferences e pk o login já foi efetuado
        if (username != null){
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_loginOut).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_Perfil).setVisible(true);
            tvUsername.setText(username);
        }else{
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_loginOut).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_Perfil).setVisible(false);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;
        Activity activity = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                setTitle(menuItem.getTitle());

                this.changeLogin();
                break;
            /*case R.id.nav_ead:
                fragment = new EadFragment();
                setTitle(menuItem.getTitle());
                break;*/
            case R.id.nav_promocoes:
                fragment = new PromocoesFragment();
                setTitle(menuItem.getTitle());
                break;
            case R.id.nav_login:
                fragment = new LoginFragment();
                setTitle(menuItem.getTitle());
                break;
            case R.id.nav_loginOut:
                SingletonGestorTabelas.getInstance(getApplicationContext()).removePreference("username");
                SingletonGestorTabelas.getInstance(getApplicationContext()).removePreference("password");
                SingletonGestorTabelas.getInstance(getApplicationContext()).removerUserDB();

                this.changeLogin();
                break;
            case R.id.nav_Perfil:
                activity = new Activity();
                setTitle(menuItem.getTitle());
                break;
        }
        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.contentFragment,
                    fragment).commit();
        }

        if (activity != null){
            Intent intent = new Intent(getBaseContext(), UserProfileActivity.class);
            startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
