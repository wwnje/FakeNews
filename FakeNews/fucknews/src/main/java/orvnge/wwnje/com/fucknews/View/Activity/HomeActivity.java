package orvnge.wwnje.com.fucknews.view.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import orvnge.wwnje.com.fucknews.AppConstants;
import orvnge.wwnje.com.fucknews.LogUtil;
import orvnge.wwnje.com.fucknews.R;
import orvnge.wwnje.com.fucknews.SharedPreferencesUtil;
import orvnge.wwnje.com.fucknews.model.MyAPI;
import orvnge.wwnje.com.fucknews.view.Fragment.BlankFragment;

public class HomeActivity extends BaseActivity{

    private Snackbar snackbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navigationView;
    private Fragment currentFragment;
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initNavigationViewHeader();
        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            currentFragment = new BlankFragment();
            switchContent(currentFragment);
        } else {
            //activity销毁后记住销毁前所在页面，用于夜间模式切换
            currentIndex = savedInstanceState.getInt(AppConstants.CURRENT_INDEX);
            switch (this.currentIndex) {
                case 0:
                    currentFragment = new BlankFragment();
                    switchContent(currentFragment);
                    break;
                case 1:
                   /* currentFragment = new WorldlFragment();
                    switchContent(currentFragment);*/
                    break;
                case 2:
                    /*currentFragment = new LifeFragment();
                    switchContent(currentFragment);*/
                    break;
            }
        }
    }

    public void switchContent(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contentLayout, fragment).commit();
        invalidateOptionsMenu();
    }



    private void initNavigationViewHeader() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //设置头像，布局app:headerLayout="@layout/drawer_header"所指定的头布局
        View view = navigationView.inflateHeaderView(R.layout.activity_home_nav_header_home);

        //View mNavigationViewHeader = View.inflate(HomeActivity.this, R.layout.drawer_header, null);
        //navigationView.addHeaderView(mNavigationViewHeader);//此方法在魅族note 1，头像显示不全
        //菜单点击事件
        navigationView.setNavigationItemSelectedListener(new NavigationItemSelected());
    }


    class NavigationItemSelected implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(final MenuItem menuItem) {
            mDrawerLayout.closeDrawers();
            switch (menuItem.getItemId()) {
                case R.id.nav_me:
                    login_InitView();
                    new AlertDialog.Builder(HomeActivity.this).setTitle("hello 发现者")
                            .setView(View_Desc)
                            .setNegativeButton("cancel", null)
                            .setNeutralButton("注册", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //注册操作
                                    if (!edit_name.getText().toString().equals("") && !edit_password.getText().toString().equals("")) {
                                        register(edit_name.getText().toString(), edit_password.getText().toString());
                                    } else {
                                        Toast.makeText(HomeActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                                    }
                                    Toast.makeText(HomeActivity.this, "手机端现不支持", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setPositiveButton("登陆", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    menuItem.setTitle(edit_name.getText());
                                    //登陆操作
                                }
                            }).show();
                    break;

                case R.id.nav_about:
                    snackbar =
                            Snackbar.make(mDrawerLayout, "By wwnje", Snackbar.LENGTH_LONG)
                                    .setAction("提交Bug", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {//提交Bug
                                            try {
                                                Uri uri = Uri.parse(getString(R.string.mail_account));
                                                Intent intent = new Intent(Intent.ACTION_SENDTO, uri); //邮箱账号
                                                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject)); //主题
                                                intent.putExtra(Intent.EXTRA_TEXT,
                                                        getString(R.string.device_model) + Build.MODEL   //设备
                                                                + "\n" + getString(R.string.sdk_version)    //手机版本
                                                                + Build.VERSION.RELEASE + "\n");
                                                startActivity(intent);
                                            } catch (android.content.ActivityNotFoundException ex) {
                                                Snackbar.make(mDrawerLayout, R.string.no_mail_app, Snackbar.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                    snackbar.show();
                    break;

                case R.id.v_score: //评分
                    try {
                        Uri uri = Uri.parse("market://details?id=" + getPackageName());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Snackbar.make(mDrawerLayout, R.string.no_market_app, Snackbar.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.navigation_item_night:
                    SharedPreferencesUtil.setBoolean(mActivity, AppConstants.ISNIGHT, true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    recreate();
                    return true;
                case R.id.navigation_item_day:
                    SharedPreferencesUtil.setBoolean(mActivity, AppConstants.ISNIGHT, false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    recreate();
                    return true;
                /*case R.id.navigation_item_1:
                    currentIndex = 0;
                    menuItem.setChecked(true);
                    currentFragment = new FristFragment();
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_item_2:
                    currentIndex = 2;
                    menuItem.setChecked(true);
                    currentFragment = new ThirdFragment();
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_item_3:
                    currentIndex = 1;
                    menuItem.setChecked(true);
                    currentFragment = new SecondFragment();
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_item_night:
                    SharedPreferencesUtil.setBoolean(mActivity, AppConstants.ISNIGHT, true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    recreate();
                    return true;
                case R.id.navigation_item_day:
                    SharedPreferencesUtil.setBoolean(mActivity, AppConstants.ISNIGHT, false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    recreate();
                    return true;*/
                default:
                    return true;
            }
            return true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        LogUtil.d("onSaveInstanceState=" + currentIndex);
        outState.putInt(AppConstants.CURRENT_INDEX, currentIndex);
        super.onSaveInstanceState(outState);
    }

    public void initDrawer(Toolbar toolbar) {
        if (toolbar != null) {
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }
            };
            mDrawerToggle.syncState();
            mDrawerLayout.addDrawerListener(mDrawerToggle);
        }
    }

    //注册方法
    private void register(final String name, final String password) {
        //判断注册信息是否正确

        // Instantiate the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = MyAPI.Register_Url;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put("username", name);
                map.put("password", password);
                return map;
            }
        };
        //把StringRequest对象加到请求队列里来
        requestQueue.add(stringRequest);
        Toast.makeText(HomeActivity.this, "注册操作" + "name:" + name + " pwd:" + password, Toast.LENGTH_SHORT).show();
    }

    Response.Listener listener = new Response.Listener() {
        @Override
        public void onResponse(Object response) {
            show_if_success.setText(response.toString());
        }
    };


    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            show_if_success.setText("error");
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);//声明
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_copyright:
                new AlertDialog.Builder(this).setTitle("标题")
                        .setMessage(R.string.copyright_content)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                break;
            case R.id.action_add_news:
                startActivity(new Intent(HomeActivity.this, AddNewsActivity.class));
                break;
            default:
                //对没有处理的事件，交给父类来处理
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    View View_Desc;
    EditText edit_name;
    EditText edit_password;
    TextView show_if_success;
    private void login_InitView() {
        //login界面数据
        View_Desc = getLayoutInflater().inflate(R.layout.dialog_login, null);
        edit_name = (EditText) View_Desc.findViewById(R.id.login_name_dialog_edit);
        edit_password = (EditText) View_Desc.findViewById(R.id.login_pwd_dialog_edit);
        show_if_success = (TextView) View_Desc.findViewById(R.id.login_show_dialog);
    }
}
