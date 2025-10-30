/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package showdowntotrainer;

/**
 *
 * @author codem
 */
public class Triple {
    String value1;
    String value2;
    int form;
    public Triple(String in1, String in2, int formID)
    {
        value1 = in1;
        value2 = in2;
        form = formID;
    }
    
    public boolean inTriple(String in)
    {
        return in.equals(value1) || in.equals(value2);
    }
    
    public String swap(String in)
    {
        if (in.equals(value1)) return value2;
        else if (in.equals(value2)) return value1;
        return "No Equivalent Found";
    }
    
    public int getID()
    {
        return form;
    }
}
