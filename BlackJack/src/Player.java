import java.util.ArrayList;

public class Player {

	protected ArrayList<Hand> hands = new ArrayList<Hand>();

	Player()
	{
		hands.add(new Hand());
	}
	
	
	public ArrayList<Hand> getHands()
	{
		return hands;
	}
	
	public void resetHand()
	{
		hands = new ArrayList<Hand>();
		hands.add(new Hand());
	}
}
