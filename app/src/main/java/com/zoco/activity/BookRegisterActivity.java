package com.zoco.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.zoco.obj.Item;

import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookRegisterActivity extends ActionBarActivity {

    CircleImageView circleImageView;
    TextView priceTV;
    TextView authorTV;
    TextView titleTV;
    NumberPicker numberPicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.book_register);

        Intent intent = getIntent();
        Item item = (Item) intent.getSerializableExtra(MainActivity.BOOK_INFO);

        //url to image
        circleImageView = (CircleImageView)findViewById(R.id.book_image);
        priceTV = (TextView)findViewById(R.id.price);
        authorTV = (TextView)findViewById(R.id.author);
        titleTV = (TextView)findViewById(R.id.title);
        numberPicker = (NumberPicker)findViewById(R.id.numberPicker);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setMinValue(0);

        String[] prices;

        if(item.price%500 == 0) {
            prices = new String[item.price/500+1];
            numberPicker.setMaxValue(item.price/500);
        } else {
            prices = new String[(item.price/500) + 2];
            numberPicker.setMaxValue((item.price/500) + 1);
            prices[(item.price/500)] = "0";
        }
        int i = 0;
        for(int price = item.price; price >= 0; price -= 500) {
            prices[i++] = price + "";
        }



        Toast.makeText(getBaseContext(),i + "",Toast.LENGTH_LONG).show();

        numberPicker.setDisplayedValues(prices);


        //Toast.makeText(getBaseContext(), ""+item.price,Toast.LENGTH_LONG).show();

        new DownloadImageTask(circleImageView)
                .execute(item.image);

        priceTV.setText(item.price + "");
        authorTV.setText(item.author);
        titleTV.setText(item.title);
		
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.register, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.register) {
            Toast.makeText(getBaseContext(), "register", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
