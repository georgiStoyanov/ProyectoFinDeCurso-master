package proyectoGeorge;

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

public class WeaponEditor extends JFrame {

	private static EntityManager em;
	private WeaponManagerHibernate manager;
	public JTextField txtCategory;
	public JTextField txtBrand;
	public JTextField txtModel;
	public JTextField txtPrice;
	public JCheckBox chActive;
	public JLabel lblCom;
	private static User u;
	public JTextField txtPriceDecimals;
	private JLabel lblCuantity;
	public JTextField txtCuantity;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WeaponEditor dialog = new WeaponEditor(u,em,false,null);
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
		setContentPane(	new ImagePanel(bg));
	}
	/**
	 * Create the dialog.
	 */
	public WeaponEditor(final User u,final EntityManager _em,final boolean isEdited,final Weapon w) {
		this.u=u;
		this.em=_em;
		this.manager=new WeaponManagerHibernate(_em);
		setImageIcon();
		setBackground();
		setTitle("Weapon editor");
		setBounds(100, 100, 466, 301);
		Dimension minimumSize=new Dimension(458,296);
		this.setMinimumSize(minimumSize);
		getContentPane().setLayout(null);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{123, 95, 56, 48, 19, 84, 0};
		gridBagLayout.rowHeights = new int[]{17, 21, 20, 20, 21, 23, 31, 14, 23, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
				
						JLabel lblCategory = new JLabel("Category:");
						lblCategory.setForeground(new Color(102, 153, 51));
						lblCategory.setFont(new Font("Aharoni", Font.PLAIN, 14));
						lblCategory.setHorizontalAlignment(SwingConstants.RIGHT);
						lblCategory.setToolTipText("Category of the weapon.If others type others");
						GridBagConstraints gbc_lblCategory = new GridBagConstraints();
						gbc_lblCategory.fill = GridBagConstraints.BOTH;
						gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
						gbc_lblCategory.gridx = 0;
						gbc_lblCategory.gridy = 0;
						getContentPane().add(lblCategory, gbc_lblCategory);
						
						
								txtCategory = new JTextField();
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
												gbc_lblManufacturer.fill = GridBagConstraints.BOTH;
												gbc_lblManufacturer.insets = new Insets(0, 0, 5, 5);
												gbc_lblManufacturer.gridx = 0;
												gbc_lblManufacturer.gridy = 1;
												getContentPane().add(lblManufacturer, gbc_lblManufacturer);
								
										txtBrand = new JTextField();
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
										lblModel.setToolTipText("Model of the wapon.If others type others");
										GridBagConstraints gbc_lblModel = new GridBagConstraints();
										gbc_lblModel.fill = GridBagConstraints.BOTH;
										gbc_lblModel.insets = new Insets(0, 0, 5, 5);
										gbc_lblModel.gridx = 0;
										gbc_lblModel.gridy = 2;
										getContentPane().add(lblModel, gbc_lblModel);
								
										txtModel = new JTextField();
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
								lblPrice.setToolTipText("Model of the wapon.If others type others");
								lblPrice.setHorizontalAlignment(SwingConstants.RIGHT);
								lblPrice.setFont(new Font("Aharoni", Font.PLAIN, 14));
								GridBagConstraints gbc_lblPrice = new GridBagConstraints();
								gbc_lblPrice.fill = GridBagConstraints.BOTH;
								gbc_lblPrice.insets = new Insets(0, 0, 5, 5);
								gbc_lblPrice.gridx = 0;
								gbc_lblPrice.gridy = 3;
								getContentPane().add(lblPrice, gbc_lblPrice);
								
										txtPrice = new JTextField();
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
						GridBagConstraints gbc_txtPriceDecimals = new GridBagConstraints();
						gbc_txtPriceDecimals.fill = GridBagConstraints.HORIZONTAL;
						gbc_txtPriceDecimals.insets = new Insets(0, 0, 5, 0);
						gbc_txtPriceDecimals.gridwidth = 2;
						gbc_txtPriceDecimals.gridx = 4;
						gbc_txtPriceDecimals.gridy = 3;
						getContentPane().add(txtPriceDecimals, gbc_txtPriceDecimals);
						txtPriceDecimals.setColumns(10);
				
						chActive = new JCheckBox("Active");
						chActive.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) {
								if(e.getKeyCode()==KeyEvent.VK_ENTER){
									chActive.setSelected(true);
								}
							}
						});
						
								lblCuantity = new JLabel("Quantity:");
								lblCuantity.setToolTipText("Model of the wapon.If others type others");
								lblCuantity.setHorizontalAlignment(SwingConstants.RIGHT);
								lblCuantity.setForeground(new Color(102, 153, 51));
								lblCuantity.setFont(new Font("Aharoni", Font.PLAIN, 14));
								GridBagConstraints gbc_lblCuantity = new GridBagConstraints();
								gbc_lblCuantity.fill = GridBagConstraints.BOTH;
								gbc_lblCuantity.insets = new Insets(0, 0, 5, 5);
								gbc_lblCuantity.gridx = 0;
								gbc_lblCuantity.gridy = 4;
								getContentPane().add(lblCuantity, gbc_lblCuantity);
						
								txtCuantity = new JTextField();
								txtCuantity.setToolTipText("In numbers.");
								txtCuantity.setColumns(10);
								GridBagConstraints gbc_txtCuantity = new GridBagConstraints();
								gbc_txtCuantity.fill = GridBagConstraints.HORIZONTAL;
								gbc_txtCuantity.insets = new Insets(0, 0, 5, 0);
								gbc_txtCuantity.gridwidth = 5;
								gbc_txtCuantity.gridx = 1;
								gbc_txtCuantity.gridy = 4;
								getContentPane().add(txtCuantity, gbc_txtCuantity);
						chActive.setFont(new Font("Aharoni", Font.PLAIN, 11));
						chActive.setForeground(new Color(102, 153, 51));
						GridBagConstraints gbc_chActive = new GridBagConstraints();
						gbc_chActive.insets = new Insets(0, 0, 5, 5);
						gbc_chActive.gridx = 1;
						gbc_chActive.gridy = 5;
						getContentPane().add(chActive, gbc_chActive);
						chActive.setOpaque(false);
		
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
							if(isEdited){
								editWeapon(_em, w);
							}
							else{
								addItem(_em);
							}
						}
					}
				});
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(isEdited){
							editWeapon(_em, w);
						}
						else{
							addItem(_em);
						}
					}
				});
				GridBagConstraints gbc_btnOk = new GridBagConstraints();
				gbc_btnOk.anchor = GridBagConstraints.NORTHEAST;
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
						WeaponEditor.this.dispose();
					}
				});
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Shop backToShop=new Shop(u,_em);
						backToShop.setVisible(true);
						WeaponEditor.this.dispose();
					}
				});
				GridBagConstraints gbc_btnCancel = new GridBagConstraints();
				gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnCancel.anchor = GridBagConstraints.NORTH;
				gbc_btnCancel.gridx = 5;
				gbc_btnCancel.gridy = 8;
				getContentPane().add(btnCancel, gbc_btnCancel);


	}
	protected void addItem(EntityManager thatEm) {
		String category=txtCategory.getText();
		String brand=txtBrand.getText();
		String model=txtModel.getText();
		int quantity=0;
		try {
			quantity=Integer.parseInt(txtCuantity.getText());
		} catch (Exception e) {
			lblCom.setText("Invalid cuantity.");
		}
		boolean isActive=chActive.isSelected();
		if(category==null || brand==null || model==null){
			lblCom.setText("*You forget something");
		}
		else{
			Weapon w=new Weapon();
			w.setCategory(category);
			w.setBrand(brand);
			w.setModel(model);
			w.setPrice(checkPrices(txtPrice.getText(), txtPriceDecimals.getText()));
			w.setActive(isActive);
			w.setQuantity(quantity);
			System.out.println(thatEm.toString());
			EntityTransaction tx = thatEm.getTransaction();
			tx.begin();
			System.err.println("New weapon:" +w);
			manager.insertWeapon(w);
			tx.commit();
			WeaponEditor.this.dispose();
			Shop backToShop=new Shop(u, em);
			backToShop.setVisible(true);
			WeaponEditor.this.dispose();
		}
	}
	public void editWeapon(EntityManager thatEm,Weapon w){
		String category=txtCategory.getText();
		String brand=txtBrand.getText();
		String model=txtModel.getText();
		boolean isActive=chActive.isSelected();
		int quantity=0;
		try {
			quantity=Integer.parseInt(txtCuantity.getText());
		} catch (Exception e) {
			lblCom.setText("Invalid cuantity.");
		}
		if(category==null || brand==null || model==null){
			lblCom.setText("*You forget something");
		}
		if(category==null || brand==null || model==null){
			lblCom.setText("*You forget something");
		}
		w.setCategory(category);
		w.setBrand(brand);
		w.setModel(model);
		w.setQuantity(quantity);
		double price=checkPrices(txtPrice.getText(),txtPriceDecimals.getText());
		w.setPrice(price);
		w.setActive(isActive);
		EntityTransaction tx = thatEm.getTransaction();
		tx.begin();
		manager.updateWeapon(w);
		tx.commit();
		WeaponEditor.this.dispose();
		Shop backToShop=new Shop(u, em);
		backToShop.setVisible(true);
		WeaponEditor.this.dispose();
	}
	private double checkPrices(String full,String decimals) {
		try {
			String price=full+"."+decimals;
			return Double.parseDouble(price);
		} catch (Exception e) {
			return 0.0;
		}
	}
}

