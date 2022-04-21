import java.util.Scanner;



public class ATM1 {
   public static void main(String[] args) {
      Scanner inp = new Scanner(System.in);
      int[] acctNum = new int[] {12345, 67890};
      String[] acctName = new String[]{"Joseph", "Yusuf"};
      String[] acctSurName = new String[]{"LEDET", "OGRENCI"};
      String[] acctPIN = new String[]{"0101", "6789"};
      double[] acctBal = new double[]{10000.0, 2.50};
      
      bankLogin(acctNum, acctName, acctSurName, acctPIN, acctBal);
   }
   
   public static int menuDisplay(String[] item, Scanner inp) {
      for(int i = 0; i < item.length; i++)
         System.out.println((i+1) + " - " + item[i]);
      System.out.print("0 to Quit\nPlease enter your selection >> ");
      return Integer.parseInt(inp.nextLine());
      
   }

   // findAcct - get the index in an array of an account
   public static int findAcct(int[] acctNums, int acctNum) {
      for (int i = 0; i < acctNums.length; i++)
         if(acctNums[i] == acctNum)
            return i;
      return -1;
   }
   
   // bankLogin - user interface to login to the atm
   public static void bankLogin(int[] acctNum, String[] acctName, String[] acctSurName, String[] acctPIN, double[] acctBalance){
      Scanner input = new Scanner(System.in);
      System.out.print("Please enter your account number >> ");
      int userNum = Integer.parseInt(input.nextLine());
      System.out.print("Please enter your PIN >> ");
      String userPIN = input.nextLine();
      int acctIndex = findAcct(acctNum, userNum);
      if(acctIndex == -1 || !userPIN.equals(acctPIN[acctIndex])){
         System.out.println("ERROR: Account/PIN combination not found.");
         return;
      }
      atm(acctName, acctSurName, acctBalance, acctIndex, input);
   }
   // atm - user interface for the atm
   public static void atm(String[] acctName, String[] acctSurName, double[] acctBalance, int index, Scanner inp){
      int choice = 0;
      String[] menu = new String[]{"Account Balance", "Deposit", "Withdrawal", "Change Name"};
      do{
         System.out.println("\nHello " + acctName[index] + " " + acctSurName[index]);
         System.out.println("What would you like to do today?");
         choice = menuDisplay(menu, inp);
         double amount = 0;
         switch(choice) {
            case 1: // Balance
               System.out.println("Your account balance is " + acctBalance[index]);
               break;
            case 2: // Deposit
               System.out.print("How much are you depositing? ");
               amount = Double.parseDouble(inp.nextLine());
               if(isDepositValid(amount))
                  acctBalance[index] += amount;
               else
                  System.out.println("ERROR: Invalid deposit amount");
               break;
            case 3: // Withdrawal
               System.out.print("How much are you withdrawing? ");
               amount = Double.parseDouble(inp.nextLine());
               if(isWithdrawalValid(acctBalance[index], amount)) {
                  System.out.println("You will receive the following:");
                  System.out.println(moneyGiven(amount));
                  acctBalance[index] -= amount;
               } else
                  System.out.println("ERROR: Invalid withdrawal amount");
               break;
            case 4: // Change Name
               inp.nextLine();
               System.out.print("Please enter your name >> ");
               String name = inp.nextLine();
               String name2 = "" + Character.toUpperCase(name.charAt(0));
               String name3 = name.substring(1).toLowerCase();
               acctName[index] = name2 + name3;
//                acctName[index] = Character.toUpperCase(acctName[index].charAt(0)) + 
//                   acctName[index].substring(1).toLowerCase();
               System.out.print("Please enter your surname >> ");
               acctSurName[index] = inp.nextLine().toUpperCase();
               break;
            case 0:
               System.out.println("Thank you for using our ATM. Have a nice day!");
               break;
            default:
               System.out.println("Invalid choice. Exiting System.");
               choice = 0;
         }
      } while (choice != 0);
   }

