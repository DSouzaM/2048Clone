public class Coordinates {
	int x;
	int y;

	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public String toString() {
		return new String(x + " " + y);
	}
}
