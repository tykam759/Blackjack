import java.util.ArrayList;

public class User extends Player{

	private int credits;
	private int totalBetAmount = 0;
	private String name = "";
	
	User(int creds, String name)
	{
		credits = creds;
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	//get/set methods
	//
	public int getCredits()
	{
		return credits;
	}
	
	public void setCredits(int credits)
	{
		this.credits = credits;
	}
	
	public void setBetAmount(int amount)
	{
		totalBetAmount = amount;
	}
	
	public int getBetAmount()
	{
		return totalBetAmount;
	}
	//
	//
	
	//checks if hand can be split
	public boolean canSplit()
	{
		if(hands.get(0).getCards().size() == 2 && hands.size() < 2)
		{
			if(hands.get(0).getCards().get(0).getName() == hands.get(0).getCards().get(1).getName())
			{
				return true;
			}
		}
		return false;
	}
	
	public void bet(int betAmount)
	{
		if(credits >= betAmount && betAmount > 0)
		{
			hands.get(0).setBetAmount(betAmount); 
			totalBetAmount += betAmount;
		}
		else
		{
			System.out.println("Illegal bet");
		}
	}
	
	//distributes credits based on result of a game
	public void finalizeBet(int value)
	{
		for(Hand hand : hands)
		{
			if(hand.getCardValue() != value)
			{
				if(hand.getBust())
				{
					credits -= hand.getBetAmount();
				}
				else
				{
					credits += hand.getBetAmount();
				}
			}
		}
	}
	
	//adds another hand to the player
	public void addHand(Hand hd)
	{
		hands.add(hd);
	}
	
	@Override
	public void resetHand()
	{
		hands = new ArrayList<Hand>();
		hands.add(new Hand());
		totalBetAmount = 0;
	}
}
