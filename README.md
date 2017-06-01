# Rapidly-Exploring Random Tree 

RRT and RRT * algorithm written in Processing/Java.

![Alt text](https://cloud.githubusercontent.com/assets/10769110/26603698/adf2a14e-453c-11e7-9ef0-b2473d852a43.gif)

(GIF from https://en.wikipedia.org/wiki/Rapidly-exploring_random_tree)

A rapidly exploring random tree (RRT) is an algorithm designed to efficiently search nonconvex, high-dimensional spaces by randomly building a space-filling tree. The tree is constructed incrementally from samples drawn randomly from the search space and is inherently biased to grow towards large unsearched areas. They easily handle problems with obstacles and differential constraints and have been widely used in autonomous robotic motion planning.

The RRT* algorithm is almost the same as the regular RRT algortihm except for the algorithm factors in a 'cost' attribute which is the distance from the root node to every new node that is randomly generated in the tree. The tree 'rewires' itself accordingly based on that cost factor, which causes the tree to feather out with a more outward movement of the unsearched configuration space. The visual differences can be seen in the demonstrations below, although it is worthy to note that this becomes more apparent as the number of iterations increases.

## Pics of Algorithm Performing Searches
Note: Blue node is the starting point, red node is the end point, and brown rectangles are obstacles. 

![Alt text](https://cloud.githubusercontent.com/assets/10769110/26661156/e9187ccc-4630-11e7-842a-cb7d89b4c714.png)

### RRT 
![Alt text](https://cloud.githubusercontent.com/assets/10769110/26661155/e5f1d796-4630-11e7-84a4-622ab1e22fe2.png)

### RRT*
![Alt text](https://cloud.githubusercontent.com/assets/10769110/26661152/dc4f8364-4630-11e7-911b-47f5cac5a2b3.png)

### RRT
![Alt text](https://cloud.githubusercontent.com/assets/10769110/26661162/f0781392-4630-11e7-83fb-48ccc36cfcb7.png)

### RRT*
![Alt text](https://cloud.githubusercontent.com/assets/10769110/26661164/f4189d50-4630-11e7-81a1-44df72479aa7.png)
