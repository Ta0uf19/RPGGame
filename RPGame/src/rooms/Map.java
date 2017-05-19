package rooms;

import java.util.List;

import game.Game;

final public class Map {
	
	
	public static Room initmap[][];
	public static Room currentRoom;
	
	
	/**
	 * Check if coords roomX and roomY is not indexed out of range map / and if room is different null
	 * then change current room
	 */
	
	public static boolean checkDirection(int roomX, int roomY) {
		return  ((roomX >= 0 && roomX < initmap.length) && 
				(roomY >= 0 && roomY < initmap.length) && 
				initmap[roomX][roomY] != null);
	}
	
	/**
	 * Move direction
	 */
	public static void moveDirection(int roomX, int roomY) {
		if(checkDirection(roomX, roomY)) {
			/*
			 * Put in currentRoom with current (x,y) room checked
			 */
			currentRoom = initmap[roomX][roomY];
			
		}
	}
	
	/**
	 * getRoom from point
	 */
	
	public static Room getRoom(Point point) {
		return initmap[point.getX()][point.getY()];
	}
	
	/**
	 * getRoom from X and Y
	 */
	public static Room getRoom(int x, int y) {
		return initmap[x][y];
	}
	
	
}
