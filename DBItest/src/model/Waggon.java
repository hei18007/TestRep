package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Waggon implements Serializable
{
	private String nummer;
	private float eigengewicht;

	public Waggon(String nummer, float eigengewicht) throws RailwayException
	{
		setNummer(nummer);
		setEigengewicht(eigengewicht);
	}

	// ------------------------------- getter ---------------------
	public float getEigengewicht()
	{
		return eigengewicht;
	}

	public String getNummer()
	{
		return nummer;
	}

	// ------------------------------- setter ---------------------
	public void setEigengewicht(float eigengewicht) throws RailwayException
	{
		if (eigengewicht >= 8 && eigengewicht <= 100)
			this.eigengewicht = eigengewicht;
		else
			// System.out.println("Falscher Parameterwert für
			// setEigengewicht("+eigengewicht+") !!");
			throw new RailwayException("Falscher Parameterwert für setEigengewicht(" + eigengewicht + ") !!");
	}

	public void setNummer(String nummer) throws RailwayException
	{
		if (nummer != null && nummer.length() >= 5 && nummer.length() <= 10)
			this.nummer = nummer;
		else
			// System.out.println("Falscher Parameterwert für
			// setNummer("+nummer+") !!");
			throw new RailwayException("Falscher Parameterwert für setNummer(" + nummer + ") !!");
	}

	// ----------------------------- others --------------------------
	public int berechneAnzPersonen()
	{
		return 0;
	}

	public abstract float berechneGewicht();

	// ----------------------------- toStrings --------------------------
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(250);
		sb.append("Wagggon: ").append(getClass().getSimpleName()).append(" # ").append(nummer).append(" Eigengewicht: ")
				.append(eigengewicht);
		return sb.toString();
	}

	public String toStringCsv()
	{
		return new StringBuilder().append(getClass().getName()).append(";").append(nummer).append(";")
				.append(eigengewicht).toString();
	}

	// ----------------------------- Comparatoren
	// ------------------------------------------

}
