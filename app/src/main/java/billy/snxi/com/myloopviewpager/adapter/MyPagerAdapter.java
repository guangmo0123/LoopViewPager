package billy.snxi.com.myloopviewpager.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * 自动循环和左右无限滑动Adapter
 */
public class MyPagerAdapter extends PagerAdapter {

    private List<ImageView> mList;
    private ImageView mImageView;
    private int mPageCount;
    private int mRealIndex;

    public MyPagerAdapter(List<ImageView> mList) {
        this.mList = mList;
        mPageCount = mList.size();
    }

    @Override
    public int getCount() {
        //将page总数返回整型最大，使之ViewPager可以无限向右滑动
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //将ViewPager的index转化为实际中Page的index
        mRealIndex = position % mPageCount;
        mImageView = mList.get(mRealIndex);
        container.addView(mImageView);
        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mRealIndex = position % mPageCount;
        mImageView = mList.get(mRealIndex);
        container.removeView(mImageView);
    }
}
