package com.ms.learn.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.ms.learn.R;
import com.ms.learn.bean.EvaluationInfoEntry;

public  class EvaluationInfoListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<EvaluationInfoEntry> mEvaluationInfoEntrys=new ArrayList<EvaluationInfoEntry>();
    Context context;
    String result[]={"a","b","c","d"};
    int choic[]={R.id.rb_choicA,R.id.rb_choicB,R.id.rb_choicC,R.id.rb_choicD};

 
    
    public EvaluationInfoListAdapter(List<EvaluationInfoEntry> mEvaluationInfoEntrys, Context context) {
		super();
		
		this.mEvaluationInfoEntrys = mEvaluationInfoEntrys;
		this.context = context;
		mInflater = LayoutInflater.from(context); 
	}
    

    public int getCount() {
    	
        return mEvaluationInfoEntrys.size();
    }
   
	public EvaluationInfoEntry getItem(int position) {
        return mEvaluationInfoEntrys.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.evaluationinfolist_item, parent ,false);

            holder = new ViewHolder();
            holder.tv_evaluationTitle = (TextView) convertView.findViewById(R.id.tv_evalutionTitle);
            holder.mRadioGroup=(RadioGroup) convertView.findViewById(R.id.radioGroupEvalution);
            
            holder.radioButton_yse=(RadioButton) convertView.findViewById(R.id.rb_choicA);
            holder.radioButton_No=(RadioButton) convertView.findViewById(R.id.rb_choicB);
           
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        EvaluationInfoEntry mEvaluationInfoEntry=getItem(position);
        holder.tv_evaluationTitle.setText(mEvaluationInfoEntry.getQuestion());
        
        holder.mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				System.out.println("+++++++++++++++++++");
			}
		});
        
       
        
        return convertView;
    }

    static class ViewHolder {
        TextView tv_evaluationTitle;
        RadioGroup mRadioGroup;
        RadioButton radioButton_yse,radioButton_No;
    }
}