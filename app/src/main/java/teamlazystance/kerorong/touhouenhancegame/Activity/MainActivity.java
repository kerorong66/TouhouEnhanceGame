package teamlazystance.kerorong.touhouenhancegame.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import teamlazystance.kerorong.touhouenhancegame.Classes.BaseActivity;
import teamlazystance.kerorong.touhouenhancegame.Classes.DBManager;
import teamlazystance.kerorong.touhouenhancegame.Classes.DBOpenHelper;
import teamlazystance.kerorong.touhouenhancegame.Classes.GlobalVariables;
import teamlazystance.kerorong.touhouenhancegame.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {
	private DBOpenHelper dbHelper = null;
	private GoogleSignInClient signInClient = null;
	private FirebaseAuth firebaseAuth;
	private FirebaseUser currentUser;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dbHelper = new DBOpenHelper(this);
		loadDBValues();
		
		Button gameStartButton = (Button)findViewById(R.id.btn_gamestart);
		gameStartButton.setOnClickListener(this);
		
		MobileAds.initialize(this, "ca-app-pub-8160485472590291~7745920168");
		GlobalVariables.INTERSTITIAL_AD = new InterstitialAd(this);
		
		GlobalVariables.INTERSTITIAL_AD.setAdUnitId("ca-app-pub-8160485472590291/7365402946");
		GlobalVariables.INTERSTITIAL_AD.loadAd(new AdRequest.Builder().build());
		signInClient = GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).requestServerAuthCode(getString(R.string.default_web_client_id)).build());
		firebaseAuth = FirebaseAuth.getInstance();
	}
	
	@Override
	public void onStart () {
		super.onStart();
		currentUser = firebaseAuth.getCurrentUser();
	}
	
	@Override
	public void onResume () {
		super.onResume();
		googleScilentSignIn();
	}
	
	private void googleScilentSignIn () {
		signInClient.silentSignIn().addOnCompleteListener(this, new OnCompleteListener<GoogleSignInAccount>() {
			@Override
			public void onComplete (@NonNull Task<GoogleSignInAccount> task) {
				if (task.isSuccessful()) {
					GlobalVariables.SIGNED_IN_ACCOUNT = task.getResult();
				} else {
					//Toast.makeText( MainActivity.this, "로그인이 필요합니다.", Toast.LENGTH_SHORT ).show();
				}
			}
		});
	}
	
	@Override
	public void onClick (View v) {
		if (GlobalVariables.SIGNED_IN_ACCOUNT != null || isSignedIn()) {
			GlobalVariables.SIGNED_IN_ACCOUNT = GoogleSignIn.getLastSignedInAccount(this);
			startGame();
		} else
			signInIntent();
	}
	
	private void startGame () {
		Intent intent = new Intent(this, InGameActivity.class);
		startActivity(intent);
	}
	
	private void signInIntent () {
		startActivityForResult(signInClient.getSignInIntent(), GlobalVariables.RC_SIGN_IN_GOOGLE);
	}
	
	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		if (requestCode == GlobalVariables.RC_SIGN_IN_GOOGLE) {
			Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
			
			try {
				GlobalVariables.SIGNED_IN_ACCOUNT = task.getResult(ApiException.class);
				startGame();
			} catch (ApiException apiException) {
				String message = apiException.getMessage();
				if (apiException.getStatusCode() == CommonStatusCodes.SIGN_IN_REQUIRED)
					message = "인증되지 않은 앱 또는 계정입니다.";
				
				if (message == null || message.isEmpty()) {
					message = getString(R.string.desc_signin_error);
				}
				new AlertDialog.Builder(this).setMessage(message).setNeutralButton(android.R.string.ok, null).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void loadDBValues () {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(DBManager.SQL_SELECT, null);
		
		if (cursor.moveToFirst()) {
			GlobalVariables.PLAYER_NAME = cursor.getString(0);
			GlobalVariables.PLAYER_SCORE = cursor.getInt(1);
			GlobalVariables.PLAYER_HIGHSCORE = cursor.getInt(2);
			GlobalVariables.PLAYER_MONEY = cursor.getInt(3);
		}
	}
}
