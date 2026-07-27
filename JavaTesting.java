import java.util.*;

public class JavaTesting {
    private record DataEntry(String name, int time) {}

    public static void main(String[] args) {
        // unordered list
        // find who badges in too often - 3 or more time in a 1 hour period
        // return names and the times they did it 

        // https://leetcode.com/discuss/post/1504226/indeed-karat-interview-by-anonymous_user-xplw/

        String[][] badgeTimes = {
            {"Paul", "1355"},
            {"Jennifer", "1910"},
            {"John", "835"},
            {"John", "830"},
            {"Paul", "1315"},
            {"John", "1615"},
            {"John", "1640"},
            {"Paul", "1405"},
            {"John", "855"},
            {"John", "930"},
            {"John", "915"},
            {"John", "730"},
            {"John", "940"},
            {"Jennifer", "1335"},
            {"Jennifer", "730"},
            {"John", "1630"},
            {"Jennifer", "5"}
        };

        /*
            Expected output (in any order)
            John: 830 835 855 915 930
            Paul: 1315 1355 1405
        */

        List<DataEntry> records = new ArrayList<>();
        Map<String, Integer> occurences = new HashMap<>();
        Map<String, List<Integer>> results = new HashMap<>();

        for (String[] record : badgeTimes) {
            String name = record[0];
            String time = record[1];

            // round to nearest hundreth
            int intTime = Integer.parseInt(time);
            intTime = intTime - (intTime % 100);

            DataEntry nextRecord = new DataEntry(name, intTime);
            records.add(nextRecord);
        }

        for (DataEntry record : records) {
            System.out.println(record);


        }










    }




















    // public static void main(String[] args) {
    //     // All employees who didnt use their badge while EXITING
    //     // All employees who didnt use their badge while ENTERING
    //    String[][] records1 = {
    //         {"Paul", "enter"},
    //         {"Pauline", "exit"},
    //         {"Paul", "enter"},
    //         {"Paul", "exit"},
    //         {"Martha", "exit"},
    //         {"Joe", "enter"},
    //         {"Martha", "enter"},
    //         {"Steve", "enter"},
    //         {"Martha", "exit"},
    //         {"Jennifer", "enter"},
    //         {"Joe", "enter"},
    //         {"Curtis", "exit"},
    //         {"Curtis", "enter"},
    //         {"Joe", "exit"},
    //         {"Martha", "enter"},
    //         {"Martha", "exit"},
    //         {"Jennifer", "exit"},
    //         {"Joe", "enter"},
    //         {"Joe", "enter"},
    //         {"Martha", "exit"},
    //         {"Joe", "exit"},
    //         {"Joe", "exit"}
    //     };

    //     // Map, to count the number of times they did it
    //     Set<String> room = new HashSet<>();

    //     // Set so we can save their names once
    //     // Entered but no EXIT
    //     Set<String> set1 = new HashSet<>();
    //     // Exited but no ENTER
    //     Set<String> set2 = new HashSet<>();
        
    //     // Do enter/exit checking IN ORDER and counting the number +1 of each in order
    //     for (String[] record : records1) {
    //         String name = record[0];
    //         String action = record[1];
            
    //         if (action.equals("exit")) {
    //             if (!room.contains(name)) {
    //                 set2.add(name);
    //             } else {
    //                 room.remove(name);
    //             }
    //         } else if (action.equals("enter")) {
    //             if (room.contains(name)) {
    //                 // never exited
    //                 set1.add(name);
    //             } else {
    //                 room.add(name);
    //             }
    //         }
    //     }

    //     // Check for those who entered, but didnt exit
    //     for (String name : room) {
    //         set1.add(name);
    //     }

    //     System.out.println(room.toString());

    //     System.out.println(set1.toString());
    //     System.out.println(set2.toString());
    // }
}
