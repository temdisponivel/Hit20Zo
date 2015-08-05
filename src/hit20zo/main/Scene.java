package hit20zo.main;

import java.util.LinkedList;
import java.util.Random;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Scene that manage components of the game.
 * 
 * @author matheus
 */
public class Scene extends Activity implements OnClickListener, Runnable {
	private int _score = 0;
	private int _scorePerHit = 10;
	private int _scorePerMiss = 5;
	private int _20zoQuantity = 1;
	private float _speedChange = 1000f; //milisegundos
	private long _lastChange = 0;
	private int _hitQuantity = 0;
	private int _missQuantity = 0;
	private int _meta = 50;
	private TextView _lblScore = null;
	private TextView _lblHit = null;
	private TextView _lblMiss = null;
	private LinkedList<ImageButton> _holeWith20zo = new LinkedList<ImageButton>();
	private ImageButton[] _holes = new ImageButton[9]; // 0 = Top 1 | 8 = Bottom 3
	private Handler _handler = null;
	private MediaPlayer _mediaPlayer = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main_scene);
		this.FetchComponents();
		this.BindListner();
		this.Change20zo();
		_handler = new Handler();
		_mediaPlayer = new MediaPlayer();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		_lblScore.setText(String.valueOf(_score));
		_handler.post(this);
		_lastChange = SystemClock.uptimeMillis();
		_mediaPlayer = MediaPlayer.create(this, R.raw.au);
		for (int i = 0; i < _holes.length; i++) {
			_holes[i].setImageResource(R.drawable.hole);
		}
	}
	
	private void update() {
		long time = SystemClock.uptimeMillis(); 
		if (time - _lastChange >= _speedChange) {
			this.Change20zo();
			_lastChange = time;
		}
	}
	
	@Override
	public void onClick(View view) {
		boolean hit = false;
		for (ImageButton button : _holeWith20zo) {
			if (view == button) {
				_score += _scorePerHit;
				if (_speedChange - _scorePerHit > 500) {
					_speedChange -= 2.5;
				}
				if (_hitQuantity - _missQuantity > _meta) {
					_20zoQuantity++;
					_meta = _meta * 2;
				}
				hit = true;
				_hitQuantity++;
				_mediaPlayer.start();
				break;
			}
		}
		
		if (!hit) {
			_score -= _scorePerMiss;
			_missQuantity++;
		}
		
		_lblScore.setText(String.valueOf(_score));
		_lblHit.setText(String.valueOf(_hitQuantity));
		_lblMiss.setText(String.valueOf(_missQuantity));
	}
	
	public void Change20zo() {
		LinkedList<Integer> newHoles = new LinkedList<Integer>();
		Random random = new Random();
		int next = 0;
		for (int i = 0; i < _20zoQuantity; i++) {
			newHoles.add(random.nextInt(9));
		}
		
		for (ImageButton button : _holeWith20zo) {
			button.setImageResource(R.drawable.hole);
		}
		
		_holeWith20zo.clear();
		for (int i = 0; i < _holes.length; i++) {
			if (newHoles.contains(i)) {
				_holes[i].setImageResource(R.drawable.hole_with_20zo);
				_holeWith20zo.add(_holes[i]);
			}
		}
	}
	
	private void FetchComponents() {
		_lblScore = (TextView) this.findViewById(R.id.lbl_score);
		_lblHit = (TextView) this.findViewById(R.id.lbl_hit);
		_lblMiss = (TextView) this.findViewById(R.id.lbl_miss);
		_holes[0] = (ImageButton) this.findViewById(R.id.hole_top_1);
		_holes[1] = (ImageButton) this.findViewById(R.id.hole_top_2);
		_holes[2] = (ImageButton) this.findViewById(R.id.hole_top_3);
		_holes[3] = (ImageButton) this.findViewById(R.id.hole_mid_1);
		_holes[4] = (ImageButton) this.findViewById(R.id.hole_mid_2);
		_holes[5] = (ImageButton) this.findViewById(R.id.hole_mid_3);
		_holes[6] = (ImageButton) this.findViewById(R.id.hole_bottom_1);
		_holes[7] = (ImageButton) this.findViewById(R.id.hole_bottom_2);
		_holes[8] = (ImageButton) this.findViewById(R.id.hole_bottom_3);
	}
	
	private void BindListner() {
		for (int i = 0; i < _holes.length; i++) {
			_holes[i].setOnClickListener(this);
		}
	}
	
	public float getSpeedChange() {
		return _speedChange;
	}

	@Override
	public void run() {
		this.update();
		_handler.post(this);
	}
}
