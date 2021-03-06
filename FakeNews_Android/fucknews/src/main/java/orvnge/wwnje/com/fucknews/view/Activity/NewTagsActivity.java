
package orvnge.wwnje.com.fucknews.view.Activity;

        import android.content.Context;
        import android.content.DialogInterface;
        import android.database.sqlite.SQLiteDatabase;
        import android.graphics.Color;
        import android.os.AsyncTask;
        import android.os.Handler;
        import android.support.v4.widget.SwipeRefreshLayout;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.View;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.AuthFailureError;
        import com.android.volley.Request;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import butterknife.Bind;
        import butterknife.ButterKnife;
        import orvnge.wwnje.com.fucknews.R;
        import orvnge.wwnje.com.fucknews.adapter.BlankItemsBaseAdapter;
        import orvnge.wwnje.com.fucknews.bean.BlankBaseItemsBean;
        import orvnge.wwnje.com.fucknews.utils.BlankAPI;
        import orvnge.wwnje.com.fucknews.utils.DatabaseHelper;
        import orvnge.wwnje.com.fucknews.utils.BlankNetMehod;
        import orvnge.wwnje.com.fucknews.utils.MyApplication;
        import orvnge.wwnje.com.fucknews.view.Fragment.ContentFragment;

/**
 * 新建tags时需要选择的type类型
 */

public class NewTagsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, BlankItemsBaseAdapter.OnItemClickListener, BlankItemsBaseAdapter.OnLongItemClickListener {

    private static final String TAG = "NewTagsActivity";
    private DatabaseHelper dbHelper;

    private Context context;

    @Bind(R.id.itemsbase_recycleview)
    RecyclerView recycleView;
    @Bind(R.id.itemsbase_swip)
    SwipeRefreshLayout swip;

    private BlankItemsBaseAdapter blankItemsBaseAdapter;
    private List<BlankBaseItemsBean> items = new ArrayList<>();

    private RecyclerView.LayoutManager layoutManager;

    private int page = 1;

    // 标志位，标志已经初始化完成，因为setUserVisibleHint是在onCreateView之前调用的，在视图未初始化的时候，在lazyLoad当中就使用的话，就会有空指针的异常
    private boolean isRefresh;
    //标志当前页面是否可见

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemsbase);
        setTitle("选择新闻类别");

        //数据库名字，版本号
        dbHelper = new DatabaseHelper(this, DatabaseHelper.DATABASE_LOCAL_MESSAGE, null, DatabaseHelper.DATABASE_LOCAL_MESSAGE_VERSION);

        context = NewTagsActivity.this;

        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        layoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);

        recycleView.setHasFixedSize(true);

        blankItemsBaseAdapter = new BlankItemsBaseAdapter(getApplicationContext(), BlankItemsBaseAdapter.ItemType.NEWS_TYPE_CHOICE);
        recycleView.setAdapter(blankItemsBaseAdapter);


        blankItemsBaseAdapter.setOnItemClickListener(this);
        blankItemsBaseAdapter.setOnLongItemClickListener(this);

        swip.setOnRefreshListener(this);

        //进入就刷新
        swip.post(new Runnable() {
            @Override
            public void run() {
                swip.setRefreshing(true);
                new LoadAllAppsTask().execute("Test AsyncTask");
            }
        });
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        page = 1;
        new LoadAllAppsTask().execute("Test AsyncTask");
    }

    /**
     * 点击进行选择type类型
     */
    @Override
    public void onItemClick(View view, int position) {

        int type_id = blankItemsBaseAdapter.blankBaseItemsBeanList.get(position).getItem_id();

        TextView tv = (TextView) view.findViewById(R.id.item_tags_name);

        //网络请求订阅
        if(BlankNetMehod.ADD_TAGS(getApplicationContext(), String.valueOf(type_id))){
            tv.setTextColor(Color.RED);

            new AlertDialog.Builder(this).setTitle("if Publish Now")
                    .setNegativeButton("cancel", null)
                    .setNeutralButton("PUBLISH", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BlankNetMehod.Share_NEWS(context);
                            finish();
                        }
                    }).show();
        }
    }

    /**
     * 长按
     */
    @Override
    public void onLongItemClick(View view, int position) {
        TextView tvRecycleViewItemText = (TextView) view.findViewById(R.id.item_tags_name);
        //如果字体本来是黑色就变成红色，反之就变为黑色
        if (tvRecycleViewItemText.getCurrentTextColor() == Color.BLACK) {
            tvRecycleViewItemText.setTextColor(Color.RED);
            //订阅
        } else {
            tvRecycleViewItemText.setTextColor(Color.BLACK);
        }
    }

    /**
     * 标签请求数据
     */

    private class LoadAllAppsTask extends AsyncTask<String, Integer, Long> {

        /**
         * 后台处理 请求
         *
         * @param params
         * @return
         */
        @Override
        protected Long doInBackground(String... params) {
            getNewsType(0, 20);//请求
            return 100L;
        }
    }

    //Volley请求
    /*
    * POST
    * offset起始点
    * limit最多条数
     */
    public void getNewsType(int offset, int limit) {//传递进来
        Map<String, String> params = new HashMap<String, String>();
        params.put("limit", String.valueOf(limit));
        params.put("offset", String.valueOf(offset));

        //TODO type_version
        params.put("type_version", String.valueOf(0));

        JSONObject paramJsonObject = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                BlankAPI.GET_TYPE_URL,
                paramJsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: " + response.toString());
                        try {
                            JSONArray array = response.getJSONArray("type");
                            for (int j = 0; j < array.length(); j++) {
                                add(array.getJSONObject(j));
                            }

                            //请求完成
                            blankItemsBaseAdapter.addAll(items);
                            items = new ArrayList<>();//清除

                            //处理完毕进行设置
                            swip.setRefreshing(false);
                            blankItemsBaseAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swip.setRefreshing(false);
                Toast.makeText(getApplicationContext(), "刷新出错", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
//        Toast.makeText(getApplicationContext(), "getRequestQueue：" + isRefresh, Toast.LENGTH_SHORT).show();
        MyApplication.getRequestQueue().add(jsonObjectRequest);
    }

    /**
     * Volley获取加入
     */
    public void add(JSONObject jsonObject) {
        try {
            String tags_name = jsonObject.getString("type_name");
            String tags_id = jsonObject.getString("type_id");

            //先通用吧
            BlankBaseItemsBean data = new BlankBaseItemsBean();
            data.setItem_name(tags_name);
            data.setItem_id(Integer.parseInt(tags_id));
            items.add(data);

            //blankItemsBaseAdapter.add(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}


