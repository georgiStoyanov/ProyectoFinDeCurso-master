package proyectoGeorge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;

import javax.swing.JList;

import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.JScrollPane;

import org.apache.derby.impl.services.locks.Timeout;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;

import java.awt.Toolkit;

import javax.swing.ListSelectionModel;
import javax.transaction.Transactional.TxType;

public class Shop extends JFrame {
	private static Shop frame;
	private static EntityManager em;
	// creando JPanel que me va a servir de bg image
	private JPanel contentPane;
	private JLabel lbluserPicture;
	private static User activeUserShop;
	private JLabel lblUsername1;
	private UserManagerHibernate manageUser;
	private WeaponManagerHibernate manageWeapon;
	private JLabel lblCom;
	private JList jList;
	private JComboBox boxManufacturer;
	private JComboBox boxModel;
	private JComboBox boxCategory;
	private JLabel lblOrders;
	private JScrollPane scrollPane_1;
	private JList JListOrders;
	private OrderManagerHibernate manageOrder;
	private JButton btnRemoveOrder;
	/**----------------------------------------------------------------------------------
	 * Valores con que se van a rellenar el combobox
	 * ----------------------------------------------------------------------------------
	 */
	//	private void comboBoxValues() {
	//		comboBoxValues[0]="All";
	//		comboBoxValues[1]="Handguns";
	//		comboBoxValues[2]="Rifles";
	//		comboBoxValues[3]="Shotguns";
	//
	//	}


	//----------------------------------------------------------------------------------
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame= new Shop(activeUserShop,em);
					frame.setVisible(true);
					Dimension minimumSize=new Dimension(833, 423);
					frame.setMinimumSize(minimumSize);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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

