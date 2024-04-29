![build](https://github.com/irvingywang/CS180-Yap/actions/workflows/action.yml/badge.svg?branch=main&event=push&job=build)
![DatabaseTest](https://github.com/irvingywang/CS180-Yap/actions/workflows/action.yml/badge.svg?branch=main&event=push&job=DatabaseTest)
![NetworkTest](https://github.com/irvingywang/CS180-Yap/actions/workflows/action.yml/badge.svg?branch=main&event=push&job=NetworkTest)

# Yap: Chat with your friends. Instantly.
![Logo](design/Welcome.png)

## Setup Guide
Clone the repository using the following command:
```bash
git clone https://github.com/irvingywang/CS180-Team-Project.git
```
## How to Run
To run the program, simply run the main method of `App.java`. 
Try the login demo with username: `purduepete` and password: `123`.

## Submissions
- Phase 1: [Vocareum Submission]() - Irving Wang
- Phase 2: [Vocareum Submission]() - Irving Wang
- Phase 3: [Vocareum Submission]() - Irving Wang

# Class Descriptions
- `App`: Main class to run the program.
  - This class allows the user to interact with the program and decide their actions within the social
  - It will start a server and client, and then run the client GUI.

## Objects
- `UserInterface`: Interface for the User class.
  - This interface provides a framework for the User class.
  - This interface is crucial for ensuring the proper functionality and implementation of User.
- `User`: Represents a user of the social media platform.
  - This class provides functionalities such as storing various information about a user, allowing
  users to add or block others, and send and receive messages from others.
  - The methods "testUserCreationAndMessaging", "testCreateUserAndRetrieve", "testMessageCreation",
  "testMarkMessageAsRead", "testSendMessage", "testAddAndRemoveFriend", "testBlockUser", 
  "testSendMessageToBlockedUser", "testUnblockUser", and "testGetUsername" 
  all verify that this class is functional. 
  - Overall, this class can be combined with the others to allow the user of the messaging system to
  perform the various crucial actions that define a social media platform.
- `MessageInterface`: Interface for the Message class.
  - This interface provides a framework for the Message class.
  - This interface is crucial for ensuring the proper functionality and implementation of Message.
- `Message`: Represents a message on the social media platform.
  - This class provides functionalities such as storing various information about a message and getting
  information about the sender, receiver, and even time.
  - The methods "testUserCreationAndMessaging", "testMessageCreation", "testMarkMessageAsRead", 
  "testSendMessage", "testSendMessageToBlockedUser", and "testGetMessages" all verify that this 
  class is functional.
  - Overall, this class can be combined with the others to allow the messages of the system to store
  and deliver information that users want to use.
- `ChatInterface`: Interface for the chat class.
  - This interface provides a framework for the Chat class.
  - This interface is crucial for ensuring the proper functionality and implementation of Chat.
- `Chat`: Represents a chat which has a list of members and messages.
  - This class provides functionalities such as storing various information about a chat, like the users
  and messages getting sent.
  - The methods "testSendMessage", "testSendMessageToBlockedUser", and "testGetMessages" all verify 
  that this class is functional.
  - Overall, this class can be combined with the others to allow messages to be received and sent
  simultaneously.
- `InvalidChatException`: Exception for invalid chats.
  - This exception class is for handling invalid chats.
  - This class does not have to be tested by other to verify that it works.
  - Overall, this class is used within others in case an exception occurs with a chat.

## Database
- `DatabaseInterface`: Interface for the Database class.
  - This interface provides a framework for the Database class.
  - This interface is crucial for ensuring the proper functionality and implementation of 
- `Database`: Represents the database of the social media platform.
  - This class serves as a database management system for users, messages, and relationships. It provides
    functionalities to create, retrieve, update, and delete user data, as well as logging events and managing
    data persistence.
  - Its usage includes: - Creating a User: Use the createUser(username, password, displayName) method to
    create a new user. If a user with the same username already exists, the system logs an event. - Retrieving
    a User: Call the getUser(username) method to retrieve a user object based on the provided username. If the
    user does not exist, a new user object is returned. - Removing a User: Use the removeUser(username) method
    to remove a user from the  - Getting All Users: Call the getUsers() method to retrieve a list of
    all user Objects stored in the 
  - The methods "testUserCreationAndMessaging", "testCreateUserAndRetrieve", "testGetUserNotFound",
    and "setUp" all verify that this class is functional.
  - This is an integral part of our social media program as it stores all user information and distributes
    it accordingly.
- `DatabaseContainer`: Contains data to be used from database
  - This class serves as a collector for maintaining data, chats, and other user profile information
  - It's designed to be serialized, in order to better standardize the data collection of this app
- `LogType`: Enum to store the type of message.
  - This creates a new data type that stores the name of different messages that could be shown,
    such as info, error, and warning.
  - This program cannot really be tested because it does not perform any function.
  - Overall, this class is useful for augmenting the simplicity of our code.

## Networking
- `ClientInterface`: Interface for the client. 
  - This interface provides a framework for the client.
  - This interface is crucial for ensuring the proper functionality and implementation of the client.
- `ServerInterface`: Interface for the server.
  - This interface provides a framework for the server.
  - This interface is crucial for ensuring the proper functionality and implementation of the server.
- `Network`: Represents a client program.
  - This class provides functionalities related to sending and receiving data to and from the server.
  - Its functionality can be tested by running the server and the client and seeing that a connection
    is established. 
  - Overall, this class communicates with the server to provide an interface for the user to interact
    with the messaging app.
- `ClientCommand`: Enum to store the success/failure from the ServerCommand class.
  - This creates a new data type that stores whether a user-selected command was successful or not.
  - This program cannot really be tested because it does not perform any function.
  - Overall, this class is useful for augmenting the simplicity of our code.
- `NetworkMessage`: Creates a framework for a network message.
  - This class allows for the creation of a message that is to be sent between the client and
    server.
  - The methods "readMessage", "listenToServer", "sendToClient", and "handleClient" verify that
    this class is functional.
  - Overall, this class can be used within the server and client to simplify messaging.
- `Server`: Represents a server program.
  - This class provides functionalities related to sending and receiving data to and from the client.
  - Its functionality can be tested by running the server and the client and seeing that a connection
    is established.
  - Overall, this class communicates with the client to provide an interface for the user to interact
    with the messaging app. The server typically processes the requests from the client.
- `ServerCommand`: Enum to store commands the user may want to run.
  - This creates a new data type that stores the name of commands users may want to do.
  - This program cannot really be tested because it does not perform any function.
  - Overall, this class is useful for augmenting the simplicity of our code.
- `Identifier`: Enum to store parts of the program.
  - This creates a new data type that stores the name of different parts of the program, such as
    client, server, database, and user.
  - This program cannot really be tested because it does not perform any function.
  - Overall, this class is useful for augmenting the simplicity of our code.

## GUI
- `GUIConstants`: Contains important constants for our GUI.
  - This class provides information like colors, window dimensions, fonts, styling, etc. for our GUI.
  - Its functionality can be tested by running the program and matching the colors.
  - Overall, this class is used by others to make the GUI look uniform and appealing.
- `Window`: Provides a framework for a window.
  - This class provides information like size, a close operation, and page-switching function
    to our GUI windows.
  - Its functionality can be tested by running the program and trying different buttons that lead
    to new windows.
  - Overall, this class is used by others to make the GUI look uniform and appealing.
- `ImagePanel`: Provides a framework for an image panel.
  - This class further customizes our GUI and allows for improved rendering quality of all of
    its components.
  - Its functionality can be tested by comparing it to a Java Swing app without it and seeing
    the difference in the visual quality.
  - Overall, this class makes the GUI look uniform and appealing.
- `Panel`: Provides a framework for a panel.
  - This class provides functions like style, visibility, and color to our GUI panels.
  - Its functionality can be tested by making an empty panel and seeing that is black and uniform
    by default.
  - Overall, this class is used by others to make the GUI look uniform and appealing.
- `Button`: Provides a framework for a button.
  - This class provides functions like size, color, and hover effect to our GUI buttons.
  - Its functionality can be tested by running the program and clicking/hovering over different buttons.
  - Overall, this class is used by others to make the GUI look uniform and appealing.
- `TextField`: Provides a framework for a text field.
  - This class provides functions like size, color, and a focus effect to our GUI text fields.
  - Its functionality can be tested by running the program and trying to enter text where prompted.
  - Overall, this class is used by others to make the GUI look uniform and appealing.
- `Dropdown`: Provides a framework for a dropdown.
  - This class provides functions like size, color, and a floating arrow to our GUI dropdowns.
  - Its functionality can be tested by running the program and trying to use a dropdown (in View Chats).
  - Overall, this class is used by others to make the GUI look appealing.
- `Label`: Provides a framework for a label.
  - This class provides information like size, color, and a font to our GUI labels.
  - Its functionality can be tested by running the program and looking at the labels.
  - Overall, this class is used by others to make the GUI look uniform and appealing.
- `Spacer`: Provides a framework for a spacer.
  - This class provides information about the size of spaces between buttons in our GUI.
  - Its functionality can be tested by running the program and looking at the uniform spaces.
  - Overall, this class is used by others to make the GUI look uniform and appealing.
- `RoundedBorder`: Provides a framework for a rounded border.
  - This class provides a rounded border to our buttons in our GUI.
  - Its functionality can be tested by running the program and seeing the rounded borders.
  - Overall, this class is used by others to make the GUI look uniform and appealing.
- `Page`: Provides a framework for a page.
  - This class provides a blank page for our GUI.
  - Its functionality cannot really be tested because it is not used directly.
  - Overall, this class is used by others to make the GUI look uniform and appealing.
- `PageInterface`: Provides an interface for the page class.
  - This interface provides a framework for the pages.
  - This interface is crucial for ensuring the proper functionality and implementation of the pages.

## Pages
- `AllChatsPage`: Page for viewing all the chats.
  - This class allows the user to see all the chats they are part of.
  - Its functionality can be tested by following instructions in the GUI testing ReadME.
  - Overall, this class is made from the GUI Package and works with other pages to make our
    application comprehensive.
- `BlankPage`: Placeholder for all other Page structures.
  - This class makes it easier to copy and paste code to create other pages.
  - Its functionality is not very significant and cannot be tested.
- `ChatPage`: Page for sending a message.
  - This class allows the user to send a message to another user.
  - Its functionality can be tested by following instructions in the GUI testing ReadME.
  - Overall, this class is made from the GUI Package and works with other pages to make our
    application comprehensive.
- `CreateChatPage`: Page for creating a new chat.
  - This class allows the user to create a new chat.
  - Its functionality can be tested by following instructions in the GUI testing ReadME.
  - Overall, this class is made from the GUI Package and works with other pages to make our
    application comprehensive.
- `CreateUserPage`: Page for creating a new user.
  - This class allows the user to create a new account.
  - Its functionality can be tested by following instructions in the GUI testing ReadME.
  - Overall, this class is made from the GUI Package and works with other pages to make our
    application comprehensive.
- `EditProfilePage`: Page for editing profile.
  - This class allows the user to edit their profile and add what's on their mind.
  - Its functionality can be tested by following instructions in the GUI testing ReadME.
  - Overall, this class is made from the GUI Package and works with other pages to make our
    application comprehensive.
- `LoginPage`: Page for existing users to enter the program.
  - This class allows the user to log into their account.
  - Its functionality can be tested by following instructions in the GUI testing ReadME.
  - Overall, this class is made from the GUI Package and works with other pages to make our
    application comprehensive.
- `MainMenu`: Page for viewing all the possible functionalities of Yap.
  - This class allows the user to select any function they would like to complete.
  - Its functionality can be tested by following instructions in the GUI testing ReadME.
  - Overall, this class is made from the GUI Package and works with other pages to make our
    application comprehensive.
- `SearchResultsPage`: Page for search results.
  - This class allows the user to see users they searched for.
  - Its functionality can be tested by following instructions in the GUI testing ReadME.
  - Overall, this class is made from the GUI Package and works with other pages to make our
    application comprehensive.
- `SearchUsersPage`: Page for searching users.
  - This class allows the user to search for another user.
  - Its functionality can be tested by following instructions in the GUI testing ReadME.
  - Overall, this class is made from the GUI Package and works with other pages to make our
    application comprehensive.
- `ViewProfilePage`: Page for viewing another users profile.
  - This class allows the user to view another user's profile and add/block them.
  - Its functionality can be tested by following instructions in the GUI testing ReadME.
  - Overall, this class is made from the GUI Package and works with other pages to make our
    application comprehensive.
- `WelcomePage`: First page when the program is launched.
  - This class allows the user to log into their account or create one.
  - Its functionality can be tested by following instructions in the GUI testing ReadME.
  - Overall, this class is made from the GUI Package and works with other pages to make our
    application comprehensive.

## Testing
- `NetworkTest`: Tests all the network methods created.
  - This class allows for the testing of all network classes and methods, which is a crucial step to ensure
    the functionality of the server and client of the social media platform.
  - The test class obviously cannot be tested itself.
- `DatabaseTest`: Tests all the database methods created.
  - This class allows for the testing of all database classes and methods, which is a crucial step to ensure
    the functionality of the database of the social media platform.
  - The test class obviously cannot be tested itself.

# Design Specifications
Color Scheme:
![Colors](design/Colors.png)
