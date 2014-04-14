
public enum DiscreteVariableType 
{


		N(0), K_avg(1), L(2), Tau(6), Averager(3), Averager_Irreducible(4), IrreducibleFile_Averager(5); // N=0, K_avg=1, L=2
		
			public final int id;
			DiscreteVariableType(int id)
			{
				this.id = id;
			}

	
}
