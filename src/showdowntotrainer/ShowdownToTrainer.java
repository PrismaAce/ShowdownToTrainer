/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package showdowntotrainer;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author codem
 */
public class ShowdownToTrainer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AbilityPairs ap = new AbilityPairs();
        ap.setup();
        MovePairs mp = new MovePairs();
        mp.setup();
        ItemPairs ip = new ItemPairs();
        ip.setup();
        FormTriples ft = new FormTriples();
        ft.setup();
        
        while (true)
        {
            if (!mainConverter()) break;
        }
        //itemPairConverter();
    }
    
    public static boolean mainConverter()
    {
        
        Scanner scan = new Scanner(System.in);
        System.out.println("Paste import: (type 'e' to exit)");
        
        
        ArrayList<String> pokemon1 = new ArrayList<>();
        ArrayList<String> pokemon2 = new ArrayList<>();
        ArrayList<String> pokemon3 = new ArrayList<>();
        ArrayList<String> pokemon4 = new ArrayList<>();
        ArrayList<String> pokemon5 = new ArrayList<>();
        ArrayList<String> pokemon6 = new ArrayList<>();
        
        int counter = 0;
        boolean emptyLine;
        while (true)
        {
            String s = scan.nextLine();
            if (s.equals("e")) return false;
            if (s.equals("")) 
            {
                emptyLine = true;
                counter++;
                s = scan.nextLine(); // eat
                if (s.equals("") && emptyLine) break; // if double empty then break
            }
            
            switch (counter)
            {
                case 0 -> pokemon1.add(s);
                case 1 -> pokemon2.add(s);
                case 2 -> pokemon3.add(s);
                case 3 -> pokemon4.add(s);
                case 4 -> pokemon5.add(s);
                case 5 -> pokemon6.add(s);
            }
        }
        Party p = new Party();
        
        
        for (int i = 0; i < counter; i++) {
            p.size++;
            switch (i)
            {
                case 0 -> p.party[0].Generate(pokemon1);
                case 1 -> p.party[1].Generate(pokemon2);
                case 2 -> p.party[2].Generate(pokemon3);
                case 3 -> p.party[3].Generate(pokemon4);
                case 4 -> p.party[4].Generate(pokemon5);
                case 5 -> p.party[5].Generate(pokemon6);
            }
        }
        p.Convert();
        p.PrintPBS();
        return true;
    }
    
    public static void moveAbilityPairConverter()
    {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> inputStrings = new ArrayList<>();
        System.out.println("Paste import:");
        
        while (scan.hasNextLine())
        {
            String lineIn = scan.nextLine();
            if (lineIn.equals("")) 
            {
                break;
            }
            inputStrings.add(lineIn);
        }
        
        ArrayList<String> filteredStrings = new ArrayList<>();
        for (String s : inputStrings)
        {
            if (s.charAt(0) == '[' || s.charAt(0) == 'N' ) filteredStrings.add(s);
        }
        System.out.println("-------");
        for (int i = 0; i < filteredStrings.size(); i+=2) {
            String PBSForm = filteredStrings.get(i).replace("[", "").replace("]", "");
            String RealForm = filteredStrings.get(i+1).split(" = ")[1];
            
            System.out.println("List.add(new Pair(\""+PBSForm+"\", \""+RealForm+"\"));");
        }
    }
    
    public static void itemPairConverter()
    {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> inputStrings = new ArrayList<>();
        System.out.println("Paste import:");
        
        while (scan.hasNextLine())
        {
            String lineIn = scan.nextLine();
            if (lineIn.equals("")) 
            {
                break;
            }
            inputStrings.add(lineIn);
        }
        
        ArrayList<String> filteredStrings = new ArrayList<>();
        for (String s : inputStrings)
        {
            if (s.charAt(0) == '[') filteredStrings.add(s);
            if (s.split(" = ")[0].equals("Name")) filteredStrings.add(s);
        }
        System.out.println("-------");
        for (int i = 0; i < filteredStrings.size(); i+=2) {
            String PBSForm = filteredStrings.get(i).replace("[", "").replace("]", "");
            String RealForm = filteredStrings.get(i+1).split(" = ")[1];
            
            System.out.println("List.add(new Pair(\""+PBSForm+"\", \""+RealForm+"\"));");
        }
    }
    
    public static void formTripleConverter()
    {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> inputStrings = new ArrayList<>();
        System.out.println("Paste import:");
        
        while (scan.hasNextLine())
        {
            String lineIn = scan.nextLine();
            if (lineIn.equals("")) 
            {
                break;
            }
            inputStrings.add(lineIn);
        }
        
        ArrayList<String> filter1 = new ArrayList<>();
        // Gets all forms
        for (String s : inputStrings)
        {
            if (s.charAt(0) == '[') filter1.add(s);
            //else if (s.charAt(0) == 'F' && s.charAt(1) == 'o' ) filter1.add(s);
            else if (s.charAt(0) == 'N' && s.charAt(1) == 'a' ) filter1.add(s);
        }

        ArrayList<String> filteredStrings = new ArrayList<>();
        for (int i = 0; i < filter1.size(); i++)
        {
            if (filter1.get(i).charAt(0) == '[') 
            {
                // if (filter1.get(i+1).charAt(0) == 'F' && filter1.get(i+1).charAt(1) == 'o' ) 
                if (filter1.get(i+1).charAt(0) == 'N' && filter1.get(i+1).charAt(1) == 'a' ) 
                {
                    // if this form, and the next line is its form name, add those two lines
                    filteredStrings.add(filter1.get(i));
                    filteredStrings.add(filter1.get(i+1));
                }
                    
            }
        }
        System.out.println("-------");
        for (int i = 0; i < filteredStrings.size(); i+=2) {
            /*
            String[] PBSForm = filteredStrings.get(i).replace("[", "").replace("]", "").split(",");
            String RealForm = filteredStrings.get(i+1).split(" = ")[1];
            
            String Pokemon = PBSForm[0].charAt(0)+PBSForm[0].toLowerCase().substring(1);
            int PBSFormID = Integer.parseInt(PBSForm[1]);
            
            System.out.println("List.add(new Triple(\""+RealForm+"\", \""+Pokemon+"-\", "+PBSFormID+"));"+" // "+Pokemon);
            */
            String PBSForm = filteredStrings.get(i).replace("[", "").replace("]", "");
            String DisplayForm = filteredStrings.get(i+1).split(" = ")[1];
            System.out.println("List.add(new Triple(\""+PBSForm+"\", \""+DisplayForm+"\", 0));");
        }
    }
    
    public static class Party
    {
        int size = 0;
        Pokemon[] party = {new Pokemon(), new Pokemon(), new Pokemon(), new Pokemon(), new Pokemon(), new Pokemon()};
        
        public void Convert()
        {
            for (int i = 0; i < size; i++) {
                party[i].ConvertToPBS();
            }
        }
        
        public void PrintPokemon()
        {
            for (int i = 0; i < size; i++) {
                party[i].PrintPokemon();
            }
        }
        
        public void PrintPBS()
        {
            for (int i = 0; i < size; i++) {
                party[i].PrintPBS();
            }
        }     
    }
    
}
