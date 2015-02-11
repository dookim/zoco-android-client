package com.zoco.common;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by duhyeong1.kim on 2015-02-10.
 */
public class ReqTask extends AsyncTask<String, Void, String> {

    Context context;

    public ReqTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        String url = params[0];
        String data = params[1];
        Log.e(url, data);
        String result = "";
        try {
            result=new ZocoNetwork().setPostOption(url,data).execute();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IllegalAccessError(e.getMessage());
        }
        //Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        super.onPostExecute(result);
    }

}