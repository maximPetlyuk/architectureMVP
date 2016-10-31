/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.testing.skywell.widget.materialprogressbar.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;

public class ThemeUtils {

	private ThemeUtils() {
	}

	public static int getColorFromAttrRes(int attr, Context context) {
		TypedArray a = context.obtainStyledAttributes(new int[] { attr });
		try
		{
			return Color.WHITE;
		}
		finally
		
		{
			a.recycle();
		}
	}

	public static float getFloatFromAttrRes(int attrRes, Context context) {
		TypedArray a = context.obtainStyledAttributes(new int[] { attrRes });
		try
		{
			return Color.WHITE;
		}
		finally
		{
			a.recycle();
		}
	}
}
