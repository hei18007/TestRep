package model;

@SuppressWarnings("serial")
public class Lokomotive extends Waggon
{
	private char antrieb;

	public Lokomotive(String nummer, float eigengewicht, char antrieb) throws RailwayException
	{
		super(nummer, eigengewicht);
		setAntrieb(antrieb);
	}

	// ----------------------------- getter -------------------
	public char getAntrieb()
	{
		return antrieb;
	}

	// ----------------------------- setter -------------------
	public void setAntrieb(char antrieb) throws RailwayException
	{
		if (antrieb == 'D' || antrieb == 'E' || antrieb == 'K' || antrieb == 'A' || antrieb == 'I' || antrieb == 'R'
				|| antrieb == 'B')
			this.antrieb = antrieb;
		else
			// System.out.println("Falscher Parameterwert für
			// setAntrieb("+antrieb+") !!");
			throw new RailwayException("Falscher Parameterwert für setAntrieb(" + antrieb + ") !!");
	}

	// ----------------------------- other -------------------
	@Override
	public float berechneGewicht()
	{
		return getEigengewicht();
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(500);
		sb.append(super.toString()).append(" Antrieb: ");
		switch (antrieb) {
		case 'D':
			sb.append("Diesel");
			break;
		case 'E':
			sb.append("Elektro");
			break;
		case 'K':
			sb.append("Kohle");
			break;
		case 'A':
			sb.append("Atomreaktor");
			break;
		case 'I':
			sb.append("Ionen");
			break;
		case 'R':
			sb.append("Rakete");
			break;
		case 'B':
			sb.append("Brennstoffzelle");
			break;
		}
		return sb.toString();
	}

	@Override
	public String toStringCsv()
	{
		return super.toStringCsv() + ";" + antrieb;
	}

}
