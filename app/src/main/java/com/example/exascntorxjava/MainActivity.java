package com.example.exascntorxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Button btn, btn2;
    static TextView textView,textView2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        textView = (TextView)findViewById(R.id.textview);
        textView2 = (TextView)findViewById(R.id.textview2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  new TestAsnc().execute();

                initObserval();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  new TestAsnc().execute();

                initObserval2();
            }
        });



    }

    public static void initObserval(){
       Observable<Long> ob = Observable.interval(1, TimeUnit.SECONDS)
               .flatMap( i -> Observable.just(i))
               .filter(count -> count <= 20)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread());
       ob.subscribe(i ->textView.setText(i.toString()+" 초"));
    }
    public static void initObserval2(){
        Observable<Long> ob = Observable.interval(1, TimeUnit.SECONDS)
                .flatMap( i -> Observable.just(i))
                .filter(count -> count <= 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        ob.subscribe(i ->textView2.setText(i.toString()+" 초"));
    }


    class TestAsnc extends AsyncTask<Integer,Integer,Integer>{

        private int value = 0 ;

        @Override
        protected Integer doInBackground(Integer... integers) {

            for(int i=0; i<100; i++){

                try {
                    Thread.sleep(1000);
                    publishProgress( i );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d("Test", "onProgressUpdate: "+values);
            textView.setText(values[0].intValue()+"");

        }
    }

}