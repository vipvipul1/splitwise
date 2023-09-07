package org.vip.splitwise;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.vip.splitwise.commands.CommandExecutor;

import java.util.Scanner;

@SpringBootApplication
public class SplitwiseApplication implements CommandLineRunner {
	Scanner scanner;
	CommandExecutor commandExecutor;

	public SplitwiseApplication(CommandExecutor commandExecutor) {
		this.commandExecutor = commandExecutor;
		this.scanner = new Scanner(System.in);
	}

	public static void main(String[] args) {
		SpringApplication.run(SplitwiseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		while(true) {
			System.out.print("Enter command: ");
			String input = scanner.nextLine();
			commandExecutor.routeCommand(input);
		}
	}
}
