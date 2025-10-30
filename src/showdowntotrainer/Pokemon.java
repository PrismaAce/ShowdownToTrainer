/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package showdowntotrainer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author codem
 */
public class Pokemon {
        // Pokemon
        String type = "";
        String formName = "";
        int form = 0;
        String nickname = "";
        String gender = "";
        String ability = "";
        String helditem = "";
        // Stats
        int level = 100;
        int[] iv = {31, 31, 31, 31, 31, 31};
        int[] ev = {0, 0, 0, 0, 0, 0};
        String nature = "";
        int happiness = 70;
        // Moves
        String[] moves = {"", "", "", ""};
        // Visuals
        String ball = "";
        boolean Shiny = false;
        boolean SuperShiny = false;
        boolean Shadow = false;
        // Gimmicks
        int dynamaxlevel = 0;
        boolean gmax = false;
        String teraType = "";
        
        boolean hasEVs = false;
        
        public void ConvertToPBS()
        {
            for (Pair p : AbilityPairs.List)
            {
                if(p.inPair(ability)) ability = p.swap(ability);
                //else System.out.println("Warning: "+ability+" has no pair?");
            }
            for (Pair p : ItemPairs.List)
            {
                if(p.inPair(helditem)) helditem = p.swap(helditem);
                //else System.out.println("Warning: "+helditem+" has no pair?");
                
                if(p.inPair(ball)) ball = p.swap(ball);
                //else System.out.println("Warning: "+ball+" has no pair?");
            }
            for (int i = 0; i < moves.length; i++)
            {
                for (Pair p : MovePairs.List)
                {
                
                    if (!moves[i].equals(""))
                    {
                        if(p.inPair(moves[i])) moves[i] = p.swap(moves[i]);
                    }
                        
                }
            }
            for (Triple t : FormTriples.List)
            {
                if(t.inTriple(type)) 
                {
                    if (t.getID() > 0)
                    {
                        form = t.getID();
                        formName = t.swap(type);
                        type = type.split("-")[0];
                        for (Triple t2 : FormTriples.List)
                        {
                            if (t2.inTriple(type))
                            {
                                type = t2.swap(type);
                                break;
                            }
                        }
                        break;
                    }
                    else type = t.swap(type);
                }
                //else System.out.println("Warning: "+type+" has no triple?");
            }
            // SpAtk, SpDef, Speed -> Speed, SpAtk, SpDef
            int speedIV = iv[5];
            int speedEV = ev[5];
            
            iv[5] = iv[4];
            ev[5] = ev[4];
            
            iv[4] = iv[3];
            ev[4] = ev[3];
            
            iv[3] = speedIV;
            ev[3] = speedEV;
            
            nature = nature.toUpperCase();
            teraType = teraType.toUpperCase();
            if (gender.startsWith("F")) gender = "female";
            else if (gender.startsWith("M")) gender = "male";
            
        }
        
        public void Generate(ArrayList<String> strings)
        {
            // Process Header First
            String header = strings.getFirst();
            String[] itemSplit = header.split(" @ ");
            if (itemSplit.length != 1)
            {
                helditem = itemSplit[1].strip();
            }
            String[] bracketDecoder = itemSplit[0].split(" ");
            String[] nicknameFinder = bracketDecoder.clone();
            String nick = "";
            if (bracketDecoder.length == 1) type = bracketDecoder[0];
            else
            {
                for (int i = 0; i < bracketDecoder.length; i++) 
                {
                    if (bracketDecoder[i].startsWith("("))
                    {
                        if (bracketDecoder[i].length() == 3) // (M) or (F)
                        {
                            gender = bracketDecoder[i].replace("(", "").replace(")", ""); 
                        }
                        else
                        {
                            type = bracketDecoder[i].replace("(", "").replace(")", "");
                        }
                        nicknameFinder[i] = "";
                    }
                }
                for (String loop : nicknameFinder) {
                    if (!loop.equals("")) {
                        nick += loop + " ";
                    }
                }
                // if we didnt get the type before, the nickname we are finding is actually the type
                if (type.equals("")) 
                {
                    type = nick.strip();
                }
                else nickname = nick.strip();
            }
            
            strings.removeFirst();
            
            for (String s : strings)
            {
                String[] splits = s.strip().split(": ");
                if (splits.length == 2) // Split by ': ' gets most cases
                {
                    switch (splits[0]) {
                        case "Ability" -> ability = splits[1];
                        case "Ball" -> ball = splits[1];
                        case "Tera Type" -> teraType = splits[1];
                        case "Level" -> level = Integer.parseInt(splits[1]);
                        case "Happiness" -> happiness = Integer.parseInt(splits[1]);
                        case "Dynamax Level" -> dynamaxlevel = Integer.parseInt(splits[1]);
                        case "Shiny" -> Shiny = splits[1].equals("Yes");
                        case "Super Shiny" -> Shiny = splits[1].equals("Yes");
                        case "Shadow" -> Shiny = splits[1].equals("Yes");
                        case "Gigantamax" -> gmax = splits[1].equals("Yes");
                        case "IVs" -> 
                        {
                            String[] IVSplit1 = splits[1].split(" / ");
                            for (String ivl : IVSplit1)
                            {
                                String[] IVSplit2 = ivl.split(" ");
                                switch (IVSplit2[1]) 
                                {
                                    case "HP" -> iv[0] = Integer.parseInt(IVSplit2[0]);
                                    case "Atk" -> iv[1] = Integer.parseInt(IVSplit2[0]);
                                    case "Def" -> iv[2] = Integer.parseInt(IVSplit2[0]);
                                    case "SpA" -> iv[3] = Integer.parseInt(IVSplit2[0]);
                                    case "SpD" -> iv[4] = Integer.parseInt(IVSplit2[0]);
                                    case "Spe" -> iv[5] = Integer.parseInt(IVSplit2[0]);
                                }
                            }
                        }
                        case "EVs" -> 
                        {
                            hasEVs = true;
                            String[] EVSplit1 = splits[1].split(" / ");
                            for (String evl : EVSplit1)
                            {
                                String[] EVSplit2 = evl.split(" ");
                                switch (EVSplit2[1]) 
                                {
                                    case "HP" -> ev[0] = Integer.parseInt(EVSplit2[0]);
                                    case "Atk" -> ev[1] = Integer.parseInt(EVSplit2[0]);
                                    case "Def" -> ev[2] = Integer.parseInt(EVSplit2[0]);
                                    case "SpA" -> ev[3] = Integer.parseInt(EVSplit2[0]);
                                    case "SpD" -> ev[4] = Integer.parseInt(EVSplit2[0]);
                                    case "Spe" -> ev[5] = Integer.parseInt(EVSplit2[0]);
                                }
                            }
                        }
                        default -> {
                        }
                    }
                    
                }
                else if (splits[0].startsWith("- ")) // moves
                {
                    for (int i = 0; i < moves.length; i++)
                    {
                        if (moves[i].equals("")) // replace next empty move slot
                        {
                            moves[i] = splits[0].substring(2); // remove the '- ';
                            break; // one replacement
                        }
                    }
                }
                else // natures only
                {
                    nature = splits[0].split(" ")[0]; // Hardy Nature -> Hardy
                }
            }
        }
        
