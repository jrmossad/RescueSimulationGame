package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class M1PrivateTests {
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
	public void testClassIsAbstractUnit() throws Exception {
		testClassIsAbstract(Class.forName(unitPath));
	}

	@Test(timeout = 1000)
	public void testClassIsAbstractMedicalUnit() throws Exception {
		testClassIsAbstract(Class.forName(medicalUnitPath));
	}

	@Test(timeout = 1000)
	public void testClassIsEnumCitizenState() throws Exception {
		testIsEnum(Class.forName(citizenStatePath));
	}

	@Test(timeout = 1000)
	public void testClassIsSubclassPoliceUnit() throws Exception {
		testClassIsSubclass(Class.forName(policeUnitPath), Class.forName(unitPath));
	}

	@Test(timeout = 1000)
	public void testClassIsSubclassEvacuator() throws Exception {
		testClassIsSubclass(Class.forName(evacuatorPath), Class.forName(policeUnitPath));
	}

	@Test(timeout = 1000)
	public void testClassIsSubclassAmbulance() throws Exception {
		testClassIsSubclass(Class.forName(ambulancePath), Class.forName(medicalUnitPath));
	}

	@Test(timeout = 1000)
	public void testClassIsSubclassFire() throws Exception {
		testClassIsSubclass(Class.forName(firePath), Class.forName(disasterPath));
	}

	@Test(timeout = 1000)
	public void testClassIsSubclassInjury() throws Exception {
		testClassIsSubclass(Class.forName(injuryPath), Class.forName(disasterPath));
	}

	@Test(timeout = 1000)
	public void testConstructorSimulator0() throws Exception {
		Class[] inputs = {};
		testConstructorExists(Class.forName(simulatorPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorCitizen0() throws Exception {
		Class[] inputs = { Class.forName(addressPath), String.class, String.class, int.class };
		testConstructorExists(Class.forName(citizenPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorFireUnit0() throws Exception {
		Class[] inputs = { String.class, Class.forName(addressPath), int.class };
		testConstructorExists(Class.forName(fireUnitPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorFireTruck0() throws Exception {
		Class[] inputs = { String.class, Class.forName(addressPath), int.class };
		testConstructorExists(Class.forName(fireTruckPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorDiseaseControlUnit0() throws Exception {
		Class[] inputs = { String.class, Class.forName(addressPath), int.class };
		testConstructorExists(Class.forName(diseaseControlUnitPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorFire0() throws Exception {
		Class[] inputs = { int.class, Class.forName(residentialBuildingPath) };
		testConstructorExists(Class.forName(firePath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorInjury0() throws Exception {
		Class[] inputs = { int.class, Class.forName(citizenPath) };
		testConstructorExists(Class.forName(injuryPath), inputs);
	}

	@Test(timeout = 1000)
	public void testEnumValuesCitizenState() throws Exception {
		String[] inputs = { "DECEASED", "RESCUED", "IN_TROUBLE", "SAFE" };
		testEnumValues(Class.forName(citizenStatePath), inputs);
	}

	@Test(timeout = 1000)
	public void testClassIsInterfaceRescuable() throws Exception {
		testIsInterface(Class.forName(rescuablePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableSimulatorCurrentCycle() throws Exception {
		testInstanceVariableIsPresent(Class.forName(simulatorPath), "currentCycle", true);
		testInstanceVariableIsPrivate(Class.forName(simulatorPath), "currentCycle");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableSimulatorEmergencyUnits() throws Exception {
		testInstanceVariableIsPresent(Class.forName(simulatorPath), "emergencyUnits", true);
		testInstanceVariableIsPrivate(Class.forName(simulatorPath), "emergencyUnits");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableSimulatorExecutedDisasters() throws Exception {
		testInstanceVariableIsPresent(Class.forName(simulatorPath), "executedDisasters", true);
		testInstanceVariableIsPrivate(Class.forName(simulatorPath), "executedDisasters");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingLocation() throws Exception {
		testInstanceVariableIsPresent(Class.forName(residentialBuildingPath), "location", true);
		testInstanceVariableIsPrivate(Class.forName(residentialBuildingPath), "location");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingGasLevel() throws Exception {
		testInstanceVariableIsPresent(Class.forName(residentialBuildingPath), "gasLevel", true);
		testInstanceVariableIsPrivate(Class.forName(residentialBuildingPath), "gasLevel");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingDisaster() throws Exception {
		testInstanceVariableIsPresent(Class.forName(residentialBuildingPath), "disaster", true);
		testInstanceVariableIsPrivate(Class.forName(residentialBuildingPath), "disaster");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenName() throws Exception {
		testInstanceVariableIsPresent(Class.forName(citizenPath), "name", true);
		testInstanceVariableIsPrivate(Class.forName(citizenPath), "name");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenHp() throws Exception {
		testInstanceVariableIsPresent(Class.forName(citizenPath), "hp", true);
		testInstanceVariableIsPrivate(Class.forName(citizenPath), "hp");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenLocation() throws Exception {
		testInstanceVariableIsPresent(Class.forName(citizenPath), "location", true);
		testInstanceVariableIsPrivate(Class.forName(citizenPath), "location");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableUnitLocation() throws Exception {
		testInstanceVariableIsPresent(Class.forName(unitPath), "location", true);
		testInstanceVariableIsPrivate(Class.forName(unitPath), "location");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableUnitStepsPerCycle() throws Exception {
		testInstanceVariableIsPresent(Class.forName(unitPath), "stepsPerCycle", true);
		testInstanceVariableIsPrivate(Class.forName(unitPath), "stepsPerCycle");
	}

	@Test(timeout = 1000)
	public void testInstanceVariablePoliceUnitDistanceToBase() throws Exception {
		testInstanceVariableIsPresent(Class.forName(policeUnitPath), "distanceToBase", true);
		testInstanceVariableIsPrivate(Class.forName(policeUnitPath), "distanceToBase");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDisasterStartCycle() throws Exception {
		testInstanceVariableIsPresent(Class.forName(disasterPath), "startCycle", true);
		testInstanceVariableIsPrivate(Class.forName(disasterPath), "startCycle");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCommandCenterEngine() throws Exception {
		testInstanceVariableIsPresent(Class.forName(commandCenterPath), "engine", true);
		testInstanceVariableIsPrivate(Class.forName(commandCenterPath), "engine");
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCommandCenterEmergencyUnits() throws Exception {
		testInstanceVariableIsPresent(Class.forName(commandCenterPath), "emergencyUnits", true);
		testInstanceVariableIsPrivate(Class.forName(commandCenterPath), "emergencyUnits");
	}

	@Test(timeout = 100)
	public void testUnitUnitIDNotInClasses() throws Exception {
		testInstanceVariableIsPresent(Class.forName(gasControlUnitPath), "unitID", false);
		testInstanceVariableIsPresent(Class.forName(policeUnitPath), "unitID", false);
		testInstanceVariableIsPresent(Class.forName(evacuatorPath), "unitID", false);
		testInstanceVariableIsPresent(Class.forName(fireUnitPath), "unitID", false);
		testInstanceVariableIsPresent(Class.forName(fireTruckPath), "unitID", false);
		testInstanceVariableIsPresent(Class.forName(medicalUnitPath), "unitID", false);
		testInstanceVariableIsPresent(Class.forName(ambulancePath), "unitID", false);
		testInstanceVariableIsPresent(Class.forName(diseaseControlUnitPath), "unitID", false);
	}

	@Test(timeout = 100)
	public void testUnitTargetNotInClasses() throws Exception {
		testInstanceVariableIsPresent(Class.forName(gasControlUnitPath), "target", false);
		testInstanceVariableIsPresent(Class.forName(policeUnitPath), "target", false);
		testInstanceVariableIsPresent(Class.forName(evacuatorPath), "target", false);
		testInstanceVariableIsPresent(Class.forName(fireUnitPath), "target", false);
		testInstanceVariableIsPresent(Class.forName(fireTruckPath), "target", false);
		testInstanceVariableIsPresent(Class.forName(medicalUnitPath), "target", false);
		testInstanceVariableIsPresent(Class.forName(ambulancePath), "target", false);
		testInstanceVariableIsPresent(Class.forName(diseaseControlUnitPath), "target", false);
	}

	@Test(timeout = 100)
	public void testDisasterStartCycleNotInClasses() throws Exception {
		testInstanceVariableIsPresent(Class.forName(injuryPath), "startCycle", false);
		testInstanceVariableIsPresent(Class.forName(firePath), "startCycle", false);
		testInstanceVariableIsPresent(Class.forName(collapsePath), "startCycle", false);
		testInstanceVariableIsPresent(Class.forName(infectionPath), "startCycle", false);
		testInstanceVariableIsPresent(Class.forName(gasLeakPath), "startCycle", false);
	}

	@Test(timeout = 1000)
	public void testResidentialBuildingImplementsRescuable() throws Exception {
		testClassImplementsInterface(Class.forName(residentialBuildingPath), Class.forName(rescuablePath));
	}

	@Test(timeout = 1000)
	public void testCitizenImplementsSimulatable() throws Exception {
		testClassImplementsInterface(Class.forName(citizenPath), Class.forName(simulatablePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableSimulatorCurrentCycleGetterAndSetter() throws Exception {
		testGetterMethodExistsInClass(Class.forName(simulatorPath), "getCurrentCycle", int.class, false);
		testSetterMethodExistsInClass(Class.forName(simulatorPath), "setCurrentCycle", int.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableSimulatorEmergencyUnitsGetterAndSetter() throws Exception {
		testGetterMethodExistsInClass(Class.forName(simulatorPath), "getEmergencyUnits", ArrayList.class, false);
		testSetterMethodExistsInClass(Class.forName(simulatorPath), "setEmergencyUnits", ArrayList.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableSimulatorExecutedDisastersGetterAndSetter() throws Exception {
		testGetterMethodExistsInClass(Class.forName(simulatorPath), "getExecutedDisasters", ArrayList.class, false);
		testSetterMethodExistsInClass(Class.forName(simulatorPath), "setExecutedDisasters", ArrayList.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingLocationGetterAndSetter() throws Exception {
		testGetterMethodExistsInClass(Class.forName(residentialBuildingPath), "getLocation", Class.forName(addressPath),
				true);
		testSetterMethodExistsInClass(Class.forName(residentialBuildingPath), "setLocation", Class.forName(addressPath),
				false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingGasLevelGetterAndSetter() throws Exception {
		testGetterMethodExistsInClass(Class.forName(residentialBuildingPath), "getGasLevel", int.class, true);
		testSetterMethodExistsInClass(Class.forName(residentialBuildingPath), "setGasLevel", int.class, true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingDisasterGetterAndSetter() throws Exception {
		testGetterMethodExistsInClass(Class.forName(residentialBuildingPath), "getDisaster",
				Class.forName(disasterPath), true);
		testSetterMethodExistsInClass(Class.forName(residentialBuildingPath), "setDisaster",
				Class.forName(disasterPath), false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenNameGetterAndSetter() throws Exception {
		testGetterMethodExistsInClass(Class.forName(citizenPath), "getName", String.class, true);
		testSetterMethodExistsInClass(Class.forName(citizenPath), "setName", String.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenHpGetterAndSetter() throws Exception {
		testGetterMethodExistsInClass(Class.forName(citizenPath), "getHp", int.class, true);
		testSetterMethodExistsInClass(Class.forName(citizenPath), "setHp", int.class, true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenLocationGetterAndSetter() throws Exception {
		testGetterMethodExistsInClass(Class.forName(citizenPath), "getLocation", Class.forName(addressPath), true);
		testSetterMethodExistsInClass(Class.forName(citizenPath), "setLocation", Class.forName(addressPath), true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableUnitLocationGetterAndSetter() throws Exception {
		testGetterMethodExistsInClass(Class.forName(unitPath), "getLocation", Class.forName(addressPath), true);
		testSetterMethodExistsInClass(Class.forName(unitPath), "setLocation", Class.forName(addressPath), true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableUnitStepsPerCycleGetterAndSetter() throws Exception {
		testGetterMethodExistsInClass(Class.forName(unitPath), "getStepsPerCycle", int.class, true);
		testSetterMethodExistsInClass(Class.forName(unitPath), "setStepsPerCycle", int.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariablePoliceUnitDistanceToBaseGetterAndSetter() throws Exception {
		testGetterMethodExistsInClass(Class.forName(policeUnitPath), "getDistanceToBase", int.class, true);
		testSetterMethodExistsInClass(Class.forName(policeUnitPath), "setDistanceToBase", int.class, true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDisasterStartCycleGetterAndSetter() throws Exception {
		testGetterMethodExistsInClass(Class.forName(disasterPath), "getStartCycle", int.class, true);
		testSetterMethodExistsInClass(Class.forName(disasterPath), "setStartCycle", int.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCommandCenterEngineGetterAndSetter() throws Exception {
		testGetterMethodExistsInClass(Class.forName(commandCenterPath), "getEngine", Class.forName(simulatorPath),
				false);
		testSetterMethodExistsInClass(Class.forName(commandCenterPath), "setEngine", Class.forName(simulatorPath),
				false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCommandCenterEmergencyUnitsGetterAndSetter() throws Exception {
		testGetterMethodExistsInClass(Class.forName(commandCenterPath), "getEmergencyUnits", ArrayList.class, false);
		testSetterMethodExistsInClass(Class.forName(commandCenterPath), "setEmergencyUnits", ArrayList.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAddressXGetterLogic0() throws Exception {
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		testGetterLogic(address0, "x", 8);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAddressYGetterLogic0() throws Exception {
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		testGetterLogic(address0, "y", 5);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenStateGetterLogic0() throws Exception {
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		testGetterLogic(citizen0, "state", Enum.valueOf((Class<Enum>) Class.forName(citizenStatePath), "SAFE"));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenNameGetterLogic0() throws Exception {
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		testGetterLogic(citizen0, "name", String7);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenNationalIDGetterLogic0() throws Exception {
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		testGetterLogic(citizen0, "nationalID", String7);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenAgeGetterLogic0() throws Exception {
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		testGetterLogic(citizen0, "age", 9);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenHpGetterLogic0() throws Exception {
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		testGetterLogic(citizen0, "hp", 100);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenBloodLossGetterLogic0() throws Exception {
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		testGetterLogic(citizen0, "bloodLoss", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenToxicityGetterLogic0() throws Exception {
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		testGetterLogic(citizen0, "toxicity", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenLocationGetterLogic0() throws Exception {
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		testGetterLogic(citizen0, "location", address1);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingLocationGetterLogic0() throws Exception {
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		testGetterLogic(residentialBuilding0, "location", address0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingStructuralIntegrityGetterLogic0() throws Exception {
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		testGetterLogic(residentialBuilding0, "structuralIntegrity", 100);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingFireDamageGetterLogic0() throws Exception {
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		testGetterLogic(residentialBuilding0, "fireDamage", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingGasLevelGetterLogic0() throws Exception {
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		testGetterLogic(residentialBuilding0, "gasLevel", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingFoundationDamageGetterLogic0() throws Exception {
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		testGetterLogic(residentialBuilding0, "foundationDamage", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorMaxCapacityGetterLogic0() throws Exception {
		String String5 = "bc";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int7 = 7;
		int int5 = 5;
		Object evacuator0 = Class.forName(evacuatorPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class, int.class)
				.newInstance(String5, address2, int7, int5);
		testGetterLogic(evacuator0, "maxCapacity", 5);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorDistanceToBaseGetterLogic0() throws Exception {
		String String5 = "bc";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int7 = 7;
		int int5 = 5;
		Object evacuator0 = Class.forName(evacuatorPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class, int.class)
				.newInstance(String5, address2, int7, int5);
		testGetterLogic(evacuator0, "distanceToBase", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorUnitIDGetterLogic0() throws Exception {
		String String5 = "bc";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int7 = 7;
		int int5 = 5;
		Object evacuator0 = Class.forName(evacuatorPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class, int.class)
				.newInstance(String5, address2, int7, int5);
		testGetterLogic(evacuator0, "unitID", String5);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorStateGetterLogic0() throws Exception {
		String String5 = "bc";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int7 = 7;
		int int5 = 5;
		Object evacuator0 = Class.forName(evacuatorPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class, int.class)
				.newInstance(String5, address2, int7, int5);
		testGetterLogic(evacuator0, "state", Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorLocationGetterLogic0() throws Exception {
		String String5 = "bc";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int7 = 7;
		int int5 = 5;
		Object evacuator0 = Class.forName(evacuatorPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class, int.class)
				.newInstance(String5, address2, int7, int5);
		testGetterLogic(evacuator0, "location", address2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorStepsPerCycleGetterLogic0() throws Exception {
		String String5 = "bc";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int7 = 7;
		int int5 = 5;
		Object evacuator0 = Class.forName(evacuatorPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class, int.class)
				.newInstance(String5, address2, int7, int5);
		testGetterLogic(evacuator0, "stepsPerCycle", 7);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTruckUnitIDGetterLogic0() throws Exception {
		String String4 = "qwnprn";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int2 = 2;
		Object fireTruck0 = Class.forName(fireTruckPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String4, address2, int2);
		testGetterLogic(fireTruck0, "unitID", String4);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTruckStateGetterLogic0() throws Exception {
		String String4 = "qwnprn";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int2 = 2;
		Object fireTruck0 = Class.forName(fireTruckPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String4, address2, int2);
		testGetterLogic(fireTruck0, "state", Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTruckLocationGetterLogic0() throws Exception {
		String String4 = "qwnprn";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int2 = 2;
		Object fireTruck0 = Class.forName(fireTruckPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String4, address2, int2);
		testGetterLogic(fireTruck0, "location", address2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTruckStepsPerCycleGetterLogic0() throws Exception {
		String String4 = "qwnprn";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int2 = 2;
		Object fireTruck0 = Class.forName(fireTruckPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String4, address2, int2);
		testGetterLogic(fireTruck0, "stepsPerCycle", 2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasControlUnitUnitIDGetterLogic0() throws Exception {
		String String5 = "bc";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int5 = 5;
		Object gasControlUnit0 = Class.forName(gasControlUnitPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String5, address2, int5);
		testGetterLogic(gasControlUnit0, "unitID", String5);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasControlUnitStateGetterLogic0() throws Exception {
		String String5 = "bc";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int5 = 5;
		Object gasControlUnit0 = Class.forName(gasControlUnitPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String5, address2, int5);
		testGetterLogic(gasControlUnit0, "state", Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasControlUnitLocationGetterLogic0() throws Exception {
		String String5 = "bc";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int5 = 5;
		Object gasControlUnit0 = Class.forName(gasControlUnitPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String5, address2, int5);
		testGetterLogic(gasControlUnit0, "location", address2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasControlUnitStepsPerCycleGetterLogic0() throws Exception {
		String String5 = "bc";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int5 = 5;
		Object gasControlUnit0 = Class.forName(gasControlUnitPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String5, address2, int5);
		testGetterLogic(gasControlUnit0, "stepsPerCycle", 5);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAmbulanceUnitIDGetterLogic0() throws Exception {
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object ambulance0 = Class.forName(ambulancePath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String5, address0, int8);
		testGetterLogic(ambulance0, "unitID", String5);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAmbulanceStateGetterLogic0() throws Exception {
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object ambulance0 = Class.forName(ambulancePath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String5, address0, int8);
		testGetterLogic(ambulance0, "state", Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAmbulanceLocationGetterLogic0() throws Exception {
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object ambulance0 = Class.forName(ambulancePath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String5, address0, int8);
		testGetterLogic(ambulance0, "location", address0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAmbulanceStepsPerCycleGetterLogic0() throws Exception {
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object ambulance0 = Class.forName(ambulancePath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String5, address0, int8);
		testGetterLogic(ambulance0, "stepsPerCycle", 8);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDiseaseControlUnitUnitIDGetterLogic0() throws Exception {
		String String3 = "r";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int0 = 0;
		Object diseaseControlUnit0 = Class.forName(diseaseControlUnitPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String3, address2, int0);
		testGetterLogic(diseaseControlUnit0, "unitID", String3);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDiseaseControlUnitStateGetterLogic0() throws Exception {
		String String3 = "r";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int0 = 0;
		Object diseaseControlUnit0 = Class.forName(diseaseControlUnitPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String3, address2, int0);
		testGetterLogic(diseaseControlUnit0, "state", Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDiseaseControlUnitLocationGetterLogic0() throws Exception {
		String String3 = "r";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int0 = 0;
		Object diseaseControlUnit0 = Class.forName(diseaseControlUnitPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String3, address2, int0);
		testGetterLogic(diseaseControlUnit0, "location", address2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDiseaseControlUnitStepsPerCycleGetterLogic0() throws Exception {
		String String3 = "r";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int0 = 0;
		Object diseaseControlUnit0 = Class.forName(diseaseControlUnitPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String3, address2, int0);
		testGetterLogic(diseaseControlUnit0, "stepsPerCycle", 0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCollapseStartCycleGetterLogic0() throws Exception {
		int int6 = 6;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		Object collapse0 = Class.forName(collapsePath).getConstructor(int.class, Class.forName(residentialBuildingPath))
				.newInstance(int6, residentialBuilding2);
		testGetterLogic(collapse0, "startCycle", 6);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCollapseTargetGetterLogic0() throws Exception {
		int int6 = 6;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		Object collapse0 = Class.forName(collapsePath).getConstructor(int.class, Class.forName(residentialBuildingPath))
				.newInstance(int6, residentialBuilding2);
		testGetterLogic(collapse0, "target", residentialBuilding2);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCollapseActiveGetterLogic0() throws Exception {
		int int6 = 6;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		Object collapse0 = Class.forName(collapsePath).getConstructor(int.class, Class.forName(residentialBuildingPath))
				.newInstance(int6, residentialBuilding2);
		testGetterLogic(collapse0, "active", false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireStartCycleGetterLogic0() throws Exception {
		int int5 = 5;
		int int8 = 8;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		Object fire0 = Class.forName(firePath).getConstructor(int.class, Class.forName(residentialBuildingPath))
				.newInstance(int5, residentialBuilding0);
		testGetterLogic(fire0, "startCycle", 5);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTargetGetterLogic0() throws Exception {
		int int5 = 5;
		int int8 = 8;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		Object fire0 = Class.forName(firePath).getConstructor(int.class, Class.forName(residentialBuildingPath))
				.newInstance(int5, residentialBuilding0);
		testGetterLogic(fire0, "target", residentialBuilding0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireActiveGetterLogic0() throws Exception {
		int int5 = 5;
		int int8 = 8;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		Object fire0 = Class.forName(firePath).getConstructor(int.class, Class.forName(residentialBuildingPath))
				.newInstance(int5, residentialBuilding0);
		testGetterLogic(fire0, "active", false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasLeakStartCycleGetterLogic0() throws Exception {
		int int9 = 9;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		Object gasLeak0 = Class.forName(gasLeakPath).getConstructor(int.class, Class.forName(residentialBuildingPath))
				.newInstance(int9, residentialBuilding0);
		testGetterLogic(gasLeak0, "startCycle", 9);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasLeakTargetGetterLogic0() throws Exception {
		int int9 = 9;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		Object gasLeak0 = Class.forName(gasLeakPath).getConstructor(int.class, Class.forName(residentialBuildingPath))
				.newInstance(int9, residentialBuilding0);
		testGetterLogic(gasLeak0, "target", residentialBuilding0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasLeakActiveGetterLogic0() throws Exception {
		int int9 = 9;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		Object gasLeak0 = Class.forName(gasLeakPath).getConstructor(int.class, Class.forName(residentialBuildingPath))
				.newInstance(int9, residentialBuilding0);
		testGetterLogic(gasLeak0, "active", false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInfectionStartCycleGetterLogic0() throws Exception {
		int int4 = 4;
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		Object infection0 = Class.forName(infectionPath).getConstructor(int.class, Class.forName(citizenPath))
				.newInstance(int4, citizen0);
		testGetterLogic(infection0, "startCycle", 4);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInfectionTargetGetterLogic0() throws Exception {
		int int4 = 4;
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		Object infection0 = Class.forName(infectionPath).getConstructor(int.class, Class.forName(citizenPath))
				.newInstance(int4, citizen0);
		testGetterLogic(infection0, "target", citizen0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInfectionActiveGetterLogic0() throws Exception {
		int int4 = 4;
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		Object infection0 = Class.forName(infectionPath).getConstructor(int.class, Class.forName(citizenPath))
				.newInstance(int4, citizen0);
		testGetterLogic(infection0, "active", false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInjuryStartCycleGetterLogic0() throws Exception {
		int int6 = 6;
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		Object injury0 = Class.forName(injuryPath).getConstructor(int.class, Class.forName(citizenPath))
				.newInstance(int6, citizen0);
		testGetterLogic(injury0, "startCycle", 6);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInjuryTargetGetterLogic0() throws Exception {
		int int6 = 6;
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		Object injury0 = Class.forName(injuryPath).getConstructor(int.class, Class.forName(citizenPath))
				.newInstance(int6, citizen0);
		testGetterLogic(injury0, "target", citizen0);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInjuryActiveGetterLogic0() throws Exception {
		int int6 = 6;
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		Object injury0 = Class.forName(injuryPath).getConstructor(int.class, Class.forName(citizenPath))
				.newInstance(int6, citizen0);
		testGetterLogic(injury0, "active", false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenStateSetterLogic0() throws Exception {
		Object citizenState1 = Enum.valueOf((Class<Enum>) Class.forName(citizenStatePath), "RESCUED");
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		testSetterLogic(citizen0, "state", citizenState1, Class.forName(citizenStatePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenHpSetterLogic0() throws Exception {
		int int1 = 1;
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		testSetterLogic(citizen0, "hp", int1, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenBloodLossSetterLogic0() throws Exception {
		int int4 = 4;
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		testSetterLogic(citizen0, "bloodLoss", int4, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenToxicitySetterLogic0() throws Exception {
		int int9 = 9;
		int int0 = 0;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		testSetterLogic(citizen0, "toxicity", int9, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCitizenLocationSetterLogic0() throws Exception {
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		testSetterLogic(citizen0, "location", address0, Class.forName(addressPath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingStructuralIntegritySetterLogic0() throws Exception {
		int int6 = 6;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		testSetterLogic(residentialBuilding0, "structuralIntegrity", int6, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingFireDamageSetterLogic0() throws Exception {
		int int6 = 6;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		testSetterLogic(residentialBuilding0, "fireDamage", int6, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingGasLevelSetterLogic0() throws Exception {
		int int1 = 1;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		testSetterLogic(residentialBuilding0, "gasLevel", int1, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResidentialBuildingFoundationDamageSetterLogic0() throws Exception {
		int int4 = 4;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		testSetterLogic(residentialBuilding0, "foundationDamage", int4, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorDistanceToBaseSetterLogic0() throws Exception {
		int int9 = 9;
		String String5 = "bc";
		int int8 = 8;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int7 = 7;
		int int5 = 5;
		Object evacuator0 = Class.forName(evacuatorPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class, int.class)
				.newInstance(String5, address2, int7, int5);
		testSetterLogic(evacuator0, "distanceToBase", int9, int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorStateSetterLogic0() throws Exception {
		Object unitState1 = Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "RESPONDING");
		String String5 = "bc";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int7 = 7;
		int int5 = 5;
		Object evacuator0 = Class.forName(evacuatorPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class, int.class)
				.newInstance(String5, address2, int7, int5);
		testSetterLogic(evacuator0, "state", unitState1, Class.forName(unitStatePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableEvacuatorLocationSetterLogic0() throws Exception {
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String5 = "bc";
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int7 = 7;
		Object evacuator0 = Class.forName(evacuatorPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class, int.class)
				.newInstance(String5, address2, int7, int5);
		testSetterLogic(evacuator0, "location", address0, Class.forName(addressPath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTruckStateSetterLogic0() throws Exception {
		Object unitState0 = Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE");
		String String4 = "qwnprn";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int2 = 2;
		Object fireTruck0 = Class.forName(fireTruckPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String4, address2, int2);
		testSetterLogic(fireTruck0, "state", unitState0, Class.forName(unitStatePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireTruckLocationSetterLogic0() throws Exception {
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String4 = "qwnprn";
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int2 = 2;
		Object fireTruck0 = Class.forName(fireTruckPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String4, address2, int2);
		testSetterLogic(fireTruck0, "location", address0, Class.forName(addressPath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasControlUnitStateSetterLogic0() throws Exception {
		Object unitState2 = Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "TREATING");
		String String5 = "bc";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int5 = 5;
		Object gasControlUnit0 = Class.forName(gasControlUnitPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String5, address2, int5);
		testSetterLogic(gasControlUnit0, "state", unitState2, Class.forName(unitStatePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasControlUnitLocationSetterLogic0() throws Exception {
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String String5 = "bc";
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object gasControlUnit0 = Class.forName(gasControlUnitPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String5, address2, int5);
		testSetterLogic(gasControlUnit0, "location", address0, Class.forName(addressPath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAmbulanceStateSetterLogic0() throws Exception {
		Object unitState0 = Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE");
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object ambulance0 = Class.forName(ambulancePath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String5, address0, int8);
		testSetterLogic(ambulance0, "state", unitState0, Class.forName(unitStatePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAmbulanceLocationSetterLogic0() throws Exception {
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object ambulance0 = Class.forName(ambulancePath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String5, address0, int8);
		testSetterLogic(ambulance0, "location", address1, Class.forName(addressPath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDiseaseControlUnitStateSetterLogic0() throws Exception {
		Object unitState0 = Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE");
		String String3 = "r";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int0 = 0;
		Object diseaseControlUnit0 = Class.forName(diseaseControlUnitPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String3, address2, int0);
		testSetterLogic(diseaseControlUnit0, "state", unitState0, Class.forName(unitStatePath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDiseaseControlUnitLocationSetterLogic0() throws Exception {
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String3 = "r";
		int int8 = 8;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		Object diseaseControlUnit0 = Class.forName(diseaseControlUnitPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String3, address2, int0);
		testSetterLogic(diseaseControlUnit0, "location", address1, Class.forName(addressPath));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCollapseActiveSetterLogic0() throws Exception {
		boolean b1 = true;
		int int6 = 6;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		Object collapse0 = Class.forName(collapsePath).getConstructor(int.class, Class.forName(residentialBuildingPath))
				.newInstance(int6, residentialBuilding2);
		testSetterLogic(collapse0, "active", b1, boolean.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableFireActiveSetterLogic0() throws Exception {
		boolean b2 = false;
		int int5 = 5;
		int int8 = 8;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		Object fire0 = Class.forName(firePath).getConstructor(int.class, Class.forName(residentialBuildingPath))
				.newInstance(int5, residentialBuilding0);
		testSetterLogic(fire0, "active", b2, boolean.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableGasLeakActiveSetterLogic0() throws Exception {
		boolean b2 = false;
		int int9 = 9;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		Object gasLeak0 = Class.forName(gasLeakPath).getConstructor(int.class, Class.forName(residentialBuildingPath))
				.newInstance(int9, residentialBuilding0);
		testSetterLogic(gasLeak0, "active", b2, boolean.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInfectionActiveSetterLogic0() throws Exception {
		boolean b1 = true;
		int int4 = 4;
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		Object infection0 = Class.forName(infectionPath).getConstructor(int.class, Class.forName(citizenPath))
				.newInstance(int4, citizen0);
		testSetterLogic(infection0, "active", b1, boolean.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableInjuryActiveSetterLogic0() throws Exception {
		boolean b2 = false;
		int int6 = 6;
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		Object injury0 = Class.forName(injuryPath).getConstructor(int.class, Class.forName(citizenPath))
				.newInstance(int6, citizen0);
		testSetterLogic(injury0, "active", b2, boolean.class);
	}

	@Test(timeout = 1000)
	public void testConstructorAddressConstructor0Initialization() throws Exception {
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		String[] names = { "x", "y" };
		Object[] values = { 8, 5 };
		testConstructorInitialization(address0, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorCitizenConstructor0Initialization() throws Exception {
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		String[] names = { "state", "name", "nationalID", "age", "hp", "bloodLoss", "toxicity", "location" };
		Object[] values = { Enum.valueOf((Class<Enum>) Class.forName(citizenStatePath), "SAFE"), String7, String7, 9,
				100, 0, 0, address1 };
		testConstructorInitialization(citizen0, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorResidentialBuildingConstructor0Initialization() throws Exception {
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		String[] names = { "location", "structuralIntegrity", "fireDamage", "gasLevel", "foundationDamage",
				"occupants", };
		Object[] values = { address0, 100, 0, 0, 0, new ArrayList<>(), };
		testConstructorInitialization(residentialBuilding0, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorEvacuatorConstructor0Initialization() throws Exception {
		String String5 = "bc";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int7 = 7;
		int int5 = 5;
		Object evacuator0 = Class.forName(evacuatorPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class, int.class)
				.newInstance(String5, address2, int7, int5);
		String[] names = { "passengers", "maxCapacity", "distanceToBase", "unitID", "state", "location",
				"distanceToTarget", "stepsPerCycle" };
		Object[] values = { new ArrayList<>(), 5, 0, String5,
				Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"), address2, 0, 7 };
		testConstructorInitialization(evacuator0, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorFireTruckConstructor0Initialization() throws Exception {
		String String4 = "qwnprn";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int2 = 2;
		Object fireTruck0 = Class.forName(fireTruckPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String4, address2, int2);
		String[] names = { "unitID", "state", "location", "distanceToTarget", "stepsPerCycle" };
		Object[] values = { String4, Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"), address2, 0, 2 };
		testConstructorInitialization(fireTruck0, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorGasControlUnitConstructor0Initialization() throws Exception {
		String String5 = "bc";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int5 = 5;
		Object gasControlUnit0 = Class.forName(gasControlUnitPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String5, address2, int5);
		String[] names = { "unitID", "state", "location", "distanceToTarget", "stepsPerCycle" };
		Object[] values = { String5, Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"), address2, 0, 5 };
		testConstructorInitialization(gasControlUnit0, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorAmbulanceConstructor0Initialization() throws Exception {
		String String5 = "bc";
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object ambulance0 = Class.forName(ambulancePath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String5, address0, int8);
		String[] names = { "healingAmount", "treatmentAmount", "unitID", "state", "location", "distanceToTarget",
				"stepsPerCycle" };
		Object[] values = { 10, 10, String5, Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"), address0,
				0, 8 };
		testConstructorInitialization(ambulance0, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorDiseaseControlUnitConstructor0Initialization() throws Exception {
		String String3 = "r";
		int int8 = 8;
		int int9 = 9;
		Object address2 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int9);
		int int0 = 0;
		Object diseaseControlUnit0 = Class.forName(diseaseControlUnitPath)
				.getConstructor(String.class, Class.forName(addressPath), int.class)
				.newInstance(String3, address2, int0);
		String[] names = { "healingAmount", "treatmentAmount", "unitID", "state", "location", "distanceToTarget",
				"stepsPerCycle" };
		Object[] values = { 10, 10, String3, Enum.valueOf((Class<Enum>) Class.forName(unitStatePath), "IDLE"), address2,
				0, 0 };
		testConstructorInitialization(diseaseControlUnit0, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorCollapseConstructor0Initialization() throws Exception {
		int int6 = 6;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding2 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		Object collapse0 = Class.forName(collapsePath).getConstructor(int.class, Class.forName(residentialBuildingPath))
				.newInstance(int6, residentialBuilding2);
		String[] names = { "startCycle", "target", "active" };
		Object[] values = { 6, residentialBuilding2, false };
		testConstructorInitialization(collapse0, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorFireConstructor0Initialization() throws Exception {
		int int5 = 5;
		int int8 = 8;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		Object fire0 = Class.forName(firePath).getConstructor(int.class, Class.forName(residentialBuildingPath))
				.newInstance(int5, residentialBuilding0);
		String[] names = { "startCycle", "target", "active" };
		Object[] values = { 5, residentialBuilding0, false };
		testConstructorInitialization(fire0, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorGasLeakConstructor0Initialization() throws Exception {
		int int9 = 9;
		int int8 = 8;
		int int5 = 5;
		Object address0 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int8, int5);
		Object residentialBuilding0 = Class.forName(residentialBuildingPath).getConstructor(Class.forName(addressPath))
				.newInstance(address0);
		Object gasLeak0 = Class.forName(gasLeakPath).getConstructor(int.class, Class.forName(residentialBuildingPath))
				.newInstance(int9, residentialBuilding0);
		String[] names = { "startCycle", "target", "active" };
		Object[] values = { 9, residentialBuilding0, false };
		testConstructorInitialization(gasLeak0, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorInfectionConstructor0Initialization() throws Exception {
		int int4 = 4;
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		Object infection0 = Class.forName(infectionPath).getConstructor(int.class, Class.forName(citizenPath))
				.newInstance(int4, citizen0);
		String[] names = { "startCycle", "target", "active" };
		Object[] values = { 4, citizen0, false };
		testConstructorInitialization(infection0, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorInjuryConstructor0Initialization() throws Exception {
		int int6 = 6;
		int int0 = 0;
		int int9 = 9;
		Object address1 = Class.forName(addressPath).getConstructor(int.class, int.class).newInstance(int0, int9);
		String String7 = "izw";
		Object citizen0 = Class.forName(citizenPath)
				.getConstructor(Class.forName(addressPath), String.class, String.class, int.class)
				.newInstance(address1, String7, String7, int9);
		Object injury0 = Class.forName(injuryPath).getConstructor(int.class, Class.forName(citizenPath))
				.newInstance(int6, citizen0);
		String[] names = { "startCycle", "target", "active" };
		Object[] values = { 6, citizen0, false };
		testConstructorInitialization(injury0, names, values);
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

	@SuppressWarnings("resource")
	private static void copyFile(File sourceFile, File destFile) throws IOException {

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

	// //////////////

	// ############################################# Helper methods

	public void testEnumValues(Class aClass, String[] value) throws ClassNotFoundException {
		for (int i = 0; i < value.length; i++) {
			try {
				Enum.valueOf((Class<Enum>) aClass, value[i]);
			} catch (IllegalArgumentException e) {
				fail(aClass.getSimpleName() + " enum can be " + value[i]);
			}
		}
	}

	private void testInstanceVariableIsPresent(Class aClass, String varName, boolean implementedVar)
			throws SecurityException {
		boolean thrown = false;
		try {
			aClass.getDeclaredField(varName);
		} catch (NoSuchFieldException e) {
			thrown = true;
		}
		if (implementedVar) {
			assertFalse(
					"There should be \"" + varName + "\" instance variable in class " + aClass.getSimpleName() + ".",
					thrown);
		} else {
			assertTrue("The instance variable \"" + varName + "\" should not be declared in class "
					+ aClass.getSimpleName() + ".", thrown);
		}
	}

	private void testInstanceVariableIsPrivate(Class aClass, String varName)
			throws NoSuchFieldException, SecurityException {
		Field f = aClass.getDeclaredField(varName);
		assertEquals("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
				+ " should not be accessed outside that class.", 2, f.getModifiers());
	}

	private void testGetterMethodExistsInClass(Class aClass, String methodName, Class returnedType,
			boolean writeVariable) {
		Method m = null;
		boolean found = true;
		try {
			m = aClass.getDeclaredMethod(methodName);
		} catch (Exception e) {
			found = false;
		}
		String varName = "";
		if (returnedType == boolean.class)
			varName = methodName.substring(2).toLowerCase();
		else
			varName = methodName.substring(3).toLowerCase();
		if (writeVariable) {
			assertTrue("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
					+ " is a READ variable.", found);
			assertTrue("Incorrect return type for " + methodName + " method in " + aClass.getSimpleName() + " class.",
					m.getReturnType().isAssignableFrom(returnedType));
		} else {
			assertFalse("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
					+ " is not a READ variable.", found);
		}
	}

	private void testSetterMethodExistsInClass(Class aClass, String methodName, Class inputType,
			boolean writeVariable) {
		Method[] methods = aClass.getDeclaredMethods();
		String varName = methodName.substring(3).toLowerCase();
		if (writeVariable) {
			assertTrue("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
					+ " is a WRITE variable.", containsMethodName(methods, methodName));
		} else {
			assertFalse("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
					+ " is not a WRITE variable.", containsMethodName(methods, methodName));
			return;
		}
		Method m = null;
		boolean found = true;
		try {
			m = aClass.getDeclaredMethod(methodName, inputType);
		} catch (NoSuchMethodException e) {
			found = false;
		}
		assertTrue(aClass.getSimpleName() + " class should have " + methodName + " method that takes one "
				+ inputType.getSimpleName() + " parameter.", found);
		assertTrue("Incorrect return type for " + methodName + " method in " + aClass.getSimpleName() + ".",
				m.getReturnType().equals(Void.TYPE));
	}

	private static boolean containsMethodName(Method[] methods, String name) {
		for (Method method : methods) {
			if (method.getName().equals(name))
				return true;
		}
		return false;
	}

	private void testConstructorExists(Class aClass, Class[] inputs) {
		boolean thrown = false;
		try {
			aClass.getConstructor(inputs);
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		if (inputs.length > 0) {
			String msg = "";
			int i = 0;
			do {
				msg += inputs[i].getSimpleName() + " and ";
				i++;
			} while (i < inputs.length);
			msg = msg.substring(0, msg.length() - 4);
			assertFalse("Missing constructor with " + msg + " parameter" + (inputs.length > 1 ? "s" : "") + " in "
					+ aClass.getSimpleName() + " class.", thrown);
		} else
			assertFalse("Missing constructor with zero parameters in " + aClass.getSimpleName() + " class.", thrown);
	}

	private void testClassIsAbstract(Class aClass) {
		assertTrue("You should not be able to create new instances from " + aClass.getSimpleName() + " class.",
				Modifier.isAbstract(aClass.getModifiers()));
	}

	private void testIsInterface(Class aClass) {
		assertEquals(aClass.getName() + " should be an Interface", aClass.isInterface(), true);
	}

	private void testIsEnum(Class aClass) {
		assertEquals(aClass.getName() + " should be an Enum", aClass.isEnum(), true);
	}

	private void testClassIsSubclass(Class subClass, Class superClass) {
		assertEquals(subClass.getSimpleName() + " class should be a subclass from " + superClass.getSimpleName() + ".",
				superClass, subClass.getSuperclass());
	}

	private void testConstructorInitialization(Object createdObject, String[] names, Object[] values)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException {
		for (int i = 0; i < names.length; i++) {
			Field f = null;
			Class curr = createdObject.getClass();
			String currName = names[i];
			Object currValue = values[i];
			while (f == null) {
				if (curr == Object.class)
					fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
							+ currName + "\".");
				try {
					f = curr.getDeclaredField(currName);
				} catch (NoSuchFieldException e) {
					curr = curr.getSuperclass();
				}
			}
			f.setAccessible(true);
			assertEquals(
					"The constructor of the " + createdObject.getClass().getSimpleName()
							+ " class should initialize the instance variable \"" + currName + "\" correctly.",
					currValue, f.get(createdObject));
		}
	}

	private void testGetterLogic(Object createdObject, String name, Object value) throws Exception {
		Field f = null;
		Class curr = createdObject.getClass();
		while (f == null) {
			if (curr == Object.class)
				fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
						+ name + "\".");
			try {
				f = curr.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
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
		assertEquals(
				"The method \"" + methodName + "\" in class " + createdObject.getClass().getSimpleName()
						+ " should return the correct value of variable \"" + name + "\".",
				value, m.invoke(createdObject));
	}

	private void testSetterLogic(Object createdObject, String name, Object value, Class type) throws Exception {
		Field f = null;
		Class curr = createdObject.getClass();
		while (f == null) {

			if (curr == Object.class)
				fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
						+ name + "\".");
			try {
				f = curr.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				curr = curr.getSuperclass();
			}
		}
		f.setAccessible(true);
		Character c = name.charAt(0);
		String methodName = "set" + Character.toUpperCase(c) + name.substring(1, name.length());
		Method m = createdObject.getClass().getMethod(methodName, type);
		m.invoke(createdObject, value);
		assertEquals("The method \"" + methodName + "\" in class " + createdObject.getClass().getSimpleName()
				+ " should set the correct value of variable \"" + name + "\".", value, f.get(createdObject));
	}

	private void testClassImplementsInterface(Class aClass, Class interfaceName) {
		Class[] interfaces = aClass.getInterfaces();
		boolean implement = false;
		for (Class i : interfaces) {
			if (i.toString().equals(interfaceName.toString()))
				implement = true;
		}
		assertTrue(aClass.getSimpleName() + " class should implement " + interfaceName.getSimpleName() + " interface.",
				implement);
	}
}