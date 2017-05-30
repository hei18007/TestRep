package model;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class Zug
{
	private String name;
	private LinkedList<Waggon> waggons;

	public Zug(String name) throws RailwayException
	{
		setName(name);
		waggons = new LinkedList<Waggon>();
	}

	// ------------------------------ setter ----------------------------
	public void setName(String name) throws RailwayException
	{
		if (name != null && name.length() > 5 && !name.isEmpty())
			this.name = name;
		else
			// System.out.println("Falscher Parameter für Zug.setName(" + name +
			// ") !!!");
			throw new RailwayException("Falscher Parameter für Zug.setName(" + name + ") !!!");
	}

	// ---------------------------- adders ---------------------------------

	public void lVor(Lokomotive lok)
	{
		if (lok != null)
		{
			if (waggons.isEmpty())
			{
				if (!(waggons.getFirst() instanceof Lokomotive))
				{
					waggons.addFirst(lok);
				}
			}
			else
				waggons.add(lok);
		}

	}

	public void lokVorsapannen(Lokomotive lok) throws RailwayException
	{
		if (lok != null)
		{
			if (!waggons.isEmpty())
			{
				if (!(waggons.getFirst() instanceof Lokomotive))
				{
					waggons.addFirst(lok);
				}
				else
					throw new RailwayException("Bereits eine Lok vorgespannt");
			}
			else
				waggons.add(lok);
		}
		else
			throw new RailwayException("Keine Lokomotive übergeben zum Vorspannen");
	}

	public int rmWL(int pos)
	{
		int anz = 0;

		for (Iterator<Waggon> i = waggons.iterator(); i.hasNext();)
		{
			Waggon waggon = i.next();
			if (waggon instanceof Personenwaggon)
			{

				if (waggon.berechneAnzPersonen() == 0)
				{
					i.remove();
				}
			}
		}
		return anz;
	}

	public void anhaengenWaggon(Waggon waggon) throws RailwayException
	{
		if (waggon != null)
			if (!waggons.contains(waggon))
				if (waggons.size() < 100)
				{
					waggons.add(waggon);
				}
				else
					// System.out.println("Kein Platz mehr für zusätlichen
					// Waggon!!");
					throw new RailwayException("Kein Platz mehr für zusätlichen Waggon!!");
			else
				// System.out.println("Waggon " + waggon.getNummer() + " ist
				// schon am Zug!!");
				throw new RailwayException("Waggon  " + waggon.getNummer() + " ist schon am Zug!!");
		else
			// System.out.println("null-Referenz für addWaggon!!");
			throw new RailwayException("null-Referenz für addWaggon!!");
	}

	// ---------------------------- removers ---------------------------------
	public void removeWaggon(int pos) throws RailwayException
	{
		if (pos >= 0 && pos < waggons.size())
			waggons.remove(pos);
		else
			// System.out.println("Falsche Position für Zug.removeWaggon(" + pos
			// + ") !!");
			throw new RailwayException("Falsche Position für Zug.removeWaggon(" + pos + ") !!");
	}

	public int removeWaggonsLeer()
	{
		int anz = 0;

		for (Iterator<Waggon> iter = waggons.iterator(); iter.hasNext();)
		{
			Waggon w = iter.next();

			if (w instanceof Personenwaggon)
			{
				if (w.berechneAnzPersonen() == 0)
				{
					iter.remove();
					anz++;
				}
			}
		}
		return 0;
	}

	// ---------------------------- others ---------------------------------
	public float berechneGesGewicht()
	{
		float gewicht = 0f;
		for (Waggon w : waggons)
			gewicht += w.berechneGewicht();
		return gewicht;
	}

	public void sortWaggonsAnzPersonen()
	{
		Collections.sort(waggons, new Comparator<Waggon>()
		{

			@Override
			public int compare(Waggon o1, Waggon o2)
			{
				if (o2 instanceof Lokomotive)
					return 0;
				else
					return Integer.compare(o2.berechneAnzPersonen(), o1.berechneAnzPersonen());
			}

		});

	}

	public void sortWaggonsGewicht(boolean aufsteigend)
	{
		Collections.sort(waggons, new Comparator<Waggon>()
		{
			@Override
			public int compare(Waggon o1, Waggon o2)
			{
				if (aufsteigend)
					return Float.compare(o1.getEigengewicht(), o2.getEigengewicht());
				else
					return Float.compare(o2.getEigengewicht(), o1.getEigengewicht());
			}
		});

	}

	// ---------------------------- toStrings ---------------------------------
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(5000);
		sb.append(name).append('\n');
		int anz = 0;
		for (Waggon w : waggons)
			sb.append(++anz).append(". ").append(w.toString()).append('\n');
		return sb.toString();
	}

	public String toStringCsv()
	{
		StringBuilder sb = new StringBuilder(5000);
		sb.append(name);
		for (Waggon w : waggons)
			sb.append(";").append(w.toStringCsv()).append("\n\r");
		return sb.toString();
	}
	// ------------------------------ files ------------------------------

	public void speichern(String filename) throws RailwayException
	{
		if (filename != null)
		{
			try
			{
				FileOutputStream fos = new FileOutputStream(filename);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(waggons);
				oos.close();
			}
			catch (FileNotFoundException e)
			{
				throw new RailwayException("FileNotFoundException für Zug.speichern(" + filename + "); \n"
						+ "Fehlermeldung lautet:" + e.getMessage());
			}
			catch (IOException e)
			{
				throw new RailwayException("IOException für Zug.speichern(" + filename + ");\n)"
						+ "Fehlermeldung lautet:" + e.getMessage());
			}

		}
		else
			throw new RailwayException(
					"null-Reference für Zug.speichern(" + filename + ");\n)" + "Fehlermeldung lautet:");
	}

	@SuppressWarnings("unchecked")
	public void laden(String filename) throws RailwayException
	{

		if (filename != null)
		{
			try
			{
				FileInputStream fis = new FileInputStream(filename);
				ObjectInputStream ois = new ObjectInputStream(fis);
				waggons = (LinkedList<Waggon>) ois.readObject();
				ois.close();
			}
			catch (FileNotFoundException e)
			{
				throw new RailwayException("FileNotFoundException für Zug.laden(" + filename
						+ ") ; \n Fehlermeldung lautet:" + e.getMessage());
			}
			catch (IOException e)
			{
				throw new RailwayException(
						"IOException für Zug.laden(" + filename + ") ; \n Fehlermeldung lautet:" + e.getMessage());
			}
			catch (ClassNotFoundException e)
			{
				throw new RailwayException("ClassNotFoundException für Zug.laden(" + filename
						+ ") ; \n Fehlermeldung lautet:" + e.getMessage());
			}
		}
		else
			throw new RailwayException("null Ref. für Zug.speichern(" + filename + ");\n)" + "Fehlermeldung lautet:");
	}

	public void exportieren(String filename) throws RailwayException
	{
		BufferedWriter bw;
		try
		{
			bw = new BufferedWriter(new FileWriter(filename));
			bw.write(toString());
			bw.close();
		}
		catch (IOException e)
		{
			throw new RailwayException(
					"IOException für Zug.exportieren(" + filename + ") ; \n Fehlermeldung lautete: " + e.getMessage());
		}

	}

	public void exportierenCsv(String filename) throws RailwayException
	{
		BufferedWriter bw;
		try
		{
			bw = new BufferedWriter(new FileWriter(filename));
			for (Waggon w : waggons)
			{
				bw.write(w.toStringCsv());
				// Christian sagt: bw.newLine();
				bw.write("\n");
			}
			// bw.write(toStringCsv());
			bw.close();
		}
		catch (IOException e)
		{
			throw new RailwayException(
					"IOException für Zug.exportieren(" + filename + ") ; \n Fehlermeldung lautete: " + e.getMessage());
		}

	}
}