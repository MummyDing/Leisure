package com.mummyding.app.leisure.support.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import com.mummyding.app.leisure.LeisureApplication;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.cache.cache.ICache;
import com.mummyding.app.leisure.cache.cache.ReadingCache;
import com.mummyding.app.leisure.cache.table.DailyTable;
import com.mummyding.app.leisure.cache.table.ReadingTable;
import com.mummyding.app.leisure.model.reading.BookBean;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.ui.reading.ReadingDetailsActivity;

import com.mummyding.app.leisure.support.adapter.ReadingAdapter.ViewHolder;

import java.util.List;

/**
 * Created by mummyding on 15-11-15.
 */
public class ReadingAdapter extends BaseListAdapter<BookBean,ViewHolder>{


    public ReadingAdapter(Context context, ICache<BookBean> cache) {
        super(context, cache);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reading, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final BookBean bookBean = getItem(position);
        holder.title.setText(bookBean.getTitle());
        holder.info.setText(bookBean.getInfo());
        holder.imageView.setImageURI(Uri.parse(bookBean.getImage()));
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReadingDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(mContext.getString(R.string.id_book), bookBean);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        if(Utils.hasString(bookBean.getEbook_url())) {
            holder.parentView.setBackground(mContext.getResources().getDrawable(R.drawable.item_bg_selected,null)); //setBackgroundColor(mContext.getResources().getColor(R.color.item_bg));
        }
        else {
            holder.parentView.setBackground(mContext.getResources().getDrawable(R.drawable.item_bg, null));
        }

        if(isCollection){
            holder.collect_cb.setVisibility(View.GONE);
            holder.text.setText(R.string.text_remove);
            holder.text.setTextSize(20);
            holder.text.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(holder.parentView, R.string.notify_remove_from_collection, Snackbar.LENGTH_SHORT).
                            setAction(mContext.getString(R.string.text_ok), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (mItems.contains(bookBean) == false) {
                                        return;
                                    }
                                    mCache.execSQL(ReadingTable.updateCollectionFlag(bookBean.getTitle(), 0));
                                    mCache.execSQL(ReadingTable.deleteCollectionFlag(bookBean.getTitle()));
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
                bookBean.setIs_collected(isChecked ? 1 : 0);
                mCache.execSQL(ReadingTable.updateCollectionFlag(bookBean.getTitle(), isChecked ? 1 : 0));
                if(isChecked){
                    mCache.addToCollection(bookBean);
                }else{
                    mCache.execSQL(ReadingTable.deleteCollectionFlag(bookBean.getTitle()));
                }
            }
        });
        holder.collect_cb.setChecked(bookBean.getIs_collected() ==1 ? true:false);
    }
     class ViewHolder extends RecyclerView.ViewHolder{
         private View parentView;
         private SimpleDraweeView imageView;
         private TextView title;
         private TextView info;
         private CheckBox collect_cb;
         private TextView text;
         public ViewHolder(View itemView) {
             super(itemView);
             parentView = itemView;
             imageView = (SimpleDraweeView) itemView.findViewById(R.id.bookImg);
             title = (TextView) itemView.findViewById(R.id.bookTitle);
             info = (TextView) itemView.findViewById(R.id.bookInfo);
             collect_cb = (CheckBox) itemView.findViewById(R.id.collect_cb);
             if(isCollection) {
                 text = (TextView) parentView.findViewById(R.id.text);
             }
         }
     }
}
