package teamlazystance.kerorong.touhouenhancegame.Classes;

/**
 * Created by cps on 2018-03-29.
 */

public class DBManager
{
    private DBManager() {}

    public static final String TABLE_CONTRACT = "GAME_DATA_T";
    public static final String COL_NAME = "NAME";
    public static final String COL_SCORE = "SCORE";
    public static final String COL_HIGHSCORE = "HIGHSCORE";
    public static final String COL_MONEY = "MONEY";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CONTRACT +
            " (" +
                COL_NAME        +   " TEXT NOT NULL, "  +
                COL_SCORE       +   " INTEGER, "        +
                COL_HIGHSCORE   +   " INTEGER, "        +
                COL_MONEY       +   " INTEGER"          +
            ")";

    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_CONTRACT;
    public static final String SQL_SELECT = "SELECT * FROM " + TABLE_CONTRACT;
    public static final String SQL_INSERT = "INSERT OR REPLACE INTO " + TABLE_CONTRACT +
            " (" +
            COL_NAME + ", " +
            COL_SCORE + ", " +
            COL_HIGHSCORE + ", " +
            COL_MONEY +
            ") VALUES ";
    public static final String SQL_DELETE = "DELETE FROM " + TABLE_CONTRACT;
}
