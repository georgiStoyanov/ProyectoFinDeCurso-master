package proyectoGeorge;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;


public class UsersWindow extends JFrame {
	public static EntityManager em;
	private JPanel contentPane;
	private UserManagerHibernate manager;
	private static User activeUser;
	private JLabel lblCom;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsersWindow frame = new UsersWindow(em,activeUser);
					frame.setResizable(false);
					frame.setVisible(true);
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
	 * Create the frame.
	 */
	public UsersWindow(final EntityManager _em,final User u) {
		this.em=_em;
		activeUser=u;
		manager=new UserManagerHibernate(em);
		setFrameIcon();
		setBackground();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 579, 269);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 28, 412, 130);
		getContentPane().add(scrollPane);
		
		final JLabel lblPicture = new JLabel("");
		lblPicture.setForeground(new Color(102, 153, 51));
		lblPicture.setToolTipText("Please upload ONLY images in a correct image format such as \"png\",\"jpg\",\"gif\".\r\nAny inappropriate image puts your whole profile at risk.");
		lblPicture.setBounds(432, 28, 126, 134);
		getContentPane().add(lblPicture);
		
		final JList monitoredList = new JList();
		monitoredList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				try {
					byte[] imageObtained=((User) monitoredList.getSelectedValue()).getImage();
					ByteArrayInputStream bais = new ByteArrayInputStream(imageObtained);
					BufferedImage img = ImageIO.read(bais);
					Image bufferedImage =img.getScaledInstance(lblPicture.getWidth(), lblPicture.getHeight(),
							Image.SCALE_SMOOTH);
					lblPicture.setIcon(new ImageIcon(bufferedImage));
				} catch (NullPointerException | IOException e) {
					lblPicture.setIcon(null);
					lblPicture.setText("No image for this user");
				}
			}
		});
		monitoredList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(monitoredList);
		
		JButton btnRemoveAdmin = new JButton("Remove admin rigths");
		btnRemoveAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userAction(monitoredList, false, true, false);
			}
		});
		btnRemoveAdmin.setBounds(221, 169, 201, 23);
		getContentPane().add(btnRemoveAdmin);
		
		JButton btnMakeAdmin = new JButton("Make admin");
		btnMakeAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monitoredList.getSelectedValue();
				userAction(monitoredList,true,false,false);
			}
		});
		btnMakeAdmin.setBounds(10, 169, 201, 23);
		getContentPane().add(btnMakeAdmin);
		
		JButton btnRemoveUser = new JButton("Remove user");
		btnRemoveUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userAction(monitoredList, false, false, true);
			}
		});
		btnRemoveUser.setBounds(10, 203, 201, 23);
		getContentPane().add(btnRemoveUser);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Shop backToShop=new Shop(u, _em);
				backToShop.setVisible(true);
				UsersWindow.this.dispose();
			}
		});
		btnExit.setBounds(221, 203, 201, 23);
		getContentPane().add(btnExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
//		setContentPane(contentPane);
		resizedAndPlaceBtnImage(btnMakeAdmin);
		resizedAndPlaceBtnImage(btnRemoveAdmin);
		resizedAndPlaceBtnImage(btnRemoveUser);
		resizedAndPlaceBtnImage(btnExit);
		fillMonitoredList(monitoredList);
		
		lblCom = new JLabel("Users:");
		lblCom.setForeground(Color.RED);
		lblCom.setBounds(10, 3, 315, 14);
		getContentPane().add(lblCom);
		
		//Trasnparent ScrollPane
				monitoredList.setOpaque(false);
				monitoredList.setCellRenderer(new TransparentListCellRenderer());
				scrollPane.getViewport().setOpaque(false);
				scrollPane.setOpaque(false);
				
	}
	protected void userAction(JList list,boolean makeAdmin,boolean rAdminRights,boolean removeUser) {
		User selected=(User) list.getSelectedValue();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		if(makeAdmin){
			selected.setAdmin(true);
			manager.updateUser(selected);
			lblCom.setText("*User "+selected.getName()+" is now an administrator.");
		}
		if(rAdminRights){
			selected.setAdmin(false);
			manager.updateUser(selected);
			lblCom.setText("*User "+selected.getName()+" is no longer admin.");
		}
		if(removeUser){
			manager.deleteUser(selected);
			lblCom.setText("*User "+selected.getName()+" has been deleted.");
		}
		tx.commit();
		fillMonitoredList(list);
	}
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
	public void fillMonitoredList(JList list){
		List<User> allUsers=manager.findAllUsers();
		DefaultListModel<User> model = new DefaultListModel<User>();
		for (User u : allUsers){
			model.addElement(u);
		}
		list.setModel(model);
	}
}
