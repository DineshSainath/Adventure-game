package adventure;
import java.util.*;

public class Room{

    private ArrayList<Item> items = new ArrayList<Item>();
    private String name;
    private String longDesc;
    private String shortDesc;
    private String roomDirection;
    private Room cRoom;
    private Room backtrace;
    private int roomId;
    private int lootId;
    private int connectRoomID;
    String sValue;
    
    public ArrayList<Item> listItems(){
        
        return items;
    }

    public void setItem(Item iname) {
        items.add(iname);   
    }

    public Item getItem() {
        return items.get(0);
    }

    /*public String getItemName() {

        Item found = items.get(0);
        return found.getName();
    }*/
    
    public void setRoomName(String rname) {
        name = rname;
    }

    public void setShortDescription(String s) {
        shortDesc = s;
    }

    public void setLongDescription(String d) {
        longDesc = d;
    }

    public String getName() {
        return name;

    }

    public String getLongDescription() {
        return longDesc;

    }

    public String getShortDescription() {
        return shortDesc;
    }

    public void setConnectedRoom(Room connectedroom) {
        cRoom = connectedroom;
    }

    public void setConnectedRoomID(int cID) {
        connectRoomID = cID;
    }

    public int getConnectedRoomID() {
        return connectRoomID;
    }

    public void setConnectRoomDirection(String dir) {
        roomDirection = dir;

    }

    public String getConnectRoomDirection() {
        return roomDirection;
    }

    public void setBackTraceRoom(Room backroom) {

        backtrace = backroom;
    }

    public Room getBTRoom() {
        return backtrace;
    }

    public void setRoomId(int givenId) {
        roomId = givenId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setLootId(int givenlootID) {
        lootId = givenlootID;
    }

    public int getLootId() {
        return lootId;
    }

    public Room getConnectedRoom(String direction) {

        if(roomDirection.equalsIgnoreCase(direction))
            return cRoom;
        
        return null;

    }

    public void setStartValue(String s) {
        sValue = s;
    }

    public String getStartValue() {
        return sValue;
    }

}
