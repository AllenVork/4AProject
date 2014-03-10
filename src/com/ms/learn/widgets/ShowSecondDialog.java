package com.ms.learn.widgets;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.ms.learn.R;
import com.ms.learn.adapter.GridViewAdapter;
import com.ms.learn.bean.VideoKind;

public class ShowSecondDialog extends Dialog {
	private Context mContext;
	private GridView gridView;
	private GridViewAdapter gridViewAdapter;
	List<VideoKind> kinds;
	
	public ShowSecondDialog(Context context, int theme,List<VideoKind> kinds) {
		super(context, theme);
		this.mContext = context;
		this.kinds=kinds;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showsecondkind_dialog);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		DisplayMetrics dm = getDensity(mContext);
		params.width = (int) (dm.widthPixels);
		params.height = (int) (dm.widthPixels*0.7);
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);
	//	window.setWindowAnimations(R.style.dialogstyle); // Ìí¼Ó¶¯»­
		this.initUi();
	}

	private void initUi() {
		gridView=(GridView) findViewById(R.id.gv_showSecond);
		gridViewAdapter=new GridViewAdapter(mContext, kinds);
		gridView.setAdapter(gridViewAdapter);
		gridView.setFocusable(true);
		gridView.setFocusableInTouchMode(true);
		gridView.setOnItemClickListener(gridClick);
	
	}

	private OnItemClickListener gridClick=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
		
			
		}
	};



	private DisplayMetrics getDensity(Context context) {
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm;
	}

	public   void dialogDismiss() {
		ShowSecondDialog.this.dismiss();
	}
	


}