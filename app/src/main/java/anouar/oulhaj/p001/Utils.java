package anouar.oulhaj.p001;

import java.util.ArrayList;

import anouar.oulhaj.p001.DB.Adjective;
import anouar.oulhaj.p001.DB.Adverb;
import anouar.oulhaj.p001.DB.Idiom;
import anouar.oulhaj.p001.DB.Noun;
import anouar.oulhaj.p001.DB.Phrasal;
import anouar.oulhaj.p001.DB.Sentence;
import anouar.oulhaj.p001.DB.Verb;

public class Utils {

    public static boolean isDarkMode = false;
    //---for Changing theme test--------
    public static boolean isDarkThemeTest = false;
    //---for saving img url test--------
    public static String imgUrl = "";
    //-----max of the List (verbs,Sentences,phrasal verbs)-------
    public static int maxVerbsCount , maxSentencesCount , maxPhrasalCount;
    public static int authorizedVerbsCount, authorizedSentencesCount , authorizedPhrasalCount;
    //-----Enum for choosing native language -------
    public static Language language = Language.FRENCH;
    //------ the main qst in native language----------
    public static String txtOftheMainQuestioninNative;


    public static ArrayList<Verb> verbsList = new ArrayList<>();
    public static ArrayList<Sentence> sentencesList = new ArrayList<>();
    public static ArrayList<Phrasal> phrasalsList = new ArrayList<>();
    public static ArrayList<Noun> nounsList = new ArrayList<>();
    public static ArrayList<Adjective> adjsList = new ArrayList<>();
    public static ArrayList<Adverb> advsList = new ArrayList<>();
    public static ArrayList<Idiom> idiomsList = new ArrayList<>();




}
