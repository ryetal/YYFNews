package com.yyf.www.project_quicknews.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.bean.NewsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 子凡 on 2017/4/6.
 */

public class SearchResultAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<NewsBean> mDatas;
    private String mKeyword;

    public SearchResultAdapter(Context context, String keyword) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = new ArrayList<>();
        mKeyword = keyword;
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

        NewsBean news = (NewsBean) getItem(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_news, null);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvNewsTitle);
            holder.tvAbstract = (TextView) convertView.findViewById(R.id.tvAbstract);
            holder.llytImages = (LinearLayout) convertView.findViewById(R.id.llytImages);
            holder.ivImage1 = (ImageView) convertView.findViewById(R.id.ivImage1);
            holder.ivImage2 = (ImageView) convertView.findViewById(R.id.ivImage2);
            holder.ivImage3 = (ImageView) convertView.findViewById(R.id.ivImage3);
            holder.imageViews = new ImageView[]{holder.ivImage1, holder.ivImage2, holder.ivImage3};
            holder.tvSource = (TextView) convertView.findViewById(R.id.tvSource);
            holder.tvComments = (TextView) convertView.findViewById(R.id.tvComments);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String[] images = new String[]{
                news.getImage1(), news.getImage2(), news.getImage3()
        };

        if (news.getImage1().equals("")
                && news.getImage2().equals("")
                && news.getImage3().equals("")) {
            holder.llytImages.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < images.length; i++) {
                if (images[i].equals("")) {
                    holder.imageViews[i].setVisibility(View.GONE);
                } else {
                    Picasso.with(mContext).load(images[i])
                            .placeholder(R.mipmap.ic_launcher)
                            .into(holder.imageViews[i]);
                }
            }
        }

        holder.tvTitle.setText(lightKeyword(news.getTitle()));
        holder.tvSource.setText(news.getSource());
        holder.tvComments.setText(news.getCommentCount() + "次评论");
        holder.tvTime.setText(news.getDate());

        return convertView;
    }

    private final class ViewHolder {
        TextView tvTitle;
        TextView tvAbstract;
        LinearLayout llytImages;
        ImageView ivImage1;
        ImageView ivImage2;
        ImageView ivImage3;
        ImageView[] imageViews;
        TextView tvSource;
        TextView tvComments;
        TextView tvTime;
    }

    public void addDatas(List<NewsBean> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void resetDatas(List<NewsBean> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 高亮Keyword
     *
     * @param rawString
     * @return
     */
    private SpannableString lightKeyword(String rawString) {

        if (rawString == null) {
            rawString = "";
        }

        SpannableString spannableString = new SpannableString(rawString);
        int index = rawString.indexOf(mKeyword);
        if (index == -1) {
            return spannableString;
        }
        spannableString.setSpan(new ForegroundColorSpan(Color.RED), index, index + mKeyword.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

}
