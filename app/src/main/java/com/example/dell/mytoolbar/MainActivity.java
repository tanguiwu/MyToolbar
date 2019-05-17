package com.example.dell.mytoolbar;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbarTitle, toolbarRight;
    private ImageView toolbarBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        toolbar = findViewById(R.id.toolbar);
        toolbarBack = findViewById(R.id.toolbar_title_back);
        toolbarTitle = findViewById(R.id.toolbar_title_name);
        toolbarRight = findViewById(R.id.toolbar_title_rightTv);

        toolbarTitle.setText("自定义toolbar");
        setSupportActionBar(toolbar);

    }

    //获取布局，子项进行设置
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_ending).setTitle("完成");
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /*利用反射机制调用MenuBuilder的setOptionalIconsVisible方法设置mOptionalIconsVisible为true，
         * 给菜单设置图标时才可见
         */
        setIconEnable(menu, true);

        getMenuInflater().inflate(R.menu.item_menu, menu);
        final MenuItem item1 = menu.findItem(R.id.menu_ending);
        MenuItem item2 = menu.findItem(R.id.menu_ending_1);
        MenuItem item3 = menu.findItem(R.id.menu_ending_2);
        MenuItem item4 = menu.findItem(R.id.menu_ending_3);
        menu.add(Menu.NONE, Menu.FIRST + 1, 0, "菜单1").setIcon(R.drawable.img);


        item1.setIcon(R.mipmap.ic_launcher);

        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(MainActivity.this, "我是" + item1.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return true;
    }

    //enable为true时，菜单添加图标有效，enable为false时无效。4.0系统默认无效
    private void setIconEnable(Menu menu, boolean enable) {
        try {
            Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            m.setAccessible(true);

            //MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特征)
            m.invoke(menu, enable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
