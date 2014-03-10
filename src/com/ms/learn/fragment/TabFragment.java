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
import android.widget.ImageView;

import com.ms.learn.R;

	public class TabFragment extends Fragment {

	    private static final int COMMEND_STATE = 0x1;
	    private static final int DETAIL_STATE = 0x2;
	    private static final int ABOUT_STATE = 0x3;

	    private int mTabState;
	  
	    ImageView[] imageViews=new ImageView[3];
	    Button[] buttons=new Button[3];
	    
	    

	    public TabFragment() {
			super();
		}

		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.fragment_tab, container, false);
	         buttons[2] = (Button) view.findViewById(R.id.about_view_tab);
	         buttons[1] = (Button) view.findViewById(R.id.detail_view_tab);
	         buttons[0] = (Button) view.findViewById(R.id.comment_view_tab);
	        
	         imageViews[2]=(ImageView) view.findViewById(R.id.iv_about);
	         imageViews[1]=(ImageView) view.findViewById(R.id.iv_detail);
	         imageViews[0]=(ImageView) view.findViewById(R.id.iv_commit);
	        
	        
	         buttons[2].setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	gotoAboutView();
	            	changeButtonBg(ABOUT_STATE);
	            }
	        });
	       
	       
	         buttons[1].setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	gotoDetailView();
	            	changeButtonBg(DETAIL_STATE);
	            }
	        });
	        
	        
	         buttons[0].setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                  gotoCommentView();
	                  changeButtonBg(COMMEND_STATE);
	                  
	            }
				
	        });

	        return view;
	    }

	    private void gotoAboutView() {
	    	  if (mTabState != ABOUT_STATE) {
		            mTabState = ABOUT_STATE;
		            FragmentManager fm = getFragmentManager();
		            if (fm != null) {
		                FragmentTransaction ft = fm.beginTransaction();
		                ft.replace(R.id.fragment_content, new AboutVideoFragment());
		                ft.commit();
		            }
		        }
	    	
		}
	    
	    public void gotoCommentView() {
	        if (mTabState != COMMEND_STATE) {
	            mTabState = COMMEND_STATE;
	            FragmentManager fm = getFragmentManager();
	            if (fm != null) {
	                FragmentTransaction ft = fm.beginTransaction();
	                ft.replace(R.id.fragment_content, new CommentVideoFragment());
	                ft.commit();
	            }
	        }
	    }

	    public void gotoDetailView() {
	        if (mTabState != DETAIL_STATE) {
	            mTabState = DETAIL_STATE;
	            FragmentManager fm = getFragmentManager();
	            if (fm != null) {
	                FragmentTransaction ft = fm.beginTransaction();
	                ft.replace(R.id.fragment_content, new DetailVideoFragment());
	                ft.commit();
	            }
	        }
	    }
	    
	    private void changeButtonBg(int i){
	    	
	    	
	    	for(int j=0;j<imageViews.length;j++){
	    		if(j==i-1){
	    			buttons[j].setBackgroundResource(R.drawable.videofragmentbar_bg);
	    			imageViews[j].setVisibility(View.VISIBLE);
	    		}else{
	    			buttons[j].setBackgroundResource(R.drawable.btdownload_down);
	    			imageViews[j].setVisibility(View.INVISIBLE);
	    		}
	    	}
	    	
	    	
	    }

	}
