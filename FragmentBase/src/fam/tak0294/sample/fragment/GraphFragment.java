package fam.tak0294.sample.fragment;

import fam.tak0294.sample.R;
import fam.tak0294.sample.R.id;
import fam.tak0294.sample.R.layout;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class GraphFragment extends Fragment {
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
	}

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
	}

	
	@Override
	public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);
    }
	
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{  
        // ��R������boolean��"container"��return����View��ǉ����邩�ǂ���  
        //true�ɂ���ƍŏI�I��layout�ɍēx�A����View group���\������Ă��܂��̂�false��OK�炵��  
		View view = inflater.inflate(R.layout.graph_fragment, container, false);

		return view;
    }  
}
