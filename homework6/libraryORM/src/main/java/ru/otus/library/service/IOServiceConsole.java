package ru.otus.library.service;

import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceConsole implements IOService{
    private final PrintStream out;
    private final Scanner scanner;

    public IOServiceConsole() {
        this.out = System.out;
        this.scanner = new Scanner(System.in);
    }

    public IOServiceConsole(Scanner scanner, PrintStream out) {
        this.out = out;
        this.scanner = scanner;
    }

    @Override
    public void out(String message) {
        out.println(message);
    }

    @Override
    public String readString() {
        return scanner.nextLine();
    }
}
