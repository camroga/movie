# movies

# Introduction
The structure of the code in this repo contain the following tools:
- MVVM
- Hilt
- Clean Architecture
- Basic Modularization
- Retrofit
- Room
- Coroutines
- Compose

# How does this code promote Clean Architecture?
As you might already know Clean Architecture is the philosophy that tries to organize the elements
that conform your Software Design into layers or ring levels that separate the different concerns
of your logic with a very strong focus on the domain of your application.
So, to answer the question _**How does this code promote Clean Architecture?**_ Well, you will notice
that the project is modularized into the following libraries:
- app (Android library)
- data (Android Library)
- domain (Pure Java/Kotlin library)