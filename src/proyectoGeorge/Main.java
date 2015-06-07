package proyectoGeorge;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Window;

import javax.swing.JTextArea;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.transaction.RollbackException;

import org.hibernate.exception.ConstraintViolationException;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLIntegrityConstraintViolationException;

public class Main extends JFrame{

	private JPasswordField txtPassword;
	private JTextField txtUser;
	JLabel lblLogin;
	private static EntityManager em;
	protected boolean isAdmin=false;
	public URL buttonImage=this.getClass().getResource("/source/woodButton.png");
	public ImageIcon buttonImg=new StretchIcon(buttonImage);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("project-oracle");
			em = emf.createEntityManager();
		} catch (Exception e) {
			System.out.println("Cannot connect to ORACLE .... Using derby!");
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("project-derby");
			em = emf.createEntityManager();
		}
		System.out.println(em.toString());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setFrameIcon() {
		URL iconImageURL=this.getClass().getResource("/source/frameIcon.png");
		Image icon=null;
		try {
			icon = ImageIO.read(iconImageURL);
		} catch (IOException e2) {
			System.out.println("IOException!Icon image does not exist!");
		}
		setIconImage(icon);
	}

	public void setBackground(){
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
	 * Create the application.
	 */
	public Main() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(em!=null){
					em.close();
					System.out.println("Entity manager closed");
					System.out.println("Closing program");
				}
			}
		});
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setBackground(Color.GRAY);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		final Main frame=this;
		setTitle("Ak collectable firearms shop");
		//		frame.addComponentListener(new ComponentAdapter() {
		////			@Override
		////			public void componentResized(ComponentEvent e) {
		////				if( frame.getSize().getHeight() > 250 ){
		////					frame.setSize( frame.getWidth(), 250 );
		////				}
		////			}
		////		});
		setFrameIcon();
		setBackground();
		createAdministratorRoot();
		frame.setBounds(100, 100, 350, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		frame.setResizable(false);
		Dimension maximumSize=new Dimension(350,270);
		frame.setMaximumSize(maximumSize);
		Dimension minimumSize=new Dimension(350, 270);
		frame.setMinimumSize(minimumSize);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {30, 30, 30};
		gridBagLayout.rowHeights = new int[] {25, 30, 10, 25, 10, 25, 25, 25, 25};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);

		lblLogin = new JLabel("Please login to continue");
		lblLogin.setFont(new Font("Gungsuh", Font.BOLD, 12));
		lblLogin.setForeground(new Color(102, 153, 51));
		GridBagConstraints gbc_lblLogin = new GridBagConstraints();
		gbc_lblLogin.insets = new Insets(0, 0, 5, 5);
		gbc_lblLogin.gridx = 1;
		gbc_lblLogin.gridy = 0;
		frame.getContentPane().add(lblLogin, gbc_lblLogin);

		txtPassword = new JPasswordField();
		txtPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					checkLogInForm();
				}
			}
		});
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtPassword.setToolTipText("The password for your username.");
		txtPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtPassword.getText().equals("Password")){
					txtPassword.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtPassword.getText().equals("")||txtPassword.getText().equals("Password")){
					//				lblLogin.setText("*Incorrect password field");
					//				lblLogin.setForeground(Color.red);
					txtPassword.setText("Password");
				}
			}
		});

		txtUser = new JTextField();
		txtUser.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtUser.setToolTipText("Your username.");
		txtUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(txtUser.getText().equals("Username")){
					txtUser.setText("");
				}
			}
		});
		txtUser.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(txtUser.getText().equals("Username")||txtUser.getText().equals("")){
					//					lblLogin.setForeground(Color.RED);
					//					lblLogin.setText("*Empty/incorrect usernames field");
					txtUser.setText("Username");
				}
			}
		});
		txtUser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(txtUser.getText().equals("Username")){
					txtUser.setText("");
				}
			}
		});
		txtUser.setText("Username");
		GridBagConstraints gbc_txtUser = new GridBagConstraints();
		gbc_txtUser.insets = new Insets(0, 0, 5, 5);
		gbc_txtUser.gridx = 1;
		gbc_txtUser.gridy = 2;
		getContentPane().add(txtUser, gbc_txtUser);
		txtUser.setColumns(13);
		txtPassword.setColumns(13);
		txtPassword.setText("Password");
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtPassword.gridx = 1;
		gbc_txtPassword.gridy = 3;
		frame.getContentPane().add(txtPassword, gbc_txtPassword);

		JButton btnLogIn = new JButton("Log in");
		btnLogIn.setFont(new Font("Gungsuh", Font.BOLD, 12));
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkLogInForm();
			}
		});
		btnLogIn.setToolTipText("Login to your user.");
		btnLogIn.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					checkLogInForm();
				}
			}
		});
		GridBagConstraints gbc_btnLogIn = new GridBagConstraints();
		gbc_btnLogIn.anchor = GridBagConstraints.NORTH;
		// insets 0,0,5,5
		gbc_btnLogIn.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogIn.gridx = 1;
		gbc_btnLogIn.gridy = 5;
		frame.getContentPane().add(btnLogIn, gbc_btnLogIn);


		JButton btnRegister = new JButton("Register");
		btnRegister.setFont(new Font("Gungsuh", Font.BOLD, 12));
		btnRegister.setToolTipText("Sign up for uor shop.");
		btnRegister.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					RegisterWindow register=new RegisterWindow();
					register.setVisible(true);
					register.setEntityManager(em);
					Main.this.dispose();
				}
			}
		});

		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				RegisterWindow register=new RegisterWindow();
				register.setVisible(true);
				register.setEntityManager(em);
				Main.this.dispose();
			}
		});
		btnRegister.setHorizontalAlignment(SwingConstants.LEADING);
		GridBagConstraints gbc_btnRegister = new GridBagConstraints();
		gbc_btnRegister.anchor = GridBagConstraints.NORTH;
		gbc_btnRegister.insets = new Insets(0, 0, 5, 5);
		gbc_btnRegister.gridx = 1;
		gbc_btnRegister.gridy = 6;
		frame.getContentPane().add(btnRegister, gbc_btnRegister);
		JButton btnCancel = new JButton("Exit");
		btnCancel.setFont(new Font("Gungsuh", Font.BOLD, 12));
		btnCancel.setToolTipText("Exit....");
		btnCancel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				em.close();
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					System.exit(0);
				}
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.anchor = GridBagConstraints.NORTH;
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 7;
		frame.getContentPane().add(btnCancel, gbc_btnCancel);
		
	}



	private void createAdministratorRoot() {
		EntityManagerFactory emf2 =null;
		EntityManager em3=null;
		try {
			emf2 = Persistence.createEntityManagerFactory("project-oracle");
			em3= emf2.createEntityManager();	
		} catch (Exception e) {
			emf2 = Persistence.createEntityManagerFactory("project-derby");
			em3= emf2.createEntityManager();	
		}
		User admin=new User();
		admin.setAdmin(true);
		admin.setName("root");
		admin.setPassword("rootroot");
		EntityTransaction tx=em3.getTransaction();
		try{
			tx.begin();
			new UserManagerHibernate(em3).insertUser(admin);
			tx.commit();
		}
		catch(Exception e){
			em3.close();
			System.out.println("Administrator user already exists!");
		}
	}

	protected void checkLogInForm() {
		UserManagerHibernate manager=new UserManagerHibernate(em);
		User u=new User();
		u.setName(txtUser.getText());
		String nameWriten=txtUser.getText();
		String passWriten=RegisterWindow.getPasswordString(txtPassword.getPassword());
		String passObtained=null;
		String nameObtained=null;
		User userObtained=null;
		boolean isItAdmin=false;
		try {
			userObtained=manager.findByUserName(u);
			passObtained=userObtained.getPasswordString();
			nameObtained=userObtained.getName();
			isItAdmin=userObtained.isAdmin();
		} catch (NullPointerException e) {
			System.out.println("Invalid entry!");
			lblLogin.setText("Username or password incorrect!");
			lblLogin.setForeground(Color.RED);
		}
		if (nameObtained.equals(nameWriten) && passWriten.equals(passObtained)) {
			System.out.println("Opening session for :"+nameObtained+" Admin?:"+isItAdmin);
			Shop shop= new Shop(userObtained,em);
			shop.setVisible(true);
			Main.this.dispose();
		}
		else{
			lblLogin.setText("Username or password incorrect!");
			lblLogin.setForeground(Color.RED);
		}
	}

	//------------------->		//To make the buttons border transparent use the first three rows
	public void paintImageToButton(JButton btn){
		btn.setBorderPainted(false);
		btn.setOpaque(false);
		btn.setContentAreaFilled(false);
		btn.setIcon(buttonImg);
	}
	//-------------> This method is replaced by the StretchImage class so it can work with the GridBahLayout... Stretch image not used for the moment bad way
//		public void resizedAndPlaceBtnImage(JButton btn){
//			URL buttonImage=this.getClass().getResource("/source/woodButton.png");
//			BufferedImage bg=null;
//			Image imageBuff=null;
//			try {
//				bg = ImageIO.read(buttonImage);
//					 imageBuff=bg.getScaledInstance((int)btn.getSize().getWidth(),(int) btn.getSize().getHeight(),
//				   	Image.SCALE_SMOOTH);
//			} catch (IOException e) {
//				System.out.println("Background image does not exist!");
//				return;
//			}
//			catch(IllegalArgumentException e){
//				System.out.println("Format of the image is not correct!");
//				e.printStackTrace();
//				return;
//			}
//			btn.setIcon(new ImageIcon(bg));
//			btn.setHorizontalTextPosition(JButton.CENTER);
//			btn.setVerticalTextPosition(JButton.CENTER);
//		}
}
