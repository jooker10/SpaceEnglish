package anouar.oulhaj.p001;

import java.util.ArrayList;

import anouar.oulhaj.p001.DB.Sentence;

public class Utils {

    public static ArrayList<Sentence> sentences1 = new ArrayList<>();
    public static ArrayList<Sentence> sentences2 = new ArrayList<>();

    public static void FillData() {
        sentences1.add(new Sentence(0, "fr0", "eng0"));
        sentences1.add(new Sentence(1, "fr1", "eng1"));
        sentences1.add(new Sentence(2, "fr2", "eng2"));
        sentences1.add(new Sentence(3, "fr3", "eng3"));

        sentences2.add(new Sentence(0, "Fr0", "Eng0"));
        sentences2.add(new Sentence(1, "Fr1", "Eng1"));
        sentences2.add(new Sentence(2, "Fr2", "Eng2"));
        sentences2.add(new Sentence(3, "Fr3", "Eng3"));

    }
}
