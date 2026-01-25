import java.util.Scanner;
public class YXBot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] list = new String[100];
        int counter = 0;

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm YXBot");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
        while (true) {
            String input = sc.nextLine();
            if (input.equals("bye")){
                System.out.println("____________________________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            }
            else if (input.equals("list")){
                if (counter == 0){
                    System.out.println("Empty list");
                }
                else{
                    System.out.println("____________________________________________________________");
                    for (int i = 0; i < counter; i++){
                        System.out.println((i + 1) + ". " + list[i]);
                    }
                    System.out.println("____________________________________________________________");
                }
            }
            else {
                System.out.println("____________________________________________________________");
                System.out.println("added: " + input);
                list[counter] = input;
                counter++;
                System.out.println("____________________________________________________________");
            }
        }
        sc.close();
    }
}
