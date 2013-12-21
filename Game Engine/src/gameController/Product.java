package gameController;

import java.util.ArrayList;
import java.util.Vector;

public class Product {


	private ManuscriptCreator mc;

	private Vector<ManuscriptCreator> productVector;//stores released manuscripts created
	private Vector<ManuscriptCreator> unreleasedProductVector; // stores unreleased manuscripts
	private Vector<Character> employeeVector; // stores list of employees


	public void addEmployee(Character character){
		employeeVector.add(character);
	}

	public void fireEmployee(Character character){
		character.fireEmployee();
		employeeVector.remove(character);
	}


	public void createNewArrays(){
		//to be removed by the garbage collector
		productVector = null;
		unreleasedProductVector = null;
		employeeVector = null;

		productVector = new Vector<ManuscriptCreator>();
		unreleasedProductVector = new Vector<ManuscriptCreator>();
		employeeVector = new Vector<Character>();
	}


	public void saveManuscript(ManuscriptCreator mc){
		//adds manuscripts to array
		this.mc = mc;
		unreleasedProductVector.add(mc);
	}

	public void releaseManuscript(ManuscriptCreator mc){
		this.mc = mc;
		mc.setIsOnSale(true);
		productVector.add(mc);	
	}

	public ManuscriptCreator getReleasedManuscript(int index){
		return productVector.get(index);
	}

	public ManuscriptCreator getUnreleasedManuscript(int index){
		return unreleasedProductVector.get(index);
	}

	public int getProductVectorSize(){
		return productVector.size();
	}

	public int getUnreleasedProductVectorSize(){
		return unreleasedProductVector.size();
	}
	
	public int getEmployeeVectorSize(){
		return employeeVector.size();
	}
	
	public Character getEmployee(int index){
		return employeeVector.get(index);
	}

}
