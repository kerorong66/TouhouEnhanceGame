package teamlazystance.kerorong.touhouenhancegame.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import teamlazystance.kerorong.touhouenhancegame.Classes.BaseActivity;
import teamlazystance.kerorong.touhouenhancegame.Classes.ChatAdapter;
import teamlazystance.kerorong.touhouenhancegame.Classes.ChatData;
import teamlazystance.kerorong.touhouenhancegame.Classes.DBOpenHelper;
import teamlazystance.kerorong.touhouenhancegame.Classes.EnhanceManager;
import teamlazystance.kerorong.touhouenhancegame.Classes.GlobalVariables;
import teamlazystance.kerorong.touhouenhancegame.Classes.ShopDialogAdapter;
import teamlazystance.kerorong.touhouenhancegame.Classes.Util;
import teamlazystance.kerorong.touhouenhancegame.R;

/**
 * Created by cps on 2018-03-29.
 */

public class InGameActivity extends BaseActivity implements View.OnClickListener {
	private DBOpenHelper dbHelper = null;
	private List<String> titleItems = new ArrayList<>();
	private List<ChatData> chatItems = new ArrayList<>();
	private TextView moneyText, cardText, priceText, luckText;
	private Button shopButton, sellButton, enhanceButton, getMoneyButton;
	private EditText chatInput;
	private ListView chatList;
	private ShopDialogAdapter adapter;
	
	private InterstitialAd mInterstitialAd;
	
	private ChatAdapter chatAdapter;
	private FirebaseDatabase firebaseDatabase;
	private DatabaseReference databaseReference;
	
