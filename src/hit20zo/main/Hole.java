package hit20zo.main;

import android.widget.ImageView;

public class Hole {
	public ImageView image = null;
	public int index = 0;
	public boolean withCharacter = false;
	
	public void setCharacter(boolean character) {
		if (character)
			this.setCharacter();
		else
			this.setHole();
	}
	
	private void setCharacter() {
		this.image.setImageResource(R.drawable.hole_with_20zo);
		this.withCharacter = true;
	}
	
	private void setHole() {
		this.image.setImageDrawable(null);
		this.withCharacter = false;
	}
}
