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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import domain.Player;

/**
 *
 * @author Nick
 */
public class Database extends AbstractDatabase{
    private static volatile Database db;
    private File xmlfile;
    
    private Database() throws DatabaseException{
        super();
        try {
            String location = Database.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString();
            int lastslash = location.lastIndexOf("/");
            location = location.substring(0, lastslash);

            this.xmlfile = new File(location + "/Scores.xml");
            boolean created = xmlfile.createNewFile();
            if(!created && xmlfile.exists()){
                load();
            } else if(!xmlfile.exists()){
                throw new DatabaseException();
            }
        } catch (IOException ex) {
            try {
                this.xmlfile = new File("Scores.xml");
                boolean created = xmlfile.createNewFile();
                if (!created && xmlfile.exists()) {
                    load();
                } else if (!xmlfile.exists()) {
                    throw new DatabaseException();
                }
            } catch (IOException ex1) {
                throw new DatabaseException();
            }
        } catch (URISyntaxException ex) {
            throw new DatabaseException();
        }
    }
    
    public static Database getInstance() throws DatabaseException{
        if (Database.db == null) {
            synchronized (Database.class) {
                if (Database.db == null) {
                    Database.db = new Database();
                }
            }
        }
        return Database.db;
    }

    private void load() throws DatabaseException{
        try{
            SAXBuilder builder = new SAXBuilder(false);
            Document doc = builder.build(xmlfile);
            Element rootElement = doc.getRootElement();
            List<Element> players = rootElement.getChildren("player");
            for(Element e :players){
                String name = e.getChildText("name");
                int score = Integer.parseInt(e.getChildText("score"));
                int type = Integer.parseInt(e.getChildText("type"));
                Player player = new Player(name, score,type);
                this.addPlayer(player);
                
            }
        } catch (JDOMException ex) {
            throw new DatabaseException();
        } catch (IOException ex) {
            throw new DatabaseException();
        }
    }

    @Override
    public void close() throws DatabaseException {
        OutputStream stream = null;
        try {
            XMLOutputter out = new XMLOutputter();
            stream = new FileOutputStream(xmlfile);

            Format format = out.getFormat();
            format.setIndent("    ");
            format.setLineSeparator("\r\n");

            out.setFormat(format);
            
            Element rootElement = new Element("users");
            Document doc = new Document(rootElement);
            for (Player player : this.getAllPlayers()) {
                Element play = new Element("player");
                rootElement.addContent(play);
                Element name = new Element("name");
                name.setText(player.getName());
                play.addContent(name);
                Element score = new Element("score");
                score.setText(String.valueOf(player.getScore()));
                play.addContent(score);
                Element type = new Element("type");
                type.setText(String.valueOf(player.getType()));
                play.addContent(type);
            }

            out.output(doc, stream);
        } catch (IOException ex) {
            throw new DatabaseException();
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                throw new DatabaseException();
            }
        }
    }
    
}
