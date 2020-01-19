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

    }
    private void carregarFragmentoInicial(){
        navigationView.setCheckedItem(R.id.nav_home);
        Fragment fragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        Activity activity = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                setTitle(menuItem.getTitle());
                break;
            case R.id.nav_favoritos:
                fragment = new FavoritoFragment();
                setTitle(menuItem.getTitle());
                break;
            case R.id.nav_promocoes:
                fragment = new PromocoesFragment();
                setTitle(menuItem.getTitle());
                break;
            case R.id.nav_login:
                fragment = new LoginFragment();
                setTitle(menuItem.getTitle());
                break;
            case R.id.nav_Perfil:
                activity = new Activity();
                setTitle(menuItem.getTitle());
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
