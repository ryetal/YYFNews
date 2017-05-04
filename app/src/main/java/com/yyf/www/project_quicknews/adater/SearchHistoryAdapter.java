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

public class SearchHistoryAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mDatas;

    public SearchHistoryAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
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
        String history = (String) getItem(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_seach_history, null);
            holder = new ViewHolder();
            holder.tvHistory = (TextView) convertView.findViewById(R.id.tvSearchHistory);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvHistory.setText(history);

        return convertView;
    }

    private final class ViewHolder {
        TextView tvHistory;
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

    public void addData(String data) {
        mDatas.add(data);
        notifyDataSetChanged();
    }

    public List<String> getDatas() {
        return mDatas;
    }
}
