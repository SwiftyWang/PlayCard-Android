package com.example.pytestandroid;

public interface IPYViewTest {
	public static interface IPYCtrlTest {
		String	GetName();						//��ȡ����
		
		void	OnClickOut(byte[] cards);		//������ư�ť��ص�
	}
	
	void	InitView(IPYCtrlTest ctrl);			//��ʼ��
	
	void	OnDealCard(byte[] cards);			//�յ�����
	void	OnPlayCard();						//�յ�Ҫ����Ƶ�֪ͨ����ʱ��ʾ��ť
	void	OnOutCard(byte[] cards);			//�յ�����
	void	OnShowAnimation();					//��ʾ���ƶ���
	
}
