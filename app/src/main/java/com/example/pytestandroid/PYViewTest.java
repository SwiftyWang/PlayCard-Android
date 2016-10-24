package com.example.pytestandroid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PYViewTest extends FrameLayout implements IPYViewTest {

    private final int CARD_HEIGHT = getResources().getDimensionPixelSize(R.dimen.CARD_HEIGHT);
    private final int CARD_MARGIN = getResources().getDimensionPixelSize(R.dimen.CARD_MARGIN);
    private final int CARD_WIDTH = getResources().getDimensionPixelSize(R.dimen.CARD_WIDTH);
    private static final int DEFAULT_CARD_ANIMATION_DURATION = 300;
    private static final long DEFAULT_DEAL_CARD_INTERVAL = 250;
    private static final int MAX_OUT_CARD_NUMBER = 3;
    private static final String TAG = "PYViewTest";
    private final byte[] defaultSort = new byte[]{66, 65, 50, 2, 18, 34, 49, 1, 17, 33, 61, 55, 7, 23, 39};
    private List<ImageView> cardViews = new LinkedList<ImageView>();
    private IPYCtrlTest mCtrl = null;
    private FrameLayout my_card_container;
    private View outCard;

    public PYViewTest(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView();
    }

    public PYViewTest(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initView();
    }

    public PYViewTest(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        initView();
    }

    @Override
    public void InitView(IPYCtrlTest ctrl) {
        mCtrl = ctrl;
    }

    @Override
    public void OnDealCard(byte[] cards) {
        Log.i("TEST", "�յ�����,����:" + cards.length);
        //��ʱӦ��ʼ������ʾ�Լ��������
        //��������Լ�ʵ��
        outCard.setVisibility(INVISIBLE);
        outCard.setClickable(false);
        //remove all card in container
        cardViews.clear();
        my_card_container.removeAllViews();
        generateCardViews(cards);

    }

    @Override
    public void OnOutCard(byte[] cards) {
        Log.i("TEST", "�յ�����֪ͨ");
        //��ʱӦ����ʾ���ƣ�ͬʱ���Լ�����ɾ������
        //��������Լ�ʵ��
        if (cards == null || cards.length == 0) return;

        outCard.setVisibility(VISIBLE);
        outCard.setClickable(false);
        for (byte card : cards) {
            for (ImageView cardView : cardViews) {
                if (((Byte) cardView.getTag()).intValue() == card) {
                    animateCard(cardView);
                    cardViews.remove(cardView);
                    break;
                }
            }
        }
    }

    @Override
    public void OnPlayCard() {
        Log.i("TEST", "�յ�Ҫ�����֪ͨ");
        //��ʱӦ��ʾ���ư�ť�������ص�
        //��������Լ�ʵ��
        outCard.setVisibility(VISIBLE);
        outCard.setClickable(true);
    }

    @Override
    public void OnShowAnimation() {
        Log.i("TEST", "�յ���ʾ����֪ͨ");

        //��ʱӦ����ʾ�������Դ�������ͼƬΪ�ز���һ������������Ϊ���µ��Ϸɳ��������ҷɳ�����ըЧ���ȡ�
        //��������Լ�ʵ��
        try {
            //convert byte to integer and add extension to get the file name
            String fileName = String.valueOf("66.png");
            ImageView cardView = new ImageView(getContext());
            cardView.setImageBitmap(BitmapFactory.decodeStream(getContext().getAssets().open(fileName)));
            LayoutParams params = new LayoutParams(CARD_WIDTH, CARD_HEIGHT);
            cardView.setLayoutParams(params);
            cardView.setTag(66);
            my_card_container.addView(cardView);
            Animation animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT, -1.0f,
                    Animation.RELATIVE_TO_PARENT, 1.0f,
                    Animation.RELATIVE_TO_PARENT, -1.5f,
                    Animation.RELATIVE_TO_PARENT, -1.5f);
            animation.setDuration(2000);
            animation.setFillAfter(true);
            animation.setInterpolator(new LinearInterpolator());
            cardView.startAnimation(animation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToContainer(int index, ImageView cardView) {
        if (index >= my_card_container.getChildCount()) {
            my_card_container.addView(cardView);
        } else {
            my_card_container.addView(cardView, index);
        }
        int marginLeft = index * CARD_MARGIN;
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) cardView.getLayoutParams();
        p.setMargins(marginLeft, p.topMargin, p.rightMargin, p.bottomMargin);
        cardView.requestLayout();
        for (int i = my_card_container.getChildCount() - 1; i > index; i--) {
            ViewGroup.MarginLayoutParams mp = (MarginLayoutParams) my_card_container.getChildAt(i).getLayoutParams();
            mp.setMargins(mp.leftMargin + CARD_MARGIN, mp.topMargin, mp.rightMargin, mp.bottomMargin);
            my_card_container.getChildAt(i).requestLayout();
        }
    }

    private void addToContainer(ImageView cardView) {
        my_card_container.addView(cardView);
        int marginLeft = my_card_container.getChildCount() * CARD_MARGIN;
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) cardView.getLayoutParams();
        p.setMargins(marginLeft, p.topMargin, p.rightMargin, p.bottomMargin);
        cardView.requestLayout();
    }

    /**
     * �յ���������Ϣ����ʾ���ƣ����ƴ���ǰ���������Ķ�����
     *
     * @param cardView
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void animateCard(final ImageView cardView) {
        TranslateAnimation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.ABSOLUTE, Util.getScreenWidth((Activity) getContext()) / 3,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.ABSOLUTE, -((int) (cardView.getHeight() * 1.3) + findViewById(R.id.view_controller).getHeight()));
        anim.setFillAfter(true);
        anim.setDuration(DEFAULT_CARD_ANIMATION_DURATION);
        anim.setInterpolator(new LinearInterpolator());

        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1f, 0.8f, // Start and end values for the X axis scaling
                1f, 0.8f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(DEFAULT_CARD_ANIMATION_DURATION);
        scaleAnimation.setInterpolator(new LinearInterpolator());

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(anim);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setFillAfter(true);
        animationSet.setDuration(DEFAULT_CARD_ANIMATION_DURATION);
        animationSet.setInterpolator(new LinearInterpolator());

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                outCard.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {

            }
        });
        cardView.startAnimation(animationSet);
    }

    private int findSortIndexInList(Byte value) {
        for (int i = 0; i < cardViews.size(); i++) {
            if (getPosInDefaultSort((Byte) cardViews.get(i).getTag()) > getPosInDefaultSort(value)) {
                return i;
            }
        }
        return cardViews.size();
    }

    private int findValueIndex(byte b) {
        for (int i = 0; i < cardViews.size(); i++) {
            if (((Byte) cardViews.get(i).getTag()).intValue() == b) {
                return i;
            }
        }
        return -1;
    }

    private void generateCardViews(byte[] cards) {
        new AsyncTask<Byte[], Byte, Boolean>() {
            Byte[] bytes;

            @Override
            protected Boolean doInBackground(Byte[]... params) {
                bytes = params[0];
                for (int i = 0; i < bytes.length; i++) {
                    publishProgress(bytes[i]);
                    SystemClock.sleep(DEFAULT_DEAL_CARD_INTERVAL);
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
            }

            @Override
            protected void onProgressUpdate(Byte... value) {
                super.onProgressUpdate(value);
                Byte card = value[0];
                try {
                    //convert byte to integer and add extension to get the file name
                    String fileName = String.valueOf(card.intValue()) + ".png";
                    ImageView cardView = new ImageView(getContext());
                    cardView.setImageBitmap(BitmapFactory.decodeStream(getContext().getAssets().open(fileName)));
                    cardView.setLayoutParams(new FrameLayout.LayoutParams(CARD_WIDTH, CARD_HEIGHT));
                    cardView.setTag(card);
                    int index = findSortIndexInList(card);
                    cardViews.add(index, cardView);
                    //Log.e(TAG, index + " " + "K:" + findValueIndex((byte) 61) + "red7:" + findValueIndex((byte) 39));
                    addToContainer(index, cardView);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.execute(Util.convert(cards));
    }

    private int getPosInDefaultSort(Byte value) {
        for (int i = 0; i < defaultSort.length; i++) {
            if (defaultSort[i] == value.intValue()) {
                return i;
            }
        }
        return -1;
    }

    private byte[] getRandomCardsTag() {
        Random r = new Random(System.currentTimeMillis());
        // random this time out number
        int number = r.nextInt(MAX_OUT_CARD_NUMBER - 1) + 1;
        //fix number with total cards size.
        if (number > cardViews.size()) number = cardViews.size();
        byte[] out = new byte[number];
        for (int i = 0; i < number; i++) {
            while (true) {
                int random = r.nextInt(cardViews.size());
                ImageView card = cardViews.get(random);
                Byte tag = (Byte) card.getTag();
                boolean hasSame = false;
                for (byte anOut : out) {
                    if (tag.intValue() == anOut) {
                        hasSame = true;
                    }
                }
                if (!hasSame) {
                    out[i] = tag;
                    break;
                }
            }
        }
        return out;
    }

    private void initView() {
        setBackgroundResource(R.drawable.bg6);
        inflate(getContext(), R.layout.view_py_test, this);
        outCard = findViewById(R.id.out_card);
        my_card_container = (FrameLayout) findViewById(R.id.my_card_container);
        outCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCtrl.OnClickOut(getRandomCardsTag());
            }
        });
    }
}
