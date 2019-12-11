package amsi.dei.estg.ipleiria.infortec_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

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
    }
    private void carregarFragmentoInicial(){
        navigationView.setCheckedItem(R.id.nav_home);
        Fragment fragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                setTitle(menuItem.getTitle());
                break;
            /*case R.id.nav_ead:
                fragment = new EadFragment();
                setTitle(menuItem.getTitle());
                break;
            case R.id.nav_sistag:
                fragment = new SistagFragment();
                setTitle(menuItem.getTitle());
                break;
            case R.id.nav_email:
                fragment = new EmailFragment();
                setTitle(menuItem.getTitle());
                break;*/
        }
        if (fragment != null)
            fragmentManager.beginTransaction().replace(R.id.contentFragment,
                    fragment).commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
