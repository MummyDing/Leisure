package com.mummyding.app.leisure.support.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.model.reading.BookBean;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookBean readingBean = getItem(position);
        holder.title.setText(readingBean.getTitle());
        holder.info.setText(readingBean.toString());
        holder.imageView.setImageURI(Uri.parse(readingBean.getImage()));
    }
    public BookBean getItem(int pos){
        return items.get(pos);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
     class ViewHolder extends RecyclerView.ViewHolder{
         SimpleDraweeView imageView;
         TextView title;
         TextView info;
         public ViewHolder(View itemView) {
             super(itemView);
             imageView = (SimpleDraweeView) itemView.findViewById(R.id.bookImg);
             title = (TextView) itemView.findViewById(R.id.bookTitle);
             info = (TextView) itemView.findViewById(R.id.bookInfo);
         }
     }
}
