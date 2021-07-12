//ENYULIU 18038125
//YIMENG GAO 18028190
package com.example.catchingandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainCharactorActivity extends AppCompatActivity {
    private String charactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_charactor);


        final ImageView imageViews[] = new ImageView[]{
                findViewById(R.id.character1),
                findViewById(R.id.character2),
                findViewById(R.id.character3),
                findViewById(R.id.character4),
        };
        Button button_c1 = findViewById(R.id.button1);
        button_c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                charactor = "a";
                imageViews[0].setImageResource(R.drawable.cover);
                imageViews[1].setImageResource(R.drawable.character2);
                imageViews[2].setImageResource(R.drawable.character3);
                imageViews[3].setImageResource(R.drawable.character4);
            }
        });
        Button button_c2 = findViewById(R.id.button2);
        button_c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                charactor = "b";
                imageViews[0].setImageResource(R.drawable.character1);
                imageViews[1].setImageResource(R.drawable.cover);
                imageViews[2].setImageResource(R.drawable.character3);
                imageViews[3].setImageResource(R.drawable.character4);
            }
        });
        Button button_c3 = findViewById(R.id.button3);
        button_c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                charactor = "c";
                imageViews[0].setImageResource(R.drawable.character1);
                imageViews[1].setImageResource(R.drawable.character2);
                imageViews[2].setImageResource(R.drawable.cover);
                imageViews[3].setImageResource(R.drawable.character4);
            }
        });
        Button button_c4 = findViewById(R.id.button4);
        button_c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                charactor = "d";
                imageViews[0].setImageResource(R.drawable.character1);
                imageViews[1].setImageResource(R.drawable.character2);
                imageViews[2].setImageResource(R.drawable.character3);
                imageViews[3].setImageResource(R.drawable.cover);
            }
        });
        Button button = findViewById(R.id.btn_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainCharactorActivity.this,MainActivity.class);
                CharacterData.character = charactor;
                startActivity(intent);

            }
        });
    }
}
