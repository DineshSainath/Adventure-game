
// Name: Dinesh Sainath Koti Reddy
// ID: 1025287

package adventure;
import java.util.Scanner;
import java.io.File;
import java.io.Reader;
import java.io.FileReader;
import java.lang.Object;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Game{
  
    public void defaultgame(Scanner scnr) {

        Adventure sampleGame = new Adventure();
        sampleGame.createRooms();
        sampleGame.gameplay(scnr);
    }

    public static JSONObject loadAdventureJson(String filename) {

        JSONObject adv_json;
        JSONParser parser = new JSONParser();
        try (Reader r = new FileReader(filename)) {
            adv_json = (JSONObject) parser.parse(r);
        } catch (Exception e) {
            System.out.println("Error finding file");
            adv_json = null;
        }

        return adv_json;
    }

    public static Adventure generateAdventure(JSONObject obj) {

        Adventure adv = new Adventure();

        JSONObject j = (JSONObject) obj.get("adventure");
        JSONArray jRooms = (JSONArray) j.get("room");

        if(j != null) {
            JSONArray Allrooms = (JSONArray) j.get("room");

            for(Object currRoom : Allrooms) {
                JSONObject room = (JSONObject) currRoom;
                String currName = (String)  room.get("name");
                String shortDesc = (String) room.get("short_description");
                String longDesc = (String) room.get("long_description");
                int roomID = ( (Number) room.get("id")).intValue();
        
                String startvalue= (String) room.get("start");
    
                JSONArray l = (JSONArray) room.get("loot");
                JSONArray e = (JSONArray) room.get("entrance");

                Room nextRoom = new Room();
                nextRoom.setRoomName(currName);
                nextRoom.setShortDescription(shortDesc);
                nextRoom.setLongDescription(longDesc);
                nextRoom.setRoomId(roomID);
                nextRoom.setStartValue(startvalue);

            /* for(Object ll : l ) {
                  System.out.println("test");
                  JSONObject loot = (JSONObject) ll;
                  System.out.println("test");
                  int lootid = ((Number) loot.get("id")).intValue();
                  System.out.println("test");
                  nextRoom.setLootId(lootid);
                  System.out.println("test");
              }*/
                
                for(Object currEntrance : e) {
                    JSONObject entrance = (JSONObject) currEntrance;
                    String dir = (String) entrance.get("dir");
                    int eID = ( (Number) entrance.get("id")).intValue();

                    nextRoom.setConnectedRoomID(eID);
                    nextRoom.setConnectRoomDirection(dir.trim());
                }
  
                adv.setAllRooms(nextRoom);

            }

            JSONObject j1 = (JSONObject) obj.get("adventure");
            JSONArray Allitems = (JSONArray) j1.get("item");
            for(Object thisItem : Allitems) {
                JSONObject item = (JSONObject) thisItem;
                String iname = (String) item.get("name");
                String idesc = (String) item.get("desc");
                Integer Itemid = ((Number) item.get("id")).intValue();
                
                Item jitem = new Item();
                jitem.setId(Itemid);
                jitem.setName(iname.trim());
                jitem.setItemDescription(idesc);

                adv.setAllItems(jitem);
            }
    }

    return adv;
        
    }

    public static void executeAdventure(Adventure adv, Scanner scnr){    
        int n = 1,index=0, index1 = 0,num=1,arraylength = 0;
        Room currRoom = adv.listAllRooms().get(index);
        Room secRoom = adv.listAllRooms().get(index1);

        System.out.println(currRoom.getShortDescription());
        while(n==1) {
            index1 = 0;
            scnr.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            String line = scnr.nextLine().trim();
            String[] input = line.split(" ");
            arraylength = input.length;
        if(input[0].equalsIgnoreCase("quit")) {
            System.out.println("Are you sure you want to quit? [yes/no]");
            scnr.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            String c = scnr.nextLine().trim();
            if(c.equalsIgnoreCase("yes")) {
                System.out.println("Thank you for playing. Goodbye.");
                n=0;
            } else {
                n = 1;
                System.out.println(currRoom.getShortDescription());
            }
        } else if(input[0].equalsIgnoreCase("go")) {
            if((input[1].equalsIgnoreCase("n")) ||(input[1].equalsIgnoreCase("e")) || (input[1].equalsIgnoreCase("w")) || (input[1].equalsIgnoreCase("s"))) {
                if(input[1].equalsIgnoreCase(currRoom.getConnectRoomDirection()) ) {
                    num = 1;
                    while(num == 1) {
                        if(currRoom.getConnectedRoomID() == secRoom.getRoomId() ) {
                           currRoom = secRoom;
                           num = 0;
                        }
                        index1++;
                        secRoom = adv.listAllRooms().get(index1);
                    }
                }
                System.out.println(currRoom.getShortDescription()); index1++;
            } else {
                System.out.println("Invalid input. Try again.");
            }
        } else if(arraylength>1 && input[0].equalsIgnoreCase("look")) {
            if(input[1].equalsIgnoreCase(currRoom.getItem().getName())) {
                Item foundItem = currRoom.getItem();
                System.out.println("You found a " + foundItem.getName());
                System.out.println(foundItem.getLongDescription());
            } else {
                System.out.println("There's no " + input[1] + " here");
            }
        } else if(input[0].equalsIgnoreCase("look")) {
            System.out.println(currRoom.getLongDescription());
        } else if(input[0].equalsIgnoreCase("help")) {
            System.out.println("You are in an estranged hotel. The hotel contains secret items in rooms.\n Your goal is to explore the hotel and find as many items as possible.\n Use the key word 'Go' to move followed by: \n'N' to go forward(north)\n 'W' to go Left(west). \n 'E' to go Right(east). \n 'S' to go backwards(south)\n 'look' for a longer description of the place\n help for instructions\n Example: go n\n");
        } else {
            System.out.println("Invalid move. Try again.");
        }

    }

    }


    public static void main(String args[]) {

        Scanner scnr = new Scanner(System.in);
        Game theGame = new Game();
        String path,subpath,newstr,choice,num, filename;
        File file;
        int index,n = 1,n0=1,n2=1;

        System.out.println("CIS 2430: Adventure 1.0");
        System.out.println("1. Play a default game \n2. Load a game from JSON file.\n3. Quit\nEnter your choice: "); 
        
        
        while( n2 == 1) {
            
        num = scnr.next();
        
        if( num.trim().equals("1") ) {
        System.out.println("Default game: LOADING....");
        System.out.println("Welcome to Hotel Adventure. Do you want instructions? [Yes/No]");
        while (n == 1 ) {
            choice = scnr.next();
        
            if (choice.equalsIgnoreCase("yes")) {
                System.out.println("You are in an estranged hotel. The hotel contains secret items in rooms.\n Your goal is to explore the hotel and find as many items as possible.\n Use the key word 'Go' to move followed by: \n'N' to go forward(north)\n 'W' to go Left(west). \n 'E' to go Right(east). \n 'S' to go backwards(south)\n 'look' for a longer description of the place\n help for instructions\n Example: go n\n");
                theGame.defaultgame(scnr);
                n = 0;
            } else if (choice.equalsIgnoreCase("no")) {
                System.out.println("Start game");
                theGame.defaultgame(scnr);
                n = 0;
            } else {
                System.out.println("Invalid input. Try again. [Yes/No]");
            }
        }
        n2 = 0;
        } else if( num.trim().equals("2") ) {

        file = new File("example_adventure.json");
        path = file.getAbsolutePath();
        index = path.indexOf("example_adventure.json");
        //System.out.println(index);
        subpath = "src\\main\\java\\adventure\\";
        System.out.println("Enter the json filename with extension");
        filename = scnr.next();
        newstr = path.substring(0, index) + subpath + filename;
        //System.out.println("new path: " + newstr);

        JSONObject load = new JSONObject();
        load = loadAdventureJson(newstr);
        Adventure newAdv = new Adventure();
        newAdv = generateAdventure(load);
   
        
        System.out.println("Loading game from JSON file");
        System.out.println("Do you want Instructions? [Yes/No]");
        
        while(n0 == 1) {
            String ch = scnr.next();
            if(ch.equalsIgnoreCase("yes")) {
                System.out.println("You are in an estranged hotel. The hotel contains secret items in rooms.\n Your goal is to explore the hotel and find as many items as possible.\n Use the key word 'Go' to move followed by: \n'N' to go forward(north)\n 'W' to go Left(west). \n 'E' to go Right(east). \n 'S' to go backwards(south)\n Example: go n\n");
                executeAdventure(newAdv, scnr);
                n0 = 0;
                
            } else if(ch.equalsIgnoreCase("no")) {
                System.out.println("Start game from JSON: ");
                executeAdventure(newAdv, scnr);
                n0 = 0;
            } else {
                System.out.println("Invalid input. Try again. [Yes/No]");
            }
        }
      n2 = 0;  
    } else if(num.trim().equals("3") ) {
        System.out.println("Goodbye.");
        n2 = 0;
    } else {
        System.out.println("Invalid input. Enter 1 or 2 or 3: ");
    }
    
    } //while
        
        scnr.close(); 
      } //main

} //class
