package com.mummyding.app.leisure.support.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.model.news.NewsBean;
import com.mummyding.app.leisure.ui.news.NewsDetailsActivity;

import java.util.List;

/**
 * Created by mummyding on 15-11-14.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<NewsBean> items;
    private Context mContext ;

    public NewsAdapter(Context context,List<NewsBean> items) {
        this.items = items;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.item_news,null);
        final ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewsDetailsActivity.class);
                intent.putExtra("url",getItem(vh.position).getLink());
                mContext.startActivity(intent);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsBean newsBean = getItem(position);
        holder.description.setText(newsBean.getDescription());
        holder.title.setText(newsBean.getTitle());
        holder.date.setText(newsBean.getPubTime());
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    private NewsBean getItem(int position){
        return items.get(position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView description;
        TextView date;
        int position;
         ViewHolder(View itemView) {
            super(itemView);
             title = (TextView) itemView.findViewById(R.id.title);
             description = (TextView) itemView.findViewById(R.id.description);
             date = (TextView) itemView.findViewById(R.id.date);
         }
    }

}
