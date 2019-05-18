# Github-Trending

## Idea of the App 
A small app that will list the most starred Github repos that were created in the last 30 days. 
App is getting sorted data directly from the Github API 

## Features
* As a User I should be able to list the most starred Github repos that were created in the last 30 days. 
* As a User I should see the results as a list. One repository per row. 
* As a User I should be able to see for each repo/row the following details :
  * Repository name
  * Repository description 
  * Numbers of stars for the repo. 
  * Username and avatar of the owner. 
* As a User I should be able to keep scrolling and new results should appear (pagination).

## How to get the data from Github 
To get the most starred Github repos created in the last 30 days (relative to 2017-11-22), you'll need to call the following endpoint : 

`https://api.github.com/search/repositories?q=created:>2017-10-22&sort=stars&order=desc`

The JSON data from Github will be paginated (you'll receive around 100 repos per JSON page). 

To get the 2nd page, you add `&page=2` to the end of your API request : 

`https://api.github.com/search/repositories?q=created:>2017-10-22&sort=stars&order=desc&page=2`

To get the 3rd page, you add `&page=3` ... etc

You can read more about the Github API over [here](https://developer.github.com/v3/search/#search-repositories
).

## Mockups
![alt text](https://raw.githubusercontent.com/hiddenfounders/mobile-coding-challenge/master/mockup.png)

Here's what each element represents : 

![alt text](https://raw.githubusercontent.com/hiddenfounders/mobile-coding-challenge/master/row-explained.png)


 ## Installation
Clone this repository and import into **Android Studio**
```bash
git clone https://github.com/hiddenfounders/mobile-coding-challenge.git
```

 ## Built With
* Android Studio: 3.3
* Gradle: 2.33
* Kotlin: 1.3.21
* Androidx
* Glide


