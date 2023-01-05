import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom";
import { getGetOptions, getPostOptions, getURL } from "../util/apihelpers";
import Home from "../views/page/home";
import { useWebSocket } from "react-use-websocket/dist/lib/use-websocket";

export default function HomePresenter (){
    const {lastMessage} = useWebSocket('ws://localhost:8080', {share:true});
    const nav = useNavigate();
    const goto = () => nav('/courseitems');
    const navItems = (course_id) => {
        sessionStorage.setItem('course_queue_id',JSON.stringify(course_id));
        goto();
    }

    const user = JSON.parse(localStorage.getItem('user'));

    const [courses, setCourses] = useState (null);
    const getCourses = async () => {
        const retURL = getURL('courses/');
        const options = getGetOptions();

        try {
            const resp = await fetch (retURL, options);
            const data = await resp.json();
            if (data.status === "success") return data;
        } catch (err){
            console.log(err);
        }
        return null;
    }

    useEffect(() => {
        if (lastMessage !== null){
            console.log("locktest: " + lastMessage)
            if (lastMessage.data == 'COURSE_CHANGE'){
                const changeHandler = async ()=> {
                    const res = await getCourses();
                    if (res){
                        setCourses(res);
                    }
                }
                changeHandler();
            }
        }
    },[lastMessage])

    const toggleCourse = async (course_id) => {
        const apiURL = getURL('toggle/');
        const options = getPostOptions({"course_id": course_id});
        fetch(apiURL, options).catch(err => console.log(err));
    }

    console.log("mango: " + JSON.stringify(courses));
    useEffect(() => {
        const courseHandler = async () => {
            const result = await getCourses();
            //console.log(result);
            if (result){
                console.log(result);
                setCourses(result);
            }
            //console.log("ran");
        }
        courseHandler();
    },[])

    return (courses?<Home toggle={toggleCourse} props={courses.courselist.sort((a, b) => a.id < b.id?-1:1)} isAdmin={user?.admin} navigate={navItems}/>:<div>Loading!</div>);
}