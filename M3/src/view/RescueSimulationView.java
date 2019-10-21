package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import simulation.Simulator;

@SuppressWarnings("serial")
public class RescueSimulationView extends JFrame {

	private JPanel rescuePanel;
	private JPanel infoPanel;
	private JPanel unitPanel;
	private JTextArea info;
	private JPanel controlPanel;
	private JPanel startContainer;
	private JLabel start;
	private JPanel unitPanelContainer;
	private JScrollPane scroll;
	private JLabel dead;
	private JLabel status;
	private JLabel background;

	public RescueSimulationView(Simulator s) throws FontFormatException, IOException {
		// JFrame control
		setTitle("Rescue Simulation");
		setBounds(0, 0, 1920, 1080);
		setLayout(new BorderLayout(20, 20));
		ImageIcon icon = new ImageIcon("art/backgrounds/gamebackground.jpg");
		Image image = icon.getImage();
		Image newimg = image.getScaledInstance(1920, 1080, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		background = new JLabel(icon);
		background.setPreferredSize(new Dimension(getWidth(), getHeight()));
		background.setLayout(new BorderLayout(20, 20));
		background.setBounds(0, 0, 1920, 1080);
		// ==========================================

		// currentCyclePanel at the top of the screen
		start = new JLabel("Click Start to begin the Game");
		start.setOpaque(false);
		InputStream resHeader = new BufferedInputStream(new FileInputStream("fonts/Oswald-Medium.ttf"));
		Font headerFont = Font.createFont(Font.TRUETYPE_FONT, resHeader);
		headerFont = headerFont.deriveFont(Font.PLAIN, 40);
		start.setForeground(new Color(7, 0, 156));
		start.setFont(headerFont);
		startContainer = new JPanel();
		startContainer.setOpaque(false);
		startContainer.setPreferredSize(new Dimension(getWidth(), (int) (getHeight() * 0.074)));
		startContainer.add(start, BorderLayout.CENTER);
		background.add(startContainer, BorderLayout.NORTH);
		// =============================================================================================

		// rescuePanel at the center of the screen
		rescuePanel = new JPanel();
		rescuePanel.setOpaque(false);
		rescuePanel.setLayout(new GridLayout(10, 10, 5, 5));
		rescuePanel.setPreferredSize(new Dimension(384, 1080));
		background.add(rescuePanel, BorderLayout.CENTER);
		// =============================================================================

		// unitPanel at the right of the screen
		unitPanel = new JPanel();
		unitPanel.setOpaque(false);
		unitPanel.setLayout(new GridLayout(8, 2, 5, 5));
		unitPanel.setPreferredSize(new Dimension(getWidth() / 5, getHeight()));
		background.add(unitPanel, BorderLayout.EAST);
		// ===========================================================================

		// infoPanel at the left of the screen
		infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(384, 1080));
		infoPanel.setOpaque(false);
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		dead = new JLabel("");
		String currentCycle = "";
		status = new JLabel(currentCycle);
		JPanel container = new JPanel();
		container.setPreferredSize(new Dimension(384, 140));
		container.setOpaque(false);
		container.setLayout(null);
		container.add(dead);
		container.add(status);
		dead.setLocation(40, 0);
		dead.setSize(384, 100);
		status.setSize(384, 100);
		status.setLocation(80, 56);
		InputStream is = new BufferedInputStream(new FileInputStream("fonts/Ubuntu-Regular.ttf"));
		Font font = Font.createFont(Font.TRUETYPE_FONT, is);
		font = font.deriveFont(Font.PLAIN, 27);
		dead.setFont(font);
		status.setFont(font);
		dead.setForeground(new Color(142, 0, 0));
		status.setForeground(new Color(142, 0, 0));
		infoPanel.add(container);
		info = new JTextArea(32, 35);
		is = new BufferedInputStream(new FileInputStream("fonts/Ubuntu-Regular.ttf"));
		font = Font.createFont(Font.TRUETYPE_FONT, is);
		font = font.deriveFont(Font.PLAIN, 13);
		info.setFont(font);
		info.setForeground(new Color(7, 0, 156));
		info.setLineWrap(true);
		info.setOpaque(false);
		info.setEditable(false);
		DefaultCaret caret = (DefaultCaret) info.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		scroll = new JScrollPane(info);
		scroll.getViewport().setOpaque(false);
		scroll.setOpaque(false);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		infoPanel.add(scroll);
		background.add(infoPanel, BorderLayout.WEST);
		// =============================================================================================

		// controlPanel at the bottom of the screen
		controlPanel = new JPanel(new FlowLayout());
		controlPanel.setOpaque(false);
		controlPanel.setPreferredSize(new Dimension(getWidth(), (int) (getHeight() * 0.074)));
		background.add(controlPanel, BorderLayout.SOUTH);
		add(background);
		// ==============================================================================================
	}

	public JPanel getControlPanel() {
		return controlPanel;
	}

	public JPanel getUnitPanel() {
		return unitPanel;
	}

	public JPanel getRescuePanel() {
		return rescuePanel;
	}

	public JLabel getStart() {
		return start;
	}

	public JPanel getUnitPanelContainer() {
		return unitPanelContainer;
	}

	public JLabel getDead() {
		return dead;
	}

	public JLabel getStatus() {
		return status;
	}

	public void updateInfo(String text) {
		String s = "";
		s += "                                 ################\n";
		s += "                                 #         Information         #\n";
		s += "                                 ################\n";
		s += text;

		info.setText(s);
	}
}