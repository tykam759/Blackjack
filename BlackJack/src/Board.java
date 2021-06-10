import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class Board {

	private ArrayList<Card> cards = new ArrayList<Card>();
	private Stack<Card> deck = new Stack<Card>();
	private Scanner myScanner = new Scanner(System.in);
	
	public Board()
	{
		startNewGame();
		myScanner.close();
	}
	
	private void startNewGame()
	{
		ArrayList<User> players = new ArrayList<User>();
		setUpNewGame(players);
		Dealer dealer = new Dealer();
		
		boolean next = true;
		while(next && players.size() > 0)
		{
			Bet(players);
			initialDeal(players, dealer);
			
			for(User player : players)
			{
				System.out.println("Player: " + player.getName() + " turn");
				handlePlayerOptions(player, dealer);
				clearConsole();
			}
			dealerPlay(players, dealer);
			displayAllUserHands(players);
			payout(players, dealer);
			
			System.out.println("Do you want to continue? (y/n)");
			String choice = "n";
			try 
			{
				choice = myScanner.next();
				myScanner.nextLine();
			}
			catch(Exception e)
			{
				myScanner.nextLine();
			}
			//the game continues only if the user types y
			//otherwise the game will end
			if(choice.equals("y"))
			{
				resetBoard(players, dealer);
			}
			else
			{
				next = false;
			}
		}
		System.out.println("Game ended");
	}
	
	
	//
	//
	//player options (bet, double down etc)
	//
	//
	
	private void Bet(ArrayList<User> pls)
	{
		for(User player : pls)
		{
			System.out.println("Player: " + player.getName() +" turn");
			while(player.getBetAmount() == 0)
			{
				System.out.println("Your credits: " + player.getCredits());
				System.out.println("Place your bet: ");
				try
				{
					player.bet(myScanner.nextInt());
					myScanner.nextLine();
				}
				catch(Exception e)
				{
					System.out.println("Make sure that you enter an integer");
					myScanner.nextLine();
				}
			}
		}
		
	}
	
	//adds card to the player hand
	private void Hit(Hand hand)
	{
		if(deck.empty())
		{
			getNewDeck();
		}
		hand.addCard(deck.pop());
	}
	
	//Doubles the player's bet amount and adds card to the hand
	private void doubleDown(User us, Hand hand)
	{
		if(deck.empty())
		{
			getNewDeck();
		}
		hand.setBetAmount(hand.getBetAmount() << 1);
		us.setBetAmount(us.getBetAmount() + hand.getBetAmount());
		hand.addCard(deck.pop());
	}

	//splits player's hand in two and doubles the initial bet amount
	private void split(User us)
	{
		if(us.canSplit())
		{
			if(us.getCredits() >= us.getBetAmount() << 1)
			{
				us.addHand(new Hand());
				us.getHands().get(1).addCard(us.getHands().get(0).getCards().get(0));
				us.getHands().get(0).removeCard(1);
				us.getHands().get(1).setBetAmount(us.getBetAmount());
				us.setBetAmount(us.getBetAmount() << 1);
			}
			else
			{
				System.out.println("Insufficient credits to perform this action");
			}
		}
		else
		{
			System.out.println("You can't split these cards");
		}
	}
		

	//
	//
	//Display menu, hands
	//
	//
	private void displayMenu()
	{
		System.out.println("1. Hit");
		System.out.println("2. Double Down");
		System.out.println("3. Split");
		System.out.println("4. Stand");
	}
	
	//displays hand of a player
	private void displayPlayerHands(User player)
	{
		for(Hand hand : player.getHands())
		{
			System.out.println("Hand: " + hand.getCardNames() + " Value:" + hand.getCardValue() + " Bet:" + hand.getBetAmount());
		}
	}
	
	//displays hands of every player
	private void displayAllUserHands(ArrayList<User> pls)
	{
		for(User player : pls)
		{
			System.out.println("Player: " + player.getName());
			displayPlayerHands(player);
		}
	}
	
	private void clearConsole()
	{
		System.out.print("\033[H\033[2J");
	    System.out.flush();	
	    System.out.println();
	}
	
	//
	//
	//Game methods
	//
	//
	
	//handles player's turn
	//performs appropriate action based on player's choice
	private void handlePlayerOptions(User user, Dealer dealer)
	{
		for(int i = 0; i < user.getHands().size(); ++i)
		{
			int choice = 0;
			Hand h = user.getHands().get(i);
			while(choice != 4 && !h.getBust())
			{
				displayMenu();
				//Dealer has 2 initial cards, only 1 is shown and second one is covered
				dealer.displayFirstCard();
				displayPlayerHands(user);
				try 
				{
					choice = myScanner.nextInt();
					myScanner.nextLine();
				}
				catch(Exception e)
				{
					System.out.println("Please choose an option from the menu");
					myScanner.nextLine();
					choice = 0;
				}
				switch(choice)
				{
					case 1:
						Hit(h);
						break;
					
					case 2:
						if(user.getCredits() >= (user.getBetAmount() + h.getBetAmount() << 1))
						{
							doubleDown(user, h);
							choice = 4;
							displayPlayerHands(user);
						}
						else
						{
							System.out.println("Insufficient credits to perform this action");
						}
						break;
					
					case 3:
						split(user);
						break;
						
					default:
						break;
				}
				
				h.checkBust();
			}
		}
	}
	
	//if at least one player didn't go bust
	//dealer gets to play
	private void dealerPlay(ArrayList<User> pls, Dealer dealer)
	{
		dealer.displayHand();
		boolean play = false;
		for(User player : pls)
		{
			for(Hand hand : player.getHands())
			{
				if(!hand.getBust())
				{
					play = true;
				}
			}
		}
		if(play)
		{
			while(dealer.getHand().getCardValue() <= 16)
			{
				Hit(dealer.getHand());
				dealer.getHand().checkBust();
				dealer.displayHand();
			}
		}
	}
	
	//make it easier?
	//pays out credits based on result of the game
	private void payout(ArrayList<User> pls, Dealer dealer)
	{
		compareHands(pls, dealer);
		for(int i = pls.size() - 1; i >= 0; --i)
		{
			User player = pls.get(i);
			player.finalizeBet(dealer.getHand().getCardValue());
			if(player.getCredits() == 0)
			{
				System.out.println("Player: " + player.getName() + " was eliminated from the game due to insufficient funds");
				pls.remove(player);
			}
		}	
	}
	
	//checks if the hand lost against dealer
	private void compareHands(ArrayList<User> pls, Dealer dealer)
	{
		for(User player : pls)
		{
			for(Hand hand : player.getHands())
			{
				if(!dealer.getHand().getBust() && dealer.getHand().getCardValue() > hand.getCardValue())
				{
					hand.setBust(true);
				}
			}
		}
	}
	
	//resets board after round is finished
	private void resetBoard(ArrayList<User> pls, Dealer dealer)
	{
		for(User player : pls)
		{
			player.resetHand();
		}
		dealer.resetHand();
	}
	
	//creates new deck
	private void getNewDeck()
	{
		//repeat 32 times to get 8 decks
		//cards arraylist holds 13 unique cards (each deck has 4 same cards)
		for(int i = 0; i < 32; ++i)
		{
			//had to make it this way
			//it was major pain in the a, since it was creating shallow copy
			deck.add(new Card(11, "A"));
			deck.addAll(cards);
		}
		Collections.shuffle(deck);
	}
	
	private void setUpNewGame(ArrayList<User> pls)
	{
		int number = 0;
		while(number < 1 || number > 10)
		{
			System.out.println("Welcome to the BlackJack game");
			System.out.println("Choose number of players 1-10: ");
			try 
			{
				number = myScanner.nextInt();
				myScanner.nextLine();
			}
			catch(Exception e)
			{
				System.out.println("Please enter correct number of players");
				myScanner.nextLine();
			}
		}
		
		//create players
		for(int i = 1; i <= number; ++i)
		{
			pls.add(new User(1000, String.valueOf(i)));
		}

		
		//create cards
		for(int i = 2; i < 11; ++i)
		{
			cards.add(new Card(i, String.valueOf(i)));
		}
		cards.add(new Card(10, "J"));
		cards.add(new Card(10, "Q"));
		cards.add(new Card(10, "K"));

		//generate new deck (one deck has 52 cards)
		getNewDeck();
	}
	
	//Every game starts the same way
	//every player gets 2 cards etc
	private void initialDeal(ArrayList<User> pls, Dealer dealer)
	{
		//first the players get 1 card each then a dealer gets 1 card
		for(int j = 0; j < 2; ++j)
		{
			for(int i = pls.size() - 1; i >= 0; --i)
			{
					Hit(pls.get(i).getHands().get(0));
			}
			Hit(dealer.getHand());
		}
	}
		
}
