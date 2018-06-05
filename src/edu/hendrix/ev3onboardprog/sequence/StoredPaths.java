package edu.hendrix.ev3onboardprog.sequence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import edu.hendrix.ev3onboardprog.StringList;

public class StoredPaths implements StringList<Program> {
	private ArrayList<Program> paths = new ArrayList<>();
	
	public static final String FILENAME = "paths.txt";
	
	public StoredPaths() throws FileNotFoundException {
		File pathFile = new File(FILENAME);
		if (pathFile.exists()) {
			Scanner in = new Scanner(pathFile);
			while (in.hasNextLine()) {
				paths.add(Program.fromInstrString(in.nextLine()));
			}
			in.close();
		}
	}
	
	public void save() throws FileNotFoundException {
		PrintWriter fout = new PrintWriter(new File(FILENAME));
		fout.print(toString());
		fout.close();
	}
	
	public Program addNew() {
		Program p = Program.make();
		paths.add(p);
		return p;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Program path: paths) {
			sb.append(path.toString() + "\n");
		}
		return sb.toString();
	}
	
	public boolean isEmpty() {
		return paths.isEmpty();
	}

	@Override
	public int size() {
		return paths.size();
	}

	@Override
	public Program get(int i) {
		return paths.get(i);
	}
}
