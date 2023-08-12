package manage;

public class Card {
    public static final int size = 5;
    
    private String name;
    private String address;
    private String phone;
    private String web;
    private String email;

    public Card(String[] initialData) {
        this.name = initialData[0];
        this.address = initialData[1];
        this.phone = initialData[2];
        this.web = initialData[3];
        this.email = initialData[4];
    }

    public String[] GetAsStringArray() {
        return new String[] {this.name, this.address, this.phone, this.web, this.email};
    }

    public void SetData(String[] newData) {
        this.name = newData[0];
        this.address = newData[1];
        this.phone = newData[2];
        this.web = newData[3];
        this.email = newData[4];
    }
}