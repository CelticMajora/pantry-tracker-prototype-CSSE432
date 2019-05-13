package ui;

public class Main {

	public static void main(String[] args) {
		API api = new API("localhost", "8080");
		api.postUser("Drippy");
	}

}
