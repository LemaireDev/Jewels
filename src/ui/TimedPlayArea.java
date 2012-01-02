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
public class TimedPlayArea extends PlayArea {
    private int count=60;
    private JLabel countlabel=new JLabel();
    Timer timera;

    public void init() {
        
        countlabel.setText("Time left: "+this.getCount());
        countlabel.setFont(new Font("Verdana",Font.BOLD,21));
        con.fill = GridBagConstraints.HORIZONTAL;
        con.gridx = 6;
        con.gridy = 9;
        con.gridwidth=3;
        area.add(countlabel, con);

        timer = new Timer(25, this);
        timer.start();
        this.initTimer();
        Toolkit.getDefaultToolkit().sync();
        this.pack();
        this.setLocation(200, 100);
        this.setResizable(false);
        this.setVisible(true);

        this.addWindowListener(new WindowListener() {

            public void windowOpened(WindowEvent we) {
            }
            public void windowClosing(WindowEvent we) {
                String name = JOptionPane.showInputDialog("Time's up!\nYou scored "+ TimedPlayArea.this.getScore()+" points!! \n\nEnter your name:");
                if(name!=null){
                    if(name.equals("")){
                        name = "Unknown";
                    }
                    parent.getDb().addPlayer(new Player(name,score,2));
                }
                TimedPlayArea.this.parent.setEnabled(true);
                TimedPlayArea.this.dispose();
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

    public void initTimer() {
        timera = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TimedPlayArea.this.count--;
                
                if(count<0){
                    timera.stop();
                    TimedPlayArea.this.processWindowEvent(
                 new WindowEvent(TimedPlayArea.this, WindowEvent.WINDOW_CLOSING));
                } else {
                    countlabel.setText("Time left: "+TimedPlayArea.this.getCount());
                    if(count<=10){
                        countlabel.setForeground(Color.red);
                    }
                }
            }
        });
        timera.start();
    }
    
    public int getCount(){
        return this.count;
    }
    
}
