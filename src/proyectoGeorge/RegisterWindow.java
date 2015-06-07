package proyectoGeorge;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Window;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.transaction.RollbackException;

import org.hibernate.exception.ConstraintViolationException;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.awt.Font;

public class RegisterWindow extends JFrame {
	private EntityManager _em;

	private JTextField userField;
	private JPasswordField passwordField;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JPasswordField passwordField_1;
	private JLabel lblRepeatPassword;
	private JLabel lblCheckU;
	private JLabel lblCheckP;
	private JLabel lblCheckP2;
	private JCheckBox chConfirm;
	private JTextArea txtrToAccessOur;
	private JLabel lblWarning;
	private JTextArea txtComm;
	// usar URL para pegar la imagen
	URL lampOffURL = this.getClass().getResource("/source/lampoff.png");
	final ImageIcon lampOff = new ImageIcon(lampOffURL);

	URL lampOnURL = this.getClass().getResource("/source/lampon.png");
	final ImageIcon lampOn = new ImageIcon(lampOnURL);

	URL infoImURL = this.getClass().getResource("/source/icon.png");
	ImageIcon infoImagen = new ImageIcon(infoImURL);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterWindow window = new RegisterWindow();
					window.setVisible(true);
					window.userField.requestFocus();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void setImageIcon() {
		URL iconImageURL = this.getClass().getResource("/source/frameIcon.png");
		Image icon = null;
		try {
			icon = ImageIO.read(iconImageURL);
		} catch (IOException e2) {
			System.out.println("IOException!Icon image does not exist!");
		}
		setIconImage(icon);
	}

	/**
	 * Create the application.
	 */
	public RegisterWindow() {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setBackground(Color.GRAY);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		final RegisterWindow frame = this;
		setImageIcon();
		setTitle("New registration window");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 30, 0, 30, 30, 30 };
		gridBagLayout.rowHeights = new int[] { 30, 30, 0, 0, 30 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 1.0, 1.0, 1.0, 1.0,
				0.0, 1.0 };
		frame.getContentPane().setLayout(gridBagLayout);

		frame.setMinimumSize(new Dimension(450, 300));

		txtComm = new JTextArea();
		txtComm.setEditable(false);
		txtComm.setWrapStyleWord(true);
		txtComm.setLineWrap(true);
		txtComm.setFont(new Font("Abyssinica SIL", Font.BOLD, 12));
		txtComm.setBackground(Color.LIGHT_GRAY);
		txtComm.setText("Sign up for AK shop");
		GridBagConstraints gbc_txtComm = new GridBagConstraints();
		gbc_txtComm.insets = new Insets(0, 0, 5, 5);
		gbc_txtComm.fill = GridBagConstraints.BOTH;
		gbc_txtComm.gridx = 1;
		gbc_txtComm.gridy = 0;
		getContentPane().add(txtComm, gbc_txtComm);
		lblUsername = new JLabel("Username");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 2;
		frame.getContentPane().add(lblUsername, gbc_lblUsername);

