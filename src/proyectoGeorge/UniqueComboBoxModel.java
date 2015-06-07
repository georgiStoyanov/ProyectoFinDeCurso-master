package proyectoGeorge;

import javax.swing.DefaultComboBoxModel;

/**
 * ComboBox model to ensure only unique items can be added.
 */
public class UniqueComboBoxModel extends DefaultComboBoxModel {
    public void addElement(Object o) {
        if (this.getIndexOf(o) == -1)
            super.addElement(o);
    }
}