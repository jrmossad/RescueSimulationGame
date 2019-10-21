package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.IncompatibleTargetException;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulator;
import view.RescueSimulationView;

public class CommandCenter implements SOSListener, ActionListener {

	// engine
	private Simulator engine;

	// items used
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private ArrayList<Unit> emergencyUnits;

	// view
	private RescueSimulationView view;
	private ArrayList<JButton> btnsLocation;
	private ArrayList<JButton> btnsUnits;

	// buttons
	private JButton nextCycle;
	private JButton disaster;
	private JButton dead;
	private JButton respond;
	private JButton help;
	private JButton exit;
	private JButton newGame;

	// respond variables
	private Unit tempUnit;
	private ResidentialBuilding tempBuilding;
	private Citizen tempCitizen;
	private int res = 0;

	public CommandCenter() throws Exception {

		// initializing the simulator
		engine = new Simulator(this);

		// initializing the items used
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = engine.getEmergencyUnits();

		// initializing the view and its panels
		view = new RescueSimulationView(engine);
		initialiseEmergencyUnitsBtns();
		intializeLocations();

		settingMenuButtons();

		// setting the view
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setExtendedState(JFrame.MAXIMIZED_BOTH);
		view.setUndecorated(true);
		view.setVisible(true);
		view.validate();
	}

	public Unit getUnitByID(String id) {
		for (int i = 0; i < emergencyUnits.size(); i++) {
			if (emergencyUnits.get(i).getUnitID().equals(id))
				return emergencyUnits.get(i);
		}
		return null;
	}

	public ArrayList<Unit> getUnitByLocation(int x, int y) {
		ArrayList<Unit> units = new ArrayList<>();
		Unit unit = null;
		for (int i = 0; i < emergencyUnits.size(); i++) {
			unit = emergencyUnits.get(i);
			Address location = unit.getLocation();
			if (location.getX() == x && location.getY() == y) {
				units.add(unit);
			}
		}
		return units;
	}

