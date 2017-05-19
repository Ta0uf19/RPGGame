package game;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.*;

import com.google.gson.Gson;

import characters.*;
import items.*;
import rooms.*;
import rooms.Map;

public class Game {
	
	
	public static Scanner scan;
	public static String playerInput = null;
	public static Player player = null;
	
	public static void main(String[] args) {
		init();
		scan = new Scanner(System.in);

	}
	
	
	/*
	 * Initialise game (new game or continue game) + import data
	 */
	
	public static void init() {
		
		
		System.out.println("                  _                         ");
		System.out.println(" ___ ___ ___    _| |_ _ ___ ___ ___ ___ ___ ");
		System.out.println("|  _| . | . |  | . | | |   | . | -_| . |   |");
		System.out.println("|_| |  _|_  |  |___|___|_|_|_  |___|___|_|_|");
		System.out.println("    |_| |___|              |___|            ");
		System.out.println("		          Made with ♥ Taoufik");
		System.out.println("New Game = 1, Continue Game = 2");
		
		boolean loop = true;
		int input = 0;
        scan = new Scanner(System.in);
        
        while(loop) {
        	try {
        		input = scan.nextInt();
        		scan.nextLine();
        	}
        	catch (InputMismatchException e) {
        		scan.next();
        	}
        	
        	if (input == 1) {
                 startGame();
                 loop = false;
             }
             else if (input == 2) {
                 System.out.println("Sorry u can't choose this.");
                 //loop = false;
             }
             else {
                System.out.println("Input invalide");
             }
        }
		
	}
	
	/**
	 * Start game:
	 * 	- Set player
	 * 	- Command loop
	 * 	- Game over
	 */
	public static void startGame() {
		
		/*
		 * Set player
		 */
		boolean loop = true;
        
        do {
        	
        	System.out.println("Choose name for your character ? ");
            playerInput = scan.nextLine();
            if(playerInput.matches("\\d+")) {
            	System.out.println("Your name contains only numbers! Change it please.");
            }
            else {
            	loop = false;
            }
            	
        }
        while(loop);
        	
        player = new Player(playerInput);
        System.out.println("Welcome " + player.getName() + " ! Your new adventure is about to begin!");
        //showInfo(player);
        System.out.println("Write help to show futher info");
        
        /*
         * Starting games! creating rooms!
         */
    
        
        loadData();

        /*
         * Managing commands - game
         */
       
        
        
        while(!playerInput.equals("quit")) {
        	System.out.print("> ");
        	playerInput = scan.nextLine();
        	command(player);
        	
        	 if (player.isKilled()) {
                 System.out.println("You died on your journey ... GAME OVER");
                 
                 return;
           
             }
        }
        
		
	}
	/**
	 * Manage commands!
	 */
	public static void command(Player player) {
		/*
		 * south +1 0
		 * north -1 0
		 * east  0 +1
		 * west  0 -1
		 */
		Room lastRoom = Map.currentRoom;
		int currentRoomX = Map.currentRoom.getRoomCoord().getX();
		int currentRoomY = Map.currentRoom.getRoomCoord().getY();
		
		switch(playerInput) {
		case "help":
			showHelp();
			break;
		case "east":
			Map.moveDirection(currentRoomX, currentRoomY+1);
			showRoom(Map.currentRoom, lastRoom, "east");
			break;
		case "west":
			Map.moveDirection(currentRoomX, currentRoomY-1);
			showRoom(Map.currentRoom, lastRoom, "west");
			break;
		case "north":
			Map.moveDirection(currentRoomX-1, currentRoomY);
			showRoom(Map.currentRoom, lastRoom, "north");
			break;
		case "south":
			Map.moveDirection(currentRoomX+1, currentRoomY);
			showRoom(Map.currentRoom, lastRoom, "south");
			break;
		case "info":
			showInfo(player);
			break;
		case "inventory":
			showInventory();
			break;
		case "road":
			showRoad(Map.currentRoom);
			break;
		case "chest":
			openChest();
			break;
		case "healme":
			healPlayer();
			break;
		case "quit":
			System.out.println("You have left the game ! See you soon ");
            break;
		default: 
			System.out.println("Command not recognized! Check help");
			break;
		}
	}
	
	
	/**
	 * Commands methods
	 */
	
	public static void showHelp() {
		
		System.out.println("help - List of all commands");
		System.out.println("east, west, north, south - Moves around different rooms.");
		System.out.println("info - Shows player informations.");
		System.out.println("inventory - Shows equipments that you own.");
		System.out.println("road - Shows roads/rooms.");
		System.out.println("chest - Open a chest in your current room");
		System.out.println("healme - Use all health potion in your inventory");
		System.out.println("quit - Quits the game!");
	}
	public static void showInfo(Player player) {
		
        //System.out.println("Somes informations about you ! ");
        printLine();
        System.out.println("+  NAME : " + player.getName());
        System.out.println("+  HEALTH : " + player.getHealth());
        System.out.println("+  STRENGHT: " + player.getStrenght());
        System.out.println("+  XP : " + player.getXp());
        printLine();
	}
	