        public void PrintPokemon()
        {
            System.out.println("--Pokemon--");
            System.out.println("type: "+type);
            System.out.println("form id: "+form);
            System.out.println("form name: "+formName);
            System.out.println("nickname: "+nickname);
            System.out.println("gender: "+gender);
            System.out.println("ability: "+ability);
            System.out.println("helditem: "+helditem);
            System.out.println("--Stats--");
            System.out.println("level: "+level);
            System.out.println("iv: "+Arrays.toString(iv));
            System.out.println("ev: "+Arrays.toString(ev));
            System.out.println("nature: "+nature);
            System.out.println("happiness: "+happiness);
            System.out.println("--Moves--");
            System.out.println("moves: "+Arrays.toString(moves));
            System.out.println("--Visual--");
            System.out.println("ball: "+ball);
            System.out.println("Shiny: "+Shiny);
            System.out.println("SuperShiny: "+SuperShiny);
            System.out.println("Shadow: "+Shadow);
            System.out.println("--Gimmicks--");
            System.out.println("dynamaxlevel: "+dynamaxlevel);
            System.out.println("gmax: "+gmax);
            System.out.println("teraType: "+teraType);
        }
        
        public void PrintPBS()
        {
            System.out.println("Pokemon = "+type + "," + level);
            if (form>0) System.out.println("    Form = "+form);
            if (!nickname.equals("")) System.out.println("    Name = "+nickname.substring(0, 10));
            if (!gender.equals("")) System.out.println("    Gender = "+gender);
            
            if (Shiny) System.out.println("    Shiny = true");
            if (SuperShiny) System.out.println("    SuperShiny = true");
            if (Shadow) System.out.println("    Shadow = true");
            if (!ability.equals("")) System.out.println("    Ability = "+ability);
            if (!helditem.equals("")) System.out.println("    Item = "+helditem);
            if (!moves[0].equals("")) 
            {
                System.out.print("    Moves = "+moves[0]);
                if (!moves[1].equals("")) System.out.print(","+moves[1]);
                if (!moves[2].equals("")) System.out.print(","+moves[2]);
                if (!moves[3].equals("")) System.out.print(","+moves[3]);
                System.out.println("");
            }
            System.out.println("    IV = "+iv[0]+","+iv[1]+","+iv[2]+","+iv[3]+","+iv[4]+","+iv[5]);
            if (hasEVs)
            {
                System.out.println("    EV = "+ev[0]+","+ev[1]+","+ev[2]+","+ev[3]+","+ev[4]+","+ev[5]);
            }
            if (!nature.equals("")) System.out.println("    Nature = "+nature);
            if (!teraType.equals("")) System.out.println("    TeraType = "+teraType);
            if (dynamaxlevel > 0) System.out.println("    DynamaxLv = "+dynamaxlevel);
            if (gmax) System.out.println("    Gigantamax = true");
            if (!ball.equals("")) System.out.println("    Ball = "+ball);
            if (happiness != 70) System.out.println("    Happiness = "+happiness);
        }
}
