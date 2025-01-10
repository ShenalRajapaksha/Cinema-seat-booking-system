import java.util.*;
public class Cinema {
    public static void main(String[] args) {
        System.out.println("Welcome to the New London Cinema");
        int [][] seats = new int[3][20];
        Ticket[] ticketList = new Ticket[60];

        //The counter variable is used to maintain the number of tickets added to the ticket array
        //such that new tickets are added to the end of the array
        int counter = 0;
        int userChoice;
        Scanner input = new Scanner(System.in);

        do {
            System.out.print("""
                ------------------------------
                1) Buy a ticket
                2) Cancel a ticket
                3) Print seating area
                4) List available seats
                5) Print person information
                6) Print ticket information and total price
                7) Sort tickets by price
                8) Quit
                ------------------------------
                
                Enter option:\s""");
            userChoice = input.nextInt();
            switch (userChoice) {
                case 0 -> System.exit(0);
                case 1 -> counter = buy_ticket(seats, input, ticketList, counter);
                case 2 -> cancel_ticket(seats, input, ticketList);
                case 3 -> print_seating_area(seats);
                case 4 -> show_available(seats);
                case 5 -> show_person_info(ticketList, input);
                case 6 -> calculate_price(ticketList);
                case 7 -> sort_tickets(ticketList);
            }
        }while (true);
    }


    public static int buy_ticket(int[][] array, Scanner input, Ticket[] ticketList, int counter){
        // This method is used to reserve a seat in the theatre. It toggles the values of the array
        // between 0 and 1 for the seating arrangement and adds tickets to array of tickets.
        Person person = new Person();
        System.out.print("Enter full name: ");
        person.setName(input.next());
        person.setSurname(input.next());
        System.out.print("Email: ");
        person.setEmail(input.next());
        int[] seatingDetails = seat_validator(input);
        int rowNum = seatingDetails[0];
        int seatNum = seatingDetails[1];

        Ticket ticket = new Ticket(rowNum,seatNum,person);
        switch (rowNum) {
            case 1 -> ticket.setPrice(15.0);
            case 2 -> ticket.setPrice(12.0);
            case 3 -> ticket.setPrice(10.0);
        }

        if (array[rowNum-1][seatNum-1] == 0){
            array[rowNum-1][seatNum-1] = 1;
            ticketList[counter] = ticket;
            System.out.println("Your booking is successful");
            counter++;
        }
        else System.out.println("Seat unavailable");
        // The value of the counter variable is returned such that new tickets are always
        // added to the end of the array
        return counter;
    }


    public static void cancel_ticket(int[][] array, Scanner input, Ticket[] tickets){
        // This method is used to cancel a seat, it removes a ticket from the array of
        // tickets and toggles between 0 and 1 for array of seating arrangement
       int[] seatingDetails = seat_validator(input);
       int rowNum = seatingDetails[0];
       int seatNum = seatingDetails[1];

        if (array[rowNum-1][seatNum-1] == 1){
            array[rowNum-1][seatNum-1] = 0;
            for(int i = 0; i<tickets.length; i++){
                if(tickets[i].getRow() == rowNum && tickets[i].getSeat() == seatNum){
                    tickets[i] = null;
                    break;
                }
            }
            System.out.println("You have cancelled seat"+seatNum);
        }
    }


    public static void print_seating_area(int[][] seatArrangement){
        // This method is used to display the visual representation of the theatre
        int index = 0;
        System.out.println("    ************");
        System.out.println("    *  SCREEN  *");
        System.out.println("    ************");
        for (int[] i : seatArrangement) {
            for (int j : i) {
                index++;
                if (j == 0)
                    System.out.print("O");
                else
                    System.out.print("X");
                if (index == 10)
                    System.out.print(" ");
            }
            index = 0;
            System.out.println();
        }
    }


    public static void show_available(int[][] array){
        // This method is used to list out all available seats
        System.out.print("Seats available in row 1: ");
        for(int i=0; i<20; i++){
            if (array[0][i] == 0)
                System.out.print((i+1)+", ");
        }
        System.out.print("Seats available in row 2: ");
        for(int i=0; i<20; i++){
            if (array[1][i] == 0)
                System.out.print((i+1)+", ");
        }
        System.out.print("Seats available in row 3: ");
        for(int i=0; i<20; i++){
            if (array[2][i] == 0)
                System.out.print((i+1)+", ");
        }
    }


    public static void show_person_info(Ticket[] ticketList, Scanner input){
        // This method displays the information about the people who booked tickets
        int[] seatingDetails = seat_validator(input);
        int rowNum = seatingDetails[0];
        int seatNum = seatingDetails[1];
        for (Ticket value : ticketList) {
            if (value == null)
                break;
            if (value.getRow() == rowNum && value.getSeat() == seatNum) {
                value.getPerson().print();
                break;
            }
        }
    }


    public static void calculate_price(Ticket[] ticketList){
        // This method is used to keep a running total of the price of
        // all tickets sold
        double totalPrice = 0;
        for(Ticket ticket: ticketList){
            if (ticket != null)
                totalPrice += ticket.getPrice();
        }
        System.out.println("The total price of the tickets sold are "+totalPrice);
    }


    private static void sort_tickets(Ticket[] ticketList) {
        // This method sorts the array of tickets according to the price value
        // of the tickets booked
        int bottom = ticketList.length - 2;
        Ticket temp;
        boolean exchanged = true;
        while (exchanged) {
            exchanged = false;
            for (int i = 0; i <= bottom; i++) {
                if(ticketList[i] == null || ticketList[i+1] == null)
                    break;
                else if (ticketList[i].getPrice() > ticketList[i + 1].getPrice()) {
                    temp = ticketList[i];
                    ticketList[i] = ticketList[i + 1];
                    ticketList[i + 1] = temp;
                    exchanged = true;
                }
            }
            bottom--;
        }
        for(Ticket ticket: ticketList){
            if(ticket == null)
                break;
            ticket.print();
        }
    }


    public static int[] seat_validator(Scanner input){
        //This method is used to validate the input a user enters about the row and seat number
        //and returns the row and seat number in an array
        int rowNum = 0, seatNum = 0;
        boolean isRowValid = true;
        while (isRowValid){
            try {
                System.out.print("Enter row: ");
                rowNum = input.nextInt();
                if (rowNum>=1 && rowNum<=3){
                    isRowValid = false;
                }
                else System.out.println("Please enter a valid row number");;
            }
            catch (Exception e){
                System.out.println("Please enter a valid row number");
                input.nextLine();
            }
        }
        boolean isSeatValid = true;
        while (isSeatValid){
            try {
                System.out.print("Enter seat: ");
                seatNum = input.nextInt();
                if (seatNum>=1 && seatNum<=20){
                    isSeatValid = false;
                }
                else System.out.println("Please enter a valid seat number");;
            }
            catch (Exception e){
                System.out.println("Please enter a valid seat number");
                input.nextLine();
            }
        }
        return new int[]{rowNum,seatNum};
    }
}
