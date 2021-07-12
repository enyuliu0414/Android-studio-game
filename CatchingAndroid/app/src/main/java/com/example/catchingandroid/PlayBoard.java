package com.example.catchingandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Vector;

public class PlayBoard extends SurfaceView implements View.OnTouchListener,View.OnClickListener{

    private static int WIDTH = 90;
    private int food = 0;
    private static int block = 15;
    private static int col = 10;
    private static int row = 10;
    private Point matrix[][];
    private Point robot;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private String character;
    private boolean check_home = false;




    public PlayBoard(Context context,String c){
        super(context);
        character = c;
        System.out.println(c);
        getHolder().addCallback(callback);
        matrix = new Point[row][col];
        for(int i=0;i<row;i++){
            for(int j = 0;j<col;j++){
                matrix[i][j] = new Point(j,i);
            }
        }
        setOnTouchListener(this);
        initialGame();

    }
    private Point getPoint(int x,int y){
        return matrix[y][x];
    }
    private void initialGame(){
        food=0;
        for(int i=0;i<row;i++){
            for(int j = 0;j<col;j++){
                matrix[i][j].setStatus(Point.STATUS_OFF);
            }
        }
        robot = new Point(5,5);
        getPoint(5,5).setStatus(Point.STATUS_IN);
        for(int i=0;i<block;){
            int x,y;
            x = (int)((Math.random()*1000)%col);
            y = (int) ((Math.random()*1000)%row);
            if(getPoint(x,y).getStatus() == Point.STATUS_OFF){
                getPoint(x,y).setStatus(Point.STATUS_ON);
                i++;
            }
        }

    }
    private void drawDot(){
        Canvas c = getHolder().lockCanvas();
        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.bgbg);
        Bitmap bmp_home = BitmapFactory.decodeResource(getResources(),R.drawable.homenew);
        c.drawColor(Color.argb(255,255,239,213));
        //draw home button and resize it
        Matrix matrix_home = new Matrix();
        matrix_home.postScale(0.7f,0.7f);
        Bitmap dstbmp_home = Bitmap.createBitmap(bmp_home,0,0,bmp_home.getWidth(),bmp_home.getHeight(),matrix_home,true);
        c.drawBitmap(dstbmp_home,20,20,null);
        //resize the background whale
        Matrix matrix = new Matrix();
        matrix.postScale(0.5f,0.5f);
        Bitmap dstbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(),
                matrix, true);
        c.drawBitmap(dstbmp, 50, 0, null);

        for(int i =0;i<row;i++){
            int offset=0;
            if(i%2 == 0){
                offset = WIDTH/2;
            }
            for(int j=0;j<col;j++){
                Point circle = getPoint(j,i);
                switch (circle.getStatus()){
                    case Point.STATUS_IN:
                        if(food == 0) {
                            if(character == "d") {
                                c.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.player4), circle.getX() * WIDTH + offset - 25, circle.getY() * WIDTH + 425, paint);
                            }else if(character == "b"){
                                c.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.player3), circle.getX() * WIDTH + offset - 25, circle.getY() * WIDTH + 425, paint);

                            }else if(character == "c"){
                                c.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.player22), circle.getX() * WIDTH + offset - 25, circle.getY() * WIDTH + 425, paint);

                            }else{
                                c.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.player1), circle.getX() * WIDTH + offset - 25, circle.getY() * WIDTH + 425, paint);

                            }
                        }else {
                            c.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.chicken), circle.getX() * WIDTH + offset, circle.getY() * WIDTH + 490, paint);
                        }
                        break;
                    case Point.STATUS_OFF:
                        paint.setColor(0xFFB0C4DE);
                        c.drawCircle(75+circle.getX()*WIDTH+offset,600+circle.getY()*WIDTH,45,paint);
                        //c.drawOval(new RectF(circle.getX()*WIDTH+offset+20,circle.getY()*WIDTH+550,(circle.getX()+1)*WIDTH+offset+20,(circle.getY()+1)*WIDTH+550),paint);
                        break;
                    case Point.STATUS_ON:
                        paint.setColor(0xFFFFA500);
                        c.drawCircle(75+circle.getX()*WIDTH+offset,600+circle.getY()*WIDTH,45,paint);
                        break;
                }
            }
        }
        getHolder().unlockCanvasAndPost(c);

    }
    SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            drawDot();
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            WIDTH = i1/(col+1);
            drawDot();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    };
    public boolean onTouch(View view, MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            int x;
            int y =-1;
            if((e.getY()>=20)&&(e.getY()<= 112)&&(e.getX()>=20)&&(e.getX()<= 112)){check_home=true;}
            if(e.getY()>=555&&e.getY()<=1545){ y = (int) ((e.getY() - 555) / WIDTH);}
            if(y==-1){
//                Intent intent = new Intent(getContext(),MainActivity.class);
//                getContext().startActivity(intent);
                System.out.println("-1");
            }
            if (y%2 != 0) {
                x = (int) ((e.getX()-35)/WIDTH);
            }
            else {
                x = (int) ((e.getX()-35-WIDTH/2)/WIDTH);
            }
            if (x+1 > col || y<0) {
                if(check_home == true){
                    check_home = false;
                    Intent intent = new Intent(getContext(),MainActivity.class);
                    getContext().startActivity(intent);
                }else {
                    initialGame();
                }
            }else if(getPoint(x, y).getStatus() == Point.STATUS_OFF){
                getPoint(x, y).setStatus(Point.STATUS_ON);
                move();
            }
            drawDot();
        }
        return true;
    }
    private boolean isAtEdge(Point p) {
        if (p.getX() == 0 || p.getX()+1 == col || p.getY()+1 == row || p.getY() == 0) {
            return true;
        }
        return false;
    }
    private Point getAround(Point one,int direction) {
        switch (direction) {
            case 1:
                return getPoint(one.getX()-1, one.getY());
            case 2:
                if (one.getY()%2 != 0) {
                    return getPoint(one.getX()-1, one.getY()-1);
                }else {
                    return getPoint(one.getX(), one.getY()-1);
                }
            case 3:
                if (one.getY()%2 != 0) {
                    return getPoint(one.getX(), one.getY()-1);
                }else {
                    return getPoint(one.getX()+1, one.getY()-1);
                }
            case 4:
                return getPoint(one.getX()+1, one.getY());
            case 5:
                if (one.getY()%2 != 0) {
                    return getPoint(one.getX(), one.getY()+1);
                }else {
                    return getPoint(one.getX()+1, one.getY()+1);
                }
            case 6:
                if (one.getY()%2 != 0) {
                    return getPoint(one.getX()-1, one.getY()+1);
                }else {
                    return getPoint(one.getX(), one.getY()+1);
                }
            default:
                break;
        }
        return null;
    }
    private int getDistance(Point one,int dir) {
        int distance = 0;
        if (isAtEdge(one)) {
            return 1;
        }
        Point ori = one,next;
        while(true){
            next = getAround(ori, dir);
            if (next.getStatus() == Point.STATUS_ON) {
                return distance*-1;
            }
            if (isAtEdge(next)) {
                distance++;
                return distance;
            }
            distance++;
            ori = next;
        }
    }
    private void MoveTo(Point one) {
        one.setStatus(Point.STATUS_IN);
        getPoint(robot.getX(), robot.getY()).setStatus(Point.STATUS_OFF);;
        robot.setXY(one.getX(), one.getY());
    }
    private void move() {
        if (isAtEdge(robot)) {
            lose();return;
        }
        Vector<Point> avaliable = new Vector<>();
        Vector<Point> positive = new Vector<>();
        HashMap<Point, Integer> al = new HashMap<Point, Integer>();
        for (int i = 1; i < 7; i++) {
            Point n = getAround(robot, i);
            if (n.getStatus() == Point.STATUS_OFF) {
                avaliable.add(n);
                al.put(n, i);
                if (getDistance(n, i) > 0) {
                    positive.add(n);

                }
            }
        }
        if (avaliable.size() == 0) {
            win();
        }else if (avaliable.size() == 1) {
            MoveTo(avaliable.get(0));
        }else{
            Point best = null;
            if (positive.size() != 0 ) {
                int min = 999;
                for (int i = 0; i < positive.size(); i++) {
                    int a = getDistance(positive.get(i), al.get(positive.get(i)));
                    if (a < min) {
                        min = a;
                        best = positive.get(i);
                    }
                }
                MoveTo(best);
            }else {
                int max = 0;
                for (int i = 0; i < avaliable.size(); i++) {
                    int k = getDistance(avaliable.get(i), al.get(avaliable.get(i)));
                    if (k <= max) {
                        max = k;
                        best = avaliable.get(i);
                    }
                }
                MoveTo(best);
            }
        }
    }
    private void lose() {
        ShowDialog();
    }

    private void win() {
        food++;
        ShowDialogWin();
        Toast.makeText(getContext(), "You Win!", Toast.LENGTH_SHORT).show();

    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.exit:
                Toast.makeText(getContext(), "exit!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),MainActivity.class);
                getContext().startActivity(intent);

                alertDialog.dismiss();
                break;
            case R.id.retry:
                initialGame();
                drawDot();
                Toast.makeText(getContext(), "retry!", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                break;
        }
    }
    void ShowDialogWin(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage("Congratuations! you got it").setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                initialGame();
                drawDot();
                dialogInterface.dismiss();
            }
        })
         .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 dialogInterface.cancel();
             }
         });
        AlertDialog alter = builder.create();
        alter.show();
    }
    void ShowDialog(){

        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View layout = inflater.inflate(R.layout.my_dialog,null);

        TextView mRetry = (TextView) layout.findViewById(R.id.retry);
        TextView mExit = (TextView) layout.findViewById(R.id.exit);

        mExit.setOnClickListener(this);
        mRetry.setOnClickListener(this);
        builder = new AlertDialog.Builder(this.getContext());
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.show();
    }
}

