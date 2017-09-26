package com.example.administrator.seachman;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.seachman.adapter.LinkAdapter;
import com.example.administrator.seachman.bean.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private RecyclerView main_recycler;
    private MyRightView myRightView;
    private TextView main_tv;
    private Handler handler = new Handler();
    private ArrayList<Person> persons;
    private LinkAdapter linkadapter;
    private int childCount;
    private LinearLayoutManager linearmanger;
    private int childCount1;
    private String[] arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initview();
    }

    private void initview() {
        main_recycler = ((RecyclerView) findViewById(R.id.main_recycler));
        main_recycler.setLayoutManager(linearmanger = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        LinkAdapter linkadapter=new LinkAdapter(this,persons);
        main_recycler.setAdapter(linkadapter);
        myRightView = ((MyRightView) findViewById(R.id.main_myRight));
        main_tv = ((TextView) findViewById(R.id.main_tv));
        setTvWord();
    }

    private void setTvWord() {
        myRightView.setIndexPressWord(new MyRightView.IndexPressWord() {
            @Override
            public void setIndexPressWord(String word) {

                getWord(word);//让recycleview跳动到此字幕的位置
                main_tv.setVisibility(View.VISIBLE);
                main_tv.setText(word);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        main_tv.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        });
    }



    private void getWord(String word) {

        for (int i = 0; i < persons.size(); i++) {
            String substring = persons.get(i).getPinyin().substring(0, 1);
            if (substring.equals(word) && persons.size() >= i) {
                //如果person拼音的首位与word相同，则实现效果,并退出循环
                View childAt = main_recycler.getChildAt(i);
                MoveToPosition(linearmanger, main_recycler, i);
                break;
            }
        }
    }

    public void MoveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }

    }

    private void initData() {
        persons=new ArrayList<>();
        Resources resource=getResources();
        arr=resource.getStringArray(R.array.list_item);
        for (int i=0;i<arr.length;i++){
            persons.add(new Person(arr[i]));
        }



        //排序
        Collections.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person lhs, Person rhs) {
                return lhs.getPinyin().compareTo(rhs.getPinyin());
            }
        });

    }


}
