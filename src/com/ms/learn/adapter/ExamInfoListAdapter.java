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
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ms.learn.R;
import com.ms.learn.bean.ExamInfoEntry;

public  class ExamInfoListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ExamInfoEntry> mmExamInfoEntrys=new ArrayList<ExamInfoEntry>();
    Context context;
    boolean isVisble=false;
    public  List<boolean[]> reList=new ArrayList<boolean[]>();
   
    
    public ExamInfoListAdapter(List<ExamInfoEntry> mExamInfoEntrys, Context context) {
		super();
		
		this.mmExamInfoEntrys = mExamInfoEntrys;
		this.context = context;
		mInflater = LayoutInflater.from(context); 
		for(int i=0;i<mmExamInfoEntrys.size();i++){
			boolean[] result={false,false,false,false,false};
	    	reList.add(i, result);
		}
	}
    

    public int getCount() {
        return mmExamInfoEntrys.size();
    }
   
	public ExamInfoEntry getItem(int position) {
        return mmExamInfoEntrys.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
    	
    	ExamInfoEntry mExamInfoEntry=getItem(position);
    	String strAnswer=mExamInfoEntry.getAnswer();
    	
    	if(strAnswer.length()==1){
    		RadioGroup mRadioGroup;
    		TextView tv_ExamTitle,tv_Answer;
    		RadioButton radioButton_A,radioButton_B,radioButton_C,radioButton_D;
    		 View view=mInflater.inflate(R.layout.examinfolistradeobutton_item, parent ,false);
    		 tv_ExamTitle = (TextView) view.findViewById(R.id.tv_exmaTitle);
    		 int textNum=position+1;
    		 tv_ExamTitle.setText(textNum+"."+mExamInfoEntry.getTitle()+"(单选)");
             tv_Answer = (TextView) view.findViewById(R.id.tv_exmaAnswer);
             tv_Answer.setText("答案："+strAnswer);
             if(isVisble){
             	tv_Answer.setVisibility(View.VISIBLE);
             }else {
             	tv_Answer.setVisibility(View.GONE);
             }
             
             radioButton_A=(RadioButton) view.findViewById(R.id.rb_choicA);
             radioButton_B=(RadioButton) view.findViewById(R.id.rb_choicB);
             radioButton_C=(RadioButton) view.findViewById(R.id.rb_choicC);
             radioButton_D=(RadioButton) view.findViewById(R.id.rb_choicD);
             radioButton_A.setText("A."+mExamInfoEntry.getChoic_A());
             radioButton_B.setText("B."+mExamInfoEntry.getChoic_B());
             radioButton_C.setText("C."+mExamInfoEntry.getChoic_C());
             radioButton_D.setText("D."+mExamInfoEntry.getChoic_D());
             
    		 mRadioGroup=(RadioGroup) view.findViewById(R.id.radioGroupExam);
    		 mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					boolean[] res= reList.get(position);
					int j = -1;
					switch (checkedId) {
					case R.id.rb_choicA:
						j=0;
						break;
					case R.id.rb_choicB:
						j=1;
						break;
					case R.id.rb_choicC:
						j=2;
						break;
					case R.id.rb_choicD:
						j=3;
						break;

					default:
						break;
					}
					System.out.println("+++++checkedId+++++++++"+j);
					
					
					res[4]=true;
					for(int i=0;i<4;i++){
						if(j==i){
							res[i]=true;
						}else{
							res[i]=false;
						}
					}
					
				}
			});
    		
    		
    		return view;
    		
    	}else if(strAnswer.length()>=2){
    	
    		
    		convertView=mInflater.inflate(R.layout.examinfolist_item, parent ,false);
        	TextView tv_ExamTitle,tv_Answer;
            CheckBox radioButton_A,radioButton_B,radioButton_C,radioButton_D;
            tv_ExamTitle = (TextView) convertView.findViewById(R.id.tv_exmaTitle);
            int textNum=position+1;
            tv_ExamTitle.setText(textNum+"."+mExamInfoEntry.getTitle()+"(多选)");
            
            tv_Answer = (TextView) convertView.findViewById(R.id.tv_exmaAnswer);
            tv_Answer.setText("答案："+strAnswer);
            if(isVisble){
            	tv_Answer.setVisibility(View.VISIBLE);
            }else {
            	tv_Answer.setVisibility(View.GONE);
            }
            
            radioButton_A=(CheckBox) convertView.findViewById(R.id.rb_choicA);
            radioButton_B=(CheckBox) convertView.findViewById(R.id.rb_choicB);
            radioButton_C=(CheckBox) convertView.findViewById(R.id.rb_choicC);
            radioButton_D=(CheckBox) convertView.findViewById(R.id.rb_choicD);
                
           
            
            radioButton_A.setText("A."+mExamInfoEntry.getChoic_A());
            radioButton_B.setText("B."+mExamInfoEntry.getChoic_B());
            radioButton_C.setText("C."+mExamInfoEntry.getChoic_C());
            radioButton_D.setText("D."+mExamInfoEntry.getChoic_D());
           
           
            radioButton_A.setOnCheckedChangeListener(new OnCheckedChangeListener() {
             
    		@Override
    		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    			// TODO Auto-generated method stub
    			 boolean[] res= reList.get(position);
    			 
    			 res[4]=true;
    			 if(isChecked){
    				
    				res[0]=true;
    	         }else{
    	            res[0]=false;
    	         }
    			 
    	            
    		}
    	});
           
          radioButton_B.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        	  
       		@Override
       		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
       		 boolean[] res= reList.get(position);
       		 res[4]=true;
       			 if(isChecked){
       				res[1]=true;
       	         }else{
       	        	res[1]=false;
       	         }
       			
       		}
       	});
          radioButton_C.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        	  
       		@Override
       		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
       		 boolean[] res= reList.get(position);
       		res[4]=true;
       			if(isChecked){
       				res[2]=true;
       	         }else{
       	        	res[2]=false;
       	         }
       			
       			
       	            
       		}
       	});
          radioButton_D.setOnCheckedChangeListener(new OnCheckedChangeListener() {
       		
       		@Override
       		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
       			boolean[] res=reList.get(position);
       			res[4]=true;
       			if(isChecked){
       				res[3]=true;
       	         }else{
       	        	res[3]=false;
       	         }
       			
       		}
       	});
            return convertView;
    	}
    	
		return null;
    }

   
    public void setIsVisible(boolean isVisible){
    	isVisble=isVisible;
    	this.notifyDataSetChanged();
    }
    
    public List<boolean[]>  getReList(){
    	return reList;
    }
    
}