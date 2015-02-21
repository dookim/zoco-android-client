package com.zoco.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.google.gson.Gson;
import com.zoco.common.ReqTask;
import com.zoco.common.ZocoConstants;
import com.zoco.common.ZocoDialog;
import com.zoco.common.ZocoHandler;
import com.zoco.common.ZocoNetwork;
import com.zoco.common.ZocoPreference;
import com.zoco.obj.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nara on 2/4/15.
 */
public class LoginActivity extends Activity {

    private static final String TAG = "NARA";
    private static final int REQUEST_CODE_RESOLVE_ERR = 9000;

    private UiLifecycleHelper uiHelper;
    private GraphUser user;

    private List<String> mFacebookPermits;
    private LoginButton facebookBtn;

    private String email;
    private String school;
    private String provider;
    private String password;

    private ZocoPreference pref;

    private enum Provider {
        facebook, google, kaako;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        pref = new ZocoPreference(this);

        provider = pref.getValue("provider", "0");


        if (!provider.equals("0")) {
            email = pref.getValue("email", "0");
            school = pref.getValue("school", "0");
            password = pref.getValue("password", "0");
            checkGetInfo(email, school);
            //로그인 한 적 있음
        }


        setContentView(R.layout.login_layout);

        if (getIntent() != null) {
            setLogOut();
        }

        initFaceBook();
    }

    private void initFaceBook() {
        facebookBtn = (LoginButton) findViewById(R.id.login_button);
        mFacebookPermits = Arrays.asList("public_profile",
                "user_education_history", "email");
        facebookBtn.setReadPermissions(mFacebookPermits);
    }

    @Override
    public void onResume() {
        super.onResume();
        Session session = Session.getActiveSession();
        if (session != null && (session.isOpened() || session.isClosed())) {
            onSessionStateChange(session, session.getState());
        }
        uiHelper.onResume();
    }

    private void onSessionStateChange(Session session, SessionState state) {
        if (state.isOpened()) {
            // Request user data and show the results
            Request.newMeRequest(session, new Request.GraphUserCallback() {

                public void onCompleted(GraphUser user, Response response) {
                    if (response != null) {
                        if (user != null) {
                            try {
                                response.getError();

                                email = user.getProperty("email").toString();
                                JSONArray education = (JSONArray) user
                                        .getProperty("education");
                                if (education.length() > 0) {
                                    for (int i = 0; i < education.length(); i++) {
                                        JSONObject edu_obj = education.optJSONObject(i);

                                        String type = edu_obj.optString("type");
                                        Log.i(TAG, type);
                                        if (type.equalsIgnoreCase("college")) {
                                            JSONObject school_obj = edu_obj
                                                    .optJSONObject("school");
                                            school = school_obj
                                                    .optString("name");

                                            password = createPassword(provider + email + school);

                                            pref.put("provider", Provider.facebook.name());
                                            pref.put("email", email);
                                            pref.put("school", school);
                                            pref.put("password", password);

                                            Log.d(TAG, "email : " + email);
                                            Log.d(TAG, "school : " + school);

                                            registerPostInfo(email, school, provider, password);
                                        }
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d(TAG, "error!!!!!");
                            }
                        }
                    }
                }

            }).executeAsync();
        } else if (state.isClosed()) {
            Log.i(TAG, " session is closed ");
        }
    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        public void call(Session session, SessionState state,
                         Exception exception) {
            if (session != null && session.isOpened()) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                onSessionStateChange(session, state);
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);

    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    private void setLogOut() {
        Session session = Session.getActiveSession();
        if (session != null && session.isOpened()) {
            Log.d(TAG, "logout ***");
            Session.getActiveSession().closeAndClearTokenInformation();

        }
    }

    Handler handler = new ZocoHandler() {
        @Override
        public void onReceive(String result) {
            if ("login success".equals(result)) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            if ("registered".equals(result) || "success".equals(result)) {
                loginPostInfo(email, provider, password);
            }

        }
    };

    private void checkGetInfo(String email, String school) {
        User user = new User(email, school);
        String userdata = new Gson().toJson(user);
        new ReqTask(getBaseContext(), ZocoNetwork.Method.GET).setHandler(handler).execute(ZocoNetwork.URL_4_REGISTER_BOOK + "is_register/", userdata);
    }

    private void registerPostInfo(String email, String school, String provider, String password) {
        User user = new User(email, school, provider, password);
        String userdata = new Gson().toJson(user);
        new ReqTask(getBaseContext(), ZocoNetwork.Method.POST).setHandler(handler).execute(ZocoNetwork.URL_4_REGISTER_BOOK + ZocoNetwork.SUFFIX_4_REGISTER_BOOK, userdata);
    }

    private void loginPostInfo(String email, String provider, String password) {
        User user = new User(email, provider, password);
        String userdata = new Gson().toJson(user);
        new ReqTask(getBaseContext(), ZocoNetwork.Method.POST).setHandler(handler).execute(ZocoNetwork.URL_4_REGISTER_BOOK + ZocoNetwork.SUFFIX_4_LOGIN, userdata);
    }

    private String createPassword(String str) {
        String pw = "";
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            pw = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            pw = null;
        }
        return pw;
    }

}