		userField = new JTextField();
		userField
		.setToolTipText("White spaces and uppercase will be eliminated.Please choose an adequate username.If your username contains inappropriate or curse words it will be eliminated by our administrators.");
		userField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				checkUsersField();
				// checkUsersDB();
			}
		});
		userField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					passwordField.requestFocus();
				}
				if (checkUsersField()) {
					lblCheckU.setIcon(lampOn);
				} else {
					lblCheckU.setIcon(lampOff);
				}
			}
		});

		GridBagConstraints gbc_userField = new GridBagConstraints();
		gbc_userField.fill = GridBagConstraints.HORIZONTAL;
		gbc_userField.insets = new Insets(0, 0, 5, 5);
		gbc_userField.gridx = 2;
		gbc_userField.gridy = 2;
		frame.getContentPane().add(userField, gbc_userField);

		lblCheckU = new JLabel("");
		lblCheckU.setToolTipText("At least 6 chars.");
		lblCheckU.setIcon(lampOff);
		GridBagConstraints gbc_lblCheckU = new GridBagConstraints();
		gbc_lblCheckU.insets = new Insets(0, 0, 5, 5);
		gbc_lblCheckU.gridx = 3;
		gbc_lblCheckU.gridy = 2;
		getContentPane().add(lblCheckU, gbc_lblCheckU);

		lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 3;
		frame.getContentPane().add(lblPassword, gbc_lblPassword);

		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					passwordField_1.requestFocus();
				}
			}
		});
		passwordField
		.setToolTipText("*At least 6 chars...All whitespace will be eliminated.");
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				int num = passwordField.getPassword().length;
				if (num < 6) {
					txtComm.setText("*Password too short!It must be at least 6 chars");
				}
			}
		});
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 3;
		frame.getContentPane().add(passwordField, gbc_passwordField);

		lblCheckP = new JLabel("");
		lblCheckP.setToolTipText("At least 6 chars.");
		lblCheckP.setIcon(lampOff);
		GridBagConstraints gbc_lblCheckP = new GridBagConstraints();
		gbc_lblCheckP.insets = new Insets(0, 0, 5, 5);
		gbc_lblCheckP.gridx = 3;
		gbc_lblCheckP.gridy = 3;
		getContentPane().add(lblCheckP, gbc_lblCheckP);

		lblRepeatPassword = new JLabel("Repeat password");
		GridBagConstraints gbc_lblRepeatPassword = new GridBagConstraints();
		gbc_lblRepeatPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepeatPassword.anchor = GridBagConstraints.EAST;
		gbc_lblRepeatPassword.gridx = 1;
		gbc_lblRepeatPassword.gridy = 4;
		getContentPane().add(lblRepeatPassword, gbc_lblRepeatPassword);

		passwordField_1 = new JPasswordField();
		passwordField_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				checkPasswords();
			}
		});
		passwordField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					chConfirm.requestFocus();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				checkPasswords();
			}
		});
		passwordField_1
		.setToolTipText("*At least 6 chars...All whitespace will be eliminated.");
		GridBagConstraints gbc_passwordField_1 = new GridBagConstraints();
		gbc_passwordField_1.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_1.gridx = 2;
		gbc_passwordField_1.gridy = 4;
		getContentPane().add(passwordField_1, gbc_passwordField_1);

		lblCheckP2 = new JLabel("");
		lblCheckP2.setToolTipText("At least 6 chars.");
		lblCheckP2.setIcon(lampOff);
		GridBagConstraints gbc_lblCheckP2 = new GridBagConstraints();
		gbc_lblCheckP2.insets = new Insets(0, 0, 5, 5);
		gbc_lblCheckP2.gridx = 3;
		gbc_lblCheckP2.gridy = 4;
		getContentPane().add(lblCheckP2, gbc_lblCheckP2);

		lblWarning = new JLabel("");
		lblWarning.setToolTipText("18 for Europe/21 for US");

		lblWarning.setIcon(infoImagen);
		GridBagConstraints gbc_lblWarning = new GridBagConstraints();
		gbc_lblWarning.anchor = GridBagConstraints.NORTH;
		gbc_lblWarning.insets = new Insets(0, 0, 5, 5);
		gbc_lblWarning.gridx = 0;
		gbc_lblWarning.gridy = 5;
		getContentPane().add(lblWarning, gbc_lblWarning);

		txtrToAccessOur = new JTextArea();
		txtrToAccessOur.setToolTipText("18 for Europe/21 for US");
		txtrToAccessOur
		.setText("In order to access our services you need to be of legal age.");
		txtrToAccessOur.setWrapStyleWord(true);
		txtrToAccessOur.setLineWrap(true);
		txtrToAccessOur.setBackground(Color.LIGHT_GRAY);
		txtrToAccessOur.setEditable(false);
		GridBagConstraints gbc_txtrToAccessOur = new GridBagConstraints();
		gbc_txtrToAccessOur.insets = new Insets(0, 0, 5, 5);
		gbc_txtrToAccessOur.fill = GridBagConstraints.BOTH;
		gbc_txtrToAccessOur.gridx = 1;
		gbc_txtrToAccessOur.gridy = 5;
		getContentPane().add(txtrToAccessOur, gbc_txtrToAccessOur);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				frame.dispose();
				Main main = new Main();
				main.setVisible(true);
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Main main = new Main();
				main.setVisible(true);
			}
		});

		final JButton btnRegister = new JButton("Register");
		btnRegister.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (correctForm()) {
						// meter en base de datos
						createUser();
					}
				}
			}
		});
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (correctForm()) {
					// meter en base de datos
					createUser();
				}
			}
		});
		GridBagConstraints gbc_btnRegister = new GridBagConstraints();
		gbc_btnRegister.anchor = GridBagConstraints.EAST;
		gbc_btnRegister.insets = new Insets(0, 0, 0, 5);
		gbc_btnRegister.gridx = 2;
		gbc_btnRegister.gridy = 7;
		frame.getContentPane().add(btnRegister, gbc_btnRegister);
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 3;
		gbc_btnCancel.gridy = 7;
		frame.getContentPane().add(btnCancel, gbc_btnCancel);

		chConfirm = new JCheckBox("Confirm");
		chConfirm.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnRegister.requestFocus();
					chConfirm.setSelected(true);
				}
			}
		});
		chConfirm.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_chConfirm = new GridBagConstraints();
		gbc_chConfirm.insets = new Insets(0, 0, 5, 5);
		gbc_chConfirm.gridx = 2;
		gbc_chConfirm.gridy = 5;
		getContentPane().add(chConfirm, gbc_chConfirm);
	}

	protected boolean checkUsersField() {
		String user = userField.getText();
		// comprueba si el user existe en la BD y si existe retorna false
		// inviert el valor retornado por defecto a false
		if (userField.getText().equals("username")
				|| userField.getText().equals("Username")) {
			txtComm.setText("*Username can not be: Username");
			lblCheckU.setIcon(lampOff);
			return false;
		}
		if (userField.getText().equals("")) {
			txtComm.setText("*Username left empty");
			lblCheckU.setIcon(lampOff);
			return false;
		}

		return true;
	}

	protected boolean checkPasswords() {
		char[] passOneChars = passwordField.getPassword();
		int passOne = passOneChars.length;
		char[] passTwoChars = passwordField_1.getPassword();
		int passTwo = passTwoChars.length;
		if (passwordField.getText().equals("Password")
				|| passwordField.getText().equals("password")) {
			txtComm.setText("*Password can not be 'Password'!");
			lblCheckU.setIcon(lampOff);
			return false;
		}
		if (passOne == passTwo && passOne >= 6 && passTwo >= 6) {
			int count = 0;
			for (int i = 0; i < passOne; i++) {
				if (passOneChars[i] == passTwoChars[i]) {
					count++;
				}
			}
			if (count == passOne) {
				lblCheckP.setIcon(lampOn);
				lblCheckP2.setIcon(lampOn);
				lblCheckP.setToolTipText("Password correct");
				lblCheckP2.setToolTipText("Password correct");
				return true;
			} else {
				txtComm.setText("*Passwords doesn't match");
			}
		}
		return false;
	}

	protected boolean correctForm() {
		if (!chConfirm.isSelected()) {
			txtComm.setText("It is a MUST to be of legal age in order to enter the shop!");
			return false;
		}
		if (checkPasswords() && checkUsersField() && chConfirm.isSelected()) {
			return true;
		}
		return false;
	}

	public void setEntityManager(EntityManager em) {
		this._em = em;
	}

	protected static String getPasswordString(char[] passwordChar) {
		String passwordString = "";
		int length = passwordChar.length;
		if (length > 5) {
			for (int i = 0; i < length; i++) {
				passwordString = passwordString + passwordChar[i];
			}
			return passwordString.trim();
		} else {
			System.out
			.println("Something went wrong! >>>Password too short<<<");
			return "";
		}
	}

	protected void createUser() {
		UserManagerHibernate manager = new UserManagerHibernate(_em);
		User user = new User();
		user.setAdmin(false);
		user.setName(userField.getText().toLowerCase().replaceAll("\\s+", ""));
		System.out.println(userField.getText().toLowerCase()
				.replaceAll("\\s+", ""));
		EntityTransaction tx = null;
		try {
			user.setPassword(getPasswordString(passwordField_1.getPassword())
					.replaceAll("\\s+", ""));
			System.out.println(_em.toString());
			tx = _em.getTransaction();
			if (!tx.isActive()) {
				tx.begin();
			}
			manager.insertUser(user);
			tx.commit();
			RegisterWindow.this.dispose();
			Main main = new Main();
			main.setVisible(true);
		} catch (Exception e) {
			txtComm.setText("*Username already taken!");
			lblCheckU.setIcon(lampOff);
			// tx=null;/*-----> al cascar pq ya hay un usuario con ese nombre
			// luego al vovler a meter los datos correctos casca otra vez...dice
			// que todavia
			// tx contiene jdbc elements!.. intente a nularlo pero no tampoco
			// va*/
			e.printStackTrace();
		}
	}
}
