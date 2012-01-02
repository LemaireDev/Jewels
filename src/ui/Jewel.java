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

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Nick
 */
public class Jewel extends JLabel {
    private String color;
    private Icon pic;
    private boolean animated;
    
    public Jewel(String color){
        this.setPic(new ImageIcon(this.getClass().getResource("gems/" + color + ".png")));
        this.setColor(color);
        this.setIcon(this.getPic());
    }
    public String getColor(){
        return this.color;
    }
    
    public void setColor(String color){
        this.color = color;
    }
    
    public void setPic(Icon icon){
        this.pic = icon;
    }
    
    public Icon getPic(){
        return this.pic;
    }
    
    public void setAnimated(){
        this.animated=true;
        this.setIcon(new ImageIcon(this.getClass().getResource("gems/animated/" + color + ".gif")));
    }
    
    public void setNotAnimated(){
        this.setIcon(pic);
        this.animated=false;
    }
    
    public boolean isAnimated(){
        return this.animated;
    }
}
