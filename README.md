
# TAUS Man 
This is a simple slack bot intergrated with Jira to do some specific task. 
The bot is written in Kotlin, using some libraries such as
- Ktor https://ktor.io/
- Simple Slack API https://github.com/Ullink/simple-slack-api
- Jira Java Rest Client https://bitbucket.org/atlassian/jira-rest-java-client/src
- Redis

## Setup

To Run on local environment, create local.conf in resources folder with these information
- authToken: the slack bot auth token
- botName: the slack bot name
- jiraEmail: jira email (for authentication)
- jiraPassword: jira password (for authentication)
- jiraUri: jira project url
- REDIS_URL: link to redis server (if you use redis as a cache)

## Feature Handler
Each slack message will pass through the handler registry. To register a new command hanler, follow these step:
- Create a class that implement **BotCommand.kt**
- Register your class in **Main.kt** with *command* method
