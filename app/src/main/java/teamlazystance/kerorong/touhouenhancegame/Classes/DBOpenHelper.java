package teamlazystance.kerorong.touhouenhancegame.Classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cps on 2018-03-29.
 */

public class DBOpenHelper extends SQLiteOpenHelper
{
    private static final int DB_VERSION = 1;
    private static final String DBFILE_CONTRACT = "gameData.db";

    public DBOpenHelper( Context context )
    {
        super(context, DBFILE_CONTRACT, null, DB_VERSION);
    }

    @Override
    public void onCreate( SQLiteDatabase db )
    {
        db.execSQL( DBManager.SQL_CREATE_TABLE );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
    {
        onCreate( db );
    }
}
