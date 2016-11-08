package assignment5;

import java.util.Iterator;
import java.util.List;

import assignment5.Critter;
import assignment5.InvalidCritterException;
import assignment5.Params;

public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	private boolean moved = false;	//moved or not
	private static boolean running = false;	//coming from run method or not
	private static boolean fighter = false;	//coming from doEncounters method or not
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	protected String look(int direction, boolean steps) {
		energy -= Params.look_energy_cost;
		int origx = x_coord;
		int origy = y_coord;
		int var = 1;	//if 1 step
		if(steps)		//if 2 steps
			var = 2;
		for(int i = 0; i < var; i++){
		switch (direction){
		case 0: x_coord++;
			if(x_coord >= 0)
				x_coord %= Params.world_width;
			else
				x_coord += Params.world_width;
			break;
		case 1: x_coord++; y_coord--;
			if(x_coord >= 0)
				x_coord %= Params.world_width;
			else
				x_coord += Params.world_width;
			if(y_coord >= 0)
				y_coord %= Params.world_height;
			else
				y_coord += Params.world_height;
			break;
		case 2: y_coord--;
			if(y_coord >= 0)
				y_coord %= Params.world_height;
			else
				y_coord += Params.world_height;
			break;
		case 3: x_coord--; y_coord--;
			if(x_coord >= 0)
				x_coord %= Params.world_width;
			else
				x_coord += Params.world_width;
			if(y_coord >= 0)
				y_coord %= Params.world_height;
			else
				y_coord += Params.world_height;
			break;
		case 4: x_coord--;
			if(x_coord >= 0)
				x_coord %= Params.world_width;
			else
				x_coord += Params.world_width;
			break;
		case 5: x_coord--; y_coord++;
			if(x_coord >= 0)
				x_coord %= Params.world_width;
			else
				x_coord += Params.world_width;
			if(y_coord >= 0)
				y_coord %= Params.world_height;
			else
				y_coord += Params.world_height;
			break;
		case 6: y_coord++;
			if(y_coord >= 0)
				y_coord %= Params.world_height;
			else
				y_coord += Params.world_height;
			break;
		case 7: x_coord++; y_coord++;
			if(x_coord >= 0)
				x_coord %= Params.world_width;
			else
				x_coord += Params.world_width;
			if(y_coord >= 0)
				y_coord %= Params.world_height;
			else
				y_coord += Params.world_height;
			break;
		}
		}
		for(int i = 0; i < population.size(); i++){
				if(population.get(i) != this){
					if(population.get(i).x_coord == this.x_coord && population.get(i).y_coord == this.y_coord){
						x_coord = origx;
						y_coord = origy;
						return population.get(i).toString();
					}
				}
		}
		x_coord = origx;
		y_coord = origy;
		return null;
		}
	
	/* rest is unchanged from Project 4 */
	
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	
	/**
	 * This method moves the critter in the specified direction one spot
	 * @param direction = specified direction
	 */
	protected final void walk(int direction) {
		if(!running){
			energy -= Params.walk_energy_cost;
			if(moved || energy < 0){
				if(energy < 0)
				energy = 0;
				return;
			}
			moved = true;
		}

		int origx = x_coord;
		int origy = y_coord;
		switch (direction){
		case 0: x_coord++;
			if(x_coord >= 0)
				x_coord %= Params.world_width;
			else
				x_coord += Params.world_width;
			break;
		case 1: x_coord++; y_coord--;
			if(x_coord >= 0)
				x_coord %= Params.world_width;
			else
				x_coord += Params.world_width;
			if(y_coord >= 0)
				y_coord %= Params.world_height;
			else
				y_coord += Params.world_height;
			break;
		case 2: y_coord--;
			if(y_coord >= 0)
				y_coord %= Params.world_height;
			else
				y_coord += Params.world_height;
			break;
		case 3: x_coord--; y_coord--;
			if(x_coord >= 0)
				x_coord %= Params.world_width;
			else
				x_coord += Params.world_width;
			if(y_coord >= 0)
				y_coord %= Params.world_height;
			else
				y_coord += Params.world_height;
			break;
		case 4: x_coord--;
			if(x_coord >= 0)
				x_coord %= Params.world_width;
			else
				x_coord += Params.world_width;
			break;
		case 5: x_coord--; y_coord++;
			if(x_coord >= 0)
				x_coord %= Params.world_width;
			else
				x_coord += Params.world_width;
			if(y_coord >= 0)
				y_coord %= Params.world_height;
			else
				y_coord += Params.world_height;
			break;
		case 6: y_coord++;
			if(y_coord >= 0)
				y_coord %= Params.world_height;
			else
				y_coord += Params.world_height;
			break;
		case 7: x_coord++; y_coord++;
			if(x_coord >= 0)
				x_coord %= Params.world_width;
			else
				x_coord += Params.world_width;
			if(y_coord >= 0)
				y_coord %= Params.world_height;
			else
				y_coord += Params.world_height;
			break;
		}
		if(fighter && !running){
			for(int i = 0; i < population.size(); i++){
				if(population.get(i) != this){
					if(population.get(i).x_coord == this.x_coord && population.get(i).y_coord == this.y_coord){
						x_coord = origx;
						y_coord = origy;
						break;
					}
				}
			}
		}
	}
	
	/**
	 * This method moves the critter in the specified direction two spots
	 * @param direction = specified direction
	 */
	protected final void run(int direction) {
		running = true;
		boolean on = false;
		if(fighter == true){	//accounts for second walk
			fighter = false;
			on = true;
		}
		
		energy -= Params.run_energy_cost;
		if(moved || energy < 0){
			if(energy < 0)
			energy = 0;
			running = false;
			return;
		}
		int origx = x_coord;
		int origy = y_coord;

		walk(direction);
		//moved = false;	//accounts for second walk
		walk(direction);
		if(on == true)
			fighter = true;
		running = false;
		moved = true;	//accts for if run already	
		if(fighter){
			for(int i = 0; i < population.size(); i++){
				if(population.get(i) != this){
					if(population.get(i).x_coord == this.x_coord && population.get(i).y_coord == this.y_coord){
						x_coord = origx;
						y_coord = origy;
						break;
					}
				}
			}
		}
	}
	
	/**
	 * This method reproduces the critter in the specified direction adjacent to the parent
	 * with the energy split in half between the parent and child
	 * @param direction = specified direction
	 * @param offspring = specified offspring
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if(this.energy < Params.min_reproduce_energy)
			return;
		
		boolean on = false;
		if(fighter == true){	//accounts for second walk
			fighter = false;
			on = true;
		}
		
		offspring.energy = this.energy/2;
		
		if(this.energy % 2 != 0)
			this.energy = this.energy/2 + 1;
		else
			this.energy = this.energy/2;
		
		offspring.x_coord = this.x_coord;
		offspring.y_coord = this.y_coord;
		
		offspring.walk(direction);
		offspring.energy+=Params.walk_energy_cost;
		
		if(on == true)
			fighter = true;
		
		babies.add(offspring);
		
	}
	
	public abstract void doTimeStep();
	public abstract boolean fight(String opponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name = empty string
	 * @throws InvalidCritterException = throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		
		//Critter newCritter;
		
		try{
			Critter newCritter = (Critter) Class.forName(myPackage + "." + critter_class_name).newInstance();
			
			newCritter.energy = Params.start_energy;
			
			newCritter.x_coord = getRandomInt(Params.world_width);
			newCritter.y_coord = getRandomInt(Params.world_height);
			
			population.add(newCritter);
			
		}
		catch (Exception e){
			
			throw new InvalidCritterException(critter_class_name);
			//InvalidCritterException(critter_class_name);

		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name = What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters
	 * @throws InvalidCritterException = throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		
		try{
			//Critter newCritter = (Critter) Class.forName(myPackage + "." + critter_class_name).newInstance();
			Critter newCritter = (Critter) Class.forName(myPackage + "." + critter_class_name).newInstance();
			
			for (Critter critter: population) {
				if (newCritter.getClass().isInstance(critter)) {
					result.add(critter);
				}
			}
			
		}
		
		catch (Exception e){
			
			throw new InvalidCritterException(critter_class_name);

		}
	
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		for (Iterator<Critter> it = population.iterator(); it.hasNext(); ) {
		    it.next();
		    it.remove();
		}
	}
	
	/**
	 * Time step all critters in population.
	 * Do encounters for all critters in same spot.
	 * Subtract rest energy for all the critters.
	 * Add algae.
	 * Remove dead critters.
	 * Move babies to population.
	 */
	public static void worldTimeStep() {
		
		for(Critter critter: population){
			critter.doTimeStep();
		}
		
		for(Critter critter: population){
			for(Critter i: population){
				if(critter.x_coord == i.x_coord && critter.y_coord == i.y_coord && critter != i){
					doEncounters(critter, i);
				}
			}
		}
		population.addAll(babies);
		for(Critter critter : population){
			if(critter.moved == true){
				critter.moved = false;
			}
			if(!babies.contains(critter))
			critter.energy -= Params.rest_energy_cost;
		}
		
		for(int i = 0; i < Params.refresh_algae_count; i++){
			try{
				makeCritter("Algae");
			}
			catch(InvalidCritterException e){
				e.toString();
			}
		}
		
		for (Iterator<Critter> it = population.iterator(); it.hasNext(); ) {
		    Critter critter = it.next();
		    if (critter.energy <= 0) {
		        it.remove();
		    }
		}

		babies.clear();
		
	}
	
	/**
	 * Displays the critters on the board
	 */
	public static void displayWorld() {		
		String[] topBorder = new String[Params.world_width];
		
		topBorder[0] = "+";
		topBorder[Params.world_width - 1] = "+";
		
		for(int i = 1; i < Params.world_width - 1; i++){
			topBorder[i] = "-";
		}
		
		String[][] world = new String[Params.world_height][Params.world_width];
		
		for(int i = 0; i < Params.world_height; i++){
			for(int j = 0; j < Params.world_width; j++){
				world[i][j] = " ";
			}
		}
		
		for(Critter critter : population){
			world[critter.y_coord][critter.x_coord] = critter.toString();
		}
		
		System.out.println();
		System.out.print("+");
		for(int j = 0; j < Params.world_width; j++){
			System.out.print("-");
		}
		System.out.println("+");
		
		for(int i = 0; i < Params.world_height; i++){
			
			System.out.print("|");
			for(int j = 0; j < Params.world_width; j++){
				System.out.print(world[i][j]);
			}
			System.out.println("|");
		}
		
		System.out.print("+");
		for(int j = 0; j < Params.world_width; j++){
			System.out.print("-");
		}
		System.out.println("+");
		
	}
	
	/**
	 * Run fights for critters when in same spot
	 * @param A = first critter to be checked
	 * @param B = second critter to be checked
	 */
	private static void doEncounters(Critter A, Critter B){
		int rollA = 0;
		int rollB = 0;
		int halfA = A.energy/2;	//ASK abt placement of this
		int halfB = B.energy/2;
		fighter = true;
		boolean Awants = A.fight(B.toString());
		boolean Bwants = B.fight(A.toString());
		
		if(A.energy > 0 && B.energy > 0){
		if(A.x_coord == B.x_coord && A.y_coord == B.y_coord){
			
			if(Awants){
				rollA = getRandomInt(A.energy);
			}
			if(Bwants){
				rollB = getRandomInt(B.energy);
			}
			if(rollA > rollB){
				B.energy = 0;
				A.energy += halfB;
			}
			else{
				A.energy = 0;
				B.energy += halfA;
			}
		}
		}
		else if(A.energy == 0){
			B.energy+=halfA;
		}
		else if(B.energy == 0){
			A.energy+=halfB;
		}
		fighter = false;
	}
	
}

