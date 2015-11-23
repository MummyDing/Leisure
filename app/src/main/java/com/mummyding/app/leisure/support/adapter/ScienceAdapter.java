package com.mummyding.app.leisure.support.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.model.science.ArticleBean;
import com.mummyding.app.leisure.ui.support.WebViewUrlActivity;

import java.util.List;

/**
 * Created by mummyding on 15-11-18.
 */
public class ScienceAdapter extends RecyclerView.Adapter<ScienceAdapter.ViewHolder>{

    private Context mContext;
    private List<ArticleBean> items;

    public ScienceAdapter(Context mContext, List<ArticleBean> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext,R.layout.item_science,null);
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
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewUrlActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(mContext.getString(R.string.id_url),articleBean.getUrl());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
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
        private SimpleDraweeView image;

        public ViewHolder(View itemView) {
            super(itemView);
            parentView = itemView;
            title = (TextView) parentView.findViewById(R.id.title);
            description = (TextView) parentView.findViewById(R.id.description);
            info = (TextView) parentView.findViewById(R.id.info);
            image = (SimpleDraweeView) parentView.findViewById(R.id.image);
        }
    }
}
