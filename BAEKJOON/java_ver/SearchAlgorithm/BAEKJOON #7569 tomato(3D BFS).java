import java.util.*;
import java.io.*;

class Node{
	int x;
	int y;
	int z;
	int day;
	
	public Node(int x, int y, int z, int day) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.day = day;
	}
}

public class Main {
	
	static int N, M, H; //N세로, M가로
	static int[][][] board;
	static boolean[][][] visit;
	static int[] dx= {0,0,1,-1,0,0}, dy= {1,-1,0,0,0,0}, dz= {0,0,0,0,1,-1}; //동서남북 위 아래.
	static List<Node> startList = new ArrayList<>(); //와 자료구조 LinkedList-> ArrayList로 바꿨을뿐인데 아래 bfs시작할때 조회하는 부분에서 LinkedList로하면 시간초과가 난다.
	
	static void print3D(int[][][] arr) {
		for(int k=0; k<arr[0][0].length; k++) {
			for(int i=0; i<arr.length; i++) {
				for(int j=0; j<arr[0].length; j++) {
				
					System.out.print(arr[i][j][k] + " ");
				}
				System.out.println();
			}
			System.out.println();
		}
	}
	
	static void printAnswer(int[][][] arr) {
		int leastDay = 0;
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[0].length; j++) {
				for(int k=0; k<arr[0][0].length; k++) {
					if(arr[i][j][k]==0) { //0이 하나라도 있으면 -1 출력
						System.out.println(-1);
						return; 
					}
					if(arr[i][j][k]>leastDay) {
						leastDay = arr[i][j][k]; //돌면서 최소 걸린 날짜 -> bfs로 구한 날짜중 최대 날짜
					}	
				}
			}
		}
		System.out.println(leastDay);
	}
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());

		board = new int[N][M][H];
		visit = new boolean[N][M][H];
		
		boolean allOne = true;
		
		for(int k=0; k<H; k++) {
			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<M; j++) {
				
					board[i][j][k] = Integer.parseInt(st.nextToken());

					if(board[i][j][k] ==1) {
						startList.add(new Node(i, j, k, 0));
					}
					
					if(board[i][j][k] == 0) {
						allOne = false;
					}
				}
			}
		}
	

		if(allOne) {
			System.out.println(0);
			return;
		}
		
		//두 start line에서 bfs를 해야 함. (한 스텝씩)
		bfs(startList);
		printAnswer(board);

	}
	
	static void bfs(List<Node> startList) {
		
		Queue<Node> Q = new LinkedList<>();
		
		//모든 시작점을 넣어서 시작한다. (BFS라서 다행히 모든 시작점에서 한 스텝씩 전진한다)
		for(int i=0; i< startList.size(); i++) {
			Q.add(startList.get(i));
		}
		
		while(!Q.isEmpty()) {
			
			Node node = Q.poll();
			
			for(int dir=0; dir<6; dir++) {
				
				int new_x = node.x + dx[dir];
				int new_y = node.y + dy[dir];
				int new_z = node.z + dz[dir];
				int new_day = node.day+1; //int new_day = node.day++; 이거랑 뭔 차이지?
				
				if(new_x<0 || new_x>=N || new_y<0 || new_y>=M || new_z<0 || new_z>=H) continue;
				if(board[new_x][new_y][new_z] != 0) continue;
				if(visit[new_x][new_y][new_z] != false) continue;
				
				Q.add(new Node(new_x, new_y, new_z, new_day));
				visit[new_x][new_y][new_z] = true;
				board[new_x][new_y][new_z] = new_day;
				
			}
		}	
	}
	
}
