# ObjectOrientedProject
This project will implement simple card game "Blackjack" using command line.

Rules:
•	The goal of blackjack is to beat the dealer's hand without going over 21.
•	Face cards are worth 10. Aces are worth 1 or 11, whichever makes a better hand.
•	Each player starts with two cards, one of the dealer's cards is hidden until the end.
•	To 'Hit' is to ask for another card. To 'Stand' is to hold your total and end your turn.
•	If you go over 21 you bust, and the dealer wins regardless of the dealer's hand.
•	If you are dealt 21 from the start (Ace & 10), you got a blackjack.
•	Blackjack usually means you win 1.5 the amount of your bet. Depends on the casino.
•	Dealer will hit until his/her cards total 17 or higher.
•	Doubling is like a hit, only the bet is doubled, and you only get one more card.
•	Split can be done when you have two of the same cards - the pair is split into two hands.
•	Splitting also doubles the bet because each new hand is worth the original bet.
•	You can only double/split on the first move, or first move of a hand created by a split.
•	You cannot play on two aces after they are split.
•	You can double on a hand resulting from a split, tripling, or quadrupling you bet.

Summary:
My main goal is to create a command line blackjack game. User will be provided with fixed amount of chips at the start of the game, 
which player will be able to bet freely (I plan to implement upper bet limit, not sure how much yet, but 1000 might be a good start). 
If the amount of chips reach 0, the player loses the game, as for the winning condition, there is not one. 
Therefore, game can go on forever if player has chips to bet (player will be given option, to leave the table). 
Another thing to consider is number of decks that will be played, as the counting cards might make the game too easy (I am thinking somewhere between 1-8 decks). 
During the bet, player will be able to choose from the available options (double down, split, hit, stand). 
The outcome will be determined based on the rules of the game. If the time will allow, I might consider adding few more options.
