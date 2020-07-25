package friends;

import structures.Queue;
import structures.Stack;

import java.util.*;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		
		Queue<Person>q = new Queue<Person>();
		HashMap<String,Person>nameToB4 = new HashMap<String,Person>();
		nameToB4.put(p1, null);
		q.enqueue(g.members[g.map.get(p1)]);
		
		while(!q.isEmpty()){
			
			Person t = q.dequeue();
			
			if(t.name.equals(p2)){
				
				ArrayList<String>a = new ArrayList<String>();
				a.add(t.name);
				Person curr = nameToB4.get(t.name);
				while(curr!=null){
					
					a.add(0, curr.name);
					curr = nameToB4.get(curr.name);
					
				}
				
				return a;
				
			}
			
			for(Friend f = t.first; f!=null; f = f.next){
				
				if(!nameToB4.containsKey(g.members[f.fnum].name)){
					
					q.enqueue(g.members[f.fnum]);
					nameToB4.put(g.members[f.fnum].name, t);
					
				}
				
			}
			
		}
		
		return null;
		
	}

	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		
		boolean b[] = new boolean[g.members.length];
		HashSet<Person>h = new HashSet<Person>();
		ArrayList<ArrayList<String>> al = new ArrayList<ArrayList<String>>();
		
		for(Person p: g.members){
			
			if(!b[g.map.get(p.name)] && p.student && p.school.equals(school)){
				
				ArrayList<String>t = BFSCliques(g,school,p,b,h);
				
				if(al.size()==0 || t.size()<al.get(al.size()-1).size()){
					
					al.add(t);
					
					
				}else{
					
					al.add(al.size()-1, t);
					
				}
				
				
			}
			
		}
		
		if(al.size()==0){
			
			return null;
			
		}
		
		return al;
		
	}
	
	private static ArrayList<String> BFSCliques(Graph g, String School, Person p, boolean[] b, HashSet<Person>h){
		
		ArrayList<String> clique = new ArrayList<String>();
		Queue<Person> q = new Queue<Person>();
		q.enqueue(p);
		
		while(!q.isEmpty()){
			
			Person temp = q.dequeue();
			b[g.map.get(temp.name)] = true;
			if(!h.contains(temp)){
				
				clique.add(temp.name);
				h.add(temp);
				
			}
				
			for(Friend f = temp.first; f!=null; f=f.next){
					
				if(!b[f.fnum] && g.members[f.fnum].student && g.members[f.fnum].school.equals(School)){
						
					q.enqueue(g.members[f.fnum]);
						
					}
					
				}

		}
		
		return clique;
		
	} 
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		
		boolean[] visited = new boolean[g.members.length];
		ArrayList<String> a = new ArrayList<String>();
		
		for(Person p: g.members){
			int[] dfsnum = new int[g.members.length];
			int[] backnum = new int[g.members.length];
			
			if(!visited[g.map.get(p.name)]){
				
				dfs(g,p,p,visited,a, dfsnum, backnum, 1);
			}
			
		}
		
		if(a.size()==0){
			
			return null;
			
		}
		
		return a;
		
	}
	
	private static boolean hasPath(Graph g, Person start, Person end, Person goes){
		
		Stack<Person>s = new Stack<Person>();
		boolean[] v = new boolean[g.members.length];
		s.push(start);
		
		while(!s.isEmpty()){
			
			Person t = s.pop();
			if(t==end){
				
				return true;
				
			}
			v[g.map.get(t.name)] = true;
			
			for(Friend f = t.first; f!=null; f = f.next){
				
				if(!v[f.fnum] && g.members[f.fnum]!=goes){
					
					s.push(g.members[f.fnum]);
					
				}
				
			}
			
		}
		
		return false;
		
	}
	
	
	private static void dfs(Graph g, Person curr, Person start, boolean[] visited, ArrayList<String>a, int[] dfsnum, int[] backnum, int c){
		visited[g.map.get(curr.name)] = true;
		dfsnum[g.map.get(curr.name)] = c;
		backnum[g.map.get(curr.name)] = c;
		
		for(Friend f = curr.first; f!=null; f = f.next){
			
			if(!visited[f.fnum]){
				dfs(g,g.members[f.fnum],start,visited,a,dfsnum,backnum,c+1);
				if(dfsnum[g.map.get(curr.name)]>backnum[f.fnum]){
					
					backnum[g.map.get(curr.name)] = Math.min(backnum[g.map.get(curr.name)], backnum[f.fnum]);
					
				}else{
					
					if(curr!=start){
						
						if(!a.contains(curr.name)){
							
							a.add(curr.name);
							
						}
						
					}else{
						
						if(curr.first.next==null){
							
							continue;
							
						}
						
						HashSet<String>h = new HashSet<String>();
						
						for(Friend x = curr.first; x!=null; x = x.next){
							
							for(Friend y = x.next; y!=null; y = y.next){
								
								if(!h.contains(g.members[x.fnum].name+","+g.members[y.fnum].name) && !h.contains(g.members[y.fnum].name+","+g.members[x.fnum].name)){
									
									if(!hasPath(g,g.members[x.fnum],g.members[y.fnum],start)){
										
										if(!a.contains(curr.name)){
											
											a.add(curr.name);
											
										}
										break;
										
									}
									
									h.add(g.members[x.fnum].name+","+g.members[y.fnum].name);
									h.add(g.members[y.fnum].name+","+g.members[x.fnum].name);
									
								}
								
							}
							
						}
						
					}
					
				}
				
			}else{
				
				backnum[g.map.get(curr.name)] = Math.min(backnum[g.map.get(curr.name)], dfsnum[f.fnum]);
				
			}
			
		}
		
		
		
	}
	
	
//hellofriend	
}

