package adventure;
import java.util.Scanner;
import java.util.ArrayList;

public class Adventure{

    private Room entrance, lobby, lounge, pool, bar, spa,currentRoom;
    private Item bell, book, drink, empty;
    private ArrayList<Room> allRooms = new ArrayList<Room>();
    private ArrayList<Item> allItems= new ArrayList<Item>();

    public void setAllRooms(Room r) {

        allRooms.add(r);

    }

    public ArrayList<Room> listAllRooms(){
        return allRooms;

    }

    public void setAllItems(Item i) {

        allItems.add(i);
    }

    public ArrayList<Item> listAllItems(){
        return allItems;

    }

    public String getCurrentRoomDescription(){

        System.out.println("Room is empty");
        return " ";

    }

    public void createItems() {

        bell = new Item();
        book = new Item();
        drink = new Item();

        empty = new Item();

        empty.setName("");
        empty.setItemDescription("");

        bell.setName("Bell");
        bell.setItemDescription("This a bell at the receptionist table.");

        book.setName("Book");
        book.setItemDescription("Harry Potter and the Chamber of Secrets. This is the second part of the Harry Potter Series");

        drink.setName("Pop");
        drink.setItemDescription("A bottle of Nestea");

    }

    public void createRooms() {

        lobby = new Room();
        lounge = new Room();
        pool = new Room();
        bar = new Room();
        spa = new Room();
        entrance = new Room();

        createItems();

        entrance.setRoomName("Entrance");
        entrance.setShortDescription("You are at the main entrance");
        entrance.setLongDescription("You see two large glass doors. Move forward to enter inside.");
        entrance.setItem(empty);

        lobby.setRoomName("Lobby");
        lobby.setShortDescription("You are at the lobby.");
        lobby.setLongDescription("It's a huge decorated hall. There are no people around.\nThere's a bell on the table");
        lobby.setItem(bell);

        lounge.setRoomName("Lounge");
        lounge.setShortDescription("You are at the Lounge.");
        lounge.setLongDescription("This is a nice place to sit and relax. \nThere's a book on the table");
        lounge.setItem(book);

        pool.setRoomName("Swimming pool");
        pool.setShortDescription("You are at the swimming pool.");
        pool.setLongDescription("The pool is closed for maintainance. Come back later");
        pool.setItem(empty);

        bar.setRoomName("Bar");
        bar.setShortDescription("You are at the bar");
        bar.setLongDescription("There's loud music playing. A NBA game is running on the Television. \nThere is a pop drink on the counter");
        bar.setItem(drink);

        spa.setRoomName("Spa");
        spa.setShortDescription("You are at the spa.");
        spa.setLongDescription("There is no vacancy. Come back later.");
        spa.setItem(empty);


        entrance.setConnectedRoom(lobby);
        entrance.setConnectRoomDirection("n");

        lobby.setConnectedRoom(lounge);
        lobby.setConnectRoomDirection("e");
        lobby.setBackTraceRoom(entrance);

        lounge.setConnectedRoom(spa);
        lounge.setConnectRoomDirection("n");
        lounge.setBackTraceRoom(lobby);

        spa.setConnectedRoom(pool);
        spa.setConnectRoomDirection("w");
        spa.setBackTraceRoom(lounge);
        
        pool.setConnectedRoom(bar);
        pool.setConnectRoomDirection("n");
        pool.setBackTraceRoom(spa);

        bar.setBackTraceRoom(pool);

    }

    public void gameplay(Scanner scnr) {
        System.out.println("You are at the main entrance.");
        int n = 1, arraylength;
        currentRoom = entrance;

        while(n == 1) {

            scnr.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            String line = scnr.nextLine().trim();
            String[] input = line.split(" ");
            arraylength = input.length;

            if(input[0].equalsIgnoreCase("quit")) {
                System.out.println("Are you sure you want to quit? [yes/no]:");
                String choice = scnr.nextLine();
                if(choice.equalsIgnoreCase("yes")) {
                    System.out.println("Thank you for playing. Goodbye.");
                    n = 0;
                } else {
                    n = 1;
                    System.out.println(currentRoom.getShortDescription());
                }
            } else if(input[0].equalsIgnoreCase("go")) {
                    if((input[1].equalsIgnoreCase("n")) ||(input[1].equalsIgnoreCase("e")) || (input[1].equalsIgnoreCase("w")) ) {
                        if(input[1].equalsIgnoreCase(currentRoom.getConnectRoomDirection())) {
                            currentRoom = currentRoom.getConnectedRoom(input[1]);
                        }
                        System.out.println(currentRoom.getShortDescription());

                    } else if(input[1].equalsIgnoreCase("s")) {
                        if(!currentRoom.getName().equalsIgnoreCase("Entrance")) {
                            currentRoom = currentRoom.getBTRoom();
                        }
                        System.out.println(currentRoom.getShortDescription());
                    } else {
                        System.out.println("Invalid input. Try again");
                    }
                }

            else if(arraylength>1 && input[0].equalsIgnoreCase("look") ) {
                if(input[1].equalsIgnoreCase(currentRoom.getItem().getName())) {
                    Item foundItem = currentRoom.getItem();
                    System.out.println("You found a " + foundItem.getName());
                    System.out.println(foundItem.getLongDescription());
                } else {
                    System.out.println("There's no " + input[1] + " here");
                }
            } else if(input[0].equalsIgnoreCase("look")) {
                System.out.println(currentRoom.getLongDescription());
            } else if(input[0].equalsIgnoreCase("help")) {
                System.out.println("You are in an estranged hotel. The hotel contains secret items in rooms.\n Your goal is to explore the hotel and find as many items as possible.\n Use the key word 'Go' to move followed by: \n'N' to go forward(north)\n 'W' to go Left(west). \n 'E' to go Right(east). \n 'S' to go backwards(south)\n 'look' for a longer description of the place\n help for instructions\n Example: go n\n");
            } else {
                System.out.println("Invalid move. Try again.");
                }
               
        }  
    }
}
