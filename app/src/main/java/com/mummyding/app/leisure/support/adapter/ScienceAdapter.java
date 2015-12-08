/*
 *
 *  * Copyright (C) 2015 MummyDing
 *  *
 *  * This file is part of Leisure( <https://github.com/MummyDing/Leisure> )
 *  *
 *  * Leisure is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * Leisure is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with Leisure.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.mummyding.app.leisure.support.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.database.cache.ICache;
import com.mummyding.app.leisure.database.table.ScienceTable;
import com.mummyding.app.leisure.model.science.ArticleBean;
import com.mummyding.app.leisure.support.HttpUtil;
import com.mummyding.app.leisure.ui.support.WebViewUrlActivity;
import com.mummyding.app.leisure.support.adapter.ScienceAdapter.ViewHolder;



/**
 * Created by mummyding on 2015-11-18.<br>
 * Science RecyclerView Adapter. It can provide different view according to Cache Type.<br>
 * @author MummyDing
 * @version Leisure 1.0
 */
public class ScienceAdapter extends BaseListAdapter<ArticleBean,ViewHolder>{


    public ScienceAdapter(Context context, ICache<ArticleBean> cache) {
        super(context, cache);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_science, null);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ArticleBean articleBean = getItem(position);
        holder.title.setText(articleBean.getTitle());

        if(isNoPicMode && HttpUtil.isWIFI == false){
            holder.image.setImageURI(null);
        }else {
            holder.image.setImageURI(Uri.parse(articleBean.getImage_info().getUrl()));
        }

        holder.description.setText(articleBean.getSummary());
        holder.info.setText(articleBean.getInfo());
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
                                    if(mItems.contains(articleBean) == false){
                                        return;
                                    }
                                    mCache.execSQL(ScienceTable.updateCollectionFlag(articleBean.getTitle(), 0));
                                    mCache.execSQL(ScienceTable.deleteCollectionFlag(articleBean.getTitle()));
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
                articleBean.setIs_collected(isChecked ? 1:0);
                mCache.execSQL(ScienceTable.updateCollectionFlag(articleBean.getTitle(),isChecked ? 1:0));
                if(isChecked){
                    mCache.addToCollection(articleBean);
                }else{
                    mCache.execSQL(ScienceTable.deleteCollectionFlag(articleBean.getTitle()));
                }
            }
        });
        holder.collect_cb.setChecked(articleBean.getIs_collected()==1 ? true:false);
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private View parentView;
        private TextView title;
        private TextView description;
        private TextView info;
        private TextView comment;
        private SimpleDraweeView image;
        private CheckBox collect_cb;
        private TextView text;
        public ViewHolder(View itemView) {
            super(itemView);
            parentView = itemView;
            title = (TextView) parentView.findViewById(R.id.title);
            description = (TextView) parentView.findViewById(R.id.description);
            info = (TextView) parentView.findViewById(R.id.info);
            image = (SimpleDraweeView) parentView.findViewById(R.id.image);
            comment = (TextView) parentView.findViewById(R.id.comment);
            collect_cb = (CheckBox) parentView.findViewById(R.id.collect_cb);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
