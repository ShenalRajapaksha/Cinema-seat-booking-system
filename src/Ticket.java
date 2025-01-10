public class Ticket {
    private int row;
    private int seat;
    private double price;
    private Person person;

    public Ticket(int row, int seat, float price, Person person) {
        this.row = row;
        this.seat = seat;
        this.person = person;
        this.price = price;
    }

    public Ticket(int row, int seat, Person person){
        this.row = row;
        this.seat = seat;
        this.person = person;
    }

    public void setRow(int row){
        this.row = row;
    }

    public int getRow(){
        return row;
    }

    public void setSeat(int seat){
        this.seat = seat;
    }

    public int getSeat(){
        return seat;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getPrice(){
        return price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void print(){
        System.out.println("-------------------------");
        person.print();
        System.out.println("Row: "+row+"  Seat: "+seat);
        System.out.println("Price: £"+price);
        System.out.println("-------------------------");
    }
}
