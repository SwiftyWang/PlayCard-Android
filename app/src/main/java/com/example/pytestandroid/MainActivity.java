package com.example.pytestandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {
	private static MainActivity s_main = null;

	public static final MainActivity getInstance() {
		return s_main;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//===================���²��ֲ�Ӧ�޸�
		s_main = this;

		m_ctrl = new PYCtrlTest();
		m_view = new PYViewTest(this);
		m_ctrl.Init(m_view);

		setContentView(m_view);
		m_ctrl.Start();

		//===============================================
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private PYCtrlTest m_ctrl	=	null;
	private PYViewTest m_view 	= 	null;
}
/*�����޸ĳ��������ṹ
 * ���������Լ��������ӻ��޸�
 */

