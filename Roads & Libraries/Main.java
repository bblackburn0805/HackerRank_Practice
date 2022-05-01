/*
    This code passes only 6/12 tests because it is not optimized and it goes over the time limit.
    n can be 10^5 cities, so I'm guessing that's the problem. To optimize, I think I could probably
    replace the ArrayLists with int[], and implement my own "containsAll" and "removeAll" methods.

    The time complexity currently is at least O(n^3). The embedded while() loop has 2 searches,
    if these were replaced with a different data structure then the time complexity could easily
    be lowered down to O(n^2).

 */




/*
Determine the minimum cost to provide library access to all citizens of HackerLand.
There are  cities numbered from  to . Currently there are no libraries and the cities are not connected.
Bidirectional roads may be built between any city pair listed in . A citizen has access to a library if:

Their city contains a library.
They can travel by road from their city to a city containing a library.
 */



package com.company;

import java.sql.Array;
import java.util.*;

public class Main {

    static int n = 5;
    static List<List<Integer>> cities = new ArrayList<>();
    static int c_road = 2;
    static int c_lib = 3;
    static int [] roads = new int[]{1,2,1,3,1,4};

    public static void main(String[] args) {

        // Remove this part
        for(int i=0; i<roads.length; i+=2){
            List<Integer> temp = new ArrayList<>();
            temp.add(roads[i]);
            temp.add(roads[i + 1]);
            cities.add(temp);
        }

        int minRoads = 0;
        int minLibraries = 0;

        // Create an adjacency Map
        HashMap<Integer, List<Integer>> adjMap = new HashMap<>();


        for(int i=1;i<=n;i++)
            adjMap.put(i, new ArrayList<>());


        for(List<Integer> list : cities){
            int city1 = list.get(0);
            int city2 = list.get(1);

            if(!adjMap.get(city1).contains(city2))
                adjMap.get(city1).add(city2);

            if(!adjMap.get(city2).contains(city1))
                adjMap.get(city2).add(city1);
        }


        List<Integer> unvisited = new ArrayList<>();
        for(int i=1;i<=n;i++)
            unvisited.add(i);



        // Each iteration of this while loop contains a cluster of cities. If the
        // loop is repeated, that means that there are a group of cities not
        // connected to the rest. There needs to be only 1 library in each
        // cluster.

        while (unvisited.size() != 0) {


            // Determine what unvisited city has the most roads.
            int max = -1, maxCity = 0;
            for(int num : unvisited){
                if(adjMap.get(num).size() > max) {
                    maxCity = num;
                    max = adjMap.get(num).size();
                }
            }

            // Initialize thisCluster with unvisited city with most roads
            // and build a road to the adjacent cities.
            // Put a library here.

            List<Integer> thisCluster = new ArrayList<>();
            thisCluster.add(maxCity);
            if(adjMap.containsKey(maxCity))
                thisCluster.addAll(adjMap.get(maxCity));
            System.out.println(maxCity);
            unvisited.removeAll(thisCluster);
            minRoads += max;
            minLibraries++;

            // Now start iterating what cities are adjacent to the first city
            int index = 1;
            while (index < thisCluster.size()) {

                // nextRoads will find the 2nd tier roads.
                List<Integer> nextRoads = new ArrayList<>(adjMap.get(thisCluster.get(index)));


                // If there are unvisited cities in this cluster, build a road
                // to them and put them into visited.
                if (!thisCluster.containsAll(nextRoads)) {
                    nextRoads.removeAll(thisCluster);
                    for (int city : nextRoads) {
                        thisCluster.add(city);
                        unvisited.remove((Object)city);
                        minRoads++;
                    }
                }


                index++;
            }// end while() search for group of connected cities


        }// end while() search for clusters. All cities have been visited.

        System.out.println("Roads: " + minRoads);
        System.out.println("Libraries: " + minLibraries);
    }
}
