export default function Topbar (){
    const[user, setUser] = useState (JSON.parse(localStorage.getItem('user')));

    useEffect (() => {
        
    },[user])
}