package calculators;

public class Triangle
{
    private double sideA;
    private double sideB;

    public Triangle()
    {
		this.sideA = 0.0;
		this.sideB = 0.0;
	}

    public String getSideA()
    {
		return Double.toString(this.sideA);
	}

	public void setSideA(String value)
	{
		try
		{
			this.sideA = Double.parseDouble(value);
		}
		catch (Exception e)
		{
			this.sideA = 0.0;
		}
	}

    public String getSideB()
    {
		return Double.toString(this.sideB);
	}

	public void setSideB(String value)
	{
		try
		{
			this.sideB = Double.parseDouble(value);
		}
		catch (Exception e)
		{
			this.sideB = 0.0;
		}
	}

	public String getSideC()
	{
		if (sideA == 0.0 || sideB == 0.0)
		    return "Please enter both sides.";
		else
		{
			Double sideC;
			sideC = Math.sqrt((sideA * sideA) + (sideB * sideB));
			return Double.toString(sideC);
		}
	}

}
