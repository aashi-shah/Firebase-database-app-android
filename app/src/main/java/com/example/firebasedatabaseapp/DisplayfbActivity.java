package com.example.firebasedatabaseapp;

import static android.os.Build.VERSION_CODES.P;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ObjectInputStream;

public class DisplayfbActivity extends AppCompatActivity {
    LoginButton loginButton;
    ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayfb);
        FacebookSdk.sdkInitialize(getApplicationContext());
        imageView = findViewById(R.id.imgfb);
        textView = findViewById(R.id.textfb);
        loginButton = findViewById(R.id.logoutfb);

        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {


            @Override
            public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                String name = "null";
                try {
                    name = jsonObject.getString("name");
                    textView.setText(name);
                    String img = jsonObject.getJSONObject("picture").getJSONObject("data").getString("url");
                    Picasso.get().load(img).into(imageView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("fields","id,name,link,picture.type(large)");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                Intent i = new Intent(DisplayfbActivity.this,FacebookloginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}