package teamlazystance.kerorong.touhouenhancegame.Classes;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by cps on 2018-04-03.
 */

public class CustomApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Typekit.getInstance()
                .addNormal( Typekit.createFromAsset(this, "NanumSquareR.ttf"))
                .addBold( Typekit.createFromAsset( this, "NanumSquareB.ttf" ));
    }
}
