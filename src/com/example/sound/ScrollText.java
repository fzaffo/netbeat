package com.example.sound;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.widget.TextView;

public class ScrollText extends TextView{

	public ScrollText(Context context) {
		super(context);
		setSelected(true); 
		setEllipsize(TruncateAt.MARQUEE); 
		setSingleLine(true);
		this.setMarqueeRepeatLimit(-1);
	}

}