	private void setBackgroundImage() {
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
	 * Create the frame.
	 */
	public Shop(final User u,final EntityManager _em) {
		setImageIcon();
		this.activeUserShop=u;
		this.em=_em;
		setTitle("AK colletable firearms shop");
		manageUser=new UserManagerHibernate(_em);
		manageWeapon=new WeaponManagerHibernate(_em);
		manageOrder=new OrderManagerHibernate(_em);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 875, 483);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setBackgroundImage();
		setResizable(false);
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.setFont(new Font("Gungsuh", Font.BOLD, 12));
		//		btnLogOut.setBackground(new Color(0, 128, 0));
		btnLogOut.setBounds(728, 419, 131, 25);
		btnLogOut.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				logOut();
			}
		});
		btnLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logOut();
			}
		});
		getContentPane().setLayout(null);

		JLabel lblCategory = new JLabel("Category :");
		lblCategory.setBounds(185, 34, 92, 15);
		lblCategory.setForeground(new Color(102, 153, 51));
		lblCategory.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCategory.setToolTipText("Weapon category");
		getContentPane().add(lblCategory);

		boxCategory = new JComboBox(new UniqueComboBoxModel());
		boxCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filterBoxes();
				addItemsToListFilter();
			}
		});
		boxCategory.setFont(new Font("Gungsuh", Font.BOLD, 11));
		boxCategory.setBackground(new Color(0, 128, 0));
		boxCategory.setBounds(277, 32, 126, 20);
		boxCategory.setToolTipText("Category");
		getContentPane().add(boxCategory);
		//rellenando el comboBox de categorias

		JLabel lblModel = new JLabel("Model :");
		lblModel.setBounds(654, 31, 71, 20);
		lblModel.setForeground(new Color(102, 153, 51));
		lblModel.setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().add(lblModel);


		boxModel = new JComboBox(new UniqueComboBoxModel());
		boxModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boxCategory.addItem("All");
				boxManufacturer.addItem("All");
				boxModel.addItem("All");
				addItemsToListFilter();
			}
		});
		boxModel.setFont(new Font("Gungsuh", Font.BOLD, 11));
		boxModel.setBackground(new Color(0, 128, 0));
		boxModel.setBounds(722, 32, 126, 20);
		boxModel.setToolTipText("Category");
		getContentPane().add(boxModel);

		JButton btnOrder = new JButton("Request");
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				orderWeapon(_em);
			}
		});
		btnOrder.setBounds(569, 420, 149, 25);
		
		
		/*--->*/if(!u.isAdmin()){
			getContentPane().add(btnOrder);
			getContentPane().add(btnOrder);
		}
		getContentPane().add(btnLogOut);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(185, 58, 674, 350);
		getContentPane().add(scrollPane);


		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0};
		gbl_contentPane.rowHeights = new int[]{0};
		gbl_contentPane.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		lbluserPicture = new JLabel("");
		lbluserPicture.setToolTipText("Please upload ONLY images in a correct image format such as \"png\",\"jpg\",\"gif\".\r\nAny inappropriate image puts your whole profile at risk.");
		lbluserPicture.setBounds(10, 11, 126, 134);
		getContentPane().add(lbluserPicture);


		JButton btnChangePhoto = new JButton("Change photo");
		btnChangePhoto.setFont(new Font("Gungsuh", Font.BOLD, 10));
		btnChangePhoto.setToolTipText("Please upload ONLY images in a correct image format such as \"png\",\"jpg\",\"gif\".\r\nAny inappropriate image puts your whole profile at risk.");
		//		btnChangePhoto.setBackground(new Color(0, 128, 0));
		btnChangePhoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				uploadPhoto(em);
			}
		});
		btnChangePhoto.setBounds(10, 156, 126, 23);
		getContentPane().add(btnChangePhoto);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 190, 82, 14);
		lblUsername.setForeground(new Color(102, 153, 51));
		getContentPane().add(lblUsername);

		lblUsername1 = new JLabel(u.getName());
		lblUsername1.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername1.setBounds(75, 190, 100, 14);
		lblUsername1.setForeground(new Color(102, 153, 51));
		getContentPane().add(lblUsername1);

		JLabel lblManufacturer = new JLabel("Manufacturer:");
		lblManufacturer.setToolTipText("Weapon category");
		lblManufacturer.setForeground(new Color(102, 153, 51));
		lblManufacturer.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblManufacturer.setBounds(413, 34, 105, 15);
		getContentPane().add(lblManufacturer);

		boxManufacturer = new JComboBox(new UniqueComboBoxModel());
		boxManufacturer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filterModel();
				addItemsToListFilter();
			}
		});
		boxManufacturer.setFont(new Font("Gungsuh", Font.BOLD, 11));
		boxManufacturer.setBackground(new Color(0, 128, 0));
		boxManufacturer.setToolTipText("Category");
		boxManufacturer.setBounds(518, 32, 126, 20);
		getContentPane().add(boxManufacturer);

		lblCom = new JLabel("");
		lblCom.setForeground(Color.RED);
		lblCom.setBounds(185, 9, 622, 14);
		getContentPane().add(lblCom);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setFont(new Font("Gungsuh", Font.BOLD, 12));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editWeapon();
			}
		});
		btnEdit.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				editWeapon();
			}
		});
		//		btnEdit.setBackground(new Color(0, 128, 0));
		btnEdit.setBounds(290, 420, 118, 23);


		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("GungsuhChe", Font.BOLD, 12));
		btnAdd.setForeground(new Color(0,128,0));
		btnAdd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					WeaponEditor editor=new WeaponEditor(u,em,false,null);
					editor.setVisible(true);
					Shop.this.dispose();
				}
			}
		});

		//		btnAdd.setBackground(Color.WHITE);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WeaponEditor editor=new WeaponEditor(u,em,false,null);
				editor.setVisible(true);
				Shop.this.dispose();
			}
		});
		btnAdd.setBounds(185, 420, 95, 23);


		JButton btnRemove = new JButton("Remove");
		btnRemove.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					removeItem();
				}
			}
		});
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeItem();
			}
		});
		btnRemove.setFont(new Font("Gungsuh", Font.BOLD, 12));
		//		btnRemove.setBackground(new Color(0, 128, 0));
		btnRemove.setBounds(413, 420, 118, 23);
		jList = new JList();
		jList.setFont(new Font("Gungsuh", Font.PLAIN, 15));
		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(jList);
		JButton btnUser = new JButton("User manager");
		btnUser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
					UsersWindow users=new UsersWindow(_em,activeUserShop);
					users.setVisible(true);
					Shop.this.dispose();
				}
			}
		});
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UsersWindow users=new UsersWindow(_em,activeUserShop);
				users.setVisible(true);
				Shop.this.dispose();
			}
		});
		btnUser.setFont(new Font("Gungsuh", Font.BOLD, 12));
		btnUser.setBounds(541, 420, 177, 23);

		//Trasnparent ScrollPane
		jList.setOpaque(false);
		jList.setCellRenderer(new TransparentListCellRenderer());
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);
		//		resizeAndPlaceButton(btnRemove, buttonImage);

		//Pintando los butones de usuarion normal
		resizedAndPlaceBtnImage(btnOrder);
		resizedAndPlaceBtnImage(btnLogOut);
		resizedAndPlaceBtnImage(btnChangePhoto);

		lblOrders = new JLabel("Requests:");
		lblOrders.setHorizontalAlignment(SwingConstants.CENTER);
		lblOrders.setForeground(new Color(102, 153, 51));
		lblOrders.setBounds(10, 215, 165, 14);
		getContentPane().add(lblOrders);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 227, 165, 181);
		getContentPane().add(scrollPane_1);

		JListOrders = new JList();
		JListOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(JListOrders);

		JListOrders.setOpaque(false);
		JListOrders.setCellRenderer(new TransparentListCellRenderer());
		scrollPane_1.getViewport().setOpaque(false);
		scrollPane_1.setOpaque(false);
		
		JButton btnOrderWindow = new JButton("Requests window");
		btnOrderWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AllOrdersWindow orders=new AllOrdersWindow(em,activeUserShop); 
				orders.setVisible(true);
				Shop.this.dispose();
			}
		});
		btnOrder.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
					AllOrdersWindow orders=new AllOrdersWindow(em,activeUserShop); 
					orders.setVisible(true);
					Shop.this.dispose();
				}
			}
		});
		btnOrderWindow.setFont(new Font("Gungsuh", Font.BOLD, 12));
		lblCategory.setForeground(new Color(102, 153, 51));
		btnOrderWindow.setBounds(10, 420, 165, 23);
		
		btnRemoveOrder = new JButton("Remove request");
		btnRemoveOrder.setFont(new Font("Gungsuh", Font.BOLD, 12));
		btnRemoveOrder.setBounds(10, 419, 165, 25);

		initializeBoxes();
		filterBoxes();
		filterModel();
		placeMyImage(u);
		addItemsToListFilter();
		fillOrdersList();
		addAdminOptions(u);
		//if user is admin add those buttons
		/*--->*/if(u.isAdmin()){
			getContentPane().add(btnUser);
			getContentPane().add(btnRemove);
			getContentPane().add(btnAdd);
			getContentPane().add(btnEdit);
			getContentPane().add(btnOrderWindow);

			//buttons with images

			resizedAndPlaceBtnImage(btnUser);
			resizedAndPlaceBtnImage(btnRemove);
			resizedAndPlaceBtnImage(btnAdd);
			resizedAndPlaceBtnImage(btnEdit);
			resizedAndPlaceBtnImage(btnOrderWindow);
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////
	protected void fillOrdersList() {
		List<Order> listOrders=manageOrder.findAllOrders();
		DefaultListModel<Order> model=new DefaultListModel<Order>();
		for(Order o: listOrders){
			String uName=o.getUser().getName();
			if(!activeUserShop.isAdmin() && activeUserShop.getName().equals(uName)){
					model.addElement(o);
			}
			if(activeUserShop.isAdmin()){
					model.addElement(o);
			}
		}
		JListOrders.setModel(model);
	}
	///////////////////////////////////////////////////////////////////////////////////////
	protected void orderWeapon(EntityManager _em) {
		Weapon w=(Weapon) jList.getSelectedValue();
		if(w!=null){
			OrderWindow ow=new OrderWindow(activeUserShop, _em, w);
			ow.txtCategory.setText(w.getCategory());
			ow.txtBrand.setText(w.getBrand());
			ow.txtModel.setText(w.getModel());
			ow.lblCom.setText("You are about to order : "+w.getBrand()+" "+w.getModel()+" on a price of "+w.getPrice());
			String priceString=Double.toString(w.getPrice());
			String[] parts=priceString.split("\\.");
			ow.txtPrice.setText(parts[0]);
			ow.txtPriceDecimals.setText((parts[1]));
			ow.setVisible(true);
			Shop.this.dispose();
		}
		else{
			lblCom.setText("Please select an item to request!");
		}
		addItemsToListFilter();
	}
	///////////////////////////////////////////////////////////////////////////////////////
	protected void filterModel() {
		boxManufacturer.addItem("All");
		boxModel.addItem("All");
		List<Weapon> listWeapons=manageWeapon.findAll();
		String selectedCat=boxCategory.getSelectedItem().toString();
		String selectedMan=boxManufacturer.getSelectedItem().toString();
		String selectedMod=boxModel.getSelectedItem().toString();
		boolean isItAdmin=activeUserShop.isAdmin();
		if(!selectedMan.equals("All")){
			boxModel.removeAllItems();
			boxModel.addItem("All");
			for(Weapon w:listWeapons){
				if(!isItAdmin && w.isActive() && w.getQuantity()>0){
					if(w.getBrand().equals(selectedMan)){
						boxModel.addItem(w.getModel());
					}
				}
				if(isItAdmin){
					if(w.getBrand().equals(selectedMan)){
						boxModel.addItem(w.getModel());
					}
				}
			}
		}
		else{
			boxModel.removeAllItems();
			boxModel.addItem("All");
		}

	}
	protected void removeItem() {
		Weapon w=(Weapon)jList.getSelectedValue();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		manageWeapon.deleteWeapon(w);
		tx.commit();
	}
	///////////////////////////////////////////////////////////////////////////////////////
	private void placeMyImage(User u) {
		try {
			byte[] imageObtained=manageUser.findByUserName(u).getImage();
			ByteArrayInputStream bais = new ByteArrayInputStream(imageObtained);
			BufferedImage img = ImageIO.read(bais);
			Image bufferedImage =img.getScaledInstance(lbluserPicture.getWidth(), lbluserPicture.getHeight(),
					Image.SCALE_SMOOTH);
			lbluserPicture.setIcon(new ImageIcon(bufferedImage));
		} catch (NullPointerException e) {
			System.out.println("This user doesn't have un image uploaded.Placing default image.");
			URL defaultUserImageURL = this.getClass().getResource("/source/defaultUserImage.png");
			lbluserPicture.setIcon(placeDefaultImage(defaultUserImageURL));
		}
		catch(IOException e){
			System.out.println("ByteArrayInputStream Shop.java IOException");
		}
	}


	protected void logOut() {
		Shop.this.dispose();
		Main login=new Main();
		login.setVisible(true);
		System.out.println("Closing session for user: "+activeUserShop.getName());
	}
	///////////////////////////////////////////////////////////////////////////////////////
	private void filterBoxes() {
		//		box.addItem("All");
		//		box.addItem("Knives");
		//		box.addItem("Pistols");
		//		box.addItem("Revolvers");
		//		box.addItem("Shotguns");
		//		box.addItem("Assault rifles");
		// 		comboBoxValues();
		List<Weapon> listWeapons=manageWeapon.findAll();
		boxModel.addItem("All");
		boxManufacturer.addItem("All");
		String selectedCat=boxCategory.getSelectedItem().toString();
		String selectedMan=boxManufacturer.getSelectedItem().toString();
		String selectedMod=boxModel.getSelectedItem().toString();
		boolean isItAdmin=activeUserShop.isAdmin();
		if(!selectedCat.equals("All")){
			boxManufacturer.removeAllItems();
			boxManufacturer.addItem("All");
			boxModel.removeAll();
			boxModel.addItem("All");
			for(Weapon w:listWeapons){
				if(!isItAdmin && w.isActive() && w.getQuantity()>0){
					if(w.getCategory().equals(selectedCat)){
						boxManufacturer.addItem(w.getBrand());
					}
				}
				if(isItAdmin){
					if(w.getCategory().equals(selectedCat)){
						boxManufacturer.addItem(w.getBrand());
					}
				}
			}
		}
		else{
			boxModel.removeAllItems();
			boxModel.addItem("All");
			boxManufacturer.removeAllItems();
			boxManufacturer.addItem("All");
		}
	}
	private void initializeBoxes(){
		boxCategory.addItem("All");
		boxManufacturer.addItem("All");
		boxModel.addItem("All");
		boolean isItAdmin=activeUserShop.isAdmin();
		List<Weapon> listWeapons=manageWeapon.findAll();
		for(Weapon w:listWeapons){
			if(!isItAdmin && w.isActive() && w.getQuantity()>0){
				boxCategory.addItem(w.getCategory());
			}
			if(isItAdmin){
				boxCategory.addItem(w.getCategory());
			}
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////
	private void addAdminOptions(User u){
		/*--->*/if(u.isAdmin()){
			System.out.println("Administrator: "+u.getName()+" has logged in.");

		}
	}
	///////////////////////////////////////////////////////////////////////////////////////
	private ImageIcon placeDefaultImage(URL location){
		BufferedImage img = null;
		try {
			img = ImageIO.read(location);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image bufferedImage =img.getScaledInstance(lbluserPicture.getWidth(), lbluserPicture.getHeight(),
				Image.SCALE_SMOOTH);
		return new ImageIcon(bufferedImage);
	}
	///////////////////////////////////////////////////////////////////////////////////////
	private void uploadPhoto(EntityManager em) {
		final JFileChooser fc = new JFileChooser();
		int returnVal=fc.showOpenDialog(Shop.this);
		File selected=fc.getSelectedFile();
		if(returnVal==JFileChooser.APPROVE_OPTION){
			BufferedImage img = null;
			try {
				System.out.println(selected);
				img = ImageIO.read(selected);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Image bufferedImage =img.getScaledInstance(lbluserPicture.getWidth(), lbluserPicture.getHeight(),
						Image.SCALE_SMOOTH);
				lbluserPicture.setIcon(new ImageIcon(bufferedImage));	
			} catch (Exception e) {
				lblCom.setText("*Please upload a valid image file!");
				//				try {
				//					TimeUnit.SECONDS.wait(0);
				//					lblCom.setText("");
				//				} catch (InterruptedException e1) {
				//					e.printStackTrace();
				//				}
			}
			/*
			 * ------------------Storing the image in the DB---------------------
			 */
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(img, "png", baos);
			} catch (IOException e) {
				System.out.println("Invalid image entry!");
			}

			byte[] bytes = baos.toByteArray();
			activeUserShop.setImage(bytes);
			System.out.println("Image uploaded! Lenght: "+bytes.toString()+"bytes");
			EntityTransaction tx = em.getTransaction();
			System.out.println(activeUserShop.toString());
			tx.begin();
			manageUser.updateUser(activeUserShop);
			tx.commit();
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////
	private void addItemsToListFilter(){
		String selectedCat=boxCategory.getSelectedItem().toString();
		String selectedMan=boxManufacturer.getSelectedItem().toString();
		String selectedMod=boxModel.getSelectedItem().toString();
		DefaultListModel<Weapon> model = new DefaultListModel<Weapon>();
		boolean isItAdmin=activeUserShop.isAdmin();
		List<Weapon> listWeapons=manageWeapon.findAll();
		jList.setModel(model);
		if(!selectedCat.equals("All") && !selectedMan.equals("All") && !selectedMod.equals("All")){
			for (Weapon w : listWeapons) {
				if(w.isActive() && !isItAdmin && w.getQuantity()>0){
					if(w.getCategory().equals(selectedCat) && w.getBrand().equals(selectedMan) && w.getModel().equals(selectedMod)){
						model.addElement(w);
					}
				}
				if(isItAdmin){
					if(w.getCategory().equals(selectedCat) && w.getBrand().equals(selectedMan) && w.getModel().equals(selectedMod)){
						model.addElement(w);
					}
				}
			}
			return;
		}
		if(!selectedCat.equals("All") && !selectedMan.equals("All")){
			for (Weapon w : listWeapons) {
				if(w.isActive() && !isItAdmin && w.getQuantity()>0){
					if(w.getCategory().equals(selectedCat) && w.getBrand().equals(selectedMan)){
						model.addElement(w);
					}
				}
				if(isItAdmin){
					if(w.getCategory().equals(selectedCat) && w.getBrand().equals(selectedMan)){
						model.addElement(w);
					}
				}
			}
			return;
		}
		if(!selectedCat.equals("All")){
			for (Weapon w : listWeapons){
				if(w.isActive() && !isItAdmin && w.getQuantity()>0){
					if(w.getCategory().equals(selectedCat)){
						model.addElement(w);
					}
				}
				if(isItAdmin){
					if(w.getCategory().equals(selectedCat)){
						model.addElement(w);
					}
				}
			}
			return;
		}
		if(selectedCat.equals("All")){
			for (Weapon w : listWeapons){
				if(w.isActive() && !isItAdmin && w.getQuantity()>0){
					System.out.println("Filling column "+w.toString());
					model.addElement(w);
				}
				if(isItAdmin){
					System.out.println("Filling column "+w.toString());
					model.addElement(w);
				}
			}
		}
		else{
			System.out.println("Something went wrong.Invalid selection on the button pannels!");
		}
		jList.setModel(model);
	}
	///////////////////////////////////////////////////////////////////////////////////////
	public void resizedAndPlaceBtnImage(JButton btn){
		URL buttonImage=this.getClass().getResource("/source/woodButton.png");
		BufferedImage bg=null;
		Image imageBuff=null;
		try {
			bg = ImageIO.read(buttonImage);
			imageBuff=bg.getScaledInstance((int)btn.getSize().getWidth(),(int) btn.getSize().getHeight(),
					Image.SCALE_SMOOTH);
		} catch (IOException e) {
			System.out.println("Background image does not exist!");
			return;
		}
		catch(IllegalArgumentException e){
			System.out.println("Format of the image is not correct!");
			return;
		}
		btn.setIcon(new ImageIcon(imageBuff));
		btn.setHorizontalTextPosition(JButton.CENTER);
		btn.setVerticalTextPosition(JButton.CENTER);
		btn.setForeground(new Color(0,128,0));
	}
	///////////////////////////////////////////////////////////////////////////////////////
	private void editWeapon(){
		Weapon w=(Weapon) jList.getSelectedValue();
		WeaponEditor editor=new WeaponEditor(activeUserShop, em,true,w);
		if(w!=null){
			editor.txtCategory.setText(w.getCategory());
			editor.txtBrand.setText(w.getBrand());
			editor.txtModel.setText(w.getModel());
			editor.lblCom.setText("Editing item with id:"+w.getIdWeapon());
			editor.chActive.setSelected(w.isActive());
			editor.txtCuantity.setText(String.valueOf(w.getQuantity()));
			String priceString=Double.toString(w.getPrice());
			System.out.println(priceString);
			String[] parts=priceString.split("\\.");
			editor.txtPrice.setText(parts[0]);
			editor.txtPriceDecimals.setText((parts[1]));
			editor.setVisible(true);
			Shop.this.dispose();
		}
		else{
			lblCom.setText("*You haven't selected an item to edit!");
		}
	}
}