package com.yyf.www.project_quicknews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.bean.CommentBean;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<CommentBean> mDatas;

    public CommentAdapter(Context context) {
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

        CommentBean comment = (CommentBean) getItem(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_comment, null);
            holder = new ViewHolder();
            holder.ivProfilePhoto = (ImageView) convertView.findViewById(R.id.ivProfilePhoto);
            holder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvComment = (TextView) convertView.findViewById(R.id.tvComment);
            holder.btnUpvote = (Button) convertView.findViewById(R.id.btnUpvote);
            holder.btnComment = (Button) convertView.findViewById(R.id.btnComment);
            holder.btnMore = (Button) convertView.findViewById(R.id.btnMore);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String profilePhotoURL = comment.getUser().getProfilePhotoURL();
        if (profilePhotoURL.equals("")) {
            profilePhotoURL = null;
        }

        Picasso.with(mContext).load(profilePhotoURL)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.ivProfilePhoto);
        holder.tvUserName.setText(comment.getUser().getUserName());
        holder.tvDate.setText(comment.getDate());
        holder.tvComment.setText(comment.getComment());

        holder.btnUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "赞", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "评论", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "更多", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    private final class ViewHolder {

        ImageView ivProfilePhoto;
        TextView tvUserName;
        TextView tvDate;
        TextView tvComment;
        Button btnUpvote;
        Button btnComment;
        Button btnMore;
    }

    public void addDatas(List<CommentBean> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void resetDatas(List<CommentBean> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }
}
