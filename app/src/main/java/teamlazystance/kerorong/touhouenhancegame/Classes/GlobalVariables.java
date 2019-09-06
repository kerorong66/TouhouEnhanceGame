package teamlazystance.kerorong.touhouenhancegame.Classes;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by cps on 2018-03-30.
 */

public class GlobalVariables {
    public static int PLAYER_MONEY = 0;
    public static String PLAYER_NAME = "";
    public static int PLAYER_SCORE = 0;
    public static int PLAYER_HIGHSCORE = 0;
    public static final int RC_SIGN_IN_GOOGLE = 9001;
    public static final int RC_ACHIEVEMENT_UI = 9003;

    public static GoogleSignInAccount SIGNED_IN_ACCOUNT;
    public static InterstitialAd INTERSTITIAL_AD;

    public static double[] LIST_NEEDPOINTS = {
            0.90, 0.85, 0.80, 0.76, 0.72,
            0.68, 0.64, 0.60, 0.56, 0.52,
            0.50, 0.45, 0.40, 0.38, 0.36,
            0.35, 0.33, 0.30, 0.25, 0.20
    };
    public static int[] LIST_PRICES = {
            500, 550, 650, 800, 1000,
            1400, 1800, 2400, 3200, 5000,
            7200, 13000, 28000, 55000, 148000,
            323000, 726000, 2415000, 66782000, 199999999, 999999999
    };
    public static int[] LIST_SHOP = {
            0, 6, 9, 11, 14
    };
}
