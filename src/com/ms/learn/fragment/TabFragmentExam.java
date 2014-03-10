package com.ms.learn.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.ms.learn.R;

	public class TabFragmentExam extends Fragment {

	    private static final int EXAM_STATE = 0x1;
	    private static final int RESEARCH_STATE = 0x2;
	    private static final int EVALUATION_STATE = 0x3;

	    private int mTabState;
	    private Button[] buttons=new Button[3];
	    

	    public TabFragmentExam() {
			super();
		}

		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.fragmentexam_tab, container, false);
             buttons[0] = (Button) view.findViewById(R.id.exam_view_tab);
             buttons[0].setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	gotoOtherView(new ExamFragment(), EXAM_STATE);
	            }
	        });
	       
             buttons[1] = (Button) view.findViewById(R.id.reserch_view_tab);
             buttons[1].setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	gotoOtherView(new ResearchFragment(), RESEARCH_STATE);
	            }
	        });
	        
             buttons[2] = (Button) view.findViewById(R.id.evaluation_view_tab);
             buttons[2].setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	gotoOtherView(new EvaluationListFragment(), EVALUATION_STATE);
	                  
	            }
				
	        });
             buttons[0].setSelected(true);

	        return view;
	    }

		public void gotoExamView() {
	    	  if (mTabState != EXAM_STATE) {
		            mTabState = EXAM_STATE;
		            FragmentManager fm = getFragmentManager();
		            if (fm != null) {
		                FragmentTransaction ft = fm.beginTransaction();
		                ft.replace(R.id.fragment_content, new ExamFragment());
		                ft.commit();
		            }
		        }
	    	
		}
	    
	  
	    public  void gotoOtherView(Fragment mFragment,int state) {
			   int i=state-1;
		        if (mTabState != state) {
		            mTabState = state;
		            FragmentManager fm = getFragmentManager();
		            if (fm != null) {
		                FragmentTransaction ft = fm.beginTransaction();
		                ft.replace(R.id.fragment_content, mFragment);
		                ft.commit();
		            }
		            for(int j=0;j<3;j++){
						if(i==j){
							buttons[j].setSelected(true);
						}else {
							buttons[j].setSelected(false);
						}
					
					}
		        }
		    }

	}
