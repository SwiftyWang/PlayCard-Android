package com.example.pytestandroid;

import com.example.pytestandroid.IPYViewTest.IPYCtrlTest;

public class PYCtrlTest implements IPYCtrlTest {

    private IPYViewTest mView = null;
    private int m_nCardCout = 0;

    public PYCtrlTest() {
    }

    public void Init(IPYViewTest view) {
        mView = view;
        mView.InitView(this);
    }

    public void Start() {
        //发出第一个发牌消息
        m_nCardCout = 15;
        mView.OnDealCard(new byte[]{61, 55, 50, 49, 39, 34, 33, 1, 2, 7, 17, 18, 23, 65, 66});

        //发出要求出牌消息
        mView.OnPlayCard();
    }

    @Override
    public String GetName() {
        return "TESTNAME";
    }

    @Override
    public void OnClickOut(byte[] cards) {
        if (m_nCardCout <= 0)                //无牌时点击出牌则重新发牌
            Start();
        else {
            //发送出牌
            mView.OnOutCard(cards);
            //修改牌数
            m_nCardCout -= cards.length;
            if (m_nCardCout <= 0)            //无牌了
            {
                mView.OnShowAnimation();    //显示结束动画
                mView.OnPlayCard();            //显示按钮
            }
        }
    }

}

/*
 * 注意，本文件禁止修改
*/