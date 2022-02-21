/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package components;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/* MenuDemo.java requires images/middle.gif. */

/*
 * This class is just like MenuLookDemo, except the menu items
 * actually do something, thanks to event listeners.
 */
public class MenuDemo implements ActionListener {
    JTextArea output;
    JScrollPane scrollPane;
    String newline = "\n";

    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        // Obtenemos el HelpSet a traves del metodo creado para tal fin
        HelpSet hs = obtenFicAyuda();
        // Creamos el HelpBroker
        HelpBroker hb = hs.createHelpBroker();
        
        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("Ayuda"); // Modificamos el nombre del menu a "Ayuda"
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

        //a group of JMenuItems
        menuItem = new JMenuItem("Contenido de ayuda",
                                 KeyEvent.VK_F1);
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        // Con esto habilitamos la respuesta del componente a la pulsacion de la tecla F1, en combinacion con el metodo .enableHelpKey() de mas abajo
        //      conseguimos que se abra la ayuda de la aplicacion al pulsar la tecla F1
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F1, 0)); // Cambiamos la mascara del alt para que no sea necesaria la combinacion -> ActionEvent.ALT_MASK -> CAMBIO A -> 0 (Sin mascara)
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        // menuItem.addActionListener(this); Deshabilitamos el Listener que implementa la clase para este componente
        
        // Asociamos la ayuda al elemento de menu creado
        hb.enableHelpOnButton(menuItem, "init", hs);
        // Habilitamos la pulsacion de F1 para abrir la ayuda (para que se ejecute la accion asociada a este menuItem)
        hb.enableHelpKey(menuItem, "init", hs);
        
        /*
            Podemos habilitar la ayuda de cada componente con el siguiente comando
                hb.enableHelp(Componente, "nombre dado al topic en el fichero de mapeo (map.jhm)", HelpSet);
        */
        
        menu.add(menuItem);

        ImageIcon icon = createImageIcon("images/middle.gif");
        menuItem = new JMenuItem("About", icon); // Cambiamos nombre a about, dejamos el icono porque me resulta gracioso
        menuItem.setMnemonic(KeyEvent.VK_B);
        // Quitamos listener del actionPerformed
        menu.add(menuItem);

        menuBar.add(menu);

        return menuBar;
    }

    public Container createContentPane() {
        //Create the content-pane-to-be.
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);

        //Create a scrolled text area.
        output = new JTextArea(5, 30);
        output.setEditable(false);
        scrollPane = new JScrollPane(output);

        //Add the text area to the content pane.
        contentPane.add(scrollPane, BorderLayout.CENTER);

        return contentPane;
    }

    // En este metodo establecemos lo que haran las acciones que tienen como listener a esta misma clase (this)
    // No lo utilizaremos, por lo tanto la accion la dejamos vacía
    @Override
    public void actionPerformed(ActionEvent e) { }

    // Returns just the class name -- no package info.
    protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = MenuDemo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    // Metodo para lanzar la ayuda
    public HelpSet obtenFicAyuda() {
        
        // Gestionamos cualquier excepcion que pueda darse
        //      La carga de la URL podria generar la Excepcion -> MalformedURLException.
        try {
            // Instanciamos un cargador de clase
            ClassLoader cl = MenuDemo.class.getClassLoader();
            // Cargamos el fichero helpset.hs
            File file = new File(MenuDemo.class.getResource("/help/helpset.hs").getFile());
            // Obtenemos la URL del recurso helpset.hs
            URL url = file.toURI().toURL();
            // Creamos un objeto HelpSet, a traves del cual crearemos el HelpBroker (Gestor de ayuda)
            HelpSet hs = new HelpSet(null, url);
            return hs;
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Fichero HelpSet no encontrado");
            return null;
        }
        
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("LanzaAyuda");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        MenuDemo demo = new MenuDemo();
        frame.setJMenuBar(demo.createMenuBar());
        frame.setContentPane(demo.createContentPane());

        //Display the window.
        frame.setSize(450, 260);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
