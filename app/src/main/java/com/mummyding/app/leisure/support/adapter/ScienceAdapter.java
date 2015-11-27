package com.mummyding.app.leisure.support.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.cache.cache.DailyCache;
import com.mummyding.app.leisure.cache.cache.ScienceCache;
import com.mummyding.app.leisure.cache.table.ScienceTable;
import com.mummyding.app.leisure.model.science.ArticleBean;
import com.mummyding.app.leisure.ui.support.WebViewUrlActivity;

import java.util.List;

/**
 * Created by mummyding on 15-11-18.
 */
public class ScienceAdapter extends RecyclerView.Adapter<ScienceAdapter.ViewHolder>{

    private Context mContext;
    private List<ArticleBean> items;
    private ScienceCache cache ;
    public ScienceAdapter(Context mContext, List<ArticleBean> items) {
        this.mContext = mContext;
        this.items = items;
        cache = new ScienceCache(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_science, null);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ArticleBean articleBean = getItem(position);
        holder.title.setText(articleBean.getTitle());
        holder.image.setImageURI(Uri.parse(articleBean.getImage_info().getUrl()));
        holder.description.setText(articleBean.getSummary());
        holder.info.setText(articleBean.toString());
        holder.comment.setText(String.valueOf(articleBean.getReplies_count()));
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewUrlActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(mContext.getString(R.string.id_url), articleBean.getUrl());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        holder.collect_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                articleBean.setIs_collected(isChecked ? 1:0);
                cache.execSQL(ScienceTable.updateCollectionFlag(articleBean.getTitle(),isChecked ? 1:0));
                if(isChecked){
                    cache.addToCollection(articleBean);
                }else{
                    cache.execSQL(ScienceTable.deleteCollectionFlag(articleBean.getTitle()));
                }
            }
        });
        holder.collect_cb.setChecked(articleBean.getIs_collected()==1 ? true:false);
    }

    private ArticleBean getItem(int position){
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private View parentView;
        private TextView title;
        private TextView description;
        private TextView info;
        private TextView comment;
        private SimpleDraweeView image;
        private CheckBox collect_cb;
        public ViewHolder(View itemView) {
            super(itemView);
            parentView = itemView;
            title = (TextView) parentView.findViewById(R.id.title);
            description = (TextView) parentView.findViewById(R.id.description);
            info = (TextView) parentView.findViewById(R.id.info);
            image = (SimpleDraweeView) parentView.findViewById(R.id.image);
            comment = (TextView) parentView.findViewById(R.id.comment);
            collect_cb = (CheckBox) parentView.findViewById(R.id.collect_cb);
        }
    }
}
