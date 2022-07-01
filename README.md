# StoreOnline
Online Shopping System (with REST API) in Java using Spring Boot, Hibernate, Maven, and MySQL.

One of the key advantages of REST APIs is that they provide a great deal of flexibility.
And online shopping is a form of electronic commerce which allows costumers to directly buy products 
from a seller over the Internet. 

In this application:
1.  User's authorization done by JSON Web Token (JWT). In this app jwt token generated in Spring Security by email and roles.
2.  Only in DB can be add role "Admin".
3.  Admin can create, modify, delete and get category, features, orders and products.
4.  Products have specifications so can be filtered by different categories, features and prices.
5.  Category has specification too and can be grouped to tree. 
6.  Customers can put products into Card and then form Order.
7.  Add Swagger for easy creating documentation and test REST API.
8.  Add FlyWay for migration and for easy way to work with DB.
