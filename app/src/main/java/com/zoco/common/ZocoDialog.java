package com.zoco.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.ArrayList;

/**
 * Created by user on 2015-02-17.
 */
public class ZocoDialog {

    private Context mContext;
    ArrayList<String> mList;

    public ZocoDialog(Context context) {
        mContext = context;
    }

    public void setDialog(ArrayList<String> list) {
        AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
        ab.setTitle("Title");
        ab.setSingleChoiceItems(list.toArray(new String[list.size()]), 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Cancel 버튼 클릭시
                    }
                });
        ab.show();
    }

}
