import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

interface MaxFlow {
	public void addEdge(int a, int b, int v);

	public int getMaxflow(int s, int t);
}
public class BFS {
	public static void main(String args[]) {
		FFImp f1 = new FFImp(1000);
		f1.addEdge(0, 1, 21);
		f1.addEdge(0, 2, 12);
		f1.addEdge(1, 2, 8);
		f1.addEdge(2, 1, 6);
		f1.addEdge(3, 2, 11);
		f1.addEdge(1, 3, 10);
		f1.addEdge(2, 4, 9);
		f1.addEdge(4, 3, 3);
		f1.addEdge(3, 5, 7);
		f1.addEdge(4, 5, 2);
		System.out.println("Maxflow BFS " + f1.getMaxflow(0, 4));
	}
}
class FFImp implements MaxFlow {
	private int n;
	private int[][] capi;

	public FFImp(int n) {
		this.n = n;
		this.capi = new int[n][n];
	}
	@Override
	public void addEdge(int a, int b, int v) {
		capi[a][b] += v;
	}
	@Override
	public int getMaxflow(int s, int t) {
		int flow = 0;
		while (true) {
			int path = bfs(s, t);
			if (path <= 0) {
				break;
			} else {
				flow += path;
			}
		}
		return flow;
	}
	public int getMaxflowBFS(int s, int t) {
		int fl = 0;
		while (true) {
			int path = bfs(s, t);
			if (path <= 0) {
				break;
			} else {
				fl += path;
			}
		}
		return fl;
	}
	private int bfs(int source, int sink) {
		Queue<Integer> queue = new LinkedBlockingQueue<Integer>();
		int[] prv = new int[n];
		Arrays.fill(prv, -1);
		boolean[] visit = new boolean[n];
		Arrays.fill(visit, false);
		queue.add(source);
		visit[source] = true;
		boolean find = false;
		while (!queue.isEmpty()) {
			int from = queue.poll();
			for (int i = 0; i < n; i++) {
				if (visit[i] == false && capi[from][i] > 0) {
					queue.add(i);
					visit[i] = true;
					prv[i] = from;

					if (i == sink) {
						find = true;
						break;
					}
				}
			}
			if (find) {
				break;
			}
		}
		if (!find) {
			return 0;
		}
		int to = sink;
		int fl = Integer.MAX_VALUE;
		while (prv[to] != -1) {
			fl = Math.min(fl, capi[prv[to]][to]);
			to = prv[to];
		}
		to = sink;
		while (prv[to] != -1) {
			capi[prv[to]][to] -= fl;
			capi[to][prv[to]] += fl;
			to = prv[to];
		}
		return fl;
	}
}
