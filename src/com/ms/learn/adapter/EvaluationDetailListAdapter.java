package com.ms.learn.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ms.learn.R;
import com.ms.learn.activity.EvaluationDetailActivity;
import com.ms.learn.bean.EvaluationInfoEntry;

public class EvaluationDetailListAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	List<EvaluationInfoEntry> evaluationInfoEntries = new ArrayList<EvaluationInfoEntry>();
	Context context;
	public List<boolean[]> reList = new ArrayList<boolean[]>();

	public EvaluationDetailListAdapter(List<EvaluationInfoEntry> evaluationInfoEntries, Context context) {
		super();

		this.evaluationInfoEntries = evaluationInfoEntries;
		this.context = context;
		mInflater = LayoutInflater.from(context);
		for (int i = 0; i < evaluationInfoEntries.size(); i++) {
			boolean[] result = { false, false };
			reList.add(i, result);
		}
	}

	public int getCount() {
		return evaluationInfoEntries.size();
	}

	public EvaluationInfoEntry getItem(int position) {
		return evaluationInfoEntries.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		EvaluationInfoEntry evaluationInfoEntries = getItem(position);

		RadioGroup mRadioGroup;
		TextView tv_ExamTitle;
		View view = mInflater.inflate(R.layout.evaluationdetaillist_item,
				parent, false);
		tv_ExamTitle = (TextView) view.findViewById(R.id.tv_exmaTitle);
		int textNum = position + 1;
		tv_ExamTitle.setText(textNum + "."+ evaluationInfoEntries.getQuestion());

		mRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroupEvalution);
		mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						boolean[] res = reList.get(position);
						switch (checkedId) {
						case R.id.rb_choicA:
							res[0] = true;
							break;
						case R.id.rb_choicB:
							res[0] = false;
							break;

						default:
							break;
						}

						res[1] = true;

					}
				});

		return view;

	}

	

	public List<boolean[]> getReList() {
		return reList;
	}

}