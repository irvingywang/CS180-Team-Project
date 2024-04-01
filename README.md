![Java CI](https://github.com/irvingywang/Group4-Team-Project/actions/workflows/action.yml/badge.svg)

# CS180 Team Project: Social Media Platform
This repository contains the Java program for the project.

## Setup Guide
Clone the repository using the following command:
```bash
git clone https://github.com/irvingywang/CS180-Team-Project.git
```

## Submissions
- Phase 1: [Vocareum Submission]() - Irving Wang

## Class Descriptions
- `User`: Represents a user of the social media platform.
  - This class provides functionalities such as storing various information about a user, allowing
  users to add or block others, and send and receive messages from others.
  - The methods "testUserCreationAndMessaging", "testCreateUserAndRetrieve", "testMessageCreation",
  "testMarkMessageAsRead", "testSendMessage", "testAddAndRemoveFriend", "testBlockUser", 
  "testSendMessageToBlockedUser", "testUnblockUser", and "testGetUsername" 
  all verify that this class is functional. 
  - Overall, this class can be combined with the others to allow the user of the messaging system to
  perform the various crucial actions that define a social media platform.
- `Message`: Represents a message on the social media platform.
  - This class provides functionalities such as storing various information about a message and getting
  information about the sender, receiver, and even time.
  - The methods "testUserCreationAndMessaging", "testMessageCreation", "testMarkMessageAsRead", 
  "testSendMessage", "testSendMessageToBlockedUser", and "testGetMessages" all verify that this 
  class is functional.
  - Overall, this class can be combined with the others to allow the messages of the system to store
  and deliver information that users want to use.
- `Database`: Represents the database of the social media platform.
  - This class serves as a database management system for users, messages, and relationships. It provides
  functionalities to create, retrieve, update, and delete user data, as well as logging events and managing
  data persistence.
  - Its usage includes: - Creating a User: Use the createUser(username, password, displayName) method to 
  create a new user. If a user with the same username already exists, the system logs an event. - Retrieving
  a User: Call the getUser(username) method to retrieve a user object based on the provided username. If the
  user does not exist, a new user object is returned. - Removing a User: Use the removeUser(username) method
  to remove a user from the database. - Getting All Users: Call the getUsers() method to retrieve a list of
  all user objects stored in the database.
  - The methods "testUserCreationAndMessaging", "testCreateUserAndRetrieve", "testGetUserNotFound",
  and "setUp" all verify that this class is functional.
  - This is an integral part of our social media program as it stores all user information and distributes
  it accordingly.
- `UserInterface`: Interface for the User class.
  - This interface provides a framework for the User class.
  - As far as we know, interfaces cannot be tested. 
  - This interface is crucial for ensuring the proper functionality and implementation of User.
- `MessageInterface`: Interface for the Message class.
  - This interface provides a framework for the Message class.
  - As far as we know, interfaces cannot be tested.
  - This interface is crucial for ensuring the proper functionality and implementation of Message.
- `DatabaseInterface`: Interface for the Database class.
  - This interface provides a framework for the Database class.
  - As far as we know, interfaces cannot be tested.
  - This interface is crucial for ensuring the proper functionality and implementation of Database.
- `App`: Creates a framework for the messaging app by processing user input.
  - This class allows the user to interact with the program and decide their actions within the social
  media platform.
  - The methods "testUserCreationAndMessaging" and "setUp" verify that this class is functional.
  - Overall, this class can be combined with the others to provide them with user input and help the
  computer decide which other methods and classes need to be called.
- `Chat`: Represents a chat which has a list of members and messages.
  - This class provides functionalities such as storing various information about a chat, like the users
  and messages getting sent.
  - The methods "testSendMessage", "testSendMessageToBlockedUser", and "testGetMessages" all verify 
  that this class is functional.
  - Overall, this class can be combined with the others to allow messages to be received and sent
  simultaneously.
- `GroupChat`: Represents a group chat with a variable amount of members.
  - This class extends chat but also allows for the creation of a group.
  - The methods "testSendMessage", "testSendMessageToBlockedUser", and "testGetMessages" all verify
  that this class is functional.
  - Overall, this class can be combined with the others to allow the creation of a group chat by adding
  (or removing) users.
- `LocalTest`: Tests all the methods created.
  - This class allows for the testing of all other classes and methods, which is a crucial step to ensure
  the functionality of the entire social media platform.
  - This class does the testing itself, so it cannot be tested (as far as we know).
- `ChatInterface`: Interface for the chat class. 
  - This interface provides a framework for the Chat class.
  - As far as we know, interfaces cannot be tested.
  - This interface is crucial for ensuring the proper functionality and implementation of Chat.
- `GroupChatInterface`: Interface for the group chat class.
  - This interface provides a framework for the GroupChat class.
  - As far as we know, interfaces cannot be tested.
  - This interface is crucial for ensuring the proper functionality and implementation of GroupChat.
- `ClientInterface`: Interface for the client. 
  - This interface provides a framework for the client.
  - As far as we know, interfaces cannot be tested.
  - This interface is crucial for ensuring the proper functionality and implementation of the client.
- `ServerInterface`: Interface for the server.
  - This interface provides a framework for the server.
  - As far as we know, interfaces cannot be tested.
  - This interface is crucial for ensuring the proper functionality and implementation of the server.
- `GUIInterface`: Interface for the GUI.
  - This interface provides a framework for the GUI.
  - As far as we know, interfaces cannot be tested.
  - This interface is crucial for ensuring the proper functionality and implementation of the GUI.
- 'App':
  - Purpose is to act like a testing module in place of a functioning GUI.
  - Proper GUI capabilities will be implemented in Phase 2.

