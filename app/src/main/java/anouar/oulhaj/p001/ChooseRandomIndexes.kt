package anouar.oulhaj.p001

import anouar.oulhaj.p001.DB.Verb


class ChooseRandomIndexes {

    fun chooseRandomIndexes (myList :  ArrayList<Verb>, includedIndex : Int) : ArrayList<Int> {

        var ranges : IntRange = 0 until myList.size
        var lists : ArrayList<Int> = ArrayList()
        lists.add(includedIndex)
       while(lists.size == 3) {
           var randomIndex = ranges.random()
           if (randomIndex != includedIndex) lists.add(randomIndex)
       }
        lists.shuffle()
        return lists
    }
}