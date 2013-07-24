package fam.tak0294.sample.cache;

import java.util.ArrayList;
import java.util.Random;

import fam.tak0294.sample.R;
import fam.tak0294.sample.util.BitmapUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;
import android.view.View;


public class CacheTray extends View {
	static final String TAG = "CacheTray";
	public static final String STORED_DATA_PREFIX = "CACHE_DATA"; 
	
	ArrayList<Cache> m_caches;
	Bitmap m_trayBitmap;
		
	public CacheTray(Context context)
	{
		super(context);
		m_caches = new ArrayList<Cache>();
		m_trayBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gra_dai);
		
	}
	
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		setMeasuredDimension(m_trayBitmap.getWidth(), m_trayBitmap.getHeight());
	}
	
	
	public void addCache(Cache cache)
	{
		m_caches.add(cache);
	}
	
	public int getSum()
	{
		int sum = 0;
		for(int ii=0;ii<m_caches.size();ii++)
		{
			sum += m_caches.get(ii).getAmount();
		}
		return sum;
	}
	
	@Override
	public void onDraw(Canvas canvas)
	{
		//canvas.drawBitmap(m_trayBitmap, 0, 0, null);
		drawCaches(canvas);
	}
	
	public void undo()
	{
		if(m_caches.size() == 0)
			return;
		
		m_caches.remove(m_caches.size()-1);
	}
	
	public void clearData()
	{
		m_caches.clear();
	}
	
	public void saveData(SharedPreferences out)
	{
		Log.i(TAG, "saveData");
		SharedPreferences.Editor editor = out.edit();
		for(int ii=0;ii<m_caches.size();ii++)
		{
			//m_cacheTray.addCache(new Cache(10000, R.drawable.gra_10000yen));
			Cache tmp = m_caches.get(ii);
			editor.putInt(CacheTray.STORED_DATA_PREFIX + "_amount" + ii, tmp.getAmount());
			editor.putInt(CacheTray.STORED_DATA_PREFIX + "_drawable" + ii, tmp.getResId());
		}
		editor.putInt(CacheTray.STORED_DATA_PREFIX + "_size", m_caches.size());
		editor.commit();
	}
	
	public void restoreData(SharedPreferences in)
	{
		Log.i(TAG, "restoreData");
		if(in.getInt(CacheTray.STORED_DATA_PREFIX + "_size", -1) != -1)
		{
			int size = in.getInt(CacheTray.STORED_DATA_PREFIX + "_size", -1);
			for(int ii=0;ii<size;ii++)
			{
				int amount   = in.getInt(CacheTray.STORED_DATA_PREFIX + "_amount" + ii, -1);
				int drawable = in.getInt(CacheTray.STORED_DATA_PREFIX + "_drawable" + ii, -1);
				if(amount != -1 && drawable != -1)
				{
					addCache(new Cache(amount, drawable));
				}
			}
		}
	}
	
	//------------------------------
	//	private methods.
	//------------------------------
	private void drawCaches(Canvas canvas)
	{
		Matrix m = new Matrix();
		Random rand = new Random(123456);
		
		int coinCount  = 0;
		int paperCount = 0;
		int coinColCount = 0;
		int paperColCount = 0;
		
		for(int ii=0;ii<m_caches.size();ii++)
		{
			Cache tmpCache = m_caches.get(ii);
			m.reset();
			int x = 0;
			int y = 0;
			
			
			BitmapFactory.Options option = new BitmapFactory.Options();
			
			//if(tmpBitmap != null)
			{
				if(tmpCache.getType() == Cache.TYPE.PAPER)
				{
					//m.postScale(0.2f, 0.2f);
					//option.inSampleSize = 2;
					x = 10 + paperColCount * 3;
					y = paperCount * 15 + 50;
					
					paperCount++;
					if(paperCount == 7)
					{
						paperCount = 0;
						paperColCount++;
					}
				}
				else
				{
					//m.postScale(0.5f, 0.5f);
					option.inSampleSize = 4;
					if(tmpCache.getAmount() == 500)
						x = 150;
					else if(tmpCache.getAmount() == 100)
						x = 160;
					else if(tmpCache.getAmount() == 50)
						x = 170;
					else if(tmpCache.getAmount() == 10)
						x = 180;
					else if(tmpCache.getAmount() == 5)
						x = 190;
					else
						x = 200;
					
					x += coinColCount*3;
					y = coinCount * 10 + 50;
					coinCount++;
					if(coinCount == 7)
					{
						coinCount = 0;
						coinColCount++;
					}
					
				}
				
				Bitmap tmpBitmap = BitmapUtil.getBitmapById(getResources(), tmpCache.getResId(), option);

				m.postTranslate(x, y);
				canvas.drawBitmap(tmpBitmap, m, null);
				//tmpBitmap.recycle();
				//tmpBitmap = null;
			}
		}
	}
}
