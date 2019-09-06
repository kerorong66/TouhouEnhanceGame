package teamlazystance.kerorong.touhouenhancegame.Classes;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by cps on 2018-04-03.
 */

public class DisableScrollListView extends ListView {
	public DisableScrollListView (Context context) {
		super(context);
	}
	
	@Override
	public boolean dispatchTouchEvent (MotionEvent ev) {
		return ev.getAction() == MotionEvent.ACTION_MOVE || super.dispatchTouchEvent(ev);
	}
}