	private ChatData lastSendMessage;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ingame);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		
		init();
	}
	
	@Override
	public boolean onCreateOptionsMenu (Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_ingame, menu);
		return true;
	}
	
	private void init () {
		dbHelper = new DBOpenHelper(this);
		lastSendMessage = new ChatData("", "", 0);
		moneyText = (TextView)findViewById(R.id.txt_money);
		cardText = (TextView)findViewById(R.id.txt_card);
		priceText = (TextView)findViewById(R.id.txt_price);
		luckText = findViewById(R.id.txt_luck);
		shopButton = (Button)findViewById(R.id.btn_shop);
		sellButton = (Button)findViewById(R.id.btn_sell);
		enhanceButton = (Button)findViewById(R.id.btn_enhance);
		getMoneyButton = (Button)findViewById(R.id.btn_get_money);
		chatInput = (EditText)findViewById(R.id.et_chat);
		chatList = (ListView)findViewById(R.id.lv_chat);
		
		if (GlobalVariables.INTERSTITIAL_AD.isLoaded())
			GlobalVariables.INTERSTITIAL_AD.show();
		
		titleItems.add(getString(R.string.shop_default_card));
		titleItems.add("+6 " + getString(R.string.name_card));
		titleItems.add("+9 " + getString(R.string.name_card));
		titleItems.add("+11 " + getString(R.string.name_card));
		titleItems.add("+14 " + getString(R.string.name_card));
		adapter = new ShopDialogAdapter(this, titleItems, GlobalVariables.LIST_SHOP);
		
		if (GlobalVariables.PLAYER_NAME.length() <= 0) {
			final EditText nameInput = new EditText(this);
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
			dialogBuilder.setTitle(getString(R.string.title_set_nickname));
			dialogBuilder.setMessage(getString(R.string.desc_set_nickname));
			dialogBuilder.setView(nameInput);
			dialogBuilder.setPositiveButton(getString(R.string.name_confirm), new DialogInterface.OnClickListener() {
				@Override
				public void onClick (DialogInterface dialog, int which) {
					GlobalVariables.PLAYER_NAME = nameInput.getText().toString();
					Util.saveData(getApplicationContext());
				}
			});
			dialogBuilder.show();
		}
		
		GamesClient gamesClient = Games.getGamesClient(InGameActivity.this, GlobalVariables.SIGNED_IN_ACCOUNT);
		gamesClient.setViewForPopups(findViewById(R.id.lo_achievement));
		
		updateView();
		setListener();
	}
	
	private void updateView () {
		String cardName = getString(R.string.text_no_card), priceName = "", moneyName = getString(R.string.text_money) + GlobalVariables.PLAYER_MONEY + getString(R.string.name_current_coin);
		moneyText.setText(moneyName);
		
		if (GlobalVariables.PLAYER_SCORE > -1) {
			setEnhanceEnable(true);
			cardName = GlobalVariables.PLAYER_SCORE > 0 ? getString(R.string.name_plus) + (GlobalVariables.PLAYER_SCORE) : "";
			cardName += getString(R.string.text_card_with_blank);
			priceName = String.format("%,d", GlobalVariables.LIST_PRICES[GlobalVariables.PLAYER_SCORE]) + getString(R.string.name_current_coin);
		}
		priceText.setText(priceName);
		cardText.setText(cardName);
		
		chatAdapter = new ChatAdapter(this, chatItems);
		chatList.setAdapter(chatAdapter);
		
		firebaseDatabase = FirebaseDatabase.getInstance();
		databaseReference = firebaseDatabase.getReference();
	}
	
	private void sayChat (String userName, String message, int nameColor) {
		ChatData chatData = new ChatData(userName, message, nameColor);
		lastSendMessage = chatData;
		chatItems.add(chatData);
		chatAdapter.notifyDataSetChanged();
		chatScrollToEnd();
		databaseReference.child("message").push().setValue(chatData);
	}
	
	private void chatScrollToEnd () {
		chatList.post(new Runnable() {
			@Override
			public void run () {
				chatList.setSelection(chatList.getCount() - 1);
			}
		});
	}
	
	private void setListener () {
		shopButton.setOnClickListener(this);
		enhanceButton.setOnClickListener(this);
		sellButton.setOnClickListener(this);
		getMoneyButton.setOnClickListener(this);
		chatInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction (TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEND && chatInput.length() > 0) {
					sayChat(GlobalVariables.PLAYER_NAME, v.getText().toString(), Color.parseColor("#FFFF00"));
					v.setText("");
				}
				return false;
			}
		});
		databaseReference.child("message").addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded (DataSnapshot dataSnapshot, String s) {
				ChatData chatData = dataSnapshot.getValue(ChatData.class);
				if (chatData != null) {
					if (!chatData.getMessage().equals(lastSendMessage.getMessage()) && !chatData.getUserName().equals(lastSendMessage.getUserName())) {
						chatItems.add(chatData);
						chatAdapter.notifyDataSetChanged();
						chatScrollToEnd();
					}
				}
			}
			
			@Override
			public void onChildChanged (DataSnapshot dataSnapshot, String s) {
				
			}
			
			@Override
			public void onChildRemoved (DataSnapshot dataSnapshot) {
				
			}
			
			@Override
			public void onChildMoved (DataSnapshot dataSnapshot, String s) {
				
			}
			
			@Override
			public void onCancelled (DatabaseError databaseError) {
				
			}
		});
	}
	
	private void setEnhanceEnable (boolean isEnable) {
		sellButton.setVisibility(isEnable ? View.VISIBLE : View.GONE);
		enhanceButton.setEnabled(isEnable);
		shopButton.setEnabled(!isEnable);
	}
	
	private void showShop () {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle(getString(R.string.name_shop));
		dialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick (DialogInterface dialog, int which) {
				int price = GlobalVariables.LIST_PRICES[GlobalVariables.LIST_SHOP[which]];
				if (GlobalVariables.LIST_SHOP[which] > GlobalVariables.PLAYER_HIGHSCORE)
					Toast.makeText(InGameActivity.this, getString(R.string.desc_not_available), Toast.LENGTH_SHORT).show();
				else if (GlobalVariables.PLAYER_MONEY >= price) {
					if (which > 0)
						setUnlockAchievement(2);
					GlobalVariables.PLAYER_MONEY -= price;
					GlobalVariables.PLAYER_SCORE = GlobalVariables.LIST_SHOP[which];
					setEnhanceEnable(true);
					Util.saveData(getApplicationContext());
					updateView();
				} else
					Toast.makeText(InGameActivity.this, getString(R.string.desc_no_money), Toast.LENGTH_SHORT).show();
			}
		});
		dialogBuilder.setPositiveButton(getString(R.string.name_done), new DialogInterface.OnClickListener() {
			@Override
			public void onClick (DialogInterface dialog, int which) {
				
			}
		});
		dialogBuilder.show();
	}
	
	private void setUnlockAchievement (int id) {
		Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this)).unlock(getResources().getString(R.string.achievment_id_2));
	}
	
	@Override
	public void onClick (View v) {
		switch (v.getId()) {
			case R.id.btn_shop:
				showShop();
				break;
			
			case R.id.btn_enhance:
				if (GlobalVariables.PLAYER_SCORE > -1) {
					switch (EnhanceManager.enhance(luckText)) {
						case SUCCESS:
							Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this)).unlock(getResources().getString(R.string.achievment_id_1));
							GlobalVariables.PLAYER_SCORE++;
							if (GlobalVariables.PLAYER_SCORE > 14) {
								String name = getResources().getString(R.string.sys_name);
								String message = String.format(Locale.KOREA, getResources().getString(R.string.sys_huge_success_enhance), GlobalVariables.PLAYER_NAME, GlobalVariables.PLAYER_SCORE);
								sayChat(name, message, Color.parseColor("#00FFFF"));
							}
							if (GlobalVariables.PLAYER_SCORE > GlobalVariables.PLAYER_HIGHSCORE)
								GlobalVariables.PLAYER_HIGHSCORE = GlobalVariables.PLAYER_SCORE;
							break;
						
						case FAIL_BROKEN:
							Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this)).unlock(getResources().getString(R.string.achievment_id_3));
							if (GlobalVariables.PLAYER_SCORE == 0)
								Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this)).unlock(getResources().getString(R.string.achievment_id_0));
							String name = getResources().getString(R.string.sys_name);
							String message = String.format(Locale.KOREA, getResources().getString(R.string.sys_failed_enhance), GlobalVariables.PLAYER_NAME, GlobalVariables.PLAYER_SCORE);
							sayChat(name, message, Color.parseColor("#00FFFF"));
							GlobalVariables.PLAYER_SCORE = -1;
							setEnhanceEnable(false);
							break;
						
						case FAIL_PREV:
							GlobalVariables.PLAYER_SCORE--;
							break;
					}
					Util.saveData(this);
					updateView();
				}
				break;
			case R.id.btn_get_money:
				GlobalVariables.PLAYER_MONEY += 100;
				Util.saveData(this);
				updateView();
				break;
			case R.id.btn_sell:
				int addMoney = GlobalVariables.LIST_PRICES[GlobalVariables.PLAYER_SCORE];
				GlobalVariables.PLAYER_MONEY += addMoney;
				GlobalVariables.PLAYER_SCORE = -1;
				updateView();
				setEnhanceEnable(false);
				break;
		}
	}
	
	@Override
	public boolean onOptionsItemSelected (MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_achievements:
				showAchievements();
				return true;
			default:
				super.onOptionsItemSelected(item);
				return false;
		}
	}
	
	private void showAchievements () {
		Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this)).getAchievementsIntent().addOnSuccessListener(new OnSuccessListener<Intent>() {
			@Override
			public void onSuccess (Intent intent) {
				startActivityForResult(intent, GlobalVariables.RC_ACHIEVEMENT_UI);
			}
		});
	}
}
