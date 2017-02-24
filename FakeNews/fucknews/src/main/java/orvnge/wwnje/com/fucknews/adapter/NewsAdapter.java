package orvnge.wwnje.com.fucknews.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import orvnge.wwnje.com.fucknews.R;
import orvnge.wwnje.com.fucknews.bean.NewsBean;
import orvnge.wwnje.com.fucknews.utils.MyUtils;
import orvnge.wwnje.com.fucknews.view.Activity.BrowseActivity;
import orvnge.wwnje.com.fucknews.view.Activity.ZhiHuActivity;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<NewsBean> newsBeen;
    private Context context;

    private int pressed = R.drawable.btn_bookmark_style_pressed;
    private int unpressed = R.drawable.btn_bookmark_style_unpressed;
    private int index = unpressed;

    private int bookmarkText = R.string.bookmark;

    public NewsAdapter(Context context){
        newsBeen = new ArrayList<>();
        //inflater = LayoutInflater.from(MyApplication.getContext());
        this.context = context;
    }

    public void add(NewsBean news) {
        newsBeen.add(news);
        notifyItemInserted(newsBeen.size() - 1);
    }

    /**
     * 首次加入
     * @param news
     */
    public void addAll(List<NewsBean> news) {
        newsBeen.clear();
        newsBeen.addAll(news);
    }

    /**
     * 加载更多
     * @param news
     */
    public void addMore(List<NewsBean> news){
        newsBeen.addAll(news);
    }

    public void clear() {
        newsBeen.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Glide
                .with(context)
                .load(newsBeen.get(position).getPic_url())
                .placeholder(R.drawable.img_loading)
                .error(R.drawable.ic_error)
                .centerCrop()
                .into(holder.ivPic);

        //init others
        holder.tvType.setText(newsBeen.get(position).getType());
        holder.tvTitle.setText(newsBeen.get(position).getTitle());
        holder.tvDesc.setText(newsBeen.get(position).getDesc());
        holder.tvTime.setText(newsBeen.get(position).getTime());
        if(newsBeen.get(position).getFinder().toString().equals("null")){
            holder.tvName.setText("admin");
        }else {
            holder.tvName.setText(newsBeen.get(position).getFinder());
        }

        //书签按钮事件加入数据库
        holder.btn_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == unpressed){
                    index = pressed;
                    bookmarkText = R.string.bookmarked;
                }else if(index == pressed){
                    index = unpressed;
                    bookmarkText = R.string.bookmark;
                }
                holder.btn_bookmark.setBackgroundResource(index);
                holder.btn_bookmark.setText(bookmarkText);
            }
        });

        //set card view
        // 进入内容浏览器事件
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DetailActivity.startActivity(context, getLayoutPosition(), showImage);

                if(MyUtils.WhichUrl(newsBeen.get(position).getContent_url()) != null){
                    Log.d(TAG, "onClick: " + MyUtils.WhichUrl(newsBeen.get(position).getContent_url()));
                    Intent intent = new Intent(context, ZhiHuActivity.class);
                    intent.putExtra("img", newsBeen.get(position).getPic_url());//参数给下一个activity
                    intent.putExtra("title", newsBeen.get(position).getTitle());//参数给下一个activity
                    intent.putExtra("slug", MyUtils.WhichUrl(newsBeen.get(position).getContent_url()));//参数给下一个activity

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context, BrowseActivity.class);
                    intent.putExtra("content_url", newsBeen.get(position).getContent_url());//参数给下一个activity
                    intent.putExtra("img", newsBeen.get(position).getPic_url());//参数给下一个activity
                    intent.putExtra("title", newsBeen.get(position).getTitle());//参数给下一个activity
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return newsBeen.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPic;

        //NetworkImageView ivPic;
        TextView tvName;
        TextView tvTitle;
        TextView tvType;
        TextView tvDesc;
        TextView tvTime;
        Button btn_bookmark;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvType = (TextView) itemView.findViewById(R.id.tv_type);
            ivPic = (ImageView) itemView.findViewById(R.id.ivPic);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            btn_bookmark = (Button) itemView.findViewById(R.id.btn_bookmark);
            cardView = (CardView) itemView.findViewById(R.id.fragment_movie_item);
        }
    }
}
