![Java CI](https://github.com/irvingywang/Group4-Team-Project/actions/workflows/action.yml/badge.svg)

# Various Instructions on Running YAP

## To test Server and Client Communication:
1. User runs the program

Expectation:
- "Server is listening on port 1234" display
- "Connected to client" display

If expectation occurs, test for server and client communication successfully passed.
If neither message appears, server and client communication has an error.

## To test Login:
1. User runs the program
2. User clicks Login button
3. User enters their Username
4. User enters their Password
5. User clicks Login button

Expectation:
- User is taken to the main menu page

If expectation occurs, test for Login successfully passed.
If not, Login has an error.

## To test Account Creation:
1. User runs the program
2. User clicks Create Account button
3. User enters desired Username
4. User enters desired Display Name
5. User enters desired Password
6. User clicks Create button

Expectation:
- User is taken to the main menu page

If expectation occurs, test for Account Creation successfully passed.
If not, Account Creation has an error.

## To test Main Menu functionality:
1. User runs the program
2. User logs in appropriately.
3. User clicks a button on the Main Menu page.
4. User clicks Back to Menu button.
5. User repeats steps 3-4 for each button, preferably starting from the top and going down.

Expectation:
- User is taken to the appropriate page of each button they click.

If expectation occurs, test for Main Menu functionality successfully passed.
If not, Main Menu functionality has an error.

## To test View Chats functionality:
1. User runs the program
2. User logs in appropriately.
3. User clicks View Chats button on the Main Menu page.
4. User selects a chat from the dropdown.
5. User clicks View Chat button.

Expectation:
- User is taken to the appropriate chat.

If expectation occurs, test for View Chats functionality successfully passed.
If not, View Chats functionality has an error.

## To test Create Chat functionality:
1. User runs the program
2. User logs in appropriately.
3. User clicks Create Chat button on the Main Menu page.
4. User enters a chat name.
5. User enters a member.
6. User clicks Create Chat button

Expectation:
- User is taken to the message-sending page to begin the chat.

If expectation occurs, test for Create Chat functionality successfully passed.
If not, Create Chat functionality has an error.

## To test message-sending functionality:
1. User runs the program
2. User logs in appropriately.
3. Method 1 - steps 4-7
4. User clicks View Chats button on the Main Menu page.
5. User selects a chat from the dropdown.
6. User clicks View Chat button.
7. User sends a message.
8. Method 2 - steps 9-12
9. User clicks Create Chat button on the Main Menu page.
10. User enters a chat name.
11. User enters a member.
12. User clicks Create Chat button
13. User sends a message.

Expectation:
- User can send a message through either page. 

If expectation occurs, test for message-sending functionality successfully passed.
If not, message-sending functionality has an error.

## To test Search Users functionality:
1. User runs the program
2. User logs in appropriately.
3. User clicks Search Users button on the Main Menu page.
4. User enters a valid user name.
5. User clicks Search button.

Expectation:
- User is taken to the profile of the user searched.

If expectation occurs, test for Search Users functionality successfully passed.
If not, Search Users functionality has an error.

## To test Add Friend functionality:
1. User runs the program
2. User logs in appropriately.
3. User clicks Search Users button on the Main Menu page.
4. User enters a valid user name.
5. User clicks Search button.
6. User clicks Add Friend button.

Expectation:
- Message is displayed saying friend is added.

If expectation occurs, test for Add Friend functionality successfully passed.
If not, Add Friend functionality has an error.

## To test Block User functionality:
1. User runs the program
2. User logs in appropriately.
3. User clicks Search Users button on the Main Menu page.
4. User enters a valid user name.
5. User clicks Search button.
6. User clicks Block User button.

Expectation:
- Message is displayed saying user is blocked.

If expectation occurs, test for Block User functionality successfully passed.
If not, Block User functionality has an error.

## To test Edit Profile functionality:
1. User runs the program
2. User logs in appropriately.
3. User clicks Edit Profile button on the Main Menu page.
4. User enters a display name.
5. User enters a user name.
6. User enters a password.
7. User selects account to be private/public.
8. User clicks Save button.
9. User clicks Back to Menu button.
10. User clicks Log Out button.
11. User logs in with newly changed credentials.

Expectation:
- User is able to log in with new credentials.

If expectation occurs, test for Edit Profile functionality successfully passed.
If not, Edit Profile functionality has an error.

## To test Log Out functionality:
1. User runs the program
2. User logs in appropriately.
3. User clicks Log Out button on the Main Menu page.

Expectation:
- User is taken to the welcome page.

If expectation occurs, test for Log Out functionality successfully passed.
If not, Log Out functionality has an error.