   // main - the main ATM program
   public static void main_old(String args[]){
      Scanner input = new Scanner(System.in);
      System.out.print("Please enter your first name >> ");
      String name = input.nextLine();
      System.out.print("Please enter your surname >> ");
      String surname = input.nextLine().toUpperCase();
      double balance = -1;
      do {
         System.out.print("Please enter your starting balance >> ");
         double balanceIn = Double.parseDouble(input.nextLine());
         if (balanceIn < 0)
            System.out.println("ERROR: Invalid balance");
         else
            balance = balanceIn;
      } while(balance < 0);
      int choice = 0;
      do{
         System.out.println("\nHello " + name + " " + surname);
         System.out.println("What would you like to do today?");
         System.out.println("1 for Account Balance");
         System.out.println("2 for Cash Deposit");
         System.out.println("3 for Cash Withdrawal");
         System.out.println("0 to quit");
         System.out.print("Please enter your selection >> ");
         
         choice = Integer.parseInt(input.nextLine());
         double amount = 0;
         switch(choice) {
            case 1: // Balance
               System.out.println("Your account balance is " + balance);
               break;
            case 2: // Deposit
               System.out.print("How much are you depositing? ");
               amount = Double.parseDouble(input.nextLine());
               if(isDepositValid(amount))
                  balance += amount;
               else
                  System.out.println("ERROR: Invalid deposit amount");
               break;
            case 3: // Withdrawal
               System.out.print("How much are you withdrawing? ");
               amount = Double.parseDouble(input.nextLine());
               if(isWithdrawalValid(balance, amount)) {
                  System.out.println("You will receive the following:");
                  System.out.println(moneyGiven(amount));
                  balance -= amount;
               } else
                  System.out.println("ERROR: Invalid withdrawal amount");
               break;
            case 0:
               System.out.println("Thank you for using our ATM. Have a nice day!");
               break;
            default:
               System.out.println("Invalid choice. Exiting System.");
               choice = 0;
         }
      } while (choice != 0);
   }
   
   // isDepositValid - is the amount valid to be deposited
   public static boolean isDepositValid(double amount){
      return amount > 0;
   }
   
   // isWithdrawalValid - is the amount valid to be withdrawn
   public static boolean isWithdrawalValid(double balance, double amount){
      return amount > 0 && amount <= balance;
   }
   
   //* moneyGiven - the different bills and coins to be given for a withdrawal
   public static String moneyGiven(double amount) { // Without 0's shown
      int intAmount = (int)(amount * 100);
      String retVal = "";
      if(intAmount >= 20000){
         retVal += intAmount / 20000 + " - 200\n";
         intAmount %= 20000;
      }

      if(intAmount >= 10000){
         retVal += intAmount / 10000 + " - 100\n";
         intAmount %= 10000;
      }

      if(intAmount >= 5000){
         retVal += intAmount / 5000 + " - 50\n";
         intAmount %= 5000;
      }

      if(intAmount >= 2000){
         retVal += intAmount / 2000 + " - 20\n";
         intAmount %= 2000;
      }

      if(intAmount >= 1000){
         retVal += intAmount / 1000 + " - 10\n";
         intAmount %= 1000;
      }

      if(intAmount >= 500){
         retVal += intAmount / 500 + " - 5\n";
         intAmount %= 500;
      }

      if(intAmount >= 100){
         retVal += intAmount / 100 + " - 1\n";
         intAmount %= 100;
      }

      if(intAmount >= 50){
         retVal += intAmount / 50 + " - 0.50\n";
         intAmount %= 50;
      }

      if(intAmount >= 25){
         retVal += intAmount / 25 + " - 0.25\n";
         intAmount %= 25;
      }

      if(intAmount >= 10){
         retVal += intAmount / 10 + " - 0.10\n";
         intAmount %= 10;
      }

      if(intAmount >= 5){
         retVal += intAmount / 5 + " - 0.05\n";
         intAmount %= 5;
      }

      if(intAmount >= 1){
         retVal += intAmount + " - 0.01";
         intAmount %= 1;
      }
      
      return retVal;

   }
   
   // moneyGiven - the different bills and coins to be given for a withdrawal
   public static String moneyGiven1(double amount) { // With 0's shown
      int intAmount = (int)(amount * 100);
      String retVal = "";
      retVal += intAmount / 20000 + " - 200\n";
      intAmount %= 20000;

      retVal += intAmount / 10000 + " - 100\n";
      intAmount %= 10000;

      retVal += intAmount / 5000 + " - 50\n";
      intAmount %= 5000;

      retVal += intAmount / 2000 + " - 20\n";
      intAmount %= 2000;

      retVal += intAmount / 1000 + " - 10\n";
      intAmount %= 1000;

      retVal += intAmount / 500 + " - 5\n";
      intAmount %= 500;

      retVal += intAmount / 100 + " - 1\n";
      intAmount %= 100;

      retVal += intAmount / 50 + " - 0.50\n";
      intAmount %= 50;

      retVal += intAmount / 25 + " - 0.25\n";
      intAmount %= 25;

      retVal += intAmount / 10 + " - 0.10\n";
      intAmount %= 10;

      retVal += intAmount / 5 + " - 0.05\n";
      intAmount %= 5;

      retVal += intAmount + " - 0.01";
      intAmount %= 1;
      
      return retVal;

   }
}