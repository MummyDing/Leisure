package com.mummyding.app.leisure.support.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mummyding.app.leisure.R;
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

    public ReadingAdapter(List<BookBean> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reading, parent, false); // View.inflate(mContext, R.layout.item_reading,null);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        final BookBean readingBean = getItem(position);
        holder.title.setText(readingBean.getTitle());
        holder.info.setText(readingBean.toString());
        holder.imageView.setImageURI(Uri.parse(readingBean.getImage()));
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReadingDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", readingBean);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        if(Utils.hasString(readingBean.getEbook_url()))
        holder.parentView.setBackgroundColor(R.color.primary);
    }
    public BookBean getItem(int pos){
        return items.get(pos);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
     class ViewHolder extends RecyclerView.ViewHolder{
         View parentView;
         SimpleDraweeView imageView;
         TextView title;
         TextView info;
         public ViewHolder(View itemView) {
             super(itemView);
             parentView = itemView;
             imageView = (SimpleDraweeView) itemView.findViewById(R.id.bookImg);
             title = (TextView) itemView.findViewById(R.id.bookTitle);
             info = (TextView) itemView.findViewById(R.id.bookInfo);
         }
     }
}
