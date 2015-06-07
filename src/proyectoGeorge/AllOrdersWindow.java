package proyectoGeorge;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;

import java.awt.Color;
import java.util.List;

import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.ListSelectionModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AllOrdersWindow extends JFrame {

	private ImagePanel contentPane;
	private static EntityManager em;
	private JList JlistOrders;
	private OrderManagerHibernate manageOrder;
	private static User activeUser;
	private WeaponManagerHibernate manageWeapon;
	private JLabel lblRequests;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AllOrdersWindow frame = new AllOrdersWindow(em,activeUser);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public ImagePanel setBackground(){
		URL bgImage=this.getClass().getResource("/source/bgShop.png");
		BufferedImage bg=null;
		try {
			bg = ImageIO.read(bgImage);
		} catch (IOException e) {
			System.out.println("Background image does not exist!");
		}
		return new ImagePanel(bg);
	}

	/**
	 * Create the frame.
	 */
	public AllOrdersWindow(final EntityManager _em,final User u) {
		this.em=_em;
		this.activeUser=u;
		this.manageWeapon=new WeaponManagerHibernate(_em);
		manageOrder = new OrderManagerHibernate(em);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 710, 375);
		Dimension minSize= new Dimension(710, 375);
		setMinimumSize(minSize);
		contentPane = setBackground();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{376, 135, 158, 0};
		gbl_contentPane.rowHeights = new int[] {15, 285, 24, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblRequests = new JLabel("Requests:");
		lblRequests.setToolTipText("Model of the wapon.");
		lblRequests.setHorizontalAlignment(SwingConstants.CENTER);
		lblRequests.setForeground(new Color(102, 153, 51));
		lblRequests.setFont(new Font("Aharoni", Font.PLAIN, 14));
		GridBagConstraints gbc_lblRequests = new GridBagConstraints();
		gbc_lblRequests.anchor = GridBagConstraints.NORTH;
		gbc_lblRequests.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblRequests.insets = new Insets(0, 0, 5, 0);
		gbc_lblRequests.gridwidth = 3;
		gbc_lblRequests.gridx = 0;
		gbc_lblRequests.gridy = 0;
		contentPane.add(lblRequests, gbc_lblRequests);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		JlistOrders = new JList();
		JlistOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(JlistOrders);
		JlistOrders.setOpaque(false);
		JlistOrders.setCellRenderer(new TransparentListCellRenderer());
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);
		
		JButton btnCancelReq = new JButton("Cancel request");
		btnCancelReq.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
					cancelOrder();
					fillOrdersList();
				}
			}
		});
		btnCancelReq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelOrder();
				fillOrdersList();
			}
		});
		
		JButton btnExec = new JButton("Executed");
		btnExec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeOrder();
				fillOrdersList();
			}
		});
		btnExec.setFont(new Font("Gungsuh", Font.BOLD, 12));
		GridBagConstraints gbc_btnExec = new GridBagConstraints();
		gbc_btnExec.fill = GridBagConstraints.VERTICAL;
		gbc_btnExec.anchor = GridBagConstraints.EAST;
		gbc_btnExec.insets = new Insets(0, 0, 0, 5);
		gbc_btnExec.gridx = 0;
		gbc_btnExec.gridy = 2;
		contentPane.add(btnExec, gbc_btnExec);
		btnCancelReq.setFont(new Font("Gungsuh", Font.BOLD, 12));
		GridBagConstraints gbc_btnCancelReq = new GridBagConstraints();
		gbc_btnCancelReq.fill = GridBagConstraints.BOTH;
		gbc_btnCancelReq.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelReq.gridx = 1;
		gbc_btnCancelReq.gridy = 2;
		contentPane.add(btnCancelReq, gbc_btnCancelReq);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Shop back=new Shop(u,_em);
				back.setVisible(true);
				AllOrdersWindow.this.dispose();
			}
		});
		btnClose.setFont(new Font("Gungsuh", Font.BOLD, 12));
		GridBagConstraints gbc_btnClose = new GridBagConstraints();
		gbc_btnClose.fill = GridBagConstraints.BOTH;
		gbc_btnClose.gridx = 2;
		gbc_btnClose.gridy = 2;
		contentPane.add(btnClose, gbc_btnClose);
		fillOrdersList();
	}
	protected void cancelOrder() {
		if(JlistOrders==null){
			lblRequests.setText("Please select an order to remove!");
			return;
		}
		else{
			lblRequests.setText("Requests:");
		}
		Order o=(Order) JlistOrders.getSelectedValue();
		int orderQuantity=o.getOrderSize();
		Weapon w=o.getWeapon();
		int previusQ=w.getQuantity();
		w.setQuantity(previusQ+orderQuantity);
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		manageWeapon.updateWeapon(w);
		manageOrder.deleteOrder(o);
		tx.commit();
	}
	protected void fillOrdersList() {
		List<Order> listOrders=manageOrder.findAllOrders();
		DefaultListModel<Order> model=new DefaultListModel<Order>();
		for(Order o: listOrders){
					model.addElement(o);
		}
		JlistOrders.setModel(model);
	}
	protected void executeOrder() {
		if(JlistOrders==null){
			lblRequests.setText("Please select an order to remove!");
			return;
		}
		else{
			lblRequests.setText("Requests:");
		}
		Order o=(Order) JlistOrders.getSelectedValue();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		manageOrder.deleteOrder(o);
		tx.commit();
	}
}
