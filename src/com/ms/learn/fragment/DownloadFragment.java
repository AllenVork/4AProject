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

	public class DownloadFragment extends Fragment {

	    private static final int DOWNLOADCOMPLITE_STATE = 0x1;
	    private static final int DOWNLOAD_NOTCOMPLITE_STATE = 0x2;

	    private int mTabState;
	    
	    

	    public DownloadFragment() {
			super();
		}

		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.fragment_downloadtab, container, false);
	        final ImageView imgOne=(ImageView) view.findViewById(R.id.fragment_up);
	        final ImageView imgTwo=(ImageView) view.findViewById(R.id.fragmenttwo_up);
	        final Button downcompliteTab = (Button) view.findViewById(R.id.downcomplite_view_tab);
	        final Button downloadingTab = (Button) view.findViewById(R.id.downloading_view_tab);
	        downcompliteTab.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	gotoDownCompliteView();
	            	downcompliteTab.setBackgroundResource(R.drawable.btdownload_up);
	            	imgTwo.setVisibility(View.VISIBLE);
	            	
	            	downloadingTab.setBackgroundResource(R.drawable.btdownload_down);
	            	imgOne.setVisibility(View.INVISIBLE);
	            }
	        });
	       
	       
	        downloadingTab.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	gotoDownLoadingView();
	            	downloadingTab.setBackgroundResource(R.drawable.btdownload_up);
	            	imgOne.setVisibility(View.VISIBLE);
	            	
	            	downcompliteTab.setBackgroundResource(R.drawable.btdownload_down);
	            	imgTwo.setVisibility(View.INVISIBLE);
	            }
	        });
	        
	    

	        return view;
	    }

	    private void gotoDownCompliteView() {
	    	  if (mTabState != DOWNLOADCOMPLITE_STATE) {
		            mTabState = DOWNLOADCOMPLITE_STATE;
		            FragmentManager fm = getFragmentManager();
		            if (fm != null) {
		                FragmentTransaction ft = fm.beginTransaction();
		                ft.replace(R.id.fragment_content, new DownloadCompliteFragment());
		                ft.commit();
		            }
		        }
	    	
		}
	    
	    public void gotoDownLoadingView() {
	        if (mTabState != DOWNLOAD_NOTCOMPLITE_STATE) {
	            mTabState = DOWNLOAD_NOTCOMPLITE_STATE;
	            FragmentManager fm = getFragmentManager();
	            if (fm != null) {
	                FragmentTransaction ft = fm.beginTransaction();
	                ft.replace(R.id.fragment_content, new DownloadingFragment());
	                ft.commit();
	            }
	        }
	    }

	 

	}
