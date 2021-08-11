# EVE Chatbot

A simple chatbot which learns from:

* giving it answers to a specific topic you have entered before,
  e.g. entering first "Hi" followed by "How are you?", then "Fine".
  It will remember this constellation and will ask you on re-entering "Hi" "How are you?".
  If you ask it "How are you?" it will answer "Fine" or something else if you taught it more words on that question.
  
# Planed features:
* Every answer will have a priority (importance). 
  It will increase by repeating questions or entries as long as you didn't find the answer you want.
  This will make EVE response mostly correctly.
  Example: You say "Hi", EVE says "Bye". That's not what you want.
  You repeat it: You say "Hi" again, EVE says now "How are you?".
  That's what you want. Now you enter a different input, e.g. "Fine" and
  EVE will recognize you wanted to hear "How are you?" instead of "Bye".