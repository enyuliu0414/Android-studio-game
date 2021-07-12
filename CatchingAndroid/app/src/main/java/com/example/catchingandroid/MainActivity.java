package com.example.catchingandroid;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;

import com.bumptech.glide.Glide;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.gif.GifDrawable;


public class MainActivity extends AppCompatActivity {
    private String character = "a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //display animation
        ImageView rocketImage = findViewById(R.id.iv);
        rocketImage.setBackgroundResource(R.drawable.my_anim);
        AnimationDrawable rocketAnimation = (AnimationDrawable) rocketImage.getBackground();
        rocketAnimation.start();

        //get the data form another activity
        character = CharacterData.character;
        //initialize the play board
        final PlayBoard p = new PlayBoard(this,character);
        Button button = findViewById(R.id.button_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,setContentView(new PlayBoard(this,character)));
//                startActivity(intent);

                setContentView(p);
            }
        });
        Button button_character = findViewById(R.id.button_change);
        button_character.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MainCharactorActivity.class);
                startActivity(intent);

            }
        });

        //   setContentView(new PlayBoard(this));
    }
}
