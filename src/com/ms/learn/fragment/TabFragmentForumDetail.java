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

	public class TabFragmentForumDetail extends Fragment {

	    public static final int MOSTNEWS_STATE = 0x1;
	    private static final int BEST_STATE = 0x2;
	    private static final int SERCH_STATE = 0x3;
	   // private static final int SEND_STATE = 0x4;
	    private static final int MY_STATE = 0x4;

	    private int mTabState;
	    
	    private Button[] buttons=new Button[4];

	    public TabFragmentForumDetail() {
			super();
		}

		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.fragmentforumdetail_tab, container, false);
	        buttons[0] = (Button) view.findViewById(R.id.mostnews_view_tab);
	        buttons[0].setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	gotoOtherView(new MostnewsFragment(), MOSTNEWS_STATE);
	            }
	        });
	       
	        buttons[1] = (Button) view.findViewById(R.id.bestposts_view_tab);
	        buttons[1].setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	gotoOtherView(new BestPostsFragment(), BEST_STATE);
	            }
	        });
	        
	        buttons[2] = (Button) view.findViewById(R.id.serchposts_view_tab);
	        buttons[2].setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	gotoOtherView(new SearchPostsFragment(), SERCH_STATE);
	                  
	            }
				
	        });
	        
	        /*Button send = (Button) view.findViewById(R.id.sendposts_view_tab);
	        send.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	gotoOtherView(new SendPostsFragment(), SEND_STATE);
	                  
	            }
				
	        });*/
	        
	        buttons[3] = (Button) view.findViewById(R.id.myposts_view_tab);
	        buttons[3].setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	           	gotoOtherView(new MyPostsFragment(), MY_STATE );
	                  
	            }
				
	        });
	        buttons[0].setSelected(true);
	        return view;
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
	            for(int j=0;j<4;j++){
					if(i==j){
						buttons[j].setSelected(true);
					}else {
						buttons[j].setSelected(false);
					}
				
				}
	        }
	    }

	}
