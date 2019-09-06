package teamlazystance.kerorong.touhouenhancegame.Classes;

import android.graphics.Color;

/**
 * Created by cps on 2018-04-03.
 */

public class ChatData
{
    private String userName;
    private String message;
    private int textColor;

    public ChatData( )
    {
    }

    public ChatData( String userName, String message, int textColor )
    {
        this.userName = userName;
        this.message = message;
        this.textColor = textColor;
    }

    public String getUserName( )
    {
        return userName;
    }

    public void setUserName( String userName )
    {
        this.userName = userName;
    }

    public String getMessage( )
    {
        return message;
    }

    public void setMessage( String message )
    {
        this.message = message;
    }

    public int getTextColor( )
    {
        return textColor;
    }

    public void setTextColor( int textColor )
    {
        this.textColor = textColor;
    }
}
