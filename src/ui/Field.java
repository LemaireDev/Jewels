/** Copyright 2011 Nick Lemaire

This file is part of Jewels.

    Jewels is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation version 3.

    Jewels is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Jewels.  If not, see <http://www.gnu.org/licenses/>.
*/
package ui;

import javax.swing.JPanel;

/**
 *
 * @author Nick
 */
public class Field extends JPanel {
    private boolean hasJewel=true;
    private Jewel jewel;
    private boolean markedfordel;
    private boolean isSelected;
    
    public boolean getHasJewel(){
        return hasJewel;
    }
    
    public void setHasJewel(boolean mark){
        this.hasJewel=mark;
    }
    
    public boolean getIsSelected(){
        return isSelected;
    }
    
    public void setIsSelected(boolean selected){
        this.isSelected=selected;
    }
    
    public Jewel getJewel(){
        return this.jewel;
    }
    
    public void setMarkedForDel(boolean mark){
        this.markedfordel=mark;
    }
    
    public boolean isMarkedForDel(){
        return markedfordel;
    }
    
    public void setJewel(Jewel jewel){
            this.jewel=jewel;
            this.add(jewel);
            this.setHasJewel(true);
    }
    
    public void setNotAnimated(){
        if(this.jewel!=null){
            this.getJewel().setNotAnimated();
        }
    }
    
    public void setAnimated(){
        if(this.jewel!=null){
            this.jewel.setAnimated();
        }
    }
    
    public void clearJewel(){
        this.setHasJewel(false);
        if(this.jewel!=null){
            this.remove(this.jewel);
        }
        this.jewel=null;
    }
    
    public String getColor(){
        String res = "";
        if(jewel!=null){
            res = jewel.getColor();
        }
        return res;
    }
}
