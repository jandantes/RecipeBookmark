package com.paglubogngaraw.recipebookmark;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jan.dantes on 9/26/13.
 */

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    CollectionPagerAdapter mCollectionPagerAdapter;
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Fragment> fragments = getFragments();

        mCollectionPagerAdapter = new CollectionPagerAdapter(getSupportFragmentManager(),fragments);

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayHomeAsUpEnabled(false);

        mViewPager = (ViewPager) findViewById(R.id.pager_main);
        mViewPager.setAdapter(mCollectionPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mCollectionPagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab()
                    .setText(mCollectionPagerAdapter.getPageTitle(i))
                    .setTabListener(this));
        }
    }

    public class CollectionPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        final int NUM_ITEMS = 3; // number of tabs
        public CollectionPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public  Fragment getItem(int i){
            return this.fragments.get(i);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String tabLabel = null;
            switch (position) {
                case 0:
                    tabLabel = getString(R.string.title_activity_recipe_list);
                    break;
                case 1:
                    tabLabel = getString(R.string.title_activity_courses);
                    break;
                case 2:
                    tabLabel = getString(R.string.title_activity_ingredients);
                    break;
            }
            return tabLabel;
        }
    }

    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<Fragment>();
        fList.add(new RecipeList());
        fList.add(new CoursesList());
        fList.add(new IngredientsList());
        return fList;
    }

    //tab states
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction){
    }
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }
    public void onTabReselected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction) {
    }

    //actionbar menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_list, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_addRecipe:
                Intent loadAddRecipe = new Intent(MainActivity.this, AddRecipe.class);
                MainActivity.this.startActivity(loadAddRecipe);
                break;
            case R.id.action_settings:
                Intent loadSettings = new Intent(MainActivity.this, Settings.class);
                MainActivity.this.startActivity(loadSettings);
                break;
            default:
                break;
        }
        return true;
    }
}
