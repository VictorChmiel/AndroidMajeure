package com.example.androidmajeure;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class RoomContextHttpManager {


    private static  RequestQueue queue = null ;
    public String room ;


    public static void retrieveRoomContextState(String room, final ContextManagementActivity contextManagementActivity){

        String url = "https://peaceful-beach-89392.herokuapp.com/api/Room/" + room;

        if(queue == null){
            queue = Volley.newRequestQueue(contextManagementActivity.getApplicationContext());
            queue.start();
        }

        //get room sensed context
        JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id = response.getString("id");
                            // the example was : int lightLevel = Integer.parseInt(response.getJSONObject("light").get("level").toString());
                            // but this seems better :D
                            int lightLevel = response.getJSONObject("light").getInt("level");
                            String lightStatus = response.getJSONObject("light").getString("status");

                            RoomContextState roomContextState = new RoomContextState(id, lightStatus, lightLevel);
                            contextManagementActivity.onUpdate(roomContextState);

                            // notify main activity for update

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(contextManagementActivity.getApplicationContext(), "ERROR accessing URL\nRoom may not exist\nInternet connection might be disabled", duration);
                        toast.show();
                    }
                });
        queue.add(contextRequest);
    };

    public static void switchLight(final ContextManagementActivity contextManagementActivity, final RoomContextState state, String room) {

        String url = "https://peaceful-beach-89392.herokuapp.com/api/Light/"+room+"/switch/light";

        /* No need to create the queue because the switch button is disabled if retrieveRoomContextState hasn't already been called
        if(queue == null){
            queue = Volley.newRequestQueue(contextManagementActivity.getApplicationContext());
            queue.start();
        }
        */

        // POST request, actualisation of context and getting of the response
        // You are supposed to use check button before switch button, otherwise, display could be wrong.
        StringRequest contextRequest = new StringRequest
                (Request.Method.POST, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        MqttManager.mqttSwitchLight();
                        if (state.getLightStatus().equals("ON")){
                            state.setLight("OFF");
                        }
                        else{
                            state.setLight("ON");
                        }
                        contextManagementActivity.onUpdate(state);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(contextManagementActivity.getApplicationContext(), "Unknown error\nHeroku?\nInternet?", duration);
                        toast.show();
                        // Some error to access URL : Room may not exists...
                    }
                });
        queue.add(contextRequest);
    };





}
