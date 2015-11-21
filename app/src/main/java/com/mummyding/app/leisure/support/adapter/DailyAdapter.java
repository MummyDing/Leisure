package com.mummyding.app.leisure.support.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.model.daily.DailyBean;
import com.mummyding.app.leisure.ui.WebViewActivity;

import java.util.List;

/**
 * Created by mummyding on 15-11-21.
 */
public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder>{

    private List<DailyBean> item;
    private Context mContext;

    public DailyAdapter(List<DailyBean> item, Context mContext) {
        this.item = item;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_daily,null);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DailyBean dailyBean = getItem(position);
        holder.title.setText(dailyBean.getTitle());
        holder.description.setText(Html.fromHtml(dailyBean.getDescription()));
        holder.info.setText("作者: "+dailyBean.getAuthor()+"  "+dailyBean.getPubDate());
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(String.valueOf(R.string.id_url),dailyBean.getLink());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    private DailyBean getItem(int position){
        return item.get(position);
    }
    @Override
    public int getItemCount() {
        return item.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private View parentView;
        private TextView title;
        private TextView description;
        private TextView info;
        public ViewHolder(View itemView) {
            super(itemView);
             parentView = itemView;
            title = (TextView) parentView.findViewById(R.id.title);
            description = (TextView) parentView.findViewById(R.id.description);
            info = (TextView) parentView.findViewById(R.id.info);
        }
    }
}
