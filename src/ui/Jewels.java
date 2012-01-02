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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;
import db.Database;
import db.DatabaseException;

/**
 *
 * @author Nick
 */
public class Jewels extends JFrame implements WindowListener {
    private Container container = this.getContentPane();
    private JMenuBar menubar = new JMenuBar();
    private JMenu menu = new JMenu("Options");
    private JMenu menu2 = new JMenu("Info");
    private JMenuItem start = new JMenuItem("New Standard Game");
    private JMenuItem startt = new JMenuItem("New Timed Game");
    private JMenuItem close = new JMenuItem("Close");
    private JMenuItem about = new JMenuItem("About");
    private JMenuItem high = new JMenuItem("High Scores");
    private JPanel bgim;
    private JButton startb = new JButton("Start standard game");
    private JButton starttb = new JButton("Start timed game");
    private JButton viewscore = new JButton("View high scores");
    private Database db;
    
    public Jewels() throws DatabaseException{
        super("Jewels");
        db = Database.getInstance();
        this.setJMenuBar(menubar);
        menubar.add(menu);
        menubar.add(menu2);
        menu.add(start);
        menu.add(startt);
        menu.add(close);
        menu2.add(high);
        menu2.add(about);
        this.setLayout(new GridLayout(3,1));
        container.add(startb);
        container.add(starttb);
        container.add(viewscore);
        addWindowListener(this);
        setLocationRelativeTo(null);
        this.setPreferredSize(new Dimension(200,200));
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                PlayArea area = new StandardPlayArea();
                area.setParent(Jewels.this);
                area.template();
            }
        });
        
        startb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                PlayArea area = new StandardPlayArea();
                area.setParent(Jewels.this);
                area.template();
            }
        });
        
        startt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                PlayArea area = new TimedPlayArea();
                area.setParent(Jewels.this);
                area.template();
            }
        });
        
        starttb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                PlayArea area = new TimedPlayArea();
                area.setParent(Jewels.this);
                area.template();
            }
        });
        
        high.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                getScores();
            }
        });
        
        viewscore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                getScores();
            }
        });

        about.setAction(new AbstractAction("About") {

            public void actionPerformed(ActionEvent e) {
                clickAbout();
            }
        });
        close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e1) {
                close();
            }
        });
    
        this.pack();
        this.setVisible(true);
    }
    
    private void getScores(){
        Scores scores = new Scores();
        scores.setParent(Jewels.this);
        scores.init();
    }

     private void clickAbout() throws HeadlessException {
        JOptionPane.showMessageDialog(null, "Jewels\n\nThe objective of this game is to swap one jewel with an adjacent jewel to form a horizontal\nor vertical chain of three or more jewels of the same color.\n"
                +"Jewels disappear when chains are formed and jewels fall from the top to fill in gaps.\n\nTimed mode gives you one minute to score as much points as you can.");
    }
     
     public Database getDb(){
         return this.db;
     }

    private void close() {
        Jewels.this.processWindowEvent(new WindowEvent(Jewels.this, WindowEvent.WINDOW_CLOSING));
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        int res = JOptionPane.showConfirmDialog(null,"Are you sure you want to close the application?","Exit?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(res==JOptionPane.YES_OPTION){
            try {
                    db.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Jewels.this, "An error occured, your last scores won't be saved.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                System.exit(0);
            System.exit(0);
        }
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }
}

