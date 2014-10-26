package com.thaddroid.apps.ccflyers;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityV2 extends Activity {
	//CONSTANTS
	private final int NUMBER_OF_SUPERMARKETS = 22;
	
	//MEMBER VARIABLES
	private SuperMarketsAdapter mSuperMarketsAdapter;
	private ArrayList<SuperMarket> superMarketList;
	private int[] imageArray;
	private ProgressDialog progressBar;
	private FlyersDownloader mFlyersDownloader;
	private int[] checkArray;
	
	//CONTROL VARIABLES
	private ListView ccFlyersListView;
	private ListHolder holder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ccflyers);
		createControlReference();
		
		initListItem();
		initImageArray();
	}
	
	private void createControlReference(){
		ccFlyersListView = (ListView)findViewById(R.id.ccFlyersListView);
	}
	
	//INITIALIZATION
	private void initImageArray(){
		if(imageArray == null){
			imageArray = new int[NUMBER_OF_SUPERMARKETS];
		}
		
		imageArray[0] = R.drawable.da_fu;
		imageArray[1] = R.drawable.da_shi_jie;
		imageArray[2] = R.drawable.da_zhong_hua;
		imageArray[3] = R.drawable.t_t;
		imageArray[4] = R.drawable.ding_tai;
		imageArray[5] = R.drawable.duo_fu;
		imageArray[6] = R.drawable.feng_tai;
		imageArray[7] = R.drawable.fu_yao;
		imageArray[8] = R.drawable.guan_ye;
		imageArray[9] = R.drawable.hong_tai;
		imageArray[10] = R.drawable.hua_sheng;
		imageArray[11] = R.drawable.seasons;
		imageArray[12] = R.drawable.sunfood;
		imageArray[13] = R.drawable.sunny;
		imageArray[14] = R.drawable.xin_da;
		imageArray[15] = R.drawable.yuanming;
		imageArray[16] = R.drawable.bestco;
		imageArray[17] = R.drawable.food_basics;
		imageArray[18] = R.drawable.freshco;
		imageArray[19] = R.drawable.no_frills;
		imageArray[20] = R.drawable.price_chopper;
		imageArray[21] = R.drawable.galleria;
	}
	
	private void initListItem(){
		if(superMarketList == null){
			superMarketList = new ArrayList<SuperMarket>();
		}
		
		String[] marketName = getResources().getStringArray(R.array.supermarket_title_list);
		
		superMarketList.clear();
		for(int i=0; i<marketName.length; i++){
			SuperMarket superMarket = new SuperMarket(marketName[i]);
			superMarket.setBannerResId(imageArray[i]);
			superMarketList.add(superMarket);
		}
	}
	
	//PRIVATE METHOD
	private String getCurDate(){
		SimpleDateFormat df = new SimpleDateFormat("YYYYMMdd");
		return df.format(Calendar.getInstance().getTime());
	}
	
	private static boolean checkURLExists(String URLName){
	    try {
	      HttpURLConnection.setFollowRedirects(false);
	      // note : you may also need
	      //        HttpURLConnection.setInstanceFollowRedirects(false)
	      HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
	      con.setRequestMethod("HEAD");
	      return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
	    }
	    catch (Exception e) {
	       e.printStackTrace();
	       return false;
	    }
	}
	
	private boolean checkInternetConnection(){
		ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Service.CONNECTIVITY_SERVICE);
		
		if(cm!=null && cm.getActiveNetworkInfo()!=null && cm.getActiveNetworkInfo().isConnected()){
			return true;
		}
		
		return false;
	}
	
	private boolean checkRequiresUpdate(){
		int checkCount = 0;
		int[] tempArray = new int[superMarketList.size()];
		
		for(int i=0; i<superMarketList.size(); i++){
			if(!superMarketList.get(i).isUpdated()){
				tempArray[checkCount] = i;
				checkCount++;
			}
		}
		
		tempArray = null;
		if(checkCount > 0){
			checkArray = new int[checkCount];
			for(int i=0; i<checkCount; i++){
				checkArray[i] = tempArray[i];
			}
			return true;
		}else{
			return false;
		}
	}
	
	//ADAPTER
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
				
				holder.listItemImageView.setImageResource(singleSuperMarket.getBannerResId());
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
	
	//ASYNCTASK
	private class FlyersDownloader extends AsyncTask<Integer, Void, Boolean>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			progressBar = new ProgressDialog(MainActivityV2.this);
			progressBar.setMax(100);
			progressBar.setMessage("Downloading Flyers");
			progressBar.setCancelable(false);
			progressBar.setIndeterminate(false);
			progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressBar.show();
		}

		@Override
		protected Boolean doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			
			progressBar.dismiss();
			
			if(result){
				Toast.makeText(MainActivityV2.this, "更新完成！！", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(MainActivityV2.this, "更新失敗！！", Toast.LENGTH_SHORT).show();
			}
		}
		
		private void checkFlyerOnThumb(){
			
		}
		
		private boolean decodeDataFromServer(){
			
			
			return true;
		}
		
	}
	
	
	//LIFECYCLE
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