	public static void showInventory() {
		printLine();
		if(player.getInventory() != null && !player.getInventory().isEmpty()) {
			for(Item p : player.getInventory()) {
				System.out.println(p);
			}
		}else {
			System.out.println("No items!");
		}
		printLine();
	}
	
	/**
	 * Move to another room from currentRoom
	 *   - Check if currentRoom changed to another
	 *   - Check key for room to open it.
	 *   - If there are monsters - ask user to escape or attack. 
	 */
	public static void showRoom(Room currentRoom, Room lastRoom, String direction) {
		
		if(currentRoom != lastRoom) {
			
			/*
			 * Open 
			 */
			if(currentRoom.getKey() == null ||
			 currentRoom.getKey() != null && 
			 player.getInventory().contains(currentRoom.getKey())) {
				
				System.out.println("You head off to  " + currentRoom.getName() + "  in the " + direction);
				System.out.println(currentRoom.getDescription());
			
				
				if(currentRoom.getMonsters() != null && !currentRoom.getMonsters().isEmpty()) {
					/*
					 * UI Choice(Escape or Attack) - Launch and print attack on console!
					 */
					printAttacks(currentRoom);
					
				}
			}
			else {
				Map.currentRoom = lastRoom;
				System.out.println("You don't have access to this room! Find a key to open it. Maybe fighting monster? ;) ");
			}
			
			/*
			 * Chest
			 */
			if(currentRoom.getChest() != null) {
				System.out.println("There are a chest in this room!");
				System.out.println("You want to open it ? Check items ? Try 'chest' command ");
			}
			
		}
		else {
			System.out.println("You can't move to " + direction + " from this room");
		}
	}
	
	/**
	 * Show different locations from currentRoom
	 */
	public static void showRoad(Room currentRoom) {
		int currentRoomX = Map.currentRoom.getRoomCoord().getX();
		int currentRoomY = Map.currentRoom.getRoomCoord().getY();
		
		/*
		 * south
		 */
		if(Map.checkDirection(currentRoomX+1, currentRoomY)) {
			
			System.out.println("In south " + Map.initmap[currentRoomX+1][currentRoomY].getName());
		}
		/*
		 * north
		 */
		if(Map.checkDirection(currentRoomX-1, currentRoomY)) {
			
			System.out.println("In north "+Map.initmap[currentRoomX-1][currentRoomY].getName());
		}
		/*
		 * east
		 */
		if(Map.checkDirection(currentRoomX, currentRoomY+1)) {
	
			System.out.println("In east "+Map.initmap[currentRoomX][currentRoomY+1].getName());
		}
		/*
		 * west
		 */
		if(Map.checkDirection(currentRoomX, currentRoomY-1)) {
	
			System.out.println("In west "+Map.initmap[currentRoomX][currentRoomY-1].getName());
		}
		
	}
	
	
	
	/**
	 * Print line 
	 */
	
	public static void printLine() {
		System.out.println("+----------------------------------------+");
	}
	
	/**
	 * Launch attacks between two creature
	 */
	public static void launchAttacks(Creature attacker, Creature defender) {
		
		System.out.print(attacker.getName() + " attacks! ");
		int damage = attacker.attack(defender);
		if(damage == -1) {
			System.out.println("... but " + defender.getName() + " avoids the attack ! ");
		}
		else {
			System.out.println("... and reduce "+ defender.getName() + "'s HP by " + damage);
			defender.printStats();
		}
	}
	
	/**
	 * Prints attack on console
	 */
	public static void printAttacks(Room currentRoom) {
		
		System.out.println("There are monster(s) here ! Look out!");
		/*
		 * Choose attacking or escape
		 */
		boolean loop = true;
		int input;
		Scanner cl = new Scanner(System.in);
		boolean escape = false;
		
		/*
		 * get first monster! 
		 */
		Monster monster = currentRoom.getMonsters().get(0);
		
		do {
			System.out.println("You want : (1) Attack Monster (2) Try to Escape ? ");
			input = cl.nextInt();
			if(input == 1) {
				loop = false;
			}
			else if(input == 2) {
				escape = monster.isEscapable();
				if(escape == false) System.out.println("You're not lucky ! " + monster.getName() + " spotted you");
				loop = false;
			}
			else {
				System.out.println("Please choose one option! ");
			}
			
		} while(loop);
		
		if(!escape) {
			System.out.println("Start attacking " + monster.getName());
			printLine();
			while(!monster.isKilled() && !player.isKilled()) {
				launchAttacks(player, monster);
				/**
				 * Delete monster from room and break
				 */
				if(monster.isKilled()) {
					currentRoom.deleteMonsters(monster);
					System.out.println("You picked up all inventory of " + monster.getName() + "  - Check your inventory now!");
					break;
				}
				launchAttacks(monster, player);
			}
		}
		else {
			System.out.println("You're lucky ! You succeeded to escape from Monster!");
		}
		printLine();
	}
	
	
	public static void openChest() {
		Chest currentChest = Map.currentRoom.getChest();
		
		if(currentChest != null) {
			printLine();
			if(currentChest.getItems() != null && !currentChest.getItems().isEmpty()) {
				
				for(Item i : currentChest.getItems()) {
					System.out.println(i);
				}
			}
			else {
				System.out.println("No items in this chest");
			}
			printLine();
			
			/*
			 * Choose
			 */
			boolean loop = true;
			int inputChest;
			Scanner cl = new Scanner(System.in);
			do {
				System.out.println("You want : (1) Get all items (3) Return to game  ");
				inputChest = cl.nextInt();
				
				if(inputChest == 3) {
					System.out.println("You closed the chest");
					loop = false;
				}else if (inputChest == 1) {
					Iterator<Item> iter = currentChest.getItems().iterator();
					while(iter.hasNext()) {
					   Item i = iter.next();
					   player.setInventory(i);
					   System.out.println("You take " + i.getName() + " from chest");	
					   iter.remove();
					}
					loop = false;
				}
				else {
					System.out.println("please choose a number!");
				}
				
			} while(loop);
		} else {
			System.out.println("There are no chest in this room");
		}
	}
	
