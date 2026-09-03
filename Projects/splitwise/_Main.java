package Projects.splitwise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _Main {
    public static void main(String[] args) {
        SplitwiseService system = new SplitwiseService();

        User user1 = system.addUser("Bob");
        User user2 = system.addUser("Joe");
        User user3 = system.addUser("Sally");

        Group group1 = system.addGroup();
        group1.addUser(user1);
        group1.addUser(user2);
        group1.addUser(user3);

        // Expense - StratEqual
        Expense expense1 = system.createExpense("Going to the Movies", group1, 100.00, user1, new SplitStratEqual());
        expense1.calculateUserSplits();

        // Expense - StractExactAmount
        Expense expense2 = system.createExpense("Going to Dinner", group1, 500.00, user1, new SplitStratExactAmount());
        expense2.setMapUserSplits(user2, 400.00);
        expense2.setMapUserSplits(user3, 100.00);
        expense2.calculateUserSplits();

        // Expense - StratPercent
        Expense expense3 = system.createExpense("Example Percents", group1, 100.00, user1, new SplitStratPercent());
        expense3.setMapUserSplits(user2, 70.00);
        expense3.setMapUserSplits(user3, 30.00);
        expense3.calculateUserSplits();

        // Calculate Owed vs. Needing to Pay
        //system.simplifyDebtAmounts();

        // Pay all
        system.printAllUserBalances();
        system.payAllSplits();
        system.printAllUserBalances();

        // Print All Transactions
        system.printAllTransactions();
    }

    /*
        Prompt: Design the low-level design (LLD) of a system like Splitwise, which allows 
        users to track shared expenses and settle debts. The system should support features 
        like adding expenses, splitting costs among users, and tracking balances.
        1)
        https://github.com/ashishps1/awesome-low-level-design/blob/main/solutions/java/src/splitwise/SplitwiseService.java
        2)
        https://www.hellointerview.com/community/questions/splitwise-lld/cm6jwwh6700bxui4bzs4jmddl 

        Requirements:
        - Create Users
        - Create & Add Users to Groups
        - Add Expenses to Groups
        - Users should be able to view their balance & pay their amount
        - Different Split Methods: Equal Split / Percentage Split / Exact Amounts

        Entities
        - Users
        - Groups
        - SplitStrategy (Equal / Percent / Exact Amounts)
        - Expense

        Class Design

        SplitwiseService
        - List<Users> users
        - List<Group> groups
        - List<Expense> expenses
        + createUser(name)
        + createGroup(name)
        + addExpense(group)
        + settleExpense(expense)
        + getBalance(group)

        User
        - String id
        
        Group
        - String id
        - Set<Users> users
        + addMember(user)
        + addExpense(expense)

        Split
        - User user
        - int balance

        SplitStrategy interface
        + calculateSplits(expense) -> List<Split>

        Expense
        - String id
        - String description
        - Group group
        - User paidByUser
        - int totalAmount
        - List<Splits> splits
        - SplitStrategy splitStrategy

        Transaction
        - User userFrom
        - User userPaid
        - int amount
        + pay()




    */
}
