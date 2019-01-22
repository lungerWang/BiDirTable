package com.lunger.bidirtabledemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;

import com.lunger.bidirtabledemo.adapter.LvInfoAdapter;
import com.lunger.bidirtabledemo.adapter.LvNameAdapter;
import com.lunger.bidirtabledemo.view.LinkedHorizontalScrollView;
import com.lunger.bidirtabledemo.view.NoScrollHorizontalScrollView;

/**
 * Created by Allen on 2015/02/05 15:40
 */
public class MainActivity extends AppCompatActivity {

    private ListView mListViewName; // 底部左侧的ListView
    private ListView mListViewInfo; // 底部右侧的ListView

    boolean isLeftListEnabled = false;
    boolean isRightListEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAdapter();
    }


    private void initView() {
        // 不可滑动的顶部右侧的ScrollView
        NoScrollHorizontalScrollView goodsNameSV = (NoScrollHorizontalScrollView) findViewById(R.id.sv_title);
        // 底部右侧的ScrollView
        LinkedHorizontalScrollView infoContainer = (LinkedHorizontalScrollView) findViewById(R.id.sv_good_detail);
        mListViewName = (ListView) findViewById(R.id.lv_goods_name);
        mListViewInfo = (ListView) findViewById(R.id.lv_good_info);
        // 联动控件
        combination(mListViewName, mListViewInfo, goodsNameSV, infoContainer);
    }

    private void initAdapter() {
        mListViewName.setAdapter(new LvNameAdapter(this));
        mListViewInfo.setAdapter(new LvInfoAdapter(this));
    }

    private void combination(final ListView lvName, final ListView lvDetail, final HorizontalScrollView title, LinkedHorizontalScrollView content) {
        // 左右滑动同步
        content.setMyScrollChangeListener(new LinkedHorizontalScrollView.LinkScrollChangeListener() {
            @Override
            public void onScroll(LinkedHorizontalScrollView view, int x, int y, int oldx, int oldy) {
                title.scrollTo(x, y);
            }
        });

        // 上下滑动同步
        // 禁止快速滑动
        lvName.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        lvDetail.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        // 左侧ListView滚动时，控制右侧ListView滚动
        lvName.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 这两个enable标志位是为了避免死循环
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    isRightListEnabled = false;
                    isLeftListEnabled = true;
                } else if (scrollState == SCROLL_STATE_IDLE) {
                    isRightListEnabled = true;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                View child = view.getChildAt(0);
                if (child != null && isLeftListEnabled) {
                    lvDetail.setSelectionFromTop(firstVisibleItem, child.getTop());
                }
            }
        });

        // 右侧ListView滚动时，控制左侧ListView滚动
        lvDetail.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    isLeftListEnabled = false;
                    isRightListEnabled = true;
                } else if (scrollState == SCROLL_STATE_IDLE) {
                    isLeftListEnabled = true;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                View c = view.getChildAt(0);
                if (c != null && isRightListEnabled) {
                    lvName.setSelectionFromTop(firstVisibleItem, c.getTop());
                }
            }
        });

    }
}
