package ui;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter an ip to connect to:");
		String ip = scan.nextLine();
		System.out.println("Please enter a port number to connect to:");
		String portNumber = scan.nextLine();
		API api = new API(ip, portNumber);
		UI ui = new UI(api);
		ui.login();
		ui.displayLoggedInUserInformation();
		ui.runCommands();
	}

}
