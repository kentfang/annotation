package com.galanz.annotation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.galanz.annotation.proxy.ITest;
import com.galanz.annotation.proxy.ProxyTestByInterface;
import com.galanz.annotation.proxy.ProxyTestByParent;
import com.galanz.annotation.proxy.Test;
import com.galanz.processors.GalanzBinder;
import com.galanz.processors.anno.BindView;
import com.galanz.processors.anno.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.text1)
    Button textView;

    @BindView(R.id.text2)
    Button textView2;

    @BindView(R.id.text3)
    Button textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GalanzBinder.bind(this);
        if (textView!=null){
            textView.setText("注入成功");
        }
        if (textView2!=null){
            textView2.setText("注入成功");
        }
        if (textView3!=null){
            textView3.setText("注入成功");
        }
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"R.id.text33333333333333333333",Toast.LENGTH_SHORT).show();
            }
        });
        textView2.setOnClickListener(this);
    }
    @OnClick({R.id.text1,R.id.text2,R.id.text3})
    public void click(View v){
        switch (v.getId()){
            case R.id.text1:
                Toast.makeText(this,"R.id.text1",Toast.LENGTH_SHORT).show();
                break;
            case R.id.text2:
                Toast.makeText(this,"R.id.text2",Toast.LENGTH_SHORT).show();
                break;
            case R.id.text3:
                Toast.makeText(this,"R.id.text3",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(MainActivity.this,"R.id.text2222222222222222222222222",Toast.LENGTH_SHORT).show();
    }



}
