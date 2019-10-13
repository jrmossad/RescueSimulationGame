package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class M1PublicTests
{
	String simulatorPath = "simulation.Simulator";
	String addressPath = "simulation.Address";
	String rescuablePath = "simulation.Rescuable";
	String simulatablePath = "simulation.Simulatable";
	String residentialBuildingPath = "model.infrastructure.ResidentialBuilding";
	String citizenStatePath = "model.people.CitizenState";
	String unitStatePath = "model.units.UnitState";
	String citizenPath = "model.people.Citizen";
	String unitPath = "model.units.Unit";
	String policeUnitPath = "model.units.PoliceUnit";
	String fireUnitPath = "model.units.FireUnit";
	String medicalUnitPath = "model.units.MedicalUnit";
	String evacuatorPath = "model.units.Evacuator";
	String fireTruckPath = "model.units.FireTruck";
	String gasControlUnitPath = "model.units.GasControlUnit";
	String ambulancePath = "model.units.Ambulance";
	String diseaseControlUnitPath = "model.units.DiseaseControlUnit";
	String disasterPath = "model.disasters.Disaster";
	String collapsePath = "model.disasters.Collapse";
	String firePath = "model.disasters.Fire";
	String gasLeakPath = "model.disasters.GasLeak";
	String infectionPath = "model.disasters.Infection";
	String injuryPath = "model.disasters.Injury";
	String commandCenterPath = "controller.CommandCenter";

	@Test(timeout = 1000)
	public void testReadingCitizenFromCSV() throws Exception {
		try{
		final Field f = Class.forName(simulatorPath).getDeclaredField(
				"citizens");

		f.setAccessible(true);

		final Field f1 = Class.forName(simulatorPath).getDeclaredField(
				"buildings");
		
		f1.setAccessible(true);
		final Field f2 = Class.forName(simulatorPath).getDeclaredField("world");

		f2.setAccessible(true);
		
		final Field f3 = Class.forName(simulatorPath).getDeclaredField("plannedDisasters");
		f3.setAccessible(true);
		
		final Field f4 = Class.forName(simulatorPath).getDeclaredField("emergencyUnits");
		f4.setAccessible(true);
		
		
		

		PrintWriter citizensWriter = new PrintWriter("citizen_test.csv");

		citizensWriter.println("5,5,1,zizo,25");
		citizensWriter.println("5,5,2,amr,30");
		citizensWriter.println("5,5,3,karam,40");
		citizensWriter.println("5,5,4,maisarah,45");
		citizensWriter.println("5,5,5,mostafa,20");

		citizensWriter.println("9,9,1,zizo1,25");
		citizensWriter.println("9,9,2,amr1,30");
		citizensWriter.println("9,9,3,karam1,40");
		citizensWriter.println("9,9,4,maisarah1,45");
		citizensWriter.println("9,9,5,mostafa1,20");

		citizensWriter.close();

		PrintWriter buildingWriter = new PrintWriter("buildings_test.csv");

		buildingWriter.println("5,5");
		buildingWriter.println("9,9");

		buildingWriter.close();

		Object s = Class.forName(simulatorPath).getConstructor().newInstance();
		
		
		Method method = Class.forName(simulatorPath).getDeclaredMethod(
				"loadBuildings", new Class[] { String.class });
		method.setAccessible(true);
		
		
		
		
		
		if(((ArrayList<Object>) f1.get(s)).size()==0)
			fail("Simulator constructor should load the buildings from the corresponding csv file.");
		if(((ArrayList<Object>) f.get(s)).size()==0)
			fail("Simulator constructor should load the citizens from the corresponding csv file.");
		if(((ArrayList<Object>) f3.get(s)).size()==0)
			fail("Simulator constructor should load the disasters from the corresponding csv file.");
		if(((ArrayList<Object>) f4.get(s)).size()==0)
			fail("Simulator constructor should load the units from the corresponding csv file.");
	
		}
		catch(Exception e){
			fail("Simulator constructor should load buildings and citizens correctly.");
		}
		finally{
			File buildingFile = new File("buildings_test.csv");
			buildingFile.delete();
			File citizenFile = new File("citizen_test.csv");
			citizenFile.delete();
		}
	}

	@Test(timeout = 1000)
	public void testConstructorCommandCenterConstructor2Initialization()
			throws Exception {
		Object commandCenter2 = Class.forName(commandCenterPath)
				.getConstructor().newInstance();
		String[] names = { "visibleBuildings", "visibleCitizens",
				"emergencyUnits" };
		Object[] values = { new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>() };
		testConstructorInitialization(commandCenter2, names, values);

		final Field f = Class.forName(commandCenterPath).getDeclaredField(
				"engine");
		f.setAccessible(true);

		Object engine = f.get(commandCenter2);

		assertFalse("CommandCenter \"engine\" should not be null",
				engine == null);
	}
	
	@Test(timeout = 1000)
	public void testClassIsAbstractPoliceUnit() throws Exception 
	{
		testClassIsAbstract(Class.forName(policeUnitPath));
	}

	@Test(timeout = 1000)
	public void testClassIsAbstractFireUnit() throws Exception 
	{
		testClassIsAbstract(Class.forName(fireUnitPath));
	}

	@Test(timeout = 1000)
	public void testClassIsAbstractDisaster() throws Exception 
	{
		testClassIsAbstract(Class.forName(disasterPath));
	}

	@Test(timeout = 1000)
	public void testClassIsEnumUnitState() throws Exception 
	{
		testIsEnum(Class.forName(unitStatePath));
	}

	@Test(timeout = 1000)
	public void testClassIsSubclassFireUnit() throws Exception 
	{
		testClassIsSubclass(Class.forName(fireUnitPath), Class.forName(unitPath));
	}

	@Test(timeout = 1000)
	public void testClassIsSubclassMedicalUnit() throws Exception 
	{
		testClassIsSubclass(Class.forName(medicalUnitPath), Class.forName(unitPath));
	}

	@Test(timeout = 1000)
	public void testClassIsSubclassFireTruck() throws Exception 
	{
		testClassIsSubclass(Class.forName(fireTruckPath), Class.forName(fireUnitPath));
	}

	@Test(timeout = 1000)
	public void testClassIsSubclassGasControlUnit() throws Exception 
	{
		testClassIsSubclass(Class.forName(gasControlUnitPath), Class.forName(fireUnitPath));
	}

	@Test(timeout = 1000)
	public void testClassIsSubclassDiseaseControlUnit() throws Exception 
	{
		testClassIsSubclass(Class.forName(diseaseControlUnitPath), Class.forName(medicalUnitPath));
	}

	@Test(timeout = 1000)
	public void testClassIsSubclassCollapse() throws Exception 
	{
		testClassIsSubclass(Class.forName(collapsePath), Class.forName(disasterPath));
	}

	@Test(timeout = 1000)
	public void testClassIsSubclassGasLeak() throws Exception 
	{
		testClassIsSubclass(Class.forName(gasLeakPath), Class.forName(disasterPath));
	}

	@Test(timeout = 1000)
	public void testClassIsSubclassInfection() throws Exception 
	{
		testClassIsSubclass(Class.forName(infectionPath), Class.forName(disasterPath));
	}

	@Test(timeout = 1000)
	public void testConstructorAddress0() throws Exception 
	{
		Class[] inputs = {int.class, int.class};
		testConstructorExists(Class.forName(addressPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorResidentialBuilding0() throws Exception 
	{
		Class[] inputs = {Class.forName(addressPath)};
		testConstructorExists(Class.forName(residentialBuildingPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorUnit0() throws Exception 
	{
		Class[] inputs = {String.class, Class.forName(addressPath), int.class};
		testConstructorExists(Class.forName(unitPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorPoliceUnit0() throws Exception 
	{
		Class[] inputs = {String.class, Class.forName(addressPath), int.class, int.class};
		testConstructorExists(Class.forName(policeUnitPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorMedicalUnit0() throws Exception 
	{
		Class[] inputs = {String.class, Class.forName(addressPath), int.class};
		testConstructorExists(Class.forName(medicalUnitPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorEvacuator0() throws Exception 
	{
		Class[] inputs = {String.class, Class.forName(addressPath), int.class, int.class};
		testConstructorExists(Class.forName(evacuatorPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorGasControlUnit0() throws Exception 
	{
		Class[] inputs = {String.class, Class.forName(addressPath), int.class};
		testConstructorExists(Class.forName(gasControlUnitPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorAmbulance0() throws Exception 
	{
		Class[] inputs = {String.class, Class.forName(addressPath), int.class};
		testConstructorExists(Class.forName(ambulancePath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorDisaster0() throws Exception 
	{
		Class[] inputs = {int.class, Class.forName(rescuablePath)};
		testConstructorExists(Class.forName(disasterPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorCollapse0() throws Exception 
	{
		Class[] inputs = {int.class, Class.forName(residentialBuildingPath)};
		testConstructorExists(Class.forName(collapsePath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorGasLeak0() throws Exception 
	{
		Class[] inputs = {int.class, Class.forName(residentialBuildingPath)};
		testConstructorExists(Class.forName(gasLeakPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorInfection0() throws Exception 
	{
		Class[] inputs = {int.class, Class.forName(citizenPath)};
		testConstructorExists(Class.forName(infectionPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorCommandCenter0() throws Exception 
	{
		Class[] inputs = {};
		testConstructorExists(Class.forName(commandCenterPath), inputs);
	}

	@Test(timeout = 1000)
	public void testEnumValuesUnitState() throws Exception 
	{
		String[] inputs = {"IDLE", "RESPONDING", "TREATING"};
		testEnumValues(Class.forName(unitStatePath), inputs);
	}

	@Test(timeout = 1000)
	public void testClassIsInterfaceSimulatable() throws Exception 
	{
		testIsInterface(Class.forName(simulatablePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableSimulatorBuildings() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(simulatorPath), "buildings", true);
		testInstanceVariableIsPrivate(Class.forName(simulatorPath), "buildings");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableSimulatorCitizens() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(simulatorPath), "citizens", true);
		testInstanceVariableIsPrivate(Class.forName(simulatorPath), "citizens");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableSimulatorPlannedDisasters() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(simulatorPath), "plannedDisasters", true);
		testInstanceVariableIsPrivate(Class.forName(simulatorPath), "plannedDisasters");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableSimulatorWorld() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(simulatorPath), "world", true);
		testInstanceVariableIsPrivate(Class.forName(simulatorPath), "world");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAddressX() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(addressPath), "x", true);
		testInstanceVariableIsPrivate(Class.forName(addressPath), "x");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAddressY() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(addressPath), "y", true);
		testInstanceVariableIsPrivate(Class.forName(addressPath), "y");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingStructuralIntegrity() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(residentialBuildingPath), "structuralIntegrity", true);
		testInstanceVariableIsPrivate(Class.forName(residentialBuildingPath), "structuralIntegrity");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingFireDamage() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(residentialBuildingPath), "fireDamage", true);
		testInstanceVariableIsPrivate(Class.forName(residentialBuildingPath), "fireDamage");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingFoundationDamage() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(residentialBuildingPath), "foundationDamage", true);
		testInstanceVariableIsPrivate(Class.forName(residentialBuildingPath), "foundationDamage");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingOccupants() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(residentialBuildingPath), "occupants", true);
		testInstanceVariableIsPrivate(Class.forName(residentialBuildingPath), "occupants");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenState() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(citizenPath), "state", true);
		testInstanceVariableIsPrivate(Class.forName(citizenPath), "state");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenDisaster() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(citizenPath), "disaster", true);
		testInstanceVariableIsPrivate(Class.forName(citizenPath), "disaster");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenNationalID() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(citizenPath), "nationalID", true);
		testInstanceVariableIsPrivate(Class.forName(citizenPath), "nationalID");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenAge() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(citizenPath), "age", true);
		testInstanceVariableIsPrivate(Class.forName(citizenPath), "age");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenBloodLoss() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(citizenPath), "bloodLoss", true);
		testInstanceVariableIsPrivate(Class.forName(citizenPath), "bloodLoss");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenToxicity() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(citizenPath), "toxicity", true);
		testInstanceVariableIsPrivate(Class.forName(citizenPath), "toxicity");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableUnitUnitID() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(unitPath), "unitID", true);
		testInstanceVariableIsPrivate(Class.forName(unitPath), "unitID");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableUnitState() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(unitPath), "state", true);
		testInstanceVariableIsPrivate(Class.forName(unitPath), "state");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableUnitTarget() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(unitPath), "target", true);
		testInstanceVariableIsPrivate(Class.forName(unitPath), "target");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableUnitDistanceToTarget() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(unitPath), "distanceToTarget", true);
		testInstanceVariableIsPrivate(Class.forName(unitPath), "distanceToTarget");
	}

	@Test(timeout = 1000)
	public void testInstanceVariablePoliceUnitPassengers() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(policeUnitPath), "passengers", true);
		testInstanceVariableIsPrivate(Class.forName(policeUnitPath), "passengers");
	}

	@Test(timeout = 1000)
	public void testInstanceVariablePoliceUnitMaxCapacity() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(policeUnitPath), "maxCapacity", true);
		testInstanceVariableIsPrivate(Class.forName(policeUnitPath), "maxCapacity");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableMedicalUnitHealingAmount() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(medicalUnitPath), "healingAmount", true);
		testInstanceVariableIsPrivate(Class.forName(medicalUnitPath), "healingAmount");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableMedicalUnitTreatmentAmount() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(medicalUnitPath), "treatmentAmount", true);
		testInstanceVariableIsPrivate(Class.forName(medicalUnitPath), "treatmentAmount");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDisasterTarget() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(disasterPath), "target", true);
		testInstanceVariableIsPrivate(Class.forName(disasterPath), "target");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDisasterActive() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(disasterPath), "active", true);
		testInstanceVariableIsPrivate(Class.forName(disasterPath), "active");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCommandCenterVisibleBuildings() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(commandCenterPath), "visibleBuildings", true);
		testInstanceVariableIsPrivate(Class.forName(commandCenterPath), "visibleBuildings");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCommandCenterVisibleCitizens() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(commandCenterPath), "visibleCitizens", true);
		testInstanceVariableIsPrivate(Class.forName(commandCenterPath), "visibleCitizens");
	}

	@Test(timeout = 100)
	public void testUnitCurrentResourcesNotInClasses() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(gasControlUnitPath), "currentResources", false);
		testInstanceVariableIsPresent(Class.forName(policeUnitPath), "currentResources", false);
		testInstanceVariableIsPresent(Class.forName(evacuatorPath), "currentResources", false);
		testInstanceVariableIsPresent(Class.forName(fireUnitPath), "currentResources", false);
		testInstanceVariableIsPresent(Class.forName(fireTruckPath), "currentResources", false);
		testInstanceVariableIsPresent(Class.forName(medicalUnitPath), "currentResources", false);
		testInstanceVariableIsPresent(Class.forName(ambulancePath), "currentResources", false);
		testInstanceVariableIsPresent(Class.forName(diseaseControlUnitPath), "currentResources", false);
	}

	@Test(timeout = 100)
	public void testUnitCurrentLocationNotInClasses() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(gasControlUnitPath), "currentLocation", false);
		testInstanceVariableIsPresent(Class.forName(policeUnitPath), "currentLocation", false);
		testInstanceVariableIsPresent(Class.forName(evacuatorPath), "currentLocation", false);
		testInstanceVariableIsPresent(Class.forName(fireUnitPath), "currentLocation", false);
		testInstanceVariableIsPresent(Class.forName(fireTruckPath), "currentLocation", false);
		testInstanceVariableIsPresent(Class.forName(medicalUnitPath), "currentLocation", false);
		testInstanceVariableIsPresent(Class.forName(ambulancePath), "currentLocation", false);
		testInstanceVariableIsPresent(Class.forName(diseaseControlUnitPath), "currentLocation", false);
	}

	@Test(timeout = 100)
	public void testUnitDistanceToTargetNotInClasses() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(gasControlUnitPath), "distanceToTarget", false);
		testInstanceVariableIsPresent(Class.forName(policeUnitPath), "distanceToTarget", false);
		testInstanceVariableIsPresent(Class.forName(evacuatorPath), "distanceToTarget", false);
		testInstanceVariableIsPresent(Class.forName(fireUnitPath), "distanceToTarget", false);
		testInstanceVariableIsPresent(Class.forName(fireTruckPath), "distanceToTarget", false);
		testInstanceVariableIsPresent(Class.forName(medicalUnitPath), "distanceToTarget", false);
		testInstanceVariableIsPresent(Class.forName(ambulancePath), "distanceToTarget", false);
		testInstanceVariableIsPresent(Class.forName(diseaseControlUnitPath), "distanceToTarget", false);
	}

	@Test(timeout = 100)
	public void testUnitStepsPerCycleNotInClasses() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(gasControlUnitPath), "stepsPerCycle", false);
		testInstanceVariableIsPresent(Class.forName(policeUnitPath), "stepsPerCycle", false);
		testInstanceVariableIsPresent(Class.forName(evacuatorPath), "stepsPerCycle", false);
		testInstanceVariableIsPresent(Class.forName(fireUnitPath), "stepsPerCycle", false);
		testInstanceVariableIsPresent(Class.forName(fireTruckPath), "stepsPerCycle", false);
		testInstanceVariableIsPresent(Class.forName(medicalUnitPath), "stepsPerCycle", false);
		testInstanceVariableIsPresent(Class.forName(ambulancePath), "stepsPerCycle", false);
		testInstanceVariableIsPresent(Class.forName(diseaseControlUnitPath), "stepsPerCycle", false);
	}

	@Test(timeout = 100)
	public void testDisasterTargetNotInClasses() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(injuryPath), "target", false);
		testInstanceVariableIsPresent(Class.forName(firePath), "target", false);
		testInstanceVariableIsPresent(Class.forName(collapsePath), "target", false);
		testInstanceVariableIsPresent(Class.forName(infectionPath), "target", false);
		testInstanceVariableIsPresent(Class.forName(gasLeakPath), "target", false);
	}

	@Test(timeout = 100)
	public void testDisasterActiveNotInClasses() throws Exception 
	{
		testInstanceVariableIsPresent(Class.forName(injuryPath), "active", false);
		testInstanceVariableIsPresent(Class.forName(firePath), "active", false);
		testInstanceVariableIsPresent(Class.forName(collapsePath), "active", false);
		testInstanceVariableIsPresent(Class.forName(infectionPath), "active", false);
		testInstanceVariableIsPresent(Class.forName(gasLeakPath), "active", false);
	}

	@Test(timeout = 1000)
	public void testResidentialBuildingImplementsSimulatable() throws Exception 
	{
		testClassImplementsInterface(Class.forName(residentialBuildingPath), Class.forName(simulatablePath));
	}

	@Test(timeout = 1000)
	public void testCitizenImplementsRescuable() throws Exception 
	{
		testClassImplementsInterface(Class.forName(citizenPath), Class.forName(rescuablePath));
	}

	@Test(timeout = 1000)
	public void testUnitImplementsSimulatable() throws Exception 
	{
		testClassImplementsInterface(Class.forName(unitPath), Class.forName(simulatablePath));
	}

	@Test(timeout = 1000)
	public void testDisasterImplementsSimulatable() throws Exception 
	{
		testClassImplementsInterface(Class.forName(disasterPath), Class.forName(simulatablePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableSimulatorBuildingsGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(simulatorPath), "getBuildings", ArrayList.class, false);
		testSetterMethodExistsInClass(Class.forName(simulatorPath), "setBuildings", ArrayList.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableSimulatorCitizensGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(simulatorPath), "getCitizens", ArrayList.class, false);
		testSetterMethodExistsInClass(Class.forName(simulatorPath), "setCitizens", ArrayList.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableSimulatorPlannedDisastersGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(simulatorPath), "getPlannedDisasters", ArrayList.class, false);
		testSetterMethodExistsInClass(Class.forName(simulatorPath), "setPlannedDisasters", ArrayList.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableSimulatorWorldGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(simulatorPath), "getWorld", Object[][].class, false);
		testSetterMethodExistsInClass(Class.forName(simulatorPath), "setWorld", Object[][].class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAddressXGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(addressPath), "getX", int.class, true);
		testSetterMethodExistsInClass(Class.forName(addressPath), "setX", int.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAddressYGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(addressPath), "getY", int.class, true);
		testSetterMethodExistsInClass(Class.forName(addressPath), "setY", int.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingStructuralIntegrityGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(residentialBuildingPath), "getStructuralIntegrity", int.class, true);
		testSetterMethodExistsInClass(Class.forName(residentialBuildingPath), "setStructuralIntegrity", int.class, true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingFireDamageGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(residentialBuildingPath), "getFireDamage", int.class, true);
		testSetterMethodExistsInClass(Class.forName(residentialBuildingPath), "setFireDamage", int.class, true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingFoundationDamageGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(residentialBuildingPath), "getFoundationDamage", int.class, true);
		testSetterMethodExistsInClass(Class.forName(residentialBuildingPath), "setFoundationDamage", int.class, true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingOccupantsGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(residentialBuildingPath), "getOccupants", ArrayList.class, true);
		testSetterMethodExistsInClass(Class.forName(residentialBuildingPath), "setOccupants", ArrayList.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenStateGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(citizenPath), "getState", Class.forName(citizenStatePath), true);
		testSetterMethodExistsInClass(Class.forName(citizenPath), "setState", Class.forName(citizenStatePath), true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenDisasterGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(citizenPath), "getDisaster", Class.forName(disasterPath), true);
		testSetterMethodExistsInClass(Class.forName(citizenPath), "setDisaster", Class.forName(disasterPath), false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenNationalIDGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(citizenPath), "getNationalID", String.class, true);
		testSetterMethodExistsInClass(Class.forName(citizenPath), "setNationalID", String.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenAgeGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(citizenPath), "getAge", int.class, true);
		testSetterMethodExistsInClass(Class.forName(citizenPath), "setAge", int.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenBloodLossGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(citizenPath), "getBloodLoss", int.class, true);
		testSetterMethodExistsInClass(Class.forName(citizenPath), "setBloodLoss", int.class, true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenToxicityGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(citizenPath), "getToxicity", int.class, true);
		testSetterMethodExistsInClass(Class.forName(citizenPath), "setToxicity", int.class, true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableUnitUnitIDGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(unitPath), "getUnitID", String.class, true);
		testSetterMethodExistsInClass(Class.forName(unitPath), "setUnitID", String.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableUnitStateGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(unitPath), "getState", Class.forName(unitStatePath), true);
		testSetterMethodExistsInClass(Class.forName(unitPath), "setState", Class.forName(unitStatePath), true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableUnitTargetGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(unitPath), "getTarget", Class.forName(rescuablePath), true);
		testSetterMethodExistsInClass(Class.forName(unitPath), "setTarget", Class.forName(rescuablePath), false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableUnitDistanceToTargetGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(unitPath), "getDistanceToTarget", int.class, false);
		testSetterMethodExistsInClass(Class.forName(unitPath), "setDistanceToTarget", int.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariablePoliceUnitPassengersGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(policeUnitPath), "getPassengers", ArrayList.class, false);
		testSetterMethodExistsInClass(Class.forName(policeUnitPath), "setPassengers", ArrayList.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariablePoliceUnitMaxCapacityGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(policeUnitPath), "getMaxCapacity", int.class, true);
		testSetterMethodExistsInClass(Class.forName(policeUnitPath), "setMaxCapacity", int.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableMedicalUnitHealingAmountGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(medicalUnitPath), "getHealingAmount", int.class, false);
		testSetterMethodExistsInClass(Class.forName(medicalUnitPath), "setHealingAmount", int.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableMedicalUnitTreatmentAmountGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(medicalUnitPath), "getTreatmentAmount", int.class, false);
		testSetterMethodExistsInClass(Class.forName(medicalUnitPath), "setTreatmentAmount", int.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDisasterTargetGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(disasterPath), "getTarget", Class.forName(rescuablePath), true);
		testSetterMethodExistsInClass(Class.forName(disasterPath), "setTarget", Class.forName(rescuablePath), false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDisasterActiveGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(disasterPath), "isActive", boolean.class, true);
		testSetterMethodExistsInClass(Class.forName(disasterPath), "setActive", boolean.class, true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCommandCenterVisibleBuildingsGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(commandCenterPath), "getVisibleBuildings", ArrayList.class, false);
		testSetterMethodExistsInClass(Class.forName(commandCenterPath), "setVisibleBuildings", ArrayList.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCommandCenterVisibleCitizensGetterAndSetter() throws Exception 
	{
		testGetterMethodExistsInClass(Class.forName(commandCenterPath), "getVisibleCitizens", ArrayList.class, false);
		testSetterMethodExistsInClass(Class.forName(commandCenterPath), "setVisibleCitizens", ArrayList.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAddressXGetterLogic1() throws Exception 
	{
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		testGetterLogic(address1, "x", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAddressXGetterLogic2() throws Exception 
	{
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		testGetterLogic(address2, "x", 8);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAddressYGetterLogic1() throws Exception 
	{
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		testGetterLogic(address1, "y", 9);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAddressYGetterLogic2() throws Exception 
	{
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		testGetterLogic(address2, "y", 9);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenStateGetterLogic1() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		String String0 = "fxyj";
		int int1 = 1;
		Object citizen1 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address0, String7, String0, int1);
		testGetterLogic(citizen1, "state",  Enum.valueOf((Class<Enum>) Class.forName(citizenStatePath), "SAFE"));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenStateGetterLogic2() throws Exception 
	{
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String String4 = "qwnprn";
		String String3 = "r";
		int int3 = 3;
		Object citizen2 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address2, String4, String3, int3);
		testGetterLogic(citizen2, "state",  Enum.valueOf((Class<Enum>) Class.forName(citizenStatePath), "SAFE"));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenNameGetterLogic1() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		String String0 = "fxyj";
		int int1 = 1;
		Object citizen1 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address0, String7, String0, int1);
		testGetterLogic(citizen1, "name", String0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenNameGetterLogic2() throws Exception 
	{
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String String4 = "qwnprn";
		String String3 = "r";
		int int3 = 3;
		Object citizen2 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address2, String4, String3, int3);
		testGetterLogic(citizen2, "name", String3);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenNationalIDGetterLogic1() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		String String0 = "fxyj";
		int int1 = 1;
		Object citizen1 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address0, String7, String0, int1);
		testGetterLogic(citizen1, "nationalID", String7);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenNationalIDGetterLogic2() throws Exception 
	{
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String String4 = "qwnprn";
		String String3 = "r";
		int int3 = 3;
		Object citizen2 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address2, String4, String3, int3);
		testGetterLogic(citizen2, "nationalID", String4);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenAgeGetterLogic1() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		String String0 = "fxyj";
		int int1 = 1;
		Object citizen1 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address0, String7, String0, int1);
		testGetterLogic(citizen1, "age", 1);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenAgeGetterLogic2() throws Exception 
	{
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String String4 = "qwnprn";
		String String3 = "r";
		int int3 = 3;
		Object citizen2 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address2, String4, String3, int3);
		testGetterLogic(citizen2, "age", 3);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenHpGetterLogic1() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		String String0 = "fxyj";
		int int1 = 1;
		Object citizen1 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address0, String7, String0, int1);
		testGetterLogic(citizen1, "hp", 100);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenHpGetterLogic2() throws Exception 
	{
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String String4 = "qwnprn";
		String String3 = "r";
		int int3 = 3;
		Object citizen2 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address2, String4, String3, int3);
		testGetterLogic(citizen2, "hp", 100);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenBloodLossGetterLogic1() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		String String0 = "fxyj";
		int int1 = 1;
		Object citizen1 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address0, String7, String0, int1);
		testGetterLogic(citizen1, "bloodLoss", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenBloodLossGetterLogic2() throws Exception 
	{
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String String4 = "qwnprn";
		String String3 = "r";
		int int3 = 3;
		Object citizen2 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address2, String4, String3, int3);
		testGetterLogic(citizen2, "bloodLoss", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenToxicityGetterLogic1() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		String String0 = "fxyj";
		int int1 = 1;
		Object citizen1 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address0, String7, String0, int1);
		testGetterLogic(citizen1, "toxicity", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenToxicityGetterLogic2() throws Exception 
	{
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String String4 = "qwnprn";
		String String3 = "r";
		int int3 = 3;
		Object citizen2 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address2, String4, String3, int3);
		testGetterLogic(citizen2, "toxicity", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenLocationGetterLogic1() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		String String0 = "fxyj";
		int int1 = 1;
		Object citizen1 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address0, String7, String0, int1);
		testGetterLogic(citizen1, "location", address0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenLocationGetterLogic2() throws Exception 
	{
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String String4 = "qwnprn";
		String String3 = "r";
		int int3 = 3;
		Object citizen2 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address2, String4, String3, int3);
		testGetterLogic(citizen2, "location", address2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingLocationGetterLogic1() throws Exception 
	{
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		testGetterLogic(residentialBuilding1, "location", address2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingLocationGetterLogic2() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		testGetterLogic(residentialBuilding2, "location", address0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingStructuralIntegrityGetterLogic1() throws Exception 
	{
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		testGetterLogic(residentialBuilding1, "structuralIntegrity", 100);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingStructuralIntegrityGetterLogic2() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		testGetterLogic(residentialBuilding2, "structuralIntegrity", 100);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingFireDamageGetterLogic1() throws Exception 
	{
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		testGetterLogic(residentialBuilding1, "fireDamage", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingFireDamageGetterLogic2() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		testGetterLogic(residentialBuilding2, "fireDamage", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingGasLevelGetterLogic1() throws Exception 
	{
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		testGetterLogic(residentialBuilding1, "gasLevel", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingGasLevelGetterLogic2() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		testGetterLogic(residentialBuilding2, "gasLevel", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingFoundationDamageGetterLogic1() throws Exception 
	{
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		testGetterLogic(residentialBuilding1, "foundationDamage", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingFoundationDamageGetterLogic2() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		testGetterLogic(residentialBuilding2, "foundationDamage", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorMaxCapacityGetterLogic1() throws Exception 
	{
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int4 = 4;
		int int1 = 1;
		Object evacuator1 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String5, address0, int4, int1);
		testGetterLogic(evacuator1, "maxCapacity", 1);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorMaxCapacityGetterLogic2() throws Exception 
	{
		String String0 = "fxyj";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int4 = 4;
		Object evacuator2 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String0, address1, int9, int4);
		testGetterLogic(evacuator2, "maxCapacity", 4);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorDistanceToBaseGetterLogic1() throws Exception 
	{
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int4 = 4;
		int int1 = 1;
		Object evacuator1 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String5, address0, int4, int1);
		testGetterLogic(evacuator1, "distanceToBase", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorDistanceToBaseGetterLogic2() throws Exception 
	{
		String String0 = "fxyj";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int4 = 4;
		Object evacuator2 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String0, address1, int9, int4);
		testGetterLogic(evacuator2, "distanceToBase", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorUnitIDGetterLogic1() throws Exception 
	{
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int4 = 4;
		int int1 = 1;
		Object evacuator1 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String5, address0, int4, int1);
		testGetterLogic(evacuator1, "unitID", String5);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorUnitIDGetterLogic2() throws Exception 
	{
		String String0 = "fxyj";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int4 = 4;
		Object evacuator2 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String0, address1, int9, int4);
		testGetterLogic(evacuator2, "unitID", String0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorStateGetterLogic1() throws Exception 
	{
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int4 = 4;
		int int1 = 1;
		Object evacuator1 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String5, address0, int4, int1);
		testGetterLogic(evacuator1, "state",  Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorStateGetterLogic2() throws Exception 
	{
		String String0 = "fxyj";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int4 = 4;
		Object evacuator2 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String0, address1, int9, int4);
		testGetterLogic(evacuator2, "state",  Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorLocationGetterLogic1() throws Exception 
	{
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int4 = 4;
		int int1 = 1;
		Object evacuator1 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String5, address0, int4, int1);
		testGetterLogic(evacuator1, "location", address0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorLocationGetterLogic2() throws Exception 
	{
		String String0 = "fxyj";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int4 = 4;
		Object evacuator2 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String0, address1, int9, int4);
		testGetterLogic(evacuator2, "location", address1);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorStepsPerCycleGetterLogic1() throws Exception 
	{
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int4 = 4;
		int int1 = 1;
		Object evacuator1 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String5, address0, int4, int1);
		testGetterLogic(evacuator1, "stepsPerCycle", 4);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorStepsPerCycleGetterLogic2() throws Exception 
	{
		String String0 = "fxyj";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int4 = 4;
		Object evacuator2 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String0, address1, int9, int4);
		testGetterLogic(evacuator2, "stepsPerCycle", 9);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTruckUnitIDGetterLogic1() throws Exception 
	{
		String String1 = "otbl";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int2 = 2;
		Object fireTruck1 = Class.forName(fireTruckPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String1, address1, int2);
		testGetterLogic(fireTruck1, "unitID", String1);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTruckUnitIDGetterLogic2() throws Exception 
	{
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int9 = 9;
		Object fireTruck2 = Class.forName(fireTruckPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String5, address0, int9);
		testGetterLogic(fireTruck2, "unitID", String5);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTruckStateGetterLogic1() throws Exception 
	{
		String String1 = "otbl";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int2 = 2;
		Object fireTruck1 = Class.forName(fireTruckPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String1, address1, int2);
		testGetterLogic(fireTruck1, "state",  Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTruckStateGetterLogic2() throws Exception 
	{
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int9 = 9;
		Object fireTruck2 = Class.forName(fireTruckPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String5, address0, int9);
		testGetterLogic(fireTruck2, "state",  Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTruckLocationGetterLogic1() throws Exception 
	{
		String String1 = "otbl";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int2 = 2;
		Object fireTruck1 = Class.forName(fireTruckPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String1, address1, int2);
		testGetterLogic(fireTruck1, "location", address1);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTruckLocationGetterLogic2() throws Exception 
	{
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int9 = 9;
		Object fireTruck2 = Class.forName(fireTruckPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String5, address0, int9);
		testGetterLogic(fireTruck2, "location", address0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTruckStepsPerCycleGetterLogic1() throws Exception 
	{
		String String1 = "otbl";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int2 = 2;
		Object fireTruck1 = Class.forName(fireTruckPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String1, address1, int2);
		testGetterLogic(fireTruck1, "stepsPerCycle", 2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTruckStepsPerCycleGetterLogic2() throws Exception 
	{
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int9 = 9;
		Object fireTruck2 = Class.forName(fireTruckPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String5, address0, int9);
		testGetterLogic(fireTruck2, "stepsPerCycle", 9);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasControlUnitUnitIDGetterLogic1() throws Exception 
	{
		String String7 = "izw";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int3 = 3;
		Object gasControlUnit1 = Class.forName(gasControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String7, address2, int3);
		testGetterLogic(gasControlUnit1, "unitID", String7);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasControlUnitUnitIDGetterLogic2() throws Exception 
	{
		String String3 = "r";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int0 = 0;
		Object gasControlUnit2 = Class.forName(gasControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String3, address2, int0);
		testGetterLogic(gasControlUnit2, "unitID", String3);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasControlUnitStateGetterLogic1() throws Exception 
	{
		String String7 = "izw";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int3 = 3;
		Object gasControlUnit1 = Class.forName(gasControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String7, address2, int3);
		testGetterLogic(gasControlUnit1, "state",  Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasControlUnitStateGetterLogic2() throws Exception 
	{
		String String3 = "r";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int0 = 0;
		Object gasControlUnit2 = Class.forName(gasControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String3, address2, int0);
		testGetterLogic(gasControlUnit2, "state",  Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasControlUnitLocationGetterLogic1() throws Exception 
	{
		String String7 = "izw";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int3 = 3;
		Object gasControlUnit1 = Class.forName(gasControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String7, address2, int3);
		testGetterLogic(gasControlUnit1, "location", address2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasControlUnitLocationGetterLogic2() throws Exception 
	{
		String String3 = "r";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int0 = 0;
		Object gasControlUnit2 = Class.forName(gasControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String3, address2, int0);
		testGetterLogic(gasControlUnit2, "location", address2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasControlUnitStepsPerCycleGetterLogic1() throws Exception 
	{
		String String7 = "izw";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int3 = 3;
		Object gasControlUnit1 = Class.forName(gasControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String7, address2, int3);
		testGetterLogic(gasControlUnit1, "stepsPerCycle", 3);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasControlUnitStepsPerCycleGetterLogic2() throws Exception 
	{
		String String3 = "r";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int0 = 0;
		Object gasControlUnit2 = Class.forName(gasControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String3, address2, int0);
		testGetterLogic(gasControlUnit2, "stepsPerCycle", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAmbulanceUnitIDGetterLogic1() throws Exception 
	{
		String String8 = "ssxckqph";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int7 = 7;
		Object ambulance1 = Class.forName(ambulancePath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String8, address2, int7);
		testGetterLogic(ambulance1, "unitID", String8);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAmbulanceUnitIDGetterLogic2() throws Exception 
	{
		String String0 = "fxyj";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object ambulance2 = Class.forName(ambulancePath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String0, address2, int9);
		testGetterLogic(ambulance2, "unitID", String0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAmbulanceStateGetterLogic1() throws Exception 
	{
		String String8 = "ssxckqph";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int7 = 7;
		Object ambulance1 = Class.forName(ambulancePath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String8, address2, int7);
		testGetterLogic(ambulance1, "state",  Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAmbulanceStateGetterLogic2() throws Exception 
	{
		String String0 = "fxyj";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object ambulance2 = Class.forName(ambulancePath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String0, address2, int9);
		testGetterLogic(ambulance2, "state",  Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAmbulanceLocationGetterLogic1() throws Exception 
	{
		String String8 = "ssxckqph";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int7 = 7;
		Object ambulance1 = Class.forName(ambulancePath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String8, address2, int7);
		testGetterLogic(ambulance1, "location", address2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAmbulanceLocationGetterLogic2() throws Exception 
	{
		String String0 = "fxyj";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object ambulance2 = Class.forName(ambulancePath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String0, address2, int9);
		testGetterLogic(ambulance2, "location", address2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAmbulanceStepsPerCycleGetterLogic1() throws Exception 
	{
		String String8 = "ssxckqph";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int7 = 7;
		Object ambulance1 = Class.forName(ambulancePath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String8, address2, int7);
		testGetterLogic(ambulance1, "stepsPerCycle", 7);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAmbulanceStepsPerCycleGetterLogic2() throws Exception 
	{
		String String0 = "fxyj";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object ambulance2 = Class.forName(ambulancePath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String0, address2, int9);
		testGetterLogic(ambulance2, "stepsPerCycle", 9);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDiseaseControlUnitUnitIDGetterLogic1() throws Exception 
	{
		String String4 = "qwnprn";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int6 = 6;
		Object diseaseControlUnit1 = Class.forName(diseaseControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String4, address0, int6);
		testGetterLogic(diseaseControlUnit1, "unitID", String4);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDiseaseControlUnitUnitIDGetterLogic2() throws Exception 
	{
		String String2 = "sshlnqpq";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int3 = 3;
		Object diseaseControlUnit2 = Class.forName(diseaseControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String2, address1, int3);
		testGetterLogic(diseaseControlUnit2, "unitID", String2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDiseaseControlUnitStateGetterLogic1() throws Exception 
	{
		String String4 = "qwnprn";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int6 = 6;
		Object diseaseControlUnit1 = Class.forName(diseaseControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String4, address0, int6);
		testGetterLogic(diseaseControlUnit1, "state",  Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDiseaseControlUnitStateGetterLogic2() throws Exception 
	{
		String String2 = "sshlnqpq";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int3 = 3;
		Object diseaseControlUnit2 = Class.forName(diseaseControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String2, address1, int3);
		testGetterLogic(diseaseControlUnit2, "state",  Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDiseaseControlUnitLocationGetterLogic1() throws Exception 
	{
		String String4 = "qwnprn";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int6 = 6;
		Object diseaseControlUnit1 = Class.forName(diseaseControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String4, address0, int6);
		testGetterLogic(diseaseControlUnit1, "location", address0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDiseaseControlUnitLocationGetterLogic2() throws Exception 
	{
		String String2 = "sshlnqpq";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int3 = 3;
		Object diseaseControlUnit2 = Class.forName(diseaseControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String2, address1, int3);
		testGetterLogic(diseaseControlUnit2, "location", address1);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDiseaseControlUnitStepsPerCycleGetterLogic1() throws Exception 
	{
		String String4 = "qwnprn";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int6 = 6;
		Object diseaseControlUnit1 = Class.forName(diseaseControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String4, address0, int6);
		testGetterLogic(diseaseControlUnit1, "stepsPerCycle", 6);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDiseaseControlUnitStepsPerCycleGetterLogic2() throws Exception 
	{
		String String2 = "sshlnqpq";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int3 = 3;
		Object diseaseControlUnit2 = Class.forName(diseaseControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String2, address1, int3);
		testGetterLogic(diseaseControlUnit2, "stepsPerCycle", 3);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCollapseStartCycleGetterLogic1() throws Exception 
	{
		int int2 = 2;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object collapse1 = Class.forName(collapsePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int2, residentialBuilding2);
		testGetterLogic(collapse1, "startCycle", 2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCollapseStartCycleGetterLogic2() throws Exception 
	{
		int int0 = 0;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object collapse2 = Class.forName(collapsePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int0, residentialBuilding2);
		testGetterLogic(collapse2, "startCycle", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCollapseTargetGetterLogic1() throws Exception 
	{
		int int2 = 2;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object collapse1 = Class.forName(collapsePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int2, residentialBuilding2);
		testGetterLogic(collapse1, "target", residentialBuilding2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCollapseTargetGetterLogic2() throws Exception 
	{
		int int0 = 0;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object collapse2 = Class.forName(collapsePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int0, residentialBuilding2);
		testGetterLogic(collapse2, "target", residentialBuilding2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCollapseActiveGetterLogic1() throws Exception 
	{
		int int2 = 2;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object collapse1 = Class.forName(collapsePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int2, residentialBuilding2);
		testGetterLogic(collapse1, "active", false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCollapseActiveGetterLogic2() throws Exception 
	{
		int int0 = 0;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object collapse2 = Class.forName(collapsePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int0, residentialBuilding2);
		testGetterLogic(collapse2, "active", false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireStartCycleGetterLogic1() throws Exception 
	{
		int int6 = 6;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		Object fire1 = Class.forName(firePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int6, residentialBuilding1);
		testGetterLogic(fire1, "startCycle", 6);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireStartCycleGetterLogic2() throws Exception 
	{
		int int2 = 2;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		Object fire2 = Class.forName(firePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int2, residentialBuilding1);
		testGetterLogic(fire2, "startCycle", 2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTargetGetterLogic1() throws Exception 
	{
		int int6 = 6;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		Object fire1 = Class.forName(firePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int6, residentialBuilding1);
		testGetterLogic(fire1, "target", residentialBuilding1);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTargetGetterLogic2() throws Exception 
	{
		int int2 = 2;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		Object fire2 = Class.forName(firePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int2, residentialBuilding1);
		testGetterLogic(fire2, "target", residentialBuilding1);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireActiveGetterLogic1() throws Exception 
	{
		int int6 = 6;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		Object fire1 = Class.forName(firePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int6, residentialBuilding1);
		testGetterLogic(fire1, "active", false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireActiveGetterLogic2() throws Exception 
	{
		int int2 = 2;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		Object fire2 = Class.forName(firePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int2, residentialBuilding1);
		testGetterLogic(fire2, "active", false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasLeakStartCycleGetterLogic1() throws Exception 
	{
		int int7 = 7;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object gasLeak1 = Class.forName(gasLeakPath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int7, residentialBuilding2);
		testGetterLogic(gasLeak1, "startCycle", 7);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasLeakStartCycleGetterLogic2() throws Exception 
	{
		int int6 = 6;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object gasLeak2 = Class.forName(gasLeakPath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int6, residentialBuilding0);
		testGetterLogic(gasLeak2, "startCycle", 6);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasLeakTargetGetterLogic1() throws Exception 
	{
		int int7 = 7;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object gasLeak1 = Class.forName(gasLeakPath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int7, residentialBuilding2);
		testGetterLogic(gasLeak1, "target", residentialBuilding2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasLeakTargetGetterLogic2() throws Exception 
	{
		int int6 = 6;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object gasLeak2 = Class.forName(gasLeakPath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int6, residentialBuilding0);
		testGetterLogic(gasLeak2, "target", residentialBuilding0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasLeakActiveGetterLogic1() throws Exception 
	{
		int int7 = 7;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object gasLeak1 = Class.forName(gasLeakPath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int7, residentialBuilding2);
		testGetterLogic(gasLeak1, "active", false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasLeakActiveGetterLogic2() throws Exception 
	{
		int int6 = 6;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object gasLeak2 = Class.forName(gasLeakPath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int6, residentialBuilding0);
		testGetterLogic(gasLeak2, "active", false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInfectionStartCycleGetterLogic1() throws Exception 
	{
		int int6 = 6;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String String4 = "qwnprn";
		String String3 = "r";
		int int3 = 3;
		Object citizen2 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address2, String4, String3, int3);
		Object infection1 = Class.forName(infectionPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int6, citizen2);
		testGetterLogic(infection1, "startCycle", 6);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInfectionStartCycleGetterLogic2() throws Exception 
	{
		int int9 = 9;
		int int0 = 0;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address1, String7, String7, int9);
		Object infection2 = Class.forName(infectionPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int9, citizen0);
		testGetterLogic(infection2, "startCycle", 9);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInfectionTargetGetterLogic1() throws Exception 
	{
		int int6 = 6;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String String4 = "qwnprn";
		String String3 = "r";
		int int3 = 3;
		Object citizen2 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address2, String4, String3, int3);
		Object infection1 = Class.forName(infectionPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int6, citizen2);
		testGetterLogic(infection1, "target", citizen2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInfectionTargetGetterLogic2() throws Exception 
	{
		int int9 = 9;
		int int0 = 0;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address1, String7, String7, int9);
		Object infection2 = Class.forName(infectionPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int9, citizen0);
		testGetterLogic(infection2, "target", citizen0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInfectionActiveGetterLogic1() throws Exception 
	{
		int int6 = 6;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String String4 = "qwnprn";
		String String3 = "r";
		int int3 = 3;
		Object citizen2 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address2, String4, String3, int3);
		Object infection1 = Class.forName(infectionPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int6, citizen2);
		testGetterLogic(infection1, "active", false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInfectionActiveGetterLogic2() throws Exception 
	{
		int int9 = 9;
		int int0 = 0;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address1, String7, String7, int9);
		Object infection2 = Class.forName(infectionPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int9, citizen0);
		testGetterLogic(infection2, "active", false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInjuryStartCycleGetterLogic1() throws Exception 
	{
		int int5 = 5;
		int int8 = 8;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		String String0 = "fxyj";
		int int1 = 1;
		Object citizen1 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address0, String7, String0, int1);
		Object injury1 = Class.forName(injuryPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int5, citizen1);
		testGetterLogic(injury1, "startCycle", 5);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInjuryStartCycleGetterLogic2() throws Exception 
	{
		int int1 = 1;
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address1, String7, String7, int9);
		Object injury2 = Class.forName(injuryPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int1, citizen0);
		testGetterLogic(injury2, "startCycle", 1);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInjuryTargetGetterLogic1() throws Exception 
	{
		int int5 = 5;
		int int8 = 8;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		String String0 = "fxyj";
		int int1 = 1;
		Object citizen1 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address0, String7, String0, int1);
		Object injury1 = Class.forName(injuryPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int5, citizen1);
		testGetterLogic(injury1, "target", citizen1);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInjuryTargetGetterLogic2() throws Exception 
	{
		int int1 = 1;
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address1, String7, String7, int9);
		Object injury2 = Class.forName(injuryPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int1, citizen0);
		testGetterLogic(injury2, "target", citizen0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInjuryActiveGetterLogic1() throws Exception 
	{
		int int5 = 5;
		int int8 = 8;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		String String0 = "fxyj";
		int int1 = 1;
		Object citizen1 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address0, String7, String0, int1);
		Object injury1 = Class.forName(injuryPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int5, citizen1);
		testGetterLogic(injury1, "active", false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInjuryActiveGetterLogic2() throws Exception 
	{
		int int1 = 1;
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address1, String7, String7, int9);
		Object injury2 = Class.forName(injuryPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int1, citizen0);
		testGetterLogic(injury2, "active", false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenStateSetterLogic1() throws Exception 
	{
		Object citizenState1 = Enum.valueOf((Class<Enum>) Class.forName(citizenStatePath), "RESCUED");
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		String String0 = "fxyj";
		int int1 = 1;
		Object citizen1 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address0, String7, String0, int1);
		testSetterLogic(citizen1, "state", citizenState1, Class.forName(citizenStatePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenStateSetterLogic2() throws Exception 
	{
		Object citizenState1 = Enum.valueOf((Class<Enum>) Class.forName(citizenStatePath), "RESCUED");
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String String4 = "qwnprn";
		String String3 = "r";
		int int3 = 3;
		Object citizen2 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address2, String4, String3, int3);
		testSetterLogic(citizen2, "state", citizenState1, Class.forName(citizenStatePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenHpSetterLogic1() throws Exception 
	{
		int int1 = 1;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		String String0 = "fxyj";
		Object citizen1 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address0, String7, String0, int1);
		testSetterLogic(citizen1, "hp", int1, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenHpSetterLogic2() throws Exception 
	{
		int int1 = 1;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String String4 = "qwnprn";
		String String3 = "r";
		int int3 = 3;
		Object citizen2 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address2, String4, String3, int3);
		testSetterLogic(citizen2, "hp", int1, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenBloodLossSetterLogic1() throws Exception 
	{
		int int4 = 4;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		String String0 = "fxyj";
		int int1 = 1;
		Object citizen1 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address0, String7, String0, int1);
		testSetterLogic(citizen1, "bloodLoss", int4, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenBloodLossSetterLogic2() throws Exception 
	{
		int int4 = 4;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String String4 = "qwnprn";
		String String3 = "r";
		int int3 = 3;
		Object citizen2 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address2, String4, String3, int3);
		testSetterLogic(citizen2, "bloodLoss", int4, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenToxicitySetterLogic1() throws Exception 
	{
		int int9 = 9;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		String String0 = "fxyj";
		int int1 = 1;
		Object citizen1 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address0, String7, String0, int1);
		testSetterLogic(citizen1, "toxicity", int9, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenToxicitySetterLogic2() throws Exception 
	{
		int int9 = 9;
		int int8 = 8;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String String4 = "qwnprn";
		String String3 = "r";
		int int3 = 3;
		Object citizen2 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address2, String4, String3, int3);
		testSetterLogic(citizen2, "toxicity", int9, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenLocationSetterLogic1() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		String String0 = "fxyj";
		int int1 = 1;
		Object citizen1 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address0, String7, String0, int1);
		testSetterLogic(citizen1, "location", address0, Class.forName(addressPath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenLocationSetterLogic2() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String String4 = "qwnprn";
		String String3 = "r";
		int int3 = 3;
		Object citizen2 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address2, String4, String3, int3);
		testSetterLogic(citizen2, "location", address0, Class.forName(addressPath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingStructuralIntegritySetterLogic1() throws Exception 
	{
		int int6 = 6;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		testSetterLogic(residentialBuilding1, "structuralIntegrity", int6, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingStructuralIntegritySetterLogic2() throws Exception 
	{
		int int6 = 6;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		testSetterLogic(residentialBuilding2, "structuralIntegrity", int6, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingFireDamageSetterLogic1() throws Exception 
	{
		int int6 = 6;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		testSetterLogic(residentialBuilding1, "fireDamage", int6, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingFireDamageSetterLogic2() throws Exception 
	{
		int int6 = 6;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		testSetterLogic(residentialBuilding2, "fireDamage", int6, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingGasLevelSetterLogic1() throws Exception 
	{
		int int1 = 1;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		testSetterLogic(residentialBuilding1, "gasLevel", int1, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingGasLevelSetterLogic2() throws Exception 
	{
		int int1 = 1;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		testSetterLogic(residentialBuilding2, "gasLevel", int1, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingFoundationDamageSetterLogic1() throws Exception 
	{
		int int4 = 4;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		testSetterLogic(residentialBuilding1, "foundationDamage", int4, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingFoundationDamageSetterLogic2() throws Exception 
	{
		int int4 = 4;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		testSetterLogic(residentialBuilding2, "foundationDamage", int4, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorDistanceToBaseSetterLogic1() throws Exception 
	{
		int int9 = 9;
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int4 = 4;
		int int1 = 1;
		Object evacuator1 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String5, address0, int4, int1);
		testSetterLogic(evacuator1, "distanceToBase", int9, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorDistanceToBaseSetterLogic2() throws Exception 
	{
		int int9 = 9;
		String String0 = "fxyj";
		int int0 = 0;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int4 = 4;
		Object evacuator2 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String0, address1, int9, int4);
		testSetterLogic(evacuator2, "distanceToBase", int9, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorStateSetterLogic1() throws Exception 
	{
		Object unitState1 = Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "RESPONDING");
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int4 = 4;
		int int1 = 1;
		Object evacuator1 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String5, address0, int4, int1);
		testSetterLogic(evacuator1, "state", unitState1, Class.forName(unitStatePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorStateSetterLogic2() throws Exception 
	{
		Object unitState1 = Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "RESPONDING");
		String String0 = "fxyj";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int4 = 4;
		Object evacuator2 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String0, address1, int9, int4);
		testSetterLogic(evacuator2, "state", unitState1, Class.forName(unitStatePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorLocationSetterLogic1() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String5 = "bc";
		int int4 = 4;
		int int1 = 1;
		Object evacuator1 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String5, address0, int4, int1);
		testSetterLogic(evacuator1, "location", address0, Class.forName(addressPath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorLocationSetterLogic2() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String0 = "fxyj";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int4 = 4;
		Object evacuator2 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String0, address1, int9, int4);
		testSetterLogic(evacuator2, "location", address0, Class.forName(addressPath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTruckStateSetterLogic1() throws Exception 
	{
		Object unitState0 = Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE");
		String String1 = "otbl";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int2 = 2;
		Object fireTruck1 = Class.forName(fireTruckPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String1, address1, int2);
		testSetterLogic(fireTruck1, "state", unitState0, Class.forName(unitStatePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTruckStateSetterLogic2() throws Exception 
	{
		Object unitState0 = Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE");
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int9 = 9;
		Object fireTruck2 = Class.forName(fireTruckPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String5, address0, int9);
		testSetterLogic(fireTruck2, "state", unitState0, Class.forName(unitStatePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTruckLocationSetterLogic1() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String1 = "otbl";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int2 = 2;
		Object fireTruck1 = Class.forName(fireTruckPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String1, address1, int2);
		testSetterLogic(fireTruck1, "location", address0, Class.forName(addressPath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTruckLocationSetterLogic2() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String5 = "bc";
		int int9 = 9;
		Object fireTruck2 = Class.forName(fireTruckPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String5, address0, int9);
		testSetterLogic(fireTruck2, "location", address0, Class.forName(addressPath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasControlUnitStateSetterLogic1() throws Exception 
	{
		Object unitState2 = Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "TREATING");
		String String7 = "izw";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int3 = 3;
		Object gasControlUnit1 = Class.forName(gasControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String7, address2, int3);
		testSetterLogic(gasControlUnit1, "state", unitState2, Class.forName(unitStatePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasControlUnitStateSetterLogic2() throws Exception 
	{
		Object unitState2 = Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "TREATING");
		String String3 = "r";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int0 = 0;
		Object gasControlUnit2 = Class.forName(gasControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String3, address2, int0);
		testSetterLogic(gasControlUnit2, "state", unitState2, Class.forName(unitStatePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasControlUnitLocationSetterLogic1() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int3 = 3;
		Object gasControlUnit1 = Class.forName(gasControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String7, address2, int3);
		testSetterLogic(gasControlUnit1, "location", address0, Class.forName(addressPath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasControlUnitLocationSetterLogic2() throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String3 = "r";
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int0 = 0;
		Object gasControlUnit2 = Class.forName(gasControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String3, address2, int0);
		testSetterLogic(gasControlUnit2, "location", address0, Class.forName(addressPath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAmbulanceStateSetterLogic1() throws Exception 
	{
		Object unitState0 = Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE");
		String String8 = "ssxckqph";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int7 = 7;
		Object ambulance1 = Class.forName(ambulancePath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String8, address2, int7);
		testSetterLogic(ambulance1, "state", unitState0, Class.forName(unitStatePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAmbulanceStateSetterLogic2() throws Exception 
	{
		Object unitState0 = Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE");
		String String0 = "fxyj";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object ambulance2 = Class.forName(ambulancePath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String0, address2, int9);
		testSetterLogic(ambulance2, "state", unitState0, Class.forName(unitStatePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAmbulanceLocationSetterLogic1() throws Exception 
	{
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String8 = "ssxckqph";
		int int8 = 8;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int7 = 7;
		Object ambulance1 = Class.forName(ambulancePath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String8, address2, int7);
		testSetterLogic(ambulance1, "location", address1, Class.forName(addressPath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAmbulanceLocationSetterLogic2() throws Exception 
	{
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String0 = "fxyj";
		int int8 = 8;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object ambulance2 = Class.forName(ambulancePath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String0, address2, int9);
		testSetterLogic(ambulance2, "location", address1, Class.forName(addressPath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDiseaseControlUnitStateSetterLogic1() throws Exception 
	{
		Object unitState0 = Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE");
		String String4 = "qwnprn";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int6 = 6;
		Object diseaseControlUnit1 = Class.forName(diseaseControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String4, address0, int6);
		testSetterLogic(diseaseControlUnit1, "state", unitState0, Class.forName(unitStatePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDiseaseControlUnitStateSetterLogic2() throws Exception 
	{
		Object unitState0 = Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE");
		String String2 = "sshlnqpq";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int3 = 3;
		Object diseaseControlUnit2 = Class.forName(diseaseControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String2, address1, int3);
		testSetterLogic(diseaseControlUnit2, "state", unitState0, Class.forName(unitStatePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDiseaseControlUnitLocationSetterLogic1() throws Exception 
	{
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String4 = "qwnprn";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int6 = 6;
		Object diseaseControlUnit1 = Class.forName(diseaseControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String4, address0, int6);
		testSetterLogic(diseaseControlUnit1, "location", address1, Class.forName(addressPath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDiseaseControlUnitLocationSetterLogic2() throws Exception 
	{
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String2 = "sshlnqpq";
		int int3 = 3;
		Object diseaseControlUnit2 = Class.forName(diseaseControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String2, address1, int3);
		testSetterLogic(diseaseControlUnit2, "location", address1, Class.forName(addressPath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCollapseActiveSetterLogic1() throws Exception 
	{
		boolean b1 = true;
		int int2 = 2;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object collapse1 = Class.forName(collapsePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int2, residentialBuilding2);
		testSetterLogic(collapse1, "active", b1, boolean.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCollapseActiveSetterLogic2() throws Exception 
	{
		boolean b1 = true;
		int int0 = 0;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object collapse2 = Class.forName(collapsePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int0, residentialBuilding2);
		testSetterLogic(collapse2, "active", b1, boolean.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireActiveSetterLogic1() throws Exception 
	{
		boolean b2 = false;
		int int6 = 6;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		Object fire1 = Class.forName(firePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int6, residentialBuilding1);
		testSetterLogic(fire1, "active", b2, boolean.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireActiveSetterLogic2() throws Exception 
	{
		boolean b2 = false;
		int int2 = 2;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		Object fire2 = Class.forName(firePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int2, residentialBuilding1);
		testSetterLogic(fire2, "active", b2, boolean.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasLeakActiveSetterLogic1() throws Exception 
	{
		boolean b2 = false;
		int int7 = 7;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object gasLeak1 = Class.forName(gasLeakPath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int7, residentialBuilding2);
		testSetterLogic(gasLeak1, "active", b2, boolean.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasLeakActiveSetterLogic2() throws Exception 
	{
		boolean b2 = false;
		int int6 = 6;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object gasLeak2 = Class.forName(gasLeakPath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int6, residentialBuilding0);
		testSetterLogic(gasLeak2, "active", b2, boolean.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInfectionActiveSetterLogic1() throws Exception 
	{
		boolean b1 = true;
		int int6 = 6;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String String4 = "qwnprn";
		String String3 = "r";
		int int3 = 3;
		Object citizen2 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address2, String4, String3, int3);
		Object infection1 = Class.forName(infectionPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int6, citizen2);
		testSetterLogic(infection1, "active", b1, boolean.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInfectionActiveSetterLogic2() throws Exception 
	{
		boolean b1 = true;
		int int9 = 9;
		int int0 = 0;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address1, String7, String7, int9);
		Object infection2 = Class.forName(infectionPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int9, citizen0);
		testSetterLogic(infection2, "active", b1, boolean.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInjuryActiveSetterLogic1() throws Exception 
	{
		boolean b2 = false;
		int int5 = 5;
		int int8 = 8;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		String String0 = "fxyj";
		int int1 = 1;
		Object citizen1 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address0, String7, String0, int1);
		Object injury1 = Class.forName(injuryPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int5, citizen1);
		testSetterLogic(injury1, "active", b2, boolean.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInjuryActiveSetterLogic2() throws Exception 
	{
		boolean b2 = false;
		int int1 = 1;
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address1, String7, String7, int9);
		Object injury2 = Class.forName(injuryPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int1, citizen0);
		testSetterLogic(injury2, "active", b2, boolean.class);
	}

	@Test(timeout = 1000)
	public void testConstructorAddressConstructor1Initialization()throws Exception 
	{
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String[] names = {"x", "y"};
		Object[] values = {0, 9};
		testConstructorInitialization(address1, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorAddressConstructor2Initialization()throws Exception 
	{
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String[] names = {"x", "y"};
		Object[] values = {8, 9};
		testConstructorInitialization(address2, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorCitizenConstructor1Initialization()throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		String String0 = "fxyj";
		int int1 = 1;
		Object citizen1 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address0, String7, String0, int1);
		String[] names = {"state", "name", "nationalID", "age", "hp", "bloodLoss", "toxicity", "location"};
		Object[] values = {Enum.valueOf((Class<Enum>) Class.forName(citizenStatePath), "SAFE"), String0, String7, 1, 100, 0, 0, address0};
		testConstructorInitialization(citizen1, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorCitizenConstructor2Initialization()throws Exception 
	{
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String String4 = "qwnprn";
		String String3 = "r";
		int int3 = 3;
		Object citizen2 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address2, String4, String3, int3);
		String[] names = {"state", "name", "nationalID", "age", "hp", "bloodLoss", "toxicity", "location"};
		Object[] values = {Enum.valueOf((Class<Enum>) Class.forName(citizenStatePath), "SAFE"), String3, String4, 3, 100, 0, 0, address2};
		testConstructorInitialization(citizen2, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorResidentialBuildingConstructor1Initialization()throws Exception 
	{
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		String[] names = {"location", "structuralIntegrity", "fireDamage", "gasLevel", "foundationDamage", "occupants",};
		Object[] values = {address2, 100, 0, 0, 0, new ArrayList<>(),};
		testConstructorInitialization(residentialBuilding1, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorResidentialBuildingConstructor2Initialization()throws Exception 
	{
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		String[] names = {"location", "structuralIntegrity", "fireDamage", "gasLevel", "foundationDamage", "occupants",};
		Object[] values = {address0, 100, 0, 0, 0, new ArrayList<>(),};
		testConstructorInitialization(residentialBuilding2, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorEvacuatorConstructor1Initialization()throws Exception 
	{
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int4 = 4;
		int int1 = 1;
		Object evacuator1 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String5, address0, int4, int1);
		String[] names = {"passengers", "maxCapacity", "distanceToBase", "unitID", "state", "location", "distanceToTarget", "stepsPerCycle"};
		Object[] values = {new ArrayList<>(), 1, 0, String5, Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"), address0, 0, 4};
		testConstructorInitialization(evacuator1, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorEvacuatorConstructor2Initialization()throws Exception 
	{
		String String0 = "fxyj";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int4 = 4;
		Object evacuator2 = Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance(String0, address1, int9, int4);
		String[] names = {"passengers", "maxCapacity", "distanceToBase", "unitID", "state", "location", "distanceToTarget", "stepsPerCycle"};
		Object[] values = {new ArrayList<>(), 4, 0, String0, Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"), address1, 0, 9};
		testConstructorInitialization(evacuator2, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorFireTruckConstructor1Initialization()throws Exception 
	{
		String String1 = "otbl";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int2 = 2;
		Object fireTruck1 = Class.forName(fireTruckPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String1, address1, int2);
		String[] names = {"unitID", "state", "location", "distanceToTarget", "stepsPerCycle"};
		Object[] values = {String1, Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"), address1, 0, 2};
		testConstructorInitialization(fireTruck1, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorFireTruckConstructor2Initialization()throws Exception 
	{
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int9 = 9;
		Object fireTruck2 = Class.forName(fireTruckPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String5, address0, int9);
		String[] names = {"unitID", "state", "location", "distanceToTarget", "stepsPerCycle"};
		Object[] values = {String5, Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"), address0, 0, 9};
		testConstructorInitialization(fireTruck2, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorGasControlUnitConstructor1Initialization()throws Exception 
	{
		String String7 = "izw";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int3 = 3;
		Object gasControlUnit1 = Class.forName(gasControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String7, address2, int3);
		String[] names = {"unitID", "state", "location", "distanceToTarget", "stepsPerCycle"};
		Object[] values = {String7, Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"), address2, 0, 3};
		testConstructorInitialization(gasControlUnit1, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorGasControlUnitConstructor2Initialization()throws Exception 
	{
		String String3 = "r";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int0 = 0;
		Object gasControlUnit2 = Class.forName(gasControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String3, address2, int0);
		String[] names = {"unitID", "state", "location", "distanceToTarget", "stepsPerCycle"};
		Object[] values = {String3, Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"), address2, 0, 0};
		testConstructorInitialization(gasControlUnit2, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorAmbulanceConstructor1Initialization()throws Exception 
	{
		String String8 = "ssxckqph";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int7 = 7;
		Object ambulance1 = Class.forName(ambulancePath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String8, address2, int7);
		String[] names = {"healingAmount", "treatmentAmount", "unitID", "state", "location", "distanceToTarget", "stepsPerCycle"};
		Object[] values = {10, 10, String8, Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"), address2, 0, 7};
		testConstructorInitialization(ambulance1, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorAmbulanceConstructor2Initialization()throws Exception 
	{
		String String0 = "fxyj";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object ambulance2 = Class.forName(ambulancePath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String0, address2, int9);
		String[] names = {"healingAmount", "treatmentAmount", "unitID", "state", "location", "distanceToTarget", "stepsPerCycle"};
		Object[] values = {10, 10, String0, Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"), address2, 0, 9};
		testConstructorInitialization(ambulance2, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorDiseaseControlUnitConstructor1Initialization()throws Exception 
	{
		String String4 = "qwnprn";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int6 = 6;
		Object diseaseControlUnit1 = Class.forName(diseaseControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String4, address0, int6);
		String[] names = {"healingAmount", "treatmentAmount", "unitID", "state", "location", "distanceToTarget", "stepsPerCycle"};
		Object[] values = {10, 10, String4, Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"), address0, 0, 6};
		testConstructorInitialization(diseaseControlUnit1, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorDiseaseControlUnitConstructor2Initialization()throws Exception 
	{
		String String2 = "sshlnqpq";
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		int int3 = 3;
		Object diseaseControlUnit2 = Class.forName(diseaseControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance(String2, address1, int3);
		String[] names = {"healingAmount", "treatmentAmount", "unitID", "state", "location", "distanceToTarget", "stepsPerCycle"};
		Object[] values = {10, 10, String2, Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"), address1, 0, 3};
		testConstructorInitialization(diseaseControlUnit2, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorCollapseConstructor1Initialization()throws Exception 
	{
		int int2 = 2;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object collapse1 = Class.forName(collapsePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int2, residentialBuilding2);
		String[] names = {"startCycle", "target", "active"};
		Object[] values = {2, residentialBuilding2, false};
		testConstructorInitialization(collapse1, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorCollapseConstructor2Initialization()throws Exception 
	{
		int int0 = 0;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object collapse2 = Class.forName(collapsePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int0, residentialBuilding2);
		String[] names = {"startCycle", "target", "active"};
		Object[] values = {0, residentialBuilding2, false};
		testConstructorInitialization(collapse2, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorFireConstructor1Initialization()throws Exception 
	{
		int int6 = 6;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		Object fire1 = Class.forName(firePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int6, residentialBuilding1);
		String[] names = {"startCycle", "target", "active"};
		Object[] values = {6, residentialBuilding1, false};
		testConstructorInitialization(fire1, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorFireConstructor2Initialization()throws Exception 
	{
		int int2 = 2;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object residentialBuilding1 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address2);
		Object fire2 = Class.forName(firePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int2, residentialBuilding1);
		String[] names = {"startCycle", "target", "active"};
		Object[] values = {2, residentialBuilding1, false};
		testConstructorInitialization(fire2, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorGasLeakConstructor1Initialization()throws Exception 
	{
		int int7 = 7;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object gasLeak1 = Class.forName(gasLeakPath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int7, residentialBuilding2);
		String[] names = {"startCycle", "target", "active"};
		Object[] values = {7, residentialBuilding2, false};
		testConstructorInitialization(gasLeak1, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorGasLeakConstructor2Initialization()throws Exception 
	{
		int int6 = 6;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(address0);
		Object gasLeak2 = Class.forName(gasLeakPath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(int6, residentialBuilding0);
		String[] names = {"startCycle", "target", "active"};
		Object[] values = {6, residentialBuilding0, false};
		testConstructorInitialization(gasLeak2, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorInfectionConstructor1Initialization()throws Exception 
	{
		int int6 = 6;
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		String String4 = "qwnprn";
		String String3 = "r";
		int int3 = 3;
		Object citizen2 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address2, String4, String3, int3);
		Object infection1 = Class.forName(infectionPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int6, citizen2);
		String[] names = {"startCycle", "target", "active"};
		Object[] values = {6, citizen2, false};
		testConstructorInitialization(infection1, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorInfectionConstructor2Initialization()throws Exception 
	{
		int int9 = 9;
		int int0 = 0;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address1, String7, String7, int9);
		Object infection2 = Class.forName(infectionPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int9, citizen0);
		String[] names = {"startCycle", "target", "active"};
		Object[] values = {9, citizen0, false};
		testConstructorInitialization(infection2, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorInjuryConstructor1Initialization()throws Exception 
	{
		int int5 = 5;
		int int8 = 8;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String7 = "izw";
		String String0 = "fxyj";
		int int1 = 1;
		Object citizen1 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address0, String7, String0, int1);
		Object injury1 = Class.forName(injuryPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int5, citizen1);
		String[] names = {"startCycle", "target", "active"};
		Object[] values = {5, citizen1, false};
		testConstructorInitialization(injury1, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorInjuryConstructor2Initialization()throws Exception 
	{
		int int1 = 1;
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(address1, String7, String7, int9);
		Object injury2 = Class.forName(injuryPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(int1, citizen0);
		String[] names = {"startCycle", "target", "active"};
		Object[] values = {1, citizen0, false};
		testConstructorInitialization(injury2, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorCommandCenterConstructor1Initialization()throws Exception 
	{
		Object commandCenter1 = Class.forName(commandCenterPath).getConstructor().newInstance();
		String[] names = {"visibleBuildings", "visibleCitizens", "emergencyUnits"};
		testConstructorInitializationNotNull(commandCenter1, names);
	}

	



	@Test(timeout = 1000)
	public void testInstanceVariableSimulatorWorldInitialization()
			throws Exception {
try{
		copyFiles();
		
		Object simulator = Class.forName(simulatorPath).getConstructor().newInstance();
		
		
		
		String[] names = {"currentCycle", "buildings", "citizens", "emergencyUnits", "plannedDisasters", "executedDisasters"};
		testConstructorInitializationNotNull(simulator, names);

		Field f = null;
		Class curr = simulator.getClass();

		try {
			f = curr.getDeclaredField("world");
		} catch (NoSuchFieldException e) {
			fail(curr.getSimpleName()
					+ " class should initialize the instance variable world");
		}
		f.setAccessible(true);
		Object world[][] = (Object[][]) f.get(simulator);
		
		if(world.length == 10)
		{
			if(world[0].length <= 0 || world[0].length > 10)
				fail(simulator.getClass().getSimpleName() + " class should initialize the instance variable \"world\" with size 10x10");
		}
		else
			fail(simulator.getClass().getSimpleName() + " class should initialize the instance variable \"world\" with size 10x10");
		
		for (int i = 0; i < world.length; i++) {
			for (int j = 0; j < world[i].length; j++) {

				Field x = null;
				Class cx = world[i][j].getClass();
				try {
					x = cx.getDeclaredField("x");
				} catch (NoSuchFieldException e) {
					fail(cx.getSimpleName()
							+ " class should initialize the instance variable x");
				}
				x.setAccessible(true);
				int fx = x.getInt(world[i][j]);

				Field y = null;
				Class cy = world[i][j].getClass();
				try {
					y = cy.getDeclaredField("y");
				} catch (NoSuchFieldException e) {
					fail(cy.getSimpleName()
							+ " class should initialize the instance variable y");
				}
				y.setAccessible(true);
				int fy = y.getInt(world[i][j]);

				if (fx != i || fy != j) {
					fail(curr.getSimpleName()
							+ " class should initialize the instance variable world correctly expected ("
							+ i + ", " + j + ") but was (" + j + ", " + i
							+ ").");
				}
			}
		}

}
catch(Exception e){
	
}finally{
	resetFiles();
}

	}

	private void testConstructorInitializationNotNull(Object createdObject,
			String[] names) throws IllegalArgumentException, IllegalAccessException {
		for (int i = 0; i < names.length; i++) 
		{
			Field f = null;
			Class curr = createdObject.getClass();
			String currName = names[i];
			while (f == null) 
			{
				if (curr == Object.class)
					fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \"" + currName + "\".");
				try 
				{
					f = curr.getDeclaredField(currName);
				} 
				catch (NoSuchFieldException e)
				{
					curr = curr.getSuperclass();
				}
			}
			f.setAccessible(true);
			if(f.get(createdObject) == null)
				fail("The constructor of the " + createdObject.getClass().getSimpleName() + " class should initialize the instance variable \"" + currName + "\" correctly.");
		}
		
	}
	


	private static void copyFiles() throws IOException {
		File sourceFileBuildings = new File("buildings.csv");
		File destinationFileBuildings = new File("buildings2.csv");
		File sourceFileCitizens = new File("citizens.csv");
		File destinationFileCitizens = new File("citizens2.csv");
		File sourceFileDisasters = new File("disasters.csv");
		File destinationFileDisasters = new File("disasters2.csv");
		File sourceFileUnits = new File("units.csv");
		File destinationFileUnits = new File("units2.csv");

		
		copyFile(sourceFileBuildings, destinationFileBuildings);
		copyFile(sourceFileCitizens, destinationFileCitizens);
		copyFile(sourceFileDisasters, destinationFileDisasters);
		copyFile(sourceFileUnits, destinationFileUnits);


	}
	
	private static void resetFiles() throws IOException {
		File sourceFileBuildings = new File("buildings2.csv");
		File destinationFileBuildings = new File("buildings.csv");
		File sourceFileCitizens = new File("citizens2.csv");
		File destinationFileCitizens = new File("citizens.csv");
		File sourceFileDisasters = new File("disasters2.csv");
		File destinationFileDisasters = new File("disasters.csv");
		File sourceFileUnits = new File("units2.csv");
		File destinationFileUnits = new File("units.csv");

		destinationFileBuildings.delete();
		destinationFileCitizens.delete();
		destinationFileDisasters.delete();
		destinationFileUnits.delete();

		destinationFileBuildings.createNewFile();
		destinationFileCitizens.createNewFile();
		destinationFileDisasters.createNewFile();
		destinationFileUnits.createNewFile();

		copyFile(sourceFileBuildings, destinationFileBuildings);
		copyFile(sourceFileCitizens, destinationFileCitizens);
		copyFile(sourceFileDisasters, destinationFileDisasters);
		copyFile(sourceFileUnits, destinationFileUnits);

		sourceFileBuildings.delete();
		sourceFileCitizens.delete();
		sourceFileDisasters.delete();
		sourceFileUnits.delete();
	}

	@SuppressWarnings("resource")
	private static void copyFile(File sourceFile, File destFile)
			throws IOException {

		if (!destFile.exists()) {
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;
		try {
			source = new RandomAccessFile(sourceFile, "rw").getChannel();
			destination = new RandomAccessFile(destFile, "rw").getChannel();

			long position = 0;
			long count = source.size();

			source.transferTo(position, count, destination);
		} finally {
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		}
	}
	
	
	@Test(timeout = 1000)
	public void testReadingBuildingsFromCSV() throws Exception 
	{
		try{
		final Field f = Class.forName(simulatorPath).getDeclaredField("buildings");
		
		f.setAccessible(true);
		
		final Field f1 = Class.forName(simulatorPath).getDeclaredField("citizens");
		
		f1.setAccessible(true);
		
		final Field f2 = Class.forName(simulatorPath).getDeclaredField("world");
		
		f2.setAccessible(true);
	
		Object s = Class.forName(simulatorPath).getConstructor().newInstance();
		
		PrintWriter buildingWriter = new PrintWriter("buildings_test.csv");
		buildingWriter.println("9,9");
		buildingWriter.println("5,5");
		buildingWriter.println("6,6");
		buildingWriter.println("3,3");
		buildingWriter.println("5,6");
		buildingWriter.println("9,3");
		buildingWriter.close();
		Object [][] testWorld = (Object[][]) f2.get(s);
		((ArrayList<Object>) f1.get(s)).clear();
		((ArrayList<Object>) f1.get(s)).add(Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(testWorld[5][5],"1","zizo",25));
		((ArrayList<Object>) f1.get(s)).add(Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(testWorld[6][6],"2","amr",30));
		((ArrayList<Object>) f1.get(s)).add(Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(testWorld[3][3],"3","karam",40));
		((ArrayList<Object>) f1.get(s)).add(Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(testWorld[5][5],"4","maisarah",45));
		((ArrayList<Object>) f1.get(s)).add(Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(testWorld[5][5],"5","mostafa",20));
		Method method = Class.forName(simulatorPath).getDeclaredMethod("loadBuildings", new Class[] { String.class });
		method.setAccessible(true);
		ArrayList<Object> testBuildings = new ArrayList<Object>();
		((ArrayList<Object>) f.get(s)).clear();
		method.invoke(s, "buildings_test.csv");
		
	
		
		
		
		testBuildings=(ArrayList<Object>) f.get(s);
		ArrayList<Object> correctBuildings = new ArrayList<Object>();
		
		
		correctBuildings.add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[9][9]));
		correctBuildings.add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[5][5]));
		correctBuildings.add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[6][6]));
		correctBuildings.add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[3][3]));
		correctBuildings.add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[5][6]));
		correctBuildings.add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[9][3]));
		
		assertEquals(
				"The loaded buildings ArrayList doesn't contain the same number of buildings given in the CSV file",
				testBuildings.size(), correctBuildings.size());
		for (int i = 0; i < testBuildings.size(); i++) {

			
			assertEquals(
					"Building  Location  is not loaded correctly from the CSV file",
					(correctBuildings.get(i)).getClass().getDeclaredMethod("getLocation").invoke(correctBuildings.get(i)),
					(testBuildings.get(i)).getClass().getDeclaredMethod("getLocation").invoke(testBuildings.get(i)));
		}	
		}
		catch(Exception e){
			
		}
		finally{
			File buildingFile = new File("buildings_test.csv");
			buildingFile.delete();
		}
		}
	
	@Test(timeout = 1000)
	public void testReadingCitizenFromCSV2() throws Exception 
	{
		try{
		final Field f = Class.forName(simulatorPath).getDeclaredField("citizens");
		
		f.setAccessible(true);
		
		final Field f1 = Class.forName(simulatorPath).getDeclaredField("buildings");
		
		f1.setAccessible(true);
		final Field f2 = Class.forName(simulatorPath).getDeclaredField("world");
		
		f2.setAccessible(true);
		Object s = Class.forName(simulatorPath).getConstructor().newInstance();
		
		PrintWriter citizensWriter = new PrintWriter("citizen_test.csv");
		citizensWriter.println("5,5,1,zizo,25");
		citizensWriter.println("6,6,2,amr,30");
		citizensWriter.println("3,3,3,karam,40");
		citizensWriter.println("5,5,4,maisarah,45");
		citizensWriter.println("5,5,5,mostafa,20");
		citizensWriter.close();
		Object [][] testWorld = (Object[][]) f2.get(s);
		((ArrayList<Object>) f1.get(s)).clear();
		((ArrayList<Object>) f1.get(s)).add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[9][9]));
		((ArrayList<Object>) f1.get(s)).add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[5][5]));
		((ArrayList<Object>) f1.get(s)).add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[6][6]));
		((ArrayList<Object>) f1.get(s)).add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[3][3]));
		((ArrayList<Object>) f1.get(s)).add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[5][6]));
		((ArrayList<Object>) f1.get(s)).add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[9][3]));
		
		Method method = Class.forName(simulatorPath).getDeclaredMethod("loadCitizens", new Class[] { String.class });
		method.setAccessible(true);
		ArrayList<Object> testCitizens = new ArrayList<Object>();
		((ArrayList<Object>) f.get(s)).clear();
		method.invoke(s, "citizen_test.csv");
		
		testCitizens=(ArrayList<Object>) f.get(s);
		
		ArrayList<Object> correctCitizens = new ArrayList<Object>();
		
		
		correctCitizens.add(Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(testWorld[5][5],"1","zizo",25));
		correctCitizens.add(Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(testWorld[6][6],"2","amr",30));
		correctCitizens.add(Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(testWorld[3][3],"3","karam",40));
		correctCitizens.add(Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(testWorld[5][5],"4","maisarah",45));
		correctCitizens.add(Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(testWorld[5][5],"5","mostafa",20));
		
		assertEquals(
				"The loaded citizen ArrayList doesn't contain the same number of citizen given in the CSV file",
				correctCitizens.size(), testCitizens.size());
		for (int i = 0; i < correctCitizens.size(); i++) {

			assertEquals(
					"Citzen  Location is not loaded correctly from the CSV file",
					(correctCitizens.get(i)).getClass().getDeclaredMethod("getLocation").invoke(correctCitizens.get(i)),
					(testCitizens.get(i)).getClass().getDeclaredMethod("getLocation").invoke(testCitizens.get(i)));
			
			assertEquals(
					"Citzen  NationalID is not loaded correctly from the CSV file",
					(correctCitizens.get(i)).getClass().getDeclaredMethod("getNationalID").invoke(correctCitizens.get(i)),
					(testCitizens.get(i)).getClass().getDeclaredMethod("getNationalID").invoke(testCitizens.get(i)));
			
			assertEquals(
					"Citzen Name is not loaded correctly from the CSV file",
					(correctCitizens.get(i)).getClass().getDeclaredMethod("getName").invoke(correctCitizens.get(i)),
					(testCitizens.get(i)).getClass().getDeclaredMethod("getName").invoke(testCitizens.get(i)));
			assertEquals(
					"Citzen Age is not loaded correctly from the CSV file",
					(correctCitizens.get(i)).getClass().getDeclaredMethod("getAge").invoke(correctCitizens.get(i)),
					(testCitizens.get(i)).getClass().getDeclaredMethod("getAge").invoke(testCitizens.get(i)));
		}
		}
		catch(Exception e){
			
		}
		finally{
			File citizenFile = new File("citizen_test.csv");
			citizenFile.delete();
		}
	}
		@Test(timeout = 1000)
		public void testReadingDisastersFromCSV() throws Exception 
		{
			try{
			final Field f = Class.forName(simulatorPath).getDeclaredField("plannedDisasters");
			
			f.setAccessible(true);
			final Field f1 = Class.forName(simulatorPath).getDeclaredField("citizens");
			
			f1.setAccessible(true);
			final Field f2 = Class.forName(simulatorPath).getDeclaredField("buildings");
			
			f2.setAccessible(true);
			final Field f3 = Class.forName(simulatorPath).getDeclaredField("world");
			
			f3.setAccessible(true);
			Object s = Class.forName(simulatorPath).getConstructor().newInstance();
			
			PrintWriter disastersWriter = new PrintWriter("disasters_test.csv");
			
			disastersWriter.println("1,INJ,3");
			disastersWriter.println("2,INF,2");
			disastersWriter.println("4,FIR,5,5");
			disastersWriter.println("5,GLK,6,6");
			
			disastersWriter.close();
			
			Object [][] testWorld = (Object[][]) f3.get(s);
			((ArrayList<Object>) f1.get(s)).clear();
			((ArrayList<Object>) f1.get(s)).add(Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(testWorld[5][5],"1","zizo",25));
			((ArrayList<Object>) f1.get(s)).add(Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(testWorld[6][6],"2","amr",30));
			((ArrayList<Object>) f1.get(s)).add(Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(testWorld[3][3],"7","karam",40));
			((ArrayList<Object>) f1.get(s)).add(Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(testWorld[5][5],"3","maisarah",45));
			((ArrayList<Object>) f1.get(s)).add(Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(testWorld[5][5],"5","mostafa",20));
			
			((ArrayList<Object>) f2.get(s)).clear();
			((ArrayList<Object>) f2.get(s)).add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[9][9]));
			((ArrayList<Object>) f2.get(s)).add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[5][5]));
			((ArrayList<Object>) f2.get(s)).add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[6][6]));
			((ArrayList<Object>) f2.get(s)).add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[3][3]));
			((ArrayList<Object>) f2.get(s)).add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[5][6]));
			((ArrayList<Object>) f2.get(s)).add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[9][3]));
			
		
			Method method = Class.forName(simulatorPath).getDeclaredMethod("loadDisasters", new Class[] { String.class });
			method.setAccessible(true);
			ArrayList<Object> testDisasters = new ArrayList<Object>();
			((ArrayList<Object>) f.get(s)).clear();
			method.invoke(s, "disasters_test.csv");
			testDisasters=(ArrayList<Object>) f.get(s);
			ArrayList<Object> correctCitizens = new ArrayList<Object>();
			
			correctCitizens.add(Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(testWorld[6][6], "2","amr",30));
			
			correctCitizens.add(Class.forName(citizenPath).getConstructor(Class.forName(addressPath), String.class, String.class, int.class).newInstance(testWorld[5][5], "3","maisarah",45));
			
			ArrayList<Object> correctBuildings = new ArrayList<Object>();
			
			correctBuildings.add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[5][5]));
			correctBuildings.add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[6][6]));
			correctBuildings.add(Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath)).newInstance(testWorld[3][3]));
			
			ArrayList<Object> correctDisasters = new ArrayList<Object>();
			correctDisasters.add(Class.forName(injuryPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(1,correctCitizens.get(1)));
			correctDisasters.add(Class.forName(infectionPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(2,correctCitizens.get(0)));
			correctDisasters.add(Class.forName(firePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(4,correctBuildings.get(0)));
			correctDisasters.add(Class.forName(gasLeakPath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(5,correctBuildings.get(1)));
			
			
			assertEquals(
					"The loaded disasters ArrayList doesn't contain the same number of disasters given in the CSV file",
					correctDisasters.size(), testDisasters.size());
		
			for (int i = 0; i < correctDisasters.size(); i++) {
				assertFalse("Disaster Target should not be null",testDisasters.get(i).getClass().getMethod("getTarget").invoke(testDisasters.get(i))==null );
				assertFalse("Disaster creation type should be done correctly, expected \""+ 
						correctDisasters.get(i).getClass().getSimpleName()+"\" but was \""+
						testDisasters.get(i).getClass().getSimpleName()+"\"."
						,correctDisasters.get(i).getClass()!=testDisasters.get(i).getClass() );
				
				assertEquals(
						"Disaster Start Cycle is not loaded correctly from the CSV file",
						(correctDisasters.get(i)).getClass().getMethod("getStartCycle").invoke(correctDisasters.get(i)),
						(testDisasters.get(i)).getClass().getMethod("getStartCycle").invoke(testDisasters.get(i)));
				
				if(Class.forName(citizenPath).isInstance(correctDisasters.get(i).getClass().getMethod("getTarget").invoke(correctDisasters.get(i)))
						&& Class.forName(citizenPath).isInstance(testDisasters.get(i).getClass().getMethod("getTarget").invoke(testDisasters.get(i)))){
					Object correctDisasterTarget = (correctDisasters.get(i).getClass().getMethod("getTarget").invoke(correctDisasters.get(i)));
					Object testDisasterTarget = (testDisasters.get(i).getClass().getMethod("getTarget").invoke(testDisasters.get(i)));
					assertEquals(
							"Disaster Target Name is not loaded correctly from the CSV file",
							(correctDisasterTarget.getClass().getMethod("getName").invoke(correctDisasterTarget)),
							(testDisasterTarget.getClass().getMethod("getName").invoke(testDisasterTarget)));
					assertEquals(
							"Disaster Target NationalID is not loaded correctly from the CSV file",
							(correctDisasterTarget.getClass().getMethod("getNationalID").invoke(correctDisasterTarget)),
							(testDisasterTarget.getClass().getMethod("getNationalID").invoke(testDisasterTarget)));
					assertEquals(
							"Disaster Target Age is not loaded correctly from the CSV file",
							(correctDisasterTarget.getClass().getMethod("getAge").invoke(correctDisasterTarget)),
							(testDisasterTarget.getClass().getMethod("getAge").invoke(testDisasterTarget)));
					assertEquals(
							"Disaster Target Location is not loaded correctly from the CSV file",
							(correctDisasterTarget.getClass().getMethod("getLocation").invoke(correctDisasterTarget)),
							(testDisasterTarget.getClass().getMethod("getLocation").invoke(testDisasterTarget)));
					
				}
				
				if(Class.forName(residentialBuildingPath).isInstance(correctDisasters.get(i).getClass().getMethod("getTarget").invoke(correctDisasters.get(i))) 
						&& Class.forName(residentialBuildingPath).isInstance(testDisasters.get(i).getClass().getMethod("getTarget").invoke(testDisasters.get(i)))){
					
					Object correctDisasterTarget = (correctDisasters.get(i).getClass().getMethod("getTarget").invoke(correctDisasters.get(i)));
					Object testDisasterTarget = (testDisasters.get(i).getClass().getMethod("getTarget").invoke(testDisasters.get(i)));
					
					assertEquals(
							"Disaster Target Location is not loaded correctly from the CSV file",
							(correctDisasterTarget.getClass().getMethod("getLocation").invoke(correctDisasterTarget)),
							(testDisasterTarget.getClass().getMethod("getLocation").invoke(testDisasterTarget)));
					
				}
				
			}
			}
			catch(Exception e){
				
			}finally{
				File disasterFile = new File("disasters_test.csv");
				disasterFile.delete();
			}
		
	}
	
		@Test(timeout = 1000)
		public void testReadingUnitsFromCSV() throws Exception 
		{
			try{
			final Field f = Class.forName(simulatorPath).getDeclaredField("emergencyUnits");
			
			f.setAccessible(true);
			final Field f2 = Class.forName(simulatorPath).getDeclaredField("world");
			
			f2.setAccessible(true);
			Object s = Class.forName(simulatorPath).getConstructor().newInstance();
			
			
			PrintWriter unitWriter = new PrintWriter("units_test.csv");
			unitWriter.println("AMB,1,2");
			unitWriter.println("DCU,2,4");
			unitWriter.println("EVC,3,6,8");
			unitWriter.println("FTK,4,8");
			unitWriter.println("GCU,9,3");
			unitWriter.close();
			
			Object [][] testWorld = (Object[][]) f2.get(s);
			Method method = Class.forName(simulatorPath).getDeclaredMethod("loadUnits", new Class[] { String.class });
			method.setAccessible(true);
			ArrayList<Object> testUnits = new ArrayList<Object>();
			((ArrayList<Object>) f.get(s)).clear();
			method.invoke(s, "units_test.csv");
			testUnits=(ArrayList<Object>) f.get(s);
			ArrayList<Object> correctUnit = new ArrayList<Object>();
			correctUnit.add(Class.forName(ambulancePath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance("1", testWorld[0][0], 2));
			correctUnit.add(Class.forName(diseaseControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance("2", testWorld[0][0], 4));
			correctUnit.add(Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance("3", testWorld[0][0], 6,8));
			correctUnit.add(Class.forName(fireTruckPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance("4", testWorld[0][0], 8));
			correctUnit.add(Class.forName(gasControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance("9", testWorld[0][0], 3));
			
			
			
			
			assertEquals(
					"The loaded units ArrayList doesn't contain the same number of disasters given in the CSV file",
					correctUnit.size(), testUnits.size());
			
			for(int i =0;i<correctUnit.size();i++){
				
				assertFalse("Unit creation type should be done correctly, expected \""+ 
						correctUnit.get(i).getClass().getSimpleName()+"\" but was \""+
						testUnits.get(i).getClass().getSimpleName()+"\"."
						,correctUnit.get(i).getClass()!=testUnits.get(i).getClass() );
				
				assertEquals(
						"unit Location is not loaded correctly from the CSV file",
						(correctUnit.get(i).getClass().getMethod("getLocation").invoke(correctUnit.get(i))),
						(testUnits.get(i).getClass().getMethod("getLocation").invoke(testUnits.get(i))));
				assertEquals(
						"unit Steps is not loaded correctly from the CSV file",
						(correctUnit.get(i).getClass().getMethod("getStepsPerCycle").invoke(correctUnit.get(i))),
						(testUnits.get(i).getClass().getMethod("getStepsPerCycle").invoke(testUnits.get(i))));
				assertEquals(
						"unit id is not loaded correctly from the CSV file",
						(correctUnit.get(i).getClass().getMethod("getUnitID").invoke(correctUnit.get(i))),
						(testUnits.get(i).getClass().getMethod("getUnitID").invoke(testUnits.get(i))));
				
			}
			}
			catch(Exception e){
				
			}finally{
				File unitsFile = new File("units_test.csv");
				unitsFile.delete();
			}
			
		}



	public void testEnumValues(Class aClass, String [] value) throws ClassNotFoundException 
	{
		for(int i=0;i<value.length;i++)
		{
			try 
			{
				Enum.valueOf((Class<Enum>) aClass, value[i]);
			} 
			catch (IllegalArgumentException e) 
			{
				fail(aClass.getSimpleName()+" enum can be " + value[i]);
			}
		}
	}

	private void testInstanceVariableIsPresent(Class aClass, String varName, boolean implementedVar) throws SecurityException 
	{
		boolean thrown = false;
		try 
		{
			aClass.getDeclaredField(varName);
		} 
		catch (NoSuchFieldException e) 
		{
			thrown = true;
		}
		if (implementedVar) 
		{
			assertFalse("There should be \"" + varName + "\" instance variable in class " + aClass.getSimpleName() + ".", thrown);
		} 
		else 
		{
			assertTrue("The instance variable \"" + varName + "\" should not be declared in class " + aClass.getSimpleName() + ".", thrown);
		}
	}
	
	private void testInstanceVariableIsPrivate(Class aClass, String varName) throws NoSuchFieldException, SecurityException 
	{
		Field f = aClass.getDeclaredField(varName);
		assertEquals("The \""+ varName + "\" instance variable in class " + aClass.getSimpleName() + " should not be accessed outside that class.", 2, f.getModifiers());
	}
	
	private void testGetterMethodExistsInClass(Class aClass, String methodName, Class returnedType, boolean writeVariable)
	{
		Method m = null;
		boolean found = true;
		try
		{
			m = aClass.getDeclaredMethod(methodName);
		} catch (Exception e)
		{
			found = false;
		}
		String varName = "";
		if (returnedType == boolean.class)
			varName = methodName.substring(2).toLowerCase();
		else
			varName = methodName.substring(3).toLowerCase();
		if (writeVariable)
		{
			assertTrue("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName() + " is a READ variable.", found);
			assertTrue("Incorrect return type for " + methodName + " method in " + aClass.getSimpleName() + " class.", m.getReturnType()
					.isAssignableFrom(returnedType));
		}
		else
		{
			assertFalse("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName() + " is not a READ variable.", found);
		}
	}
	
	private void testSetterMethodExistsInClass(Class aClass, String methodName, Class inputType, boolean writeVariable) 
	{
		Method[] methods = aClass.getDeclaredMethods();
		String varName = methodName.substring(3).toLowerCase();
		if (writeVariable) 
		{
			assertTrue("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName() + " is a WRITE variable.", containsMethodName(methods, methodName));
		} 
		else 
		{
			assertFalse("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName() + " is not a WRITE variable.", containsMethodName(methods, methodName));
			return;
		}
		Method m = null;
		boolean found = true;
		try 
		{
			m = aClass.getDeclaredMethod(methodName, inputType);
		} 
		catch (NoSuchMethodException e) 
		{
			found = false;
		}
		assertTrue(aClass.getSimpleName() + " class should have " + methodName + " method that takes one " + inputType.getSimpleName() + " parameter.", found);
		assertTrue("Incorrect return type for " + methodName + " method in " + aClass.getSimpleName() + ".", m.getReturnType().equals(Void.TYPE));
	}
	
	private static boolean containsMethodName(Method[] methods, String name) 
	{
		for (Method method : methods) 
		{
			if (method.getName().equals(name))
				return true;
		}
		return false;
	}
	
	private void testConstructorExists(Class aClass, Class[] inputs) 
	{
		boolean thrown = false;
		try 
		{
			aClass.getConstructor(inputs);
		} 
		catch (NoSuchMethodException e) 
		{
			thrown = true;
		}
		if (inputs.length > 0) 
		{
			String msg = "";
			int i = 0;
			do 
			{
				msg += inputs[i].getSimpleName() + " and ";
				i++;
			} while (i < inputs.length);
			msg = msg.substring(0, msg.length() - 4);
			assertFalse("Missing constructor with " + msg + " parameter" + (inputs.length > 1 ? "s" : "") + " in " + aClass.getSimpleName() + " class.", thrown);
		} 
		else
			assertFalse("Missing constructor with zero parameters in " + aClass.getSimpleName() + " class.", thrown);
	}
	
	private void testClassIsAbstract(Class aClass) 
	{
		assertTrue("You should not be able to create new instances from " + aClass.getSimpleName() + " class.", Modifier.isAbstract(aClass.getModifiers()));
	}

	private void testIsInterface(Class aClass) 
	{
		assertEquals(aClass.getName() + " should be an Interface", aClass.isInterface(), true);
	}
	
	private void testIsEnum(Class aClass) 
	{
		assertEquals(aClass.getName() + " should be an Enum", aClass.isEnum(), true);
	}

	private void testClassIsSubclass(Class subClass, Class superClass) 
	{
		assertEquals(subClass.getSimpleName() + " class should be a subclass from " + superClass.getSimpleName() + ".", superClass, subClass.getSuperclass());
	}
	
	private void testConstructorInitialization(Object createdObject, String[] names, Object[] values) throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException 
	{
		for (int i = 0; i < names.length; i++) 
		{
			Field f = null;
			Class curr = createdObject.getClass();
			String currName = names[i];
			Object currValue = values[i];
			while (f == null) 
			{
				if (curr == Object.class)
					fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \"" + currName + "\".");
				try 
				{
					f = curr.getDeclaredField(currName);
				} 
				catch (NoSuchFieldException e)
				{
					curr = curr.getSuperclass();
				}
			}
			f.setAccessible(true);
			assertEquals("The constructor of the " + createdObject.getClass().getSimpleName() + " class should initialize the instance variable \"" + currName + "\" correctly.", currValue, f.get(createdObject));
		}
	}
	
	private void testGetterLogic(Object createdObject, String name, Object value) throws Exception 
	{
		Field f = null;
		Class curr = createdObject.getClass();
		while (f == null) 
		{
			if (curr == Object.class)
				fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \"" + name + "\".");
			try 
			{
				f = curr.getDeclaredField(name);
			} 
			catch (NoSuchFieldException e) 
			{
				curr = curr.getSuperclass();
			}
		}
		f.setAccessible(true);
		f.set(createdObject, value);
		Character c = name.charAt(0);
		String methodName = "get" + Character.toUpperCase(c) + name.substring(1, name.length());
		if (value.getClass().equals(Boolean.class))
			methodName = "is" + Character.toUpperCase(c) + name.substring(1, name.length());
		Method m = createdObject.getClass().getMethod(methodName);
		assertEquals("The method \"" + methodName + "\" in class " + createdObject.getClass().getSimpleName() + " should return the correct value of variable \"" + name + "\".", value, m.invoke(createdObject));
	}
	
	private void testSetterLogic(Object createdObject, String name, Object value, Class type) throws Exception 
	{
		Field f = null;
		Class curr = createdObject.getClass();
		while (f == null)
		{

			if (curr == Object.class)
				fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \"" + name + "\".");
			try 
			{
				f = curr.getDeclaredField(name);
			} 
			catch (NoSuchFieldException e) 
			{
				curr = curr.getSuperclass();
			}
		}
		f.setAccessible(true);
		Character c = name.charAt(0);
		String methodName = "set" + Character.toUpperCase(c) + name.substring(1, name.length());
		Method m = createdObject.getClass().getMethod(methodName, type);
		m.invoke(createdObject, value);
		assertEquals("The method \"" + methodName + "\" in class " + createdObject.getClass().getSimpleName() + " should set the correct value of variable \"" + name + "\".", value, f.get(createdObject));
	}
	
	private void testClassImplementsInterface(Class aClass, Class interfaceName)
	{
		Class[] interfaces = aClass.getInterfaces();
		boolean implement = false;
		for (Class i : interfaces)
		{
			if (i.toString().equals(interfaceName.toString()))
				implement = true;
		}
		assertTrue(aClass.getSimpleName() + " class should implement " + interfaceName.getSimpleName() + " interface.", implement);
	}
	
	@Test(timeout = 1000)
	public void testReadingCSVCallingLoads() throws Exception {
		
		try{
			copyWithoutDeletingFiles();
			
		final Field citizenField = Class.forName(simulatorPath).getDeclaredField(
				"citizens");

		citizenField.setAccessible(true);

		final Field buildingsField = Class.forName(simulatorPath).getDeclaredField(
				"buildings");

		buildingsField.setAccessible(true);
		
		final Field worldField = Class.forName(simulatorPath).getDeclaredField("world");

		worldField.setAccessible(true);

		
		final Field disastersField = Class.forName(simulatorPath).getDeclaredField("plannedDisasters");

		disastersField.setAccessible(true);
		
		final Field unitsField = Class.forName(simulatorPath).getDeclaredField("emergencyUnits");

		unitsField.setAccessible(true);
		
				
		
		PrintWriter citizensWriter = new PrintWriter("citizens.csv");

		citizensWriter.println("3,3,1,zizo,25");
		citizensWriter.println("3,3,2,amr,30");
		citizensWriter.println("3,3,3,karam,40");
		citizensWriter.println("3,3,4,maisara,45");
		citizensWriter.println("3,3,5,mostafa,20");
		
		citizensWriter.close();

		PrintWriter buildingWriter = new PrintWriter("buildings.csv");

		buildingWriter.println("3,3");
		buildingWriter.println("5,5");
		buildingWriter.println("6,6");
		buildingWriter.println("9,9");
		
		buildingWriter.close();

		PrintWriter unitWriter = new PrintWriter("units.csv");
		unitWriter.println("AMB,1,2");
		unitWriter.println("DCU,2,4");
		unitWriter.println("EVC,3,6,8");
		unitWriter.println("FTK,4,8");
		unitWriter.println("GCU,9,3");
		unitWriter.close();

		PrintWriter disastersWriter = new PrintWriter("disasters.csv");

		disastersWriter.println("1,INJ,2");
		disastersWriter.println("2,INF,1");
		disastersWriter.println("4,FIR,3,3");
		disastersWriter.println("5,GLK,5,5");

		disastersWriter.close();

		Method method = Class.forName(simulatorPath).getDeclaredMethod(
				"loadBuildings", new Class[] { String.class });

		method.setAccessible(true);
		ArrayList<Object> testBuildings = new ArrayList<Object>();

		Object s = Class.forName(simulatorPath).getConstructor().newInstance();
		
		testBuildings = (ArrayList<Object>) buildingsField.get(s);

		method = Class.forName(simulatorPath).getDeclaredMethod("loadCitizens",
				new Class[] { String.class });
		method.setAccessible(true);
		ArrayList<Object> testCitizens = new ArrayList<Object>();

		testCitizens = (ArrayList<Object>) citizenField.get(s);

		assertEquals(
				"The loaded citizens ArrayList doesn't contain the same number of citizens given in the CSV file",
				5, testCitizens.size());
		assertEquals(
				"The loaded Buildings ArrayList doesn't contain the same number of buildings given in the CSV file",
				4, testBuildings.size());

		for (int i = 0; i < testBuildings.size(); i++) {
			Object currentBuilding = testBuildings.get(i);
			ArrayList<Object> currentOccupants = (ArrayList<Object>) Class
					.forName(residentialBuildingPath).getMethod("getOccupants")
					.invoke(currentBuilding);
			if(i==0)
				assertEquals("The number of citizens per building is not correct.",
						5, currentOccupants.size());

			for (int j = 0; j < currentOccupants.size(); j++) {
				if (!testCitizens.contains(currentOccupants.get(j))) {
					fail("The citizen inside the building is not matching the one in the simulator.");
				}
			}
		}
		Object [][] testWorld = (Object[][]) worldField.get(s);
		ArrayList<Object> testDisasters = (ArrayList<Object>) disastersField.get(s);
		
		ArrayList<Object> correctCitizens = new ArrayList<Object>();
		correctCitizens.add(testCitizens.get(0));
		correctCitizens.add(testCitizens.get(1));
		
		ArrayList<Object> correctBuildingsForDisasters = new ArrayList<Object>();
		correctBuildingsForDisasters.add(testBuildings.get(0));
		correctBuildingsForDisasters.add(testBuildings.get(1));
		correctBuildingsForDisasters.add(testBuildings.get(2));
		
		ArrayList<Object> correctDisasters = new ArrayList<Object>();
		correctDisasters.add(Class.forName(injuryPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(1,correctCitizens.get(1)));
		correctDisasters.add(Class.forName(infectionPath).getConstructor(int.class, Class.forName(citizenPath)).newInstance(2,correctCitizens.get(0)));
		correctDisasters.add(Class.forName(firePath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(4,correctBuildingsForDisasters.get(0)));
		correctDisasters.add(Class.forName(gasLeakPath).getConstructor(int.class, Class.forName(residentialBuildingPath)).newInstance(5,correctBuildingsForDisasters.get(1)));
		
		assertEquals(
				"The loaded disasters ArrayList doesn't contain the same number of disasters given in the CSV file",
				correctDisasters.size(), testDisasters.size());
	
		for (int i = 0; i < correctDisasters.size(); i++) {
			assertFalse("Disaster Target should not be null",testDisasters.get(i).getClass().getMethod("getTarget").invoke(testDisasters.get(i))==null );
			assertFalse("Disaster creation type should be done correctly, expected \""+ 
					correctDisasters.get(i).getClass().getSimpleName()+"\" but was \""+
					testDisasters.get(i).getClass().getSimpleName()+"\"."
					,correctDisasters.get(i).getClass()!=testDisasters.get(i).getClass() );
			
			assertEquals(
					"Disaster Start Cycle is not loaded correctly from the CSV file",
					(correctDisasters.get(i)).getClass().getMethod("getStartCycle").invoke(correctDisasters.get(i)),
					(testDisasters.get(i)).getClass().getMethod("getStartCycle").invoke(testDisasters.get(i)));
			
			if(Class.forName(citizenPath).isInstance(correctDisasters.get(i).getClass().getMethod("getTarget").invoke(correctDisasters.get(i)))
					&& Class.forName(citizenPath).isInstance(testDisasters.get(i).getClass().getMethod("getTarget").invoke(testDisasters.get(i)))){
				Object correctDisasterTarget = (correctDisasters.get(i).getClass().getMethod("getTarget").invoke(correctDisasters.get(i)));
				Object testDisasterTarget = (testDisasters.get(i).getClass().getMethod("getTarget").invoke(testDisasters.get(i)));
				assertEquals(
						"Disaster Target Name is not loaded correctly from the CSV file",
						(correctDisasterTarget.getClass().getMethod("getName").invoke(correctDisasterTarget)),
						(testDisasterTarget.getClass().getMethod("getName").invoke(testDisasterTarget)));
				assertEquals(
						"Disaster Target NationalID is not loaded correctly from the CSV file",
						(correctDisasterTarget.getClass().getMethod("getNationalID").invoke(correctDisasterTarget)),
						(testDisasterTarget.getClass().getMethod("getNationalID").invoke(testDisasterTarget)));
				assertEquals(
						"Disaster Target Age is not loaded correctly from the CSV file",
						(correctDisasterTarget.getClass().getMethod("getAge").invoke(correctDisasterTarget)),
						(testDisasterTarget.getClass().getMethod("getAge").invoke(testDisasterTarget)));
				assertEquals(
						"Disaster Target Location is not loaded correctly from the CSV file",
						(correctDisasterTarget.getClass().getMethod("getLocation").invoke(correctDisasterTarget)),
						(testDisasterTarget.getClass().getMethod("getLocation").invoke(testDisasterTarget)));
				
			}
			
			if(Class.forName(residentialBuildingPath).isInstance(correctDisasters.get(i).getClass().getMethod("getTarget").invoke(correctDisasters.get(i))) 
					&& Class.forName(residentialBuildingPath).isInstance(testDisasters.get(i).getClass().getMethod("getTarget").invoke(testDisasters.get(i)))){
				
				Object correctDisasterTarget = (correctDisasters.get(i).getClass().getMethod("getTarget").invoke(correctDisasters.get(i)));
				Object testDisasterTarget = (testDisasters.get(i).getClass().getMethod("getTarget").invoke(testDisasters.get(i)));
				
				assertEquals(
						"Disaster Target Location is not loaded correctly from the CSV file",
						(correctDisasterTarget.getClass().getMethod("getLocation").invoke(correctDisasterTarget)),
						(testDisasterTarget.getClass().getMethod("getLocation").invoke(testDisasterTarget)));
				
			}
			
		}
		
		
		ArrayList<Object> testUnits= (ArrayList<Object>) unitsField.get(s);
		ArrayList<Object> correctUnit = new ArrayList<Object>();
		correctUnit.add(Class.forName(ambulancePath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance("1", testWorld[0][0], 2));
		correctUnit.add(Class.forName(diseaseControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance("2", testWorld[0][0], 4));
		correctUnit.add(Class.forName(evacuatorPath).getConstructor(String.class, Class.forName(addressPath), int.class, int.class).newInstance("3", testWorld[0][0], 6,8));
		correctUnit.add(Class.forName(fireTruckPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance("4", testWorld[0][0], 8));
		correctUnit.add(Class.forName(gasControlUnitPath).getConstructor(String.class, Class.forName(addressPath), int.class).newInstance("9", testWorld[0][0], 3));
		
		
		
		assertEquals(
				"The loaded units ArrayList doesn't contain the same number of disasters given in the CSV file",
				correctUnit.size(), testUnits.size());
		
		for(int i =0;i<correctUnit.size();i++){
			
			assertFalse("Unit creation type should be done correctly, expected \""+ 
					correctUnit.get(i).getClass().getSimpleName()+"\" but was \""+
					testUnits.get(i).getClass().getSimpleName()+"\"."
					,correctUnit.get(i).getClass()!=testUnits.get(i).getClass() );
			
			assertEquals(
					"unit Location is not loaded correctly from the CSV file",
					(correctUnit.get(i).getClass().getMethod("getLocation").invoke(correctUnit.get(i))),
					(testUnits.get(i).getClass().getMethod("getLocation").invoke(testUnits.get(i))));
			assertEquals(
					"unit Steps is not loaded correctly from the CSV file",
					(correctUnit.get(i).getClass().getMethod("getStepsPerCycle").invoke(correctUnit.get(i))),
					(testUnits.get(i).getClass().getMethod("getStepsPerCycle").invoke(testUnits.get(i))));
			assertEquals(
					"unit id is not loaded correctly from the CSV file",
					(correctUnit.get(i).getClass().getMethod("getUnitID").invoke(correctUnit.get(i))),
					(testUnits.get(i).getClass().getMethod("getUnitID").invoke(testUnits.get(i))));
			
		}
		}
		catch(Exception e){
			
		}
		finally{
			resetWithoutDeletingFiles();
		}
		
		
	}
	private static void copyWithoutDeletingFiles() throws IOException {
		File sourceFileBuildings = new File("buildings.csv");
		File destinationFileBuildings = new File("buildings_test2.csv");
		copyFile(sourceFileBuildings, destinationFileBuildings);
		File sourceFileCitizens = new File("citizens.csv");
		File destinationFileCitizens = new File("citizens_test2.csv");
		copyFile(sourceFileCitizens, destinationFileCitizens);
		File sourceFileDisasters = new File("disasters.csv");
		File destinationFileDisasters = new File("disasters_test2.csv");
		copyFile(sourceFileDisasters, destinationFileDisasters);

		File sourceFileUnits = new File("units.csv");
		File destinationFileUnits = new File("units_test2.csv");
		copyFile(sourceFileUnits, destinationFileUnits);

		sourceFileBuildings.delete();
		sourceFileCitizens.delete();
		sourceFileDisasters.delete();
		sourceFileUnits.delete();

	}
	private static void resetWithoutDeletingFiles() throws IOException {

		File sourceFileBuildings = new File("buildings_test2.csv");
		File destinationFileBuildings = new File("buildings.csv");
		File sourceFileCitizens = new File("citizens_test2.csv");
		File destinationFileCitizens = new File("citizens.csv");
		File sourceFileDisasters = new File("disasters_test2.csv");
		File destinationFileDisasters = new File("disasters.csv");
		File sourceFileUnits = new File("units_test2.csv");
		File destinationFileUnits = new File("units.csv");

		destinationFileBuildings.delete();
		destinationFileCitizens.delete();
		destinationFileDisasters.delete();
		destinationFileUnits.delete();

		destinationFileBuildings.createNewFile();
		destinationFileCitizens.createNewFile();
		destinationFileDisasters.createNewFile();
		destinationFileUnits.createNewFile();

		copyFile(sourceFileBuildings, destinationFileBuildings);
		copyFile(sourceFileCitizens, destinationFileCitizens);
		copyFile(sourceFileDisasters, destinationFileDisasters);
		copyFile(sourceFileUnits, destinationFileUnits);

		sourceFileBuildings.delete();
		sourceFileCitizens.delete();
		sourceFileDisasters.delete();
		sourceFileUnits.delete();
	}

}