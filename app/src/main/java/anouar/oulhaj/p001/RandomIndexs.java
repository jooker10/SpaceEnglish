package anouar.oulhaj.p001;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import anouar.oulhaj.p001.DB.Verb;

public class RandomIndexs {
    public List<Integer> randomIndexes(List<Verb> aAllList, int includedIndex) {
        List<Integer> lists = new ArrayList<>();
        Random random = new Random();
        lists.add(includedIndex);

        while (lists.size() < 3) {
              int randomIndex = random.nextInt(aAllList.size());
              if(randomIndex != includedIndex) {
                  lists.add(randomIndex);
              }
        }
        Collections.shuffle(lists);
        return lists;
    }
}
