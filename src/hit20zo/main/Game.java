package hit20zo.main;

import java.util.Random;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Scene that manage components of the game.
 * 
 * @author matheus
 */
public class Game extends Scene implements OnClickListener {
	public long _lastChange = 0;
	private TextView _lblScore = null;
	private TextView _lblHit = null;
	private TextView _lblMiss = null;
	private Hole[] _holes = new Hole[9]; // 0 = Top 1 | 8 = Bottom 3
	private SoundPool soundPool;
	private int soundID;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.game);
		this.CreateHoles();
		this.FetchComponents();
		this.BindListner();
		this.UpdateLabels();
		_holes[new Random().nextInt(8)].setCharacter(true);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		_lblScore.setText(String.valueOf(Hit20zo.instance.score));
		_lastChange = SystemClock.uptimeMillis();
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
	    soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
	    soundID = soundPool.load(this, R.raw.tap, 1);
	}
	
	private void CreateHoles() {
		for (int i = 0; i < _holes.length; i++) {
			_holes[i] = new Hole();
			_holes[i].index = i;
		}	
	}
	
	@Override
	protected void update() {
		long time = SystemClock.uptimeMillis(); 
		if (time - _lastChange >= Hit20zo.instance.speedChange) {
			for (Hole hole : _holes) {
				if (hole.withCharacter) {
					this.Change(hole);
					break;
				}
			}
		}
		
		if (Hit20zo.instance.score < 0) {
			this.finish();
			this.startActivity(new Intent(this.getApplicationContext(), GameOver.class));
			this.update = false;
		}
	}
	
	@Override
	public void onClick(View view) {
		boolean hit = false;
		for (Hole hole : _holes) {
			if (view == hole.image && hole.withCharacter) {
				Hit20zo.instance.score += Hit20zo.instance.scorePerHit * (1000/(SystemClock.uptimeMillis() - _lastChange));
				if (Hit20zo.instance.speedChange - Hit20zo.instance.scorePerHit > 100) {
					Hit20zo.instance.speedChange -= 1;
				}
				Hit20zo.instance.hitQuantity++;
				if (Hit20zo.instance.sound) {
					this.PlaySound();
				}
				this.Change(hole);
				hit = true;
				break;
			}
		}
		
		if (!hit) {
			if (Hit20zo.instance.vibrate) {
				((Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(200);
			}
			Hit20zo.instance.score -= Hit20zo.instance.scorePerMiss;
			Hit20zo.instance.missQuantity++;
		}
		
		this.UpdateLabels();
	}
	
	public void UpdateLabels() {
		_lblScore.setText(String.valueOf((int)Hit20zo.instance.score));
		_lblHit.setText(String.valueOf(Hit20zo.instance.hitQuantity));
		_lblMiss.setText(String.valueOf(Hit20zo.instance.missQuantity));
	}
	
	
	public void Change(Hole hole) {
		_holes[this.fingFreeHole()].setCharacter(true);
		hole.setCharacter(false);
		_lastChange = SystemClock.uptimeMillis();
	}
	
	private int fingFreeHole() {
		Random random = new Random();
		int next = -1;
		while (_holes[next = random.nextInt(9)].withCharacter) {
			continue;
		}
		return next;
	}
	
	private void FetchComponents() {
		_lblScore = (TextView) this.findViewById(R.id.lbl_score);
		_lblHit = (TextView) this.findViewById(R.id.lbl_hit);
		_lblMiss = (TextView) this.findViewById(R.id.lbl_miss);
		_holes[0].image = (ImageView) this.findViewById(R.id.hole_top_1);
		_holes[1].image = (ImageView) this.findViewById(R.id.hole_top_2);
		_holes[2].image = (ImageView) this.findViewById(R.id.hole_top_3);
		_holes[3].image = (ImageView) this.findViewById(R.id.hole_mid_1);
		_holes[4].image = (ImageView) this.findViewById(R.id.hole_mid_2);
		_holes[5].image = (ImageView) this.findViewById(R.id.hole_mid_3);
		_holes[6].image = (ImageView) this.findViewById(R.id.hole_bottom_1);
		_holes[7].image = (ImageView) this.findViewById(R.id.hole_bottom_2);
		_holes[8].image = (ImageView) this.findViewById(R.id.hole_bottom_3);
	}
	
	private void BindListner() {
		for (Hole hole : _holes) {
			hole.image.setOnClickListener(this);
		}
	}
	
	@Override
	public void finish() {
		soundPool.release();
		super.finish();
	}
	
	private void PlaySound() {
		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = actualVolume / maxVolume;
		soundPool.play(soundID, volume, volume, 1, 0, 1f);
	}
}
