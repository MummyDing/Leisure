package com.mummyding.app.leisure.support.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mummyding.app.leisure.LeisureApplication;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.cache.cache.ReadingCache;
import com.mummyding.app.leisure.cache.table.ReadingTable;
import com.mummyding.app.leisure.model.reading.BookBean;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.ui.reading.ReadingDetailsActivity;

import java.util.List;

/**
 * Created by mummyding on 15-11-15.
 */
public class ReadingAdapter extends RecyclerView.Adapter<ReadingAdapter.ViewHolder> {

    private List<BookBean> items;
    private Context mContext;
    private ReadingCache cache;
    public ReadingAdapter(List<BookBean> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
        cache = new ReadingCache(LeisureApplication.AppContext);
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
    public void onBindViewHolder(ViewHolder holder,int position) {
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

        holder.collect_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bookBean.setIs_collected(isChecked ? 1 : 0);
                cache.execSQL(ReadingTable.updateCollectionFlag(bookBean.getTitle(), isChecked ? 1 : 0));
                if(isChecked){
                    cache.addToCollection(bookBean);
                }else{
                    cache.execSQL(ReadingTable.deleteCollectionFlag(bookBean.getTitle()));
                }
            }
        });
        holder.collect_cb.setChecked(bookBean.getIs_collected() ==1 ? true:false);
    }
    public BookBean getItem(int pos){
        return items.get(pos);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
     class ViewHolder extends RecyclerView.ViewHolder{
         private View parentView;
         private SimpleDraweeView imageView;
         private TextView title;
         private TextView info;
         private CheckBox collect_cb;
         public ViewHolder(View itemView) {
             super(itemView);
             parentView = itemView;
             imageView = (SimpleDraweeView) itemView.findViewById(R.id.bookImg);
             title = (TextView) itemView.findViewById(R.id.bookTitle);
             info = (TextView) itemView.findViewById(R.id.bookInfo);
             collect_cb = (CheckBox) parentView.findViewById(R.id.collect_cb);
         }
     }
}
