package proyectoGeorge;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class OrderCellRenderer extends JLabel implements ListCellRenderer<Order> {
	public OrderCellRenderer() {
		setOpaque(false);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Order> list,
			Order value, int index, boolean isSelected, boolean cellHasFocus) {
		setText(value.getUser().getName()+" Requested: "+value.getOrderSize()+" "+value.getWeapon().getBrand()+" "+value.getWeapon().getModel()+" on the "+value.getDate());

        setForeground(new Color(102, 153, 51));
        setOpaque(isSelected);

		return this;
	}
}