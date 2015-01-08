package com.faizanv.helloyelp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MenuActivity extends Activity
{
	private ArrayList<FoodItem> foods;
	private ListView menuList;
	private Firebase firebase;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		menuList = (ListView)findViewById(R.id.listView1);
		foods = new ArrayList<FoodItem>();
		
		Firebase.setAndroidContext(this);
		firebase = new Firebase("https://hello-yelp2.firebaseio.com/");
		firebase.addValueEventListener(new ValueEventListener()
		{
			@Override
			public void onCancelled(FirebaseError e) {}
			
			@Override
			public void onDataChange(DataSnapshot datas)
			{
				foods.clear();
				for(DataSnapshot data: datas.getChildren())
				{
					FoodItem food = new FoodItem();
					food.name = data.getName();
					food.price = (String) data.child("Price").getValue();
					
					float averageRating = 0;
					int numRatings = 0;
					for(DataSnapshot childData: data.getChildren())
					{
						if(childData.getName().contains("Rating"))
						{
							averageRating += Float.parseFloat(""+ childData.getValue());
							numRatings++;
						}
					}
					averageRating /= numRatings;
					food.rating = averageRating;
					foods.add(food);
				}
				setListData(foods);
			}
		});
	}
	
	private void setListData(ArrayList<FoodItem> data)
	{
		SimpleArrayAdapter listAdapter = new SimpleArrayAdapter(getApplicationContext(),foods);
		menuList.setAdapter(listAdapter);
	}
	
	private class SimpleArrayAdapter extends ArrayAdapter<FoodItem>
	{
		private Context context;
		private final List<FoodItem> items;
		
		public SimpleArrayAdapter(Context context,List<FoodItem> items)
		{
			super(context,R.layout.list_fooditem,items);
			this.context = context;
			this.items = items;
			map = new HashMap<RatingBar,String>();
			
		}
		FoodItem f;
		HashMap<RatingBar,String> map;
		public View getView(int position, View convertView, ViewGroup parent)
		{
			LayoutInflater inflater =
					(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row = inflater.inflate(R.layout.list_fooditem,parent,false);
			TextView foodName = (TextView) row.findViewById(R.id.textView1);
			TextView foodPrice = (TextView) row.findViewById(R.id.textView2);
			RatingBar foodRating = (RatingBar) row.findViewById(R.id.ratingBar1);
			f = items.get(position);
			map.put(foodRating,f.name);
			foodName.setText(f.name);
			foodPrice.setText(f.price);
			foodRating.setRating(f.rating);
			foodRating.setOnRatingBarChangeListener(new OnRatingBarChangeListener()
			{

				@Override
				public void onRatingChanged(RatingBar ratingBar1, float rating,
						boolean fromUser) {
					
					int x = (int)(Math.random()*10000000);
					firebase.child(map.get(ratingBar1)).child("Rating"+x).setValue(rating);
				}
				
				
			});
			
			return row;
		}
	}
	
	private class FoodItem
	{
		public String name;
		public String price;
		public float rating;
		
		public FoodItem(){}
	}
	
}
