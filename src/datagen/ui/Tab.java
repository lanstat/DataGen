/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datagen.ui;

import javax.swing.JPanel;

/**
 *
 * @author juan.garson
 */
public abstract class Tab extends JPanel{
    
    public abstract void refreshContent(Object data);
}