	private void initialiseEmergencyUnitsBtns() throws FontFormatException, IOException {
		btnsUnits = new ArrayList<>();
		for (Unit unit : emergencyUnits) {
			JButton btn = new JButton("ID: " + unit.getUnitID());
			ImageIcon icon = null;
			if (unit instanceof Evacuator)
				icon = new ImageIcon("art/units/Evacuator.png");
			else if (unit instanceof Ambulance)
				icon = new ImageIcon("art/units/Ambulance.png");
			else if (unit instanceof DiseaseControlUnit)
				icon = new ImageIcon("art/units/DiseaseControlUnit.png");
			else if (unit instanceof FireTruck)
				icon = new ImageIcon("art/units/FireTruck.png");
			else if (unit instanceof GasControlUnit)
				icon = new ImageIcon("art/units/GasControlUnit.png");
			Image image = icon.getImage();
			Image newimg = image.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH); // scale it the smooth // way
			icon = new ImageIcon(newimg);
			btn.setIcon(icon);
			btn.setSize(icon.getIconWidth(), icon.getIconHeight());
			btn.setActionCommand("unit");
			InputStream is = new BufferedInputStream(new FileInputStream("fonts/Ubuntu-Regular.ttf"));
			Font font = Font.createFont(Font.TRUETYPE_FONT, is);
			font = font.deriveFont(Font.PLAIN, 15);
			btn.setFont(font);
			btn.setForeground(Color.WHITE);
			btn.setContentAreaFilled(false);
			btn.setFocusPainted(false);
			btn.setBorderPainted(false);
			addMouseListeners(btn);
			view.getUnitPanel().add(btn);
			btn.addActionListener(this);
			btnsUnits.add(btn);
		}
	}

	private void intializeLocations() throws FontFormatException, IOException {
		btnsLocation = new ArrayList<>();
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++) {
				JButton btn = new JButton();
				if (i == 0 && j == 0)
					btn.setActionCommand("unitInfo");
				btn.setContentAreaFilled(false);
				btn.setFocusPainted(false);
				InputStream resHeader = new BufferedInputStream(new FileInputStream("fonts/Oswald-SemiBold.ttf"));
				Font headerFont = Font.createFont(Font.TRUETYPE_FONT, resHeader);
				headerFont = headerFont.deriveFont(Font.PLAIN, 15);
				btn.setForeground(new Color(7, 0, 156));
				btn.setFont(headerFont);
				btn.addActionListener(this);
				btnsLocation.add(btn);
				view.getRescuePanel().add(btn);
			}
	}

	private void decorateButton(JButton j, String s) throws FontFormatException, IOException {
		InputStream resMenu = new BufferedInputStream(new FileInputStream("fonts/Ubuntu-Regular.ttf"));
		Font menuFont = Font.createFont(Font.TRUETYPE_FONT, resMenu);
		menuFont = menuFont.deriveFont(Font.PLAIN, 30);
		j.setFont(menuFont);
		j.setForeground(Color.WHITE);
		j.setPreferredSize(new Dimension(200, 50));
		j.setActionCommand(s);
		j.setContentAreaFilled(false);
		j.setFocusPainted(false);
		j.setBorderPainted(false);
		j.addActionListener(this);
		addMouseListeners(j);
		view.getControlPanel().add(j);
	}

	private void settingMenuButtons() throws FontFormatException, IOException {
		// initializing buttons
		nextCycle = new JButton("Start");
		disaster = new JButton("Disasters");
		dead = new JButton("Destroyed");
		respond = new JButton("Respond");
		help = new JButton("Help");
		newGame = new JButton("New Game");
		exit = new JButton("Exit");

		// decorating buttons
		decorateButton(nextCycle, "nextCycle");
		decorateButton(disaster, "disaster");
		decorateButton(dead, "destroyed");
		decorateButton(respond, "respond");
		decorateButton(newGame, "newGame");
		decorateButton(help, "help");
		decorateButton(exit, "exit");
		disaster.setVisible(false);
		dead.setVisible(false);
		respond.setVisible(false);
		newGame.setVisible(false);
	}

	@Override
	public void receiveSOSCall(Rescuable r) {
		String soundName = "sounds/destroyed.wav";
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception e1) {
		}
		int x = r.getLocation().getX() * 10 + r.getLocation().getY();
		addMouseListeners(btnsLocation.get(x));
		if (r instanceof ResidentialBuilding) {
			if (btnsLocation.get(x).getText().equals("$") || btnsLocation.get(x).getText().equals("* - $"))
				btnsLocation.get(x).setText("* - $");
			else if (btnsLocation.get(x).getText().equals("S - R") || btnsLocation.get(x).getText().equals("* - R"))
				btnsLocation.get(x).setText("* - R");
			else if (btnsLocation.get(x).getText().equals("S - $"))
				btnsLocation.get(x).setText("* - $");
			else if (btnsLocation.get(x).getText().equals("S - P") || btnsLocation.get(x).getText().equals("* - P"))
				btnsLocation.get(x).setText("* - P");
			else
				btnsLocation.get(x).setText("*");
			btnsLocation.get(x).setActionCommand("building");
			if (!visibleBuildings.contains(r)) {
				visibleBuildings.add((ResidentialBuilding) r);
			}
		} else {
			if (btnsLocation.get(x).getText().equals("*") || btnsLocation.get(x).getText().equals("* - $")) {
				btnsLocation.get(x).setActionCommand("building");
				btnsLocation.get(x).setText("* - $");
			} else if (btnsLocation.get(x).getText().equals("S - R") || btnsLocation.get(x).getText().equals("S - $"))
				btnsLocation.get(x).setText("S - $");
			else if (btnsLocation.get(x).getText().equals("* - R"))
				btnsLocation.get(x).setText("* - $");
			else {
				btnsLocation.get(x).setActionCommand("citizen");
				btnsLocation.get(x).setText("$");
			}
			if (!visibleCitizens.contains(r))
				visibleCitizens.add((Citizen) r);
		}
	}

	private String updateInfo() {
		String s = "";
		for (int i = 0; i < visibleBuildings.size(); i++) {
			ResidentialBuilding building = visibleBuildings.get(i);
			if (!(building.getFireDamage() == 0 && building.getGasLevel() == 0 && building.getFoundationDamage() == 0)
					&& building.getStructuralIntegrity() > 0)
				s += "\n The building in the location " + visibleBuildings.get(i).getLocation() + " is struck by "
						+ visibleBuildings.get(i).getDisaster().getClass().getSimpleName()
						+ "\n **********************************************\n";
		}
		for (int i = 0; i < visibleCitizens.size(); i++)
			if (visibleCitizens.get(i).getState() != CitizenState.RESCUED
					&& visibleCitizens.get(i).getState() != CitizenState.DECEASED)
				s += "\n The citizen with the id " + visibleCitizens.get(i).getNationalID() + " in the location "
						+ visibleCitizens.get(i).getLocation() + "\n is struck by "
						+ visibleCitizens.get(i).getDisaster().getClass().getSimpleName()
						+ "\n **********************************************\n";

		return s;
	}

	private String dead() {
		String s = "";
		for (int i = 0; i < visibleBuildings.size(); i++) {
			ResidentialBuilding building = visibleBuildings.get(i);
			if (building.getStructuralIntegrity() == 0)
				s += "\n The building in the location " + visibleBuildings.get(i).getLocation()
						+ " has been destroyed \n by "
						+ visibleBuildings.get(i).getDisaster().getClass().getSimpleName()
						+ "\n **********************************************\n";
		}
		for (int i = 0; i < visibleCitizens.size(); i++)
			if (visibleCitizens.get(i).getState() == CitizenState.DECEASED)
				s += "\n The citizen with the id " + visibleCitizens.get(i).getNationalID() + " in the location "
						+ visibleCitizens.get(i).getLocation() + "\n has died by the affect of "
						+ visibleCitizens.get(i).getDisaster().getClass().getSimpleName()
						+ "\n **********************************************\n";
		return s;
	}

	private void updateAllInfo() {
		String s = updateInfo() + dead();
		view.updateInfo(s);
	}

	private void updateCycle() {
		String s = "";
		s += "Number of Casualities is " + engine.calculateCasualties();
		view.getDead().setText(s);
		s = "Current Cycle is " + engine.getCurrentCycle();
		view.getStatus().setText(s);
	}

	private void updateLocation() {
		for (Iterator<Citizen> i = visibleCitizens.iterator(); i.hasNext();) {
			Citizen citizen = i.next();
			int x = citizen.getLocation().getX() * 10 + citizen.getLocation().getY();
			if (getUnitByLocation(citizen.getLocation().getX(), citizen.getLocation().getY()).size() != 0)
				btnsLocation.get(x).setBorder(BorderFactory.createLineBorder(new Color(153, 0, 7), 3));
			else
				btnsLocation.get(x).setBorder(BorderFactory.createEmptyBorder());
			if (citizen.getState() == CitizenState.DECEASED) {
				ResidentialBuilding building = getBuildingByLocation(citizen.getLocation().getX(),
						citizen.getLocation().getY());
				if (building != null)
					if (building.getStructuralIntegrity() == 0)
						btnsLocation.get(x).setText("D - P");
					else if (building.getFireDamage() == 0 && building.getGasLevel() == 0
							&& building.getFoundationDamage() == 0)
						btnsLocation.get(x).setText("S - P");
					else
						btnsLocation.get(x).setText("* - P");
				else {
					btnsLocation.get(x).setText("P");
					btnsLocation.get(x).setActionCommand("citizen");

				}

			} else if (citizen.getState() == CitizenState.RESCUED) {
				ResidentialBuilding building = getBuildingByLocation(citizen.getLocation().getX(),
						citizen.getLocation().getY());
				if (building != null) {
					boolean flag = true;
					for (Iterator<Citizen> j = getCitizenByLocation(citizen.getLocation().getX(),
							citizen.getLocation().getY()).iterator(); j.hasNext();)
						if (j.next() != citizen) {
							flag = false;
							break;
						}
					if (building.getStructuralIntegrity() != 0) {
						if (flag)
							btnsLocation.get(x).setText("* - R");
					} else if (building.getFireDamage() == 0 && building.getGasLevel() == 0
							&& building.getFoundationDamage() == 0) {
						if (flag)
							btnsLocation.get(x).setText("S - R");
					}
				} else {
					boolean flag = true;
					for (Iterator<Citizen> j = getCitizenByLocation(citizen.getLocation().getX(),
							citizen.getLocation().getY()).iterator(); j.hasNext();)
						if (j.next() != citizen) {
							flag = false;
							break;
						}
					if (flag)
						btnsLocation.get(x).setText("R");
					else
						btnsLocation.get(x).setText("$");
					btnsLocation.get(x).setActionCommand("citizen");
					addMouseListeners(btnsLocation.get(x));
				}
			} else if (citizen.getState() == CitizenState.IN_TROUBLE) {
				ResidentialBuilding building = getBuildingByLocation(citizen.getLocation().getX(),
						citizen.getLocation().getY());
				if (building != null)
					if (building.getFireDamage() == 0 && building.getGasLevel() == 0
							&& building.getFoundationDamage() == 0)
						btnsLocation.get(x).setText("S - $");
					else
						btnsLocation.get(x).setText("* - $");
				else {
					btnsLocation.get(x).setText("$");
					btnsLocation.get(x).setActionCommand("citizen");
				}
			}
		}

		for (Iterator<ResidentialBuilding> i = visibleBuildings.iterator(); i.hasNext();) {
			ResidentialBuilding building = i.next();
			int x = building.getLocation().getX() * 10 + building.getLocation().getY();
			if (getUnitByLocation(building.getLocation().getX(), building.getLocation().getY()).size() != 0)
				btnsLocation.get(x).setBorder(BorderFactory.createLineBorder(new Color(153, 0, 7), 3));
			else
				btnsLocation.get(x).setBorder(BorderFactory.createEmptyBorder());
			ArrayList<Citizen> citizens = getCitizenByLocation(building.getLocation().getX(),
					building.getLocation().getY());
			if (citizens.size() == 0) {
				if (building.getStructuralIntegrity() == 0) {
					btnsLocation.get(x).setText("D");
				} else {
					if (building.getFireDamage() == 0 && building.getGasLevel() == 0
							&& building.getFoundationDamage() == 0)
						btnsLocation.get(x).setText("S");
				}
			}
		}
	}

	public String occupantsInfo(ArrayList<Citizen> citizens) {
		String s = "";
		for (int i = 0; i < citizens.size(); i++)
			s += " Citizen " + (i + 1) + ":\n*********\n" + citizens.get(i).toString()
					+ " *****************************\n";
		return s;
	}

	public String UnitInfo(ArrayList<Unit> units) {
		String s = "";
		for (int i = 0; i < units.size(); i++)
			s += " Unit " + (i + 1) + ":\n******\n" + units.get(i).toString() + " *****************************\n";
		return s;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("nextCycle")) {
			if (res == 0) {
				if (!(engine.checkGameOver())) {
					try {
						engine.nextCycle();
						updateCycle();
						updateLocation();
						updateAllInfo();
					} catch (BuildingAlreadyCollapsedException | CitizenAlreadyDeadException e1) {

						JOptionPane.showMessageDialog(null, e1.getMessage(), "WARNING!", JOptionPane.WARNING_MESSAGE);
					}
					if (getUnitByLocation(0, 0).size() != 0) {
						btnsLocation.get(0).setBorder(BorderFactory.createLineBorder(new Color(153, 0, 7), 3));
					} else {
						btnsLocation.get(0).setBorder(BorderFactory.createEmptyBorder());
					}
					view.getStart().setText("Enjoy!");
					nextCycle.setText("Next Cycle");
					respond.setVisible(true);
					disaster.setVisible(true);
					dead.setVisible(true);
					tempCitizen = null;
					tempBuilding = null;
					tempUnit = null;
				} else {
					JOptionPane.showMessageDialog(null,
							"        GAME IS OVER!\nNumber of Casualities is " + engine.calculateCasualties(),
							"WARNING!", JOptionPane.WARNING_MESSAGE);
					view.getStatus().setText("End Cycle is " + engine.getCurrentCycle());
					for (JButton jButton : btnsLocation) {
						for (ActionListener al : jButton.getActionListeners()) {
							jButton.removeActionListener(al);
						}
					}
					for (JButton jButton : btnsUnits) {
						for (ActionListener al : jButton.getActionListeners()) {
							jButton.removeActionListener(al);
						}
					}
					view.getStart().setText("Game is Over!");
					view.updateInfo("\nHope you Enjoyed the game");
					respond.setVisible(false);
					nextCycle.setVisible(false);
					disaster.setVisible(false);
					dead.setVisible(false);
					newGame.setVisible(true);
				}
			} else
				JOptionPane.showMessageDialog(null, "Please Finish Responding First", "WARNING!",
						JOptionPane.WARNING_MESSAGE);
		} else if (e.getActionCommand().equals("unit")) {
			if (res == 1)
				tempUnit = getUnitByID(((JButton) e.getSource()).getText().substring(4));
			String s = "";
			s += " Unit:\n ****\n" + getUnitByID(((JButton) e.getSource()).getText().substring(4));
			view.updateInfo(s);
		} else if (e.getActionCommand().equals("building")) {
			String s = "";
			int z = btnsLocation.indexOf(((JButton) e.getSource())) / 10;
			int y = btnsLocation.indexOf(((JButton) e.getSource())) % 10;
			s += " Building:\n *********\n" + getBuildingByLocation(z, y);
			s += UnitInfo(getUnitByLocation(z, y));
			view.updateInfo(s);
			ResidentialBuilding building = getBuildingByLocation(z, y);
			if (getCitizenByLocation(z, y).size() != 0 && res == 1) {
				int n = 0;
				ArrayList<Citizen> citizens = new ArrayList<>();
				for (Iterator<Citizen> k = getCitizenByLocation(z, y).iterator(); k.hasNext();) {
					Citizen citizen = k.next();
					if (citizen.getState() != CitizenState.DECEASED && citizen.getState() != CitizenState.RESCUED)
						citizens.add(citizen);
				}
				if (citizens.size() == 0) {
					Object[] options1 = { "Building" };
					n = JOptionPane.showOptionDialog(null, "What do you want to respond to?", "Respond",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options1, null);
					if (n == 0)
						tempBuilding = building;
				} else {
					Object[] options2 = { "Building", "Citizen" };
					n = JOptionPane.showOptionDialog(null, "What do you want to respond to?", "Respond",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options2, null);
					if (n == 0) {
						tempBuilding = building;
					} else {
						Object[] p = new Object[citizens.size()];
						Iterator<Citizen> w = citizens.iterator();
						for (int r = 0; r < p.length; r++)
							p[r] = w.next().getName();
						@SuppressWarnings({ "unchecked", "rawtypes" })
						JComboBox optionList = new JComboBox(p);
						optionList.setSelectedIndex(p.length - 1);
						JOptionPane.showMessageDialog(null, optionList, "Respond", JOptionPane.QUESTION_MESSAGE);
						String t = (String) optionList.getSelectedItem();
						for (Iterator<Citizen> q = citizens.iterator(); q.hasNext();) {
							Citizen c = q.next();
							if (c.getName().equals(t)) {
								tempCitizen = c;
								break;
							}
						}
					}
				}
			} else if (res == 1)
				tempBuilding = getBuildingByLocation(z, y);
		} else if (e.getActionCommand().equals("citizen")) {
			String s = "";
			int z = btnsLocation.indexOf(((JButton) e.getSource())) / 10;
			int y = btnsLocation.indexOf(((JButton) e.getSource())) % 10;
			s += occupantsInfo(getCitizenByLocation(z, y));
			s += UnitInfo(getUnitByLocation(z, y));
			view.updateInfo(s);
			if (res == 1)
				tempCitizen = getCitizenByLocation(z, y).get(0);
		} else if (e.getActionCommand().equals("respond")) {
			if (res == 0) {
				JOptionPane.showMessageDialog(null, "Please select the target and the unit!", "Info",
						JOptionPane.INFORMATION_MESSAGE);
				respond.setText("Responding");
				res = 1;
			} else if (tempCitizen != null && tempUnit != null && res == 1) {
				try {
					tempUnit.respond(tempCitizen);
					String s = "";
					s += " " + tempUnit.getClass().getSimpleName() + " " + tempUnit.getUnitID() + " is responding to: ";
					s += "\n $" + tempCitizen.getName();
					s += " in the location " + tempCitizen.getLocation();
					view.updateInfo(s);
					tempBuilding = null;
					tempCitizen = null;
					tempUnit = null;
					res = 0;
					respond.setText("Respond");
				} catch (IncompatibleTargetException | CannotTreatException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "WARNING!", JOptionPane.WARNING_MESSAGE);
					tempBuilding = null;
					tempCitizen = null;
					tempUnit = null;
					res = 0;
					respond.setText("Respond");
				}
			} else if (tempBuilding != null && tempUnit != null && res == 1) {
				try {
					tempUnit.respond(tempBuilding);
					String s = "";
					s += " " + tempUnit.getClass().getSimpleName() + " " + tempUnit.getUnitID() + " is responding to: ";
					s += "\n $the building";
					s += " in the location " + tempBuilding.getLocation();
					view.updateInfo(s);
					tempBuilding = null;
					tempCitizen = null;
					tempUnit = null;
					res = 0;
					respond.setText("Respond");
				} catch (CannotTreatException | IncompatibleTargetException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "WARNING!", JOptionPane.WARNING_MESSAGE);
					tempBuilding = null;
					tempCitizen = null;
					tempUnit = null;
					res = 0;
					respond.setText("Respond");
				}
			}

			else {
				JOptionPane.showMessageDialog(null, "Please choose the unit and the target correctly", "WARNING!",
						JOptionPane.WARNING_MESSAGE);
				tempBuilding = null;
				tempCitizen = null;
				tempUnit = null;
				res = 0;
				respond.setText("Respond");
			}
		} else if (e.getActionCommand().equals("exit"))
			System.exit(0);
		else if (e.getActionCommand().equals("newGame")) {
			try {
				new CommandCenter();
				view.dispose();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (e.getActionCommand().equals("disaster")) {
			view.updateInfo(updateInfo());
		} else if (e.getActionCommand().equals("help")) {
			JOptionPane.showMessageDialog(null,
					"~Symbols meaning:\n                                  -S: Saved Building\n                                  "
							+ "-R: Rescued Citizen\n                                  -*: Building in Trouble\n                                  "
							+ "-$: Citizen in Trouble\n                                  -D: Destroyed Building\n"
							+ "                                  -P: Dead Citizen"
							+ "\n~The Location 0 0 is the base and the safe place"
							+ "\n~The Location containing units is represented by red border"
							+ "\n~The Board shows the recuable item - Citizen or Building - affected by a disaster\n"
							+ "~You can rescue an item by clicking respond and selecting the suitable emergency unit from the unit panel\n"
							+ "~When you click on any item, all its info is shown in the info panel\n"
							+ "~Keep in mind that when you rescue a citizen and there is another citizen in danger in"
							+ " the building the cell will show $ as danger has higher priority",
					"Help", JOptionPane.INFORMATION_MESSAGE);
		} else if (e.getActionCommand().equals("unitInfo")) {
			String s = UnitInfo(getUnitByLocation(0, 0));
			view.updateInfo(s);
		} else if (e.getActionCommand().equals("destroyed"))
			view.updateInfo(dead());
	}

	private ResidentialBuilding getBuildingByLocation(int x, int y) {
		ResidentialBuilding building = null;
		for (int i = 0; i < visibleBuildings.size(); i++) {
			building = visibleBuildings.get(i);
			Address location = building.getLocation();
			if (location.getX() == x && location.getY() == y) {
				return building;
			}
		}
		return null;
	}

	private ArrayList<Citizen> getCitizenByLocation(int x, int y) {
		ArrayList<Citizen> citizens = new ArrayList<>();
		Citizen citizen = null;
		for (int i = 0; i < visibleCitizens.size(); i++) {
			citizen = visibleCitizens.get(i);
			Address location = citizen.getLocation();
			if (location.getX() == x && location.getY() == y) {
				citizens.add(citizen);
			}
		}
		return citizens;
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
				b.setForeground(new Color(255, 203, 41));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				if (b.getActionCommand().equals("citizen") || b.getActionCommand().equals("building"))
					b.setForeground(new Color(7, 0, 156));
				else
					b.setForeground(Color.WHITE);
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
	}

}
