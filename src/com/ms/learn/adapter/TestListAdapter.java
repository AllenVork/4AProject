package com.ms.learn.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ms.learn.R;
import com.ms.learn.bean.PostsReciveEnrty;
import com.ms.learn.bean.PushItem;
import com.ms.learn.bean.TestEntry;
import com.ms.learn.bean.VideoKind;
import com.ms.learn.bean.VideoKindList;
import com.ms.learn.widgets.RemoteImageView;

public  class TestListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<TestEntry> testEntrys=new ArrayList<TestEntry>();
    Context context;

    public TestListAdapter(Context context,  List<TestEntry> testEntrys) {
        mInflater = LayoutInflater.from(context);
        this.testEntrys=testEntrys;
        this.context=context;

    }

    public int getCount() {
        return testEntrys.size();
    }
    public TestEntry getItem(int position) {
        return testEntrys.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.testlv_item, parent ,false);

            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_TestName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TestEntry testEntry=testEntrys.get(position);
        holder.tv_title.setText(testEntry.gettName());
        
       
        return convertView;
    }

    static class ViewHolder {
        TextView tv_title;
       
    }
}