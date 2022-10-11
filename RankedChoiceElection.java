//Emma Trauber (@etrauber) and Cassidy McKee (@cmckee114)
import java.util.*;
import java.util.ArrayList;
public class RankedChoiceElection{

    public static String bordaCount(ArrayList<ArrayList<Integer>> votes, ArrayList<String> candidates){
        //create arraylist to hold the total amount of votes per candidate
        ArrayList<Integer> voterCounter = new ArrayList<>();
        for (int i =0; i< candidates.size(); i++){
            voterCounter.add(0);
        }

        //for loop to add up the votes per candidate and assign it to voterCounter arraylist
        for(int i=0; i<votes.size(); i++){
            for(int j=0; j<votes.get(i).size(); j++){
                voterCounter.set(j,(voterCounter.get(j) + votes.get(i).get(j)));
            }
        }

        //create a max vote-count variable to start with
        int min = voterCounter.get(0);
        //create an index for that variable 
        int index = 0;
        //loop to determine which index in voterCounter is the largest
        for(int i=0; i<voterCounter.size(); i++){
            if(voterCounter.get(i) < min){
                min = voterCounter.get(i);
                index = i;
            }
        }
        //return the index of the largest count in candidates - will correspond
        return candidates.get(index);
    }

    public static String instantRunOff(ArrayList<ArrayList<Integer>> votes, ArrayList<String> candidates){
        //variable to help store majority value 
        String majority = null;
        // create new arraylist where each the index of each col represents the number choice of each candidate on the voter ballot (ex/ index of col 0 = first choices)
        ArrayList<ArrayList<String>> order = new ArrayList<>();
        //for loop to iterate through each row and re-order it based on the preferances of voter
        for(int r=0; r<votes.size(); r++){
            //create counter arraylist for each row - to add to broader order arraylist 
            ArrayList<String> row = new ArrayList<>();
            //fill in row-arraylist with empty spaces in order to set these places later ("" = place holders) 
            for(int i=0; i<candidates.size(); i++){
                row.add("");
            }
            //initalize rows 
            for(int c=0; c<votes.get(r).size(); c++){
                //index = preferance - 1 (since index starts at 0), String = candidate - represented as col correlates to candidate arraylist
                row.set(votes.get(r).get(c)-1, candidates.get(c));
            }
            //add individual row to broader order arraylist
            order.add(row);
        }

        //create while loop to run as long as no majority is found
        while(majority == null){
            //create counter array to count up amount of first place votes each candidate got - each index = specific candidate
            int[] counter = new int[candidates.size()];
            //for loop to iterate through first column in order arraylist to count up votes
            for(int i=0; i<order.size(); i++){
                //index = the index of the candidate of the current row being looked at (all in col 0)
                int index = candidates.indexOf(order.get(i).get(0));
                //add 1 to counter at index
                counter[index] += 1;
            }

            //for loop to determine if there is a majoirty by looking at counter array
            for(int i=0; i<counter.length; i++){
                if(counter[i] >= votes.size()/2 + 1){
                    //return candidate that has the majoirty votes
                    return candidates.get(i);
                }
            }

            //If no majoirty winner: find the candidate with the least amount of first choice votes
            //initalizing with candidate at index 0 (if there is a tie - candidate at index 0 will be eliminate first)
            int min = counter[0];
            int candidateIndex = 0;
            //for loop to determine which place has the fewest votes - keeps track of index as it corresponds to index of candidate in arraylist
            for(int i=1; i<counter.length; i++){
                if(counter[i] < min){
                    min = counter[i];
                    candidateIndex = i;
                }
            }


            //delete all occurances of the worst vote in every ballot
            for(int r=0; r<order.size(); r++){
                order.get(r).remove(candidates.get(candidateIndex));
            }
            candidates.remove(candidateIndex);
        }
    return majority;

    }
    
    public static void main (String[] args){
        ArrayList<ArrayList<Integer>> arrList = new ArrayList<>();
        //int[][] arr = {{3, 4, 2, 1}, {3, 1, 4, 2}, {1, 4, 2, 3}, {1, 2, 3, 4}, {2, 1, 3, 4}, {2, 1, 3, 4}, {3, 4, 2, 1}, {2, 3, 1, 4}, {3, 1, 2, 4}};
        //int[][] arr = {{1,2,3}, {1,2,3}, {2,3,1}, {2,3,1}, {2,3,1}};
        //int[][] arr = {{1,2,3}, {3,2,1}, {3,1,2}, {2,3,1}};
        int[][] arr = {{1,2,3}, {1,2,3}, {2,3,1}, {2,3,1}, {3,1,2}, {3,1,2}};
        for(int i=0; i<arr.length; i++){
            ArrayList<Integer> row = new ArrayList<>();
            for(int j=0; j<arr[i].length; j++){
                row.add(arr[i][j]);
            }
            arrList.add(row);
        }
        ArrayList<String> candidates = new ArrayList<>(Arrays.asList("You Belong with me", "All too well", "Love Story", "Fifteen"));
        ArrayList<String> letters = new ArrayList<>(Arrays.asList("A","B","C"));
        System.out.println("BordaCount: " + bordaCount(arrList, letters));
        System.out.println("InstantRunOff: " + instantRunOff(arrList, letters));

    }

}