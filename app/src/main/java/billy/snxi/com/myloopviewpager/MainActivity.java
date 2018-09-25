package billy.snxi.com.myloopviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import billy.snxi.com.myloopviewpager.adapter.MyPagerAdapter;

/**
 * ViewPager可左右无限滑动，并且系统也会自动滑动
 */
public class MainActivity extends Activity {

    private ViewPager vp_banner;
    private TextView tv_banner_caption;
    private LinearLayout ll_banner_point;
    private int[] mImageIds;
    private List<ImageView> mImageViewList;
    private String[] mBannerCaption;
    private int mPreviousIndex;
    private int mPageCount;
    private int mRealIndex;
    private static Handler sHandler;
    private boolean isViewPagerAutoChange = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vp_banner = (ViewPager) findViewById(R.id.vp_banner);
        tv_banner_caption = (TextView) findViewById(R.id.tv_banner_caption);
        ll_banner_point = (LinearLayout) findViewById(R.id.ll_banner_point);
        mImageIds = getImageIds();
        initViewPagerData();
        vp_banner.setAdapter(new MyPagerAdapter(mImageViewList));
        vp_banner.setOnPageChangeListener(pageChangeListener);
        //将ViewPager的index设置为整型最大的中间，使之左和右均有无限的index可以用，这样即实现左右无限滑动
        int dafIndex = Integer.MAX_VALUE / 2;
        //将默认index设置为第一个page index上
        dafIndex = dafIndex - (dafIndex % mPageCount);
        vp_banner.setCurrentItem(dafIndex);
        //设置默认值
        tv_banner_caption.setText(mBannerCaption[0]);
        ll_banner_point.getChildAt(0).setEnabled(true);
        sHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //此处必须添加判断，因为退出activity后，会继续发送handle消息
                if (isViewPagerAutoChange) {
                    vp_banner.setCurrentItem(vp_banner.getCurrentItem() + 1);
                    sHandler.sendEmptyMessageDelayed(0, 2000);
                }
            }
        };
        sHandler.sendEmptyMessageDelayed(0, 2000);
    }

    private void initViewPagerData() {
        mPageCount = mImageIds.length;
        mImageViewList = new ArrayList<>();
        mBannerCaption = new String[]{
                "巩俐不低俗，我就不能低俗",
                "扑树又回来啦！再唱经典老歌引万人大合唱",
                "揭秘北京电影如何升级",
                "乐视网TV版大派送",
                "热血屌丝的反杀"};
        ImageView imageView;
        View view;
        for (int i = 0; i < mPageCount; i++) {
            imageView = new ImageView(this);
            imageView.setImageResource(mImageIds[i]);
            mImageViewList.add(imageView);
            //向ll_banner_point布局添加“点”
            view = new View(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(5, 5);
            layoutParams.leftMargin = 5;
            layoutParams.bottomMargin = 2;
            view.setLayoutParams(layoutParams);
            view.setBackgroundResource(R.drawable.selector_point);
            view.setEnabled(false);
            ll_banner_point.addView(view);
        }
    }

    private int[] getImageIds() {
        return new int[]{R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e};
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //将ViewPager的index转化为实际中Page的index
            mRealIndex = position % mPageCount;
            tv_banner_caption.setText(mBannerCaption[mRealIndex]);
            ll_banner_point.getChildAt(mRealIndex).setEnabled(true);
            ll_banner_point.getChildAt(mPreviousIndex).setEnabled(false);
            mPreviousIndex = mRealIndex;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onDestroy() {
        isViewPagerAutoChange = false;
        super.onDestroy();
    }
}
