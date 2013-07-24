package fam.tak0294.sample;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;


import fam.tak0294.sample.fragment.GraphFragment;
import fam.tak0294.sample.fragment.InputFragment;
import fam.tak0294.sample.fragment.TabFragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends SherlockFragmentActivity {

	static final String TAG = "MainActivity";
	
	Fragment m_inputFragment = null;
	Fragment m_graphFragment = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) { 
			//getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
		}
		
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		setContentView(R.layout.activity_main);
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
		
		if(savedInstanceState == null)
		{
			Log.i(TAG, "savedInstanceState == null");
			m_inputFragment = new InputFragment();
			m_graphFragment = new GraphFragment();
			
			FragmentManager manager = getSupportFragmentManager();
			FragmentTransaction transaction = manager.beginTransaction();
			
			transaction.add(R.id.content_fragment, m_inputFragment);
			transaction.add(R.id.tab_fragment, new TabFragment());
			
			transaction.commit();
		}
	}
	
	
	public void addFragment(int id)
	{
		System.out.println("ADD FRAGMENT CALL" + id);
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		
		if(id == 1)
		{
			transaction.replace(R.id.content_fragment, m_inputFragment);
		}
		else if(id == 2)
		{
			transaction.replace(R.id.content_fragment, m_graphFragment);
		}
		
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		//transaction.addToBackStack(null);
		transaction.commit();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		Log.i(TAG, "onSaveInstanceState");
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		System.out.println("[MainActivity] onPause");
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		System.out.println("[MainActivity] onResume");
	}
}
