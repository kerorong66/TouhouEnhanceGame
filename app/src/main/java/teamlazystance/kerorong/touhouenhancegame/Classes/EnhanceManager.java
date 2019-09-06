package teamlazystance.kerorong.touhouenhancegame.Classes;

import android.widget.TextView;

/**
 * Created by cps on 2018-04-04.
 */

public class EnhanceManager {
	public enum RESULT {
		SUCCESS, FAIL_PREV, FAIL_BROKEN
	}
	
	public static RESULT enhance () {
		RESULT result = RESULT.SUCCESS;
		double random = Math.random();
		double needPoint = Util.needPoint();
		if (random <= needPoint)
			return result;
		random = Math.random();
		if (GlobalVariables.PLAYER_SCORE > 0 && random <= 0.5)
			result = RESULT.FAIL_PREV;
		else
			result = RESULT.FAIL_BROKEN;
		return result;
	}
	
	public static RESULT enhance (TextView luckText) {
		RESULT result = RESULT.SUCCESS;
		double random = Math.random();
		double needPoint = Util.needPoint();
		luckText.setText("다음 확률 값: " + Math.round(Util.needPointNext() * 100) + "\n나온 값: " + Math.round(random * 100) + " / 확률 값: " + Math.round(needPoint * 100));
		if (random <= needPoint)
			return result;
		random = Math.random();
		if (GlobalVariables.PLAYER_SCORE > 0 && random <= 0.5)
			result = RESULT.FAIL_PREV;
		else
			result = RESULT.FAIL_BROKEN;
		return result;
	}
}
