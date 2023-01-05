import { useEffect , useState} from "react";
import { useWebSocket } from "react-use-websocket/dist/lib/use-websocket";
import { getPostOptions, getURL } from "../util/apihelpers";
import LoggedInComponent from "../views/components/loggedInComponent";
import LoggedOutComponent from "../views/components/loggedOutComponent";

export default function Topbar (){
    const [user, setUser] = useState (JSON.parse(localStorage.getItem('user')));
    const {lastMessage} = useWebSocket('ws://localhost:8080', {share:true});
    console.log(user);
    useEffect(() => {
        if (lastMessage !== null){
            console.log("top bar:" + lastMessage.data);
            if (lastMessage.data == 'AUTH_CHANGED'){
                console.log("wow");
                const checkuser = JSON.parse(localStorage.getItem('user'));
                setUser(checkuser);
            }
        }
    }, [lastMessage]);

    const logout = async () => {
        const retURL = getURL('logout/');
        const options = getPostOptions ({});
        localStorage.removeItem('user');
        setUser(JSON.parse(localStorage.getItem('user')));
        try {
            const resp = await fetch(retURL, options);
            const data = await resp.json();
            if (data.status === "success"){
            }
        } catch (err){
            console.log(err);
        }
    }


    return (
        ((user !== null) && <LoggedInComponent username={user.username} role={user.admin?"admin":"student"}  logout={logout}/> )||<LoggedOutComponent/>
    );
}