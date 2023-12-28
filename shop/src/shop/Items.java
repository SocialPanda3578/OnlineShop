package shop;

public class Items {
    private String ID = new String();
    private String Name = new String();
    private Double Price = 0.0;
    private Integer Number = 0;

    public Items(String ID,String Name,Double Price,Integer Number){
        this.ID = ID;
        this.Name = Name;
        this.Number = Number;
        this.Price = Price;
    }

    public Items(){

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Integer getNumber() {
        return Number;
    }

    public void setNumber(Integer number) {
        Number = number;
    }

    @Override
    public String toString() {
        return "Items{" +
                "ID='" + ID + '\'' +
                ", Name='" + Name + '\'' +
                ", Price=" + Price +
                ", Number=" + Number +
                '}';
    }
}
