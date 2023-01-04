import { useEffect, useState } from "react";
import { useFetcher, useNavigate } from "react-router-dom";
import { getPostOptions, getURL } from "../util/apihelpers";
import CoursePage from "../views/page/coursepage";
import { useWebSocket } from "react-use-websocket/dist/lib/use-websocket";

export default function CoursePresenter (){
    const {lastMessage} = useWebSocket('ws://localhost:8080', {share:true});

    const [qitems, setQitems] = useState(null);
    const nav = useNavigate();
    const navHome = () => nav('/');
    const [storage, setStorage] = useState (sessionStorage.getItem('course_queue_id'))
    const user = JSON.parse(localStorage.getItem('user'));

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

    const dequeue = async (item_id) => {
        const apiURL = getURL('deleteitem');
        const options = getPostOptions({"item_id": item_id});

        try {
            await fetch(apiURL, options);
        } catch (error) {
            console.log(error);
        }
    }

    useEffect(() => {
        if (lastMessage !== null){
            console.log(lastMessage?.data)
            if (lastMessage.data == 'QUEUE_CHANGE'){
                const itemhandler = async () => {
                    const result = await getItems();
        
                    if (result) {
                        setQitems (result);
                    }
                }
                if (!storage) navHome();
                else itemhandler();
            }
        }
    },[lastMessage]);

    useEffect(() => {
        const itemhandler = async () => {
            const result = await getItems();

            if (result) {
                console.log(JSON.stringify(result));
                setQitems (result);
            }
        }
        if (!storage) navHome();
        else itemhandler();
    },[])

    return <CoursePage user={user} props={qitems} dequeue={dequeue}/>;
}