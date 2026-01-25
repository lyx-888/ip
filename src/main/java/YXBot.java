import java.util.Scanner;
public class YXBot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Task[] list = new Task[100];
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
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < counter; i++){
                        System.out.println((i + 1) + "." + list[i].toString());
                    }
                    System.out.println("____________________________________________________________");
                }
            }
            else if (input.startsWith("mark ")){
                int taskNum = Integer.parseInt(input.split(" ")[1]) - 1;
                if(list[taskNum] == null){
                    System.out.println("____________________________________________________________");
                    System.out.println("task does not exist");
                    System.out.println("____________________________________________________________");
                }
                else{
                    list[taskNum].markAsDone();
                    System.out.println("____________________________________________________________");
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(list[taskNum].toString());
                    System.out.println("____________________________________________________________");
                }
            }
            else if (input.startsWith("unmark ")) {
                int taskNum = Integer.parseInt(input.split(" ")[1]) - 1;
                if (list[taskNum] == null) {
                    System.out.println("____________________________________________________________");
                    System.out.println("task does not exist");
                    System.out.println("____________________________________________________________");
                } else {
                    list[taskNum].markAsNotDone();
                    System.out.println("____________________________________________________________");
                    System.out.println("Ok, I've marked this task as not done yet:");
                    System.out.println(list[taskNum].toString());
                    System.out.println("____________________________________________________________");
                }
            }
            else {
                System.out.println("____________________________________________________________");
                System.out.println("added: " + input);
                Task t = new Task(input);
                list[counter] = t;
                counter++;
                System.out.println("____________________________________________________________");
            }
        }
        sc.close();
    }
}
