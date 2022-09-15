# Bill Calculator Application
An application designed to monitor expenses and split bills between friends.

## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Screenshots](#screenshots)
* [Setup](#setup)
* [Project Status](#project-status)
* [Room for Improvement](#room-for-improvement)
* [Contact](#contact)


## General Information
The app is designed to easily monitor expenses and share invoices between friends. After adding 
users and invoices, the app presents the settlements taking into account of all bills. The calculator 
allows to settle selected products differently than the entire invoice. For example, 3 people pay for 
the bill, but there are two products for only one person on the bill. The app supports this situation
and takes it into account when billing.

During the development and implementation I used information on the following pages:
https://material.io/design
https://developer.android.com/guide

## Technologies Used
- Material Design 1.5.0
- Kotlin 1.7.0
- Navigation Graph 2.5.1
- ViewBinding
- Room database 2.4.1
- Kotlin corutines 1.5.2
- Reactive programming RxJava2
- CameraX 1.1.0-beta01
- ViewModel 2.5.0
- LiveData 2.5.0
- RecyclerView 1.2.1



## Features
List the ready features here:
* Models class
* Database schema
* User CRUD
* Bill, Product and Photo CRUD (during development)
* Settlements (TODO)
* Settlement details (TODO)
 

## Screenshots
Below some application screens
### Application main screens

<p align="center">
	<img src="./screenshot/user_list.png" width="250" >
</p>
<p align="center">
	<img src="./screenshot/bill_list.png" width="250" >
</p>


### Overview of application screens

<p align="center">
	<img src="./screenshot/edit_user_screen.png" width="250" >
	<img src="./screenshot/add_bill_info.png" width="250" >
	<img src="./screenshot/product_list.png" width="250" >
</p>

## Setup
1. Download the samples by cloning this repository
2. In the welcome screen of Android Studio, select "Open an Existing project"
3. Select one of the sample directories from this repository

Alternatively, use the `gradlew build` command to build the project directly
 

## Project Status
Project is: _during development_


## Room for Improvement
To do:
* Add external database
* Add Account CRUD
* Add settlements screen
* Add DrawerLayout

Improvement:
* Improve Multithreading
* Perform more operations in the background
* Colour palette
* Place all dimensions in dimens.xml

## Contact
Created by [@HKonstanty](https://github.com/HKonstanty/HKonstanty) - feel free to contact me!
