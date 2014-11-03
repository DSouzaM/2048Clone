import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameWindow {
	static GameWindow gameWindow;

	JFrame gameFrame;
	JPanel tilePanel;
	JLabel scoreLabel;
	int[][] cells;
	ArrayList<Coordinates> coordinateList;
	static ArrayList<Coordinates> mergedNumList;

	Font cellFont = new Font(Font.SANS_SERIF, Font.PLAIN, 40);

	int score;

	public static void main(String[] args) {
		gameWindow = new GameWindow();
	}

	public GameWindow() {
		coordinateList = new ArrayList<Coordinates>();
		mergedNumList = new ArrayList<Coordinates>();
		score = 0;
		cells = new int[4][4];

		gameFrame = new JFrame("2048");
		gameFrame.setSize(500, 500);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setVisible(true);

		tilePanel = new TilePanel();

		gameFrame.add(tilePanel);
		gameFrame.addKeyListener(new MyKeyListener());
		addNewTwo();
	}

	void checkEmpty() {
		coordinateList.clear();
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (cells[x][y] == 0)
					coordinateList.add(new Coordinates(x, y));
			}
		}
	}

	boolean hasCoordinate(ArrayList<Coordinates> cList, int x, int y) {
		boolean has = false;
		for (Coordinates c : cList) {
			if (c.x == x && c.y == y) {
				has = true;
				break;
			}
		}
		return has;
	}

	void addNewTwo() {
		checkEmpty();
		int chosenCell = (int) (Math.random() * (coordinateList.size()));
		cells[coordinateList.get(chosenCell).x][coordinateList.get(chosenCell).y] = 2;
		tilePanel.repaint(); 
	}

	boolean moveUp() {
		boolean moved = false;
		for (int y = 1; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (cells[y][x] != 0 && cells[y - 1][x] == 0) {
					cells[y - 1][x] = cells[y][x];
					cells[y][x] = 0;
					moved = true;
					moveUp();
				} else if (cells[y][x] != 0 && cells[y - 1][x] == cells[y][x]
						&& !hasCoordinate(mergedNumList, x, y - 1)
						&& !hasCoordinate(mergedNumList, x, y)) {
					cells[y - 1][x] *= 2;
					cells[y][x] = 0;
					mergedNumList.add(new Coordinates(x, y - 1));
					moveUp();
					moved = true;
				}
			}
		}
		tilePanel.repaint();
		return moved;
	}

	boolean moveDown() {
		boolean moved = false;
		for (int y = 2; y >= 0; y--) {
			for (int x = 0; x < 4; x++) {
				if (cells[y][x] != 0 && cells[y + 1][x] == 0) {
					cells[y + 1][x] = cells[y][x];
					cells[y][x] = 0;
					moveDown();
					moved = true;
				} else if (cells[y][x] != 0 && cells[y + 1][x] == cells[y][x]
						&& !hasCoordinate(mergedNumList, x, y + 1)
						&& !hasCoordinate(mergedNumList, x, y)) {
					cells[y + 1][x] *= 2;
					cells[y][x] = 0;
					mergedNumList.add(new Coordinates(x, y + 1));
					moveDown();
					moved = true;
				}
			}
		}
		tilePanel.repaint();
		return moved;
	}

	boolean moveLeft() {
		boolean moved = false;
		for (int x = 1; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (cells[y][x] != 0 && cells[y][x - 1] == 0) {
					cells[y][x - 1] = cells[y][x];
					cells[y][x] = 0;
					moveLeft();
					moved = true;
				} else if (cells[y][x] != 0 && cells[y][x - 1] == cells[y][x]
						&& !hasCoordinate(mergedNumList, x - 1, y)
						&& !hasCoordinate(mergedNumList, x, y)) {
					cells[y][x - 1] *= 2;
					cells[y][x] = 0;
					mergedNumList.add(new Coordinates(x - 1, y));
					moveLeft();
					moved = true;
				}
			}
		}
		tilePanel.repaint();
		return moved;
	}

	boolean moveRight() {
		boolean moved = false;
		for (int x = 2; x >= 0; x--) {
			for (int y = 0; y < 4; y++) {
				if (cells[y][x] != 0 && cells[y][x + 1] == 0) {
					cells[y][x + 1] = cells[y][x];
					cells[y][x] = 0;
					moveRight();
					moved = true;
				} else if (cells[y][x] != 0 && cells[y][x + 1] == cells[y][x]
						&& !hasCoordinate(mergedNumList, x + 1, y)
						&& !hasCoordinate(mergedNumList, x, y)) {
					cells[y][x + 1] *= 2;
					cells[y][x] = 0;
					mergedNumList.add(new Coordinates(x + 1, y));
					moveRight();
					moved = true;
				}
			}
		}
		tilePanel.repaint();
		return moved;
	}

	class TilePanel extends JPanel {
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setFont(cellFont);
			g2d.clearRect(0, 0, 500, 500);
			g2d.setColor(Color.black);
			for (int x = 0; x < 400; x += 100) {
				for (int y = 0; y < 400; y += 100) {
					g2d.drawRect(x, y, 100, 100);
					int value = cells[y / 100][x / 100];
					if (value != 0) {
						int length = 0;
						for (int num = value; num > 0; num /= 10) {
							length++;
						}
						int hBuffer;
						switch (length) {
						case 1:
							hBuffer = 40;
							break;
						case 2:
							hBuffer = 30;
							break;
						case 3:
							hBuffer = 18;
							break;
						case 4:
							hBuffer = 7;
							break;
						default:
							hBuffer = 40;
							break;
						}
						g2d.setColor(Color.black);
						g2d.drawString(value + "", x + hBuffer, y + 70);
					}
				}
			}
		}
	}

	class MyKeyListener implements KeyListener {

		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				if (moveUp()) {
					mergedNumList.clear();
					addNewTwo();
				}
				break;
			case KeyEvent.VK_DOWN:
				if (moveDown()) {
					mergedNumList.clear();
					addNewTwo();
				}
				break;
			case KeyEvent.VK_LEFT:
				if (moveLeft()) {
					mergedNumList.clear();
					addNewTwo();
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (moveRight()) {
					mergedNumList.clear();
					addNewTwo();
				}
				break;
			default:
				break;
			}

		}

		public void keyReleased(KeyEvent arg0) {
		}

		public void keyTyped(KeyEvent arg0) {
		}
	}
}
