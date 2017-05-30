# Rapidly-Exploring Random Tree 

RRT and RRT * algorithm written in Processing/Java.

GIF of a standard Rapidly-Exploring Random Tree
![Alt text](https://cloud.githubusercontent.com/assets/10769110/26603698/adf2a14e-453c-11e7-9ef0-b2473d852a43.gif)
(GIF from https://en.wikipedia.org/wiki/Rapidly-exploring_random_tree)

A rapidly exploring random tree (RRT) is an algorithm designed to efficiently search nonconvex, high-dimensional spaces by randomly building a space-filling tree. The tree is constructed incrementally from samples drawn randomly from the search space and is inherently biased to grow towards large unsearched areas. They easily handle problems with obstacles and differential constraints and have been widely used in autonomous robotic motion planning.

## Demonstrations of Algorithm
Note: Blue is the start node, red is the goal node, and brown blocks are obstacles.

## RRT 

### Example 1).
![Alt text](https://cloud.githubusercontent.com/assets/10769110/26565142/6e40e794-449d-11e7-94a7-747c6ea31b4a.png)

### Example 2). 
![Alt text](https://cloud.githubusercontent.com/assets/10769110/26564807/7a366f54-449a-11e7-8f6b-73e5ecf1da39.gif)

## RRT* (Slightly smarter version of RRT)

The RRT* is the same thing as an RRT except that the algorithm performs a "rewiring" of nodes if it sees there is a shorter path from the start node to a newly inserted node, which causes the branches to "fan out" and perform a more efficient search for the goal node, at the cost of more computation power.

### Example 1).
![Alt text](https://cloud.githubusercontent.com/assets/10769110/26600237/2daeaf16-4530-11e7-92c2-ce7ed6813f56.png)

### Example 2).
![Alt text](https://cloud.githubusercontent.com/assets/10769110/26600246/38848cda-4530-11e7-9545-643bdcbf0e3c.png)

### Exmaple 3).
![Alt text](https://cloud.githubusercontent.com/assets/10769110/26600257/416caaee-4530-11e7-9572-617de0f05f1c.png)
