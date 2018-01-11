package com.example.androidmajeure;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import static com.example.androidmajeure.R.id.*;



public class ContextManagementActivity extends AppCompatActivity {

    private String room;
    RoomContextState roomContextState ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context_management);

        ((Button) findViewById(buttonCheck)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                room = ((EditText) findViewById(editText1))
                        .getText().toString();
                retrieveRoomContextState(room);
            }
        });

        // For following copy paste, I deleted redundant castings
        findViewById(button1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                room = ((EditText) findViewById(editText1))
                        .getText().toString();
                switchLight(roomContextState, room);
                retrieveRoomContextState(room);

            }
        });







        findViewById(button1).setEnabled(false);
    }

    public void onUpdate(RoomContextState context) {
        roomContextState = context ;





        // maintenant que tu as récupéré ta room, tu vas pouvoir les utiliser tes boutons
        findViewById(button1).setEnabled(true);

        // On affiche l'icône qui va bien pour la lumière
        if (context.getLightStatus().equals("ON")) {
            ((ImageView) findViewById(R.id.imageView1)).setVisibility(View.VISIBLE);
            findViewById(R.id.imageView2).setVisibility(View.INVISIBLE);
        }
        else {
            findViewById(R.id.imageView1).setVisibility(View.INVISIBLE);
            findViewById(R.id.imageView2).setVisibility(View.VISIBLE);
        }



        // On affiche les valeurs numériques, on caste bizarrement psk ça permet d'avoir une espace après les deux points
        // le textView est ici obligé pour utiliser setText apparemment
        ((TextView) findViewById(textViewLightValue)).setText(" " + context.getLight());

    }


    // Functions calling results from HTTP Requests
    protected void retrieveRoomContextState(String room){
        RoomContextHttpManager.retrieveRoomContextState(room, this);
    }

    protected void switchLight(RoomContextState state, String room){
        RoomContextHttpManager.switchLight(this, state, room);
    }



    
}
