package com.joma.testclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RequestQueue mRequestQue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQue = Volley.newRequestQueue(this);
        Button send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage("+996552112195");
            }
        });
    }

    private void sendMessage(String driverNumber) {
        JSONObject json = new JSONObject();
        try {
            json.put("to", "/topics/" + driverNumber.replace("+", ""));
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title", "title");
            notificationObj.put("body", "body");
            //replace notification with data when went send data
            json.put("notification", notificationObj);

            String URL = "https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("-----", "onResponse: " + response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("------", "onError: " + error.networkResponse);
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=AAAAz8Aicns:APA91bG6_IQ1W_dO03xQ3yt-_fvaFI9gUWHGagqGOQRqzqcSxiTSESE8IUyTLWRILzT4QJ0TlRDgUh6jv-16miTJTkc6_El2U0mwR1txJzG5nP6Xix-siCYjG8Ur7qXDVvUWTQPFQdM9");
                    return header;
                }
            };


            mRequestQue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
