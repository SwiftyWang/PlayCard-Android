package com.example.pytestandroid;

public interface IPYViewTest {
	public static interface IPYCtrlTest {
		String	GetName();						//获取名字
		
		void	OnClickOut(byte[] cards);		//点击出牌按钮后回调
	}
	
	void	InitView(IPYCtrlTest ctrl);			//初始化
	
	void	OnDealCard(byte[] cards);			//收到发牌
	void	OnPlayCard();						//收到要求出牌的通知，此时显示按钮
	void	OnOutCard(byte[] cards);			//收到出牌
	void	OnShowAnimation();					//显示出牌动画
	
}