	public static void healPlayer() {
		
		int totalHealth = 0;
		
		Iterator<Item> iter = player.getInventory().iterator();
		while(iter.hasNext()) {
		   Item inv = iter.next();
		   if(inv instanceof HealthPotion) {
				totalHealth += ((HealthPotion) inv).getHealth();
				iter.remove();
			}
		}
		
		if(totalHealth == 0) {
			System.out.println("No health potion in your inventory");
		}
		else {
			
			System.out.println("You added health : " + totalHealth + " Check info!");
			player.setHealth(player.getHealth() + totalHealth);
		}
		
	}
	
	/**
	 * Load data 
	 */
	public static void loadData() {
		
		Room[][] maze = new Room[5][5];
        
        maze[0][2] = new Room("Archway", "The entrance of the hell!", new Point(0,2));
        maze[0][1] = new Room("Treasure Stone", "A circle of tall stones stands in the east side of the room, Someone has scrawled 'The dwarf will betray you'", new Point(0,1));
        maze[1][1] = new Room("Dwarf", "First Dwarf Monster Room", new Point(1,1));
        maze[2][0] = new Room("Treasure of dwarf", "First Monster Room", new Point(2,0));
        maze[2][1] = new Room("Lost Room", "The blood covered the floor of this Lost Room! Humain slit everywhere", new Point(2,1));
        maze[2][2] = new Room("Wyvern", "Third Wyvern Monster Room", new Point(2,3));
        maze[3][1] = new Room("Arkadia", "An altar of evil sits in the center of the room!", new Point(3,1));
        maze[3][2] = new Room("LWahch", "Third Monster Room", new Point(3,2)); 
        maze[3][3] = new Room("Tresure of the end!", "Look out! Evil Dead", new Point(3,3));
        maze[3][4] = new Room("Evil Dead", "Final Monster Room", new Point(3,4));
        maze[4][2] = new Room("Quiet", "Grave of Altar.. dead body", new Point(4,2));
        
        Map.initmap = maze;
		Map.currentRoom = maze[0][2];
       /*
        * Set Key
        */
        Map.getRoom(2,1).setKey(new Key("key_lost","This key is for Lost Room"));
        Map.getRoom(3,1).setKey(new Key("key_arkadia","This key is for Arkadia Room"));
        
        /*
         * Set Monsters
         */
        Monster dwarf = new Monster("Dwarf",200,0.65,40,0.25);
        dwarf.getInventory().add(Map.getRoom(2,1).getKey());
        Monster wyvern = new Monster("Wyvern",250,0.9,50,0.15);
        wyvern.getInventory().add(Map.getRoom(3,1).getKey());
        
        Map.getRoom(1,1).setMonster(dwarf);
        Map.getRoom(2,2).setMonster(wyvern);
        Map.getRoom(3,2).setMonster(new Monster("LWahch",300,0.8,70,0.10));
        Map.getRoom(3,4).setMonster(new Monster("Evil",400,1,100,0));
        
        /*
         * Set trasure chest
         */
        Chest chest1 = new Chest("Treasure stone", "You'll find all .. items here");
        chest1.setItem(new HealthPotion("Health Potion", "100 HP", 100));
        Map.getRoom(0, 1).setChest(chest1);
        
        Chest chest2 = new Chest("Blood Cofre", "You'll find all .. items here");
        chest2.setItem(new HealthPotion("Health Potion", "200 HP", 200));
        Map.getRoom(2, 0).setChest(chest2);
        
        Chest chest3 = new Chest("Treasure of king!", "You'll find all .. items here");
        chest2.setItem(new HealthPotion("Health Potion", "150 HP", 150));
        Map.getRoom(3, 3).setChest(chest2);
        
		
	}

}
