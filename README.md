# anybody-here-chat

Welcome to **_Anybody Here Chat_**, a study/portfolio project of a robust and scalable real-time chat application
designed to facilitate instant
communication between users. This project leverages modern web technologies to provide a seamless and responsive
chatting experience.

## Purpose

This project offers a reliable and efficient platform for users to connect and
communicate instantly. Whether it's for casual conversations or collaborative discussions, the system is designed
to handle multiple users and messages concurrently, ensuring that every message is delivered promptly and securely.

## Key Features

* Real-Time Messaging: Experience instant communication with low-latency message delivery.
* User Authentication: Secure login and registration processes to protect user data.
* Profile Management: Users can update their profile information and set their online status.
* Contact Management: Easily add, remove, and manage contacts within the application.
* Group Chats: Create and manage group conversations to facilitate teamwork and group interactions.
* Message History: Access past conversations with message history storage.
* Notifications: Receive real-time notifications for new messages and events.
* Security: Ensure message privacy with end-to-end encryption and secure authentication methods.

With scalability and performance in mind, using a microservices architecture that
allows
for easy maintenance and future enhancements. By utilizing WebSockets and STOMP protocols, the system ensures a stable
and efficient communication channel, providing a superior user experience. Additionally, the system's modular design
and
comprehensive documentation make it an excellent choice for developers looking to expand and customize the application
to meet specific needs.

We invite you to explore **_Anybody Here Chat_** and experience the ease and efficiency of real-time communication at
your
fingertips.

Feel free to fork this project and customize to your own purpose

## Functional Requirements

**1. Registration and Authentication:**

* User Registration: Users must be able to register with an email and password.
* Login: Users must be able to log in with their credentials.
* Password Recovery: Users must be able to recover their passwords via email.

**2. User Profile:**

* Profile Viewing: Users must be able to view and edit their profile information.
* Online/Offline Status: Users must be able to set their status (online, offline, busy).

**3. Contacts:**

* Add/Remove Contacts: Users must be able to add and remove contacts.
* Contact List: View the contact list with status (online/offline).

**4. Messaging:**

* Send Messages: Users must be able to send text messages to other users in real-time.
* Receive Messages: Users must receive messages instantly when sent by other users.
* Message History: Store and view message history with each contact.
* Notifications: Real-time notifications for new messages received.
* Group Messaging: Create and manage group chats.

## Non-Functional Requirements

**1. Performance:**

* Low Latency: The system must have low latency to ensure near-instantaneous message delivery.
* Scalability: The system must be scalable to support a large number of concurrent users.

**2. Reliability:**

* High Availability: The system must have high availability (uptime).
* Data Persistence: Data must be stored reliably and securely.

**3. Usability:**

* Intuitive Interface: The user interface must be intuitive and easy to use.
* Responsiveness: The application must be responsive and work well on different devices (desktop, mobile).

**4. Maintainability:**

* Modular Code: The system must be developed modularly to facilitate maintenance and the addition of new features.
* Documentation: Code and APIs must be well documented.

**5. Security:**

* Protection Against Attacks: The system must be protected against common attacks, such as SQL Injection, XSS, CSRF.
* Audit and Logs: There must be a logging system for auditing and monitoring.

## System Architecture

**1. Microservices:**

* Authentication and Authorization: Service responsible for user management, login, registration, and authentication.
* Contact Management: Service for adding, removing, and listing contacts.
* Messaging: Service responsible for sending, receiving, and storing messages.
* Notifications: Service for managing and sending notifications.
* Group Chat: Service for creating and managing group chats.

**2. Some Technologies:**

* Backend: Spring Boot for developing microservices in Java.
* Real-Time Communication: WebSockets or STOMP for real-time communication.
* Database: PostgreSQL for relational data and MongoDB for non-relational data (message history).
* Message Storage: Cassandra for temporary message storage.
* Authentication: JWT (JSON Web Tokens) for authentication.

## Basic System Flow

**1. Registration/Login:**

* User registers with email and password.
* User logs in with credentials.
* System validates credentials and generates a JWT token for authentication.

**2. Contact Management:**

* User adds/removes contacts.
* System updates the user's contact list.

**3. Sending Messages:**

* User selects a contact and sends a message.
* Message service sends the message in real-time via WebSockets.
* Message is stored in the database for history.

**4. Receiving Messages:**

* User receives real-time notifications for new messages.
* Messages are displayed in the user interface.

**5. Group Messaging:**

* User creates a group and adds contacts.
* Messages sent to the group are relayed to all group members.