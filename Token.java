package ass5;
import java.util.*;

public class Token {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("----------------------------------------------------------");
        System.out.print("*** Enter the number of nodes to form the ring: ");
        int n = input.nextInt();
        System.out.println();

        int token = 0; // starting token
        int ch; // choice to send again

        // Display all nodes
        System.out.println("Nodes in the ring:");
        for (int i = 0; i < n; i++) {
            System.out.print(i + " ");
        }
        System.out.println(0); // To show ring is circular

        do {
            System.out.println("----------------------------------------------------------");
            System.out.print("** Enter sender from the above nodes: ");
            int sender = input.nextInt();
            System.out.println("===============");
            System.out.print("** Enter receiver from the above nodes: ");
            int receiver = input.nextInt();
            System.out.println("===============");
            System.out.print("** Enter data to send: ");
            int data = input.nextInt();
            System.out.println();

            System.out.println("----------------------------------------------------------");

            // Token Passing
            System.out.print("Token passing:");
            int current = token;
            while (current != sender) 
            {
                System.out.print(" " + current + "->");
                current = (current + 1) % n;
            }
            System.out.println(" " + sender);

            // Sender sending data
            System.out.println("------> Sender " + sender + " sending data: " + data);

            // Forwarding data
            current = (sender + 1) % n;
            while (current != receiver) {
                System.out.println("Data " + data + " forwarded by node " + current);
                current = (current + 1) % n;
            }

            // Receiver received data
            System.out.println("------> Receiver " + receiver + " received data: " + data);
            System.out.println("----------------------------------------------------------");

            token = sender; // move token to the sender after sending

            // Ask if user wants to send again
            int choice;
            while (true) {
                System.out.print("Do you want to send again? Enter 1 for Yes, 0 for No: ");
               // try {
                    choice = input.nextInt();
                    if (choice == 1 || choice == 0) {
                        break; // correct input, exit loop
                    } else {
                        System.out.println("Invalid input! Enter only 1 or 0.");
                    }
               // } catch (InputMismatchException e) {
                   // System.out.println("Invalid input! Enter only numbers (1 or 0).");
                   // input.next(); // clear wrong input
                //}
            }
            ch = choice; // save final choice

        } while (ch == 1);

        System.out.println("Exiting Token Ring Simulation...");
    }
}
