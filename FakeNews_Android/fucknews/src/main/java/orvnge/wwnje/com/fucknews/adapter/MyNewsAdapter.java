package orvnge.wwnje.com.fucknews.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import orvnge.wwnje.com.fucknews.R;
import orvnge.wwnje.com.fucknews.bean.BookMarkBean;
import orvnge.wwnje.com.fucknews.bean.NewsBean;
import orvnge.wwnje.com.fucknews.view.Activity.BrowseActivity;

/**
 * Created by wwnje on 2017/2/19.
 * Like && BookMark
 */

public class MyNewsAdapter extends RecyclerView.Adapter<MyNewsAdapter.ViewHolder> {

    private static final String TAG = "BookMarkAdapter";

    private List<NewsBean> newsBeenList;
    private Context context;

    public MyNewsAdapter(Context context) {
        newsBeenList = new ArrayList<>();
        this.context = context;
    }

    //定义一个监听对象，用来存储监听事件
    public MyNewsAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(MyNewsAdapter.OnItemClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    //定义OnItemClickListener的接口,便于在实例化的时候实现它的点击效果
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    /**
     * 添加
     */

    public void addAll(List<NewsBean> bookmarks){
        newsBeenList.clear();
        newsBeenList.addAll(bookmarks);
    }

    public void add(NewsBean bookmark) {
        newsBeenList.add(bookmark);

        notifyItemInserted(getItemCount() - 1);
    }

    public void clear() {
        newsBeenList.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO 只出现第一条数据 改为null
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tags, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.tags_name.setText(newsBeenList.get(position).getNews_id() +
                newsBeenList.get(position).getTitle()
        );

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(context, BrowseActivity.class);
                    intent.putExtra("content_url", newsBeenList.get(position).getContent_url());//参数给下一个activity
                    intent.putExtra("img", newsBeenList.get(position).getPic_url());//参数给下一个activity
                    intent.putExtra("title", newsBeenList.get(position).getTitle());//参数给下一个activity
                    intent.putExtra("news_id", newsBeenList.get(position).getNews_id());//参数给下一个activity
                    intent.putExtra("position", position);//参数给下一个activity
                    intent.putExtra(" bool_show_like", false);
                    //intent.putExtra("frag_id", frag_id);//参数给下一个activity
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsBeenList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tags_name;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tags_name = (TextView) itemView.findViewById(R.id.item_tags_name);
            cardView = (CardView) itemView.findViewById(R.id.item_tags_cardView);

            cardView.setOnClickListener(this);
        }

        //通过接口回调来实现RecyclerView的点击事件
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                //此处调用的是onItemClick方法，而这个方法是会在RecyclerAdapter被实例化的时候实现
                mOnItemClickListener.onItemClick(v, getItemCount());
            }
        }
    }
}
