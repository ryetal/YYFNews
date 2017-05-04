package com.yyf.www.project_quicknews.adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yyf.www.project_quicknews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 子凡 on 2017/4/6.
 */

public class SearchHotAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mDatas;
    private int mItemCount;

    public SearchHotAdapter(Context context, int itemCount) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = new ArrayList<>();
        mItemCount = itemCount;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : Math.min(mDatas.size(), mItemCount);
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        String title = (String) getItem(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_seach_hot, null);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvSearchHot);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(title);

        return convertView;
    }

    private final class ViewHolder {
        TextView tvTitle;
    }

    public void addDatas(List<String> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void resetDatas(List<String> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }
}
