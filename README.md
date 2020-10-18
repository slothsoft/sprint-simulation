# Sprint Simulation

[![Build Status](https://travis-ci.com/slothsoft/sprint-simulation.svg?branch=master)](https://travis-ci.com/slothsoft/sprint-simulation)

**Content:**

- [Preface](#preface)
- [Overview](#overview)
    - [Estimation](#estimation) 
    - [Execution](#execution) 
- [Results](#results)
- [Real Life](#real-life)
- [Links](#links)


# Preface

Currently I've gotten into a lot of discussion over the question *"In scrum should we pre-assign tasks during sprint planning?"*. 

I'm absolutely **for** pre-assigning tasks, because:

- It's easier to address individual capacities (e.g. the never-before-heard "part-time")
- It's fairer ("You do this ugly bug, and I'll do that one.")
- It's more efficient (just give the task to whoever estimates the fastest time)
- It makes the planning faster (everybody has to understand only their own tasks, and team members can even prepare for that)
- Many bugs are already implicitly pre-assigned, because team members are expert in different things
- You don't double book people (e.g. by adding two tasks that can only be done by one person efficiently, when each of them alone would fill the time of that person completely)

My team is against it, because:

1. It's not failsafe (if a member can't participate in the sprint, nobody can take on their tasks)

(I don't think there was ever a second argument.)

And just to address this:

1. "Nobody can take on their tasks" - this is exactly what happens in our team. And we don't pre-assign. It's just that implicitly assigned tasks will not be done by anybody else. For good reason. If the expert is back next sprint, why should I half-ass their tasks?

I have another suspicion why pre-assigning tasks is better, and that is our horrible estimations. It's clear that you never learn to estimate if you can't practice, so in that regard pre-assignments are already better. However I have a hunch that even with good estimations it won't work if multiple people estimate, and only one of those does the task.

*This project was born to simulate sprint planning and execution, and figure out if you can estimate tasks as a group.*


# Overview


![class diagram](readme/class-diagram.png)

This project makes some assumptions:

- **`Sprint`**
    - a sprint consists of **`Task`s**
        - tasks have a **`Complexity`** (i.e. "story points")
            - for this simulation, we have three complexities (if your standard task is 3sp, think of them maybe as 1sp, 3sp and 8sp)
    - a sprint has a length in days
- **`Member`**
    - a team member has a work **`Performance`**
        - we have three standard performances, but you can always add your own (i.e. a team member that is good in some task and bad in others)
    - a team member's estimation deviates from reality by an error margin in _both directions_, but on average the member *estimate correctly*
    - a team member has a fixed set of hours they can work each day
    

## Estimation    

*(See [SprintGenerator](core/src/main/java/de/slothsoft/sprintsim/generation/SprintGenerator.java))*

1. The team **`Member`s** get together
2. A **`Task`s** is created from the never-ending task machine (see [TaskCreator])(core/src/main/java/de/slothsoft/sprintsim/config/TaskCreator.java)
3. Each team member estimates how long it will take to do the task (for *them*) - for that they take the time it *will* take them to do the task, and use a Gaussian curve capped at their `estimationDeviation` to add a bit of variance; on average this means they will estimate correctly
4. A operation is used to get the team's estimation from the member's (average right now)
5. If there is still hours left in the **`Sprint`** start again at 2.


## Execution    
    
1. The team **`Member`s** and **`Task`s** get together
2. Tasks are sorted by complexity, so longer tasks are done earlier
3. Each task is assigned to the team member who has the most time open to do it
4. The team member tracks does the task and tracks their time
    
    
# Results



# Real Life  

This is just a simulation, and humans don't act like computer programs. So in reality, these problems additionally arise:
    
- of course, most glaringly, you can't calculate hours from story points (but somehow we have to create different-lengh tasks)
- most of them time, team members will not estimate correctly on average; from what I've seen, even skilled team members will (almost) always over-estimate or under-estimate, even if only by a little
- junior members (and others with little knowledge of a task at hand) will usually not estimate their needed time, but try to stay close to their peers; often this is enforced by the group (this is a conversation I actually had when I estimated my *needed* time: "Why would you estimate so much more than us?" - "Because I've never heard of the technology before and I'll need some time to learn it." - "But it's easy!" - "So... you want me to use your estimation?" - "Yes.")



# Links

- [DrawIO](https://app.diagrams.net) - to create beautiful diagrams online

