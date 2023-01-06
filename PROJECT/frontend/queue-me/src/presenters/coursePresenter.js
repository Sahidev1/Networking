import { useEffect, useRef, useState } from "react";
import { useFetcher, useNavigate } from "react-router-dom";
import { getGetOptions, getPostOptions, getURL } from "../util/apihelpers";
import CoursePage from "../views/page/coursepage";
import { useWebSocket } from "react-use-websocket/dist/lib/use-websocket";
import WriteBox from "../views/components/writeBox";
import MessageBox from "../views/components/messageBox";

export default function CoursePresenter (){
    const {lastMessage} = useWebSocket('ws://localhost:8080', {share:true});


    const [msg, setMsg] = useState (null);
    const [sendto, setSendTo] = useState(null);
    const [qitems, setQitems] = useState(null);
    const nav = useNavigate();
    const navHome = () => nav('/');
    const [storage, setStorage] = useState (sessionStorage.getItem('course_queue_id'))
    const user = JSON.parse(localStorage.getItem('user'));

    const messageRef = useRef ();

    const locRef = useRef ();
    const comRef = useRef ();
    const refArr = [locRef, comRef];
    const [addItemData, setAddItemData] = useState(null);

    const sortItems = (items) => {
        items.sort((a, b) => a.id < b.id?-1:1);
    }

    const clickAddItem = () => {
        const course_id = sessionStorage.getItem('course_queue_id');
        const user = JSON.parse(localStorage.getItem('user'));
        const newData = {"user_id": user.id, "course_id": course_id,
         "location": locRef.current.value, 'comment': comRef.current.value};
        setAddItemData (newData);
    }

    const clickUpdItem = () => {
        const user = JSON.parse(localStorage.getItem('user'));
        const newData = {"user_id": user.id,
         "location": locRef.current.value, 'comment': comRef.current.value};
        setAddItemData (newData);
    }

    const clickMessage = (user_id, username) => {
        setSendTo({"user_id": user_id, "username": username});
    }

    const unsetSendTo = () => setSendTo(null);

    useEffect (() => {
        console.log("DEBUGGER" + addItemData);
        const changeHandler = async () => {
            const path = Object.keys(addItemData).length === 4?'additem/':'updateitem/';
            const apiURL = getURL(path);
            const options = getPostOptions(addItemData);
            try {
                await fetch(apiURL,options);
            } catch (error) {
                console.log(error)
            }
        }

        if (addItemData){
            changeHandler();
        }
    }, [addItemData])

    const getItems = async () => {
        const course_id = JSON.parse(sessionStorage.getItem('course_queue_id'));
        if (course_id === null) return null;
        const apiURL = getURL('items/');
        const options = getPostOptions({"course_id":course_id});

        try {
            const resp = await fetch (apiURL, options);
            const data = await resp.json();
            if (data.status === "success"){
               // sessionStorage.removeItem('course_queue_id');
                 return data.items;
            }
        } catch (error) {
            console.log(error)
        }
    }

    const getMessage = async () => {
        const apiURL = getURL ('getmsg/');
        const options = getGetOptions();

        try {
            const resp = await fetch (apiURL, options);
            const respdata = await resp.json();
            if (respdata.status === "success"){
                return respdata.data;
            }
        } catch (error) {
            console.log(error);
        }
        return null;
    }

    const sendMessage = async (to_id) => {
        const from_id = user.id;
        const timeof = new Date().toLocaleString();//toISOString();
        console.log(timeof);
        const apiURL = getURL ('putmsg/');
        const options = getPostOptions({"from_id": from_id, "to_id": to_id , "timeof": timeof, "content": messageRef.current.value.substring(0, 128)});
        console.log(messageRef.current.value)
        fetch (apiURL, options).then(e => setSendTo(null)).catch (err => console.log(err));
    }

    const dequeue = async (item_id) => {
        const apiURL = getURL('deleteitem');
        const options = getPostOptions({"item_id": item_id});

        try {
            await fetch(apiURL, options);
        } catch (error) {
            console.log(error);
        }
    }

    const itemhandler = async () => {
        const result = await getItems();
        if (result) {
            console.log(JSON.stringify(result));
            sortItems(result);
            setQitems (result);
        }
    }

    const msgGetter = async () => {
        const result = await getMessage();
        if (result){
            result.time = new Date(result.time).toLocaleString();
            setMsg (result);
        }
    }

    useEffect(() => {
        if (lastMessage !== null){
            console.log(lastMessage?.data)
            if (lastMessage.data == 'QUEUE_CHANGE'){
                if (!storage) navHome();
                else itemhandler();
            }
            else if (lastMessage.data === 'NEW_MESSAGE'){
                msgGetter();
            }
        }
    },[lastMessage]);

    useEffect(() => {
        if (!storage) navHome();
        else itemhandler();
    },[])

    useEffect(() => {
        msgGetter();
    },[]);

    return (<div>
        <CoursePage user={user} props={qitems} dequeue={dequeue} refArr={refArr} clickAdd={clickAddItem} clickUpd={clickUpdItem} clickMsg={clickMessage}/>
            <div id={sendto&&user.admin?"":"hide"}> <WriteBox sender={sendMessage} msgRef={messageRef} unset={unsetSendTo} sendProps={sendto}/> </div>
            <div id={msg&&!user?.admin?"":"hide"} > <MessageBox closer={() => setMsg(null)} msgProp={msg}/> </div>
        </div>);
}