package teamlazystance.kerorong.touhouenhancegame.Classes;

import android.content.Context;

/**
 * Created by cps on 2018-04-04.
 */

public class CardData
{
    private int id = -1;
    private int score = 0;

    public CardData( int id )
    {
        this.id = id;
    }

    public CardData( int id, int score )
    {
        this.id = id;
        this.score = score;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public int getId( )
    {
        return id;
    }

    public void setScore( int score )
    {
        this.score = score;
    }

    public int getScore( )
    {
        return score;
    }

    public String getName( Context context )
    {
        String resourceName = "@string/card_name_" + id;
        String packageName = context.getPackageName();
        int resourceId = context.getResources().getIdentifier( resourceName, "string", packageName );
        return context.getResources().getString( resourceId );
    }

    public String getDesc( Context context )
    {
        String resourceName = "@string/card_desc_" + id;
        String packageName = context.getPackageName();
        int resourceId = context.getResources().getIdentifier( resourceName, "string", packageName );
        return context.getResources().getString( resourceId );
    }
}
