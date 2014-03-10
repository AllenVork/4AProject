

package com.ms.learn.util;

import java.util.WeakHashMap;

import android.graphics.Bitmap;

/**
 Õº∆¨ª∫¥Ê
 */
public class ImageCache extends WeakHashMap<String, Bitmap> {

	private static final long serialVersionUID = 1L;
	//∆•≈‰URL
	public boolean isCached(String url){
		return containsKey(url) && get(url) != null;
	}

}
