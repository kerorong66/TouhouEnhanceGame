package teamlazystance.kerorong.touhouenhancegame.Classes;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by cps on 2018-04-04.
 */

public class StrokeTextView extends AppCompatTextView
{
    public StrokeTextView( Context context )
    {
        super( context );
    }

    public StrokeTextView( Context context, @Nullable AttributeSet attrs )
    {
        super( context, attrs );
    }

    public StrokeTextView( Context context, @Nullable AttributeSet attrs, int defStyleAttr )
    {
        super( context, attrs, defStyleAttr );
    }

    @Override
    protected void onDraw( Canvas canvas )
    {
        ColorStateList states = getTextColors();

        getPaint().setStyle( Paint.Style.STROKE );
        getPaint().setStrokeWidth( 3.5f );
        setTextColor( Color.rgb( 0, 0, 0 ) );
        super.onDraw( canvas );

        getPaint().setStyle( Paint.Style.FILL );
        setTextColor( states );
        super.onDraw( canvas );
    }
}
