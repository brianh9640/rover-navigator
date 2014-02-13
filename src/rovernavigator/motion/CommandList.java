/*
 * Project:     Rover-Navigator
 * Description: Science Olympiad Rover Navigator Scoring Program
 * 
 * File:        motion/CommandList.java
 * 
 * Created:     June 2013
 * 
 * Repository:  https://github.com/brianh9640/rover-navigator
 * 
 */
package rovernavigator.motion;

import java.text.DecimalFormat;

/**
 *
 * @author Brian
 */
public class CommandList {
    
    public static final int MAX_COMMANDS            = 500;
    
    public static final int COMMAND_NOP             = 0;
    public static final int COMMAND_LEFT            = 1;
    public static final int COMMAND_RIGHT           = 2;
    public static final int COMMAND_FORWARD         = 3;
    public static final int COMMAND_BACKWARD        = 4;
    public static final int COMMAND_TEST            = 5;
    
    public static final String PROGRAM_EXTENSION    = "xpg";
    
    public String teamNo;
    public String school;
    
    public int count;
    public int type[];
    public double value[];
    public String note[];

    DecimalFormat formatAngle;
    DecimalFormat formatDistance;
    
    public CommandList() {
        formatAngle = new DecimalFormat("###");
        formatDistance = new DecimalFormat("###.0");
        
        teamNo = "";
        school = "";
        
        clear();
    }
    
    public void clear() {
        count = 0;
        
        type  = new int[MAX_COMMANDS+1];
        value = new double[MAX_COMMANDS+1];
        note = new String[MAX_COMMANDS+1];
        
        for (int c=0;c<=MAX_COMMANDS;c++) {
            type[c] = 0;
            value[c] = 0.0;
            note[c] = "";
        }
    }

    public int getCount() { return count; }
    
    public int getType(int c) {
        if (c <= 0 || c > count) return 0;
        
        return type[c];
    }
    
    public double getValue(int c) {
        if (c <= 0 || c > count) return 0;
        
        return value[c];
    }
    
    public int modType(int c,int type) {
        if (c <= 0 || c > count+1) return 0;
        
        if (c == count+1) {
            return add(type,0.0);
        }
        
        System.out.println("Mod Cmd[" + c + "] = " + type);
        this.type[c] = type;
        
        return count;
    }
    
    public int modValue(int c,int type,double value) {
        if (c <= 0 || c > count+1) return 0;
        
        if (c == count+1) {
            return add(type,value);
        }
        
        System.out.println("Mod Cmd[" + c + "] = " + type);
        this.type[c] = type;
        this.value[c] = value;
        
        return count;
    }
    
    public int add(int type,double value) {
        return add(type,value,"");
    }
    
    public int add(int type,String note) {
        return add(type,0.0,note);
    }
        
    public int add(int type,double value,String note) {
        if (count >= MAX_COMMANDS) return count;

        if (type < 0 || type > COMMAND_TEST) return count;
        count++;
        
        this.type[count] = type;
        this.value[count] = value;
        this.note[count] = note;
        
        return count;
    }
    
    public String outputCommandText(int c) {
        String textCmd = "";
        
        if (c <= 0 || c > count) return textCmd;
        
        switch (type[c]) {
            case CommandList.COMMAND_LEFT :
                textCmd += "LEFT ";
                textCmd += formatAngle.format(value[c]);
                break;
            case CommandList.COMMAND_RIGHT :
                textCmd += "RIGHT ";
                textCmd += formatAngle.format(value[c]);
                break;
            case CommandList.COMMAND_FORWARD :
                textCmd += "FORWARD ";
                textCmd += formatDistance.format(value[c]);
                break;
            case CommandList.COMMAND_TEST :
                textCmd += "TEST ";
                textCmd += note[c];
                break;
        }
        return textCmd;
    }
    
    
}
