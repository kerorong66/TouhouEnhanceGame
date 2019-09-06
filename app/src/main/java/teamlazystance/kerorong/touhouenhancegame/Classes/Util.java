package teamlazystance.kerorong.touhouenhancegame.Classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by cps on 2018-03-30.
 */

public class Util
{
    public static void saveData( Context context )
    {
        DBOpenHelper dbHelper = new DBOpenHelper( context );
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL( DBManager.SQL_DELETE );

        String sqlInsert = DBManager.SQL_INSERT +
                " ('" +
                    GlobalVariables.PLAYER_NAME + "', " +
                    GlobalVariables.PLAYER_SCORE + ", " +
                    GlobalVariables.PLAYER_HIGHSCORE + ", " +
                    GlobalVariables.PLAYER_MONEY +
                ")";
        db.execSQL( sqlInsert );
    }

    public static double needPoint()
    {
        return GlobalVariables.LIST_NEEDPOINTS[GlobalVariables.PLAYER_SCORE];
    }

    public static double needPointNext()
    {
        return GlobalVariables.LIST_NEEDPOINTS[GlobalVariables.PLAYER_SCORE+1];
    }
}
