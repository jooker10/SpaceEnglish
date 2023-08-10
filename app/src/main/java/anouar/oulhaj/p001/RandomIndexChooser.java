package anouar.oulhaj.p001;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomIndexChooser {

    public static List<Integer> chooseRandomIndexes(int listSize, int includedIndex) {
        if (listSize < 3) {
            throw new IllegalArgumentException("List size should be at least 2.");
        }

        List<Integer> randomIndexes = new ArrayList<>();
        randomIndexes.add(includedIndex);

        while (randomIndexes.size() < 3) {
            int randomIndex = new Random().nextInt(listSize);
            if (!randomIndexes.contains(randomIndex)) {
                randomIndexes.add(randomIndex);
            }
        }

        return randomIndexes;
    }

   /* public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        int includedIndex = 5;
        List<Integer> randomIndexes = chooseRandomIndexes(list.size(), includedIndex);

        System.out.println("Random indexes including index " + includedIndex + ": " + randomIndexes);
    }*/
}
