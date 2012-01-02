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
package db;

/**
 *
 * @author Nick Lemaire
 */
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import domain.Player;

public abstract class AbstractDatabase extends AbstractListModel{
    
    private List<Player> playerlist = new ArrayList<Player>();
    private Player selectedItem;

    public abstract void close() throws DatabaseException;

    public List<Player> getAllPlayers() {
        return this.playerlist;
    }


    public boolean addPlayer(Player player) {
        boolean res = false;
        if(player!=null){
            res = this.playerlist.add(player);
        }
        return res;
    }

    private boolean hasPlayer(Player p){
        return playerlist.contains(p);
    }

    public Object getElementAt(int i) {
        return this.playerlist.get(i);
    }

    public int getSize() {
        return this.playerlist.size();
    }

    public Object getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Object o) {
        this.selectedItem = (Player)o;
    }



}
