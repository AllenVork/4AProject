package com.ms.learn.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.ms.learn.R;
import com.ms.learn.activity.ExamInfoActivity;
import com.ms.learn.activity.ResearchInfoActivity;
import com.ms.learn.bean.ExamInfoEntry;
import com.ms.learn.bean.ResearchInfoEntry;

public  class ResearchInfoListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ResearchInfoEntry> mResearchInfoEntrys=new ArrayList<ResearchInfoEntry>();
    Context context;
    

 
    
    public ResearchInfoListAdapter(List<ResearchInfoEntry> mResearchInfoEntrys, Context context) {
		super();
		
		this.mResearchInfoEntrys = mResearchInfoEntrys;
		this.context = context;
		mInflater = LayoutInflater.from(context); 
	}
    

    public int getCount() {
    	
        return mResearchInfoEntrys.size();
    }
   
	public ResearchInfoEntry getItem(int position) {
        return mResearchInfoEntrys.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        boolean[] result={false,false,false,false};
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.researchinfolist_item, parent ,false);

            holder = new ViewHolder();
            holder.tv_reseachTitle = (TextView) convertView.findViewById(R.id.tv_researchTitle);
            holder.mRadioGroup=(RadioGroup) convertView.findViewById(R.id.radioGroupResearch);
            
            holder.radioButton_A=(CheckBox) convertView.findViewById(R.id.rb_choicA);
            holder.radioButton_B=(CheckBox) convertView.findViewById(R.id.rb_choicB);
            holder.radioButton_C=(CheckBox) convertView.findViewById(R.id.rb_choicC);
            holder.radioButton_D=(CheckBox) convertView.findViewById(R.id.rb_choicD);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ResearchInfoEntry mResearchInfoEntry=getItem(position);
        int num=position+1;
        holder.tv_reseachTitle.setText(num+"."+mResearchInfoEntry.getTitle());
        holder.radioButton_A.setText("A."+mResearchInfoEntry.getChoic_A());
        holder.radioButton_B.setText("B."+mResearchInfoEntry.getChoic_B());
        holder.radioButton_C.setText("C."+mResearchInfoEntry.getChoic_C());
        holder.radioButton_D.setText("D."+mResearchInfoEntry.getChoic_D());
        
        ResearchInfoActivity.reList.add(position, result);
        holder.radioButton_A.setOnCheckedChangeListener(new OnCheckedChangeListener() {
          
 		@Override
 		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
 			// TODO Auto-generated method stub
 			 boolean[] res= ResearchInfoActivity.reList.get(position);
 			 ResearchInfoActivity.isDone=true;
 			 if(isChecked){
 				
 				res[0]=true;
 	         }else{
 	            res[0]=false;
 	         }
 			 
 	            
 		}
 	});
        
        holder.radioButton_B.setOnCheckedChangeListener(new OnCheckedChangeListener() {
     	  
    		@Override
    		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    		 boolean[] res= ResearchInfoActivity.reList.get(position);
    			// TODO Auto-generated method stub
    			 if(isChecked){
    				res[1]=true;
    	         }else{
    	        	res[1]=false;
    	         }
    			
    		}
    	});
        holder.radioButton_C.setOnCheckedChangeListener(new OnCheckedChangeListener() {
     	  
    		@Override
    		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    		 boolean[] res= ResearchInfoActivity.reList.get(position);
    			if(isChecked){
    				res[2]=true;
    	         }else{
    	        	res[2]=false;
    	         }
    			
    			
    	            
    		}
    	});
        holder.radioButton_D.setOnCheckedChangeListener(new OnCheckedChangeListener() {
    		
    		@Override
    		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    			boolean[] res= ResearchInfoActivity.reList.get(position);
    			if(isChecked){
    				res[3]=true;
    	         }else{
    	        	res[3]=false;
    	         }
    			
    		}
    	});
      
        
        return convertView;
    }

    static class ViewHolder {
        TextView tv_reseachTitle;
        RadioGroup mRadioGroup;
        CheckBox radioButton_A,radioButton_B,radioButton_C,radioButton_D;
    }
}