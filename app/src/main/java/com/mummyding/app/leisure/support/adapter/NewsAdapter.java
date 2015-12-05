package com.mummyding.app.leisure.support.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.database.cache.ICache;
import com.mummyding.app.leisure.database.table.NewsTable;
import com.mummyding.app.leisure.model.news.NewsBean;
import com.mummyding.app.leisure.ui.support.WebViewUrlActivity;

/**
 * Created by mummyding on 15-11-14.
 */
public class NewsAdapter extends BaseListAdapter<NewsBean,NewsAdapter.ViewHolder> {


    public NewsAdapter(Context context, ICache<NewsBean> cache) {
        super(context, cache);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.item_news,null);
        final ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewUrlActivity.class);
                intent.putExtra("url",getItem(vh.position).getLink());
                mContext.startActivity(intent);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final NewsBean newsBean = getItem(position);
        holder.description.setText(newsBean.getDescription());
        holder.title.setText(newsBean.getTitle());
        holder.date.setText(newsBean.getPubTime());
        holder.position = position;
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewUrlActivity.class);
                intent.putExtra("url", getItem(position).getLink());
                mContext.startActivity(intent);
            }
        });

        if(isCollection){
            holder.collect_cb.setVisibility(View.GONE);
            holder.text.setVisibility(View.VISIBLE);
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(holder.parentView, R.string.notify_remove_from_collection, Snackbar.LENGTH_SHORT).
                            setAction(mContext.getString(R.string.text_ok), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (mItems.contains(newsBean) == false) {
                                        return;
                                    }
                                    mCache.execSQL(NewsTable.updateCollectionFlag(newsBean.getTitle(), 0));
                                    mCache.execSQL(NewsTable.deleteCollectionFlag(newsBean.getTitle()));
                                    mItems.remove(position);
                                    notifyDataSetChanged();
                                }
                            })
                            .show();
                }
            });
            return;
        }

        holder.collect_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                newsBean.setIs_collected(isChecked ? 1 : 0);
                mCache.execSQL(NewsTable.updateCollectionFlag(newsBean.getTitle(), isChecked ? 1 : 0));
                if (isChecked) {
                    mCache.addToCollection(newsBean);
                } else {
                    mCache.execSQL(NewsTable.deleteCollectionFlag(newsBean.getTitle()));
                }
            }
        });
        holder.collect_cb.setChecked(newsBean.getIs_collected() == 1 ? true:false);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View parentView;
        private TextView title;
        private TextView description;
        private TextView date;
        private CheckBox collect_cb;
        private TextView text;
        private int position;
         ViewHolder(View itemView) {
             super(itemView);
             parentView = itemView;
             title = (TextView) itemView.findViewById(R.id.title);
             description = (TextView) itemView.findViewById(R.id.description);
             date = (TextView) itemView.findViewById(R.id.date);
             collect_cb = (CheckBox) itemView.findViewById(R.id.collect_cb);
             text = (TextView) itemView.findViewById(R.id.text);
         }
    }

}
