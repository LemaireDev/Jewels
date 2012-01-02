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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import domain.Player;

/**
 *
 * @author Nick
 */
public class StandardPlayArea extends PlayArea{
    public void init() {

        timer = new Timer(25, this);
        timer.start();
        Toolkit.getDefaultToolkit().sync();
        this.pack();
        this.setLocation(200, 100);
        this.setResizable(false);
        this.setVisible(true);

        this.addWindowListener(new WindowListener() {

            public void windowOpened(WindowEvent we) {
            }
            public void windowClosing(WindowEvent we) {
                String name = JOptionPane.showInputDialog("You scored "+ StandardPlayArea.this.getScore()+" points!! \n Enter your name:");
                if(name!=null){
                    if(name.equals("")){
                        name = "Unknown";
                    }
                    parent.getDb().addPlayer(new Player(name,score,1));
                }
                StandardPlayArea.this.parent.setEnabled(true);
                StandardPlayArea.this.dispose();
            }
            public void windowClosed(WindowEvent we) {
            }
            public void windowIconified(WindowEvent we) {
            }
            public void windowDeiconified(WindowEvent we) {
            }
            public void windowActivated(WindowEvent we) {
            }
            public void windowDeactivated(WindowEvent we) {
            }
        });
    }
}
