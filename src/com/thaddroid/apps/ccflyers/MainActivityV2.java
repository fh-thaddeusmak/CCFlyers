package com.thaddroid.apps.ccflyers;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivityV2 extends Activity {
	
	//MEMBER VARIABLES
	private SuperMarketsAdapter mSuperMarketsAdapter;
	private ArrayList<SuperMarket> superMarketList;
	
	//CONTROL VARIABLES
	private ListView ccFlyersListView;
	private ListHolder holder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ccflyers);
		
		createControlReference();
		initListItem();
	}
	
	private void createControlReference(){
		ccFlyersListView = (ListView)findViewById(R.id.ccFlyersListView);
	}
	
	private void initListItem(){
		if(superMarketList == null){
			superMarketList = new ArrayList<SuperMarket>();
		}
		
		String[] marketName = getResources().getStringArray(R.array.supermarket_title_list);
		
		superMarketList.clear();
		for(int i=0; i<marketName.length; i++){
			superMarketList.add(new SuperMarket(marketName[i]));
		}
	}
	
	private class SuperMarketsAdapter extends BaseAdapter{
		private Context context;
		private ArrayList<SuperMarket> superMarketArray;
		
		private SuperMarketsAdapter(Context context, ArrayList<SuperMarket> arrayList){
			this.context = context;
			this.superMarketArray = arrayList;
		}

		@Override
		public int getCount() {
			return superMarketArray.size();
		}

		@Override
		public Object getItem(int position) {
			return superMarketArray.get(position);
		}

		@Override
		public long getItemId(int position) {
			return superMarketArray.get(position).hashCode();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			holder = null;
			
			final SuperMarket singleSuperMarket = superMarketArray.get(position);
			if(row == null){
				LayoutInflater inflater = ((Activity)context).getLayoutInflater();
				inflater.inflate(R.layout.listitem, parent, false);
				
				holder.listItemImageView = (ImageView)row.findViewById(R.id.listItemImageView);
				holder.listItemMarketNameTextView = (TextView)row.findViewById(R.id.listItemMarketNameTextView);
				holder.listItemWeekDiscountTextView = (TextView)row.findViewById(R.id.listItemWeekDiscountTextView);
				holder.listItemDiscountPeriodTextView = (TextView)row.findViewById(R.id.listItemDiscountPeriodTextView);
			
				row.setTag(holder);
			}else{
				holder = (ListHolder) row.getTag();
			}
			
			if(singleSuperMarket != null){
				holder.listItemMarketNameTextView.setText(singleSuperMarket.getName());
			}
			
			return row;
		}
		
	}
	
	private class ListHolder{
		public ImageView listItemImageView;
		public TextView listItemMarketNameTextView;
		public TextView listItemWeekDiscountTextView;
		public TextView listItemDiscountPeriodTextView;
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		mSuperMarketsAdapter = new SuperMarketsAdapter(this, superMarketList);
		ccFlyersListView.setAdapter(mSuperMarketsAdapter);
		ccFlyersListView.invalidate();
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
}
