package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.CommandCenter;

@SuppressWarnings("serial")
public class StartMenu extends JFrame {

	private JLabel background;

	public StartMenu() throws IOException, FontFormatException {
		setTitle("Rescue Simulation");
		background = new JLabel(new ImageIcon(ImageIO.read(new File("art/backgrounds/mainbackground.jpg"))));
		background.setLayout(new BorderLayout());
		JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
		header.setPreferredSize(new Dimension(300, 150));

		InputStream resHeader = new BufferedInputStream(new FileInputStream("fonts/Oswald-Medium.ttf"));
		Font headerFont = Font.createFont(Font.TRUETYPE_FONT, resHeader);
		headerFont = headerFont.deriveFont(Font.PLAIN, 80);

		JLabel rescueSimualtion = new JLabel("Rescue Simulation");
		rescueSimualtion.setForeground(new Color(0, 9, 142));
		rescueSimualtion.setFont(headerFont);
		header.add(rescueSimualtion);
		header.setOpaque(false);

		JPanel menu = new JPanel(new FlowLayout());
		menu.setPreferredSize(new Dimension(200, 500));

		JButton startGame = new JButton("Start Game");
		JButton exit = new JButton("Exit");

		InputStream resMenu = new BufferedInputStream(new FileInputStream("fonts/RobotoMono-Regular.ttf"));
		Font menuFont = Font.createFont(Font.TRUETYPE_FONT, resMenu);
		menuFont = menuFont.deriveFont(Font.PLAIN, 30);

		exit.setFont(menuFont);
		exit.setPreferredSize(new Dimension(400, 100));
		exit.setForeground(new Color(43, 43, 43));
		exit.setContentAreaFilled(false);
		exit.setFocusPainted(false);
		exit.setBorderPainted(false);

		startGame.setFont(menuFont);
		startGame.setPreferredSize(new Dimension(400, 100));
		startGame.setForeground(new Color(43, 43, 43));
		startGame.setContentAreaFilled(false);
		startGame.setFocusPainted(false);
		startGame.setBorderPainted(false);

		JPanel buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonsContainer.setOpaque(false);
		menu.add(startGame);
		menu.add(exit);
		menu.setOpaque(false);

		buttonsContainer.add(menu);
		background.add(header, BorderLayout.NORTH);
		background.add(buttonsContainer, BorderLayout.CENTER);
		addTheActions(startGame, exit, this);
		add(background);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);
		validate();
		String soundName = "sounds/intro.wav";
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e1) {
		}
	}

	public void addTheActions(JButton b1, JButton b2, StartMenu current) {
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new CommandCenter();
					current.dispose();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});

		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		addMouseListeners(b1);
		addMouseListeners(b2);
	}

	private void addMouseListeners(JButton b) {
		b.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				String soundName = "sounds/tip.wav";
				try {
					AudioInputStream audioInputStream = AudioSystem
							.getAudioInputStream(new File(soundName).getAbsoluteFile());
					Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
				} catch (Exception e1) {
				}
				b.setForeground(new Color(219, 0, 10));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				b.setForeground(new Color(43, 43, 43));

			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
	}

	public static void main(String[] args) throws Exception {
		new StartMenu();
	}

}
