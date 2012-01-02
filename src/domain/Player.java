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

package domain;

/**
 *
 * @author Nick
 */
public class Player implements Comparable<Player>{
    public String name;
    public int score;
    public int type; //1 = standard, 2 = timed

    public Player(String name, int score, int type) {
        this.setName(name);
        this.setScore(score);
        this.setType(type);
    }

    public void setName(String name) {
        if(name!=null){
            this.name=name;
        }
    }

    public void setScore(int score) {
        this.score=score;
    }
    
    public int getScore(){
        return this.score;
    }
    
    public String getName(){
        return this.name;
    }
    
    public int getType(){
        return this.type;
    }
    
    public void setType(int type){
        if(type==1||type==2){
            this.type=type;
        }
    }

    @Override
    public int compareTo(Player o) {
        int res = 0;
        if(this.getScore() > o.getScore()) res = -1;
        if(this.getScore() < o.getScore()) res = 1;
        return res;
    }
    
}
