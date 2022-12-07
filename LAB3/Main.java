import java.io.Console;
import java.util.Scanner;

public class Main {
    public static final String LOG_IN_SUCCESS = " OK LOGIN completed";
    private static final  String CMD_LINE = "---------------------------------------------------------------------------------------------------------------------------------------------------------";
    private static final String EMAIL_HOST = "webmail.kth.se";
    private static final int EMAIL_PORT = 993;
    private static final String SMTP_HOST = "smtp.kth.se";
    private static final int SMTP_PORT = 587;
    private static final String CRLF = "\r\n";
    public static void main(String[] args) {
        Console console = System.console();
        MailReader reader = null;
        final String choiceGUI = "Mail User Agent Options:\n>Read email: READ\n>Write email: WRITE"
        + "\n>Quit Program: QUIT";
        String choice;
        MailSender sender = null;

        while(true){
            System.out.println(choiceGUI);
            choice = console.readLine(">");
            if (choice.equalsIgnoreCase("READ")){
                mailReadProcedure(reader, console);
            }
            else if (choice.equalsIgnoreCase("WRITE")){
                mailSenderProcedure(sender, console);
            }
            else if (choice.equalsIgnoreCase("QUIT")){
                break;
            }
            else {
                System.out.println("Unknown command");
            }
        }
    }

    private static void mailSenderProcedure(MailSender sender, Console console){
        sender = new MailSender(SMTP_HOST, SMTP_PORT, EMAIL_HOST);
        boolean loginFlag = false;
        String response = sender.initConnection();
        StringBuilder dataString = new StringBuilder("");

        readResponseSMTP(response);
        response = sender.sayHELO();
        readResponseSMTP(response);
        response = sender.writeSMTP("STARTTLS");
        readResponseSMTP(response);
        if (!response.contains("Ready to start TLS")){
            System.out.println("STARTTLS Failed");
            return;
        }
        sender.upgradeSocketTLS();
        sender.sayHELO();
        readResponseSMTP(response);

        String username;
        char[] password;
        while(!loginFlag){
            response = sender.writeSMTP("AUTH LOGIN");
            readResponseSMTP(response);
            username = console.readLine("Enter username: ");
            password = console.readPassword("Enter password: ");
            response = sender.loginSMTP(username, String.valueOf(password));
            readResponseSMTP(response);
            if (response.contains("Authentication successful")){
                loginFlag = true;
            }
            else {
                System.out.println("Enter 0 to exit login interface, Enter anything else to try again, ");
                response = console.readLine(">");
                if (response.equals("0")){
                    sender.closeConnection();
                    return;
                }
            }
        }

        String cmd, writeData;
        boolean keepWrite = true;
        String[] args;
        String cmdGUI = "COMMANDS:\n>Send Email: EMAIL [FROM] [TO] \n>Log Out: LOGOUT";
        while(loginFlag){
            System.out.println(cmdGUI);
            cmd = console.readLine(">");
            args = cmd.split(" ");
            if (args[0].equalsIgnoreCase("EMAIL")){
                if (args.length >= 3){
                    response = sender.emailSendFrom(args[1]);
                    readResponseSMTP(response);
                    if (!response.contains("250") && !response.toLowerCase().contains("ok")){
                        continue;
                    }
                    response = sender.emailSendTo(args[2]);
                    readResponseSMTP(response);
                    if (!response.contains("250") && !response.toLowerCase().contains("ok")){
                        continue;
                    }
                    response = sender.writeSMTP("DATA");
                    readResponseSMTP(response);
                    if (!response.contains("354")) continue;
                    System.out.println(">Write message below, to stop writing type a single '.' and CLRF");
                    keepWrite = true;
                    while(keepWrite){
                        writeData = console.readLine(">");
                        if (writeData.equals(".")){
                            response =sender.writeSMTP(dataString.toString() + CRLF + "." + CRLF);
                            System.out.println(response);
                            keepWrite = false;
                        }
                        else {
                            dataString.append(writeData + "\n");
                        }
                    }
                }
            }
            else if (args[0].equalsIgnoreCase("LOGOUT")){
                response = sender.writeSMTP("QUIT");
                readResponseSMTP(response);
                sender.closeConnection();
                loginFlag = false;
            }
            else {
                System.out.println("Command '" + cmd + "' is unknown");
            }
        }
    }

    private static void mailReadProcedure(MailReader reader, Console console) {
        reader = new MailReader(EMAIL_HOST, EMAIL_PORT);
        boolean loginFlag = false;
        String ConnMsg = reader.connectIMAP();
        System.out.println(ConnMsg);
        String msg;

        String username, password;
        char[] pass;

        String response;
        while(!loginFlag){
            System.out.println ("Login at " + EMAIL_HOST + ":" + EMAIL_PORT);
            username = console.readLine("Enter username: ");
            pass = console.readPassword("Enter password: ");
            password = String.valueOf(pass);
            msg = reader.loginIMAP(username, password);
            System.out.println(msg);
            if (msg.contains(reader.getCodeStr() + LOG_IN_SUCCESS)){
                loginFlag = true;
            }
            else {
                System.out.println("Enter 0 to exit login interface, Enter anything else to try again, ");
                response = console.readLine(">");
                if (response.equals("0")){
                    reader.closeConnection();
                    return;
                }
            }
        }
        
        String cmd;
        String GUImsg = "COMMANDS:\n>check inbox: INBOX" + 
        "\n>log out: LOGOUT\n>fetch full: FULL [UID]" + 
        "\n>fetch header: HEADER [UID]\n>body text: TEXT [UID]";
        while (loginFlag){
            System.out.println(CMD_LINE);
            System.out.println (GUImsg);
            switch ((cmd = console.readLine(">")).split(" ")[0].toUpperCase()) {
                case "INBOX":
                    System.out.println(reader.checkInbox());
                    break;
                case "LOGOUT":
                    System.out.println(reader.logOut());
                    reader.closeConnection();
                    loginFlag = false;
                    break;
                case "FULL":
                    if (cmd.split(" ").length >= 2){
                        System.out.println(reader.fetchFull(Long.parseLong(cmd.split(" ")[1])));
                    }
                    break;
                case "HEADER":
                    if (cmd.split(" ").length >= 2){
                        System.out.println(reader.fetchHeader(Long.parseLong(cmd.split(" ")[1])));
                    }
                    break;
                case "TEXT":
                    if (cmd.split(" ").length >= 2){
                        System.out.println(reader.fetchBodyText(Long.parseLong(cmd.split(" ")[1])));
                    }       
                    break;
                default:
                    System.out.println("Unknown command!");
                    break;
            }
        }
    }

    private static void readResponseSMTP(String response) {
        Scanner strScan = new Scanner(response);
        while (strScan.hasNextLine()){
            System.out.println("S: " + strScan.nextLine());
        }
        strScan.close();
    }
}