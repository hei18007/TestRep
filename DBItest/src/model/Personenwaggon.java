package model;

@SuppressWarnings("serial")
public class Personenwaggon extends Waggon
{
	private int plaetzeMax;
	private int plaetzeFrei;

	public Personenwaggon(String nummer, float eigengewicht, int plaetzeMax) throws RailwayException
	{
		super(nummer, eigengewicht);
		setPlaetzeMax(plaetzeMax);
		setPlaetzeFrei(plaetzeMax);
	}

	// ----------------------------- getter -------------------
	public int getPlaetzeFrei()
	{
		return plaetzeFrei;
	}

	public int getPlaetzeMax()
	{
		return plaetzeMax;
	}

	// ----------------------------- setter -------------------
	public void setPlaetzeMax(int plaetzeMax) throws RailwayException
	{
		if (plaetzeMax > 0 && plaetzeMax <= 1000)
			this.plaetzeMax = plaetzeMax;
		else
			// System.out.println("Falscher Parameterwert f�r
			// setPlaetzeMax("+plaetzeMax+") !!");
			throw new RailwayException("Falscher Parameterwert f�r setPlaetzeMax(" + plaetzeMax + ") !!");
	}

	public void setPlaetzeFrei(int plaetzeFrei) throws RailwayException
	{
		if (plaetzeFrei > 0 && plaetzeFrei <= plaetzeMax)
			this.plaetzeFrei = plaetzeFrei;
		else
			// System.out.println("Falscher Parameterwert f�r
			// setPlaetzeFrei("+plaetzeFrei+") !!");
			throw new RailwayException("Falscher Parameterwert f�r setPlaetzeFrei(" + plaetzeFrei + ") !!");
	}

	// ----------------------------- Berechnungen -------------------
	@Override
	public int berechneAnzPersonen()
	{
		return plaetzeMax - plaetzeFrei;
	}

	@Override
	public float berechneGewicht()
	{
		return getEigengewicht() + berechneAnzPersonen() * 0.1f;
	}

	// ----------------------------- sonstige -------------------
	public void tickektsVerkaufen(int anz) throws RailwayException
	{
		if (anz > 0 && anz <= plaetzeFrei)
			plaetzeFrei -= anz;
		else
			// System.out.println("Falscher Parameterwert f�r
			// ticketsVerkaufen("+anz+")" +
			// "\nEs sind nur mehr "+plaetzeFrei+" Tickets verf�gbar !!");
			throw new RailwayException("Falscher Parameterwert f�r ticketsVerkaufen(" + anz + ")"
					+ "\nEs sind nur mehr " + getPlaetzeFrei() + " Tickets verf�gbar !!");
	}

	// ----------------------------- toStrings --------------------------
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(250).append(super.toString()).append(" Pers. belegt: ")
				.append(berechneAnzPersonen());
		return sb.toString();
	}

	@Override
	public String toStringCsv()
	{
		return super.toStringCsv() + ";" + plaetzeMax + ";" + plaetzeFrei;
	}

}
