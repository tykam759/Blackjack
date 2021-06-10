
public class Dealer extends Player {

	
	
	public void displayFirstCard()
	{
		System.out.println("Dealer: X" + getHands().get(0).getCards().get(1).getName());
	}
	
	public void displayHand()
	{
		System.out.println("Dealer: " + getHands().get(0).getCardNames() + " Value:" + getHands().get(0).getCardValue());
	}
	
	public Hand getHand()
	{
		return getHands().get(0);
	}
}
