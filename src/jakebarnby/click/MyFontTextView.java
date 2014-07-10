package jakebarnby.click;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import com.jakebarnby.click.R;

/**
 * A <code>TextView</code> that allows a resource font to be set in XML.
 * @author Jake Barnby
 *
 */
public class MyFontTextView extends TextView {

	public MyFontTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	public MyFontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public MyFontTextView(Context context) {
		super(context);
		init(null);
	}

	/**
	 * Creates and sets a type face using the XML declared font name and attributes
	 * @param attrs - The attributes to apply to the text view
	 */
	private void init(AttributeSet attrs) {
		if (attrs != null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyFontTextView);
			String fontName = a.getString(R.styleable.MyFontTextView_fontName);
			if (fontName != null) {
				Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
				setTypeface(myTypeface);
			}
			a.recycle();
		}
	}
}
