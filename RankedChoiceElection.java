import java.util.*;
import java.util.ArrayList;
public class RankedChoiceElectionEC{

    public static String bordaCount(ArrayList<ArrayList<Integer>> votes, ArrayList<String> candidates){
        //create arraylist to hold the total amount of votes per candidtae
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
        String majority = null;
        //1. look at only 1st choices
        ArrayList<ArrayList<String>> order = new ArrayList<>();
        for(int r=0; r<votes.size(); r++){
            ArrayList<String> row = new ArrayList<>();
            for(int i=0; i<candidates.size(); i++){
                row.add("");
            }
            for(int c=0; c<votes.get(r).size(); c++){
                row.set(votes.get(r).get(c)-1, candidates.get(c));
            }
            order.add(row);
        }

        System.out.println(order);

        while(majority == null){
            //2. majority?
            //#ballots+1 /2 
            int[] counter = new int[candidates.size()];
            for(int i=0; i<order.size(); i++){
                //checking first colomn of arraylist order
                    //looking at index within candidates
                int index = candidates.indexOf(order.get(i).get(0));
                counter[index] += 1;
            }
            System.out.println(Arrays.toString(counter));

            for(int i=0; i<counter.length; i++){
                if(counter[i] >= votes.size()/2 + 1){
                    System.out.println("majority");
                    majority = candidates.get(i);
                    return candidates.get(i);
                }
            }
            System.out.println(majority);

            //3. If no majoirty winner: find the candidate with the least amount of first choice votes
                //check if candidate got no first choice cuz that would still be least
            int min = counter[0];
            int candidateIndex = 0;
            for(int i=1; i<counter.length; i++){
                if(counter[i] < min){
                    min = counter[i];
                    candidateIndex = i;
                }
            }
            System.out.println("least");
            System.out.println(candidates.get(candidateIndex));

            //4. delete all occurances of the worst vote - in every ballot
            for(int r=0; r<order.size(); r++){
                order.get(r).remove(candidates.get(candidateIndex));
            }
            candidates.remove(candidateIndex);
            System.out.println(order);
        }
    return majority;

    }
    
    public static void main (String[] args){
        ArrayList<ArrayList<Integer>> arrList = new ArrayList<>();
        int[][] arr = {{3, 4, 2, 1}, {3, 1, 4, 2}, {1, 4, 2, 3}, {1, 2, 3, 4}, {2, 1, 3, 4}, {2, 1, 3, 4}, {3, 4, 2, 1}, {2, 3, 1, 4}, {3, 1, 2, 4}};
        //int[][] arr = {{1,2,3,4}, {2,3,4,1}};
        for(int i=0; i<arr.length; i++){
            ArrayList<Integer> row = new ArrayList<>();
            for(int j=0; j<arr[i].length; j++){
                row.add(arr[i][j]);
            }
            arrList.add(row);
        }
        ArrayList<String> candidates = new ArrayList<>(Arrays.asList("You Belong with me", "All too well", "Love Story", "Fifteen"));
        System.out.println(bordaCount(arrList, candidates));
        System.out.println(instantRunOff(arrList, candidates));

    }

}