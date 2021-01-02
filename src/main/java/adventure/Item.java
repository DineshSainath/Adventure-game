package adventure;

public class Item {
    private String name;
    private String desc;
    private Room x;
    private int id;

    public void setName(String itemname) {
        name = itemname;
    }

    public void setItemDescription(String itemdesc) {
        desc = itemdesc;
    }

    public String getName() {
        return name;

    }

    public String getLongDescription() {
        return desc;

    }

    public void setId(int ID) {
        id = ID;
    }

    public int getId() {
        return id;
    }

    public Room getContainingRoom() {
        x = new Room();
        return x;
    }

}
