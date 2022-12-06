import java.io.Console;
import java.util.Scanner;

public class Main {
    public static final String LOG_IN_SUCCESS = " OK LOGIN completed";
    private static final  String CMD_LINE = "---------------------------------------------------------------------------------------------------------------------------------------------------------";
    private static final String EMAIL_HOST = "webmail.kth.se";
    private static final int EMAIL_PORT = 993;
    private static final String SMTP_HOST = "smtp.kth.se";
    private static final int SMTP_PORT = 587;
    public static void main(String[] args) {
        boolean run = true;
        Console console = System.console();
        MailReader reader = null;
        final String choiceGUI = "Choose:\n>Read email: READ\n>Write email: WRITE"
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

        /*OutputStream out = reader.getOut();
        InputStream in = reader.getIn();
        String outmsg = "<open connection>";
        byte[] readbuffer = new byte[1024];
        try {
            out.write(outmsg.getBytes());
            in.read(readbuffer);
            System.out.println(new String(readbuffer));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private static void mailSenderProcedure(MailSender sender, Console console){
        Scanner strScan;
        sender = new MailSender(SMTP_HOST, SMTP_PORT, EMAIL_HOST);
        String response = sender.initConnection();

        readResponseSMTP(response);
        response = sender.sayHELO();
        readResponseSMTP(response);
        response = sender.writeSMTP("STARTTLS");
        readResponseSMTP(response);
        response = sender.writeSMTP("AUTH LOGIN");
    }

    private static void readResponseSMTP(String response) {
        Scanner strScan = new Scanner(response);
        while (strScan.hasNextLine()){
            System.out.println("S: " + strScan.nextLine());
        }
        strScan.close();
    }

    private static void mailReadProcedure(MailReader reader, Console console) {
        reader = new MailReader(EMAIL_HOST, EMAIL_PORT);
        boolean loginFlag = false;
        String ConnMsg = reader.connectIMAP();
        System.out.println(ConnMsg);
        String msg;

        String username, password;
        char[] pass;

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
}