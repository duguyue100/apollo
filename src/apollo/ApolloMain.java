package apollo;

import shoot.ArrowInfo;

public class ApolloMain {
	public static int X=50;
	public static int Y=100;
	public static int WIDTH=1200;
	public static int HEIGHT=600;
	public static String PROJECT_TITLE="APOLLO";
	
	public static void main(String[] args)
	{
		new ApolloFrame(PROJECT_TITLE, X, Y, WIDTH, HEIGHT);
	}
}
