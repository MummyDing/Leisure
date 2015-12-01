package com.mummyding.app.leisure.ui;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.support.ScreenUtil;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.ui.daily.DailyFragment;
import com.mummyding.app.leisure.ui.news.BaseNewsFragment;
import com.mummyding.app.leisure.ui.news.NewsFragment;
import com.mummyding.app.leisure.ui.reading.BaseReadingFragment;
import com.mummyding.app.leisure.ui.reading.ReadingActivity;
import com.mummyding.app.leisure.ui.reading.ReadingFragment;
import com.mummyding.app.leisure.ui.science.BaseScienceFragment;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Drawer drawer ;
    private AccountHeader header;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;
    private  Fragment currentFragment ;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScreenUtil.init(this);
        initData();
        currentFragment =new DailyFragment();
        switchFragment();
    }

    private void switchFragment(){
        if(currentFragment instanceof DailyFragment){
            switchFragment(currentFragment, getString(R.string.daily), R.menu.menu_daily);
        }else if(currentFragment instanceof BaseReadingFragment){
            switchFragment(currentFragment, getString(R.string.reading),R.menu.menu_reading);
        }else if(currentFragment instanceof BaseNewsFragment){
            switchFragment(currentFragment, getString(R.string.news),R.menu.menu_news);
        }else if(currentFragment instanceof BaseScienceFragment){
            switchFragment(currentFragment, getString(R.string.science),R.menu.menu_science);
        }
    }
    private void switchFragment(Fragment fragment,String title,int resourceMenu){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(title);
        if(menu != null) {
            menu.clear();
            getMenuInflater().inflate(resourceMenu, menu);
        }
        currentFragment = null;
    }
    private void initData(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         header = new AccountHeaderBuilder().withActivity(this)
                .withCompactStyle(false)
                .withHeaderBackground(R.drawable.header)
                .build();
       drawer = new DrawerBuilder().withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.daily).withIcon(R.mipmap.ic_home).withIdentifier(R.mipmap.ic_home),
                        new PrimaryDrawerItem().withName(R.string.reading).withIcon(R.mipmap.ic_reading).withIdentifier(R.mipmap.ic_reading),
                        new PrimaryDrawerItem().withName(R.string.news).withIcon(R.mipmap.ic_news).withIdentifier(R.mipmap.ic_news),
                        new PrimaryDrawerItem().withName(R.string.science).withIcon(R.mipmap.ic_science).withIdentifier(R.mipmap.ic_science),
                        new PrimaryDrawerItem().withName(R.string.shake).withIcon(R.mipmap.ic_shake).withIdentifier(R.mipmap.ic_shake),
                        new SectionDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.setting).withIcon(R.mipmap.ic_setting).withIdentifier(R.mipmap.ic_setting),
                        new SecondaryDrawerItem().withName(R.string.about).withIcon(R.mipmap.ic_about).withIdentifier(R.mipmap.ic_about)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                   @Override
                   public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                       switch (drawerItem.getIdentifier()) {
                           case R.mipmap.ic_home:
                               if (currentFragment instanceof DailyFragment) {
                                   return false;
                               }
                               currentFragment = new DailyFragment();
                               break;
                           case R.mipmap.ic_reading:
                               if (currentFragment instanceof BaseReadingFragment) {
                                   return false;
                               }
                               currentFragment = new BaseReadingFragment();
                               break;
                           case R.mipmap.ic_news:
                               if (currentFragment instanceof BaseNewsFragment) {
                                   return false;
                               }
                               currentFragment = new BaseNewsFragment();
                               break;
                           case R.mipmap.ic_science:
                               if (currentFragment instanceof BaseScienceFragment) {
                                   return false;
                               }
                               currentFragment = new BaseScienceFragment();
                               break;
                           case R.mipmap.ic_setting:
                               Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_SHORT).show();
                               break;
                           case R.mipmap.ic_about:
                               Toast.makeText(MainActivity.this, "about", Toast.LENGTH_SHORT).show();
                               break;
                           case R.mipmap.ic_shake:
                               Toast.makeText(MainActivity.this, "Shake Shakes", Toast.LENGTH_SHORT).show();
                               break;
                       }
                       switchFragment();
                       return false;
                   }
               })
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_daily, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_home:
                drawer.setSelection(R.mipmap.ic_home);
                currentFragment =  new DailyFragment();
                break;
            case R.id.menu_reading:
                drawer.setSelection(R.mipmap.ic_reading);
                currentFragment = new BaseReadingFragment();
                break;
            case R.id.menu_news:
                drawer.setSelection(R.mipmap.ic_news);
                currentFragment = new BaseNewsFragment();
                break;
            case R.id.menu_science:
                drawer.setSelection(R.mipmap.ic_science);
                currentFragment = new BaseScienceFragment();
                break;
            case R.id.menu_search:
                showSearchDialog();
                return true;
        }
        switchFragment();
        currentFragment = null;
        return super.onOptionsItemSelected(item);
    }
    private void showSearchDialog(){
        final EditText editText = new EditText(this);
        editText.setGravity(Gravity.CENTER);
        editText.setSingleLine();
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.text_search_books))
                .setIcon(R.mipmap.ic_search)
                .setView(editText)
                .setPositiveButton(getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(Utils.hasString(editText.getText().toString())){
                            Intent intent = new Intent(MainActivity.this,ReadingActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(getString(R.string.id_search_text),editText.getText().toString());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                }).show();
    }
}
