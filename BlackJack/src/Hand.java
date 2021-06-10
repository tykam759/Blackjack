import java.util.ArrayList;

public class Hand {
	private ArrayList<Card> cards = new ArrayList<Card>();
	private boolean bust = false;
	private int cardValue = 0;
	private int betAmount = 0;
	
	public Hand(){}
	
	//
	//get/set methods
	public int getCardValue()
	{
		return cardValue;
	}
	
	public void setCardValue(int v)
	{
		cardValue = v;
	}
	
	public boolean getBust()
	{
		return bust;
	}
	
	public void setBust(boolean b)
	{
		bust = b;
	}
	
	public ArrayList<Card> getCards()
	{
		return cards;
	}
	
	public void setCards(ArrayList<Card> card)
	{
		cards = card;
	}
	
	public int getBetAmount()
	{
		return betAmount;
	}
	
	public void setBetAmount(int amount)
	{
		betAmount = amount;
	}
	//
	//
	
	//returns all card names in a string
	public String getCardNames()
	{
		String cardNames = "";
		for(int i = 0; i < cards.size(); ++i)
		{
			cardNames += cards.get(i).getName();
		}
		return cardNames;
	}
	
	//adds card to the hand
	public void addCard(Card card)
	{
		cards.add(card);
		cardValue += card.getValue();
	}
	
	//removes card from the hand
	public void removeCard(int index)
	{
		cardValue -= cards.get(index).getValue();
		cards.remove(index);
	}
	

	//checks if the hand is bust
	//if there is ace in the hand it changes it's value from 11 to 1
	public void checkBust()
	{
		if(cardValue > 21)
		{
			bust = true;
			for(Card c : cards)
			{
				if(c.getValue() == 11)
				{
					c.setValue(1);
					cardValue -= 10;
					bust = false;
				}
			}
		}
	}
	
}
