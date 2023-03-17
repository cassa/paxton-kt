# Maintenance Guide

So, you've been handed the job of maintaining Paxton... or you're curiously searching through the repository.. either way, this page was written in hopes that you can gain a base understanding in how you can maintain this source code. :)

The Paxton (Kotlin) project was originally set up by [Lachlan Adamson](https://github.com/lokka30/), if you need advice with maintaining this project, feel free to contact. 

## Editors

IntelliJ is the only viable option for maintaining Kotlin software at the time of writing. There's an excellent free Community Edition. JetBrains may or may not still be offering students free licenses for the Ultimate edition. Check the GitHub student pack!

If you use any other editors on this project, please remember to not commit any of your personal files (such as `.vscode` or something). If you notice they pop up, `git rm` them and add those file paths to the `.gitignore` file.

## Compiling

**Apache Maven** is a very popular build tool used for Java and Kotlin applications. Maven has been chosen since it is very popular in the community and is easier to use than alternatives.

Unless you're using IntelliJ (or *potentially* Eclipse), you'll need to install Maven manually on your machine.

This is pretty much the only command you will ever need for Maven. It'll run all the tests and compile the project.

```shell
mvn clean install
```

The `.jar` file will end up in the `target` directory.

If you've been messing around with `pom.xml` and Maven isn't working correctly, this command might help refresh things:

```shell
mvn -U clean install
```

## Adding Commands

Just copy and paste another command ... edit the contents ... make sure to register the new command object you've created in the command manager.

## Configurations

There are 2 configs - Data and Settings.

Settings stores values that are easy to understand and modify.

Data stores the rest of persistent settings and data.

In the event Paxton needs to store a considerable amount of user data, consider using a proper database. At the moment, Paxton doesn't, so this JSON file stores everything we need for the bot.

## Unit Tests

Any volunteers?.. no? 😁

Write them if you want... they probably don't exist. It's quite difficult to write tests for Discord bots. This project is quite small and wouldn't realise the common benefits of implementing unit tests.
