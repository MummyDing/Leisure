/*
 *
 *  * Copyright (C) 2015 MummyDing
 *  *
 *  * This file is part of Leisure( <https://github.com/MummyDing/Leisure> )
 *  *
 *  * Leisure is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * Leisure is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with Leisure.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.mummyding.app.leisure.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.ui.about.AboutActivity;
import com.mummyding.app.leisure.ui.collection.BaseCollectionFragment;
import com.mummyding.app.leisure.ui.daily.DailyFragment;
import com.mummyding.app.leisure.ui.news.BaseNewsFragment;
import com.mummyding.app.leisure.ui.reading.BaseReadingFragment;
import com.mummyding.app.leisure.ui.reading.ReadingActivity;
import com.mummyding.app.leisure.ui.science.BaseScienceFragment;

import java.util.List;



public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Drawer drawer ;
    private AccountHeader header;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;
    private  Fragment currentFragment ;
    private Menu menu;
    List<Fragment> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
/*
        list = new ArrayList<>();
        list.add(new DailyFragment());
        list.add(new BaseReadingFragment());
        list.add(new BaseNewsFragment());
        list.add(new BaseScienceFragment());
        list.add(new BaseCollectionFragment());*/


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
        }else if(currentFragment instanceof BaseCollectionFragment){
            switchFragment(currentFragment,getString(R.string.collection),R.menu.menu_daily);
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
                .addProfiles(new ProfileDrawerItem().withIcon(R.drawable.logo)
                        .withEmail(getString(R.string.author_email))
                        .withName(getString(R.string.author_name)))
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        Intent i = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(i);
                        return false;
                    }
                })
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
                        new SectionDrawerItem().withName(R.string.app_name),
                        new SecondaryDrawerItem().withName(R.string.collection).withIcon(R.mipmap.ic_collect).withIdentifier(R.mipmap.ic_collect),
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
                           case R.mipmap.ic_collect:
                               if (currentFragment instanceof BaseCollectionFragment) {
                                   return false;
                               }
                               currentFragment = new BaseCollectionFragment();
                               break;
                           case R.mipmap.ic_about:
                               Intent i = new Intent(MainActivity.this, AboutActivity.class);
                               startActivity(i);
                               return false;
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
                currentFragment = new DailyFragment();
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
