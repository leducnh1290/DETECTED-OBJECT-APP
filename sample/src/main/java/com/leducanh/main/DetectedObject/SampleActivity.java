package com.leducanh.main.DetectedObject;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.leducanh.main.DataBase.DataBase;
import com.leducanh.main.DetectedObject.fragment.CenteredTextFragment;
import com.leducanh.main.DetectedObject.fragmentmenu.Main;
import com.leducanh.main.DetectedObject.menu.DrawerAdapter;
import com.leducanh.main.DetectedObject.menu.SpaceItem;
import com.leducanh.main.Notice.ServicePushNoti;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.leducanh.main.DetectedObject.R;
import com.leducanh.main.DetectedObject.menu.DrawerItem;
import com.leducanh.main.DetectedObject.menu.SimpleItem;

import java.util.Arrays;

/**
 * Created by yarolegovich on 25.03.2017.
 */

public class SampleActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {
FirebaseDatabase fb =  FirebaseDatabase.getInstance();
    private static final int POS_MAIN = 0;
    private static final int POS_HISTORY = 1;
    private static final int POS_INFO = 2;
    private static final int POS_LOGOUT = 4;

    private String[] screenTitles;
    private Drawable[] screenIcons;
    Fragment selectedScreen;
    Toolbar toolbar;
    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
initDB(fb,getString(R.string.data));
        if(ServicePushNoti.a!=1)
            startservice();
       toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_MAIN).setChecked(true),
                createItemFor(POS_HISTORY),
                createItemFor(POS_INFO),
                new SpaceItem(48),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_MAIN);
    }

    private void initDB(FirebaseDatabase fb, String index_database_data) {
        new DataBase(fb,index_database_data);
    }

    @Override
    public void onItemSelected(int position) {
        if (position==POS_LOGOUT)
            finishAffinity();
        toolbar.setTitle(screenTitles[position]);
        switch (position){
            case POS_MAIN:
                selectedScreen = new Main(DataBase.myRef);
                break;
            case POS_HISTORY:
                 selectedScreen =CenteredTextFragment.createFor(screenTitles[position]);
                break;
            case POS_INFO:
                selectedScreen =CenteredTextFragment.createFor(screenTitles[position]);
                break;
        }
        slidingRootNav.closeMenu();
        showFragment(selectedScreen);
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }
    private void startservice() {
        Intent intent = new Intent(this, ServicePushNoti.class);
        startService(intent);
    }
}
