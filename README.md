# Rapidly-Exploring Random Tree 

RRT and RRT * algorithm written in Processing/Java.

A rapidly exploring random tree (RRT) is an algorithm designed to efficiently search nonconvex, high-dimensional spaces by randomly building a space-filling tree. The tree is constructed incrementally from samples drawn randomly from the search space and is inherently biased to grow towards large unsearched areas of the problem. They easily handle problems with obstacles and differential constraints and have been widely used in autonomous robotic motion planning.

### Picture of app performing search (blue is the start node & red is the goal node)
![Alt text] (https://cloud.githubusercontent.com/assets/10769110/26565142/6e40e794-449d-11e7-94a7-747c6ea31b4a.png)

### Example 1) App finding path with 0% bias
![Alt tex](https://cloud.githubusercontent.com/assets/10769110/26564628/351aa6c6-4498-11e7-80b5-5d16fd74bd42.gif)

### Example 2) Same as example 1 but with slightly harder obstacles
![Alt text](https://cloud.githubusercontent.com/assets/10769110/26564807/7a366f54-449a-11e7-8f6b-73e5ecf1da39.gif)
