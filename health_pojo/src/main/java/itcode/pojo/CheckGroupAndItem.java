package itcode.pojo;

public class CheckGroupAndItem {
    private int id;
    private int checkgtroupId;
    private int checkitemId;

    public CheckGroupAndItem(int id, int checkgtroupId, int checkitemId) {
        this.id = id;
        this.checkgtroupId = checkgtroupId;
        this.checkitemId = checkitemId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCheckgtroupId() {
        return checkgtroupId;
    }

    public void setCheckgtroupId(int checkgtroupId) {
        this.checkgtroupId = checkgtroupId;
    }

    public int getCheckitemId() {
        return checkitemId;
    }

    public void setCheckitemId(int checkitemId) {
        this.checkitemId = checkitemId;
    }
}
