package mn.munkhuu.testnewapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import mn.munkhuu.personaltab.PersonalTab;
import mn.munkhuu.personaltab.PersonalTabHost;
import mn.munkhuu.personaltab.PersonalTabListener;

public class MainActivity extends AppCompatActivity implements PersonalTabListener {


    private PersonalTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabHost = (PersonalTabHost) findViewById(R.id.tabHost);
        tabHost.setPersonalTabListener(this);

        for (int i = 0; i < 4; i++) {
            PersonalTab tab = tabHost.newTab();
            tab.setIcon(getResources().getDrawable(android.support.v7.appcompat.R.drawable.abc_ic_menu_share_mtrl_alpha));
            tabHost.addTab(tab);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(PersonalTab tab) {
        Log.d("TEST","SELECT"+tab.getPosition());
    }

    @Override
    public void onTabReselected(PersonalTab tab) {
        Log.d("TEST","RESELECTED"+tab.getPosition());

    }

    @Override
    public void onTabUnselected(PersonalTab tab) {
        Log.d("TEST","UNSELECT"+tab.getPosition());

    }
}
