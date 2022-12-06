import java.io.Console;

public class Main {
    public static final String LOG_IN_SUCCESS = " OK LOGIN completed";
    private static final  String CMD_LINE = "---------------------------------------------------------------------------------------------------------------------------------------------------------";
    private static final String HOST = "webmail.kth.se";
    private static final int PORT = 993;
    public static void main(String[] args) {
        MailReader reader = new MailReader(HOST, PORT);
        boolean loginFlag = false;
        String ConnMsg = reader.connectIMAP();
        System.out.println(ConnMsg);
        String msg;

        String username, password;
        Console console = System.console();
        char[] pass;

        while(!loginFlag){
            System.out.println ("Login at " + HOST + ":" + PORT);
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
                    System.out.println("Unkown command!");
                    break;
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
}