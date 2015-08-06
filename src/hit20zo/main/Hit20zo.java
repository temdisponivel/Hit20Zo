package hit20zo.main;

public class Hit20zo {
	static public Hit20zo instance = null;
	public float score = 0;
	public float scorePerHit = 10;
	public int scorePerMiss = 5;
	public float speedChange = 1000f; //milisegundos
	public int hitQuantity = 0;
	public int missQuantity = 0;
	public boolean sound = true;
	public boolean vibrate = true;
	
	public Hit20zo(){
		if (instance == null)
			instance = this;
	}
	
	public void Reset() {
		score = 0;
		scorePerHit = 10;
		scorePerMiss = 5;
		speedChange = 1000f; //milisegundos
		hitQuantity = 0;
		missQuantity = 0;
		sound = true;
		vibrate = true;
	}
}
