package fam.tak0294.sample.util;

import java.util.HashMap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;

public class BitmapUtil {
	private static final String TAG = "BitmapUtil";
	private static Context context;
	private static SparseArray<Bitmap> bitmapCache = new SparseArray<Bitmap>();
	
	public static Bitmap getBitmapById(Resources resource, int id, BitmapFactory.Options option)
	{
		if(bitmapCache.indexOfKey(id) < 0)
			bitmapCache.put(id, BitmapFactory.decodeResource(resource, id, option));
		return bitmapCache.get(id);
	}
}
