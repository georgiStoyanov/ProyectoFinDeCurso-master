package proyectoGeorge;
//////----------------------->ORDER IS NOW REQUEST IN THE SHOP<-------------------------
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OrderWindow extends JFrame {

	private static EntityManager em;
	private WeaponManagerHibernate managerWeapon;
	public JTextField txtCategory;
	public JTextField txtBrand;
	public JTextField txtModel;
	public JTextField txtPrice;
	public JLabel lblCom;
	private static User u;
	public JTextField txtPriceDecimals;
	public JLabel lblCuantity;
	public JTextField txtQuantity;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			OrderWindow dialog = new OrderWindow(u,em,null);
			dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void setImageIcon() {
		URL iconImageURL=this.getClass().getResource("/source/frameIcon.png");
		Image icon=null;
		try {
			icon = ImageIO.read(iconImageURL);
		} catch (IOException e2) {
			System.out.println("IOException!Icon image does not exist!");
		}
		setIconImage(icon);
	}
	private void setBackground() {
		URL bgImage=this.getClass().getResource("/source/bgShop.png");
		BufferedImage bg=null;
		try {
			bg = ImageIO.read(bgImage);
		} catch (IOException e) {
			System.out.println("Background image does not exist!");
		}
		ImagePanel imagePanel = new ImagePanel(bg);
		setContentPane(	imagePanel);
	}
	/**
	 * Create the dialog.
	 */
	public OrderWindow(final User u,final EntityManager _em,final Weapon w) {
		this.u=u;
		this.em=_em;
		this.managerWeapon=new WeaponManagerHibernate(_em);
		setImageIcon();
		setBackground();
		setTitle("Ordering");
		setBounds(100, 100, 459, 292);
		Dimension minimumSize=new Dimension(458,296);
		this.setMinimumSize(minimumSize);
		getContentPane().setLayout(null);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{123, 95, 56, 34, 19, 84, 0};
		gridBagLayout.rowHeights = new int[]{20, 20, 20, 20, 21, 23, 31, 14, 23, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		JLabel lblCategory = new JLabel("Category:");
		lblCategory.setForeground(new Color(102, 153, 51));
		lblCategory.setFont(new Font("Aharoni", Font.PLAIN, 14));
		lblCategory.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCategory.setToolTipText("Category of the weapon.");
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.gridx = 0;
		gbc_lblCategory.gridy = 0;
		getContentPane().add(lblCategory, gbc_lblCategory);


		txtCategory = new JTextField();
		txtCategory.setEditable(false);
		GridBagConstraints gbc_txtCategory = new GridBagConstraints();
		gbc_txtCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCategory.insets = new Insets(0, 0, 5, 0);
		gbc_txtCategory.gridwidth = 5;
		gbc_txtCategory.gridx = 1;
		gbc_txtCategory.gridy = 0;
		getContentPane().add(txtCategory, gbc_txtCategory);
		txtCategory.setColumns(10);

		JLabel lblManufacturer = new JLabel("Manufacturer:");
		lblManufacturer.setForeground(new Color(102, 153, 51));
		lblManufacturer.setFont(new Font("Aharoni", Font.PLAIN, 14));
		lblManufacturer.setHorizontalAlignment(SwingConstants.RIGHT);
		lblManufacturer.setToolTipText("Manufacturer of the item.... ");
		GridBagConstraints gbc_lblManufacturer = new GridBagConstraints();
		gbc_lblManufacturer.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblManufacturer.insets = new Insets(0, 0, 5, 5);
		gbc_lblManufacturer.gridx = 0;
		gbc_lblManufacturer.gridy = 1;
		getContentPane().add(lblManufacturer, gbc_lblManufacturer);

		txtBrand = new JTextField();
		txtBrand.setEditable(false);
		GridBagConstraints gbc_txtBrand = new GridBagConstraints();
		gbc_txtBrand.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBrand.insets = new Insets(0, 0, 5, 0);
		gbc_txtBrand.gridwidth = 5;
		gbc_txtBrand.gridx = 1;
		gbc_txtBrand.gridy = 1;
		getContentPane().add(txtBrand, gbc_txtBrand);
		txtBrand.setColumns(10);

		JLabel lblModel = new JLabel("Model:");
		lblModel.setForeground(new Color(102, 153, 51));
		lblModel.setFont(new Font("Aharoni", Font.PLAIN, 14));
		lblModel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblModel.setToolTipText("Model of the wapon.");
		GridBagConstraints gbc_lblModel = new GridBagConstraints();
		gbc_lblModel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblModel.insets = new Insets(0, 0, 5, 5);
		gbc_lblModel.gridx = 0;
		gbc_lblModel.gridy = 2;
		getContentPane().add(lblModel, gbc_lblModel);

		txtModel = new JTextField();
		txtModel.setEditable(false);
		GridBagConstraints gbc_txtModel = new GridBagConstraints();
		gbc_txtModel.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtModel.insets = new Insets(0, 0, 5, 0);
		gbc_txtModel.gridwidth = 5;
		gbc_txtModel.gridx = 1;
		gbc_txtModel.gridy = 2;
		getContentPane().add(txtModel, gbc_txtModel);
		txtModel.setColumns(10);

		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setForeground(new Color(102, 153, 51));
		lblPrice.setToolTipText("Model of the wapon.");
		lblPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrice.setFont(new Font("Aharoni", Font.PLAIN, 14));
		GridBagConstraints gbc_lblPrice = new GridBagConstraints();
		gbc_lblPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrice.gridx = 0;
		gbc_lblPrice.gridy = 3;
		getContentPane().add(lblPrice, gbc_lblPrice);

		txtPrice = new JTextField();
		txtPrice.setEditable(false);
		txtPrice.setColumns(10);
		GridBagConstraints gbc_txtPrice = new GridBagConstraints();
		gbc_txtPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPrice.insets = new Insets(0, 0, 5, 5);
		gbc_txtPrice.gridwidth = 3;
		gbc_txtPrice.gridx = 1;
		gbc_txtPrice.gridy = 3;
		getContentPane().add(txtPrice, gbc_txtPrice);

		JLabel label = new JLabel(",");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 3;
		gbc_label.gridy = 3;
		getContentPane().add(label, gbc_label);

		txtPriceDecimals = new JTextField();
		txtPriceDecimals.setEditable(false);
		GridBagConstraints gbc_txtPriceDecimals = new GridBagConstraints();
		gbc_txtPriceDecimals.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPriceDecimals.insets = new Insets(0, 0, 5, 0);
		gbc_txtPriceDecimals.gridwidth = 2;
		gbc_txtPriceDecimals.gridx = 4;
		gbc_txtPriceDecimals.gridy = 3;
		getContentPane().add(txtPriceDecimals, gbc_txtPriceDecimals);
		txtPriceDecimals.setColumns(10);

		lblCuantity = new JLabel("Quantity:");
		lblCuantity.setToolTipText("Model of the wapon.");
		lblCuantity.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCuantity.setForeground(new Color(102, 153, 51));
		lblCuantity.setFont(new Font("Aharoni", Font.PLAIN, 14));
		GridBagConstraints gbc_lblCuantity = new GridBagConstraints();
		gbc_lblCuantity.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCuantity.insets = new Insets(0, 0, 5, 5);
		gbc_lblCuantity.gridx = 0;
		gbc_lblCuantity.gridy = 4;
		getContentPane().add(lblCuantity, gbc_lblCuantity);

		txtQuantity = new JTextField();
		txtQuantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				checkAvailable(w);
			}
		});
		txtQuantity.setToolTipText("In ARABIC NUMBERS.");
		txtQuantity.setColumns(10);
		GridBagConstraints gbc_txtCuantity = new GridBagConstraints();
		gbc_txtCuantity.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCuantity.insets = new Insets(0, 0, 5, 0);
		gbc_txtCuantity.gridwidth = 5;
		gbc_txtCuantity.gridx = 1;
		gbc_txtCuantity.gridy = 4;
		getContentPane().add(txtQuantity, gbc_txtCuantity);

		lblCom = new JLabel("");
		lblCom.setForeground(new Color(102, 153, 51));
		lblCom.setFont(new Font("Aharoni", Font.PLAIN, 12));
		GridBagConstraints gbc_lblCom = new GridBagConstraints();
		gbc_lblCom.fill = GridBagConstraints.BOTH;
		gbc_lblCom.insets = new Insets(0, 0, 5, 0);
		gbc_lblCom.gridwidth = 6;
		gbc_lblCom.gridx = 0;
		gbc_lblCom.gridy = 7;
		getContentPane().add(lblCom, gbc_lblCom);

		JButton btnOk = new JButton("OK");
		btnOk.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
					orderWeapon(_em, w);
				}
			}
		});
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				orderWeapon(_em, w);
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOk.anchor = GridBagConstraints.NORTH;
		gbc_btnOk.insets = new Insets(0, 0, 0, 5);
		gbc_btnOk.gridwidth = 2;
		gbc_btnOk.gridx = 3;
		gbc_btnOk.gridy = 8;
		getContentPane().add(btnOk, gbc_btnOk);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Shop backToShop=new Shop(u,_em);
				backToShop.setVisible(true);
				OrderWindow.this.dispose();
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Shop backToShop=new Shop(u,_em);
				backToShop.setVisible(true);
				OrderWindow.this.dispose();
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancel.anchor = GridBagConstraints.NORTH;
		gbc_btnCancel.gridx = 5;
		gbc_btnCancel.gridy = 8;
		getContentPane().add(btnCancel, gbc_btnCancel);


	}
	protected boolean checkAvailable(Weapon w) {
		int quantity=0;
		try {
			quantity=Integer.parseInt(txtQuantity.getText());
			lblCom.setForeground(new Color(102, 153, 51));
			lblCom.setText("*You are about to order : "+w.getBrand()+" "+w.getModel()+" on a price of "+w.getPrice());
		} catch (Exception e) {
			lblCom.setForeground(Color.RED);
			lblCom.setText("*Put NUMBERS in quantity field please!");
			return false;
		}
		if(quantity>w.getQuantity()){
			lblCom.setForeground(Color.RED);
			lblCom.setText("*We don't have that much in stock! Available :"+w.getQuantity());
		}
		else{
			return true;
		}
		return false;

	}
	protected void addItem(EntityManager thatEm) {
		String category=txtCategory.getText();
		String brand=txtBrand.getText();
		String model=txtModel.getText();
		int quantity=0;
		try {
			quantity=Integer.parseInt(txtQuantity.getText());
		} catch (Exception e) {
			lblCom.setText("Invalid cuantity.");
		}
		if(category==null || brand==null || model==null){
			lblCom.setText("*You forget something");
		}
		else{
			Weapon w=new Weapon();
			w.setCategory(category);
			w.setBrand(brand);
			w.setModel(model);
			w.setPrice(checkPrices(txtPrice.getText(), txtPriceDecimals.getText()));
			w.setQuantity(quantity);
			System.out.println(thatEm.toString());
			EntityTransaction tx = thatEm.getTransaction();
			tx.begin();
			System.err.println("New weapon:" +w);
			managerWeapon.insertWeapon(w);
			tx.commit();
			OrderWindow.this.dispose();
			Shop backToShop=new Shop(u, em);
			backToShop.setVisible(true);
			OrderWindow.this.dispose();
		}
	}
	public void orderWeapon(EntityManager thatEm,Weapon w){
		if(checkAvailable(w)){
			String category=txtCategory.getText();
			String brand=txtBrand.getText();
			String model=txtModel.getText();
			OrderManagerHibernate managerOrder= new OrderManagerHibernate(thatEm);
			UserManagerHibernate managerUser=new UserManagerHibernate(thatEm);
			Order o=new Order();
			o.setUser(u);
			o.setWeapon(w);
			int qOrdered=0;
			try {
				qOrdered=Integer.parseInt(txtQuantity.getText());
			} catch (Exception e) {
				lblCom.setText("Alien invasion!");
			}
			o.setOrderSize(qOrdered);
			w.setQuantity(w.getQuantity()-qOrdered);
			u.addOrder(o);
			w.addOrder(o);
			EntityTransaction tx = thatEm.getTransaction();
			tx.begin();
			System.out.println("Ordering : " +o +" by user"+u);
			managerWeapon.updateWeapon(w);
			managerUser.updateUser(u);
			managerOrder.updateOrder(o);
			tx.commit();
			OrderWindow.this.dispose();
			Shop backToShop=new Shop(u, em);
			backToShop.setVisible(true);
			OrderWindow.this.dispose();
		}
		else{
			lblCom.setText("Press cancel to exit or check your quantity again!Available:"+w.getQuantity());
		}
	}
	private double checkPrices(String full,String decimals) {
		if(full==null && decimals==null){
			full="0";
			decimals="0";
			String price=full+"."+decimals;
			double priceDouble=Double.parseDouble(price);
			System.out.println("Precio:"+priceDouble);
			return priceDouble;
		}
		if(full==null){
			full="0";
			String price=full+"."+decimals;
			return Double.parseDouble(price);
		}
		if(decimals==null){
			decimals="0";
			String price=full+"."+decimals;
			return Double.parseDouble(price);
		}
		String price=full+"."+decimals;
		return Double.parseDouble(price);
	}
}