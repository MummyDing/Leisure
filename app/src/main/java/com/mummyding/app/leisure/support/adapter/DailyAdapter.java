/*
 *  Copyright (C) 2015 MummyDing
 *
 *  This file is part of Leisure( <https://github.com/MummyDing/Leisure> )
 *
 *  Leisure is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *                             ｀
 *  Leisure is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Leisure.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mummyding.app.leisure.support.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.api.DailyApi;
import com.mummyding.app.leisure.database.cache.ICache;
import com.mummyding.app.leisure.database.table.DailyTable;
import com.mummyding.app.leisure.model.daily.StoryBean;
import com.mummyding.app.leisure.support.HttpUtil;
import com.mummyding.app.leisure.support.Settings;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.ui.daily.DailyDetailsActivity;
import com.mummyding.app.leisure.ui.support.WebViewLocalActivity;

import com.mummyding.app.leisure.support.adapter.DailyAdapter.ViewHolder;
import com.mummyding.app.leisure.ui.support.WebViewUrlActivity;


/**
 * Created by mummyding on 15-11-21.<br>
 * Daily RecyclerView Adapter. It can provide different view according to Cache Type.<br>
 * @author MummyDing
 * @version Leisure 1.0
 */
public class DailyAdapter extends BaseListAdapter<StoryBean,ViewHolder>{


    public DailyAdapter(Context context, ICache<StoryBean> cache) {
        super(context, cache);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_daily, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final StoryBean storyBean = getItem(position);
        holder.title.setText(storyBean.getTitle());

        if(Settings.noPicMode && HttpUtil.isWIFI == false){
            holder.image.setImageURI(null);
        }else {
            holder.image.setImageURI(Uri.parse(storyBean.getImages()[0]));
        }
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DailyDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(mContext.getString(R.string.id_url), DailyApi.daily_details_url+storyBean.getId());
                bundle.putString(mContext.getString(R.string.id_title),storyBean.getTitle());
                bundle.putString(mContext.getString(R.string.id_body),storyBean.getBody());
                bundle.putString(mContext.getString(R.string.id_imageurl),storyBean.getLargepic());
                bundle.putInt(mContext.getString(R.string.id_id),storyBean.getId());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

/*
        if(isCollection){
            holder.collect_cb.setVisibility(View.GONE);
            holder.text.setText(R.string.text_remove);
            holder.text.setTextSize(20);
            holder.text.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(holder.parentView, R.string.notify_remove_from_collection,Snackbar.LENGTH_SHORT).
                            setAction(mContext.getString(R.string.text_ok), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (mItems.contains(storyBean) == false){
                                        return;
                                    }
                                    mCache.execSQL(DailyTable.updateCollectionFlag(storyBean.getId(), 0));
                                    mCache.execSQL(DailyTable.deleteCollectionFlag(storyBean.getId()));
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
                storyBean.setCollected(isChecked ? 1:0);
                mCache.execSQL(DailyTable.updateCollectionFlag(storyBean.getId(), isChecked ? 1 : 0));
                if(isChecked){
                    mCache.addToCollection(storyBean);
                }else{
                    mCache.execSQL(DailyTable.deleteCollectionFlag(storyBean.getId()));
                }
            }
        });
        holder.collect_cb.setChecked(storyBean.isCollected() == 1 ? true:false);*/
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private View parentView;
        private TextView title;
        private SimpleDraweeView image;
       // private TextView info;
        //private CheckBox collect_cb;
        //private TextView text;
        public ViewHolder(View itemView) {
            super(itemView);
            parentView = itemView;
            title = (TextView) parentView.findViewById(R.id.title);
            image = (SimpleDraweeView) parentView.findViewById(R.id.image);
            //info = (TextView) parentView.findViewById(R.id.info);
          /*  collect_cb = (CheckBox) parentView.findViewById(R.id.collect_cb);
            if(isCollection) {
                text = (TextView) parentView.findViewById(R.id.text);
            }*/
        }
    }
}
