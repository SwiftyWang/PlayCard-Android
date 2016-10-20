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
        //������һ��������Ϣ
        m_nCardCout = 15;
        mView.OnDealCard(new byte[]{61, 55, 50, 49, 39, 34, 33, 1, 2, 7, 17, 18, 23, 65, 66});

        //����Ҫ�������Ϣ
        mView.OnPlayCard();
    }

    @Override
    public String GetName() {
        return "TESTNAME";
    }

    @Override
    public void OnClickOut(byte[] cards) {
        if (m_nCardCout <= 0)                //����ʱ������������·���
            Start();
        else {
            //���ͳ���
            mView.OnOutCard(cards);
            //�޸�����
            m_nCardCout -= cards.length;
            if (m_nCardCout <= 0)            //������
            {
                mView.OnShowAnimation();    //��ʾ��������
                mView.OnPlayCard();            //��ʾ��ť
            }
        }
    }

}

/*
 * ע�⣬���ļ���ֹ�޸�
*/